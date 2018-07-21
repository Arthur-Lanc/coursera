
/**
 * Write a description of class MarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class MarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    
    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
    }
    
/*    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-2);  // random word to start with
        String key1 = myText[index];
        String key2 = myText[index+1];
        sb.append(key1);
        sb.append(" ");
        sb.append(key2);
        sb.append(" ");
        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key1,key2);
            //System.out.println("key: "+key);
            //System.out.println("follows: "+follows);
            //System.out.println("");
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key1 = key2;
            key2 = next;
        }
        
        return sb.toString().trim();
    }*/

    public String getRandomText(int numWords){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with

        String[] tempWords = new String[myOrder];
        for(int k=0;k<myOrder;k++){
            tempWords[k] = myText[index+k];
        }
        WordGram key = new WordGram(tempWords, 0, tempWords.length);
        sb.append(key.toString());
        sb.append(" ");

        for(int k=0; k < numWords-1; k++){
            ArrayList<String> follows = getFollows(key);
            //System.out.println("key: "+key);
            //System.out.println("follows: "+follows);
            //System.out.println("");
            if (follows.size() == 0) {
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            sb.append(" ");
            key = key.shiftAdd(next);
        }
        
        return sb.toString().trim();
    }
    
/*    private ArrayList<String> getFollows(String key) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while (pos < myText.length){
            int start = indexOf(myText,key,pos);
            if (start == -1){
                break;
            }
            //if (start + key.length() >= myText.length-1){
            if (start + 1 > myText.length-1){    
                break;
            }
            String next = myText[start+1];
            follows.add(next);
            // pos = start+key.length();
            pos = start+1;
        }
        return follows;
    }*/

/*    private ArrayList<String> getFollows(String key1,String key2) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while (pos < myText.length){
            int start = indexOf(myText,key1,key2,pos);
            if (start == -1){
                break;
            }
            //if (start + key.length() >= myText.length-1){
            if (start + 2 > myText.length-1){    
                break;
            }
            String next = myText[start+2];
            follows.add(next);
            // pos = start+key.length();
            pos = start+2;
        }
        return follows;
    }*/

    private ArrayList<String> getFollows(WordGram kGram) {
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while (pos < myText.length){
            int start = indexOf(myText,kGram,pos);
            if (start == -1){
                break;
            }
            if (start + kGram.length() > myText.length-1){    
                break;
            }
            String next = myText[start+kGram.length()];
            follows.add(next);
            pos = start+kGram.length();
        }
        return follows;
    }

/*    private int indexOf(String[] words, String target1,String target2, int start){
        for(int k=start;k<words.length-1;k++){
            if (words[k].equals(target1) && words[k+1].equals(target2)){
                return k;
            }
        }
        return -1;
    }*/

    private int indexOf(String[] words, WordGram target, int start){
        String[] tempWords = new String[target.length()];

        for(int k=start;k<=words.length-target.length();k++){
            for(int x=0;x<tempWords.length;x++){
                tempWords[x] = words[k+x];
            }
            WordGram wg = new WordGram(tempWords, 0, tempWords.length);

            if(target.equals(wg)){
                return k;
            }
        }
        return -1;
    }

/*    public void testGetFollows(){
        String text = "this is just a test yes this is a simple test";
        myText = text.split("\\s+");
        System.out.println(getFollows("this","is"));
        System.out.println(getFollows("just","a"));
    }*/

/*    public void testIndexOf(){
        String text = "this is just a test yes this is a simple test";
        String[] words = text.split("\\s+");
        System.out.println(indexOf(words,"this",0));
        System.out.println(indexOf(words,"this",3));
        System.out.println(indexOf(words,"frog",0));
        System.out.println(indexOf(words,"frog",5));
        System.out.println(indexOf(words,"simple",2));
        System.out.println(indexOf(words,"test",5));
    }*/

}
