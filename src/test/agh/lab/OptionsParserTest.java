package agh.lab;

import agh.lab.MoveDirection;
import agh.lab.OptionsParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionsParserTest {
    @Test public void parseTest() {
        String[] rawMoves = new String[] {"f", "backward", "right", "Jaka broń nigdy się nie psuje? Działa", "l"};
        OptionsParser converter = new OptionsParser();
        MoveDirection[] convertedMoves = converter.parse(rawMoves);
        Assertions.assertArrayEquals(new MoveDirection[] {MoveDirection.FORWARD, MoveDirection.BACKWARD,
                MoveDirection.RIGHT, MoveDirection.LEFT}, convertedMoves);
        Assertions.assertEquals(4, convertedMoves.length);
    }
}
