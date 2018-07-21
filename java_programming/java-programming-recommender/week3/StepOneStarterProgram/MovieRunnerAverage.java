
/**
 * Write a description of class MovieRunnerAverage here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.*;
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.nio.charset.*;

public class MovieRunnerAverage {
    public void printAverageRatings() throws IOException{
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv","ratings.csv");
        System.out.println("the number of movies: "+sr.getMovieSize());
        System.out.println("the number of raters: "+sr.getRaterSize());

        ArrayList<Rating> averageRatingList = sr.getAverageRatings(12);
        Collections.sort(averageRatingList);
        for(Rating rating : averageRatingList){
            System.out.println(rating.getValue()+" "+sr.getTitle(rating.getItem()));
        }
    }

    public void getAverageRatingOneMovie() throws java.io.IOException{
        SecondRatings sr = new SecondRatings("ratedmoviesfull.csv","ratings.csv");
        double average = sr.getAverageByID(sr.getID("Vacation"),1);
        System.out.println("the average ratings for a specific movie title: "+average);
    }


}
