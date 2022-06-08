import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable {

    final int screenWidth = 640;
    final int screenHeight = 360;

    final int halfScreenWidth = (int)(screenWidth/2);
    final int halfScreenHeight = (int)(screenHeight/2);

    //BouncingRect dimensions
    final int bouncingRectWidth  = screenWidth/14;
    final int bouncingRectHeight = screenHeight/9;

    int startPointer = 0;

    Box[] boxes;
    public Box[] createBoxes() {
        Box[] boxes = new Box[2];
        for (int i = 0; i < boxes.length; i++) {
            int randX = randoNum(0, screenWidth/3);
            int randY = randoNum(0, screenHeight/3);
            int randSpedX = randoNum(1, screenWidth/90);
            int randSpedY = randoNum(1, screenHeight/160);
            boxes[i] = new Box(randX, randY, bouncingRectWidth, bouncingRectHeight, randSpedX, randSpedY, colours[colourIndex]);
        }
        return boxes;
    }

    static int numColours = 10;

    public static Color[] createColours() {
        Color[] Colors = new Color[numColours];
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
    Color[] colours;
    
    //FPS
    int fps = 60;

    Thread gameThread;

    //Set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public Panel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));// Set the window size
        this.setBackground(Color.GRAY);// Make the background black
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
            if (startPointer == 0) {
                colours = createColours();
                boxes = createBoxes();
                startPointer += 1;
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
                drawCount = 0;
                timer = 0;
            }
        }
        
    }

    public void update() {
        for (int i = 0; i < boxes.length; i++) {
            // x-axis movement controller
            //Bounce off the left and right of the screen
            if (boxes[i].x() < 0 || boxes[i].x() + boxes[i].width() > screenWidth) {
                boxes[i].invSpedX();
                colourIndex += 1;
                boxes[i].changeColour(colours[colourIndex%10]);
                System.out.println("Box #" + i + " inverted left-right (WALL)");
                System.out.println("Current colour index: " + colourIndex%10);
            }
            // y-axis movement controller       
            //Bounce off the top and bottom edges of the screen
            if (boxes[i].y() < 0 || boxes[i].y() + boxes[i].height() > screenHeight) {
                boxes[i].invSpedY();
                colourIndex += 1;
                boxes[i].changeColour(colours[colourIndex%10]);
                System.out.println("Box #" + i + " inverted up-down (WALL)");
                System.out.println("Current colour index: " + colourIndex%10);
            }

            //Movement Code
            boxes[i].moveX();
            boxes[i].moveY();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D box = (Graphics2D)g;
        for (int i = 0; i < boxes.length; i++) {
            box.setColor(boxes[i].colour());

            box.fillRect(boxes[i].x(), boxes[i].y(), boxes[i].width(), boxes[i].height());
            //box.dispose();
        }

        for (int i = 0; i < boxes.length; i++) {
            box.dispose();
        }

        // disposal of this graphics context and stop using system resources
    }

    public int randoNum (int pMin, int pMax) {
        int rand = (int) Math.floor(Math.random()*(pMax-pMin+1)+pMin);
        return rand;
    }
}
