import java.io.*;
import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.nio.charset.*;
import java.text.DecimalFormat;

public class RecommendationRunner implements Recommender {
    private static DecimalFormat df2 = new DecimalFormat(".##");

    public ArrayList<String> getItemsToRate (){
        ArrayList<String> ret = new ArrayList<String>();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        // AllFilters af = new AllFilters();
        // af.addFilter(new YearAfterFilter(2010));
        // af.addFilter(new MinutesFilter(70,200));
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        Random rand = new Random();

        for(int k=0;k<10;k++){
            int randomIndex = rand.nextInt(movies.size());
            ret.add(movies.get(randomIndex));
            movies.remove(randomIndex);
        }
        return ret;
    }

    public void printRecommendationsFor (String webRaterID){
        FourthRatings sr = new FourthRatings();
        RaterDatabase.initialize("ratings.csv");
        // System.out.println("the number of raters: "+RaterDatabase.size());
        MovieDatabase.initialize("ratedmoviesfull.csv");
        // System.out.println("the number of movies: "+MovieDatabase.size());
        ArrayList<Rating> movieIdWeightAverageList = sr.getSimilarRatings(webRaterID,15,3);
        int count = 0;
        Rater wr = RaterDatabase.getRater(webRaterID);
        String ret = "";
        String str1="<style>body {background-color: #C6C6C6;}.topdiv {font-family: \"Courier New\", Courier, monospace;text-align: center;background-color: #8FA9E9;}.cleardiv {font-family: Verdana, Geneva, sans-serif;}.boxdiv {background-color: #8FA9E9;font-family: Verdana, Geneva, sans-serif;}table, th, td {border: 1px solid black;}</style>";
        String str2="<div class=\"topdiv\"><h1>Recommand the movie for you</h1><b>Below here, we will list the movie that you might like it!</b></div><div class=\"cleardiv\"><h2>What's your favor?</h2><em><b>Let me see ......</b></em></div>";
        String str3="<div class=\"boxdiv\"><h3>Movie: </h3>";
        String str4="<table style=\"width:100%\"><tr><th>#</th><th>Poster</th><th>Title</th><th>Genre</th><th>Year</th><th>Time</th></tr>";
        // String tmp = String.format("<tr><td>%d</td><td><img src=\"%s\"/></td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>",num_int,img_str,title,genre,year,time);
        String str5="</table>";
        String str6="</div>";
        String tmp = "";

        for(Rating rating : movieIdWeightAverageList){
            if(wr.hasRating(rating.getItem())){
                continue;
            }
            // System.out.println("<p><b>"+df2.format(rating.getValue())
            //     +" "+MovieDatabase.getTitle(rating.getItem())
            //     +"</b></p>"
            // );
            count += 1;
            String img_str = MovieDatabase.getPoster(rating.getItem());
            String title = MovieDatabase.getTitle(rating.getItem());
            String genre = MovieDatabase.getGenres(rating.getItem());
            int year = MovieDatabase.getYear(rating.getItem());
            int time = MovieDatabase.getMinutes(rating.getItem());
            tmp = tmp + String.format("<tr><td>%d</td><td><img src=\"%s\" style=\"width:90px;height:120px;\"></td><td>%s</td><td>%s</td><td>%d</td><td>%d</td></tr>",count,img_str,title,genre,year,time);
            if(count >= 10){
                break;
            }
        }

        if(count == 0){
            String tmp2 = "No movies could be recommended, please try again.";
            ret = str1+str2+str3+tmp2+str6;
            System.out.println(ret);
        }
        else{
            ret = str1+str2+str3+str4+tmp+str5+str6;
            System.out.println(ret);
        }
    }
}




