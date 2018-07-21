import java.util.Random;
import edu.duke.*;
import java.util.*;

public class Tester {
    public void testGetFollows (){
        String st = "this is a test yes this is a test.";
        MarkovOne markov = new MarkovOne();
        markov.setTraining(st);
        ArrayList<String> list1 = markov.getFollows("t");
        ArrayList<String> list2 = markov.getFollows("e");
        ArrayList<String> list3 = markov.getFollows("es");

        ArrayList<String> list4 = markov.getFollows(".");
        ArrayList<String> list5 = markov.getFollows("t.");

        System.out.println(list1);
        System.out.println("size: "+list1.size());
        System.out.println();

        System.out.println(list2);
        System.out.println("size: "+list2.size());
        System.out.println();

        System.out.println(list3);
        System.out.println("size: "+list3.size());
        System.out.println();

        System.out.println(list4);
        System.out.println("size: "+list4.size());
        System.out.println();

        System.out.println(list5);
        System.out.println("size: "+list5.size());
        System.out.println();
    }

    public void testGetFollowsWithFile (){
        FileResource fr = new FileResource();
        String st = fr.asString();
        st = st.replace('\n', ' ');
        MarkovOne markov = new MarkovOne();
        markov.setTraining(st);
        ArrayList<String> list1 = markov.getFollows("he");

        System.out.println(list1);
        System.out.println("size: "+list1.size());
        System.out.println();
    }
}