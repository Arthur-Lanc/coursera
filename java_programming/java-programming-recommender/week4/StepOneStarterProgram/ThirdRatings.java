
/**
 * Write a description of ThirdRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class ThirdRatings {
    // private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings(){
        // default constructor
        // this("ratedmoviesfull.csv", "ratings.csv");
        this("ratings.csv");
    }

/*    public ThirdRatings(String moviefile,String ratingsfile) throws java.io.IOException{
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies("data/"+moviefile);
        myRaters = fr.loadRaters("data/"+ratingsfile);
    }*/

    public ThirdRatings(String ratingsfile){
        FirstRatings fr = new FirstRatings();
        // myMovies = fr.loadMovies("data/"+moviefile);
        myRaters = fr.loadRaters("data/"+ratingsfile);
    }

/*    public int getMovieSize(){
        return myMovies.size();
    }*/

    public int getRaterSize(){
        return myRaters.size();
    }
    
    public double getAverageByID(String id,int minimalRaters){
        int count = 0;
        double sum = 0;
        for(Rater rater : myRaters){
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

/*        for(Movie movie : myMovies){
            double average = getAverageByID(movie.getID(),minimalRaters);
            if(Double.compare(average,zero) != 0){
                Rating averageRating = new Rating(movie.getID(),average);
                averageRatingList.add(averageRating);
            }
        }*/ 

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

/*    public String getTitle(String id){
        for(Movie movie : myMovies){
            if(movie.getID().equals(id)){
                return movie.getTitle();
            }
        }
        return "ID was not found";
    }*/

/*    public String getID(String title){
        for(Movie movie : myMovies){
            if(movie.getTitle().equals(title)){
                return movie.getID();
            }
        }
        return "NO SUCH TITLE";
    }*/

}
