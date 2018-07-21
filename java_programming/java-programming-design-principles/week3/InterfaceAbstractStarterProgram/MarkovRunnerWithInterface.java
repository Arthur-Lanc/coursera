
/**
 * Write a description of class MarkovRunner here.
 * 
 * @author Duke Software
 * @version 1.0
 */

import edu.duke.*; 

public class MarkovRunnerWithInterface {
    public void runModel(IMarkovModel markov, String text, int size, int seed) {
        markov.setTraining(text);
        markov.setRandom(seed);
        System.out.println("running with " + markov);
        for(int k=0; k < 3; k++){
            String st= markov.getRandomText(size);
            printOut(st);
        }
    }
    
    public void runMarkov() {
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 200;
        
        //MarkovZero mz = new MarkovZero();
        //runModel(mz, st, size, 1024);
    
        //MarkovOne mOne = new MarkovOne();
        //runModel(mOne, st, size, 88);
        
        //MarkovModel mThree = new MarkovModel(3);
        //runModel(mThree, st, size, 88);
        
        //MarkovFour mFour = new MarkovFour();
        //runModel(mFour, st, size, 88);

        EfficientMarkovModel emk = new EfficientMarkovModel(5);
        runModel(emk, st, size, 531);
    }

    public void testHashMap(){
        String st = "yes-this-is-a-thin-pretty-pink-thistle";
        int size = 50;

        EfficientMarkovModel emk = new EfficientMarkovModel(2);
        runModel(emk, st, size, 42);
    }

    public void compareMethods(){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        int size = 1000;

        long startTime = System.nanoTime();
        MarkovModel mThree = new MarkovModel(2);
        runModel(mThree, st, size, 42);
        long estimatedTime = System.nanoTime() - startTime;
        double seconds = (double)estimatedTime / 1000000000.0;
        System.out.println("estimatedTime: "+estimatedTime);
        System.out.println("seconds: "+seconds);
        System.out.println("");

        startTime = System.nanoTime();
        EfficientMarkovModel emk = new EfficientMarkovModel(2);
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
