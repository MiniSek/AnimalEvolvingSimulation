package agh.lab;

public class RectangularMap extends AbstractWorldMap{
    private Vector2d leftLowerCorner;
    private Vector2d rightUpperCorner;
    public RectangularMap(int width, int height) {
        super();
        this.leftLowerCorner = new Vector2d(0,0);
        this.rightUpperCorner = new Vector2d(width-1, height-1);
        this.leftLowerCornerToDraw = this.leftLowerCorner;
        this.rightUpperCornerToDraw = this.rightUpperCorner;
    }

    //abstract method implementation
    public void updateDrawFrame() {
        //update is unnecessary, map to draw is set in constructor
    }

    @Override public boolean canMoveTo(Vector2d position) {
        return super.canMoveTo(position) && position.precedes(this.rightUpperCorner) && position.follows(this.leftLowerCorner);
    }
}
