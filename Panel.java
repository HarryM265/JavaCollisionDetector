import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI.KeyHandler;

public class Panel extends JPanel implements Runnable {

    final int screenWidth = 500;
    final int screenHeight = 500;

    final int halfScreenWidth = (int)(screenWidth/2);
    final int halfScreenHeight = (int)(screenHeight/2);

    //Point size
    final int pDimens = 5;
    final int pWidth = 20;
    final int pHeight = 20;

    //First point
    final int randPointX = 556;
    final int randPointY = 424;

    Point startP = new Point();
    Point nextP = new Point();

    //Boolean to use random point from ui
    boolean isFirstDraw = true;

    //Number of points
    final int numPoints = 3;

    //p1 Location
    final int p1x = halfScreenWidth;
    final int p1y = halfScreenHeight/8;

    //p2 Location
    final int p2x = halfScreenWidth/4;
    final int p2y = (screenHeight/8) * 7;

    //p3 Location
    final int p3x = (halfScreenWidth * 3) /4 + halfScreenWidth;
    final int p3y = ((screenHeight * 7)/8);

    //Triangle points array (array literal)
    Point[] tPoints = new Point[]{new Point(p1x, p1y), new Point(p2x, p2y), new Point(p3x, p3y)};

    //Found points
    ArrayList<Point> foundPoints = new ArrayList<Point>();

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
                System.out.println("Found Points:");

                //TODO Testing code
                for (int i = 0; i < foundPoints.size(); i++) {
                    System.out.println(foundPoints.get(i).pointToString());
                }

                //System.out.println("Random Number is: " + randChoice(numPoints));
                drawCount = 0;
                timer = 0;
            }
        }
        
    }

    public void update() {

        Point triangP; // Trangle points
        int deltaX, deltaY; //difference between current point and chosen triangle point

        //set first point to UI point
        if (isFirstDraw) {
            startP.setX(randPointX);
            startP.setY(randPointY);
            isFirstDraw = false;
        } else {
            startP.setX(nextP.getX());
            startP.setY(nextP.getY());
        }

        triangP = tPoints[randChoice(numPoints)-1]; //Choose a vertex

        deltaX = subLessFromMore(startP.getX(), triangP.getX()); //find the difference in x coords
        deltaY = subLessFromMore(startP.getY(), triangP.getY()); //find the difference in y coords
        
        //Find next point location x value
        if (triangP.getX() > startP.getX()) {
            nextP.setX(startP.getX() + (int)(0.5*deltaX));
        } else {
            nextP.setX(startP.getX() - (int)(0.5*deltaX));
        }

        //Find next point location y value
        if (triangP.getY() > startP.getY()) {
            nextP.setY(startP.getY() + (int)(0.5*deltaY));
        } else {
            nextP.setY(startP.getY() - (int)(0.5*deltaY));
        }

        foundPoints.add(nextP);
        //System.out.println("Found point is: " + nextP.pointToString());
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //ArrayList of triangle point graphics
        ArrayList<Graphics2D> tps = new ArrayList<Graphics2D>();

        //Draw all traingle points
        for (int i = 0; i < tPoints.length; i++) {
            tps.add((Graphics2D)g);
            tps.get(i).setColor(Color.BLACK);
            tps.get(i).fillRect(tPoints[i].getX(), tPoints[i].getY(), pDimens, pDimens);
        }

        Graphics2D randPoint = (Graphics2D)g;
        randPoint.setColor(Color.BLACK);
        randPoint.fillRect(randPointX, randPointY, pDimens, pDimens);
        
        //ArrayList of found point graphics
        ArrayList<Graphics2D> ps = new ArrayList<Graphics2D>();

        //Draw all found points
        System.out.println("Current points are: ");
        for (int i = 0; i < foundPoints.size(); i++) {
            ps.add((Graphics2D)g);
            Point currP = foundPoints.get((i));//Can be moved onto the same line
            ps.get(i).setColor(Color.BLACK);
            ps.get(i).fillRect(currP.getX(), currP.getY(), pWidth, pHeight);
            System.out.println(currP.pointToString());
        }

        randPoint.dispose();

        //Dispose of all graphics content on tPoints (Triangle points)
        for (int i = 0; i < tPoints.length; i++) {
            tps.get(i).dispose();
        }

        //Dispose of all graphics on ps (found points)
        for (int i = 0; i < ps.size(); i++) {
            ps.get(i).dispose();
        }
    }

    //Pick a random outcome from the 1 and the parameter
    public static int randChoice(int max) {
        int result;

        Random rand = new Random(); // Instance of random class

        //Generate result
        result = rand.nextInt(max);

        return result +1;
    }

    //Find the distance between two points
    public static int distanceOf2Points(int x1, int y1, int x2, int y2) {
        int result = (int)(Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)));

        return result;
    }

    //Subtract less value from more value
    public static int subLessFromMore(int p1, int p2) {
        int r;
        if (p1 > p2) {
            r = p1-p2;
        } else {
            r = p2 - p1;
        }
        return r;
    }
}
