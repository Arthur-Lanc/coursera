
/**
 * Write a description of class EfficientRater here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class EfficientRater implements Rater {
    private String myID;
    private HashMap<String,Rating> myRatings;

    public EfficientRater(String id) {
        myID = id;
        myRatings = new HashMap<String,Rating>();
    }

    public void addRating(String item, double rating) {
        // myRatings.add(new Rating(item,rating));
        myRatings.put(item,new Rating(item,rating));
    }

    public boolean hasRating(String item) {
/*        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return true;
            }
        }

        return false;*/

        if(myRatings.containsKey(item)){
            return true;
        }
        else{
            return false;
        }
    }

    public String getID() {
        return myID;
    }

    public double getRating(String item) {
/*        for(int k=0; k < myRatings.size(); k++){
            if (myRatings.get(k).getItem().equals(item)){
                return myRatings.get(k).getValue();
            }
        }
        
        return -1;*/

        if(myRatings.containsKey(item)){
            return myRatings.get(item).getValue();
        }
        else{
            return -1;
        }
    }

    public HashMap<String,Rating> getRating2() {    
        return myRatings;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
/*        ArrayList<String> list = new ArrayList<String>();
        for(int k=0; k < myRatings.size(); k++){
            list.add(myRatings.get(k).getItem());
        }
        
        return list;*/

        ArrayList<String> list = new ArrayList<String>(myRatings.keySet());
        return list;
    }
}
