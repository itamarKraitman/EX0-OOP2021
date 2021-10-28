package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

public class YIOnlineElevAlgo implements ElevatorAlgo {
    private static final int UP = 1, DOWN = -1;
    private Building _building;
    private EleQueue[] calls;
    private int numElev;

    public YIOnlineElevAlgo(Building b) {
        _building = b;
        int numElev = b.numberOfElevetors();
        calls = new EleQueue[numElev];
        for (int i = 0; i < numElev; i++) {
            calls[i] = new EleQueue();
        }
    }

    /**
     * returns the time that will take the elevator to reach the destination
     *
     * @param ele  the elevator to calculate for
     * @param dest the destination floor
     * @return time for ele to reach dest
     */
    private double timeToDest(int start, int end, int pos, int id) { // Time calculation for pickup on the way
        Elevator ele = this.getBuilding().getElevetor(id);
        int floorToPass = Math.abs((end - start) + (start - pos));
        double speed = ele.getSpeed();
        return ele.getTimeForClose() + ele.getTimeForOpen() + (floorToPass / speed) + ele.getStopTime() + ele.getStartTime();
    }

    private double timeToDest(Elevator ele, int dest) {
        int pos = ele.getPos();
        int floorToPass = Math.abs(dest - pos);
        double speed = ele.getSpeed();
        return ele.getTimeForClose() + ele.getTimeForOpen() + (floorToPass / speed) + ele.getStopTime() + ele.getStartTime();
    }


    @Override
    public Building getBuilding() {
        return _building;
    }

    @Override
    public String algoName() {
        return "Elevator algo, Yuval Bubnovsky & Itamar Kraitman";
    }

    @Override
    public int allocateAnElevator(CallForElevator c) {
        double min = Double.MAX_VALUE;
        int ind = -1;
        boolean flag = false;
      /*  for(int i = 0;i<this.getBuilding().numberOfElevetors();i++){
            Elevator curr = this.getBuilding().getElevetor(i);
            EleQueue eq = calls[i];
            double ph; // place holder
            if(curr.getState()!=-2){
                if(eq.upQ.isEmpty() && eq.downQ.isEmpty()){
                    ph = timeToDest(c.getSrc(),c.getDest(),curr.getPos(),i);
                    min = Math.min(ph,min);
                    if(min == ph) {
                        ind = i;
                    }
                }
                if(eq.heading) {
                    if(curr.getPos()<c.getSrc()){
                        ph = timeToDest(c.getSrc(),c.getDest(),curr.getPos(),i);
                        min = Math.min(ph,min);
                        if(min == ph) {
                            ind = i;
                        }
                        if(c.getSrc() < c.getDest()){
                            ph = timeToDest(c.getSrc(),c.getDest(),curr.getPos(),i);
                            min = Math.min(ph,min);
                            if(min == ph) {
                                ind = i;
                            }
                        } else {
                            ph = timeToDest(c.getSrc(),c.getDest(),curr.getPos(),i);
                            min = Math.min(ph,min);
                            if(min == ph) {
                                ind = i;
                        }
                    }
                }
            }
        }*/
        for (int i = 0; i < this.getBuilding().numberOfElevetors(); i++) {
//            if (this.getBuilding().getElevetor(i).getState() == 0) {
//                flag = true;
//                ind = i;
//            }
            if (this.getBuilding().getElevetor(i).getState() == 0 || (this.getBuilding().getElevetor(i).getState() == 1 && this.getBuilding().getElevetor(i).getPos() < c.getSrc() && c.getSrc() - c.getDest() < 1)) {
                flag = true;
                if (timeToDest(_building.getElevetor(i), c.getSrc()) < min) {
                    min = timeToDest(_building.getElevetor(i), c.getSrc());
                    ind = i;
                }
            }
            if (this.getBuilding().getElevetor(i).getState() == 0 || (this.getBuilding().getElevetor(i).getState() == -1 && this.getBuilding().getElevetor(i).getPos() > c.getSrc() && c.getSrc() - c.getDest() > 1)) {
                flag = true;
                if (timeToDest(_building.getElevetor(i), c.getSrc()) < min) {
                    min = timeToDest(_building.getElevetor(i), c.getSrc());
                    ind = i;
                }
            }
        }
        if (flag) {
            allocate(c, ind);
            return ind;
        }
        for (int i = 0; i < this.getBuilding().numberOfElevetors(); i++) {
            if (timeToDest(_building.getElevetor(i), c.getSrc()) < min) {
                min = timeToDest(_building.getElevetor(i), c.getSrc());
                ind = i;
            }


      /*  for(int i = 0;i<this.getBuilding().numberOfElevetors();i++){
            calls[i].upQ.sortQueueA(calls[i].upQ);
            calls[i].downQ.sortQueueD(calls[i].downQ);
        }*/

        }
        allocate(c, ind);
        return ind;
    }

    private void allocate(CallForElevator c, int ind) {
        if (calls[ind].upQ.isEmpty() && calls[ind].downQ.isEmpty()) {
            if (this.getBuilding().getElevetor(ind).getPos() < c.getSrc()) {
                calls[ind].upQ.enqueue(c.getSrc());
            }
            if (this.getBuilding().getElevetor(ind).getPos() > c.getSrc()) {
                calls[ind].downQ.enqueue(c.getSrc());
            }
        }
        if (c.getSrc() - c.getDest() < 1) {
            calls[ind].upQ.enqueue(c.getDest());
            calls[ind].upQ.enqueue(c.getSrc());
            calls[ind].upQ.sortQueueA(calls[ind].upQ);
        } else {
            calls[ind].downQ.enqueue(c.getDest());
            calls[ind].downQ.enqueue(c.getSrc());
            calls[ind].downQ.sortQueueD(calls[ind].downQ);
        }
        //  System.out.println(calls[ind].upQ.toString());
    }

    @Override
    public void cmdElevator(int elev) {
//        if(calls[elev].pointer == calls[elev].upQ){
//            calls[elev].pointer.sortQueueA(calls[elev].pointer);
//        } else {
//            calls[elev].pointer.sortQueueD(calls[elev].pointer);
//        }
        Elevator curr = this._building.getElevetor(elev);
        if (calls[elev].downQ.isEmpty() && calls[elev].upQ.isEmpty()) {
            return;
        }
        if (calls[elev].pointer.isEmpty()) {
            calls[elev].Switch();
            return;
        } else if (calls[elev].pointer.getFirst().getData() == curr.getPos()) {
            calls[elev].pointer.dequeue();
        }
        if (!calls[elev].pointer.isEmpty()) {
            if (curr.getState() == 0) {
                curr.goTo(calls[elev].pointer.getFirst().getData());
            } else if (curr.getState() == 1 || curr.getState() == -1) {
                curr.stop(calls[elev].pointer.getFirst().getData());
            }
        }

//        if (calls[elev].pointer.getFirst() != null) {
//            if (curr.getState() == 0) {
//                curr.goTo(calls[elev].pointer.peek());
//                calls[elev].pointer.dequeue();
//            } else if (curr.getState() == 1) {
//                curr.goTo(calls[elev].pointer.peek());
//                calls[elev].pointer.dequeue();
//                curr.goTo(calls[elev].pointer.getLast().getData());
//            } else if (curr.getState() == -1) {
//                curr.goTo(calls[elev].pointer.peek());
//                calls[elev].pointer.dequeue();
//                curr.goTo(calls[elev].pointer.getLast().getData());
//            }
//        }
//        if (calls[elev].pointer.isEmpty()) {
//            calls[elev].Switch();
    }
}


