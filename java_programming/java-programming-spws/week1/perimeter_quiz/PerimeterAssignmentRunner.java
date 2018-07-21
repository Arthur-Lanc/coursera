import edu.duke.*;
import java.io.File;
import java.text.DecimalFormat;

public class PerimeterAssignmentRunner {
    public int getNumPoints (Shape s) {
        // Put code here
        int point_count = 0;
        for (Point p : s.getPoints()) {
            point_count = point_count + 1;
        }
        return point_count;
    }

    public double getAverageLength(Shape s) {
        // Put code here
        double all_length = 0;
        Point last_point = s.getLastPoint();
        for (Point current_point : s.getPoints()) {
            double dist = last_point.distance(current_point);
            all_length = all_length + dist;
            last_point = current_point;
        }
        int point_count = getNumPoints(s);
        double avg_length = all_length / point_count;
        return avg_length;
    }

    public double getLargestSide(Shape s) {
        // Put code here
        Point last_point = s.getLastPoint();
        double max_dist = 0;
        for (Point current_point : s.getPoints()) {
            double dist = last_point.distance(current_point);
            if (dist > max_dist) {
                max_dist = dist;
            }
            last_point = current_point;
        }
        return max_dist;
    }

    public double getLargestX(Shape s) {
        // Put code here
        double max_x = 0;
        for (Point current_point : s.getPoints()) {
            double dist_x = current_point.getX();
            if (dist_x > max_x) {
                max_x = dist_x;
            }
        }
        return max_x;
    }

    public double getPerimeter (Shape s) {
        double totalPerim = 0.0;
        Point prevPt = s.getLastPoint();
        for (Point currPt : s.getPoints()) {
            double currDist = prevPt.distance(currPt);
            totalPerim = totalPerim + currDist;
            prevPt = currPt;
        }
        return totalPerim;
    }
    
    public double getLargestPerimeterMultipleFiles() {
        // Put code here
        DirectoryResource dr = new DirectoryResource();
        double largest_peri = 0;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double curr_peri = getPerimeter(s);
            if (curr_peri > largest_peri) {
                largest_peri = curr_peri;
            }
        }
        return largest_peri;
    }

    public String getFileWithLargestPerimeter() {
        // Put code here
        DirectoryResource dr = new DirectoryResource();
        double largest_peri = 0;
        File temp = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double curr_peri = getPerimeter(s);
            if (curr_peri > largest_peri) {
                largest_peri = curr_peri;
                temp = f;
            }
        }
        return temp.getName();
    }

    public void testPerimeterMultipleFiles() {
        // Put code here
        double result1 = getLargestPerimeterMultipleFiles();
        System.out.println("getLargestPerimeterMultipleFiles = " + result1+ " " + new DecimalFormat("##.##").format(result1));
    }

    public void testFileWithLargestPerimeter() {
        // Put code here
        String result1= getFileWithLargestPerimeter();
        System.out.println("getFileWithLargestPerimeter = " + result1);
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        System.out.println("getNumPoints = " + getNumPoints(s) + " " + new DecimalFormat("##.##").format(getNumPoints(s)));
        System.out.println("getAverageLength = " + getAverageLength(s) + " " + new DecimalFormat("##.##").format(getAverageLength(s)));
        System.out.println("getLargestSide = " + getLargestSide(s) + " " + new DecimalFormat("##.##").format(getLargestSide(s)));
        System.out.println("getLargestX = " + getLargestX(s) + " " + new DecimalFormat("##.##").format(getLargestX(s)));
        System.out.println("getPerimeter = " + getPerimeter(s) + " " + new DecimalFormat("##.##").format(getPerimeter(s)));
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        //pr.testPerimeter();
        pr.testPerimeterMultipleFiles();
        pr.testFileWithLargestPerimeter();
    }
}
