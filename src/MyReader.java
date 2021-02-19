import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Class for reading
 */
public class MyReader {
    private static boolean chekByZero(String line) {
        boolean check = false;
        char[] chars = line.toCharArray();
        if (chars.length > 1 && !line.equals("-0")) {
            if (chars[0] == '0' && chars[1] != '.' || chars[0] == '-' && chars[1] == '0' && chars[2] != '.'){
                check = false;
            } else {
                check = true;
            }
        } else check = true;
        return check;
    }

    /**
     * Method gets new Vehicle from User
     *
     * @return Vehicle
     */
    public static Vehicle getElementFromConsole(Scanner scanner) {
        String name;
        while (true) {
            System.out.println("Enter the name:");
            name = scanner.nextLine();
            if (!name.equals("")) {
                break;
            }
        }
        System.out.println("Enter X coordinate (X is float and > -898):");
        String x_check;
        float x;
        while (true) {
            x_check = scanner.nextLine();
            try {
                if (chekByZero(x_check)) {
                    x = Float.parseFloat(x_check);
                    if (x > -898) {
                        if (x<=Float.MAX_VALUE){
                            break;
                        }else throw new TooBigNumberException();
                    } else throw new NumberFormatException();
                } else throw new ZeroCheckException();
            } catch (TooBigNumberException e){
                System.out.println("You tried to enter too big number");
            } catch (ZeroCheckException e) {
                System.out.println("You wrote '000000' or '-000000' or something same. If you want fill field like 0(1/etc),please write just 0(1/etc)");
            } catch (NumberFormatException e) {
                System.out.println("X has to be float type and  > -898.Try again");
            }
        }
        System.out.println("Enter Y coordinate (Y is double):");
        String y_check;
        double y;
        while (true) {
            y_check = scanner.nextLine();
            try {
                if (chekByZero(y_check)) {
                    y = Double.parseDouble(y_check);
                    if (y<=Double.MAX_VALUE && y>=Double.MIN_VALUE){
                        break;
                    }else throw new TooBigNumberException();
                } else throw new ZeroCheckException();
            }catch (TooBigNumberException e){
                System.out.println("You tried to enter too big or too small number");
            } catch (ZeroCheckException e) {
                System.out.println("You wrote '000000' or '-000000' or something same. If you want fill field like 0(1/etc),please write just 0(1/etc)");
            } catch (NumberFormatException e) {
                System.out.println("Y has to be double type.Try again");
            }
        }
        Coordinates coordinates = new Coordinates(x, y);
        System.out.println("Enter Engine power (EnginePower is float and >0):");
        String enginePower_check;
        float enginePower;
        while (true) {
            enginePower_check = scanner.nextLine();
            try {
                enginePower = Float.parseFloat(enginePower_check);
                if (chekByZero(enginePower_check)) {
                    if (enginePower > 0) {
                        if (enginePower<=Float.MAX_VALUE){
                            break;
                        }else throw new TooBigNumberException();
                    } else throw new NumberFormatException();
                } else throw new ZeroCheckException();
            } catch (TooBigNumberException e){
                System.out.println("You tried to enter too big number");
            } catch (ZeroCheckException e) {
                System.out.println("You wrote '000000' or '-000000' or something same. If you want fill field like 0(1/etc),please write just 0(1/etc)");
            } catch (NumberFormatException e) {
                System.out.println("Engine power has to be float and > 0. Try again");
            }
        }
        System.out.println("Enter capacity (Capacity is int and >0):");
        String capacity_check;
        int capacity;
        while (true) {
            capacity_check = scanner.nextLine();
            try {
                if (chekByZero(capacity_check)) {
                    capacity = Integer.parseInt(capacity_check);
                    if (capacity > 0) {
                        break;
                    } else throw new NumberFormatException();
                } else throw new ZeroCheckException();
            } catch (ZeroCheckException e) {
                System.out.println("You wrote '000000' or '-000000' or something same. If you want fill field like 0(1/etc),please write just 0(1/etc)");
            } catch (NumberFormatException e) {
                System.out.println("Capacity has to be int and > 0. Try again");
            }
        }
        System.out.println("Choose type of vehicle: " + VehicleType.showAllValues());
        String vehicleType_check = scanner.nextLine();
        VehicleType vehicleType;
        try {
            vehicleType = VehicleType.valueOf(vehicleType_check);
        } catch (IllegalArgumentException e) {
            System.out.println("You tried to enter incorrect data. The field will be null");
            vehicleType = null;
        }
        System.out.println("Choose type of fuel: " + FuelType.showAllValues());
        String fuelType_check = scanner.nextLine();
        FuelType fuelType;
        try {
            fuelType = FuelType.valueOf(fuelType_check);
        } catch (IllegalArgumentException e) {
            System.out.println("You tried to enter incorrect data. The field will be null");
            fuelType = null;
        }
        Vehicle vehicle = new Vehicle(name, coordinates, enginePower, capacity, vehicleType, fuelType);
        return vehicle;
    }

    public static Vehicle getElementFromConsoleToUpdate(Scanner scanner, int id) {
        Vehicle vehicle = getElementFromConsole(scanner);
        String name = vehicle.getName();
        Coordinates coordinates = vehicle.getCoordinates();
        float enginePower = vehicle.getEnginePower();
        int capacity = vehicle.getCapacity();
        VehicleType vehicleType = vehicle.getVehicleType();
        FuelType fuelType = vehicle.getFuelType();
        Vehicle vehicleToReturn = new Vehicle(id, name, coordinates, enginePower, capacity, vehicleType, fuelType);
        return vehicleToReturn;
    }

    /**
     * Read and execute script
     *
     * @param scriptFile
     * @param saveFile
     * @param collection
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static boolean readScript(String scriptFile, String saveFile, MyCollection collection) throws IOException,
            IllegalArgumentException,IndexOutOfBoundsException, FileCycleException{
        BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
        String command;
        boolean check = true;
        while ((command = reader.readLine()) != null) {
            String[] commands = command.split(" ");
            if (commands[0].equals("help")) {
                Main.printOut();
            } else if (commands[0].equals("info")) {
                collection.info();
            } else if (commands[0].equals("show")) {
                collection.show();
            } else if (commands[0].equals("add")) {
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
            } else if (commands[0].equals("update")) {
                int id = Integer.parseInt(commands[1]);
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
            } else if (commands[0].equals("remove_by_id")) {
                int id = Integer.parseInt(commands[1]);
                collection.removeById(id);
            } else if (commands[0].equals("clear")) {
                collection.clear();
            } else if (commands[0].equals("save")) {
                collection.save(saveFile);
            } else if (commands[0].equals("execute_script")) {
                String sName = commands[1];
                if (scriptFile.equals(sName)) throw new FileCycleException();
                readScript(sName, saveFile, collection);
            } else if (commands[0].equals("remove_first")) {
                collection.removeFirst();
            } else if (commands[0].equals("remove_head")) {
                collection.showFirst();
            } else if (commands[0].equals("add_if_max")) {
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
            } else if (commands[0].equals("remove_any_by_fuel_type")) {
                String fuelType = commands[1];
                collection.removeByFuelType(fuelType);
            } else if (commands[0].equals("max_by_name")) {
                collection.getMaxName();
            } else if (commands[0].equals("group_counting_by_creation_date")) {
                Map<LocalDateTime, Integer> LocalDateMap = collection.groupByCreationDate();
                Set<LocalDateTime> keys = LocalDateMap.keySet();
                for (LocalDateTime key : keys) {
                    System.out.println("Creation date is " + key + " .The amounts is " + LocalDateMap.get(key));
                }
            }else if (commands[0].equals("exit")){
                check = false;
            }
        }
        return check;
    }
}
