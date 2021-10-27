package ex0.algo;

import java.util.Comparator;
import java.util.PriorityQueue;
import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

public class YIOnlineElevAlgo implements ElevatorAlgo{
    private static final int UP=1, DOWN=0;
    private Building _building;
    private PriorityQueue<CallForElevator> calls;
    private Comparator<Double> timeComparator;

    /**returns the time that will take the elevator to reach the destination
     * @param ele the elevator to calculate for
     * @param dest the destination floor
     * @return time for ele to reach dest*/
    private static double timeToDest(Elevator ele, int dest) {
        int src = ele.getPos();
        int floorToPass = Math.abs(src - dest);
        double speed = ele.getSpeed();
        return ele.getTimeForClose() + ele.getTimeForOpen() + (floorToPass * speed) + ele.getStopTime() + ele.getStartTime();
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
        return 0;
    }


    @Override
    public void cmdElevator(int elev) {
        Elevator curr = this.getBuilding().getElevetor(elev);
//        if (calls[elev] != null && calls[elev].size() != 0) {
//            curr.goTo(calls[elev].get(0));
//            curr.goTo(calls[elev].get(1));
//            if (curr.getState() == 0) {
//                curr.goTo(1);
//            }
//        }
        for (int i = 0; i < this._building.numberOfElevetors(); i++) {
            while (calls[i] != null && calls[i].size() != 0) {
                CallForElevator current = calls[i].get(0);//call
                if (current.getType() == UP) {//handling all UP calls
                    destQueue.enqueue(current.getDest());
//                    srcQueue.enqueue(cu);
                }
            }
            destQueue = sortQueueAscendingOrder(destQueue);
//            callNode call = destQueue.getFirst();
            while (!destQueue.isEmpty()) {
                curr.goTo(destQueue.getFirst().getData());
                destQueue.dequeue();
            }
        }
    }

    public static callsQueue sortQueueAscendingOrder(callsQueue queue) {
        callsQueue newQueue = new callsQueue();
        if (!queue.isEmpty()) {
            int[] temp = new int[queue.getSize()];
            callNode curr = queue.getFirst();
            int i = 0;
            while (curr.getNext() != null) {
                temp[i] = curr.getData();
                curr = curr.getNext();
                i++;
            }
            Arrays.sort(temp);
            for (int j = 0; j < temp.length; j++) {
                newQueue.enqueue(temp[j]);
            }
        }
        return newQueue;

    }
}
