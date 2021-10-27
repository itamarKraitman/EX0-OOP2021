package ex0.algo;

import ex0.CallForElevator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;

/**
 * This class implements a data structure with FIFO Logic in order to make order in the calls for elevators
 */
public class callsQueue {
    private callNode front, end;
    private int size;

    public callsQueue() {
        this.front = this.end = null;
        this.size = 0;
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
        size++;
    }

    public callNode dequeue() {
        if (this.front != null) {
            callNode temp = this.front;
            this.front = this.front.getNext();
            size--;
            return temp;
        } else {
            System.out.println("queue is empty, nothing to dequeue");
            return null;
        }
    }

    public callNode peek() {
        return this.front;
    }
    public int getSize() {
        return this.size;
    }
    public callNode getFirst() {
        return this.front;
    }
    public boolean isEmpty() {
        return size == 0;
    }
}
