import java.util.*;
import edu.duke.*;

public class EarthQuakeClient2 {
    public EarthQuakeClient2() {
        // TODO Auto-generated constructor stub
    }

    public ArrayList<QuakeEntry> filter(ArrayList<QuakeEntry> quakeData, Filter f) { 
        ArrayList<QuakeEntry> answer = new ArrayList<QuakeEntry>();
        for(QuakeEntry qe : quakeData) { 
            if (f.satisfies(qe)) { 
                answer.add(qe); 
            } 
        } 
        
        return answer;
    } 

    public void quakesWithFilter() { 
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);         
        System.out.println("read data for "+list.size()+" quakes");

        // Filter f = new MinMagFilter(4.0); 
        // ArrayList<QuakeEntry> m7  = filter(list, f); 
        // for (QuakeEntry qe: m7) { 
        //     System.out.println(qe);
        // } 

        // Filter f = new MagnitudeFilter(4.0,5.0,"Magnitude"); 
        // Filter f2 = new DepthFilter(-35000.0,-12000.0,"Depth"); 
        // list  = filter(list, f); 
        // list  = filter(list, f2); 
        // for (QuakeEntry qe: list) { 
        //     System.out.println(qe);
        // } 
        //System.out.println("Found "+list.size()+" quakes that match that criteria");

        //Location city =  new Location(35.42, 139.43);
        //Filter f = new DistanceFilter(city,10000000,"Distance"); 
        //Filter f2 = new PhraseFilter("end","Japan","Phrase"); 
        //list  = filter(list, f); 
        //list  = filter(list, f2); 
        //for (QuakeEntry qe: list) { 
        //    System.out.println(qe);
        //} 
        //System.out.println("Found "+list.size()+" quakes that match that criteria");
        
        //Location city =  new Location(39.7392, -104.9903);
        //Filter f = new DistanceFilter(city,1000000,"Distance"); 
        //Filter f2 = new PhraseFilter("end","a","Phrase"); 
        //list  = filter(list, f); 
        //list  = filter(list, f2); 
        //for (QuakeEntry qe: list) { 
        //    System.out.println(qe);
        //} 
        //System.out.println("Found "+list.size()+" quakes that match that criteria");

         Filter f = new MagnitudeFilter(3.5,4.5,"Magnitude"); 
         Filter f2 = new DepthFilter(-55000.0,-20000.0 ,"Depth"); 
         list  = filter(list, f); 
         list  = filter(list, f2); 
         for (QuakeEntry qe: list) { 
             System.out.println(qe);
         } 
        System.out.println("Found "+list.size()+" quakes that match that criteria");

    }

    public void testMatchAllFilter(){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);   
        // for (QuakeEntry qe : list) {
        //     System.out.println(qe);
        // }
        System.out.println("read data for "+list.size()+" quakes");

        MatchAllFilter maf = new MatchAllFilter(); 
        Filter f = new MagnitudeFilter(1.0,4.0,"Magnitude"); 
        Filter f2 = new DepthFilter(-180000.0,-30000.0,"Depth"); 
        Filter f3 = new PhraseFilter("any","o","Phrase"); 
        maf.addFilter(f);
        maf.addFilter(f2);
        maf.addFilter(f3);
        list = filter(list, maf);
        for (QuakeEntry qe: list) { 
            System.out.println(qe);
        } 
        
        System.out.println("Filters used are: "+maf.getName());
        System.out.println("Found "+list.size()+" quakes that match that criteria");
    }

    public void testMatchAllFilter2(){
        EarthQuakeParser parser = new EarthQuakeParser(); 
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        String source = "data/nov20quakedata.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);   
        // for (QuakeEntry qe : list) {
        //     System.out.println(qe);
        // }
        System.out.println("read data for "+list.size()+" quakes");

        MatchAllFilter maf = new MatchAllFilter(); 
        Filter f = new MagnitudeFilter(0.0,5.0,"Magnitude"); 
        Location city =  new Location(55.7308, 9.1153);
        Filter f2 = new DistanceFilter(city,3000000,"Distance"); 
        Filter f3 = new PhraseFilter("any","e","Phrase"); 
        maf.addFilter(f);
        maf.addFilter(f2);
        maf.addFilter(f3);
        list = filter(list, maf);
        for (QuakeEntry qe: list) { 
            System.out.println(qe);
        } 
        
        System.out.println("Filters used are: "+maf.getName());
        System.out.println("Found "+list.size()+" quakes that match that criteria");
    }

    public void createCSV() {
        EarthQuakeParser parser = new EarthQuakeParser();
        //String source = "../data/nov20quakedata.atom";
        String source = "data/nov20quakedatasmall.atom";
        //String source = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.atom";
        ArrayList<QuakeEntry> list  = parser.read(source);
        dumpCSV(list);
        System.out.println("# quakes read: "+list.size());
    }

    public void dumpCSV(ArrayList<QuakeEntry> list) {
        System.out.println("Latitude,Longitude,Magnitude,Info");
        for(QuakeEntry qe : list){
            System.out.printf("%4.2f,%4.2f,%4.2f,%s\n",
                qe.getLocation().getLatitude(),
                qe.getLocation().getLongitude(),
                qe.getMagnitude(),
                qe.getInfo());
        }
    }

}
