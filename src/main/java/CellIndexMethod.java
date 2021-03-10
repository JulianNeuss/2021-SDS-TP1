import java.util.*;

public class CellIndexMethod {

    // Suppresses default constructor, ensuring non-instantiability.
    private CellIndexMethod() {
    }

    public static Map<Integer, Set<Integer>> findNeighbours(Set<Particle> particles, int matrixSize, int matrixRowsAndColumns, double interactionRadius){
        if(matrixSize <= 0 || matrixRowsAndColumns <= 0)
            throw new IllegalArgumentException("Matrix size nor matrix rows nor columns can't be <=0 (matrixSize: " + matrixSize + ", matrixRowsAndColumns: " + matrixRowsAndColumns);

        double cellSize = (double) matrixSize/matrixRowsAndColumns;

        if(cellSize <= interactionRadius){
            throw new IllegalArgumentException("The condition L/M > rc is not met");
        }

        ArrayList<ArrayList<Set<Particle>>> particlesMatrix = initializeMatrix(matrixRowsAndColumns, matrixRowsAndColumns);

        for (Particle particle : particles){
            particlesMatrix.get((int) Math.floor(particle.getPosition().getY() / cellSize))
                    .get((int) Math.floor(particle.getPosition().getX() / cellSize))
                    .add(particle);
        }

        Map<Integer, Set<Integer>> idToNeighbourIdsMap = new HashMap<>();

        for(int row = 0; row < matrixRowsAndColumns; row++){
            for(int column = 0; column < matrixRowsAndColumns; column++){
                for(Particle particle : particlesMatrix.get(row).get(column)){
                    // misma celda
                    addNeighboursFromCell(particle, row, column, interactionRadius, particlesMatrix, idToNeighbourIdsMap);

                    boolean canLookUp = row - 1 >= 0;
                    boolean canLookRight = column + 1 < matrixRowsAndColumns;
                    boolean canLookDown = row + 1 < matrixRowsAndColumns;

                    if(canLookUp){
                        // celda de arriba
                        addNeighboursFromCell(particle, row-1, column, interactionRadius, particlesMatrix, idToNeighbourIdsMap);

                        // celda arriba-derecha
                        if(canLookRight)
                            addNeighboursFromCell(particle, row-1, column+1, interactionRadius, particlesMatrix, idToNeighbourIdsMap);
                    }

                    if(canLookRight){
                        // celda derecha
                        addNeighboursFromCell(particle, row, column + 1, interactionRadius, particlesMatrix, idToNeighbourIdsMap);

                        // celda abajo-derecha
                        if(canLookDown){
                            addNeighboursFromCell(particle, row + 1, column + 1, interactionRadius, particlesMatrix, idToNeighbourIdsMap);
                        }
                    }
                }
            }
        }

        return idToNeighbourIdsMap;
    }

    private static void addNeighboursFromCell(Particle particle, int row, int column, double interactionRadius, ArrayList<ArrayList<Set<Particle>>> particlesMatrix, Map<Integer, Set<Integer>> idToNeighbourIdsMap) {
        for (Particle otherParticle : particlesMatrix.get(row - 1). get(column)){
            if(particle.distanceTo(otherParticle) <= interactionRadius){
                if(!idToNeighbourIdsMap.containsKey(particle.getId()))
                    idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                idToNeighbourIdsMap.get(particle.getId()).add(otherParticle.getId());
            }
        }
    }

    private static ArrayList<ArrayList<Set<Particle>>> initializeMatrix(int rows, int columns){
        ArrayList<ArrayList<Set<Particle>>> particlesMatrix = new ArrayList<>(rows);

        for (int row = 0; row < rows; row++){
            particlesMatrix.add(new ArrayList<>(columns));

            for (int column = 0; column < columns; column++){
                particlesMatrix.get(row).add(new HashSet<>());
            }
        }

        return particlesMatrix;
    }
}
