package ex0.algo;

import ex0.CallForElevator;

import java.util.*;

/**
 * This class implements a data structure with FIFO Logic in order to make order in the calls for elevators
 */
public class callsQueue {
    private callNode front, end;
    private int size;
    private int dest;

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
            return null;
        }
    }

    public int peek() {
        if(this.front == null){
            return 0;
        }
        return this.front.getData();
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
    public void sortQueueA(callsQueue q) { // Ascending sort
        ArrayList<Integer> temp = new ArrayList<>();
        while(!q.isEmpty())
        {
            temp.add(q.peek());
            q.dequeue();
        }
        Collections.sort(temp);
        dest = (temp.size() == 0 ? null : temp.get(temp.size() - 1));
        for (Integer integer : temp) {
            q.enqueue(integer);
        }
    }
    public void sortQueueD(callsQueue q) { // Descending sort
        ArrayList<Integer> temp = new ArrayList<>();
        while(!q.isEmpty())
        {
            temp.add(q.peek());
            q.dequeue();
        }
        Collections.sort(temp, Collections.reverseOrder());
        dest = (temp.size() == 0 ? null : temp.get(temp.size() - 1));
        for (Integer integer : temp) {
            q.enqueue(integer);
        }
    }

    public callNode getLast(){
        return this.end;
    }
    public int getLast(callsQueue q) {
        return this.dest;
    }
    public int size() {
        return  this.size;
    }
}
