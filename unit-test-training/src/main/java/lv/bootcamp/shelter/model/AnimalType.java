package lv.bootcamp.shelter.model;

public enum AnimalType {

    DOG("Dog", 15),
    CAT("Cat", 18),
    BIRD("Bird", 25),
    RABBIT("Rabbit", 10);

    private final String displayName;
    private final int averageLifespanYears;

    AnimalType(String displayName, int averageLifespanYears) {
        this.displayName = displayName;
        this.averageLifespanYears = averageLifespanYears;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAverageLifespanYears() {
        return averageLifespanYears;
    }

    /**
     * Returns true if the given age exceeds half of the average lifespan for this animal type.
     */
    public boolean isSenior(int age) {
        return age > averageLifespanYears / 2;
    }

    /**
     * Looks up an AnimalType by its display name (case-insensitive).
     * Returns null if no match is found.
     */
    public static AnimalType fromDisplayName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }
        for (AnimalType type : values()) {
            if (type.displayName.equalsIgnoreCase(name.trim())) {
                return type;
            }
        }
        return null;
    }
}
