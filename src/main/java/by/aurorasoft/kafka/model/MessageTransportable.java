package by.aurorasoft.kafka.model;

public class MessageTransportable {

    private final Long id;
    /** seconds */
    private final long datetime;
    private final float latitude;
    private final float longitude;
    private final int altitude;
    private final int speed;
    private final int amount_satellite;
    private final int course;
    private final String params;
    private final long unitId;
    private final boolean isArchive;
    private final boolean isValid;

    public MessageTransportable(Long id,
                                long datetime,
                                float latitude,
                                float longitude,
                                int altitude,
                                int speed,
                                int amount_satellite,
                                int course,
                                String params,
                                long unitId,
                                boolean isArchive,
                                boolean isValid) {
        this.id = id;
        this.datetime = datetime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.speed = speed;
        this.amount_satellite = amount_satellite;
        this.course = course;
        this.params = params;
        this.unitId = unitId;
        this.isArchive = isArchive;
        this.isValid = isValid;
    }

    public Long getId() {
        return id;
    }

    public long getDatetime() {
        return datetime;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getAltitude() {
        return altitude;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAmount_satellite() {
        return amount_satellite;
    }

    public int getCourse() {
        return course;
    }

    public String getParams() {
        return params;
    }

    public long getUnitId() {
        return unitId;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public boolean isValid() {
        return isValid;
    }
}
