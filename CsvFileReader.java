import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CsvFileReader {

    private static int totalTotal;
    private static int totalPoisitive;
    private static int totalNegative;
    
    
    
    //getter kai setters
    public static int getTotal(){
        return totalTotal;
    }
    
    public static int getTotalPositive(){
        return totalPoisitive;
    }
    public static void  setTotalPositive(int number){
        totalPoisitive = number;
    }
    public static void  setTotal(int number){
        totalTotal = number;
    }
    public static int getTotalNegative(){
        return totalNegative;
    }

    public static void setTotalNegative(int number){
        totalNegative= number;
    }

    
    
    public static List<Example> readFilesFromFolders(String positiveFolder, String negativeFolder) {
        List<Example> data = new ArrayList<>();

        //diavasma thetikwn reviews (Label = 1)
        data.addAll(readFilesFromFolder(positiveFolder, "positive"));
        totalPoisitive= data.size();
        System.out.println("positive examples number: "+totalPoisitive);
        //diavasma arnhtikwn reviews (Label = 0)
        data.addAll(readFilesFromFolder(negativeFolder, "negative"));
        
        totalTotal=data.size();
        totalNegative= totalTotal-totalPoisitive;
        System.out.println("negative examples number: "+totalNegative);
        
        return data;
    }

    private static List<Example> readFilesFromFolder(String folderPath, String label) {
        List<Example> examples = new ArrayList<>();
        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Error: Directory not found -> " + folderPath);
            return examples;
        }

        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                try {
                    //diavasma periexomenou arxeiou
                    String content = Files.readString(file.toPath());

                    //ginetai tokenize to keimeno me RegexTokenizer
                    Set<String> tokens = RegexTokenizer.tokenize(content);

                    //metatropi tou set twn tokens se ena string me kena anamesa
                    String processedText = String.join(" ", tokens);

                    //prosthetoume to example sth lista
                    examples.add(new Example(processedText, label));

                } catch (IOException e) {
                    System.out.println("Error reading file: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
        return examples;
    }

}
