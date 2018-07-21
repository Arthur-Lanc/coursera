
/**
 * Write a description of Part4 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.io.*;

public class Part4 {
    public String findingWebLinks(String url_location,String youtube_com_str) {
        int pos = 0;
        int start_pos = 0;
        int end_pos = 0;
        String youtube_link = "";
        String quotation_mark = "\"";
        URLResource ur = new URLResource(url_location);

        String youtube_com_str_lowercase = youtube_com_str.toLowerCase();

        for (String word : ur.words()) {
            String word_lowercase = word.toLowerCase();

            pos = word_lowercase.indexOf(youtube_com_str_lowercase);
            if (pos != -1) {
                end_pos = word_lowercase.indexOf(quotation_mark,pos);
                start_pos = word_lowercase.lastIndexOf(quotation_mark,pos);
                youtube_link = word.substring(start_pos,end_pos+1);
                System.out.println(youtube_link); 
            }
        }
        
        return "";
    }

    public void testing() {
        String url_location = "http://www.dukelearntoprogram.com/course2/data/manylinks.html";
        String youtube_com_str = "youtube.com";
        findingWebLinks(url_location,youtube_com_str);
    }

}