import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    // Antes de utilizar esta clase te recomiendo ver el ejemplo en MAIN

    // filePath: Should look like this "/Users/julian/Desktop/Static100.txt"
    // type: Should be 0 if the input file is Static or 1 if it is Dynamic
    String filePath;
    int type;
    int N;
    int L;
    int t0;
    List<Double> particleRadiusList = new ArrayList<>();
    List<Double> particlePropertyList = new ArrayList<>();
    List<Double> x = new ArrayList<>();
    List<Double> y = new ArrayList<>();

    public Parser(String filePath, int type) {
        this.filePath = filePath;
        this.type = type;

        try {
            Scanner scanner = new Scanner(new File(filePath));
            if(type == 0){
                this.N = Integer.parseInt(scanner.nextLine().trim());
                this.L = Integer.parseInt(scanner.nextLine().trim());
                while (scanner.hasNextLine()) {
                    String[] aux = scanner.nextLine().trim().split("    ", 2);
                    particleRadiusList.add(Double.parseDouble(aux[0]));
                    particlePropertyList.add(Double.parseDouble(aux[1]));
                }
            }
            if(type == 1){
                this.t0 = Integer.parseInt(scanner.nextLine().trim());
                while (scanner.hasNextLine()) {
                    String[] aux = scanner.nextLine().trim().split("   ", 2);
                    x.add(Double.parseDouble(aux[0]));
                    y.add(Double.parseDouble(aux[1]));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getN() {
        return N;
    }

    public int getL() {
        return L;
    }

    public int getT0() {
        return t0;
    }

    public List<Double> getParticleRadiusList() {
        return particleRadiusList;
    }

    public List<Double> getParticlePropertyList() {
        return particlePropertyList;
    }

    public List<Double> getXList() {
        return x;
    }

    public List<Double> getYList() {
        return y;
    }

    public static void main(String[] args) {

    //Aca dejo ejemplo de como se utilizaria esta clase

    // Creamos el Parser pasandole el path como string y el tipo de archivo. //
    // STATIC = 0
    // DINAMIC = 1

        // Parser ParserExample = new Parser("/Users/julian/Desktop/Dynamic100.txt",1);

    // Solicitamos los valores guardados en el archivo estatico //

        // ParserExample.getN();
        // ParserExample.getL();
        // ParserExample.getParticlePropertyList();
        // ParserExample.getParticleRadiusList();

    // Solicitamos los valores guardados en el archivo dinamico //

        // ParserExample.getT0();
        // ParserExample.getXList();
        // ParserExample.getYList();
    }

}