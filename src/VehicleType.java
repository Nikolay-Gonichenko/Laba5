public enum VehicleType {
    HELICOPTER("Helicopter"),
    SUBMARINE("Submarine"),
    CHOPPER("Chopper"),
    HOVERBOARD("Hoverboard"),
    SPACESHIP("Spaceship");

    private String name;

    VehicleType(String name) {
        this.name = name;
    }

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
