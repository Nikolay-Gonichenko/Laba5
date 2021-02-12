import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class MyCollection {
    private Queue<Vehicle> queue = new PriorityQueue<>();
    public void show(){
        Queue<Vehicle> printQueue = queue;
        while(!queue.isEmpty()){
            System.out.println(queue.poll());
        }
    }
    public void info(){
        System.out.println("Type of collection is "+queue.getClass().toString()+". Size of collection is "+queue.size());
    }
}
