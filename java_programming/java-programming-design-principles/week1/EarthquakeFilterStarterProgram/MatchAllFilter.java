
/**
 * Write a description of class MatchAllFilter here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;

public class MatchAllFilter implements Filter
{
    private ArrayList<Filter> list; 
    
    public MatchAllFilter() { 
        list = new ArrayList<Filter>();
    } 

    public void addFilter (Filter f){
        list.add(f);
    }

    public boolean satisfies(QuakeEntry qe) { 
        for (Filter f : list) {
            if (!f.satisfies(qe)) { 
                return false;
            } 
        } 
        return true;
    }

    public String getName(){
        String filterName = "";
        for (Filter f : list) {
            filterName += f.getName();
            filterName += " ";
        } 
        filterName = filterName.substring(0, filterName.length() - 1);
        return filterName;
    };
}