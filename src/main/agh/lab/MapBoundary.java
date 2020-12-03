package agh.lab;

import java.util.ArrayList;

public class MapBoundary implements IPositionChangeObserver {
    private class ObjectOnMap {
        public final Vector2d position;
        public final boolean isAnimal;

        public ObjectOnMap(Vector2d position, boolean isAnimal) {
            this.position = position;
            this.isAnimal = isAnimal;
        }
    }

    private final ArrayList<ObjectOnMap> xSortedObjects = new ArrayList<>();
    private final ArrayList<ObjectOnMap> ySortedObjects = new ArrayList<>();
    private int numberOfObjects;

    public MapBoundary() {
        this.numberOfObjects = 0;
    }

    //to add object of type Object to lists from outside of this class
    public void addObjectToMapBoundary(Object object) throws IllegalArgumentException{
        if(object instanceof Animal)
            this.addToSortedLists(((Animal)object).getPosition(), true);
        else if(object instanceof Grass)
            this.addToSortedLists(((Grass)object).getPosition(), true); // true?
        else
            throw new IllegalArgumentException("this type of object cannot be added to MapBoundary class");
    }

    //to add object which is created from position and type (isAnimal) to lists
    private void addToSortedLists(Vector2d position, boolean isAnimal) {
        ObjectOnMap object = new ObjectOnMap(position, isAnimal);
        if(this.numberOfObjects==0) {
            this.xSortedObjects.add(0, object);
            this.ySortedObjects.add(0, object);
        }
        else {
            this.xSortedObjects.add(this.binarySearch(this.xSortedObjects, 1,0, this.numberOfObjects - 1, object),object);
            this.ySortedObjects.add(this.binarySearch(this.ySortedObjects, 2,0, this.numberOfObjects - 1, object),object);
        }
        this.numberOfObjects += 1;
    }

    //I cant check whether the newPosition is actually the current position of this Animal on the map
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
            int index = this.binarySearch(this.xSortedObjects, 1, 0, this.numberOfObjects, new ObjectOnMap(oldPosition, true));
            this.xSortedObjects.remove(index);
            index = this.binarySearch(this.ySortedObjects, 2, 0, this.numberOfObjects, new ObjectOnMap(oldPosition, true));
            this.ySortedObjects.remove(index);

            this.numberOfObjects -= 1;
            this.addToSortedLists(newPosition, true);
    }

    //finding index in list sorted by numberOfCoordinate to add object
    //or position of the object in this list if it is in the list already
    private int binarySearch(ArrayList<ObjectOnMap> sortedObjects, int numberOfCoordinate, int l, int r, ObjectOnMap object) {  // gdyby Pan skorzystał z SortedSet'u to ta metoda by nie była potrzebna
        if(l<=r) {
            int mid = l+(r-l)/2;
            if(sortedObjects.get(mid).position.equals(object.position)) {
                if(object.isAnimal)
                    return mid;
                return mid+1;
            }
            if(sortedObjects.get(mid).position.getCoordinate(numberOfCoordinate) < object.position.getCoordinate(numberOfCoordinate) ||
                    (sortedObjects.get(mid).position.getCoordinate(numberOfCoordinate) == object.position.getCoordinate(numberOfCoordinate)
                            && sortedObjects.get(mid).position.getCoordinate(numberOfCoordinate%2+1) < object.position.getCoordinate(numberOfCoordinate%2+1)))
                return binarySearch(sortedObjects, numberOfCoordinate,mid+1, r, object);
            return binarySearch(sortedObjects, numberOfCoordinate, l, mid - 1, object);
        }
        return l;
    }

    public Vector2d getLeftLowerCornerToDraw() {
        if(this.numberOfObjects > 0)
            return this.xSortedObjects.get(0).position.lowerLeft(this.ySortedObjects.get(0).position);
        else
            return new Vector2d(0,0);
    }

    public Vector2d getRightUpperCornerToDraw() {
        if(this.numberOfObjects > 0)
            return this.xSortedObjects.get(this.numberOfObjects-1).position.upperRight(this.ySortedObjects.get(this.numberOfObjects-1).position);
        else
            return new Vector2d(0,0);
    }
}
