
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.ArrayList;

public class CharactersInPlay {
    private ArrayList<String> myCharacters;
    private ArrayList<Integer> myFreqs;

    public CharactersInPlay() {
        myCharacters = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }

    public void update (String person){
        int index = myCharacters.indexOf(person);
        if (index == -1){
            myCharacters.add(person);
            myFreqs.add(1);
        }
        else {
            int freq = myFreqs.get(index);
            myFreqs.set(index,freq+1);
        }
    }

    public void findAllCharacters (){
        myCharacters.clear();
        myFreqs.clear();
        FileResource resource = new FileResource();

        for(String lines : resource.lines()){
            int index = lines.indexOf(".");
            if (index != -1){
                String person = lines.substring(0,index);
                update(person);
            }
        }
    }

    public void tester(){
        findAllCharacters();
        System.out.println("Number of unique characters: "+myCharacters.size());

        //for (int k=0;k<myCharacters.size();k++){
        //    System.out.println(myCharacters.get(k)+" "+myFreqs.get(k));
        //}

        //charactersWithNumParts(30,999999999);
        //charactersWithNumParts(200,999999999);

        // int max_freq = 0;
        // int max_index = 0;
        // for (int k=0;k<myCharacters.size();k++){
        //     if(myFreqs.get(k) > max_freq){
        //         max_freq = myFreqs.get(k);
        //         max_index = k;
        //     }
        // }
        // System.out.println(myCharacters.get(max_index)+" "+myFreqs.get(max_index));
        // System.out.println("--------------------------");
        // charactersWithNumParts(70,999999999);
        
        System.out.println("--------------------------");
        charactersWithNumParts(10,15);
    }

    public void charactersWithNumParts(int num1,int num2){
        for (int k=0;k<myCharacters.size();k++){
            if(myFreqs.get(k)>=num1 && myFreqs.get(k)<=num2){
                System.out.println(myCharacters.get(k)+" "+myFreqs.get(k));
            }
        }
    }


}
