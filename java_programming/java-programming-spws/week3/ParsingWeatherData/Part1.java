
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Part1 {
    public CSVRecord findColdestInTwoRecord (CSVRecord result,CSVRecord csvRecord){
        if (result == null){
            result = csvRecord;
        }
        else{
            double result_temp = Double.parseDouble(result.get("TemperatureF"));
            double csvrecord_temp = Double.parseDouble(csvRecord.get("TemperatureF"));

            if (csvrecord_temp < result_temp && csvrecord_temp != -9999){
                result = csvRecord;
            }
        }
        return result;
    }

    public CSVRecord coldestHourInFile (CSVParser parser){
        CSVRecord result = null;
        for (CSVRecord csvRecord : parser) {
            result = findColdestInTwoRecord(result,csvRecord);
        }
        return result;
    }

    public void testColdestHourInFile (){
        CSVRecord result = null;
        FileResource fr = new FileResource();

        CSVParser parser = fr.getCSVParser();
        result = coldestHourInFile(parser);
        System.out.println(result.get("TemperatureF")+" "+result.get("TimeEST")); 
        System.out.println(""); 
    }

    public String fileWithColdestTemperature (){
        String result_file_name = null;
        CSVRecord result = null;
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord csvRecord = coldestHourInFile(parser);

            if (result == null){
                result = csvRecord;
                result_file_name = f.getAbsolutePath();
            }
            else{
                double result_temp = Double.parseDouble(result.get("TemperatureF"));
                double csvrecord_temp = Double.parseDouble(csvRecord.get("TemperatureF"));

                if (csvrecord_temp < result_temp){
                    result = csvRecord;
                    result_file_name = f.getAbsolutePath();
                }
            }
        }

        return result_file_name;
    }

    public void testFileWithColdestTemperature(){
        String result_file_name = null;
        CSVRecord result = null;

        result_file_name = fileWithColdestTemperature();
        FileResource fr = new FileResource(result_file_name);
        CSVParser parser = fr.getCSVParser();
        result = coldestHourInFile(parser);

        System.out.println(result_file_name);
        System.out.println(result.get("TemperatureF")); 
        parser = fr.getCSVParser();
        for (CSVRecord csvRecord : parser) {
            System.out.println(csvRecord.get("DateUTC")+" "+csvRecord.get("TemperatureF")); 
        }
        System.out.println(""); 
    }

    public CSVRecord findLowestHumidityInTwoRecord (CSVRecord result,CSVRecord csvRecord){
        if (! "N/A".equals(csvRecord.get("Humidity"))){
            if (result == null){
                result = csvRecord;
            }
            else{
                double result_temp = Double.parseDouble(result.get("Humidity"));
                double csvrecord_temp = Double.parseDouble(csvRecord.get("Humidity"));

                if (csvrecord_temp < result_temp){
                    result = csvRecord;
                }
            } 
        }

        return result;
    }

    public CSVRecord lowestHumidityInFile (CSVParser parser){
        CSVRecord result = null;

        for (CSVRecord csvRecord : parser) {
            result = findLowestHumidityInTwoRecord(result,csvRecord);
        }

        return result;
    }

    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);

        System.out.println(csv.get("Humidity")+" "+csv.get("DateUTC")); 
        System.out.println(""); 
    }

    public CSVRecord lowestHumidityInManyFiles (){
        CSVRecord result = null;
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord csvRecord = lowestHumidityInFile(parser);

            result = findLowestHumidityInTwoRecord(result,csvRecord);
        }

        return result;
    }

    public void testLowestHumidityInManyFiles(){
        CSVRecord result = lowestHumidityInManyFiles();

        System.out.println(result.get("Humidity")+" "+result.get("DateUTC")); 
        System.out.println(""); 
    }

    public double averageTemperatureInFile(CSVParser parser){
        double temp_sum = 0;
        int count = 0 ;

        for (CSVRecord csvRecord : parser) {
            double csvrecord_temp = Double.parseDouble(csvRecord.get("TemperatureF"));

            if (csvrecord_temp != -9999){
                temp_sum = temp_sum + csvrecord_temp;
                count = count + 1;
            }
        }
        return temp_sum/count;
    }

    public void testAverageTemperatureInFile() {
        double result;
        FileResource fr = new FileResource();

        CSVParser parser = fr.getCSVParser();
        result = averageTemperatureInFile(parser);
        System.out.println(result); 
        System.out.println(""); 
    }

    public double averageTemperatureWithHighHumidityInFile(CSVParser parser,int value){
        double temp_sum = 0;
        int count = 0 ;

        for (CSVRecord csvRecord : parser) {
            double csvrecord_temp = Double.parseDouble(csvRecord.get("TemperatureF"));
            double csvrecord_humi;
            if (! "N/A".equals(csvRecord.get("Humidity"))){
                csvrecord_humi = Double.parseDouble(csvRecord.get("Humidity"));
            }
            else{
                continue;
            }

            if (csvrecord_temp != -9999 && csvrecord_humi >= value){
                temp_sum = temp_sum + csvrecord_temp;
                count = count + 1;
            }
        }

        if (count == 0){
            return (double) -999999;
        }
        else{
            return temp_sum/count;    
        }
        
    }

    public void testAverageTemperatureWithHighHumidityInFile(){
        double result;
        FileResource fr = new FileResource();

        CSVParser parser = fr.getCSVParser();
        result = averageTemperatureWithHighHumidityInFile(parser,80);
        if (result == -999999){
            System.out.println("no temperature with that humidity"); 
        }
        else{
            System.out.println("humidity " + result);     
        }
        System.out.println(""); 
    }
}
