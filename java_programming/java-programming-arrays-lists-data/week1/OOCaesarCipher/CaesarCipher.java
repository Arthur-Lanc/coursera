
/**
 * Write a description of CaesarCipher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;

    public CaesarCipher(int key){
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphabet = alphabet.substring(key)+alphabet.substring(0,key);
        mainKey = key;
    }

    public String encrypt (String input){
        StringBuilder input_sb = new StringBuilder(input);
        // String alphabet = "abcdefghijklmnopqrstuvwxyz";
        // String shiftedAlphabet = alphabet.substring(key)+alphabet.substring(0,key);

        for (int i = 0;i<input_sb.length();i++){
            char currChar = input_sb.charAt(i);
            char lower_currChar = Character.toLowerCase(currChar);
            int idx = alphabet.indexOf(lower_currChar);

            if (idx != -1){
                char newChar = shiftedAlphabet.charAt(idx);
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

    public String decrypt(String input){
        CaesarCipher cc = new CaesarCipher(26 - mainKey);
        return cc.encrypt(input);
    }

    public void testencrypt(){
        String encrypted;
        String decrypted;

        // result = encrypt("FIRST LEGION ATTACK EAST FLANK!", 23);
        // System.out.println("result = "+result);

        // result = encrypt("First Legion", 23);
        // System.out.println("result = "+result);

        // result = encrypt("First Legion", 17);
        // System.out.println("result = "+result);

        // result = encrypt("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 15);
        // System.out.println("result = "+result);

        CaesarCipher cc = new CaesarCipher(15);
        encrypted = cc.encrypt("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!");
        System.out.println("encrypted = "+encrypted);
        decrypted = cc.decrypt(encrypted);
        System.out.println("decrypted = "+decrypted);
    }

    // public void testCaesar (){
    //     int key = 23;

    //     FileResource fr = new FileResource();
    //     String message = fr.asString();
    //     String encrypted = encrypt(message, key);
    //     System.out.println("key is "+key+"\n"+encrypted);

    //     String decrypted = encrypt(encrypted, 26-key);
    //     System.out.println("-----------------------");
    //     System.out.println("-----------------------");
    //     System.out.println(decrypted);
    // }

    // public String encryptTwoKeys (String input,int key1,int key2){
    //     StringBuilder input_sb = new StringBuilder(input);
    //     String alphabet = "abcdefghijklmnopqrstuvwxyz";
    //     String shiftedAlphabet1 = alphabet.substring(key1)+alphabet.substring(0,key1);
    //     String shiftedAlphabet2 = alphabet.substring(key2)+alphabet.substring(0,key2);

    //     for (int i = 0;i<input_sb.length();i++){
    //         char currChar = input_sb.charAt(i);
    //         char lower_currChar = Character.toLowerCase(currChar);
    //         int idx = alphabet.indexOf(lower_currChar);

    //         if (idx != -1){
    //             char newChar;
    //             if(i%2 == 0){
    //                 newChar = shiftedAlphabet1.charAt(idx);
    //             }
    //             else{
    //                 newChar = shiftedAlphabet2.charAt(idx);
    //             }

    //             if (Character.isUpperCase(currChar)){
    //                 input_sb.setCharAt(i,Character.toUpperCase(newChar));
    //             }
    //             else{
    //                 input_sb.setCharAt(i,newChar);
    //             }
    //         }
    //     }

    //     return input_sb.toString();
    // }

    // public void testencryptTwoKeys(){
    //     String result;

    //     // result = encryptTwoKeys("First Legion",23,17);
    //     // System.out.println("result = "+result);

    //     result = encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!",8,21);
    //     System.out.println("result = "+result);
        
    //     result = encryptTwoKeys("Io iwjv jz dv bcm kjvammmikz mwju edbc twpz pvb wi awm v ncmxmqnm xvzog. TMGT TJCY!",26-8,26-21);
    //     System.out.println("result = "+result);
        
    //     result = encryptTwoKeys("Top ncmy qkff vi vguv vbg ycpx",26-2,26-20);
    //     System.out.println("result = "+result);
        
    // }
}
