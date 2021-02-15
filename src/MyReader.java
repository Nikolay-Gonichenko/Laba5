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
                }
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
                }
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
                }
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
}
