import java.time.LocalDate;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class Vehicle implements Comparable<Vehicle> {
    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDate creationDate;
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

    public Vehicle(String name, Coordinates coordinates, float enginePower, int capacity, VehicleType type, FuelType fuelType) {
        id  = (int) (Math.random()*100);
        creationDate = LocalDate.now();
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.type = type;
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name= " + name + '\'' +
                ", coordinates= " + coordinates +
                ", creationDate= " + creationDate +
                ", enginePower= " + enginePower +
                ", capacity= " + capacity +
                ", type= " + type +
                ", fuelType= " + fuelType +
                '}';
    }
}
