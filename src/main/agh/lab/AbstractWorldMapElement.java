package agh.lab;

public abstract class AbstractWorldMapElement implements IMapElement {
    protected Vector2d location;

    public Vector2d getPosition() {
        return this.location;
    }
}
