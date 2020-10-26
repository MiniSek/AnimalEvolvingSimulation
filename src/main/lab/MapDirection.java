package lab;

public enum MapDirection {
    NORTH(new Vector2d(0,1)),
    EAST(new Vector2d(1,0)),
    SOUTH(new Vector2d(0,-1)),
    WEST(new Vector2d(-1,0));

    private Vector2d unitVector;

    private MapDirection(Vector2d unitVector) {
        this.unitVector = unitVector;
    }

    public String toString() {
        switch(this) {
            case NORTH: return "Północ";
            case SOUTH: return "Południe";
            case WEST: return "Zachód";
            case EAST: return "Wschód";
            default: return null;
        }
    }

    public MapDirection next() {
        if(this == NORTH)
            return EAST;
        else if(this == EAST)
            return SOUTH;
        else if(this == SOUTH)
            return WEST;
        else
            return NORTH;
    }

    public MapDirection previous() {
        if(this == NORTH)
            return WEST;
        else if(this == EAST)
            return NORTH;
        else if(this == SOUTH)
            return EAST;
        else
            return SOUTH;
    }

    public Vector2d toUnitVector() {
        /*if(this == NORTH)
            return new Vector2d(0, 1);
        else if(this == WEST)
            return new Vector2d(1, 0);
        else if(this == SOUTH)
            return new Vector2d(0,-1);
        else
            return new Vector2d(-1,0);*/
        return unitVector;
    }
}
