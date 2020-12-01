package agh.lab;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {
    @Test public void parseTest() {
        String[] rawMoves = new String[]{"f", "backward", "right", "Jaka broń nigdy się nie psuje? Działa", "l"};
        OptionsParser converter = new OptionsParser();
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> { converter.parse(rawMoves);});
        Assertions.assertEquals("'Jaka broń nigdy się nie psuje? Działa' is not legal move specification", e.getMessage());

        String[] rawMoves2 = new String[]{"f", "backward", "right", "l"};
        MoveDirection[] convertedMoves = converter.parse(rawMoves2);
        Assertions.assertArrayEquals(new MoveDirection[] {MoveDirection.FORWARD, MoveDirection.BACKWARD,
                    MoveDirection.RIGHT, MoveDirection.LEFT}, convertedMoves);
        Assertions.assertEquals(4, convertedMoves.length);
    }
}
