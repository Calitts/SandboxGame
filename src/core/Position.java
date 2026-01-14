package core;
public class Position {
    private double x;
    private double y;


    Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return this.x;
    }

    double getY() {
        return this.y;
    }

    int intX() {
        return (int) this.x;
    }

    int intY() {
        return (int) this.y;
    }
}