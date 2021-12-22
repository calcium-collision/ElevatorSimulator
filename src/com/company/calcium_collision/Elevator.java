package com.company.calcium_collision;

import java.util.*;

public class Elevator {

    private int currentFloor;
    private int currentLoadKg = 0;
    private int currentCountOfPeoples = 0;
    private final int highestFloor;
    private final int maxWorkloadKg;
    private final int peopleCapacity;
    private ArrayList<ElevatorUser> elevatorSeats;
    private ArrayList<ElevatorUser> callList = new ArrayList<ElevatorUser>();
    private int indexSeats = 0;
    private MoveDirection dir;

    public Elevator(int highestFloor, int maxWorkloadKg, int peopleCapacity){
        this.highestFloor = highestFloor;
        this.maxWorkloadKg = maxWorkloadKg;
        this.peopleCapacity = peopleCapacity;
        this.currentFloor = (int) (Math.random() * (highestFloor - 1)) + 1; // Random non-zero floor
        elevatorSeats = new ArrayList<ElevatorUser>(peopleCapacity);
        System.out.printf("Elevator on floor number %d\n", currentFloor);
        System.out.printf(
                "Specs - [max load in kg:%d, people capacity:%d, highest floor:%d]\n",
                maxWorkloadKg,
                peopleCapacity,
                highestFloor);
    }

    public void startLift(){

        if (callList.size() == 0 && elevatorSeats.size() == 0){return;}

        // To calculate move direction of elevator
        int wishingUp = 0;
        int wishingDown = 0;

        // Lists with floors when we must stop to pick up user during we move up/down
        ArrayList<Integer> pickUpFloorUp = new ArrayList<Integer>();
        ArrayList<Integer> pickUpFloorDown = new ArrayList<Integer>();

        // Lists with floors when we must stop to let out user during we move up/down
        ArrayList<Integer> letOutFloorUp = new ArrayList<Integer>();
        ArrayList<Integer> letOutFloorDown = new ArrayList<Integer>();

        Iterator<ElevatorUser> iteration = callList.iterator();
        while (iteration.hasNext()){
            ElevatorUser user = iteration.next();
            if (user.getDirection() == MoveDirection.UP){
                wishingUp++;
                pickUpFloorUp.add(user.getCurrentFloor());
                letOutFloorUp.add(user.getRequiredFloor());
            } else {
                wishingDown++;
                pickUpFloorDown.add(user.getCurrentFloor());
                letOutFloorDown.add(user.getRequiredFloor());
            }
        }
        if (wishingUp > wishingDown){dir = MoveDirection.UP;}
        else {dir = MoveDirection.DOWN;}


        if (dir == MoveDirection.UP){
            pickUpFloorUp.addAll(letOutFloorUp);
            removeDuplicates(pickUpFloorUp);
            Collections.sort(pickUpFloorUp);
            Iterator<Integer> iter = pickUpFloorUp.iterator();
            while (iter.hasNext()){
                System.out.printf("\nElevator move from floor number %d", currentFloor);
                recursiveElevatorGoToFloor(iter.next());
                System.out.printf(" and came to floor number %d\n", currentFloor);
                pickUpOnCurrentFloor();
                freeUpElevator();
            }
        } else {
            pickUpFloorDown.addAll(letOutFloorDown);
            removeDuplicates(pickUpFloorDown);
            Collections.sort(pickUpFloorDown);
            Collections.reverse(pickUpFloorDown);
            Iterator<Integer> iter = pickUpFloorDown.iterator();
            while (iter.hasNext()){
                System.out.print(String.format("\nElevator move from floor number %d", currentFloor));
                recursiveElevatorGoToFloor(iter.next());
                System.out.println(String.format(" and came to floor number %d", currentFloor));
                pickUpOnCurrentFloor();
                freeUpElevator();
            }
        }
        startLift();
    }

    private void pickUpOnCurrentFloor(){
        ArrayList<ElevatorUser> toRemoveFromCallList = new ArrayList<ElevatorUser>();
        Iterator<ElevatorUser> iteration = callList.iterator();
        while (iteration.hasNext()){
            if (currentCountOfPeoples == peopleCapacity){return;}
            ElevatorUser user = iteration.next();
            if (user.getDirection() == dir && user.getCurrentFloor() == currentFloor){
                if (user.getWeight() + currentLoadKg < maxWorkloadKg){
                    System.out.printf(
                            "User get in the elevator " +
                                    "[weight:%d, " +
                                    "from floor:%d, " +
                                    "floor goal:%d]%n", user.getWeight(), user.getCurrentFloor(), user.getRequiredFloor());
                    elevatorSeats.add(user);
                    currentCountOfPeoples++;
                    toRemoveFromCallList.add(user);

                }

            }

        }
        callList.removeAll(toRemoveFromCallList);


//        if (user.getWeight() + currentLoadKg > maxWorkloadKg){ return false; }
//        if (currentCountOfPeoples + 1 > peopleCapacity){ return false; }
//        currentCountOfPeoples++;
//        currentLoadKg += user.getWeight();
//        callList.remove(user);
//        elevatorSeats.add(user);
//
//        return true;
    }

    public void callTheElevator(ElevatorUser caller){
        callList.add(caller);
    }

    private void freeUpElevator(){
        ArrayList<ElevatorUser> toRemoveFromSeats = new ArrayList<ElevatorUser>();
        Iterator<ElevatorUser> iteration = elevatorSeats.iterator();
        while (iteration.hasNext()){
            ElevatorUser user = iteration.next();
            if (currentFloor == user.getRequiredFloor()){
                System.out.printf(
                        "User get out of elevator " +
                        "[weight:%d, " +
                        "from floor:%d, " +
                                "floor goal:%d]%n", user.getWeight(), user.getCurrentFloor(), user.getRequiredFloor());
                currentLoadKg -= user.getWeight();
                currentCountOfPeoples--;
                toRemoveFromSeats.add(user);

            }
        }
        elevatorSeats.removeAll(toRemoveFromSeats);
    }

    private void recursiveElevatorGoToFloor(int goalFloor){
        if (currentFloor < goalFloor){
            currentFloor++;
            recursiveElevatorGoToFloor(goalFloor);
        } else if (currentFloor > goalFloor){
            currentFloor--;
            recursiveElevatorGoToFloor(goalFloor);
        } else {
            return;
        }
    }

    public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> list)
    {

        // Create a new LinkedHashSet
        Set<Integer> set = new HashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }

    public int getHighestFloor() {
        return highestFloor;
    }
}
