package evolution;

import java.util.Random;

public class MapCoordinates {
    public int width;
    public int height;
    public int jungleWidth;
    public int jungleHeight;

    public final Vector2d leftLowerCorner;
    public final Vector2d rightUpperCorner;
    public final Vector2d leftLowerCornerOfJungle;
    public final Vector2d rightUpperCornerOfJungle;

    public MapCoordinates(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;

        this.leftLowerCorner = new Vector2d(0,0);
        this.rightUpperCorner = new Vector2d(width-1, height-1);

        //check if it is good
        this.leftLowerCornerOfJungle = new Vector2d((int)Math.floor((width-1)*(1-jungleRatio)/2),
                (int)Math.floor((height-1)*(1-jungleRatio)/2));
        this.rightUpperCornerOfJungle = new Vector2d((int)Math.floor((width-1)*(1-jungleRatio)/2+(width-1)*jungleRatio),
                (int)Math.floor((height-1)*(1-jungleRatio)/2+(height-1)*jungleRatio));
        this.jungleWidth = this.rightUpperCornerOfJungle.x - this.leftLowerCornerOfJungle.x + 1;
        this.jungleHeight =  this.rightUpperCornerOfJungle.y - this.leftLowerCornerOfJungle.y + 1;
    }

    //done, don't change
    public Vector2d setCorrespondingPositionIfCurrentIsOutsideOfMap(Vector2d position) {
        Vector2d positionPrecedingRightUpperCorner = new Vector2d(position.x % (this.rightUpperCorner.x + 1),
                position.y % (this.rightUpperCorner.y + 1));

        return new Vector2d((positionPrecedingRightUpperCorner.x + this.rightUpperCorner.x + 1) %
                (this.rightUpperCorner.x + 1), (positionPrecedingRightUpperCorner.y + this.rightUpperCorner.y + 1) %
                (this.rightUpperCorner.y + 1));
    }

    public boolean isPositionInJungle(Vector2d position) {
        return position.precedes(this.rightUpperCornerOfJungle) && position.follows(this.leftLowerCornerOfJungle);
    }

    public boolean isPositionInSavanna(Vector2d position) {
        return position.precedes(this.rightUpperCorner) && position.follows(this.leftLowerCorner) && !this.isPositionInJungle(position);
    }

    public Vector2d drawPositionInJungle() {
        Random generator = new Random();
        return new Vector2d(generator.nextInt(this.jungleWidth) + this.leftLowerCornerOfJungle.x,
                generator.nextInt(this.jungleHeight) + this.leftLowerCornerOfJungle.y);
    }

    //what have i done
    public Vector2d drawPositionInSavanna() {
        Random generator = new Random();
        int numberOfPossiblePositionsInSavanna = this.width * this.height - this.jungleWidth * this.jungleHeight;
        int x = generator.nextInt(numberOfPossiblePositionsInSavanna) + 1;
        if(x <= this.height * (this.leftLowerCornerOfJungle.x)) {
            return new Vector2d(generator.nextInt(this.leftLowerCornerOfJungle.x), generator.nextInt(this.height));
        }
        else if(x <= this.height * (this.leftLowerCornerOfJungle.x) + (this.leftLowerCornerOfJungle.y * this.jungleWidth)) {
            return new Vector2d(generator.nextInt(this.jungleWidth) + this.leftLowerCornerOfJungle.x,
                    generator.nextInt(this.leftLowerCornerOfJungle.y));
        }
        else if(x <= this.height * (this.leftLowerCornerOfJungle.x) + (this.leftLowerCornerOfJungle.y * this.jungleWidth) +
                (this.height - this.rightUpperCornerOfJungle.y) * (this.jungleWidth)) {
            return new Vector2d(generator.nextInt(this.jungleWidth) + this.leftLowerCornerOfJungle.x,
                    generator.nextInt(this.height - this.rightUpperCornerOfJungle.y - 1) + this.rightUpperCornerOfJungle.y + 1);
        }
        else {
            return new Vector2d(generator.nextInt(this.width - this.rightUpperCornerOfJungle.x - 1) +
                    this.rightUpperCornerOfJungle.x + 1, generator.nextInt(this.height));
        }
    }
}
