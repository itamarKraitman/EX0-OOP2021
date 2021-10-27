package ex0.algo;

import ex0.CallForElevator;

public class callNode {
    private int data;
    private callNode next;

    public callNode(int data) {
        this.data = data;
        this.next = null;
    }
    public callNode(int data, callNode next) {
        this.data = data;
        this.next = next;
    }
    public int getData() {
        return data;
    }
    public callNode getNext() {
        return next;
    }
    public void setData(int data) {
        this.data = data;
    }
    public void setNext(callNode next) {
        this.next = next;
    }
}
