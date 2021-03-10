import java.util.Objects;

public class Particle {
    private final int id;
    private final int radius;
    private final Position position;

    public Particle(int id, int radius, double x, double y) {
        this.id = id;
        if(radius <= 0)
            throw new IllegalArgumentException("Radius is less or equal than 0");
        this.radius = radius;
        this.position = new Position(x, y);
    }

    public int getId() {
        return id;
    }

    public int getRadius() {
        return radius;
    }

    public double distanceTo(Particle otherParticle){
        return position.distanceTo(otherParticle.getPosition());
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
