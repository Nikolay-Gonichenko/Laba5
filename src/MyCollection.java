import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Collections;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

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
}
