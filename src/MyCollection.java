import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyCollection {
    private Queue<Vehicle> queue = new PriorityQueue<>();
    public void show(){
        Queue<Vehicle> printQueue = queue;
        while(!printQueue.isEmpty()){
            System.out.println(printQueue.poll().toString());
        }
    }
    public void info(){
        System.out.println("Type of collection is "+queue.getClass().toString()+". Size of collection is "+queue.size());
    }

    public void add(Vehicle vehicle) {
        queue.offer(vehicle);
    }

    public void update(int id,Vehicle vehicle) {
        Queue<Vehicle> updateQueue = new PriorityQueue<>();
        for (Vehicle vehicle1 : queue){
            if (vehicle1.getId()!=id){
               updateQueue.offer(queue.poll());
            }
        }
        updateQueue.offer(vehicle);
        queue = updateQueue;
    }

    public void removeById(int idToRemove) {
        Queue<Vehicle> updateQueue = new PriorityQueue<>();
        for (Vehicle vehicle1 : queue){
            if (vehicle1.getId()!=idToRemove){
                updateQueue.offer(queue.poll());
            }
        }
        queue = updateQueue;
    }

    public void clear() {
        queue.clear();
    }

    public void removeFirst() {
        queue.poll();
    }

    public String showFirst() {
        return queue.poll().toString();
    }
    private Vehicle getMax(){
        Queue<Vehicle> queueVehicle = new PriorityQueue<>(queue.size(),Collections.reverseOrder());
        return queueVehicle.poll();
    }

    public void addIfMax(Vehicle vehicleAddIfMax) {
        if (vehicleAddIfMax.compareTo(this.getMax())>0){
            queue.offer(vehicleAddIfMax);
        }
    }

    public void readScript(String scriptName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(scriptName));
        String command;
        while ((command=reader.readLine())!=null){

        }
    }

    public void save(String fileName) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(fileName,false);
        Queue<Vehicle> printQueue = queue;
        String start = "<Vehicles> \n";
        String finish = "</Vehicles>";
        StringBuilder s = new StringBuilder();
        while(!printQueue.isEmpty()){
            Vehicle vehicle = printQueue.poll();
            s.append("  <Vehicle>\n");
            s.append("      <Name>").append(vehicle.getName()).append("</Name>\n");
            s.append("      <X>").append(vehicle.getCoordinates().getX()).append("</X>\n");
            s.append("      <Y>").append(vehicle.getCoordinates().getY()).append("</Y>\n");
            s.append("      <EnginePower>").append(vehicle.getEnginePower()).append("</EnginePower>\n");
            s.append("      <Capacity>").append(vehicle.getCapacity()).append("</Capacity>\n");
            s.append("      <VehicleType>").append(vehicle.getVehicleType().toString()).append("</VehicleType>\n");
            s.append("      <FuelType>").append(vehicle.getFuelType().toString()).append("</FuelType>\n");
            s.append("  </Vehicle>\n");
            byte[] buffer = s.toString().getBytes();
            try {
                fos.write(buffer,0,buffer.length);
            } catch (IOException e) {
                System.out.println("Something went wrong");
            }
        }
    }

    public void removeByFuelType(String fuelType) {
        FuelType fuelType1 = FuelType.valueOf(fuelType);
        Queue<Vehicle> printQueue = queue;
        for (Vehicle vehicle:queue){
            if (vehicle.getFuelType().equals(fuelType1)){
                printQueue.offer(vehicle);
            }
        }
        queue.removeAll(printQueue);
    }

    public String getMaxName() {
        Queue<Vehicle> printQueue = queue;
        String s = printQueue.poll().getName();
        for (Vehicle v:printQueue){
            String prom = v.getName();
            if (s.compareTo(prom)<0){
                s = prom;
            }
        }
        return s;
    }

    public void fillFromFile(String fileName) throws  IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String name = null;
        float x = 0;
        double y = 0;
        float enginePower = 0;
        int capacity = 0;
        String vehicleType = null;
        String fuelType = null;
        String line;
        String regex = "\\d+";
        int count = 0;
        while ((line=reader.readLine())!=null) {
            if (count == 0) {
                final Pattern pattern = Pattern.compile("<Name>(" + regex + ")</Name>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    name = matcher.group(1);
                    count++;
                }
            } else if (count == 1) {
                final Pattern pattern = Pattern.compile("<X>(" + regex + ")</X>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    x = Float.parseFloat(matcher.group(1));
                    count++;
                }
            }else if (count == 2) {
                final Pattern pattern = Pattern.compile("<Y>(" + regex + ")</Y>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    y = Double.parseDouble(matcher.group(1));
                    count++;
                }
            }else if (count==3){
                final Pattern pattern = Pattern.compile("<EnginePowerr>(" + regex + ")</EnginePower>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    enginePower = Float.parseFloat(matcher.group(1));
                    count++;
                }
            }else if (count==4){
                final Pattern pattern = Pattern.compile("<Capacity>(" + regex + ")</Capacity>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    capacity = Integer.parseInt(matcher.group(1));
                    count++;
                }
            }else if (count==5){
                final Pattern pattern = Pattern.compile("<VehicleType>(" + regex + ")</VehicleType>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    vehicleType = matcher.group(1);
                    count++;
                }
            }else if (count==6){
                final Pattern pattern = Pattern.compile("<FuelType>(" + regex + ")</FuelType>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    fuelType = matcher.group(1);
                    count = 0;
                    if (name!=null){
                        Vehicle vehicle = new Vehicle(name,new Coordinates(x,y),enginePower,
                                capacity,VehicleType.valueOf(vehicleType),FuelType.valueOf(fuelType));
                        this.add(vehicle);
                    }

                }
            }
        }
    }
}
