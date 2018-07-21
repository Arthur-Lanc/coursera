
public class WordGram {
    private String[] myWords;
    private int myHash;

    public WordGram(String[] source, int start, int size) {
        myWords = new String[size];
        System.arraycopy(source, start, myWords, 0, size);
    }

    public String wordAt(int index) {
        if (index < 0 || index >= myWords.length) {
            throw new IndexOutOfBoundsException("bad index in wordAt "+index);
        }
        return myWords[index];
    }

    public int length(){
        // TODO: Complete this method
        return myWords.length;
    }

    public String toString(){
        String ret = "";
        // TODO: Complete this method
        for(int k=0;k<myWords.length;k++){
            ret += myWords[k];
            ret += " ";
        }
        return ret.trim();
    }

    public boolean equals(Object o) {
        WordGram other = (WordGram) o;
        // TODO: Complete this method
        if(myWords.length != other.length()){
            return false;
        }
        for(int k=0;k<myWords.length;k++){
            if(!myWords[k].equals(other.wordAt(k))){
                return false;
            }
        }
        return true;
    }

    public WordGram shiftAdd(String word) { 
        // WordGram out = new WordGram(myWords, 0, myWords.length);
        // shift all words one towards 0 and add word at the end. 
        // you lose the first word
        // TODO: Complete this method
        String[] tempWords = new String[myWords.length];
        for(int k=1;k<=myWords.length-1;k++){
            tempWords[k-1] = myWords[k];
        }
        tempWords[myWords.length-1] = word;
        WordGram out = new WordGram(tempWords, 0, myWords.length);
        return out;
    }

    public int hashCode(){
        String tempStr = this.toString();
        return tempStr.hashCode();
        
        // //it's wrong answer!!! The hashCode could be too large and out of range.
        //int ret = 0;
        //for(int k=0; k < myWords.length; k++) {
        //    ret += myWords[k].hashCode();
        //}
        //return ret;
    }

    public void testShiftAdd(){
        //String[] tempWords = {"this", "is","a","test"};
        //WordGram wg = new WordGram(tempWords, 0, tempWords.length);
        //System.out.println(wg.shiftAdd("yes"));
        System.out.println(this.shiftAdd("yes"));
    }

}