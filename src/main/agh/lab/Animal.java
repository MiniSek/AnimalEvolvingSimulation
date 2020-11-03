package agh.lab;

public class Animal {
    private MapDirection direction;
    private Vector2d location;
    private IWorldMap map;

    /*
    jak zaimplementować mechanizm, który wyklucza pojawienie się dwóch zwierząt w tym samym miejscu
    Należy stworzyć tablicę z modyfikatorem: private static boolean taken[5][5], tablica ta będzie wspólna dla wszystkich obiektów
    true - pole zajęte
    false - pole wolne
    Jeśli chcę przesunąć zwierzę na nową pozycję sprawdzam czy jest wolna i jeśli tak to przemieszczam je a następnie
    zmieniam wartości odpowiednich pól tablicy taken[][]
    lub stworzyc klase nadrzedna, ktora te informacje będzie przetrzymywać tak jak jest w laboratorium 4
    */

    public Animal(IWorldMap map) {
        this.direction = MapDirection.NORTH;
        this.location = new Vector2d(2,2);
        this.map = map;
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
        else if(direction == MoveDirection.FORWARD) {
            Vector2d possibleNewLocation = this.location.add(this.direction.toUnitVector());
            if(this.map.canMoveTo(possibleNewLocation)) {
                this.location = possibleNewLocation;
            }
        }
        else if(direction == MoveDirection.BACKWARD) {
            Vector2d possibleNewLocation = this.location.subtract(this.direction.toUnitVector());
            if(this.map.canMoveTo(possibleNewLocation)) {
                this.location = possibleNewLocation;
            }
        }
    }

    public Vector2d getPosition() {
        return this.location;
    }
}
