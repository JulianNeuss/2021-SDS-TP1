import java.util.*;

public class CellIndexMethod {

    // Suppresses default constructor, ensuring non-instantiability.
    private CellIndexMethod() {
    }

    public static Map<Integer, Set<Integer>> findNeighbours(Set<Particle> particles, double matrixSize, int matrixRowsAndColumns, double interactionRadius, boolean periodicBorder){
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
                    Set<Integer> neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row).get(column), interactionRadius, matrixSize, periodicBorder);
                    if(!neighboursIds.isEmpty()) {
                        if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                            idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                        for (Integer neighbourId : neighboursIds){
                            idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                            if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                            idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                        }
                    }

                    boolean canLookUp = row - 1 >= 0;
                    boolean canLookRight = column + 1 < matrixRowsAndColumns;
                    boolean canLookDown = row + 1 < matrixRowsAndColumns;

                    if(!periodicBorder) {
                        if (canLookUp) {
                            // celda de arriba
                            neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row - 1).get(column), interactionRadius, matrixSize, false);
                            if (!neighboursIds.isEmpty()) {
                                if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                                    idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                                for (Integer neighbourId : neighboursIds) {
                                    idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                                    if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                        idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                                    idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                                }
                            }

                            // celda arriba-derecha
                            if (canLookRight) {
                                neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row - 1).get(column + 1), interactionRadius, matrixSize, false);
                                if (!neighboursIds.isEmpty()) {
                                    if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                                        idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                                    for (Integer neighbourId : neighboursIds) {
                                        idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                                        if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                            idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                                        idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                                    }
                                }
                            }
                        }

                        if (canLookRight) {
                            // celda derecha
                            neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row).get(column + 1), interactionRadius, matrixSize, false);
                            if (!neighboursIds.isEmpty()) {
                                if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                                    idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                                for (Integer neighbourId : neighboursIds) {
                                    idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                                    if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                        idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                                    idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                                }
                            }

                            // celda abajo-derecha
                            if (canLookDown) {
                                neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row + 1).get(column + 1), interactionRadius, matrixSize, false);
                                if (!neighboursIds.isEmpty()) {
                                    if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                                        idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                                    for (Integer neighbourId : neighboursIds) {
                                        idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                                        if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                            idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                                        idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                                    }
                                }
                            }
                        }
                    } else {
                        // celda de arriba
                        neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get((row - 1 + matrixRowsAndColumns) % matrixRowsAndColumns).get(column), interactionRadius, matrixSize, true);
                        if (!neighboursIds.isEmpty()) {
                            if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                                idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                            for (Integer neighbourId : neighboursIds) {
                                idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                                if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                    idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                                idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                            }
                        }

                        // celda arriba-derecha
                        neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get((row - 1 + matrixRowsAndColumns) % matrixRowsAndColumns).get((column + 1) % matrixRowsAndColumns), interactionRadius, matrixSize, true);
                        if (!neighboursIds.isEmpty()) {
                            if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                                idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                            for (Integer neighbourId : neighboursIds) {
                                idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                                if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                    idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                                idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                            }
                        }

                        // celda derecha
                        neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get(row).get((column + 1) % matrixRowsAndColumns), interactionRadius, matrixSize, true);
                        if (!neighboursIds.isEmpty()) {
                            if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                                idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                            for (Integer neighbourId : neighboursIds) {
                                idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                                if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                    idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                                idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                            }
                        }

                        // celda abajo-derecha
                        neighboursIds = getNeighboursFromCell(particle, particlesMatrix.get((row + 1) % matrixRowsAndColumns).get((column + 1) % matrixRowsAndColumns), interactionRadius, matrixSize, true);
                        if (!neighboursIds.isEmpty()) {
                            if (!idToNeighbourIdsMap.containsKey(particle.getId()))
                                idToNeighbourIdsMap.put(particle.getId(), new HashSet<>());

                            for (Integer neighbourId : neighboursIds) {
                                idToNeighbourIdsMap.get(particle.getId()).add(neighbourId);

                                if (!idToNeighbourIdsMap.containsKey(neighbourId))
                                    idToNeighbourIdsMap.put(neighbourId, new HashSet<>());
                                idToNeighbourIdsMap.get(neighbourId).add(particle.getId());
                            }
                        }
                    }
                }
            }
        }
        return idToNeighbourIdsMap;
    }

    private static Set<Integer> getNeighboursFromCell(Particle particle, Set<Particle> cell, double interactionRadius, double matrixSize, boolean periodicBorder) {
        Set<Integer> neighboursIds = new HashSet<>();
        for (Particle otherParticle : cell){
            if(particle.distanceTo(otherParticle, matrixSize, matrixSize, periodicBorder, periodicBorder) <= interactionRadius && particle.getId() != otherParticle.getId()){
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
