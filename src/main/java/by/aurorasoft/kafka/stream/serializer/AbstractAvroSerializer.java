package by.aurorasoft.kafka.stream.serializer;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractAvroSerializer implements Serializer<SpecificRecordBase> {

    private Schema schema;

    @Override
    public void close() {
        // No-op
    }

    @Override
    public void configure(Map<String, ?> arg0, boolean arg1) {
        schema = this.createSchema();
    }

    protected abstract Schema createSchema();

    @Override
    public byte[] serialize(String topic, SpecificRecordBase data) {
        try {
            byte[] result = null;

            if (data != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BinaryEncoder binaryEncoder =
                        EncoderFactory.get().binaryEncoder(byteArrayOutputStream, null);

                DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
                datumWriter.write(data, binaryEncoder);

                binaryEncoder.flush();
                byteArrayOutputStream.close();

                result = byteArrayOutputStream.toByteArray();
            }
            return result;
        } catch (IOException ex) {
            throw new SerializationException("Can't serialize data='" + data + "' for topic='" + topic + "'", ex);
        }
    }
}
