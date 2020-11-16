package agh.lab;

public class Grass extends AbstractWorldMapElement{
    public Grass(Vector2d location) {
        this.location = location;
    }

    public String toString() {
        return "*";
    }
}
