import java.util.Objects;

public class Vehicle implements Comparable<Vehicle> {
    private int id;
    private String name;
    private Coordinates coordinates;
    private java.time.LocalDateTime creationDate;
    private float enginePower;
    private int capacity;
    private VehicleType type;
    private FuelType fuelType;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Vehicle vehicle) {
        if (id > vehicle.getId())
            return 1;
        if (id < vehicle.getId())
            return -1;
        return name.compareTo(vehicle.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id &&
                Float.compare(vehicle.enginePower, enginePower) == 0 &&
                capacity == vehicle.capacity &&
                Objects.equals(name, vehicle.name) &&
                Objects.equals(coordinates, vehicle.coordinates) &&
                Objects.equals(creationDate, vehicle.creationDate) &&
                type == vehicle.type &&
                fuelType == vehicle.fuelType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, enginePower, capacity, type, fuelType);
    }
}
