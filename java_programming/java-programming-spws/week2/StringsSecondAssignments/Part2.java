
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public int howMany(String stringa,String stringb) {
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

        return occur_num;
    }

    public void testHowMany () {
        String stringa = "";
        String stringb = "";
        int result = 0;
            
        stringa = "GAA";
        stringb = "ATGAACGAATTGAATC";
        result = howMany(stringa,stringb);
        System.out.println("stringa: " + stringa);
        System.out.println("stringb: " + stringb);
        System.out.println("result: " + result);
        System.out.println("");

        stringa = "AA";
        stringb = "ATAAAA";
        result = howMany(stringa,stringb);
        System.out.println("stringa: " + stringa);
        System.out.println("stringb: " + stringb);
        System.out.println("result: " + result);
        System.out.println("");
    }
}
