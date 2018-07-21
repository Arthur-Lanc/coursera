
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class Part1 {
    public void tester () {
        String result;
        int count;
        FileResource fr = new FileResource();
        
        CSVParser parser = fr.getCSVParser();
        result = countryInfo(parser,"Nauru");
        System.out.println(result); 
        System.out.println(""); 

        parser = fr.getCSVParser();
        listExportersTwoProducts(parser,"cotton","flowers");
        System.out.println(""); 

        parser = fr.getCSVParser();
        count = numberOfExporters(parser,"cocoa");
        System.out.println(count); 
        System.out.println(""); 

        parser = fr.getCSVParser();
        bigExporters(parser,"$999,999,999,999");
        System.out.println(""); 
    }

    public String countryInfo(CSVParser parser,String country) {
        String result_str = "NOT FOUND";
        
        for (CSVRecord csvRecord : parser) {
            if (csvRecord.get("Country").contains(country)){
            // if (true){
                result_str = csvRecord.get("Country")+": "+csvRecord.get("Exports")+": "+csvRecord.get("Value (dollars)");
                break;
            }
        }

        return result_str;
    }


    public void listExportersTwoProducts (CSVParser parser,String exportItem1,String exportItem2) {
        for (CSVRecord csvRecord : parser) {
            if (csvRecord.get("Exports").contains(exportItem1) && csvRecord.get("Exports").contains(exportItem2)){
            // if (true){
                System.out.println(csvRecord.get("Country"));
            }
        }
    }

    public int numberOfExporters (CSVParser parser,String exportItem) {
        int result = 0;

        for (CSVRecord csvRecord : parser) {
            if (csvRecord.get("Exports").contains(exportItem)){
            // if (true){
                result = result + 1;
            }
        }

        return result;
    }

    public void bigExporters (CSVParser parser,String amount) {
        for (CSVRecord csvRecord : parser) {
            if (csvRecord.get("Value (dollars)").length() > amount.length()){
            // if (true){
                System.out.println(csvRecord.get("Country")+" "+csvRecord.get("Value (dollars)"));
            }
        }
    }


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
