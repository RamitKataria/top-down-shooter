package model;

/*
 * Represents a wall (blocks some of the game objects)
 */
public class Wall extends GameObject {
    int width;
    int height;

    // EFFECTS: constructs a wall
    public Wall(int posX, int posY, int height, int width) {
        super(posX, posY);
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
