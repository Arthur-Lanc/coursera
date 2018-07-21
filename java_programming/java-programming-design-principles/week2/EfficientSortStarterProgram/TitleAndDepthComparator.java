
/**
 * Write a description of class TitleAndDepthComparator here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class TitleAndDepthComparator implements Comparator<QuakeEntry> {
    
    public int compare(QuakeEntry qe1, QuakeEntry qe2) {
        int compValue =  qe1.getInfo().compareTo(qe2.getInfo());
        
        if (compValue < 0){
        	return -1;
        }
        else if (compValue > 0){
        	return 1;
        }
        else{
        	return Double.compare(qe1.getDepth(), qe2.getDepth());
        }
    }
    
}
 