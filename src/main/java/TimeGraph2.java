import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

public class TimeGraph2 {
    static String RESULTS_DIRECTORY_PATH = "data";
    static String RESULTS_FILENAME = "timegraph2.txt";
    static int tryQty = 10;

    public static void main(String[] args) {
        int matrixSide = 100;
        int minParticles = 1;
        int maxParticles = 10001;
        int NStep = 100;
        int N = 1000;
        double rc = 1.00;
        double radius = 1;
        Properties properties = System.getProperties();
        if( properties.getProperty("N")!= null ){
            N = Integer.parseInt(properties.getProperty("N"));
        }
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
        if( properties.getProperty("NStep")!= null ){
            NStep = Integer.parseInt(properties.getProperty("NStep"));
        }


        if( properties.getProperty("dataPath")!= null ){
            RESULTS_DIRECTORY_PATH = properties.getProperty("dataPath");
        }

        if( properties.getProperty("dataFilename")!= null ){
            RESULTS_FILENAME = properties.getProperty("dataFilename");
        }

        System.out.println(
                "Running test with { N:" + N +
                        ",minN:" + minParticles +
                        ",maxN:" + maxParticles +
                        ",rc:" + rc +
                        ",r:" + radius +
                        ",L:" + matrixSide +
                        ",NStep:" + NStep +
                        "}"
        );

        final Particle minValues = new Particle(0,radius,0,0);
        final Particle maxValues = new Particle(1,radius,matrixSide,matrixSide);



        List<TestResult> resultsSameN = new ArrayList<>();
        List<TestResult> resultsSameM = new ArrayList<>();
        List<TestResult> bruteForce = new ArrayList<>();
        {
            Set<Particle> particles = new HashSet<>(ParticleGenerator.generate(N,minValues,maxValues));
            for (int m = 1; m < matrixSide/(rc + radius);m++){
                long millisSum = 0;
                long millisMax = 0;
                long millisMin = 0;
                CellIndexMethod.findNeighbours(particles,matrixSide,m,rc,false);
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
                resultsSameN.add(new TestResult(
                        N, m,
                        matrixSide,
                        ((double)millisSum) / tryQty,
                        millisMax,
                        millisMin
                ));
            }
        }
        {
            int M = (int)Math.floor(matrixSide/(rc + radius));
            if(M == matrixSide/(rc + radius)){
                M--;
            }
            for (int n = minParticles; n <= maxParticles; n+=NStep){
                Set<Particle> particles = new HashSet<>(ParticleGenerator.generate(n,minValues,maxValues));
                long millisSum = 0;
                long millisMax = 0;
                long millisMin = 0;
                CellIndexMethod.findNeighbours(particles,matrixSide,M,rc,false);
                for (int i=0; i < tryQty; i++){
                    long startMillis = System.currentTimeMillis();
                    CellIndexMethod.findNeighbours(particles,matrixSide,M,rc,false);
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
                resultsSameM.add(new TestResult(
                        n, M,
                        matrixSide,
                        ((double)millisSum) / tryQty,
                        millisMax,
                        millisMin
                ));
            }
        }
        {
            int M = 1;
            for (int n = minParticles; n <= maxParticles; n+=NStep){
                Set<Particle> particles = new HashSet<>(ParticleGenerator.generate(n,minValues,maxValues));
                long millisSum = 0;
                long millisMax = 0;
                long millisMin = 0;
                for (int i=0; i < tryQty; i++){
                    long startMillis = System.currentTimeMillis();
                    CellIndexMethod.findNeighbours(particles,matrixSide,M,rc,false);
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
                bruteForce.add(new TestResult(
                        n, M,
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
            for( TestResult t : resultsSameN){
                writer.write(t.toString() + "\n");
            }
            writer.write( "\n");
            for( TestResult t : resultsSameM){
                writer.write(t.toString() + "\n");
            }
            writer.write( "\n");
            for( TestResult t : bruteForce){
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