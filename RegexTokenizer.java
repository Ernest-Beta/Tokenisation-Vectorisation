import java.util.*;
import java.util.regex.*;

public class RegexTokenizer {

    //lista me lekseis arnisis (negation)
    private static final Set<String> NEGATIONS = new HashSet<>(Arrays.asList(
    "not", "never", "no", "without", "hardly", "barely", "scarcely",
    "none", "nothing", "nobody", "neither", "nor", "lack",
    "can't", "cannot", "don't", "doesn't", "didn't", "won't",
    "wouldn't", "isn't", "aren't", "wasn't", "weren't", "ain't",
    "couldn't", "shouldn't", "haven't", "hadn't", "needn't"
    ));

    //lista me lekseis emfasis (intensifiers)
    private static final Set<String> INTENSIFIERS = new HashSet<>(Arrays.asList(
        "very", "so", "extremely", "too", "super",
        "really", "truly", "absolutely", "highly", "completely",
        "totally", "fully", "deeply", "greatly", "incredibly",
        "remarkably", "significantly", "tremendously", "hugely",
        "immensely", "unbelievably", "wildly", "ridiculously",
        "insanely", "quite"
    ));
    //lista me lekseis pou exoun synaisthimatiki xromatiki (sentiment words)
    private static final Set<String> SENTIMENT_WORDS = new HashSet<>(Arrays.asList(
        // Positive words
        "good", "great", "excellent", "awesome", "amazing", "fantastic",
        "wonderful", "incredible", "marvelous", "fabulous", "outstanding",
        "superb", "impressive", "lovely", "delightful", "brilliant", "perfect",

        // Negative words
        "bad", "terrible", "poor", "awful", "horrible", "dreadful",
        "disgusting", "abysmal", "atrocious", "lousy", "pathetic",
        "nasty", "unpleasant", "disappointing", "horrendous", "sad"
    ));

    // tokenize to input keimeno me:
    // 1) xrhsimopoihsh regex gia na vroume lexeis + proairetiko meros apostrofou (case-insensitive).
    // 2) metatroph twn tokens se mikra grammata.
    // 3) sygxwnevsh sygkekrimenwn pattern token (arnhtika + enisxytika + synaisthimatika, klp.) se ena monadiko token.
    // 4) epistrofh enos Set apo monadika tokens.
    public static Set<String> tokenize(String text) {
        //xrisimopoioume regex gia na vroume lekseis kai pithana apostrofa
        //motivo : ena h perissotera grammata/psifia, proeretika akolouthomeno apo grammata/pshfio.
        Pattern pattern = Pattern.compile("[a-zA-Z0-9]+('[a-zA-Z0-9]+)?");
        Matcher matcher = pattern.matcher(text);

        //apothikeush antistoiximenwn tokens (gia diatirish ths seiras)
        List<String> rawTokens = new ArrayList<>();
        while (matcher.find()) {
            rawTokens.add(matcher.group().toLowerCase());
        }

        //kalei ti methodo gia na enosei oses lekseis prepei
        List<String> mergedTokens = mergeTokens(rawTokens);

        //epistrefei ena set me monadikes lekseis
        return new HashSet<>(mergedTokens);
    }

    /**
     * sygxwneuei tokens me vash:
     *  - arnisi + enisxutiko + sunesthima  ("not so good" -> "not_so_good")
     *  - arnisi + sunesthima               ("no good" -> "no_good")
     *  - enisxutiko + epomenh leksi            ("very entertaining" -> "very_entertaining")
     * 
     */
    private static List<String> mergeTokens(List<String> tokens) {
        List<String> merged = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            String current = tokens.get(i);

            //elegxei arnisi + emfasi + synaisthimatiki leksi ( "not so good" -> "not_so_good")
            if (i + 2 < tokens.size()
                    && NEGATIONS.contains(current)
                    && INTENSIFIERS.contains(tokens.get(i + 1))
                    && SENTIMENT_WORDS.contains(tokens.get(i + 2))) {

                merged.add(current + "_" + tokens.get(i + 1) + "_" + tokens.get(i + 2));
                i += 2; //skip epomena 2 tokens

            //elegxei arnisi + synaisthimatiki leksi ( "no good" -> "no_good")
            } else if (i + 1 < tokens.size()
                    && NEGATIONS.contains(current)
                    && SENTIMENT_WORDS.contains(tokens.get(i + 1))) {

                merged.add(current + "_" + tokens.get(i + 1));
                i++; //skip epomeno token

            //elegxei emfasi + epomeni leksh ("very entertaining" -> "very_entertaining")
            //    (an theleis na sundiastei mono o enisxuths kai to sunesthima tote des to SENTIMENT_WORDS.contains(tokens.get(i+1)))
            } else if (i + 1 < tokens.size()
                    && INTENSIFIERS.contains(current)) {

                merged.add(current + "_" + tokens.get(i + 1));
                i++; // skip epomeno token 

            //alliws kratame tin leksh opos einai
            } else {
                merged.add(current);
            }
        }

        return merged;
    }

    
}
