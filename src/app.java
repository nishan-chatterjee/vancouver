import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class app {
    public static void main(String[] args) {
        // stopFinder obj = new stopFinder();
        String[] arr = stopFinder.getStopByArival("05:25:00");
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}