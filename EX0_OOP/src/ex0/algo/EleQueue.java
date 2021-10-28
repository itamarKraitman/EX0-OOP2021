package ex0.algo;

/*
    EleQueue data structure is a wrapper class which allows us to have 2 Queues for each elevator : an up queue and a down queue
    this class allows us to store all the data we need and keeps track of each elevator's calls
 */

public class EleQueue {
    callsQueue upQ;
    callsQueue downQ;
    callsQueue pointer;

    public EleQueue(){
        this.upQ = new callsQueue();
        this.downQ = new callsQueue();
        this.pointer = upQ;
    }

    /*
    this pointer queue & switch allows us to access the relevant queue when needed for allocation or for elevator commands
     */
    public void Switch(){
        if(this.pointer == upQ){
            this.pointer = downQ;
        } else {
            this.pointer = upQ;
        }
    }
}
