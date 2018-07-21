
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part3 {
    public Boolean twoOccurrences(String stringa,String stringb) {
        int last_pos = 0;
        int current_pos = 0;
        int occur_num = 0;
        int end_flag = 0;

        for (;end_flag == 0;) {
            current_pos = stringb.indexOf(stringa, last_pos);
            if (current_pos == -1) {
                end_flag = 1;
            }
            else {
                last_pos = current_pos + stringa.length();
                occur_num = occur_num + 1;
            }
        }

        if (occur_num >= 2) {
            return true;
        } 
        else {
            return false;
        }
    }

    public String lastPart(String stringa,String stringb) {
        int last_pos = 0;
        int current_pos = 0;

        current_pos = stringb.indexOf(stringa, last_pos);
        if (current_pos == -1) {
            return stringb;
        }
        else {
            last_pos = current_pos + stringa.length();
            return stringb.substring(last_pos);
        }
    }

    public void testing() {
        String stringa = "";
        String stringb = "";
        Boolean result = null;
        String result_string = null;
            
        stringa = "by";
        stringb = "A story by Abby Long";
        result = twoOccurrences(stringa,stringb);
        System.out.println("stringa: " + stringa);
        System.out.println("stringb: " + stringb);
        System.out.println("result: " + result);
        System.out.println("");

        stringa = "a";
        stringb = "banana";
        result = twoOccurrences(stringa,stringb);
        System.out.println("stringa: " + stringa);
        System.out.println("stringb: " + stringb);
        System.out.println("result: " + result);
        System.out.println("");

        stringa = "atg";
        stringb = "ctgtatgta";
        result = twoOccurrences(stringa,stringb);
        System.out.println("stringa: " + stringa);
        System.out.println("stringb: " + stringb);
        System.out.println("result: " + result);
        System.out.println("");

        stringa = "an";
        stringb = "banana";
        result_string = lastPart(stringa,stringb);
        System.out.println("The part of the string after " + stringa + " in " + stringb + " is " + result_string);

        stringa = "zoo";
        stringb = "forest";
        result_string = lastPart(stringa,stringb);
        System.out.println("The part of the string after " + stringa + " in " + stringb + " is " + result_string);

    }
}
