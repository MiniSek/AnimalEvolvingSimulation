package evolution;

public enum MapDirection {
    NORTH(new Vector2d(0,1)),
    NORTHEAST(new Vector2d(1,1)),
    EAST(new Vector2d(1,0)),
    SOUTHEAST(new Vector2d(1,-1)),
    SOUTH(new Vector2d(0,-1)),
    SOUTHWEST(new Vector2d(-1,-1)),
    WEST(new Vector2d(-1,0)),
    NORTHWEST(new Vector2d(-1,1));

    private final Vector2d unitVector;

    MapDirection(Vector2d unitVector) {
        this.unitVector = unitVector;
    }

    public String toString() {
        switch(this) {
            case NORTH: return "Północ";
            case NORTHWEST: return "Północnywschód";
            case WEST: return "Zachód";
            case SOUTHWEST: return "Południowywschód";
            case SOUTH: return "Południe";
            case SOUTHEAST: return "Południowyzachód";
            case EAST: return "Wschód";
            case NORTHEAST: return "Północnyzachód";
            default: return null;
        }
    }

    public MapDirection next() {
        if(this == NORTH)
            return NORTHEAST;
        else if(this == NORTHEAST)
            return EAST;
        else if(this == EAST)
            return SOUTHEAST;
        else if(this == SOUTHEAST)
            return SOUTH;
        else if(this == SOUTH)
            return SOUTHWEST;
        else if(this == SOUTHWEST)
            return WEST;
        else if(this == WEST)
            return NORTHWEST;
        else if(this == NORTHWEST)
            return NORTH;
        else
            return null;
    }

    public MapDirection previous() {
        if(this == NORTH)
            return NORTHWEST;
        else if(this == NORTHEAST)
            return NORTH;
        else if(this == EAST)
            return NORTHEAST;
        else if(this == SOUTHEAST)
            return EAST;
        else if(this == SOUTH)
            return SOUTHEAST;
        else if(this == SOUTHWEST)
            return SOUTH;
        else if(this == WEST)
            return SOUTHWEST;
        else if(this == NORTHWEST)
            return WEST;
        else
            return null;
    }

    public Vector2d toUnitVector() {
        return unitVector;
    }
}
