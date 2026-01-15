package core;

public class Vector2D {
    private int X;
    private int Y;

    public Vector2D(){
        X = 0;
        Y = 0;
    }

   public Vector2D(int x, int y){
       X = x;
       Y = y;
    }

    public void setPosition(int x, int y){
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }
}
