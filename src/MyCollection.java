import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class which contain collection of Vehicles
 */
public class MyCollection {
    private Queue<Vehicle> queue = new PriorityQueue<>();

    /**
     * Returns collections. Use only for changing element by some field!!!
     * @return
     */
    public Queue<Vehicle> getQueue() {
        return queue;
    }

    /**
     * Show all elements from collection
     */
    public void show() {
        for (Vehicle vehicle : queue) {
            System.out.println(vehicle.toString());
        }
    }

    /**
     * Show info about collection(Type and size)
     */
    public void info() {
        System.out.println("Type of collection is " + queue.getClass().toString() + ". Size of collection is " + queue.size());
    }

    /**
     * Method adds <i>vehicle</i> to collection
     *
     * @param vehicle
     */
    public void add(Vehicle vehicle) {
        queue.offer(vehicle);
    }

    /**
     * Method changes element with <i>id</i> to <i>vehicle</i>
     *
     * @param id
     * @param vehicle
     */
    public void update(int id, Vehicle vehicle) {
        Queue<Vehicle> prom = new PriorityQueue<>(queue);
        Queue<Vehicle> updateQueue = new PriorityQueue<>();
        while(!prom.isEmpty()){
            Vehicle v = prom.poll();
            if (v.getId()!=id){
                updateQueue.offer(v);
            }
        }
        updateQueue.offer(vehicle);
        queue = updateQueue;
    }

    /**
     * Method removes element with <i>idToRemove</i>
     *
     * @param idToRemove
     */
    public void removeById(int idToRemove) {
        Queue<Vehicle> prom = queue;
        Queue<Vehicle> updateQueue = new PriorityQueue<>();
        while(!prom.isEmpty()){
            Vehicle v = prom.poll();
            if (v.getId()!=idToRemove){
               updateQueue.offer(v);
            }
        }
        queue = updateQueue;
    }

    /**
     * Removes all element from collections
     */
    public void clear() {
        if (queue.isEmpty()){
            System.out.println("Collection is already empty");
        }else{
            queue.clear();
            System.out.println("Collection is empty");
        }
    }

    /**
     * Removes first element in collection
     */
    public void removeFirst() {
        queue.poll();
    }

    /**
     * Removes and return first element in collection
     *
     * @return first element in collection
     */
    public String showFirst() {
        return queue.poll().toString();
    }

    /**
     * Returns max element from collection
     *
     * @return max element
     */
    private Vehicle getMax() {
        Queue<Vehicle> queueVehicle = new PriorityQueue<>(queue.size(), Collections.reverseOrder());
        return queueVehicle.poll();
    }

    /**
     * Compare <i>vehicleAddIfMax</i> and max element. If <i>vehicleAddIfMax</i> higher, puts it in collection
     *
     * @param vehicleAddIfMax
     */
    public void addIfMax(Vehicle vehicleAddIfMax) {
        if (vehicleAddIfMax.compareTo(this.getMax()) > 0) {
            queue.offer(vehicleAddIfMax);
        }
    }
    /**
     * Groups collection by field <i>creationDate</i>
     *
     * @return Map which contains amount of elements in each group
     */
    public Map<LocalDateTime, Integer> groupByCreationDate() {
        Map<LocalDateTime, Integer> map = new TreeMap<>();
        Queue<Vehicle> printQueue = queue;
        for (Vehicle vehicle : printQueue) {
            LocalDateTime date = vehicle.getCreationDate();
            Integer count = map.get(date);
            if (count == null) {
                map.put(date, 1);
            } else {
                map.put(date, count + 1);
            }
        }
        return map;
    }

    /**
     * Saves collection to <i>fileName</i>
     *
     * @param fileName
     * @throws FileNotFoundException
     */
    public void save(String fileName) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName, false);
        Queue<Vehicle> printQueue = new PriorityQueue<>(queue);
        StringBuilder s = new StringBuilder();
        s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n").append("<Vehicles>\n");
        while (!printQueue.isEmpty()) {
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
        }
        s.append("</Vehicles>");
        byte[] buffer = s.toString().getBytes();
        try {
            fos.write(buffer, 0, buffer.length);
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }finally {
            fos.close();
        }
    }

    /**
     * Removes one element with FuelType = <i>fuelType</i>
     *
     * @param fuelType
     */
    public void removeByFuelType(String fuelType) {
        FuelType fuelType1 = FuelType.valueOf(fuelType);
        Queue<Vehicle> printQueue = new PriorityQueue<>(queue);
        for (Vehicle vehicle : queue) {
            if (vehicle.getFuelType().equals(fuelType1)) {
                printQueue.offer(vehicle);
                break;
            }
        }
        queue.removeAll(printQueue);
    }

    /**
     * Returns max element by field Name
     *
     * @return maxName element
     */
    public String getMaxName() {
        if (!queue.isEmpty()){
            Queue<Vehicle> printQueue = queue;
            String s = printQueue.poll().getName();
            for (Vehicle v : printQueue) {
                String prom = v.getName();
                if (s.compareTo(prom) < 0) {
                    s = prom;
                }
            }
            return "Max element is"+s;
        }else{
            return  "Collection is empty. Add element firstly";
        }
    }
    /**
     * Fill collection from XML file <i>fileName</i>
     *
     * @param fileName
     * @throws IOException
     */
    public void fillFromFile(String fileName) throws FileNotFoundException,IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String name = null;
        float x = 0;
        double y = 0;
        float enginePower = 0;
        int capacity = 0;
        String vehicleType = null;
        String fuelType = null;
        String line;
        String regex1 = "\\w*";
        String regex2 ="-?\\d+?(\\.d+?)?";
        int count = 0;
        while ((line = reader.readLine()) != null) {
            if (count == 0) {
                final Pattern pattern = Pattern.compile("<Name>(" + regex1 + ")</Name>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    name = matcher.group(1);
                    count++;
                }
            } else if (count == 1) {
                final Pattern pattern = Pattern.compile("<X>(" + regex2 +")</X>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    x = Float.parseFloat(matcher.group(1));
                    count++;
                }
            } else if (count == 2) {
                final Pattern pattern = Pattern.compile("<Y>(" + regex2 + ")</Y>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    y = Double.parseDouble(matcher.group(1));
                    count++;
                }
            } else if (count == 3) {
                final Pattern pattern = Pattern.compile("<EnginePower>(" + regex2 + ")</EnginePower>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    enginePower = Float.parseFloat(matcher.group(1));
                    count++;
                }
            } else if (count == 4) {
                final Pattern pattern = Pattern.compile("<Capacity>(" + regex2 + ")</Capacity>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    capacity = Integer.parseInt(matcher.group(1));
                    count++;
                }
            } else if (count == 5) {
                final Pattern pattern = Pattern.compile("<VehicleType>(" + regex1 + ")</VehicleType>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    vehicleType = matcher.group(1);
                    count++;
                }
            } else if (count == 6) {
                final Pattern pattern = Pattern.compile("<FuelType>(" + regex1 + ")</FuelType>", Pattern.DOTALL);
                final Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    fuelType = matcher.group(1);
                    count = 0;
                    if (!name.isEmpty()) {
                        Vehicle vehicle = new Vehicle(name, new Coordinates(x, y), enginePower,
                                capacity, VehicleType.valueOf(vehicleType), FuelType.valueOf(fuelType));
                        this.add(vehicle);
                    }
                }
            }
        }
    }

    /**
     * Check collection by emptiness
     * @return
     */
    public boolean checkToEmpty() {
        return queue.isEmpty();
    }
}
