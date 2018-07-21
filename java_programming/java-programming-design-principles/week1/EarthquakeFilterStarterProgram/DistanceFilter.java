
/**
 * Write a description of class DistanceFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DistanceFilter implements Filter
{
    private Location from; 
    private double distMax; 
    private String filterName;
    
    public DistanceFilter(Location from,double distMax,String name) { 
        this.from = from;
        this.distMax = distMax;
        filterName = name;
    } 

    public boolean satisfies(QuakeEntry qe) { 
        
        return from.distanceTo(qe.getLocation()) < distMax; 
    } 

    public String getName(){
        return filterName;
    };
}