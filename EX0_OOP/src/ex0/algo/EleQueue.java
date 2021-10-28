package ex0.algo;

public class EleQueue {
    callsQueue upQ;
    callsQueue downQ;
    callsQueue pointer;
    boolean heading;

    public EleQueue(){
        this.upQ = new callsQueue();
        this.downQ = new callsQueue();
        this.pointer = upQ;
        this.heading = true;
    }

    public void Switch(){
        if(this.pointer == upQ){
            this.pointer = downQ;
            this.heading = false;
        } else {
            this.pointer = upQ;
            this.heading = true;
        }
    }
}
