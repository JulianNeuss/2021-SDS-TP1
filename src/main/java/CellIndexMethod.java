import java.util.*;

public class CellIndexMethod {

    // Suppresses default constructor, ensuring non-instantiability.
    private CellIndexMethod() {
    }

    public static Map<Integer, Set<Integer>> findNeighbours(Set<Particle> particles, double matrixSize, int matrixRowsAndColumns, double interactionRadius){
        if(matrixSize <= 0 || matrixRowsAndColumns <= 0)
            throw new IllegalArgumentException("Matrix size nor matrix rows nor columns can't be <=0 (matrixSize: " + matrixSize + ", matrixRowsAndColumns: " + matrixRowsAndColumns);

        double cellSize = matrixSize/matrixRowsAndColumns;

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
                    Set<Integer> neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row).get(column), interactionRadius);
                    if(!neighboursIds.isEmpty() && !idToNeighbourIdsMap.containsKey(particle.getId()))
                        idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());
                    idToNeighbourIdsMap.get(particle.getId()).addAll(neighboursIds);

                    boolean canLookUp = row - 1 >= 0;
                    boolean canLookRight = column + 1 < matrixRowsAndColumns;
                    boolean canLookDown = row + 1 < matrixRowsAndColumns;

                    if(canLookUp){
                        // celda de arriba
                        neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row - 1).get(column), interactionRadius);
                        if(!neighboursIds.isEmpty() && !idToNeighbourIdsMap.containsKey(particle.getId()))
                            idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());
                        idToNeighbourIdsMap.get(particle.getId()).addAll(neighboursIds);

                        // celda arriba-derecha
                        if(canLookRight){
                            neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row - 1).get(column + 1), interactionRadius);
                            if(!neighboursIds.isEmpty() && !idToNeighbourIdsMap.containsKey(particle.getId()))
                                idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());
                            idToNeighbourIdsMap.get(particle.getId()).addAll(neighboursIds);
                        }
                    }

                    if(canLookRight){
                        // celda derecha
                        neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row).get(column + 1), interactionRadius);
                        if(!neighboursIds.isEmpty() && !idToNeighbourIdsMap.containsKey(particle.getId()))
                            idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());
                        idToNeighbourIdsMap.get(particle.getId()).addAll(neighboursIds);

                        // celda abajo-derecha
                        if(canLookDown){
                            neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row + 1).get(column + 1), interactionRadius);
                            if(!neighboursIds.isEmpty() && !idToNeighbourIdsMap.containsKey(particle.getId()))
                                idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());
                            idToNeighbourIdsMap.get(particle.getId()).addAll(neighboursIds);                        }
                    }
                }
            }
        }

        return idToNeighbourIdsMap;
    }

    private static Set<Integer> getNeighboursFromCell(Particle particle, Set<Particle> cell, double interactionRadius) {
        Set<Integer> neighboursIds = new HashSet<>();
        for (Particle otherParticle : cell){
            if(particle.distanceTo(otherParticle) <= interactionRadius){
                neighboursIds.add(otherParticle.getId());
            }
        }
        return neighboursIds;
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
