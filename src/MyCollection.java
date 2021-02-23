import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     *
     * @return
     */
    public Queue<Vehicle> getQueue() {
        return queue;
    }

    /**
     * Show all elements from collection
     */
    public void show() {
        Queue<Vehicle> testQueue = new PriorityQueue<>(queue);
        while (!testQueue.isEmpty()) {
            System.out.println(testQueue.poll().toString());
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
        while (!prom.isEmpty()) {
            Vehicle v = prom.poll();
            if (v.getId() != id) {
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
        while (!prom.isEmpty()) {
            Vehicle v = prom.poll();
            if (v.getId() != idToRemove) {
                updateQueue.offer(v);
            }
        }
        queue = updateQueue;
    }

    /**
     * Removes all element from collections
     */
    public void clear() {
        if (queue.isEmpty()) {
            System.out.println("Collection is already empty");
        } else {
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
        queueVehicle.addAll(queue);
        return queueVehicle.poll();
    }

    /**
     * Compare <i>vehicleAddIfMax</i> and max element. If <i>vehicleAddIfMax</i> higher, puts it in collection
     *
     * @param vehicleAddIfMax
     */
    public void addIfMax(Vehicle vehicleAddIfMax) {
        if (queue.isEmpty()) {
            queue.offer(vehicleAddIfMax);
        } else {
            if (vehicleAddIfMax.compareTo(this.getMax()) > 0) {
                queue.offer(vehicleAddIfMax);
            }
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
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        String lineForReading;
        StringBuilder s = new StringBuilder();
        while ((lineForReading = reader.readLine()) != null) {
            if (!lineForReading.trim().equals("</Vehicles>")) {
                s.append(lineForReading).append("\n");
            }
        }
        reader.close();
        FileOutputStream fos = new FileOutputStream(fileName, false);
        Queue<Vehicle> printQueue = new PriorityQueue<>(queue);
        while (!printQueue.isEmpty()) {
            Vehicle vehicle = printQueue.poll();

            if (!vehicle.isInFile()) {
                s.append("  <Vehicle>\n");
                s.append("      <Name>").append(vehicle.getName()).append("</Name>\n");
                s.append("      <Id>").append(vehicle.getId()).append("</Id>\n");
                s.append("      <X>").append(vehicle.getCoordinates().getX()).append("</X>\n");
                s.append("      <Y>").append(vehicle.getCoordinates().getY()).append("</Y>\n");
                if (vehicle.getCreationDate() != null) {
                    s.append("      <CreationDate>").append(vehicle.getCreationDate()).append("</CreationDate>\n");
                } else {
                    s.append("      <CreationDate>").append("null").append("</CreationDate>\n");
                }
                s.append("      <EnginePower>").append(vehicle.getEnginePower()).append("</EnginePower>\n");
                s.append("      <Capacity>").append(vehicle.getCapacity()).append("</Capacity>\n");
                if (vehicle.getVehicleType() != null) {
                    s.append("      <VehicleType>").append(vehicle.getVehicleType().toString()).append("</VehicleType>\n");
                } else {
                    s.append("      <VehicleType>").append("null").append("</VehicleType>\n");
                }
                if (vehicle.getFuelType() != null) {
                    s.append("      <FuelType>").append(vehicle.getFuelType().toString()).append("</FuelType>\n");
                } else {
                    s.append("      <FuelType>").append("null").append("</FuelType>\n");
                }
                s.append("  </Vehicle>\n");
            }
            isFile(vehicle.getId());
        }
        s.append("</Vehicles>");
        byte[] buffer = s.toString().getBytes();
        try {
            fos.write(buffer, 0, buffer.length);
        } catch (
                IOException e) {
            System.out.println("Something went wrong");
        } finally {
            fos.close();
        }

    }

    /**
     * Private method for setting checkInFile as true
     * @param id
     * @return
     */
    private void isFile(int id){
        for (Vehicle v:queue){
            if (v.getId()==id){
                v.setInFile(true);
            }
        }
    }

    /**
     * Removes one element with FuelType = <i>fuelType</i>
     *
     * @param fuelType
     */
    public void removeByFuelType(String fuelType) throws IllegalArgumentException {
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
        if (!queue.isEmpty()) {
            Queue<Vehicle> printQueue = queue;
            String s = printQueue.poll().getName();
            for (Vehicle v : printQueue) {
                String prom = v.getName();
                if (s.compareTo(prom) < 0) {
                    s = prom;
                }
            }
            return "Max element is " + s;
        } else {
            return "Collection is empty. Add element firstly";
        }
    }

    /**
     * Fill collection from XML file <i>fileName</i>
     *
     * @param fileName
     * @throws IOException
     */
    public void fillFromFile(String fileName) throws FileNotFoundException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
        int id = 0;
        String name = null;
        float x = 0;
        double y = 0;
        LocalDateTime creationDate = null;
        float enginePower = 0;
        int capacity = 0;
        String vehicleType = null;
        String fuelType = null;
        String line1;
        String line;
        VehicleType vehicleType1 = null;
        FuelType fuelType1 = null;
        String regex1 = "\\w*";
        String regex2 = "-?\\d+?(\\.\\d+?)?";
        String regex3 = "\\d{4}-\\d{2}-\\d{2}\\w\\d{2}:\\d{2}:\\d{2}.\\d*";
        int count = 0;
        int countOfBrokenObjects = 0;
        ArrayList<String> namesOfBrokenObjects = new ArrayList<>();
        while ((line1 = reader.readLine()) != null) {
            line = line1.trim();
            if (!line.equals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                    && !line.equals("<Vehicles>") && !line.equals("<Vehicle>") && !line.equals("</Vehicles>") && !line.equals("</Vehicle>")) {
                if (count == 1) {
                    final Pattern pattern = Pattern.compile("<Id>(" + regex2 + ")</Id>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        try {
                            id = Integer.parseInt(matcher.group(1));
                            if (id>0){
                                count++;
                            }else throw new NumberFormatException();
                        } catch (NumberFormatException e) {
                            count = -1;
                            countOfBrokenObjects++;
                            namesOfBrokenObjects.add(name);
                        }
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                } else if (count == 0) {
                    final Pattern pattern = Pattern.compile("<Name>(" + regex1 + ")</Name>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        name = matcher.group(1);
                        count++;
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                } else if (count == 2) {
                    final Pattern pattern = Pattern.compile("<X>(" + regex2 + ")</X>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        try {
                            x = Float.parseFloat(matcher.group(1));
                            if (x>-898 && x<=Float.MAX_VALUE){
                                count++;
                            }else throw new NumberFormatException();
                        } catch (NumberFormatException e) {
                            count = -1;
                            countOfBrokenObjects++;
                            namesOfBrokenObjects.add(name);
                        }
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                } else if (count == 3) {
                    final Pattern pattern = Pattern.compile("<Y>(" + regex2 + ")</Y>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        try {
                            y = Double.parseDouble(matcher.group(1));
                            if (y>=Double.MIN_VALUE && y<=Double.MAX_VALUE){
                                count++;
                            }else throw new NumberFormatException();
                        } catch (NumberFormatException e) {
                            count = -1;
                            countOfBrokenObjects++;
                            namesOfBrokenObjects.add(name);
                        }
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                } else if (count == 4) {
                    final Pattern pattern = Pattern.compile("<CreationDate>(" + regex3 + ")</CreationDate>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        try {
                            creationDate = LocalDateTime.parse(matcher.group(1));
                            count++;
                        } catch (IllegalArgumentException e) {
                            count = -1;
                            countOfBrokenObjects++;
                            namesOfBrokenObjects.add(name);
                        }
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                } else if (count == 5) {
                    final Pattern pattern = Pattern.compile("<EnginePower>(" + regex2 + ")</EnginePower>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        try {
                            enginePower = Float.parseFloat(matcher.group(1));
                            if (enginePower>0 && enginePower<=Float.MAX_VALUE){
                                count++;
                            }else throw new NumberFormatException();
                        } catch (NumberFormatException e) {
                            count = -1;
                            countOfBrokenObjects++;
                            namesOfBrokenObjects.add(name);
                        }
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                } else if (count == 6) {
                    final Pattern pattern = Pattern.compile("<Capacity>(" + regex2 + ")</Capacity>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        try {
                            capacity = Integer.parseInt(matcher.group(1));
                            if (capacity>0){
                                count++;
                            }else throw new NumberFormatException();
                        } catch (NumberFormatException e) {
                            count = -1;
                            countOfBrokenObjects++;
                            namesOfBrokenObjects.add(name);
                        }
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                } else if (count == 7) {
                    final Pattern pattern = Pattern.compile("<VehicleType>(" + regex1 + ")</VehicleType>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        vehicleType = matcher.group(1);
                        count++;
                        try {
                            if (vehicleType.equals("null")) {
                                vehicleType1 = null;
                            } else {
                                vehicleType1 = VehicleType.valueOf(vehicleType);
                            }
                        } catch (IllegalArgumentException e) {
                            count = -1;
                            countOfBrokenObjects++;
                            namesOfBrokenObjects.add(name);
                        }
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                } else if (count == 8) {
                    final Pattern pattern = Pattern.compile("<FuelType>(" + regex1 + ")</FuelType>", Pattern.DOTALL);
                    final Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        fuelType = matcher.group(1);
                        try {
                            if (fuelType.equals("null")) {
                                fuelType1 = null;
                            } else {
                                fuelType1 = FuelType.valueOf(fuelType);
                            }
                        } catch (IllegalArgumentException e) {
                            count = 0;
                            countOfBrokenObjects++;
                            namesOfBrokenObjects.add(name);
                        }
                    } else {
                        count = -1;
                        countOfBrokenObjects++;
                        namesOfBrokenObjects.add(name);
                    }
                    if (name != null && count != 0) {
                        Vehicle vehicle = new Vehicle(id, name, new Coordinates(x, y), creationDate, enginePower,
                                capacity, vehicleType1, fuelType1);
                        this.add(vehicle);
                        count = 0;
                    }
                }
            } else if (line.equals("<Vehicle>")) {
                count = 0;
            }
        }
        if (countOfBrokenObjects == 0) {
            System.out.println("All vehicles were written");
        } else {
            System.out.println("Amount of incorrect objects: " + countOfBrokenObjects + ". Their names: " + namesOfBrokenObjects);
        }
        if (!queue.isEmpty()) {
            Vehicle.setIdStart(findMaxId(queue));
        } else {
            Vehicle.setIdStart(1);
        }
    }

    /**
     * Private Method for finding max element by ID
     * @param queue
     * @return
     */
    private int findMaxId(Queue<Vehicle> queue) {
        int max = 0;
        for (Vehicle v : queue) {
            if (v.getId() > max) {
                max = v.getId();
            }
        }
        max++;
        return max;
    }

    /**
     * Check collection by emptiness
     *
     * @return
     */
    public boolean checkToEmpty() {
        return queue.isEmpty();
    }
}
