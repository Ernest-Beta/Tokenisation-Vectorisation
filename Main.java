import java.util.*;

public class Main {

    public static void main(String[] args) {

        //thetei tis uperparametrous gia to training
        double k = 0.04; //pososto ton ligotero sixnon lexeon pou tha afairesoume
        int n = 150;     //arithmos twn pio sixnon lexeon pou tha afairesoume
        int m = 3000;    //arithmos lexeon pou kratai meta tin epilogi me Information Gain

        //paths gia to training dataset (thetikwn kai arnitikwn reviews)
        String trainPositivePath = "C:\\Users\\ernes\\Desktop\\PartA\\Dataset\\aclImdb\\train\\pos";  
        String trainNegativePath = "C:\\Users\\ernes\\Desktop\\PartA\\Dataset\\aclImdb\\train\\neg";

        //paths gia to test dataset
        String testPositivePath = "C:\\Users\\ernes\\Desktop\\PartA\\Dataset\\aclImdb\\test\\pos";  
        String testNegativePath = "C:\\Users\\ernes\\Desktop\\PartA\\Dataset\\aclImdb\\test\\neg";

        //diavasma twn arxeiwn tou training set
        List<Example> trainExamples = CsvFileReader.readFilesFromFolders(trainPositivePath, trainNegativePath);
        //pairnoume ton arithmo ton thetikwn kai arnitikwn reviews
        int totalPositive = CsvFileReader.getTotalPositive();
        int totalNegative = CsvFileReader.getTotalNegative();

        System.out.println("Total Train Reviews Processed: " + trainExamples.size());
        System.out.println("Total Positive: " + totalPositive);
        System.out.println("Total Negative: " + totalNegative);

        //dhmiourgia lexilogiou me xrhsh Information Gain
        List<String> vocabulary = Vocabulary.createVocabulary(k, n, m, trainExamples);

        //metatropi twn reviews se dianismata (vectorization)
        List<Integer> trainLabels = new ArrayList<>();
        List<Vector<Integer>> trainVectors = Vocabulary.vectorize(trainExamples, vocabulary, trainLabels);

        //dromologia gia ta arxeia opou tha apothikeusoume ta vectors kai ta labels
        String trainVectorFilePath = "C:\\Users\\ernes\\Desktop\\PartA\\train_vectors.txt";
        String trainLabelFilePath = "C:\\Users\\ernes\\Desktop\\PartA\\train_labels.txt";
        String featureFilePath = "C:\\Users\\ernes\\Desktop\\PartA\\features.txt";

        //apothikeusi twn dianismatwn kai labels sta arxeia
        VectorExporter.exportVectorsToFile(trainVectors, trainVectorFilePath);
        VectorExporter.exportLabelsToFile(trainLabels, trainLabelFilePath);
        VectorExporter.exportFeatureWordsToFile(vocabulary, featureFilePath);

        System.out.println("Training Data Processing Completed!\n");

        // -------------------- PROCESS TEST SET -------------------- //

        //diavasma twn arxeiwn tou test set
        List<Example> testExamples = CsvFileReader.readFilesFromFolders(testPositivePath, testNegativePath);

        System.out.println("Total Test Reviews Processed: " + testExamples.size());

        //metatropi twn test reviews se dianismata (vectorization)
        List<Integer> testLabels = new ArrayList<>();
        List<Vector<Integer>> testVectors = Vocabulary.vectorize(testExamples, vocabulary, testLabels);

        //paths gia ta arxeia twn test vectors kai labels
        String testVectorFilePath = "C:\\Users\\ernes\\Desktop\\PartA\\test_vectors.txt";
        String testLabelFilePath = "C:\\Users\\ernes\\Desktop\\PartA\\test_labels.txt";

        //apothikeusi twn test dianismatwn kai labels sta arxeia
        VectorExporter.exportVectorsToFile(testVectors, testVectorFilePath);
        VectorExporter.exportLabelsToFile(testLabels, testLabelFilePath);

        System.out.println("Test Data Processing Completed!");
    }
}
