import java.util.Comparator;

class SortByFrequency implements Comparator<Word> {
    @Override
    //sigrinei tis lekseis me vasi tin sixnotita tous
    public int compare(Word a, Word b) {
        return Integer.compare(a.getFrequency(), b.getFrequency());
    }
}

