package model;

// represents a pair of 2 objects that collided in the current frame
public class CollisionPair {
    private final GameObject first;
    private final GameObject second;

    // EFFECTS: create a new collision pair with the 2 objects
    public CollisionPair(GameObject first, GameObject second) {
        this.first = first;
        this.second = second;
    }

    // EFFECTS: if both objects are still alive, execute the appropriate effect depending on the object
    public void executeCollision() {
        if (!(first.isDead() || second.isDead())) {
            first.hit(second);
        }
    }

    // EFFECTS: return true if this contains the given object; otherwise false
    public boolean contains(GameObject o) {
        return first.equals(o) || second.equals(o);
    }

    // EFFECTS: return true if the given object is a collision pair with the same elements
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CollisionPair)) {
            return false;
        }
        CollisionPair that = (CollisionPair) o;
        return that.contains(first) && that.contains(second);
    }
}
