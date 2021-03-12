import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParticleGenerator {

    public static List<Particle> generate(final int N, final Particle minValues, final Particle maxValues) {
        final double rrange = maxValues.getRadius() - minValues.getRadius();
        final double xrange = maxValues.getPosition().getX() - minValues.getPosition().getX();
        final double yrange = maxValues.getPosition().getY() - minValues.getPosition().getY();


        return Stream.iterate(1, id -> id + 1)
                .map(id -> new Particle(
                        id,
                        Math.random() * rrange + minValues.getRadius(),
                        Math.random() * xrange + minValues.getPosition().getX(),
                        Math.random() * yrange + minValues.getPosition().getY()
                )).limit(N).collect(Collectors.toList());
    }
}
