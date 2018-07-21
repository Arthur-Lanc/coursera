
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public void findAbc(String input) {
        int index = input.indexOf("abc");
        String found;
        while (true) {
            if (index == -1) {
                break;
            }
            //System.out.println(index+1);
            //System.out.println(index+4);
            if (index > input.length() - 4){
                break;
            }
            found = input.substring(index+1, index+4);
            System.out.println(found);
            index = input.indexOf("abc", index+4);
        }
    }
       public void test() {
        //no code yet
        // findAbc("abcd");
        // findAbc("abcdabc");
        // findAbc("eusabce");
        // findAbc("aaaaabc");
        // findAbc("yabcyabc");
        // findAbc("woiehabchi");        
        findAbc("abcbbbabcdddabc");        
    }
}
