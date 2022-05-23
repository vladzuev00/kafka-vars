package by.aurorasoft.kafka.model;

import by.nhorushko.aurutils.message.MessageType;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.apache.avro.reflect.Nullable;

import java.time.Instant;

@Value
@FieldNameConstants
public class MessageTransportable {

    @Nullable
    Long id;
    Instant time;
    float latitude;
    float longitude;
    int altitude;
    int speed;
    int amountSatellite;
    int course;
    /**
     * kilometers
     */
    double gpsOdometer;
    /**
     * GSM level strength (Percent)
     */
    int gsmStrength;
    /**
     * tracker battery charge (Percent)
     */
    int battery;
    /**
     * tracker battery voltage (mV)
     */
    int batteryVoltage;

    /**
     * external power supply of the device (Volt)
     */
    float onboardVoltage;

    /**
     * acceleration of acceleration (G)
     */
    float ecoAcceleration;
    /**
     * acceleration of braking (G)
     */
    float ecoBraking;

    /**
     * cornering acceleration (G)
     */
    float ecoCornering;

    /**
     * bump acceleration (G)
     */
    float ecoBump;

    String params;
    long unitId;
    boolean isArchive;
    MessageType type;
}
