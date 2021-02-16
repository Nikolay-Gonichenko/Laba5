import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Class of elements which is contained in collection
 */
public class Vehicle implements Comparable<Vehicle> {
    private static int ID_START = 1;
    private int id;
    private String name;
    private Coordinates coordinates;
    private LocalDateTime creationDate;
    private float enginePower;
    private int capacity;
    private VehicleType vehicleType;
    private FuelType fuelType;



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
                vehicleType == vehicle.vehicleType &&
                fuelType == vehicle.fuelType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, enginePower, capacity, vehicleType, fuelType);
    }

    public Vehicle(String name, Coordinates coordinates, float enginePower, int capacity, VehicleType type, FuelType fuelType) {
        id  = ID_START++;
        creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.vehicleType = type;
        this.fuelType = fuelType;
    }
    public Vehicle(int id, String name, Coordinates coordinates, float enginePower, int capacity, VehicleType vehicleType, FuelType fuelType) {
        this.id = id;
        creationDate = LocalDateTime.now();
        this.name = name;
        this.coordinates = coordinates;
        this.enginePower = enginePower;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
        this.fuelType = fuelType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public float getEnginePower() {
        return enginePower;
    }

    public int getCapacity() {
        return capacity;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public String toString() {
        return "Vehicle " +
                "id:" + id +
                ", name: " + name +
                ", coordinates: " + coordinates.toString() +
                ", creationDate: " + creationDate +
                ", enginePower: " + enginePower +
                ", capacity: " + capacity +
                ", vehicleType: " + vehicleType +
                ", fuelType: " + fuelType;
    }
}
