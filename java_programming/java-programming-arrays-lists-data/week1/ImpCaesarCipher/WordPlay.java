
/**
 * Write a description of WordPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.lang.*;

public class WordPlay {
    public boolean isVowel (char ch){
        char lower_char = Character.toLowerCase(ch);
        if (lower_char == 'a' || lower_char == 'e' || lower_char == 'i' || lower_char == 'o' || lower_char == 'u'){
            return true;
        }
        else {
            return false;
        }
    }

    public void testisVowel (){
        boolean result;

        result = isVowel('a');
        System.out.println("result = "+result);

        result = isVowel('e');
        System.out.println("result = "+result);

        result = isVowel('I');
        System.out.println("result = "+result);

        result = isVowel('O');
        System.out.println("result = "+result);

        result = isVowel('u');
        System.out.println("result = "+result);

        result = isVowel('v');
        System.out.println("result = "+result);

        result = isVowel('c');
        System.out.println("result = "+result);

        result = isVowel('b');
        System.out.println("result = "+result);
    }

    public String replaceVowels (String phrase,char ch){
        StringBuilder phrase_sb = new StringBuilder(phrase);

        for (int i = 0;i<phrase_sb.length();i++){
            char currChar = phrase_sb.charAt(i);
            if (isVowel(currChar)){
                phrase_sb.setCharAt(i,ch);
            }
        }

        return phrase_sb.toString();
    }

    public void testreplaceVowels(){
        String result;

        result = replaceVowels("Hello World", '*');
        System.out.println("result = "+result);
    }

    public String emphasize (String phrase,char ch){
        StringBuilder phrase_sb = new StringBuilder(phrase);
        char lower_char = Character.toLowerCase(ch);

        for (int i = 0;i<phrase_sb.length();i++){
            char currChar = phrase_sb.charAt(i);
            char lower_currChar = Character.toLowerCase(currChar);

            if (lower_currChar == lower_char){
                if ((i+1)%2 == 1){
                    phrase_sb.setCharAt(i,'*');
                }
                else{
                    phrase_sb.setCharAt(i,'+');
                }
            }

        }

        return phrase_sb.toString();
    }

    public void testemphasize(){
        String result;

        result = emphasize("dna ctgaaactga", 'a');
        System.out.println("result = "+result);

        result = emphasize("Mary Bella Abracadabra", 'a');
        System.out.println("result = "+result);
    }
}
