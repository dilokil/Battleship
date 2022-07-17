package OnePerson;

import java.util.HashSet;

public class MyHashSet extends HashSet<Coordinate> {
    public boolean containsCoordinate(Coordinate findCoord) {
        for (Coordinate coord : this) {
            if (coord.getX() == findCoord.getX() && coord.getY() == findCoord.getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean removeCoordinate(Coordinate delCoord) {
        for (Coordinate coord : this) {
            if (coord.getX() == delCoord.getX() && coord.getY() == delCoord.getY()) {
                this.remove(coord);
                return true;
            }
        }
        return false;
    }
}
