
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        StringBuilder asciiArt = new StringBuilder();
        String asciiChars = "@%#*+=-:. ";
        //String asciiChars = "$% ";
//        String asciiChars = "█GMei=- ";
        boolean inverted = true;
        int buffer = 8;
        String path = "C:\\Users\\Nick\\IdeaProjects\\cgTask2\\src\\images\\1.jpg";

        AsciiGen asciiGen = new AsciiGen(asciiChars, inverted, buffer, path);


        asciiGen.makeArtMultichromic();

    }
}
//ffmpeg
//opencv
