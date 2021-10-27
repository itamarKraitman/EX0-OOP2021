package ex0.algo;

public class EleQueue {
    callsQueue upQ;
    callsQueue downQ;
    callsQueue pointer;

    public EleQueue(){
        this.upQ = new callsQueue();
        this.downQ = new callsQueue();
        this.pointer = upQ;
    }

    public void Switch(){
        if(this.pointer == upQ){
            this.pointer = downQ;
        } else {
            this.pointer = upQ;
        }
    }
}
