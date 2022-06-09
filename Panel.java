import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {

    final int screenWidth = 640;
    final int screenHeight = 360;

    //BouncingRect dimensions
    final int bouncingRectWidth  = screenWidth/14;
    final int bouncingRectHeight = screenHeight/9;

    int cornerCounter = 0;
    boolean startPointer = true;

    static int numBoxes = 2;
    public Box[] createBouncers(int pNumBoxes) {
        Box[] boxes = new Box[pNumBoxes];
        for (int i = 0; i < boxes.length; i++) {
            int randX = randoNum(0, screenWidth/3);
            int randY = randoNum(0, screenHeight/3);
            int randSpedX = randoNum(1, screenWidth/90);
            int randSpedY = randoNum(1, screenHeight/160);
            int randColourI = randoNum(0, numColours -1);
            boxes[i] = new Box(randX, randY, bouncingRectWidth, bouncingRectHeight, randSpedX, randSpedY, colours[randColourI]);
        }
        return boxes;
    }

    static int numFrameRows = 2;
    static int numFrameCols = 4;
    Frame[] frames;
    public Frame[] createFrames() {
        int numFrames = numFrameRows * numFrameCols;
        Frame[] frameArray = new Frame[numFrames];
        int xOffset = screenWidth/64;
        int yOffset = screenHeight/36;
        int newHeight = (screenHeight/numFrameRows) - (yOffset*2);
        int newWidth = (screenWidth/numFrameCols) - (xOffset*2);
        int currX, currY = 0;
        int frameCount = 0;
        for (int i = 1; i <= numFrameRows; i++) {
            currX = 0;
            currY += yOffset;
            for (int j = 1; j <= numFrameCols; j++) {
                currX += xOffset;
                Box[] bouncers = createBouncers(numBoxes);
                frameArray[frameCount]= new Frame(currX, currY, newWidth, newHeight, 0, 0, Color.GRAY, bouncers);
                currX += newWidth + xOffset;
                frameCount++;
            }
            currY += newHeight + yOffset;
        }
        return frameArray;
    }

    static int numColours = 10;
    Color[] colours;
    public static Color[] createColours() {
        Color[] Colors = new Color[numColours];
        Colors[0] = Color.BLACK;
        Colors[1] = Color.GREEN;
        Colors[2] = Color.ORANGE;
        Colors[3] = Color.RED;
        Colors[4] = Color.MAGENTA;
        Colors[5] = Color.PINK;
        Colors[6] = Color.YELLOW;
        Colors[7] = Color.WHITE;
        Colors[8] = Color.BLUE;
        Colors[9] = Color.CYAN;
        return Colors;
    }
    int colourIndex = 0;
    
    //FPS
    int fps = 60;

    Thread gameThread;

    //Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    //Make background
    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));// Set the window size
        this.setBackground(Color.BLACK);// Make the background black
        this.setDoubleBuffered(true);// All drawing from this component will be performed on an off-screen painting buffer
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

            //ONLY GEN ONCE
            if (startPointer) {
                colours = createColours();
                frames = createFrames();
                /*
                for (int i = 0; i < frames.length; i++) {
                    System.out.println("x: " + frames[i].x() + ", y: " + frames[i].y() + "\nHeight: " + frames[i].height() + ", Width: " + frames[i].width());
                }
                */
                startPointer = false;
            }

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
                System.out.println("# of corners: " + cornerCounter);
                drawCount = 0;
                timer = 0;
            }
        }
        
    }

    public void update() {
        
        for (int i = 0; i < frames.length; i++) {
            Frame currFrame = frames[i];
            Box[] currBouncers = currFrame.bouncers();
            for (int j = 0; j < currBouncers.length; j++) {
                //Corner counter
                if (
                    currBouncers[j].x()<= currFrame.x() && currBouncers[j].y()<=currFrame.x() || //Top-left Corner
                    currBouncers[j].x() + currBouncers[j].width() >= currFrame.width() && currBouncers[j].y() <=currFrame.x() || //Top-right corner
                    currBouncers[j].x()<=currFrame.x() && currBouncers[j].y() + currBouncers[j].height() >= currFrame.height() || //Bottom-left corner
                    currBouncers[j].x() + currBouncers[j].width() >= currFrame.width() && currBouncers[j].y() + currBouncers[j].height() >= currFrame.height() //Bottom-right corner
                    ) {
                    cornerCounter++;
                }

                // x-axis movement controller
                //Bounce off the left and right of the screen
                if (currBouncers[j].x() < currFrame.x() || currBouncers[j].x() + currBouncers[j].width() > currFrame.width()) {
                    currBouncers[j].invSpedX();
                    colourIndex += 1;
                    currBouncers[j].changeColour(colours[colourIndex%numColours]);
                    //System.out.println("Box #" + i + " inverted left-right (WALL)");
                    //System.out.println("Current colour index: " + colourIndex%numColours);
                }
                // y-axis movement controller       
                //Bounce off the top and bottom edges of the screen
                if (currBouncers[j].y() < currFrame.x() || currBouncers[j].y() + currBouncers[j].height() > currFrame.height()) {
                    currBouncers[j].invSpedY();
                    colourIndex += 1;
                    currBouncers[j].changeColour(colours[colourIndex%numColours]);
                    //System.out.println("Box #" + i + " inverted up-down (WALL)");
                    //System.out.println("Current colour index: " + colourIndex%numColours);
                }

                //Movement Code
                currBouncers[j].moveX();
                currBouncers[j].moveY();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        /*
        Graphics2D box = (Graphics2D)g;
        for (int i = 0; i < boxes.length; i++) {
            x.setColor(boxes[i].colour());

            box.fillRect(boxes[i].xbo(), boxes[i].y(), boxes[i].width(), boxes[i].height());
            //box.dispose();
        }

        for (int i = 0; i < boxes.length; i++) {
            box.dispose();
        }

        // disposal of this graphics context and stop using system resources
        */
        Graphics2D frame = (Graphics2D)g;
        for (int i = 0; i < frames.length; i++) {
            frame.setColor(frames[i].colour());

            frame.fillRect(frames[i].x(), frames[i].y(), frames[i].width(), frames[i].height());
            Frame currentFrame = frames[i];
            Box[] currentBouncers = currentFrame.bouncers();
            Graphics2D box = null;
            for (int j = 0; j < currentBouncers.length; j++) {
                box = (Graphics2D)g;
                box.setColor(currentBouncers[i].colour());

                box.fillRect(currentBouncers[i].x(), currentBouncers[i].y(), currentBouncers[i].width(), currentBouncers[i].height());
            }
            for (int j = 0; j < currentBouncers.length; j++) {
                box.dispose();
            }
        }
        for (int i = 0; i < frames.length; i++) {
            frame.dispose();
        }
    }

    public int randoNum (int pMin, int pMax) {
        int rand = (int) Math.floor(Math.random()*(pMax-pMin+1)+pMin);
        return rand;
    }
}
