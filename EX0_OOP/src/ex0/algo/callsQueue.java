package ex0.algo;

import java.util.ArrayList;
import java.util.Collections;

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
        } else {
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
        if (this.front == null) {
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
        while (!q.isEmpty()) {
            temp.add(q.peek());
            q.dequeue();
        }
        Collections.sort(temp);
        for (Integer integer : temp) {
            q.enqueue(integer);
        }
    }

    public void sortQueueD(callsQueue q) { // Descending sort
        ArrayList<Integer> temp = new ArrayList<>();
        while (!q.isEmpty()) {
            temp.add(q.peek());
            q.dequeue();
        }
        temp.sort(Collections.reverseOrder());
        for (Integer integer : temp) {
            q.enqueue(integer);
        }
    }

    public String toString() {
        String build = "";
        callNode current = (isEmpty()) ? null : getFirst();
        while (current != null) {
            String currElem = String.valueOf(current.getData());
            build = (build.length() == 0) ? currElem : currElem + ", " + build;
            current = current.getNext();
        }
        return "[" + build + "]";
    }

    public callNode getLast() {
        return this.end;
    }

    public int size() {
        return this.size;
    }
}
