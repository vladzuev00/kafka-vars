package by.aurorasoft.kafka.model;

import java.time.Instant;

public class MessageTransportable {

    private final Long id;
    private final Instant time;
    private final float latitude;
    private final float longitude;
    private final int altitude;
    private final int speed;
    private final int amountSatellite;
    private final int course;
    /**
     * kilometers
     */
    private final double gpsOdometer;
    /**
     * GSM level strength (Percent)
     */
    private final int gsmStrength;
    /**
     * tracker battery charge (Percent)
     */
    private final int battery;
    /**
     * tracker battery voltage (mV)
     */
    private final int batteryVoltage;

    /**
     * external power supply of the device (Volt)
     */
    private final float onboardVoltage;

    /**
     * acceleration of acceleration (G)
     */
    private final float ecoAcceleration;
    /**
     * acceleration of braking (G)
     */
    private final float ecoBraking;

    /**
     * cornering acceleration (G)
     */
    private final float ecoCornering;

    /**
     * bump acceleration (G)
     */
    private final float ecoBump;

    private final String params;
    private final long unitId;
    private final boolean isArchive;
    private final boolean isValid;

    public MessageTransportable(Long id, Instant time, float latitude, float longitude, int altitude, int speed,
                                int amountSatellite, int course, double gpsOdometer, int gsmStrength, int battery,
                                int batteryVoltage, float onboardVoltage,
                                float ecoAcceleration, float ecoBraking, float ecoCornering, float ecoBump,
                                String params, long unitId, boolean isArchive, boolean isValid) {
        this.id = id;
        this.time = time;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.amountSatellite = amountSatellite;
        this.course = course;
        this.gpsOdometer = gpsOdometer;
        this.gsmStrength = gsmStrength;
        this.battery = battery;
        this.batteryVoltage = batteryVoltage;
        this.onboardVoltage = onboardVoltage;
        this.ecoAcceleration = ecoAcceleration;
        this.ecoBraking = ecoBraking;
        this.ecoCornering = ecoCornering;
        this.ecoBump = ecoBump;
        this.params = params;
        this.unitId = unitId;
        this.isArchive = isArchive;
        this.isValid = isValid;
    }
}
