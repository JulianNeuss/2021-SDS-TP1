import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

public class GenerateFiles {
    private static String STATIC_FILENAME = "./data/static.txt";
    private static String DYNAMIC_FILENAME = "./data/dynamic.txt";

    private static int PARTICLE_NUMBER = 100;
    private static double RC = 1;
    private static double RADIUS = 1;
    private static int MATRIX_SIDE = 10;

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        if( properties.getProperty("dynamicFilename")!= null ){
            DYNAMIC_FILENAME = properties.getProperty("dynamicFilename");
        }
        DYNAMIC_FILENAME = Paths.get(DYNAMIC_FILENAME).toAbsolutePath().toString();
        if( properties.getProperty("staticFilename")!= null ){
            STATIC_FILENAME = properties.getProperty("staticFilename");
        }
        STATIC_FILENAME = Paths.get(STATIC_FILENAME).toAbsolutePath().toString();

        if( properties.getProperty("N")!= null ) {
            PARTICLE_NUMBER = Integer.parseInt(properties.getProperty("N"));
        }
        if( properties.getProperty("rc")!= null ){
            RC = Double.parseDouble(properties.getProperty("rc"));
        }
        if( properties.getProperty("r")!= null ){
            RADIUS = Double.parseDouble(properties.getProperty("r"));
        }
        if( properties.getProperty("L")!= null ){
            MATRIX_SIDE = Integer.parseInt(properties.getProperty("L"));
        }

        // check static
        File staticFile = new File(STATIC_FILENAME);
        if(!staticFile.getParentFile().exists()){
            if(!staticFile.getParentFile().mkdirs()){
                System.err.println("Static's folder does not exist and could not be created");
                System.exit(1);
            }
        }
        //check dynamic
        File dynamicFile = new File(DYNAMIC_FILENAME);
        if(!dynamicFile.getParentFile().exists()){
            if(!dynamicFile.getParentFile().mkdirs()) {
                System.err.println("Dynamic's folder does not exist and could not be created");
                System.exit(1);
            }
        }

        List<Particle> particles = ParticleGenerator.generate(
                PARTICLE_NUMBER,
                new Particle(0,RADIUS,0,0),
                new Particle(0,RADIUS,MATRIX_SIDE,MATRIX_SIDE)
                );

        try{
            BufferedWriter sbw = new BufferedWriter(new FileWriter(staticFile));
            BufferedWriter dbw = new BufferedWriter(new FileWriter(dynamicFile));
            sbw.write(Integer.toString(particles.size()) + "\n");
            sbw.write(Integer.toString(MATRIX_SIDE) + "\n");
            dbw.write(Integer.toString(0) + "\n");

            for (int i = 0; i < particles.size(); i++){
                Particle p = particles.get(i);
                if(i < particles.size() - 1){
                    dbw.write("" + p.getPosition().getX() + "   " + p.getPosition().getY() + "\n");
                    sbw.write("" + p.getRadius() + "    " + RC + "\n" );
                }else{
                    dbw.write("" + p.getPosition().getX() + "   " + p.getPosition().getY());
                    sbw.write("" + p.getRadius() + "    " + RC );
                }
            }
            sbw.close();
            dbw.close();
        }catch (IOException e){
            System.err.println("There was a problem generating files");
            e.printStackTrace();
        }
    }
}
