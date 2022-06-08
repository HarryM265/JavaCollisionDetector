public class Box {
    private int x;
    private int y;
    private int width;
    private int height;

    public Box(int pX, int pY, int pWidth, int pHeight) {
        this.x = pX;
        this.y = pY;
        this.width = pWidth;
        this.height = pHeight;
    }

    public int x() {
        return this.x;
    }
    public int y() {
        return this.y;
    }
    public int width() {
        return this.width;
    }
    public int height() {
        return this.height;
    }
}
