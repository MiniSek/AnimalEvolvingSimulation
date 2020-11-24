package agh.lab;

public class RectangularMap extends AbstractWorldMap{
    public RectangularMap(int width, int height) {
        super();
        this.leftLowerCorner = new Vector2d(0,0);
        this.rightUpperCorner = new Vector2d(width-1, height-1);
    }

    //implementacja metody abstrakcyjnej
    public void updateDrawFrame() {
        this.leftLowerCornerToDraw = this.leftLowerCorner;
        this.rightUpperCornerToDraw = this.rightUpperCorner;
    }
}
