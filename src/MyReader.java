import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

/**
 * Class for reading
 */
public class MyReader {
    /**
     * Method gets new Vehicle from User
     * @return Vehicle
     */
    public static Vehicle getElementFromConsole(Scanner scanner){
        String name;
        while (true){
            System.out.println("Enter the name:");
            name  = scanner.nextLine();
            if (!name.equals("")){
                break;
            }
        }
        System.out.println("Enter X coordinate:");
        String  x_check;
        float x;
        while (true) {
            x_check = scanner.nextLine();
            try{
                x = Float.parseFloat(x_check);
                if (x>-898){
                    break;
                }else throw new NumberFormatException();
            }catch (NumberFormatException e){
                System.out.println("X has to be float type and  > -898.Try again");
            }
        }
        System.out.println("Enter Y coordinate:");
        String y_check;
        double y;
        while (true) {
            y_check = scanner.nextLine();
            try{
                y = Double.parseDouble(y_check);
                break;
            }catch (NumberFormatException e){
                System.out.println("Y has to be double type.Try again");
            }
        }
        Coordinates coordinates = new Coordinates(x,y);
        System.out.println("Enter Engine power:");
        String enginePower_check;
        float enginePower;
        while (true){
            enginePower_check = scanner.nextLine();
            try{
                enginePower = Float.parseFloat(enginePower_check);
                if (enginePower>0){
                    break;
                }else throw new NumberFormatException();
            }catch (NumberFormatException e){
                System.out.println("Engine power has to be float and > 0. Try again");
            }
        }
        System.out.println("Enter capacity:");
        String capacity_check;
        int capacity;
        while(true){
            capacity_check = scanner.nextLine();
            try{
                capacity = Integer.parseInt(capacity_check);
                if (capacity>0){
                    break;
                }else throw new NumberFormatException();
            }catch (NumberFormatException e){
                System.out.println("Capacity has to be int and > 0. Try again");
            }
        }
        System.out.println("Choose type of vehicle: "+ VehicleType.showAllValues());
        String vehicleType_check = scanner.nextLine();
        VehicleType vehicleType;
        try{
            vehicleType = VehicleType.valueOf(vehicleType_check);
        }catch (IllegalArgumentException e){
            System.out.println("You tried to enter incorrect data. The field will be null");
            vehicleType = null;
        }
        System.out.println("Choose type of fuel: "+ FuelType.showAllValues());
        String fuelType_check = scanner.nextLine();
        FuelType fuelType;
        try{
            fuelType = FuelType.valueOf(fuelType_check);
        }catch (IllegalArgumentException e){
            System.out.println("You tried to enter incorrect data. The field will be null");
            fuelType = null;
        }
        Vehicle vehicle = new Vehicle(name, coordinates, enginePower, capacity, vehicleType, fuelType);
        return vehicle;
    }
    public static Vehicle getElementFromConsoleToUpdate(Scanner scanner, int id) {
        String name;
        while (true){
            System.out.println("Enter the name:");
            name  = scanner.nextLine();
            if (!name.equals("")){
                break;
            }
        }
        System.out.println("Enter X coordinate:");
        String  x_check;
        float x;
        while (true) {
            x_check = scanner.nextLine();
            try{
                x = Float.parseFloat(x_check);
                if (x>-898){
                    break;
                }else throw new NumberFormatException();
            }catch (NumberFormatException e){
                System.out.println("X has to be float type and  > -898.Try again");
            }
        }
        System.out.println("Enter Y coordinate:");
        String y_check;
        double y;
        while (true) {
            y_check = scanner.nextLine();
            try{
                y = Double.parseDouble(y_check);
                break;
            }catch (NumberFormatException e){
                System.out.println("Y has to be double type.Try again");
            }
        }
        Coordinates coordinates = new Coordinates(x,y);
        System.out.println("Enter Engine power:");
        String enginePower_check;
        float enginePower;
        while (true){
            enginePower_check = scanner.nextLine();
            try{
                enginePower = Float.parseFloat(enginePower_check);
                if (enginePower>0){
                    break;
                }else throw new NumberFormatException();
            }catch (NumberFormatException e){
                System.out.println("Engine power has to be float and > 0. Try again");
            }
        }
        System.out.println("Enter capacity:");
        String capacity_check;
        int capacity;
        while(true){
            capacity_check = scanner.nextLine();
            try{
                capacity = Integer.parseInt(capacity_check);
                if (capacity>0){
                    break;
                }else throw new NumberFormatException();
            }catch (NumberFormatException e){
                System.out.println("Capacity has to be int and > 0. Try again");
            }
        }
        System.out.println("Choose type of vehicle: "+ VehicleType.showAllValues());
        String vehicleType_check = scanner.nextLine();
        VehicleType vehicleType;
        try{
            vehicleType = VehicleType.valueOf(vehicleType_check);
        }catch (IllegalArgumentException e){
            System.out.println("You tried to enter incorrect data. The field will be null");
            vehicleType = null;
        }
        System.out.println("Choose type of fuel: "+ FuelType.showAllValues());
        String fuelType_check = scanner.nextLine();
        FuelType fuelType;
        try{
            fuelType = FuelType.valueOf(fuelType_check);
        }catch (IllegalArgumentException e){
            System.out.println("You tried to enter incorrect data. The field will be null");
            fuelType = null;
        }
        Vehicle vehicle = new Vehicle(id,name, coordinates, enginePower, capacity, vehicleType, fuelType);
        return vehicle;
    }

    /**
     * Read and execute script
     * @param scriptFile
     * @param saveFile
     * @param collection
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static void readScript(String scriptFile, String saveFile,MyCollection collection)throws IOException,IllegalArgumentException {
        BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
        String command;
        while ((command = reader.readLine()) != null) {
            if (command.equals("help")) {
                Main.printOut();
            } else if (command.equals("info")) {
                collection.info();
            } else if (command.equals("show")) {
                collection.show();
            } else if (command.equals("add")) {
                String name = reader.readLine();
                Float x = Float.parseFloat(reader.readLine());
                Double y = Double.parseDouble(reader.readLine());
                Coordinates coordinates = new Coordinates(x, y);
                float enginePower = Float.parseFloat(reader.readLine());
                int capacity = Integer.parseInt(reader.readLine());
                String vehicleType = reader.readLine();
                String fuelType = reader.readLine();
                Vehicle vehicle = new Vehicle(name, coordinates, enginePower, capacity, VehicleType.valueOf(vehicleType), FuelType.valueOf(fuelType));
                collection.add(vehicle);
            } else if (command.equals("update")) {
                int id = Integer.parseInt(reader.readLine());
                String name = reader.readLine();
                Float x = Float.parseFloat(reader.readLine());
                Double y = Double.parseDouble(reader.readLine());
                Coordinates coordinates = new Coordinates(x, y);
                float enginePower = Float.parseFloat(reader.readLine());
                int capacity = Integer.parseInt(reader.readLine());
                String vehicleType = reader.readLine();
                String fuelType = reader.readLine();
                Vehicle vehicle = new Vehicle(name, coordinates, enginePower, capacity, VehicleType.valueOf(vehicleType), FuelType.valueOf(fuelType));
                collection.update(id, vehicle);
            } else if (command.equals("remove_by_id")) {
                int id = Integer.parseInt(reader.readLine());
                collection.removeById(id);
            } else if (command.equals("clear")) {
                collection.clear();
            } else if (command.equals("save")) {
                collection.save(saveFile);
            } else if (command.equals("execute_script")) {
                String sName = reader.readLine();
                readScript(sName, saveFile,collection);
            } else if (command.equals("remove_first")) {
                collection.removeFirst();
            } else if (command.equals("remove_head")) {
                collection.showFirst();
            } else if (command.equals("add_if_max")) {
                String name = reader.readLine();
                Float x = Float.parseFloat(reader.readLine());
                Double y = Double.parseDouble(reader.readLine());
                Coordinates coordinates = new Coordinates(x, y);
                float enginePower = Float.parseFloat(reader.readLine());
                int capacity = Integer.parseInt(reader.readLine());
                String vehicleType = reader.readLine();
                String fuelType = reader.readLine();
                Vehicle vehicle = new Vehicle(name, coordinates, enginePower,
                        capacity, VehicleType.valueOf(vehicleType), FuelType.valueOf(fuelType));
                collection.addIfMax(vehicle);
            } else if (command.equals("remove_any_by_fuel_type")) {
                String fuelType = reader.readLine();
                collection.removeByFuelType(fuelType);
            } else if (command.equals("max_by_name")) {
                collection.getMaxName();
            } else if (command.equals("group_counting_by_creation_date")) {
                Map<LocalDate, Integer> LocalDateMap = collection.groupByCreationDate();
            }
        }
    }
}
