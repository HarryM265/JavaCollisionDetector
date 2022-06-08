import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class Panel extends JPanel implements Runnable {

    final int screenWidth = 400;
    final int screenHeight = 400;

    final int halfScreenWidth = (int)(screenWidth/2);
    final int halfScreenHeight = (int)(screenHeight/2);

    //CentreRect dimensions
    final int centreRectWidth = screenWidth/2;
    final int centreRectHeight  = screenHeight/3;
    final int halfCentreRectWidth = (int)(centreRectWidth/2);
    final int halfCentreRectHeight = (int)(centreRectHeight/2);
    //CentreRect location
    int centreRectX = halfScreenWidth - halfCentreRectWidth;
    int centreRectY = halfScreenHeight - halfCentreRectHeight;

    //BouncingRect starting location
    final int bouncingRectStartLoc = 5;

    //REMOVE LATER
    //BouncingRect dimensions
    final int bouncingRectWidth  = screenWidth/20;
    final int bouncingRectHeight = screenHeight/20;
    //BouncingRect location
    int bouncingRectX = bouncingRectStartLoc;
    int bouncingRectY = bouncingRectStartLoc;
    
    //Speed of the bouncing rectangle
    int bouncingRectSpeedX = screenWidth/100;
    int bouncingRectSpeedY = screenHeight/160;
    //END REMOVE LATER

    BounceBox bouncer = new BounceBox(screenWidth/20, screenHeight/20, 5, 5, 1, 1);

    //FPS
    int fps = 60;

    BasicComboBoxUI bComBoUI = new BasicComboBoxUI();
    KeyHandler keyH = bComBoUI.new KeyHandler();
    Thread gameThread;

    //Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));// Set the window size
        this.setBackground(Color.GRAY);// Make the background black
        this.setDoubleBuffered(true);// All drawing from this component will be performed on an off-screen painting buffer
        this.addKeyListener(keyH);
        this.setFocusable(true);// this game panel can be 'focused' to recieve key input

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // START delta style game loop
        double drawInterval = 1000000000 / fps;
        double delta = 0;
        // 1,000,000,000 nano seconds = 1 second
        long lastTime = System.nanoTime();// Returns the time since the current program was ran in nano seconds
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {

            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;// Find how much time has passed and divide it by the draw interval
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //When delta reaches the draw interval, draw and update, then reset the delta
            if (delta >= 1) {
            // 1 UPDATE: update information such as character position
            update();
            // 2 DRAW: draw the screen with the updated information
            repaint(); // Calls paintComponent
            delta--;
            drawCount++;
            }
            // END delta style game loop

            //Print the fps count to the console
            if (timer >= 1000000000) {
                //System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
        
    }

    public void update() {
        // x-axis movement controller
        //If I keep moving in my current X direction, will I collide with the centre rectangle?
        if (
            //If where the right of bouncing rect is going to be, is greater than the centre rect's left side AND
            bouncer.FutureBoxRight() > centreRectX &&
            //If where the left side of where bouncing rect is going to be, is less than centre rect's right side AND
            bouncer.FutureBoxLeft() < centreRectX + centreRectWidth && 
            //If the bottom of the bouncing rect is greater than the centre rect's top AND
            bouncer.BoxBottom() > centreRectY && 
            //If the top of the bouncing rect is less than centre rect's bottom THEN
            bouncer.BoxTop() < centreRectY + centreRectHeight
            ) {
            //Invert the x-axis movement
            bouncer.invX();
            System.out.println("Inverted left-right (BOX)");
        //Bounce off the left and right of the screen
        } else if (bouncer.BoxLeft() < 0 || bouncer.BoxRight() > screenWidth) {
            bouncer.invX();
            System.out.println("Inverted left-right (WALL)");
        }

        // y-axis movement controller
        //If I keep moving in my current Y direction, will I collide with the centre rectangle?
        /*
        if (
            //If bouncing rect;s right side is past the centre rect;s left side AND
            bouncer.BoxRight() > centreRectX && 
            //If bouncing rect's left side is less than centre rect's right side AND
            bouncer.BoxLeft() < centreRectX + centreRectWidth && 
            //If where the bottom of where bouncing rect is going to be, is greater than the centre rect's top AND
            bouncer.FutureBoxBottom() > centreRectY && 
            //If the top of where the bouncing rect is going to be, is less than centre rect's bottom side THEN
            bouncer.FutureBoxTop() < centreRectY + centreRectHeight
            ) {
            //Invert the y-axis movement
            bouncer.invY();
            System.out.println("Inverted up-down (BOX)");
        //Bounce off the top and bottom edges of the screen
        } else if (bouncer.BoxTop() < 0 || bouncer.BoxBottom() > screenHeight) {
            bouncer.invY();
            System.out.println("Inverted up-down (WALL)");
        } */

        if (
            //If where the right of bouncing rect is going to be, is greater than the centre rect's left side AND
            bouncer.FutureBoxBottom() > centreRectY &&
            //If where the left side of where bouncing rect is going to be, is less than centre rect's right side AND
            bouncer.FutureBoxTop() < centreRectY + centreRectHeight && 
            //If the bottom of the bouncing rect is greater than the centre rect's top AND
            bouncer.BoxRight() > centreRectX && 
            //If the top of the bouncing rect is less than centre rect's bottom THEN
            bouncer.BoxLeft() < centreRectX + centreRectWidth
            ) {
            //Invert the x-axis movement
            bouncer.invY();
            System.out.println("Inverted up-down (BOX)");
            System.out.println("Width: " + screenWidth + ", Height: " + screenHeight + "\n" + bouncer.BoxInfo());
        //Bounce off the left and right of the screen
        } else if (bouncer.BoxTop() <= 0 || bouncer.FutureBoxBottom() >= screenHeight) {
            bouncer.invY();
            System.out.println("Inverted up-down (WALL)");
            System.out.println("Width: " + screenWidth + ", Height: " + screenHeight + "\n" + bouncer.BoxInfo());
        }

        //Movement Code
        bouncer.moveX();
        bouncer.moveY();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
  
        Graphics2D centreRect = (Graphics2D)g;
        centreRect.setColor(Color.WHITE);

        centreRect.fillRect(centreRectX, centreRectY, centreRectWidth, centreRectHeight);

        Graphics2D bouncingRect = (Graphics2D)g;
        bouncingRect.setColor(Color.BLACK);

        bouncingRect.fillRect(bouncer.BoxLeft(), bouncer.BoxBottom(), bouncer.getWidth(), bouncer.getHeight());

        // disposal of this graphics context and stop using system resources
        centreRect.dispose();
        bouncingRect.dispose();
    }
}
