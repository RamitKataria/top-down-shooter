package model;

import model.gameobjects.GameObject;

public class CollisionPair {
    private final GameObject first;
    private final GameObject second;

    public CollisionPair(GameObject first, GameObject second) {
        this.first = first;
        this.second = second;
    }

    public void executeCollision() {
        if (!(first.isDead() || second.isDead())) {
            first.hit(second);
        }
    }

    public boolean contains(GameObject o) {
        return first.equals(o) || second.equals(o);
    }

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
