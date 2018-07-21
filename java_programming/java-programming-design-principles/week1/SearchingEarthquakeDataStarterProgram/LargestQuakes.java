
/**
 * Find N biggest earthquakes
 * 
 * @author 
 * @version 
 */

import java.util.*;
import java.lang.Math;

public class LargestQuakes {
    public void findLargestQuakes(){
        EarthQuakeParser parser = new EarthQuakeParser();
        String source = "data/nov20quakedata.atom";
        // String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        //for (QuakeEntry qe : list) {
        //    System.out.println(qe);
        //}
        System.out.println("read data for "+list.size());

        //int maxMagIdx = indexOfLargest(list);
        //System.out.printf("location %d and has magnitude %3.2f",maxMagIdx,list.get(maxMagIdx).getMagnitude());
        list = getLargest(list,50);
        for (QuakeEntry qe : list) {
            System.out.println(qe);
        }
    }

    public int indexOfLargest(ArrayList<QuakeEntry> quakeData){
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        // TO DO
        int maxMagIdx = 0;
        for (int k=0;k<copy.size();k++) {
            QuakeEntry qe = copy.get(k);
            if (qe.getMagnitude() > copy.get(maxMagIdx).getMagnitude()){
                maxMagIdx = k;
            }
        }

        return maxMagIdx;
    }
        
    public ArrayList<QuakeEntry> getLargest(ArrayList<QuakeEntry> quakeData,int howMany){
        ArrayList<QuakeEntry> copy = new ArrayList<QuakeEntry>(quakeData);
        ArrayList<QuakeEntry> ret = new ArrayList<QuakeEntry>();
        // TO DO
        int times = Math.min(howMany,copy.size());
        for(int x=0;x<times;x++){
            int maxMagIdx = indexOfLargest(copy);
            ret.add(copy.get(maxMagIdx));
            copy.remove(maxMagIdx);
        }

        return ret;
    }
    
}
