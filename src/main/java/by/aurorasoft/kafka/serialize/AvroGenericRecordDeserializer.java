package by.aurorasoft.kafka.serialize;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class AvroGenericRecordDeserializer implements Deserializer {
    private Schema schema = null;

    @Override
    public void configure(Map configs, boolean isKey) {
        schema = (Schema) configs.get("SCHEMA");
    }

//    @Override
//    public Object deserialize(String s, byte[] bytes) {
//        DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
//        SeekableByteArrayInput arrayInput = new SeekableByteArrayInput(bytes);
//        List<GenericRecord> records = new ArrayList<>();
//
//        DataFileReader<GenericRecord> dataFileReader;
//        try {
//            dataFileReader = new DataFileReader<>(arrayInput, datumReader);
//            while (dataFileReader.hasNext()) {
//                GenericRecord record = dataFileReader.next();
//                records.add(record);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return records;
//    }

    @Override
    public Object deserialize(String topic, byte[] bytes) {
        GenericRecord result = null;
        try {
            if (bytes != null) {
                DatumReader<GenericRecord> datumReader = new GenericDatumReader<>(schema);
                Decoder decoder = DecoderFactory.get().binaryDecoder(bytes, null);
                result = datumReader.read(null, decoder);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() {
        // do nothing
    }
}
