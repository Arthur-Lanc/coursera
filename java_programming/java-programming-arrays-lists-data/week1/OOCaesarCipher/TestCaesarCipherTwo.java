
/**
 * Write a description of TestCaesarCipherTwo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;

public class TestCaesarCipherTwo {
    public int[] countLetters(String message){
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for (int k=0;k<message.length();k++){
            char ch = Character.toLowerCase(message.charAt(k));
            int dex = alph.indexOf(ch);
            if (dex != -1){
                counts[dex] += 1;
            }
        }
        return counts;
    }

    public int maxIndex(int[] vals){
        int maxDex = 0;
        for(int k=0;k<vals.length;k++){
            if(vals[k]>vals[maxDex]){
                maxDex = k;
            }
        }
        return maxDex;
    }

    public String halfOfString(String message,int start){
        StringBuilder sb = new StringBuilder();

        for (int k=0;k<message.length();k++){
            if ((k >= start) && (k%2 == start%2)){
                char ch = message.charAt(k);
                sb.append(ch);
            }
        }

        return sb.toString();
    }

    public int getKey(String s){
        int[] freqs = countLetters(s);
        int maxDex = maxIndex(freqs);

        int dkey = maxDex - 4;
        if (maxDex < 4){
            dkey = 26 -(4-maxDex);
        }

        return dkey;
    }

    public void simpleTests(){
        //String encrypted;
        String decrypted1;
        String decrypted2;

        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        
        //String message = "Can you imagine life WITHOUT the internet AND computers in your pocket?";
        
        //CaesarCipherTwo cc = new CaesarCipherTwo(14,24);
        //encrypted = cc.encrypt(message);
        //System.out.println("encrypted = "+encrypted);
        //decrypted1 = cc.decrypt(encrypted);
        //System.out.println("decrypted1 = "+decrypted1);

        decrypted2 = breakCaesarCipher(encrypted);
        System.out.println("decrypted2 = "+decrypted2);
        
        
        //CaesarCipherTwo cc = new CaesarCipherTwo(14,24);
        //encrypted = cc.encrypt(message);
        //encrypted = "Aal uttx hm aal Qtct Fhljha pl Wbdl. Pvxvxlx!";
        //System.out.println("encrypted = "+encrypted);
        //decrypted1 = cc.decrypt(encrypted);
        //System.out.println("decrypted1 = "+decrypted1);

        //decrypted2 = breakCaesarCipher(encrypted);
        //System.out.println("decrypted2 = "+decrypted2);
    }

    public String breakCaesarCipher (String input){
        String encrypted_p1;
        String encrypted_p2;
        encrypted_p1 = halfOfString(input, 0);
        encrypted_p2 = halfOfString(input, 1);

        int dkey1 = getKey(encrypted_p1);
        int dkey2 = getKey(encrypted_p2);
        System.out.println("dkey1: "+dkey1);
        System.out.println("dkey2: "+dkey2);

        CaesarCipherTwo cc = new CaesarCipherTwo(dkey1,dkey2);
        String message = cc.decrypt(input);
        return message;
    }
    
}
