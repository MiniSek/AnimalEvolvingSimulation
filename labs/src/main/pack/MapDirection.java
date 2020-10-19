package pack;

public enum MapDirection {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    public String toString() {
        switch(this) {
            case NORTH: return "Północ";
            case SOUTH: return "Południe";
            case WEST: return "Zachód";
            case EAST: return "Wschód";
        }
        return "";
    }

    MapDirection next() {
        if(this == NORTH)
            return EAST;
        else if(this == EAST)
            return SOUTH;
        else if(this == SOUTH)
            return WEST;
        else
            return NORTH;
    }

    MapDirection previous() {
        if(this == NORTH)
            return WEST;
        else if(this == EAST)
            return NORTH;
        else if(this == SOUTH)
            return EAST;
        else
            return SOUTH;
    }

    Vector2d toUnitVector() {
        if(this == NORTH)
            return new Vector2d(0, 1);
        else if(this == WEST)
            return new Vector2d(1, 0);
        else if(this == SOUTH)
            return new Vector2d(0,-1);
        else
            return new Vector2d(-1,0);
    }
}
