
/**
 * Write a description of class TitleLastAndMagnitudeComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class TitleLastAndMagnitudeComparator implements Comparator<QuakeEntry> {
    
    public int compare(QuakeEntry qe1, QuakeEntry qe2) {
        String[] wordList1 = qe1.getInfo().split("\\s+");
        String lastWord1 = wordList1[wordList1.length-1];

        String[] wordList2 = qe2.getInfo().split("\\s+");
        String lastWord2 = wordList2[wordList2.length-1];

        int compValue =  lastWord1.compareTo(lastWord2);
        
        if (compValue < 0){
        	return -1;
        }
        else if (compValue > 0){
        	return 1;
        }
        else{
        	return Double.compare(qe1.getMagnitude(), qe2.getMagnitude());
        }
    }
    
}
 