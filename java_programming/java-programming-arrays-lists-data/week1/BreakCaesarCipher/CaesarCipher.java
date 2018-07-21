
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class CaesarCipher {
    public String encrypt (String input,int key){
        StringBuilder input_sb = new StringBuilder(input);
        String alphebat_str = "abcdefghijklmnopqrstuvwxyz";
        String shift_alphebat_str = alphebat_str.substring(key)+alphebat_str.substring(0,key);

        for (int i = 0;i<input_sb.length();i++){
            char currChar = input_sb.charAt(i);
            char lower_currChar = Character.toLowerCase(currChar);
            int idx = alphebat_str.indexOf(lower_currChar);

            if (idx != -1){
                char newChar = shift_alphebat_str.charAt(idx);
                if (Character.isUpperCase(currChar)){
                    input_sb.setCharAt(i,Character.toUpperCase(newChar));
                }
                else{
                    input_sb.setCharAt(i,newChar);
                }
            }
        }

        return input_sb.toString();
    }

    public void testencrypt(){
        String result;

        // result = encrypt("FIRST LEGION ATTACK EAST FLANK!", 23);
        // System.out.println("result = "+result);

        // result = encrypt("First Legion", 23);
        // System.out.println("result = "+result);

        // result = encrypt("First Legion", 17);
        // System.out.println("result = "+result);

        result = encrypt("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 15);
        System.out.println("result = "+result);
    }

    public void testCaesar (){
        int key = 23;

        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = encrypt(message, key);
        System.out.println("key is "+key+"\n"+encrypted);

        String decrypted = encrypt(encrypted, 26-key);
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println(decrypted);
    }

    public String encryptTwoKeys (String input,int key1,int key2){
        StringBuilder input_sb = new StringBuilder(input);
        String alphebat_str = "abcdefghijklmnopqrstuvwxyz";
        String shift_alphebat_str1 = alphebat_str.substring(key1)+alphebat_str.substring(0,key1);
        String shift_alphebat_str2 = alphebat_str.substring(key2)+alphebat_str.substring(0,key2);

        for (int i = 0;i<input_sb.length();i++){
            char currChar = input_sb.charAt(i);
            char lower_currChar = Character.toLowerCase(currChar);
            int idx = alphebat_str.indexOf(lower_currChar);

            if (idx != -1){
                char newChar;
                if(i%2 == 0){
                    newChar = shift_alphebat_str1.charAt(idx);
                }
                else{
                    newChar = shift_alphebat_str2.charAt(idx);
                }

                if (Character.isUpperCase(currChar)){
                    input_sb.setCharAt(i,Character.toUpperCase(newChar));
                }
                else{
                    input_sb.setCharAt(i,newChar);
                }
            }
        }

        return input_sb.toString();
    }

    public void testencryptTwoKeys(){
        String result;

        // result = encryptTwoKeys("First Legion",23,17);
        // System.out.println("result = "+result);

        result = encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!",8,21);
        System.out.println("result = "+result);
        
        result = encryptTwoKeys("Io iwjv jz dv bcm kjvammmikz mwju edbc twpz pvb wi awm v ncmxmqnm xvzog. TMGT TJCY!",26-8,26-21);
        System.out.println("result = "+result);
        
        result = encryptTwoKeys("Top ncmy qkff vi vguv vbg ycpx",26-2,26-20);
        System.out.println("result = "+result);
        
    }
}
