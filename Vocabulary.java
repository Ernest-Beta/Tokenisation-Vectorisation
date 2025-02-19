import java.util.*;

public class Vocabulary{
    

    public static List<String> createVocabulary(double k, int n, int m, List<Example> examples){
		
		//xartis pou kratame tis lekseis kai tin sixnotita tous se ola ta examples	
		HashMap<String,Word> wordsInAllExamples = new HashMap<String,Word>();
																	
        for(Example example : examples){  
			//lista gia na kratame tis lekseis pou exoume dei se auto to example
			List<String> wordsInThisExample = new ArrayList<String>();  
			//parsaroume to keimeno se lekseis me diaxwrismo sta min-alfarithmitika xarakthra (px kommata, kena, teleies)
			String[] words = example.getText().split("\\W+"); 
			//an mia leksi den exei ksana emfanistei sto trexon example, tin prosthetoume
			for(String word : words){   //pare ena ena ta words auto tou Example
				if( !wordsInThisExample.contains(word)){  //an den to exoume jana dei auto to word se auto to example krata to
					wordsInThisExample.add(word);         
				}
			}
			
			String label = example.getLabel();
			
			for( String word : wordsInThisExample){    // pare mia mia tis lejeis pou brhkame
				if(!wordsInAllExamples.containsKey(word)){   // an den yparxei sto hasmap arxikopoihse thn
					wordsInAllExamples.put(word,new Word(word));
				}
				wordsInAllExamples.get(word).incrementFrequencyAndLabelCount(label);	
			}
		}
	//----------------------------------------------------------------------------------------------------------------
		
		
		//metatropi tou hashmap se lista gia taxinomisi
		List<Word> wordsInAllExamplesList = new ArrayList<Word>();
		for(Map.Entry<String,Word> entry : wordsInAllExamples.entrySet()) {
			wordsInAllExamplesList.add(entry.getValue());
		}
		//taxinomisi me vasi ti sixnotita
		Collections.sort(wordsInAllExamplesList, new SortByFrequency());  
												
		//ektupwsi twn  3 teleutaiwn leksewn gia elegxo
		System.out.println(wordsInAllExamplesList.get(0));
		System.out.println(wordsInAllExamplesList.get(1));
		System.out.println(wordsInAllExamplesList.get(2));
		System.out.println(wordsInAllExamplesList.get(wordsInAllExamplesList.size()-3));
		System.out.println(wordsInAllExamplesList.get(wordsInAllExamplesList.size()-2));
		System.out.println(wordsInAllExamplesList.get(wordsInAllExamplesList.size()-1));
		
		int size = wordsInAllExamplesList.size();
		int newk = (int) (size * k);
		System.out.println("Before cut size: "+size+"\n");;
		//afairesi twn k ligotero sixnon kai twn n pio sixnon leksewn
		if (k + n <= size) {
            wordsInAllExamplesList = new ArrayList<>(wordsInAllExamplesList.subList(newk, wordsInAllExamplesList.size() - n));
		}else{
			System.out.println("Invalid k and n values.");
		}
		System.out.println("After cut size: "+ wordsInAllExamplesList.size()+"\n");
		//ipologismos entropy gia to dataset
		int totalPositive=CsvFileReader.getTotalPositive();
		int totalNegative=CsvFileReader.getTotalNegative();
		double entropy = computeEntropy(totalPositive, totalNegative);
		//ipologismos IG gia kathe leksi
		for(Word wordObj : wordsInAllExamplesList){
			wordObj.calculateIG(entropy,totalPositive,totalNegative);
		}
		
		//taxinomisi me vasi to Information Gain (IG)
		Collections.sort(wordsInAllExamplesList, new SortByIG());
		
		

		
		//epilogi twn m leksewn me to megalytero IG
		wordsInAllExamplesList=new ArrayList<>(wordsInAllExamplesList.subList(Math.max(0, wordsInAllExamplesList.size() - m), wordsInAllExamplesList.size()));
		// to MAth.max diasfalizei oti to arxiko index ths leitourgeia subList() den einai pote katw apo to 0 
	
		//lista me tis lekseis mono (xwris ta object Word)
		List<String> justTheString = new ArrayList<String>();
		for(Word wordObj : wordsInAllExamplesList){
			justTheString.add(wordObj.getWord());
		}
		return justTheString;
		
		
	}
	//ypologismos entropy tou dataset

    private static double computeEntropy(int totalPositive, int totalNegative) {
        int totalDocs = totalPositive + totalNegative;
        double pPos = (double) totalPositive / totalDocs;
        double pNeg = (double) totalNegative / totalDocs;
        return -pPos * log2(pPos) - pNeg * log2(pNeg);
    }

    //logarithmos vasis 2
    private static double log2(double x) {
        return (x == 0) ? 0 : Math.log(x) / Math.log(2);
    }
	//metatropi twn reviews se dianysmata me vasi to lexilogio
	public static List<Vector<Integer>> vectorize(List<Example> examples, List<String> vocabulary, List<Integer> labels) {
		int vSize = vocabulary.size();
		List<Vector<Integer>> vectors = new ArrayList<>();
		labels.clear(); //kanoume clear ta labels gia na min exoun proigoumena dedomena
		System.out.println("Vocabulary size: " + vSize);
		System.out.println("Vector size: " + vSize);
	
		//metatropi tou lexilogiou se hashmap gia grigoroteri anazitisi (O(1))
		Map<String, Integer> vocabIndex = new HashMap<>();
		for (int i = 0; i < vSize; i++) {
			vocabIndex.put(vocabulary.get(i), i);
		}
	
		//gia kathe example dimiourgoume ena dianisma xaraktiristikwn
		for (Example example : examples) {
			Vector<Integer> vector = new Vector<>(Collections.nCopies(vSize, 0)); 
	
			//tokenization tou keimenou
			Set<String> words = RegexTokenizer.tokenize(example.getText());
	
			for (String word : words) {
				Integer index = vocabIndex.get(word);
				if (index != null) {
					vector.set(index, 1); //orizei to 1 an i leksi einai parousa
				}
			}
	
			vectors.add(vector);
			labels.add(example.getLabel().equalsIgnoreCase("positive") ? 1 : 0); //kataxwroume to label
		}
	
		return vectors;
	}
}		