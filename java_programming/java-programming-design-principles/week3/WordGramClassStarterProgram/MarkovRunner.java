
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runMarkov() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        //MarkovWordOne markovWord = new MarkovWordOne(); 
        //runModel(markovWord, st, 200); 
        
        //MarkovWord markovWord = new MarkovWord(5); 
        //runModel(markovWord, st, 120,844); 
        
        EfficientMarkovWord markovWord = new EfficientMarkovWord(2); 
        runModel(markovWord, st, 120,65); 
    } 

    public void runMarkovWord() { 
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        MarkovWord markovWord = new MarkovWord(3); 
        runModel(markovWord, st, 120,643); 
    } 

    public void testHashMap(){
        String st = "this is a test yes this is really a test";
        //String st = "this is a test yes this is really a test yes a test this is wow";
        
        st = st.replace('\n', ' '); 
        int size = 50;

        EfficientMarkovWord emk = new EfficientMarkovWord(2);
        runModel(emk, st, size, 42);
    }

    public void compareMethods(){
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        int size = 100;

        long startTime = System.nanoTime();
        MarkovWord mThree = new MarkovWord(2);
        runModel(mThree, st, size, 42);
        long estimatedTime = System.nanoTime() - startTime;
        double seconds = (double)estimatedTime / 1000000000.0;
        System.out.println("estimatedTime: "+estimatedTime);
        System.out.println("seconds: "+seconds);
        System.out.println("");

        startTime = System.nanoTime();
        EfficientMarkovWord emk = new EfficientMarkovWord(2);
        runModel(emk, st, size, 42);
        estimatedTime = System.nanoTime() - startTime;
        seconds = (double)estimatedTime / 1000000000.0;
        System.out.println("estimatedTime: "+estimatedTime);
        System.out.println("seconds: "+seconds);
    }

    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 

}
