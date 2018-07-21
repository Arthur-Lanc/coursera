
/**
 * Write a description of class FirstRatings here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.io.*;
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.nio.charset.*;

public class FirstRatings {
    public ArrayList<Movie> loadMovies(String filename){
        ArrayList<Movie> movieList = new ArrayList<Movie>();
        
        try {
            File csvData = new File(filename);
            CSVParser parser = CSVParser.parse(csvData,Charset.forName("UTF-8"), CSVFormat.RFC4180.withHeader());
            
            for (CSVRecord csvRecord : parser) {
                Movie movieTmp = new Movie(csvRecord.get("id"), csvRecord.get("title"), csvRecord.get("year"), 
                    csvRecord.get("genre"), csvRecord.get("director"),csvRecord.get("country"), 
                    csvRecord.get("poster"), Integer.parseInt(csvRecord.get("minutes").trim()));
                    //System.out.println(movieTmp);
                movieList.add(movieTmp);
            }
        } catch (IOException ioe) {
            System.out.println("Trouble reading from the file: " + ioe.getMessage());
        } 
        
        return movieList;
    }

    public ArrayList<Rater> loadRaters (String filename){
        ArrayList<Rater> raterList = new ArrayList<Rater>();
        
        try {
            File csvData = new File(filename);
            CSVParser parser = CSVParser.parse(csvData,Charset.forName("UTF-8"), CSVFormat.RFC4180.withHeader());
            HashMap<String, Integer> raterIdIndexMap = new HashMap<String, Integer>();
            int index = 0;
    
            for (CSVRecord csvRecord : parser) {
                if(!raterIdIndexMap.containsKey(csvRecord.get("rater_id"))){
                    Rater raterTmp = new EfficientRater(csvRecord.get("rater_id"));
                    raterTmp.addRating(csvRecord.get("movie_id"),Double.parseDouble(csvRecord.get("rating")));
                    raterList.add(raterTmp);
                    raterIdIndexMap.put(csvRecord.get("rater_id"), index);
                    index += 1;
                }
                else{
                    int where = raterIdIndexMap.get(csvRecord.get("rater_id"));
                    Rater raterTmp = raterList.get(where);
                    raterTmp.addRating(csvRecord.get("movie_id"),Double.parseDouble(csvRecord.get("rating")));
                    raterList.set(where, raterTmp);
                }
            }
        } catch (IOException ioe) {
            System.out.println("Trouble reading from the file: " + ioe.getMessage());
        } 
        
        return raterList;
    }

    public void testLoadMovies() throws java.io.IOException{
        int num = 0;
        //ArrayList<Movie> movieList = loadMovies("data/ratedmovies_short.csv");
        ArrayList<Movie> movieList = loadMovies("data/ratedmoviesfull.csv");

        System.out.println("the number of movies: "+movieList.size());

        //for (Movie movie : movieList) {
        //    System.out.println(movie);
        //}

        num = 0;
        for (Movie movie : movieList) {
            if (movie.getGenres().contains("Comedy")){
                num += 1;
            }
        }
        System.out.println("the number of movies include the Comedy genre: "+num);

        num = 0;
        for (Movie movie : movieList) {
            if (movie.getMinutes() > 150){
                num += 1;
            }
        }
        System.out.println("the number of movies greater than 150 minutes: "+num);

        num = 0;
        HashMap<String, Integer> direNumMap = new HashMap<String, Integer>();
        for (Movie movie : movieList) {
            String dires = movie.getDirector();
            String[] diresArray = dires.trim().split("\\s*,\\s*");
            for(int k=0;k<diresArray.length;k++){
                if(direNumMap.containsKey(diresArray[k])){
                    direNumMap.put(diresArray[k], direNumMap.get(diresArray[k])+1);
                }
                else{
                    direNumMap.put(diresArray[k], 1);
                }
            }
        }
        for(String director : direNumMap.keySet()){
            if(direNumMap.get(director) > num){
                num = direNumMap.get(director);
            }
        }
        ArrayList<String> direWithMaxMovieList = new ArrayList<String>();
        for(String director : direNumMap.keySet()){
            if(direNumMap.get(director) == num){
                direWithMaxMovieList.add(director);
            }
        }
        System.out.println("the maximum number of movies by any director: "+num);
        System.out.println("the directors are that directed that many movies: "+direWithMaxMovieList);
        System.out.println("there are "+direWithMaxMovieList.size()+" directors that directed max movie");
    }


    public void testLoadRaters() throws java.io.IOException{
        int num = 0;
        //ArrayList<Rater> raterList = loadRaters("data/ratings_short.csv");
        ArrayList<Rater> raterList = loadRaters("data/ratings.csv");

        System.out.println("the number of raters: "+raterList.size());

        //for (Rater rater : raterList) {
        //   System.out.println("the rater’s ID: "+rater.getID()+" the number of ratings: "+rater.numRatings());
        //   System.out.println("the movie ID and the rating: "+rater.getRating2());
        //}

        num = 0;
        for (Rater rater : raterList) {
            if (rater.getID().equals("193")){
                num = rater.numRatings();
                break;
            }
        }
        System.out.println("the number of ratings for a particular rater: "+num);

        num = 0;
        for (Rater rater : raterList) {
            if (rater.numRatings() > num){
                num = rater.numRatings();
            }
        }
        ArrayList<String> raterWithMaxRatingList = new ArrayList<String>();
        for (Rater rater : raterList) {
            if (rater.numRatings() == num){
                raterWithMaxRatingList.add(rater.getID());
            }
        }
        System.out.println("the maximum number of ratings by any rater: "+num);
        System.out.println("how many raters have this maximum number of ratings: "+raterWithMaxRatingList.size());
        System.out.println("who those raters are: "+raterWithMaxRatingList);

        num = 0;
        for (Rater rater : raterList) {
            if (rater.hasRating("1798709")){
                num += 1;
            }
        }
        System.out.println("the number of ratings a particular movie has: "+num);

        num = 0;
        HashSet<String> set = new HashSet<String>();
        for (Rater rater : raterList) {
            ArrayList<String> itemList = rater.getItemsRated();
            for (String s : itemList) {
                set.add(s);
            }
        }
        System.out.println("how many different movies have been rated by all these raters: "+set.size());
    }
}
