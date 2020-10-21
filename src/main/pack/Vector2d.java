package pack;   // pakiet o nazwie 'pakiet' ?

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    boolean precedes(Vector2d other) {
        if(this.x<=other.x && this.y<=other.y)
            return true;
        return false;
    }

    boolean follows(Vector2d other) {
        if(this.x>=other.x && this.y>=other.y)
            return true;
        return false;
    }

    Vector2d upperRight(Vector2d other) {   // Math.max
        int X, Y;   // zmienne nazywamy camelCasem
        if(this.x>=other.x)
            X=this.x;
        else
            X=other.x;

        if(this.y>=other.y)
            Y=this.y;
        else
            Y=other.y;

        Vector2d point = new Vector2d(X,Y);
        return point;
    }

    Vector2d lowerLeft(Vector2d other) {
        int X, Y;
        if(this.x<=other.x)
            X=this.x;
        else
            X=other.x;

        if(this.y<=other.y)
            Y=this.y;
        else
            Y=other.y;

        Vector2d point = new Vector2d(X,Y);
        return point;
    }

    Vector2d add(Vector2d other) {
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    Vector2d subtract(Vector2d other) {
        return new Vector2d(this.x - other.x, this.y - other.y);
    }

    public boolean equals(Object other){
        if(this == other)
            return true;
        if(!(other instanceof Vector2d))
            return false;
        Vector2d that = (Vector2d) other;
        if(this.x == that.x && this.y == that.y)
            return true;
        return false;
    }

    Vector2d oppposite() {
        return new Vector2d(-this.x,-this.y);
    }
}
