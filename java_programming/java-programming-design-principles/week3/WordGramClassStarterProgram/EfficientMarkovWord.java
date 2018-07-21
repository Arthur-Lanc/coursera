
/**
 * Write a description of class EfficientMarkovWord here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientMarkovWord implements IMarkovModel {
    private String[] myText;
    private Random myRandom;
    private int myOrder;
    private HashMap<WordGram, ArrayList<String>> map;
    
    public EfficientMarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
        map = new HashMap<WordGram, ArrayList<String>>();
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void setTraining(String text){
        myText = text.split("\\s+");
        buildMap();
        printHashMapInfo();
    }

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

/*    public void buildMap(){
        String[] tempWords = new String[myOrder];
        for(int x = 0; x+myOrder <= myText.length; x++){
            System.arraycopy(myText, x, tempWords, 0, myOrder);
            //for(int k=0;k<myOrder;k++){
            //    tempWords[k] = myText[x+k];
            //}
            WordGram kGram = new WordGram(tempWords, 0, tempWords.length);

            if (!map.containsKey(kGram)){
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
                
                map.put(kGram, follows);
            }
        }
    }*/

    public void buildMap(){
        String[] tempWords = new String[myOrder];
        for(int x = 0; x+myOrder <= myText.length; x++){

            System.arraycopy(myText, x, tempWords, 0, myOrder);
            WordGram kGram = new WordGram(tempWords, 0, tempWords.length);

            if(!map.containsKey(kGram)){
                ArrayList<String> follows = new ArrayList<String>();
                if(x+kGram.length() < myText.length){
                    String next = myText[x+kGram.length()];
                    follows.add(next);
                }
                map.put(kGram, follows);
            }
            else{
                if(x+kGram.length() < myText.length){
                    ArrayList<String> follows = map.get(kGram);
                    String next = myText[x+kGram.length()];
                    follows.add(next);
                    map.put(kGram, follows);
                }
            }
        }
    }

    public ArrayList<String> getFollows(WordGram key){
        ArrayList<String> follows = new ArrayList<String>();

        if (map.containsKey(key)){
            follows = map.get(key);
        }

        return follows;
    }

    public void printHashMapInfo(){
        //System.out.println("HashMap: "+map);
        System.out.println("It has "+map.size()+" keys in the HashMap");

        int maxSize = 0;
        for (WordGram s : map.keySet()) {
            ArrayList<String> follows = map.get(s);
            if (follows.size() > maxSize){
                maxSize = follows.size();
            }
        } 
        System.out.println("The maximum number of elements following a key is "+maxSize);
        
        int maxKeyNum = 0;
        ArrayList<WordGram> keys = new ArrayList<WordGram>();
        for (WordGram s : map.keySet()) {
            ArrayList<String> follows = map.get(s);
            if (follows.size() == maxSize){
                keys.add(s);
                maxKeyNum += 1;
            }
        } 
        System.out.println("There is "+maxKeyNum+" key that has two follow items. "+"Keys that have the largest ArrayList are: "+keys);
    }

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

}
