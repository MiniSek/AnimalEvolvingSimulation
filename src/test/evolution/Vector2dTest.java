package evolution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Vector2dTest {
    @Test public void equalsTest() {
        Vector2d point = new Vector2d(1,3);
        Vector2d other = new Vector2d(1,4);
        String napis="Co mówi Meksykanin gdy ukradną mu samochód? Carlos";
        Assertions.assertFalse(point.equals(napis));
        Assertions.assertTrue(point.equals(point));
        Assertions.assertFalse(point.equals(other));
    }

    @Test public void toStringTest() {
        Vector2d point = new Vector2d(0,6);
        Assertions.assertEquals("(0,6)", point.toString());
    }

    @Test public void precedesTest() {
        Vector2d point1 = new Vector2d(2,-1);
        Vector2d point2 = new Vector2d(6,0);
        Assertions.assertTrue(point1.precedes(point2));
        Vector2d point3 = new Vector2d(1,3);
        Assertions.assertFalse(point1.precedes(point3));
    }

    @Test public void followsTest() {
        Vector2d point1 = new Vector2d(4,2);
        Vector2d point2 = new Vector2d(2,1);
        Assertions.assertTrue(point1.follows(point2));
        Vector2d point3 = new Vector2d(5,-1);
        Assertions.assertFalse(point1.follows(point3));
    }

    @Test public void upperRightTest() {
        Vector2d point1 = new Vector2d(0,3);
        Vector2d point2 = new Vector2d(-2,5);
        Vector2d point3 = point1.upperRight(point2);
        Assertions.assertEquals(0, point3.x);
        Assertions.assertEquals(5, point3.y);
    }

    @Test public void lowerLeftTest() {
        Vector2d point1 = new Vector2d(-4,-1);
        Vector2d point2 = new Vector2d(-5,-8);
        Vector2d point3 = point1.lowerLeft(point2);
        Assertions.assertEquals(-5, point3.x);
        Assertions.assertEquals(-8, point3.y);
    }

    @Test public void addTest() {
        Vector2d point1 = new Vector2d(-4,0);
        Vector2d point2 = new Vector2d(5,-2);
        Vector2d point3 = point1.add(point2);
        Assertions.assertEquals(1, point3.x);
        Assertions.assertEquals(-2, point3.y);
    }

    @Test public void subtractTest() {
        Vector2d point1 = new Vector2d(6,2);
        Vector2d point2 = new Vector2d(3,7);
        Vector2d point3 = point1.subtract(point2);
        Assertions.assertEquals(3, point3.x);
        Assertions.assertEquals(-5, point3.y);
    }

    @Test public void oppositeTest() {
        Vector2d point = new Vector2d(2,-3);
        point = point.opposite();
        Assertions.assertEquals(-2,point.x);
        Assertions.assertEquals(3,point.y);
    }
}
