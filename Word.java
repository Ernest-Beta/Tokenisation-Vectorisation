public class Word {
	
	private String word;
	private int frequency;   //arithmos fores pou emfanizetai i leksi sta training examples
	private int pCount;      //arithmos thetikwn paradeigmatwn pou periexoun tin leksi
	private int nCount;      //arithmos arnitikwn paradeigmatwn pou periexoun tin leksi
	private double ig;       //information gain tis leksis
	
	public Word(String word) {
		this.word = word;
		this.frequency = 0;
		this.pCount = 0;
		this.nCount = 0;
		this.ig = 0;
	}
	
	public void incrementFrequencyAndLabelCount(String label) {
		this.increaseFrequency();
		if (label.equals("positive")) {
			this.increasePCount();
		} else {
			this.increaseNCount();
		}
	}
	
	public double getIg() {
		return this.ig;
	}

	//ypologizei to information gain tis leksis, xreiazetai to entropy tou dataset ws eisodo
	public void calculateIG(double datasetEntropy, int totalPositive, int totalNegative) {
		int totalDocs = totalPositive + totalNegative;
		int posWithW = this.pCount;
		int negWithW = this.nCount;
		int posWithoutW = totalPositive - posWithW;
		int negWithoutW = totalNegative - negWithW;

		//ypologismos conditional entropy
		double conditionalEntropy = computeConditionalEntropy(posWithW, negWithW, posWithoutW, negWithoutW, totalPositive, totalNegative);

		//ypologismos information gain
		this.ig = datasetEntropy - conditionalEntropy;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public int getFrequency() {
		return this.frequency;
	}
	
	public void increaseFrequency() {
		this.frequency++;
	}
	
	public void increasePCount() {
		this.pCount++;
	}
	
	public int getPCount() {
		return this.pCount;
	}
	
	public void increaseNCount() {
		this.nCount++;
	}
	
	public int getNCount() {
		return this.nCount;
	}

	@Override
	public String toString() {
		return "Word: " + this.word + "\n" +
				"Frequency: " + this.frequency + "\n" +
				"Positive: " + this.pCount + "\n" +
				"Negative: " + this.nCount + "\n" +
				"IG: " + this.ig + "\n";
	}

	//ypologismos conditional entropy gia mia leksi
	private double computeConditionalEntropy(int posW, int negW, int posNoW, int negNoW, int totalPos, int totalNeg) {
		int totalW = posW + negW, totalNoW = posNoW + negNoW;
		double H_W = computeEntropy(posW, negW);
		double H_NoW = computeEntropy(posNoW, negNoW);
		return (totalW * H_W + totalNoW * H_NoW) / (totalPos + totalNeg);
	}

	//ypologismos entropy
	private static double computeEntropy(int totalPos, int totalNeg) {
		int totalDocs = totalPos + totalNeg;
		double pPos = (double) totalPos / totalDocs;
		double pNeg = 1.0 - pPos;
		return -pPos * log2(pPos) - pNeg * log2(pNeg);
	}

	//logarithmos vasis 2
	private static double log2(double x) {
		return (x == 0) ? 0 : Math.log(x) / Math.log(2);
	}
}
