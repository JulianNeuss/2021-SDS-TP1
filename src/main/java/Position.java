import java.util.Objects;

public class Position {
    private double x;
    private double y;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distanceTo(Position otherPosition, double limitX, double limitY, boolean periodicX, boolean periodicY){
        double relativeX, relativeY;
        if(periodicX) {
            relativeX = Math.min(limitX - Math.abs(x - otherPosition.x), Math.abs(x - otherPosition.x));
        }
        else
            relativeX = Math.abs(x - otherPosition.x);

        if(periodicY)
            relativeY = Math.min(limitY - Math.abs(y - otherPosition.y), Math.abs(y - otherPosition.y));
        else
            relativeY = Math.abs(y - otherPosition.y);
        return Math.sqrt(relativeX * relativeX + relativeY * relativeY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
