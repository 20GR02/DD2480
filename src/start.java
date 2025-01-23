import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class start{
    public static void main(String[] args){
        
        // reading the number of points
        String N = "N.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(N));
            for (String line : lines) {
                System.out.println(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        // reading the points
        String Points = "POINTS.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(Points));
            for (String line : lines) {
                System.out.println(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        // reading the points
        String LCM = "LCM.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(LCM));
            for (String line : lines) {
                System.out.println(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        // reading the PUV
        String PUV = "PUV.txt";
        try {
            List<String> lines = Files.readAllLines(Paths.get(PUV));
            for (String line : lines) {
                System.out.println(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
