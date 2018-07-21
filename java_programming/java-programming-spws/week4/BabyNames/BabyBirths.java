
/**
 * Write a description of BabyBirths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            if( numBorn <= 100){
                System.out.println("Name "+rec.get(0)+
                                    " Gender "+rec.get(1)+
                                    " Num Born "+rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr){
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int total_names = 0;
        int total_girls_names = 0;
        int total_boys_names = 0;
        for (CSVRecord rec : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            total_names += 1;
            if (rec.get(1).equals("M")){
                totalBoys += numBorn;
                total_boys_names += 1;
            }
            else {
                totalGirls += numBorn;
                total_girls_names += 1;
            }
        }
        System.out.println("total births = "+totalBirths);
        System.out.println("total girls = "+totalGirls);
        System.out.println("total boys = "+totalBoys);
        System.out.println("total_names = "+total_names);
        System.out.println("total_girls_names = "+total_girls_names);
        System.out.println("total_boys_names = "+total_boys_names);
    }

    public void testTotalBirths () {
        FileResource fr = new FileResource("../us_babynames/us_babynames_by_year/yob1905.csv");
        totalBirths(fr);
    }

    public int getRank (int year,String name,String gender){
        int rank_result = 0;
        int find_flag = 0;

        String file_path = "../us_babynames/us_babynames_by_year/yob"+year+".csv";
        FileResource fr = new FileResource(file_path);
        for (CSVRecord rec : fr.getCSVParser(false)){
            if (rec.get(1).equals(gender)){
                rank_result += 1;
                if (rec.get(0).equals(name)){
                    find_flag = 1;
                    break;
                }
            }
        }

        if (find_flag == 0){
            return -1;
        }
        else{
            return rank_result;
        }
    }

    public void testgetRank () {
        int rank_result;

        rank_result = getRank(1960,"Emily","F");
        System.out.println("rank_result = "+rank_result);

        rank_result = getRank(1971,"Frank","M");
        System.out.println("rank_result = "+rank_result);
    }

    public String getName (int year,int rank,String gender){
        int rank_count = 0;

        String file_path = "../us_babynames/us_babynames_by_year/yob"+year+".csv";
        FileResource fr = new FileResource(file_path);
        for (CSVRecord rec : fr.getCSVParser(false)){
            if (rec.get(1).equals(gender)){
                rank_count += 1;
                if (rank_count == rank){
                    return rec.get(0);
                }
            }
        }

        return "NO NAME";     
    }

    public void testgetName () {
        String name_result;

        name_result = getName(1980,350,"F");
        System.out.println("name_result = "+name_result);

        name_result = getName(1982,450,"M");
        System.out.println("name_result = "+name_result);
    }

    public void whatIsNameInYear (String name,int year,int newYear,String gender){
        String file_path = "../us_babynames/us_babynames_by_year/yob"+year+".csv";
        String file_path2 = "../us_babynames/us_babynames_by_year/yob"+newYear+".csv";
        String gender_dect_str;

        if (gender.equals("F")){
            gender_dect_str = "she";
        }else{
            gender_dect_str = "he";
        }

        int rank_result = getRank(year,name,gender);
        if (rank_result == -1){
            System.out.println("rank_result = "+rank_result);
        }else{
            String name_result = getName(newYear,rank_result,gender);
            System.out.println(name+" born in "+year+" would be "+name_result+" if "+gender_dect_str+" was born in "+newYear);
        }
    }

    public void testwhatIsNameInYear () {
        whatIsNameInYear("Susan",1972,2014,"F");
        whatIsNameInYear("Owen",1974,2014,"M");
    }

    public int yearOfHighestRank (String name,String gender){
        int year_result = 0;
        int highest_rank = 0;
        int for_num = 0;
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            String file_name = f.getName();
            int year = Integer.parseInt(file_name.replaceAll("\\D+",""));
            // System.out.println(year);
            int rank_result = getRank(year,name,gender);

            if (rank_result != -1){
                if (for_num == 0){
                    year_result = year;
                    highest_rank = rank_result;
                }
                else {
                    if (rank_result < highest_rank){
                        year_result = year;
                        highest_rank = rank_result;
                    }
                }

                for_num += 1;
            }

        }

        if (highest_rank != 0){
            return year_result;
        }else{
            return -1;
        }
    }

    public void testyearOfHighestRank () {
        int year_result;

        year_result = yearOfHighestRank("Genevieve","F");
        System.out.println("year_result = "+year_result);

        year_result = yearOfHighestRank("Mich","M");
        System.out.println("year_result = "+year_result);
    } 

    public double getAverageRank(String name,String gender){
        double count = 0;
        double rank_sum = 0;
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            String file_name = f.getName();
            int year = Integer.parseInt(file_name.replaceAll("\\D+",""));
            // System.out.println(year);
            int rank_result = getRank(year,name,gender);

            if(rank_result != -1){
                rank_sum += rank_result;
                count += 1;
            }
        }

        if (count == 0){
            return (double) -1;
        }
        else {
            return rank_sum/count;
        }
    }

    public void testgetAverageRank () {
        double result;

        result = getAverageRank("Susan","F");
        System.out.println("result = "+result);

        result = getAverageRank("Robert","M");
        System.out.println("result = "+result);
    } 

    public int getTotalBirthsRankedHigher(int year,String name,String gender){
        int total_births = 0;
        String file_path = "../us_babynames/us_babynames_by_year/yob"+year+".csv";
        FileResource fr = new FileResource(file_path);

        for (CSVRecord rec : fr.getCSVParser(false)){
            if (rec.get(1).equals(gender)){
                if (! rec.get(0).equals(name)){
                    total_births += Integer.parseInt(rec.get(2));
                }
                else{
                    break;
                }
            }
        }

        return total_births;
    }

    public void testgetTotalBirthsRankedHigher () {
        int result;

        result = getTotalBirthsRankedHigher(1990,"Emily","F");
        System.out.println("result = "+result);

        result = getTotalBirthsRankedHigher(1990,"Drew","M");
        System.out.println("result = "+result);
    } 

}
