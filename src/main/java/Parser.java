import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    private int particlesQty;
    private int matrixSide;
    private int initialTime;
    private final List<Double> particleRadiusList = new ArrayList<>();
    private final List<Double> particlePropertyList = new ArrayList<>();
    private final List<Position> particlePositions = new ArrayList<>();

    // filePath: Should look like this "/Users/julian/Desktop/Static100.txt"
    public Parser(String filePath, FileType type) {
        try {
            Scanner scanner = new Scanner(new File(filePath));
            if(type == FileType.STATIC){
                this.particlesQty = Integer.parseInt(scanner.nextLine().trim());
                this.matrixSide = Integer.parseInt(scanner.nextLine().trim());
                while (scanner.hasNextLine()) {
                    String[] aux = scanner.nextLine().trim().split(" {4}", 2);
                    particleRadiusList.add(Double.parseDouble(aux[0]));
                    particlePropertyList.add(Double.parseDouble(aux[1]));
                }
            }
            else if(type == FileType.DYNAMIC){
                this.initialTime = Integer.parseInt(scanner.nextLine().trim());
                while (scanner.hasNextLine()) {
                    String[] aux = scanner.nextLine().trim().split(" {3}", 2);
                    particlePositions.add(new Position(Double.parseDouble(aux[0]), Double.parseDouble(aux[1])));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getParticlesQty() {
        return particlesQty;
    }

    public int getMatrixSide() {
        return matrixSide;
    }

    public int getInitialTime() {
        return initialTime;
    }

    public List<Double> getParticleRadiusList() {
        return particleRadiusList;
    }

    public List<Double> getParticlePropertyList() {
        return particlePropertyList;
    }

    public List<Position> getParticlePositions() {
        return particlePositions;
    }

}