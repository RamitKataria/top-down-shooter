package model;

public class CollisionPair {
    private final GameObject[] contents;
    private CollisionType collisionType;

    public CollisionPair(GameObject first, GameObject second, CollisionType type) {
        contents = new GameObject[]{first, second};
        this.collisionType = type;
    }

    public void executeCollision() {
        if (collisionType == CollisionType.PLAYER_BULLET) {
            contents[1].hit(contents[2]);
            contents[2].hit(contents[1]);
        } else if (collisionType == CollisionType.PLAYER_ENEMY) {
            contents[1].hit(contents[2]);
            contents[2].hit(contents[1]);
        } else if (collisionType == CollisionType.ENEMY_BULLET) {

        } else if (collisionType == CollisionType.WALL_BULLET) {

        }
    }
}
