package lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {
    @Test public void parseTest() {
        String[] rawMoves = new String[5];
        OptionsParser converter = new OptionsParser();  // https://www.baeldung.com/java-initialize-array
        rawMoves[0]="f";
        rawMoves[1]="backward";
        rawMoves[2]="right";
        rawMoves[3]="Jaka broń nigdy się nie psuje? Działa";
        rawMoves[4]="l";
        MoveDirection[] convertedMoves = converter.parse(rawMoves);
        Assertions.assertEquals(MoveDirection.FORWARD, convertedMoves[0]);  // warto stosować assertArrayEquals
        Assertions.assertEquals(MoveDirection.BACKWARD, convertedMoves[1]);
        Assertions.assertEquals(MoveDirection.RIGHT, convertedMoves[2]);
        Assertions.assertEquals(MoveDirection.LEFT, convertedMoves[3]);
        Assertions.assertEquals(4, convertedMoves.length);
    }
}
