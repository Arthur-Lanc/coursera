
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;
import java.io.*;

public class WordsInFiles {
    private HashMap<String,ArrayList<String>> word_filenames_hm;

    public WordsInFiles(){
        word_filenames_hm = new HashMap<String,ArrayList<String>>();        
    }

    private void addWordsFromFile(File f){
        String filename = f.getName();
        FileResource fr = new FileResource(f);

        for (String word : fr.words()) {
            if(word_filenames_hm.containsKey(word)){
                ArrayList<String> temp_al = word_filenames_hm.get(word);
                if(!temp_al.contains(filename)){
                    temp_al.add(filename);
                    word_filenames_hm.put(word, temp_al);
                }
            }
            else{
                //word_filenames_hm.get(word).add(filename);
                ArrayList<String> temp_al = new ArrayList<String>();
                temp_al.add(filename);
                word_filenames_hm.put(word, temp_al);
            }
        }
    }

    public void buildWordFileMap(){
        word_filenames_hm.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }

    public int maxNumber(){
        int max_number = 0;
        for (String s : word_filenames_hm.keySet()) {
            int count = word_filenames_hm.get(s).size();
            if(count > max_number){
                max_number = count;
            }
        }
        return max_number;
    }

    public ArrayList<String> wordsInNumFiles(int number){
        ArrayList<String> result_al = new ArrayList<String>();
        for(String s : word_filenames_hm.keySet()){
            if(word_filenames_hm.get(s).size() == number){
                result_al.add(s);
            }
        }
        return result_al;
    }

    public void printFilesIn(String word){
        ArrayList<String> temp_al = word_filenames_hm.get(word);
        for (String filename : temp_al) {
            System.out.println(filename);
        }
    }

    public void tester(){
        buildWordFileMap();

        int max_number = maxNumber();
        System.out.println("the maximum number of files: "+max_number);

        // System.out.println("all the words that are in the maximum number of files: ");
        // ArrayList<String> temp_al = wordsInNumFiles(max_number);
        // for (String word : temp_al) {
        //     System.out.println(word);
        // }

        // for (String word : temp_al) {
        //     System.out.println("\""+word+"\" appears in the files: ");
        //     printFilesIn(word);
        // }

        // (optional) 
        //System.out.println("");
        //System.out.println("complete map: ");
        //for(String word : word_filenames_hm.keySet()){
        //    System.out.println("\""+word+"\" appears in the files: ");
        //    printFilesIn(word);
        //}

        // int count1 = 0;
        // for(String word : word_filenames_hm.keySet()){
        //     if(word_filenames_hm.get(word).size() == 7){
        //         count1 = count1 + 1;
        //     }
        // }
        // System.out.println("count1: "+count1);

        // int count2 = 0;
        // for(String word : word_filenames_hm.keySet()){
        //     if(word_filenames_hm.get(word).size() == 4){
        //         count2 = count2 + 1;
        //     }
        // }
        // System.out.println("count2: "+count2);

        System.out.println("-----------------------");
        printFilesIn("sea");
        System.out.println("-----------------------");
        printFilesIn("tree");
    }


}
