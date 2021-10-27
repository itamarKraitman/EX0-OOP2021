package ex0.algo;

import java.util.Collections;

/**
 * This class implements a data structure with FIFO Logic in order to make order in the calls for elevators
 */
public class callsQueue {
    public callNode front, end;

    public callsQueue() {
        this.front = this.end = null;
    }

    public void enqueue(int data) {
        callNode newCall = new callNode(data);
        if (this.front == null) {//queue is empty
            this.front = this.end = newCall;
        }
        else {
            this.end.setNext(newCall);
            this.end = newCall;
        }
    }

    public callNode dequeue() {
        if (this.front != null) {
            callNode temp = this.front;
            this.front = this.front.getNext();
            return temp;
        }
        System.out.println("queue is empty, nothing to dequeue");
        return null;
    }
    public callNode peek() {
        return this.front;
    }
//    public void sortQueueAscendingOrder() {
//        Collections.sort(q);
//    }
}
