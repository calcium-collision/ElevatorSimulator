package com.company.calcium_collision;

public class Main {

    public static void main(String[] args) {
        Elevator elevator = new Elevator(25,800,8);
        ElevatorUser user1 = new ElevatorUser(elevator);
        ElevatorUser user2 = new ElevatorUser(elevator);
        ElevatorUser user3 = new ElevatorUser(elevator);
        ElevatorUser user4 = new ElevatorUser(elevator);
        ElevatorUser user5 = new ElevatorUser(elevator);
        ElevatorUser user6 = new ElevatorUser(elevator);
        ElevatorUser user7 = new ElevatorUser(elevator);
        ElevatorUser user8 = new ElevatorUser(elevator);
        elevator.callTheElevator(user1);
        elevator.callTheElevator(user2);
        elevator.callTheElevator(user3);
        elevator.callTheElevator(user4);
        elevator.callTheElevator(user5);
        elevator.callTheElevator(user6);
        elevator.callTheElevator(user7);
        elevator.callTheElevator(user8);

        elevator.startLift();

    }
}
