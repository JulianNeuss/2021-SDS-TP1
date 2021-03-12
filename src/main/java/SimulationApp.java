import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class SimulationApp {
    private static String OUTPUT_FILENAME = "./data/output.txt";
    private static String STATIC_FILENAME = "./data/static.txt";
    private static String DYNAMIC_FILENAME = "./data/dynamic.txt";

    private static boolean PERIODIC_BORDER = true;

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
        if( properties.getProperty("outputFilename")!= null ){
            OUTPUT_FILENAME = properties.getProperty("staticFilename");
        }
        OUTPUT_FILENAME = Paths.get(OUTPUT_FILENAME).toAbsolutePath().toString();
        if( properties.getProperty("periodicBorder")!= null ){
            PERIODIC_BORDER = Boolean.parseBoolean(properties.getProperty("staticFilename"));
        }

        //check file existance and create ouput file
        // check static
        File checker = new File(STATIC_FILENAME);
        if(!checker.exists()){
            System.err.println("Dynamic file does not exist");
            System.exit(1);
        }
        //check dynamic
        checker = new File(DYNAMIC_FILENAME);
        if(!checker.exists()){
            System.err.println("Dynamic file does not exist");
            System.exit(1);
        }
        checker = new File(OUTPUT_FILENAME);
        if(!checker.getParentFile().exists()){
            if(!checker.getParentFile().mkdirs()){
                System.err.println("Output directory does not exist and could not be created");
                System.exit(1);
            }
        }



        Parser staticParser = new Parser(STATIC_FILENAME, FileType.STATIC);
        Parser dynamicParser = new Parser(DYNAMIC_FILENAME, FileType.DYNAMIC);

        Set<Particle> particles = new HashSet<>();
        for(int particleId = 0; particleId < staticParser.getParticlesQty(); particleId++){
            particles.add(new Particle(particleId + 1, staticParser.getParticleRadiusList().get(particleId),
                    dynamicParser.getParticlePositions().get(particleId).getX(), dynamicParser.getParticlePositions().get(particleId).getY()));
        }

        int matrixRowsAndColumns = 5;

        Map<Integer, Set<Integer>> idsToNeighboursIdsMap =  CellIndexMethod.findNeighbours(particles, staticParser.getMatrixSide(),
                matrixRowsAndColumns, staticParser.getParticlePropertyList().get(0), PERIODIC_BORDER);

        StringBuilder str = new StringBuilder();
        for(Integer id : idsToNeighboursIdsMap.keySet()){
            str.append(id);
            for(Integer neighbourId : idsToNeighboursIdsMap.get(id)){
                str.append(',').append(neighbourId);
            }
            str.append('\n');
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILENAME, false));
            writer.write(str.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
