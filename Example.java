public class Example{
    private String text;
    private String label;

    public Example(String text,String label){
        //ti grafei to review
        this.text=text;    
        //label tou review, dhladh an einai positive h negative
        this.label=label;  
    }
    //getter kai setters
    public String getText(){
        return this.text;
    }

    public String getLabel(){
        return this.label;
    }

    public void setText(String text){
        this.text=text;
    }

    public void setLabel(String label){
        this.label=label;
    }

    public String toString(){
        return "Text: "+this.text+"\n"+"Label: "+this.label+"\n";
    }
}