package lab; // proszę tu zrobić jakiś pakiet z przynajmniej jedną kropką

public class Animal {
    private MapDirection direction;
    private Vector2d location;

    /*
    jak zaimplementować mechanizm, który wyklucza pojawienie się dwóch zwierząt w tym samym miejscu
    Należy stworzyć tablicę z modyfikatorem: private static boolean taken[5][5], tablica ta będzie wspólna dla wszystkich obiektów
    true - pole zajęte
    false - pole wolne
    Jeśli chcę przesunąć zwierzę na nową pozycję sprawdzam czy jest wolna i jeśli tak to przemieszczam je a następnie
    zmieniam wartości odpowiednich pól tablicy taken[][]
    */

    public Animal() {
        this.direction = MapDirection.NORTH;
        this.location = new Vector2d(2,2);
    }

    public String toString() {
        return this.location.toString() + " " + this.direction.toString();
    }
    //return this.location; sprawia chyba że z racji zwracania String zachodzi ukryte wywołanie toSting(), a z racji że
    //nadpisałem tą metodę to wywołuje właśnie ją, tak samo z this.direction

    public void move(MoveDirection direction) {
        Vector2d rightUpperCorner = new Vector2d(4,4);  //  warto wydzielić do stałych
        Vector2d leftLowerCorner = new Vector2d(0,0);
        if(direction == MoveDirection.RIGHT) {
            this.direction = this.direction.next();
        }
        else if(direction == MoveDirection.LEFT) {
            this.direction = this.direction.previous();
        }
        else if(direction == MoveDirection.FORWARD) {
            Vector2d possibleNewLocation = this.location.add(this.direction.toUnitVector());
            if(possibleNewLocation.precedes(rightUpperCorner) && possibleNewLocation.follows(leftLowerCorner)) {
                this.location = possibleNewLocation;
            }
        }
        else {
            Vector2d possibleNewLocation = this.location.subtract(this.direction.toUnitVector());
            if(possibleNewLocation.precedes(rightUpperCorner) && possibleNewLocation.follows(leftLowerCorner)) {
                this.location = possibleNewLocation;
            }
        }
    }

}
