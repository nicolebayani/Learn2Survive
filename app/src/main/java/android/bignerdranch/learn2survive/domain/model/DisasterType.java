package android.bignerdranch.learn2survive.domain.model;

public enum DisasterType {
    EARTHQUAKE("earthquake"),
    TYPHOON("typhoon"),
    FLOOD("flood"),
    FIRE("fire");

    private final String value;

    DisasterType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static DisasterType fromValue(String value) {
        for (DisasterType type : DisasterType.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return EARTHQUAKE;
    }
}
