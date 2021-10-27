package ex0.algo;

import java.util.Comparator;
import java.util.PriorityQueue;
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
    private static double timeToDest(Elevator ele, int dest) { // Time calculation for pickup on the way
        int src = ele.getPos();
        int floorToPass = Math.abs(src - dest);
        double speed = ele.getSpeed();
        return ele.getTimeForClose() + ele.getTimeForOpen() + (floorToPass / speed) + ele.getStopTime() + ele.getStartTime();
    }

    private static double timeToDestReverse(int ele, int dest){ // Time calculation based on destination if no pick up on the way is available
        return ele-dest;
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
        for (int i = 0; i < this.getBuilding().numberOfElevetors(); i++) {
            if (this.getBuilding().getElevetor(i).getState() == 0 && this.getBuilding().getElevetor(i).getPos() == c.getSrc()) {
                flag = true;
                ind = i;
            }
            if (this.getBuilding().getElevetor(i).getState() == 1 && this.getBuilding().getElevetor(i).getPos() < c.getSrc() && c.getType() == 1) {
                flag = true;
                if (timeToDest(_building.getElevetor(i), c.getSrc()) < min) {
                    min = timeToDest(_building.getElevetor(i), c.getSrc());
                    ind = i;
                }
            }
            if (this.getBuilding().getElevetor(i).getState() == -1 && this.getBuilding().getElevetor(i).getPos() > c.getSrc() && c.getType() == -1) {
                flag = true;
                if (timeToDest(_building.getElevetor(i), c.getSrc()) < min) {
                    min = timeToDest(_building.getElevetor(i), c.getSrc());
                    ind = i;
                }
            }
            if(!flag) {
                if (timeToDestReverse(calls[i].upQ.getLast(calls[i].upQ), c.getSrc()) < min) {
                    min = timeToDestReverse(calls[i].upQ.getLast(calls[i].upQ), c.getSrc());
                    ind = i;
                }
                if (timeToDestReverse(calls[i].downQ.getLast(calls[i].downQ), c.getSrc()) < min) {
                    min = timeToDestReverse(calls[i].downQ.getLast(calls[i].downQ), c.getSrc());
                    ind = i;
                }
            }
            if (c.getType() == 1) {
                calls[ind].upQ.enqueue(c.getSrc());
                calls[ind].upQ.enqueue(c.getDest());
                calls[ind].upQ.sortQueueA(calls[ind].upQ);
            } else {
                calls[ind].downQ.enqueue(c.getSrc());
                calls[ind].downQ.enqueue(c.getDest());
                calls[ind].downQ.sortQueueD(calls[ind].downQ);
            }
        }
        if(flag){
            flag = false;
        }
        return ind;
    }


    @Override
    public void cmdElevator(int elev) {
        if(calls[elev].pointer.size() == 0){
            calls[elev].Switch();
        }
        //UP
        if (calls[elev].pointer.getFirst() != null) {
            this._building.getElevetor(elev).goTo(calls[elev].pointer.getFirst().getData());
            if (calls[elev].pointer.getFirst().getData() > this._building.getElevetor(elev).getPos() &&
                    calls[elev].pointer.getLast().getData() > this._building.getElevetor(elev).getPos() &&
                    calls[elev].pointer.getFirst().getData() > calls[elev].pointer.getLast().getData())
                if (this._building.getElevetor(elev).getPos() == calls[elev].pointer.getFirst().getData()) {
                    calls[elev].pointer.dequeue();
                }
        }
    }
}
