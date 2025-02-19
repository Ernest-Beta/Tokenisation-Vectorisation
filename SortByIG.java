import java.util.Comparator;

class SortByIG implements Comparator<Word> {
    @Override
    //sigrinei tis lekseis me vasi to information gain tous
    public int compare(Word a, Word b) {
        return Double.compare(a.getIg(),b.getIg());
    }
}