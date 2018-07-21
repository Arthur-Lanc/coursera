
/**
 * Write a description of class MagnitudeFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MagnitudeFilter implements Filter
{
    private double magMin; 
    private double magMax; 
    private String filterName;
    
    public MagnitudeFilter(double min,double max,String name) { 
        magMin = min;
        magMax = max;
        filterName = name;
    } 

    public boolean satisfies(QuakeEntry qe) { 
        return qe.getMagnitude() >= magMin && qe.getMagnitude() <= magMax; 
    } 

    public String getName(){
        return filterName;
    };

}
