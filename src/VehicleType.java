/**
 * Enum of VehicleType
 */
public enum VehicleType {
    HELICOPTER("HELICOPTER"),
    SUBMARINE("SUBMARINE"),
    CHOPPER("CHOPPER"),
    HOVERBOARD("HOVERBOARD"),
    SPACESHIP("SPACESHIP");

    private String name;

    VehicleType(String name) {
        this.name = name;
    }

    /**
     * Show all values in enum
     * @return String with all Values
     */
    public static String showAllValues() {
        StringBuilder s = new StringBuilder();
        for (VehicleType env : VehicleType.values()) {
            s.append(env.getName()).append(" ");
        }
        return s.toString();
    }

    private String getName() {
        return name;
    }
}
