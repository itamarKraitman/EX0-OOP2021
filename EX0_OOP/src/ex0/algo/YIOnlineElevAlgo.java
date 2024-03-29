package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

public class YIOnlineElevAlgo implements ElevatorAlgo {
    private static final int UP = 1, DOWN = -1;
    private Building _building;
    private EleQueue[] calls;
    private int numElev;

    // Constructor class + EleQueue initialization
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

    /*
     The "Dispatcher" that decides which call is allocated to which elevator, the algorithm uses a very simple logic : it first checks for any 'LEVEL' elevators or elevators that can
     "pick up" the call if it's on their intended route, if no such elevator can be found - loop through all the elevators again and allocate to the one which will be quickest to arrive
      it then sends the call to an "allocate" helper function
     */
    @Override
    public int allocateAnElevator(CallForElevator c) {
        double min = Double.MAX_VALUE;
        int ind = -1;
        boolean flag = false;
        for (int i = 0; i < this.getBuilding().numberOfElevetors(); i++) {
            if (this.getBuilding().getElevetor(i).getState() == 0 || (this.getBuilding().getElevetor(i).getState() == 1 && this.getBuilding().getElevetor(i).getPos() < c.getSrc() && c.getSrc() - c.getDest() < 1)) {
                flag = true;
                if (timeToDest(this._building.getElevetor(i), c.getSrc()) < min) {
                    min = timeToDest(_building.getElevetor(i), c.getSrc());
                    ind = i;
                }
            }
            if (this.getBuilding().getElevetor(i).getState() == 0 || (this.getBuilding().getElevetor(i).getState() == -1 && this.getBuilding().getElevetor(i).getPos() > c.getSrc() && c.getSrc() - c.getDest() > 1)) {
                flag = true;
                if (timeToDest(this._building.getElevetor(i), c.getSrc()) < min) {
                    min = timeToDest(this._building.getElevetor(i), c.getSrc());
                    ind = i;
                }
            }
        }
        if (flag) {
            allocate(c, ind);
            return ind;
        }
        for (int i = 0; i < this.getBuilding().numberOfElevetors(); i++) {
            if (timeToDest(this._building.getElevetor(i), c.getSrc()) < min) {
                min = timeToDest(this._building.getElevetor(i), c.getSrc());
                ind = i;
            }
        }
        allocate(c, ind);
        return ind;
    }

    /*
    allocation helper function, add the call source and destination to the correct queue (up or down) and then sorts the queues
     */
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
            calls[ind].upQ.enqueue(c.getSrc());
            calls[ind].upQ.enqueue(c.getDest());
            calls[ind].upQ.sortQueueA(calls[ind].upQ);
        } else {
            calls[ind].downQ.enqueue(c.getSrc());
            calls[ind].downQ.enqueue(c.getDest());
            calls[ind].downQ.sortQueueD(calls[ind].downQ);
        }
    }

    @Override
    /*
    this is where we tell each elevator where to go. We achieve this by first checking to see if the pointer is pointing at an empty queue (if so - we switch it to the other one)
    then we just run through each queue and execute it's orders
     */
    public void cmdElevator(int elev) {
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
            } else {
                if (curr.getState() == 1) {
                    if (this._building.getElevetor(elev).getPos() <= calls[elev].pointer.getFirst().getData()) {
                        curr.stop(calls[elev].pointer.getFirst().getData());
                    }
                } else {
                    if (this._building.getElevetor(elev).getPos() >= calls[elev].pointer.getFirst().getData()) {
                        curr.stop(calls[elev].pointer.getFirst().getData());
                    }
                }
            }
        }
    }
}



