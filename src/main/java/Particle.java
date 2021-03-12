import java.util.Objects;

public class Particle {
    private final int id;
    private final double radius;
    private final Position position;

    public Particle(int id, double radius, double x, double y) {
        this.id = id;
        if(radius < 0)
            throw new IllegalArgumentException("Radius can't be negative");
        this.radius = radius;
        this.position = new Position(x, y);
    }

    public int getId() {
        return id;
    }

    public double getRadius() {
        return radius;
    }

    public double distanceTo(Particle otherParticle){
        return position.distanceTo(otherParticle.getPosition()) - radius - otherParticle.radius;
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

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                ", radius=" + radius +
                ", position=" + position +
                '}';
    }
}
