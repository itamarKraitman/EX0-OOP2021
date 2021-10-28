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
        if (this.size == 0) {//queue is empty
            this.front = this.end = newCall;
        } else {
            this.end.setNext(newCall);
            this.end = newCall;
        }
        size++;
    }

    public void enqueue(callNode newCall) {
        if (this.size == 0) {//queue is empty
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
            if (this.front.getNext() == this.end) this.front = this.end;
            else this.front = this.front.getNext();
            size--;
            return temp;
        } else {
            System.out.println("queue is empty, nothing to dequeue");
            return null;
        }
    }

    public int peek() {
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
//        ArrayList<Integer> temp = new ArrayList<>();
//        while(!q.isEmpty())
//        {
//            temp.add(q.peek());
//            q.dequeue();
//        }
//        Collections.sort(temp);
//        dest = (temp.size() == 0 ? null : temp.get(temp.size() - 1));
//        for (Integer integer : temp) {
//            q.enqueue(integer);
//        }
        callsQueue newQ = new callsQueue();
        callNode[] temp = new callNode[q.getSize()];
        callNode current = q.getFirst();
        int i = 0;
        while (current != null) {
            temp[i] = current;
            current = current.getNext();
            i++;
        }
        sortArrOfCalls(temp);
        for (int j = 0; j < temp.length; j++) {
            newQ.enqueue(temp[j]);
        }
    }

    public void sortQueueD(callsQueue q) { // Descending sort
//        ArrayList<Integer> temp = new ArrayList<>();
//        while(!q.isEmpty())
//        {
//            temp.add(q.peek());
//            q.dequeue();
//        }
//        Collections.sort(temp, Collections.reverseOrder());
//        dest = (temp.size() == 0 ? null : temp.get(temp.size() - 1));
//        size--;
//        for (Integer integer : temp) {
//            q.enqueue(integer);
//        }
        callsQueue newQ = new callsQueue();
        callNode[] temp = new callNode[q.getSize()];
        callNode current = q.getFirst();
        int i = 0;
        while (current != null) {
            temp[i] = current;
            current = current.getNext();
        }
        sortArrOfCalls(temp);
        sortArrOfCallsD(temp);
        for (int j = 0; j < temp.length; j++) {
            newQ.enqueue(temp[j]);
        }
    }

    private void sortArrOfCallsD(callNode[] arr) {
        for (int i = 0; i <= arr.length / 2; i++) {
            for (int j = arr.length - 1; j > i; j--) {
                callNode temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }

    public callNode getLast() {
        return this.end;
    }

    public int getLast(callsQueue q) {
        return this.dest;
    }

    public int size() {
        return this.size;
    }

    public int getDest() {
        return this.dest;
    }

    private void sortArrOfCalls(callNode[] arr) {
        if (arr.length == 1) return;
        if (arr.length == 2) {
            if (arr[0].getData() > arr[1].getData()) {
                callNode temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
            return;
        }
        else MergeSort(arr, 0, arr.length - 1);
    }

    private static void MergeSort(callNode[] arr, int start, int end) {
        if (start < end) {
            int middle = (start + end) / 2;
            MergeSort(arr, start, middle);
            MergeSort(arr, middle + 1, end);
            Merge(arr, start, middle, end);
        }
    }

    private static void Merge(callNode[] arr, int start, int middle, int end) {
        callNode[] temp = new callNode[end - start + 1];
        int i = start;
        int j = middle + 1;
        int k = 0; // The Running Pointer
        while (i <= middle && j < end) {
            if (arr[i].getData() < arr[j].getData()) temp[k++] = arr[i++];
            else temp[k++] = arr[j++];
        }
        while (i <= middle)
            temp[k++] = arr[i++];

        while (j <= end)
            temp[k++] = arr[j++];

        // Copy The array
        i = start;
        k = 0;
        while (k < temp.length && i <= end) {
            arr[i++] = temp[k++];
        }
    }
}
//    public void setDest() {this.}


