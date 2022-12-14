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

    public Point(int pX, int pY) {
        x = pX;
        y = pY;
    }

    public String pointToString() {
        return ("(" + x + ", " + y + ")");
    }
}
