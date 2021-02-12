public enum FuelType {
    KEROSENE("Kerosine"),
    NUCLEAR("Nuclear"),
    PLASMA("Plasma");
    private final String name;

    FuelType(String name) {
        this.name = name;
    }

    public static String showAllValues() {
        StringBuilder s = new StringBuilder("");
        for (FuelType env : FuelType.values()) {
            s.append(env.getName()).append(" ");
        }
        return s.toString();
    }

    private String getName() {
        return name;
    }
}
