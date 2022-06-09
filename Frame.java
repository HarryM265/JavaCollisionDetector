import java.awt.Color;

public class Frame extends Box {
    private Box[] bouncers;

    public Frame(int pX, int pY, int pWidth, int pHeight, int pSpeedX, int pSpeedY, Color pColour, Box[] pBouncers) {
        super(pX, pY, pWidth, pHeight, pSpeedX, pSpeedY, pColour);

        this.bouncers = pBouncers;
    }

    public Box[] bouncers() {
        return this.bouncers;
    }
}
