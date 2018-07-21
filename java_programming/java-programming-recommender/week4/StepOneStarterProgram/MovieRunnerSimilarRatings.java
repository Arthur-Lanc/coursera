
/**
 * Write a description of class MovieRunnerSimilarRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.*;
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.nio.charset.*;

public class MovieRunnerSimilarRatings {
    public void printAverageRatings(){
        FourthRatings sr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("the number of raters: "+RaterDatabase.size());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("the number of movies: "+MovieDatabase.size());

        ArrayList<Rating> averageRatingList = sr.getAverageRatings(35);
        System.out.println("found "+averageRatingList.size()+" movies");

        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
        }
    }

    public void printAverageRatingsByYearAfterAndGenre (){
        FourthRatings sr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        System.out.println("the number of raters: "+RaterDatabase.size());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("the number of movies: "+MovieDatabase.size());

        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(1990));
        af.addFilter(new GenreFilter("Drama"));
        ArrayList<Rating> averageRatingList = sr.getAverageRatingsByFilter(8,af);
        System.out.println("found "+averageRatingList.size()+" movies");

        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()
                +" "+MovieDatabase.getYear(rating.getItem())
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
            System.out.println(
                "\t"+MovieDatabase.getGenres(rating.getItem())
            );
        }
    }

    public void printSimilarRatings(){
        FourthRatings sr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        // System.out.println("the number of raters: "+RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        // System.out.println("the number of movies: "+MovieDatabase.size());
        ArrayList<Rating> movieIdWeightAverageList = sr.getSimilarRatings("71",20,5);
        for(Rating rating : movieIdWeightAverageList){
            System.out.println(rating.getValue()
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
        }
    }

    public void printSimilarRatingsByGenre(){
        FourthRatings sr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        // System.out.println("the number of raters: "+RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        // System.out.println("the number of movies: "+MovieDatabase.size());
        ArrayList<Rating> movieIdWeightAverageList = sr.getSimilarRatingsByFilter("964",20,5,new GenreFilter("Mystery"));
        for(Rating rating : movieIdWeightAverageList){
            System.out.println(rating.getValue()
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
            System.out.println(
                "\t"+MovieDatabase.getGenres(rating.getItem())
            );
        }
    }

    public void printSimilarRatingsByDirector(){
        FourthRatings sr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        // System.out.println("the number of raters: "+RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        // System.out.println("the number of movies: "+MovieDatabase.size());
        ArrayList<Rating> movieIdWeightAverageList = sr.getSimilarRatingsByFilter("120",10,2,new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh"));
        for(Rating rating : movieIdWeightAverageList){
            System.out.println(rating.getValue()
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
            System.out.println(
                "\t"+MovieDatabase.getDirector(rating.getItem())
            );
        }
    }

    public void printSimilarRatingsByGenreAndMinutes(){
        FourthRatings sr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        // System.out.println("the number of raters: "+RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        // System.out.println("the number of movies: "+MovieDatabase.size());
        AllFilters af = new AllFilters();
        af.addFilter(new GenreFilter("Drama"));
        af.addFilter(new MinutesFilter(80,160));
        ArrayList<Rating> movieIdWeightAverageList = sr.getSimilarRatingsByFilter("168",10,3,af);
        for(Rating rating : movieIdWeightAverageList){
            System.out.println(rating.getValue()
                +" Time: "+MovieDatabase.getMinutes(rating.getItem())
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
            System.out.println(
                "\t"+MovieDatabase.getGenres(rating.getItem())
            );
        }
    }

    public void printSimilarRatingsByYearAfterAndMinutes(){
        FourthRatings sr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        // System.out.println("the number of raters: "+RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        // System.out.println("the number of movies: "+MovieDatabase.size());
        AllFilters af = new AllFilters();
        af.addFilter(new YearAfterFilter(1975));
        af.addFilter(new MinutesFilter(70,200));
        ArrayList<Rating> movieIdWeightAverageList = sr.getSimilarRatingsByFilter("314",10,5,af);
        for(Rating rating : movieIdWeightAverageList){
            System.out.println(rating.getValue()
                +" Year: "+MovieDatabase.getYear(rating.getItem())
                +" Time: "+MovieDatabase.getMinutes(rating.getItem())
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
        }
    }

}
