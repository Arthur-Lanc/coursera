
/**
 * Write a description of class MovieRunnerWithFilters here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.*;
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.nio.charset.*;

public class MovieRunnerWithFilters {
    public void printAverageRatings(){
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("the number of raters: "+sr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("the number of movies: "+MovieDatabase.size());

        ArrayList<Rating> averageRatingList = sr.getAverageRatings(35);
        System.out.println("found "+averageRatingList.size()+" movies");

        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()+" "+MovieDatabase.getTitle(rating.getItem()));
        }
    }

    public void printAverageRatingsByYear (){
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("the number of raters: "+sr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("the number of movies: "+MovieDatabase.size());

        ArrayList<Rating> averageRatingList = sr.getAverageRatingsByFilter(20,new YearAfterFilter(2000));
        System.out.println("found "+averageRatingList.size()+" movies");

        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()
                +" "+MovieDatabase.getYear(rating.getItem())
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
        }
    }

    public void printAverageRatingsByGenre (){
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("the number of raters: "+sr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("the number of movies: "+MovieDatabase.size());

        ArrayList<Rating> averageRatingList = sr.getAverageRatingsByFilter(20,new GenreFilter("Comedy"));
        System.out.println("found "+averageRatingList.size()+" movies");

        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()
                // +" "+MovieDatabase.getYear(rating.getItem())
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
            System.out.println(
                "\t"+MovieDatabase.getGenres(rating.getItem())
            );
        }
    }

    public void printAverageRatingsByMinutes (){
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("the number of raters: "+sr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("the number of movies: "+MovieDatabase.size());

        ArrayList<Rating> averageRatingList = sr.getAverageRatingsByFilter(5,new MinutesFilter(105,135));
        System.out.println("found "+averageRatingList.size()+" movies");

        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()
                +" Time: "+MovieDatabase.getMinutes(rating.getItem())
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
        }
    }

    public void printAverageRatingsByDirectors (){
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("the number of raters: "+sr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("the number of movies: "+MovieDatabase.size());

        ArrayList<Rating> averageRatingList = sr.getAverageRatingsByFilter(4,new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack"));
        System.out.println("found "+averageRatingList.size()+" movies");

        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()
                // +" "+MovieDatabase.getYear(rating.getItem())
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
            System.out.println(
                "\t"+MovieDatabase.getDirector(rating.getItem())
            );
        }
    }

    public void printAverageRatingsByYearAfterAndGenre (){
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("the number of raters: "+sr.getRaterSize());

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

    public void printAverageRatingsByDirectorsAndMinutes (){
        ThirdRatings sr = new ThirdRatings("ratings.csv");
        System.out.println("the number of raters: "+sr.getRaterSize());

        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("the number of movies: "+MovieDatabase.size());

        AllFilters af = new AllFilters();
        af.addFilter(new MinutesFilter(90,180));
        af.addFilter(new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack"));
        ArrayList<Rating> averageRatingList = sr.getAverageRatingsByFilter(3,af);
        System.out.println("found "+averageRatingList.size()+" movies");

        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()
                +" Time: "+MovieDatabase.getMinutes(rating.getItem())
                +" "+MovieDatabase.getTitle(rating.getItem())
            );
            System.out.println(
                "\t"+MovieDatabase.getDirector(rating.getItem())
            );
        }
    }

/*    public void getAverageRatingOneMovie() throws java.io.IOException{
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv","ratings.csv");
        double average = sr.getAverageByID(sr.getID("Vacation"),1);
        System.out.println("the average ratings for a specific movie title: "+average);
    }*/


}
