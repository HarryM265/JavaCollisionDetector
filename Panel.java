import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class Panel extends JPanel implements Runnable {

    final int screenWidth = 640;
    final int screenHeight = 360;

    final int halfScreenWidth = (int)(screenWidth/2);
    final int halfScreenHeight = (int)(screenHeight/2);

    //BouncingRect starting location
    final int bouncingRectStartLoc = 5;
    //BouncingRect dimensions
    final int bouncingRectWidth  = screenWidth/14;
    final int bouncingRectHeight = screenHeight/9;
    //BouncingRect location
    int bouncingRectX = bouncingRectStartLoc;
    int bouncingRectY = bouncingRectStartLoc;
    
    //Speed of the bouncing rectangle
    int bouncingRectSpeedX = screenWidth/180;
    int bouncingRectSpeedY = screenHeight/320;

    int numColours = 10;

    public static Color[] createColours() {
        Color[] Colors = new Color[10];
        Colors[0] = Color.BLUE;
        Colors[1] = Color.GREEN;
        Colors[2] = Color.ORANGE;
        Colors[3] = Color.RED;
        Colors[4] = Color.MAGENTA;
        Colors[5] = Color.PINK;
        Colors[6] = Color.YELLOW;
        Colors[7] = Color.WHITE;
        Colors[8] = Color.BLACK;
        Colors[9] = Color.CYAN;

        return Colors;
    }
    int colourIndex = 0;
    

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
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
        
    }

    public void update() {
        // x-axis movement controller
        //Bounce off the left and right of the screen
        if (bouncingRectX < 0 || bouncingRectX + bouncingRectWidth > screenWidth) {
            bouncingRectSpeedX *= -1;
            colourIndex += 1;
            System.out.println("Inverted left-right (WALL)");
            System.out.println("Current colour index: " + colourIndex%10);
        }

        // y-axis movement controller       
        //Bounce off the top and bottom edges of the screen
         if (bouncingRectY < 0 || bouncingRectY + bouncingRectHeight > screenHeight) {
            bouncingRectSpeedY *= -1;
            colourIndex += 1;
            System.out.println("Inverted up-down (WALL)");
            System.out.println("Current colour index: " + colourIndex%10);
        }

        //Movement Code
        bouncingRectX += bouncingRectSpeedX;
        bouncingRectY += bouncingRectSpeedY;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color[] colours = createColours();

        Color currentColor = colours[colourIndex % 10];

        Graphics2D bouncingRect = (Graphics2D)g;
        bouncingRect.setColor(currentColor);

        bouncingRect.fillRect(bouncingRectX, bouncingRectY, bouncingRectWidth, bouncingRectHeight);

        // disposal of this graphics context and stop using system resources
        bouncingRect.dispose();
    }
}
