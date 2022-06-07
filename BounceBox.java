public class BounceBox {
    private int width;
    private int height;
    private int x;
    private int y;
    private int speedX;
    private int speedY;

    //Parameters contructor
    BounceBox(int pWidth, int pHeight, int pX, int pY, int pSpeedX, int pSpeedY) {
        this.width = pWidth;
        this.height = pHeight;
        this.x = pX;
        this.y = pY;
        this.speedX = pSpeedX;
        this.speedY = pSpeedY;
    }

    public void moveX() {
        this.x += this.speedX;
    }

    public void moveY() {
        this.y += this.speedY;
    }

    public void invX() {
        this.speedX *= -1;
    }

    public void invY() {
        this.speedY *= -1;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    //Current Box Location

    public int BoxLeft() {
        int boxLeft = this.x;
        return boxLeft;
    }

    public int BoxRight() {
        int boxRight = this.x + this.speedX;
        return boxRight;
    }
    
    public int BoxBottom() {
        int boxBottom = this.y + this.height;
        return boxBottom;
    }

    public int BoxTop() {
        int boxTop = this.y;
        return boxTop;
    }

    //Future box location

    public int FutureBoxRight() {
        int futureBoxRight = this.x + this.width + this.speedX;
        return futureBoxRight;
    }

    public int FutureBoxLeft() {
        int futureBoxLeft = this.x + this.speedX;
        return futureBoxLeft;
    }

    public int FutureBoxBottom() {
        int futureBoxBottom = this.y + this.height + this.speedY;
        return futureBoxBottom;
    }

    public int FutureBoxTop() {
        int futureBoxTop = this.y + this.speedY;
        return futureBoxTop;
    }
}
