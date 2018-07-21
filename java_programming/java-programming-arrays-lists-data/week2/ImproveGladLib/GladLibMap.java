
/**
 * Write a description of GladLibMap here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class GladLibMap {
    // private ArrayList<String> adjectiveList;
    // private ArrayList<String> nounList;
    // private ArrayList<String> colorList;
    // private ArrayList<String> countryList;
    // private ArrayList<String> nameList;
    // private ArrayList<String> animalList;
    // private ArrayList<String> timeList;
    // private ArrayList<String> verbList;
    // private ArrayList<String> fruitList;
    private ArrayList<String> trackList;
    private ArrayList<String> usedCategoryList;
    private HashMap<String,ArrayList<String>> myMap;
    private int replaced_word_num;
    
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLibMap(){
        myMap = new HashMap<String,ArrayList<String>>();        
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }
    
    public GladLibMap(String source){
        myMap = new HashMap<String,ArrayList<String>>();    
        initializeFromSource(source);
        myRandom = new Random();
    }
    
    private void initializeFromSource(String source) {
        // adjectiveList= readIt(source+"/adjective.txt"); 
        // nounList = readIt(source+"/noun.txt");
        // colorList = readIt(source+"/color.txt");
        // countryList = readIt(source+"/country.txt");
        // nameList = readIt(source+"/name.txt");      
        // animalList = readIt(source+"/animal.txt");
        // timeList = readIt(source+"/timeframe.txt");     
        // verbList = readIt(source+"/verb.txt");
        // fruitList = readIt(source+"/fruit.txt");
        String[] category_array = {"adjective","noun","color","country","name","animal","timeframe","verb","fruit"};
        for (String category:category_array){
            ArrayList<String> temp_al = readIt(source+"/"+category+".txt");
            //System.out.println(category);
            //System.out.println(temp_al);
            //System.out.println("-------------");
            myMap.put(category,temp_al);
        }
        trackList = new ArrayList<String>();
        usedCategoryList = new ArrayList<String>();
        replaced_word_num = 0;
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        // if (label.equals("country")) {
        //     return randomFrom(countryList);
        // }
        // if (label.equals("color")){
        //     return randomFrom(colorList);
        // }
        // if (label.equals("noun")){
        //     return randomFrom(nounList);
        // }
        // if (label.equals("name")){
        //     return randomFrom(nameList);
        // }
        // if (label.equals("adjective")){
        //     return randomFrom(adjectiveList);
        // }
        // if (label.equals("animal")){
        //     return randomFrom(animalList);
        // }
        // if (label.equals("timeframe")){
        //     return randomFrom(timeList);
        // }
        // if (label.equals("verb")){
        //     return randomFrom(verbList);
        // }
        // if (label.equals("fruit")){
        //     return randomFrom(fruitList);
        // }
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        return randomFrom(myMap.get(label));
        // return "**UNKNOWN**";
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub;
        // String sub = getSubstitute(w.substring(first+1,last));

        while(true){
            sub = getSubstitute(w.substring(first+1,last));
            if(!trackList.contains(sub)){
                break;
            }
        }

        if (!usedCategoryList.contains(w.substring(first+1,last))){
            usedCategoryList.add(w.substring(first+1,last));
            //System.out.println(w.substring(first+1,last));
        }

        trackList.add(sub);
        replaced_word_num = replaced_word_num + 1;

        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public void makeStory(){
        trackList.clear();
        usedCategoryList.clear();
        replaced_word_num = 0;
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);

        System.out.println("\n");
        System.out.println("total number of words that were replaced: "+replaced_word_num);

        System.out.println("");
        System.out.println("total number of words that were possible to pick from: "+totalWordsInMap());

        System.out.println("");
        System.out.println("total number of words in the ArrayLists of the categories that were used for a particular GladLib: "+totalWordsConsidered());
    }

    private int totalWordsInMap (){    
        int result = 0;
        for (String category : myMap.keySet()) {
            ArrayList<String> temp_al = myMap.get(category);
            result = result + temp_al.size();
        }
        return result;
    }
    
    private int totalWordsConsidered (){
        int result = 0;
        for (String category : usedCategoryList) {
            if (!category.equals("number")){
                ArrayList<String> temp_al = myMap.get(category);
                result = result + temp_al.size();
            }
        }
        return result;
    }


}
