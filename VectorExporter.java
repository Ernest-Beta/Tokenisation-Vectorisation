import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class VectorExporter {

    /**
     * exportarei ta dianysmata xaraktiristikwn (1 kai 0) se ena arxeio keimenou.
     * kathe grammh antistoixei se ena review, me 1 gia parousia lekseon kai 0 gia apousia.
     */
    public static void exportVectorsToFile(List<Vector<Integer>> vectors, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            System.out.println("Exporting feature vectors to: " + filePath);

            for (Vector<Integer> vector : vectors) {
                StringBuilder line = new StringBuilder();
                
                for (int i = 0; i < vector.size(); i++) {
                    line.append(vector.get(i));
                    if (i < vector.size() - 1) {
                        line.append(","); //xwrizei tis times me komma
                    }
                }

                writer.write(line.toString());
                writer.newLine();
            }

            System.out.println("Feature vectors successfully exported!");
        } catch (IOException e) {
            System.err.println("Error writing feature vectors file: " + e.getMessage());
        }
    }

    //
    public static void exportLabelsToFile(List<Integer> labels, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            System.out.println("Exporting labels to: " + filePath);

            for (Integer label : labels) {
                writer.write(label.toString());
                writer.newLine();
            }

            System.out.println("Labels successfully exported!");
        } catch (IOException e) {
            System.err.println("Error writing labels file: " + e.getMessage());
        }
    }


    /**
     * exportarei tis lekseis xaraktiristikwn (lexilogio) se ena arxeio.
     * auto to arxeio periexei tis lekseis pou xrisimopoiountai gia to training tou montelou.
     */
    public static void exportFeatureWordsToFile(List<String> vocabulary, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("Feature Words (Vocabulary)\n");

            for (int i = 0; i < vocabulary.size(); i++) {
                writer.write(i + ": " + vocabulary.get(i) + "\n");
            }

            System.out.println("Feature words successfully exported to: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }
    /**
     * importarei tis lekseis xaraktiristikwn apo ena arxeio.
     * fortwnei to lexilogio apo ena arxeio pou dhmiourgithike prin.
     */
    public static List<String> importFeatureWordsFromFile(String filePath) {
        List<String> vocabulary = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Importing feature words from: " + filePath);
            String line;

            while ((line = reader.readLine()) != null) {
                //paraleipei tin header grammh an yparxei
                if (line.startsWith("Feature Words")) {
                    continue;
                }

                //spaei tin grammh se "index: leksi"
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    vocabulary.add(parts[1].trim());
                }
            }

            System.out.println("Feature words successfully imported!");
        } catch (IOException e) {
            System.err.println("Error reading feature words file: " + e.getMessage());
        }

        return vocabulary;
    }
    
}
