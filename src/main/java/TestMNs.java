import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestMNs {
    final static String RESULTS_DIRECTORY_PATH = "data";
    final static String RESULTS_FILENAME = "testmns.txt";

    public static void main(String[] args) {
        final int matrixSide = 100;
        final int MIN_PARTICLES = 990;
        final int MAX_PARTICLES = 1000;
        final double rc = 1.00;
        final int radius = 1;
        final Particle minValues = new Particle(0,radius,0,0);
        final Particle maxValues = new Particle(1,radius,matrixSide,matrixSide);



        List<TestResult> results = new ArrayList<>();
        for (int n = MIN_PARTICLES; n < MAX_PARTICLES; n++){
            Set<Particle> particles = new HashSet<>(ParticleGenerator.generate(n,minValues,maxValues));
            for (int m = 1; m < matrixSide/(rc + radius);m++){
                long startMillis = System.currentTimeMillis();
                CellIndexMethod.findNeighbours(particles,matrixSide,m,rc);
                long endMillis = System.currentTimeMillis();
                results.add(new TestResult(
                        n, m,
                        ((double) n)/matrixSide,
                        endMillis - startMillis
                ));
            }
        }
        try {
            File dirPath = new File(RESULTS_DIRECTORY_PATH);
            if (!dirPath.exists()){
                dirPath.mkdirs();
            }

            FileWriter fileWriter = new FileWriter(Paths.get(dirPath.getPath(),RESULTS_FILENAME).toString());
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for( TestResult t : results){
                writer.write(t.toString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static class TestResult{
        double density;
        int N;
        int M;
        double time;
        public TestResult(int N,int M,double density,long time){
            this.N = N;
            this.M = M;
            this.density = density;
            this.time = time;
        }

        @Override
        public String toString() {
            return "" + N + " " + M + " " + density +" " + time;
        }
    }
}
