import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class TestMNs {
    final static String RESULTS_DIRECTORY_PATH = "data";
    final static String RESULTS_FILENAME = "testmns.txt";

    public static void main(String[] args) {
        int matrixSide = 100;
        int minParticles = 990;
        int maxParticles = 1000;
        double rc = 1.00;
        double radius = 1;
        Properties properties = System.getProperties();
        if( properties.getProperty("minN")!= null ){
            minParticles = Integer.parseInt(properties.getProperty("minN"));
        }
        if( properties.getProperty("maxN")!= null ){
            maxParticles = Integer.parseInt(properties.getProperty("maxN"));
        }
        if( properties.getProperty("rc")!= null ){
            rc = Double.parseDouble(properties.getProperty("rc"));
        }
        if( properties.getProperty("r")!= null ){
            radius = Double.parseDouble(properties.getProperty("r"));
        }
        if( properties.getProperty("L")!= null ){
            matrixSide = Integer.parseInt(properties.getProperty("L"));
        }

        System.out.println(
                "Running test with { minN:" + minParticles+
                        ",maxN:" + maxParticles +
                        ",rc:" + rc +
                        ",r:" + radius +
                        ",L:" + matrixSide +
                "}"
        );

        final Particle minValues = new Particle(0,radius,0,0);
        final Particle maxValues = new Particle(1,radius,matrixSide,matrixSide);



        List<TestResult> results = new ArrayList<>();
        for (int n = minParticles; n < maxParticles; n++){
            Set<Particle> particles = new HashSet<>(ParticleGenerator.generate(n,minValues,maxValues));
            for (int m = 1; m < matrixSide/(rc + radius);m++){
                long startMillis = System.currentTimeMillis();
                CellIndexMethod.findNeighbours(particles,matrixSide,m,rc,false);
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
