
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
    public void convertAndSave(){
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            ImageResource ir1 = new ImageResource(f);
            ImageResource ir2 = makeGray(ir1);
            
            String fname = ir1.getFileName();
            String newName = "gray-"+fname;
            ir2.setFileName(newName);
            ir2.draw();
            ir2.save();
        }
    }

    public ImageResource makeGray(ImageResource ir1){
        ImageResource ir2 = new ImageResource(ir1.getWidth(),ir1.getHeight());
        for (Pixel p2 : ir2.pixels()){
            Pixel p1 = ir1.getPixel(p2.getX(), p2.getY());
            int average = (p1.getRed()+p1.getGreen()+p1.getBlue())/3;
            p2.setRed(average);
            p2.setGreen(average);
            p2.setBlue(average);
        }
        return ir2;
    }

}
