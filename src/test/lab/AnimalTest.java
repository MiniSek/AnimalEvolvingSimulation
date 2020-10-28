package lab;

import org.junit.jupiter.api.Assertions;    // można zastosować import static
import org.junit.jupiter.api.Test;

public class AnimalTest {
    @Test public void toStringTest() {
        Animal animal = new Animal();
        Assertions.assertEquals("(2,2) Północ", animal.toString());
    }

    @Test public void moveTest() {
        Animal animal = new Animal();
        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals("(2,2) Wschód", animal.toString());
        animal.move(MoveDirection.RIGHT);
        Assertions.assertEquals("(2,2) Południe", animal.toString());
        animal.move(MoveDirection.LEFT);
        Assertions.assertEquals("(2,2) Wschód", animal.toString());
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals("(3,2) Wschód", animal.toString());
        animal.move(MoveDirection.LEFT);
        Assertions.assertEquals("(3,2) Północ", animal.toString());
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals("(3,3) Północ", animal.toString());
        animal.move(MoveDirection.LEFT);
        animal.move(MoveDirection.BACKWARD);
        Assertions.assertEquals("(4,3) Zachód", animal.toString());
        animal.move(MoveDirection.RIGHT);
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals("(4,4) Północ", animal.toString());
        animal.move(MoveDirection.FORWARD);
        Assertions.assertEquals("(4,4) Północ", animal.toString());

        Animal animal2 = new Animal();
        animal2.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.FORWARD);
        Assertions.assertEquals("(4,2) Wschód", animal2.toString());
        animal2.move(MoveDirection.RIGHT);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.FORWARD);
        animal2.move(MoveDirection.FORWARD);
        Assertions.assertEquals("(4,0) Południe", animal2.toString());

        Animal animal3 = new Animal();
        animal3.move(MoveDirection.RIGHT);
        animal3.move(MoveDirection.RIGHT);
        animal3.move(MoveDirection.FORWARD);
        animal3.move(MoveDirection.FORWARD);
        animal3.move(MoveDirection.FORWARD);
        Assertions.assertEquals("(2,0) Południe", animal3.toString());
        animal3.move(MoveDirection.LEFT);
        animal3.move(MoveDirection.BACKWARD);
        animal3.move(MoveDirection.BACKWARD);
        animal3.move(MoveDirection.BACKWARD);
        Assertions.assertEquals("(0,0) Wschód", animal3.toString());

    }
}
