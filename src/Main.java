import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
/**
 * @author Gonichenko Nikolay R3136
 * This is main class
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * This is start point of program
     * @param args
     */
    public static void main(String[] args) {
        printOut();
        System.out.println("Enter a file's name for starting");
        MyCollection collection = new MyCollection();
        String fileName = scanner.nextLine();
        boolean checkFile = false;
        while(!checkFile){
            try {
                collection.fillFromFile(fileName);
                checkFile = true;
            } catch (IOException e) {
                System.out.println("Something went wrong with reading xml file");
                fileName = scanner.nextLine();
            }
        }
        System.out.println("Enter a command");
        String command = scanner.nextLine();
        while(!command.equals("exit")){
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
                    System.out.println("Please enter an element");
                    Vehicle vehicle = getElement();
                    collection.add(vehicle);
                    break;
                case "update":
                    System.out.println("Please enter an id of element ");
                    scanner.nextLine();
                    int id = scanner.nextInt();
                    Vehicle vehicleUpdate = getElement();
                    collection.update(id,vehicleUpdate);
                    break;
                case "remove_by_id":
                    System.out.println("Please enter an id of element ");
                    scanner.nextLine();
                    int idToRemove = scanner.nextInt();
                    collection.removeById(idToRemove);
                    break;
                case "clear":
                    collection.clear();
                    break;
                case "save":
                    try {
                        collection.save(fileName);
                    } catch (FileNotFoundException e) {
                        System.out.println("File doesn't exist. Enter a command");
                    }
                    break;
                case "execute_script":
                    System.out.println("Enter a scriptName");
                    String scriptName = scanner.nextLine();
                    try {
                        collection.readScript(scriptName,fileName);
                    } catch (FileNotFoundException e) {
                        System.out.println("File doesn't exist. Enter command and scriptName again");
                        continue;
                    } catch (IOException e) {
                        System.out.println("Command in file incorrect. Executing of file has stopped.");
                    }
                    break;
                case "remove_first":
                    collection.removeFirst();
                    break;
                case "remove_head":
                    System.out.println(collection.showFirst());
                    break;
                case "add_if_max":
                    System.out.println("Please enter an id of element");
                    Vehicle vehicleAddIfMax = getElement();
                    collection.addIfMax(vehicleAddIfMax);
                    break;
                case "remove_any_by_fuel_type":
                    System.out.println("Enter a FuelType");
                    String fuelType = scanner.nextLine();
                    collection.removeByFuelType(fuelType);
                    break;
                case "max_by_name":
                    System.out.println("MaxName Element is "+collection.getMaxName());
                    break;
                case "group_counting_by_creation_date":
                    Map<LocalDate,Integer> LocalDateMap = collection.groupByCreationDate();
                    Set<LocalDate> keys = LocalDateMap.keySet();
                    for (LocalDate key:keys){
                        System.out.println("Creation date is "+key+" .The amounts is "+LocalDateMap.get(key));
                    }
                    break;
            }
            System.out.println("Enter a command");
            command = scanner.nextLine();
        }
    }

    /**
     * Method prints the legend
     */
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

    /**
     * Method gets new Vehicle from User
     * @return Vehicle
     */
    public static Vehicle getElement(){
        System.out.println("Enter the name:");
        String name = scanner.nextLine();
        System.out.println("Enter coordinates:");
        Float x = scanner.nextFloat();
        Double y = scanner.nextDouble();
        Coordinates coordinates = new Coordinates(x,y);
        System.out.println("Enter Engine power and capacity:");
        float enginePower = scanner.nextFloat();
        int capacity = scanner.nextInt();
        System.out.println("Choose type of vehicle: "+ VehicleType.showAllValues());
        String vehicleType = scanner.nextLine();
        System.out.println("Choose type of fuel: "+ FuelType.showAllValues());
        String fuelType = scanner.nextLine();
        Vehicle vehicle = new Vehicle(name, coordinates, enginePower, capacity, VehicleType.valueOf(vehicleType), FuelType.valueOf(fuelType));
        return vehicle;
    }
}
