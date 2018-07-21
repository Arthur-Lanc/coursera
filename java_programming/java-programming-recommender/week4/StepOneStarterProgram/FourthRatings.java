
/**
 * Write a description of FourthRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class FourthRatings {

/*    // private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public FourthRatings(){
        // default constructor
        // this("ratedmoviesfull.csv", "ratings.csv");
        this("ratings.csv");
    }

    public FourthRatings(String ratingsfile){
        FirstRatings fr = new FirstRatings();
        // myMovies = fr.loadMovies("data/"+moviefile);
        myRaters = fr.loadRaters("data/"+ratingsfile);
    }

    public int getRaterSize(){
        return myRaters.size();
    }*/
    
    public double getAverageByID(String id,int minimalRaters){
        int count = 0;
        double sum = 0;
        for(Rater rater : RaterDatabase.getRaters()){
            if(rater.hasRating(id)){
                sum += rater.getRating(id);
                count += 1;
            }
        }

        if(count >= minimalRaters){
            return sum/count;
        }
        else{
            return 0.0;
        }
    }

    public ArrayList<Rating> getAverageRatings(int minimalRaters){
        double zero = new Double(0.0);
        ArrayList<Rating> averageRatingList = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

        for(String movieId : movies){
            double average = getAverageByID(movieId,minimalRaters);
            if(Double.compare(average,zero) != 0){
                Rating averageRating = new Rating(movieId,average);
                averageRatingList.add(averageRating);
            }
        }

        return averageRatingList;
    }

    public ArrayList<Rating> getAverageRatingsByFilter (int minimalRaters,Filter filterCriteria){
        double zero = new Double(0.0);
        ArrayList<Rating> averageRatingList = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);

        for(String movieId : movies){
            double average = getAverageByID(movieId,minimalRaters);
            if(Double.compare(average,zero) != 0){
                Rating averageRating = new Rating(movieId,average);
                averageRatingList.add(averageRating);
            }
        }

        return averageRatingList;
    }


    private double dotProduct(Rater me,Rater r){
        double ret = 0;
        for(String item : me.getItemsRated()){
            if(r.hasRating(item)){
                ret = ret + (me.getRating(item)-5)*(r.getRating(item)-5);
            }
        }
        return ret;
    }

    private ArrayList<Rating> getSimilarities(String id){
        ArrayList<Rating> ret = new ArrayList<Rating>();
        ArrayList<Rater> raterList = RaterDatabase.getRaters();
        Rater me = RaterDatabase.getRater(id);

        for(Rater r: raterList){
            double dpValue = dotProduct(me,r);
            if((!r.getID().equals(id)) && dpValue > 0){
                ret.add(new Rating(r.getID(),dpValue));
            }
        }

        Collections.sort(ret, Collections.reverseOrder());
        return ret;
    }

    public double getAverageByID2(String id,int minimalRaters,ArrayList<Rating> raterIdSimlarityList,int numSimilarRaters){
        int count = 0;
        double sum = 0;

        for(int k=0;k<numSimilarRaters;k++){
            if(k>=raterIdSimlarityList.size()){
                break;
            }
            Rating raterIdSimlarity = raterIdSimlarityList.get(k);
            Rater rater = RaterDatabase.getRater(raterIdSimlarity.getItem());
            if(rater.hasRating(id)){
                sum = sum + rater.getRating(id)*raterIdSimlarity.getValue();
                count += 1;
            }
        }

        if(count >= minimalRaters){
            return sum/count;
        }
        else{
            return 0.0;
        }
    }

    public ArrayList<Rating> getSimilarRatings(String id,int numSimilarRaters,int minimalRaters){
        double zero = new Double(0.0);
        ArrayList<Rating> averageRatingList = new ArrayList<Rating>();
        ArrayList<Rating> raterIdSimlarityList= getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());

        for(String movieId : movies){
            double average = getAverageByID2(movieId,minimalRaters,raterIdSimlarityList,numSimilarRaters);
            if(Double.compare(average,zero) != 0){
                Rating averageRating = new Rating(movieId,average);
                averageRatingList.add(averageRating);
            }
        }

        Collections.sort(averageRatingList, Collections.reverseOrder());
        return averageRatingList;
    }

    public ArrayList<Rating> getSimilarRatingsByFilter(String id,int numSimilarRaters,int minimalRaters,Filter filterCriteria){
        double zero = new Double(0.0);
        ArrayList<Rating> averageRatingList = new ArrayList<Rating>();
        ArrayList<Rating> raterIdSimlarityList= getSimilarities(id);
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);

        for(String movieId : movies){
            double average = getAverageByID2(movieId,minimalRaters,raterIdSimlarityList,numSimilarRaters);
            if(Double.compare(average,zero) != 0){
                Rating averageRating = new Rating(movieId,average);
                averageRatingList.add(averageRating);
            }
        }

        Collections.sort(averageRatingList, Collections.reverseOrder());
        return averageRatingList;
    }

}
