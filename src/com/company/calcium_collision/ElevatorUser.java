package com.company.calcium_collision;

public class ElevatorUser {
    private final int weight = (int) (60 + (Math.random() * 60));
    private int currentFloor;
    private int requiredFloor;
    private MoveDirection direction;

    public ElevatorUser(Elevator elevator){
        setTwoRandomFloors(elevator.getHighestFloor());
        defineDirection();

    }

    private void setTwoRandomFloors(int num){

        // Generate a random non-zero floor
        this.currentFloor = (int) (Math.random() * (num - 1)) + 1;

        // Generate a random non-zero and not equal to the previous floor
        this.requiredFloor = (int) (Math.random() * (num - 1)) + 1;
        while (requiredFloor == currentFloor){
            this.requiredFloor = (int) (Math.random() * (num - 1)) + 1;
        }

    }

    private void defineDirection(){
        if (currentFloor > requiredFloor){
            direction = MoveDirection.DOWN;
        } else {
            direction = MoveDirection.UP;
        }
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getRequiredFloor() {
        return requiredFloor;
    }

    public int getWeight() {
        return weight;
    }

    public MoveDirection getDirection() {
        return direction;
    }
}
