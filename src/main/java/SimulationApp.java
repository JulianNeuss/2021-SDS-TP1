import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimulationApp {
    private static final String OUTPUT_FILENAME = "/Users/aroca/Desktop/2021-SDS-TP1/visualization/matplot/dataExamples/output.txt";
    private static final String STATIC_FILENAME = "/Users/aroca/Desktop/2021-SDS-TP1/visualization/matplot/dataExamples/staticExample.txt";
    private static final String DYNAMIC_FILENAME = "/Users/aroca/Desktop/2021-SDS-TP1/visualization/matplot/dataExamples/dynamicExample.txt";

    private static final boolean PERIODIC_BORDER = true;

    public static void main(String[] args) {
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
