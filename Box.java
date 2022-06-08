public class Box {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speedX;
    private int speedY;

    public Box(int pX, int pY, int pWidth, int pHeight, int pSpeedX, int pSpeedY) {
        this.x = pX;
        this.y = pY;
        this.width = pWidth;
        this.height = pHeight;
        this.speedX = pSpeedX;
        this.speedY = pSpeedY;
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
    public int spedX() {
        return this.speedX;
    }
    public int spedY() {
        return this.speedY;
    }

    public void invSpedX() {
        this.speedX *= -1;
    }
    public void invSpedY() {
        this.speedY *= -1;
    }

    public void moveX() {
        this.x += this.speedX;
    }
    public void moveY() {
        this.y += this.speedY;
    }
}
