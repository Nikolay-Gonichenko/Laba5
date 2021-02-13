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
        StringBuilder s = new StringBuilder();
        while(!printQueue.isEmpty()){
            s.append(printQueue.poll().toString());
        }
        byte[] buffer = s.toString().getBytes();
        try {
            fos.write(buffer,0,buffer.length);
        } catch (IOException e) {
            System.out.println("Something went wrong");
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

    public void fillFromFile(String fileName) throws FileNotFoundException,IOException {
        String name = null;
        Float x = (float) 0;
        Double y = (double) 0;
        Float enginePower = (float) 0;
        Integer capacity = 0;
        String type = null;
        String fuelType = null;
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String c;
        int count = 0;
        while((c=reader.readLine())!=null){
            StringBuilder stringBuilder = new StringBuilder();
            boolean check = false;
            for (int i=0;i<c.length();i++){
                if (c.charAt(i)=='<'){
                    check = false;
                }
                if (check){
                    stringBuilder.append(c.charAt(i));
                }
                if (c.charAt(i)=='>'){
                    check = true;
                }
            }
            if (!stringBuilder.toString().equals("")){
                count++;
            }
            if (count==0){
                name = stringBuilder.toString();
            }else if (count==1){
                x = Float.parseFloat(stringBuilder.toString());
            }else if (count==2){
                y = Double.parseDouble(stringBuilder.toString());
            }else if (count==3){
                enginePower = Float.parseFloat(stringBuilder.toString());
            }else if (count==4){
                capacity = Integer.parseInt(stringBuilder.toString());
            }else if (count==5){
                type = stringBuilder.toString();
            }else if (count==6){
                fuelType = stringBuilder.toString();
                count = 0;
            }
        }
        Vehicle vehicle = new Vehicle(name,new Coordinates(x,y),enginePower,
                capacity,VehicleType.valueOf(type),FuelType.valueOf(fuelType));
        this.add(vehicle);
    }
}
