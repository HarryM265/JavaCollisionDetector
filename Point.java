public class Point {
    public int x;
    public int y;

    public void setX(int p) {
        x = p;
    }
    public void setY(int p) {
        y = p;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    //Parameters constructor
    public Point(int pX, int pY) {
        x = pX;
        y = pY;
    }

    //Default constructor
    public Point() {
        x = 500;
        y= 500;
    }

    public String pointToString() {
        return ("(" + x + ", " + y + ")");
    }
}
