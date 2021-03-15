import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class TimeGraph {
    static String RESULTS_DIRECTORY_PATH = "data";
    static String RESULTS_FILENAME = "timegraph.txt";
    static int tryQty = 10;

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


        if( properties.getProperty("dataPath")!= null ){
            RESULTS_DIRECTORY_PATH = properties.getProperty("dataPath");
        }

        if( properties.getProperty("dataFilename")!= null ){
            RESULTS_FILENAME = properties.getProperty("dataFilename");
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
                long millisSum = 0;
                long millisMax = 0;
                long millisMin = 0;
                for (int i=0; i < tryQty; i++){
                    long startMillis = System.currentTimeMillis();
                    CellIndexMethod.findNeighbours(particles,matrixSide,m,rc,false);
                    long endMillis = System.currentTimeMillis();
                    long total = endMillis - startMillis;
                    if(i == 0){
                        millisMax = total;
                        millisMin = total;
                    }
                    if(total < millisMin){
                        millisMin = total;
                    }
                    if(total > millisMax){
                        millisMax = total;
                    }
                    millisSum += total;
                }
                results.add(new TestResult(
                        n, m,
                        matrixSide,
                        ((double)millisSum) / tryQty,
                        millisMax,
                        millisMin
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
        int N;
        int M;
        double L;
        double time;
        double maxtime;
        double mintime;
        public TestResult(int N,int M,double L,double time,double maxtime,double mintime){
            this.N = N;
            this.M = M;
            this.L = L;
            this.time = time;
            this.maxtime = maxtime;
            this.mintime = mintime;
        }

        @Override
        public String toString() {
            return "" + N + " " + M + " " + L +" " + time + " " + maxtime + " " + mintime;
        }
    }
}