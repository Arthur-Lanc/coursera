
/**
 * Write a description of class EfficientMarkovModel here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import java.util.Random;
import edu.duke.*;
import java.util.*;

public class EfficientMarkovModel extends AbstractMarkovModel{
/*    private String myText;
    private Random myRandom;*/
    private int myPredictNum;
    private HashMap<String, ArrayList<String>> map;
    
    public EfficientMarkovModel(int n) {
        myRandom = new Random();
        myPredictNum = n;
        map = new HashMap<String, ArrayList<String>>();
    }
    
    public void setRandom(int seed){
        myRandom = new Random(seed);
    }
    
    public void setTraining(String s){
        myText = s.trim();
        buildMap();
        printHashMapInfo();
    }
    
/*    public String getRandomText(int numChars){
        if (myText == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for(int k=0; k < numChars; k++){
            int index = myRandom.nextInt(myText.length());
            sb.append(myText.charAt(index));
        }
        
        return sb.toString();
    }*/

    public String getRandomText(int numChars){
        StringBuilder sb = new StringBuilder();
        int index = myRandom.nextInt(myText.length()-myPredictNum);
        String key = myText.substring(index, index+myPredictNum);
        sb.append(key);

        for(int k=0; k < numChars-myPredictNum; k++){
            ArrayList<String> follows = getFollows(key);
            if (follows.size() == 0){
                break;
            }
            index = myRandom.nextInt(follows.size());
            String next = follows.get(index);
            sb.append(next);
            // key = next;
            key = key.substring(1) + next;
        }
        
        return sb.toString();
    }

    public void buildMap(){
        for(int x = 0; x+myPredictNum <= myText.length(); x++){
            String key = myText.substring(x,x+myPredictNum);

            if (!map.containsKey(key)){
                ArrayList<String> follows = new ArrayList<String>();
                int pos = 0;
                while (pos < myText.length()){
                    int start = myText.indexOf(key,pos);
                    if (start == -1){
                        break;
                    }
                    //if (start + key.length() >= myText.length()-1){
                     if (start + key.length() > myText.length()-1){
                        break;
                    }
                    String next = myText.substring(start+key.length(),start+key.length()+1);
                    follows.add(next);
                    pos = start+key.length();
                }
                map.put(key, follows);
            }

        }
        
    }

    public ArrayList<String> getFollows(String key){
        ArrayList<String> follows = new ArrayList<String>();

        if (map.containsKey(key)){
            follows = map.get(key);
        }

        return follows;
    }

/*    public ArrayList<String> getFollows(String key){
        ArrayList<String> follows = new ArrayList<String>();
        int pos = 0;
        while (pos < myText.length()){
            int start = myText.indexOf(key,pos);
            if (start == -1){
                break;
            }
            if (start + key.length() > myText.length()-1){
                break;
            }
            String next = myText.substring(start+key.length(),start+key.length()+1);
            follows.add(next);
            pos = start+key.length();
        }
        return follows;
    }*/

    public void printHashMapInfo(){
        // System.out.println("HashMap: "+map);
        System.out.println("It has "+map.size()+" keys in the HashMap");

        int maxSize = 0;
        for (String s : map.keySet()) {
            ArrayList<String> follows = map.get(s);
            if (follows.size() > maxSize){
                maxSize = follows.size();
            }
        } 
        System.out.println("The maximum number of keys following a key is "+maxSize);
        
        ArrayList<String> keys = new ArrayList<String>();
        for (String s : map.keySet()) {
            ArrayList<String> follows = map.get(s);
            if (follows.size() == maxSize){
                keys.add(s);
            }
        } 
        System.out.println("Keys that have the largest ArrayList are: "+keys);
    }

    public String toString(){
        return String.format("EfficientMarkovModel of order %d", myPredictNum);
    }
}
