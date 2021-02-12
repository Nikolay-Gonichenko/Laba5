import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        printOut();
        System.out.println("Enter a file's name for starting");
        MyCollection collection = new MyCollection();
        String fileName = scanner.nextLine();
        String command = scanner.nextLine();
        while(command!="exit"){
            System.out.println("Enter a command \n");
            switch (command){
                case "help":
                    printOut();
                    break;
                case "info":
                    collection.info();
                    break;
                case "show":
                    collection.show();
                    break;
                case "add":
                    System.out.println("Please enter an element \n");
                    Vehicle vehicle = getElement();
                    collection.add(vehicle);
                    break;
                case "update":
                    System.out.println("Please enter an id of element \n");
                    scanner.nextLine();
                    int id = scanner.nextInt();
                    Vehicle vehicleUpdate = getElement();
                    collection.update(id,vehicleUpdate);
                    break;
                case "remove_by_id":
                    System.out.println("Please enter an id of element \n");
                    scanner.nextLine();
                    int idToRemove = scanner.nextInt();
                    collection.removeById(idToRemove);
                    break;
                case "clear":
                    collection.clear();
                    break;
                case "save":
                    break;
                case "execute_script":
                    break;
                case "exit":
                    break;
                case "remove_first":
                    collection.removeFirst();
                    break;
                case "remove_head":
                    System.out.println(collection.showFirst());
                    break;
                case "add_if_max":
                    System.out.println("Please enter an id of element \n");
                    scanner.nextLine();
                    Vehicle vehicleAddIfMax = getElement();
                    collection.addIfMax(vehicleAddIfMax);
                    break;
                case "remove_any_by_fuel_type":
                    break;
                case "max_by_name":
                    break;
                case "group_counting_by_creation_date":
                    break;
            }
        }
    }
    public static void printOut(){
        System.out.println("All commands: \n"
        + "help - show list of commands \n"
        + "info - show information about collection \n"
        + "show - show all elements of collection \n"
        + "add - add new element \n"
        + "update - update element with id \n"
        + "remove_by_id - remove element from collection \n"
        + "clear - clear the collection \n"
        + "save - save collection to file \n"
        + "execute_script - read and execute commands from file_name \n"
        + "exit - end the program \n"
        + "remove_first - remove first element \n"
        + "remove_head - remove fist element and delete him \n"
        + "add_if_max - add element if it's bigger than max element in collection \n"
        + "remove_any_by_fuel_type - remove elements which have this fuelType \n"
        + "max_by_name - show element with max name \n"
        + "group_counting_by_creation_date - group elements by creationDate and show amounts of each group \n");
    }
    public static Vehicle getElement(){
        System.out.println("Enter the name:");
        scanner.nextLine();
        String name = scanner.next();
        System.out.println("Enter coordinates:");
        scanner.nextLine();
        Float x = scanner.nextFloat();
        Double y = scanner.nextDouble();
        Coordinates coordinates = new Coordinates(x,y);
        System.out.println("Enter Engine power and capacity:");
        scanner.nextLine();
        float enginePower = scanner.nextFloat();
        int capacity = scanner.nextInt();
        System.out.println("Choose type of vehicle: "+ VehicleType.showAllValues());
        scanner.nextLine();
        String vehicleType = scanner.next();
        System.out.println("Choose type of fuel: "+ FuelType.showAllValues());
        scanner.nextLine();
        String fuelType = scanner.next();
        Vehicle vehicle = new Vehicle(name,coordinates,enginePower,capacity,VehicleType.valueOf(vehicleType),FuelType.valueOf(fuelType));
        return vehicle;
    }
}
