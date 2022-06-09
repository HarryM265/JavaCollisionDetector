import java.awt.Color;
public class Box {
    private int x;
    private int y;
    private int width;
    private int height;
    private int speedX;
    private int speedY;
    private Color colour;

    //Parameters Constructor
    public Box(int pX, int pY, int pWidth, int pHeight, int pSpeedX, int pSpeedY, Color pColour) {
        this.x = pX;
        this.y = pY;
        this.width = pWidth;
        this.height = pHeight;
        this.speedX = pSpeedX;
        this.speedY = pSpeedY;
        this.colour = pColour;
    }
    //Copy constructor
    public Box(Box pBox) {
        this.x = pBox.x();
        this.y = pBox.y();
        this.width = pBox.width();
        this.height = pBox.height();
        this.speedX = pBox.spedX();
        this.speedY = pBox.spedY();
        this.colour = pBox.colour();
    }

    //Getters
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
    public Color colour() {
        return this.colour;
    }

    //Setters
    public void invSpedX() {
        this.speedX *= -1;
    }
    public void invSpedY() {
        this.speedY *= -1;
    }

    public void setSpedX(int pSped) {
        this.speedX = pSped;
    }
    public void setSpedY(int pSped) {
        this.speedY = pSped;
    }

    public void moveX() {
        this.x += this.speedX;
    }
    public void moveY() {
        this.y += this.speedY;
    }

    public void changeColour(Color pColour) {
        this.colour = pColour;
    }

    public String boxInfo() {
        String boxInfo = "x: " + this.x +
                        "\ny: " + this.y +
                        "\nspeed x: " + this.speedX + 
                        "\nspeed y: " + this.speedY;
        return boxInfo;
    }
}
