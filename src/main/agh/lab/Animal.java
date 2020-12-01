package agh.lab;

import java.util.ArrayList;

public class Animal extends AbstractWorldMapElement {
    private MapDirection direction;
    private final IWorldMap map;
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map) {
        this(map, new Vector2d(2,2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.direction = MapDirection.NORTH;
        this.location = initialPosition;
        this.map = map;
    }

    public String toString() {
        switch (this.direction) {
            case NORTH:
                return "^";
            case EAST:
                return ">";
            case SOUTH:
                return "v";
            case WEST:
                return "<";
            default:
                return null;
        }
    }

    public void move(MoveDirection direction) {
        if(direction == MoveDirection.RIGHT) {
            this.direction = this.direction.next();
        }
        else if(direction == MoveDirection.LEFT) {
            this.direction = this.direction.previous();
        }
        else if(direction == MoveDirection.FORWARD || direction == MoveDirection.BACKWARD) {
            Vector2d possibleNewLocation;
            if(direction == MoveDirection.FORWARD) {
                possibleNewLocation = this.location.add(this.direction.toUnitVector());
            }
            else {
                possibleNewLocation = this.location.subtract(this.direction.toUnitVector());
            }

            if(this.map.canMoveTo(possibleNewLocation)) {
                Vector2d oldLocation = this.location;
                this.location = possibleNewLocation;
                this.positionChanged(oldLocation, this.location);
            }
        }
    }

    public void addObserver(IPositionChangeObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        for(IPositionChangeObserver observerFromList : this.observers)
            if(observerFromList.equals(observer))
                this.observers.remove(observer);
    }

    private void positionChanged(Vector2d oldLocation, Vector2d newLocation) {
        for(IPositionChangeObserver observerFromList : this.observers)
            observerFromList.positionChanged(oldLocation, newLocation);
    }
}
