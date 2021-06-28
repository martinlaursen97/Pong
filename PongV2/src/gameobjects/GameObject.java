package gameobjects;

public abstract class GameObject {
    public int posX;
    public int posY;

    public GameObject(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
