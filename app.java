import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

public class app {
    public static void main(String[] args) {
        getStopByArival("10:05:09");

    }

    /**
     * removes data with invalid times from the povidied data
     */
    private static String[] getStopByArival(String time) {
        boolean add_to_list = false;
        // create an arraylist to hold the data
        ArrayList<String> s = new ArrayList<String>();
        String[] array;

        String fileName = "inputs\\stop_times.txt";
        File file = new File(fileName);
        System.out.println(" ");
        try {
            Scanner inputStream = new Scanner(file);
            while (inputStream.hasNextLine()) {
                String data = inputStream.nextLine();
                String[] d = data.split(",");
                // System.out.println("the val of ->" + d[0]);
                if (d[0].compareTo("trip_id") != 0) {
                    // System.out.println("the current data is\n " + data);
                    // System.out.println(d[0]);// trip id
                    // System.out.println(d[1]);// arrival time

                    if (Integer.parseInt(d[0]) == 9018011) {
                        int a = 0;
                    }

                    // split the arrival time
                    String[] arrival_times = d[1].split(":");

                    // split the hours into unit and seconds
                    String[] hours = arrival_times[0].split(" ");
                    if (hours[0] == "") {
                        if (Integer.parseInt(hours[1]) < 0
                                || Integer.parseInt(hours[1]) != Integer.parseInt(time.split(":")[0])
                                || Integer.parseInt(time.split(":")[1]) != Integer.parseInt(arrival_times[1])
                                || Integer.parseInt(time.split(":")[2]) != Integer.parseInt(arrival_times[2])) {
                            add_to_list = false;
                        }
                    } else {
                        // if the hours are invalid then we won't add the data to the list
                        if (Integer.parseInt(arrival_times[0]) > 23 || Integer.parseInt(arrival_times[0]) < 0
                                || Integer.parseInt(arrival_times[0]) != Integer.parseInt(time.split(":")[0])) {
                            add_to_list = false;
                        }
                    }
                    // if invalid mins or seconds.
                    if (Integer.parseInt(arrival_times[1]) > 59 || Integer.parseInt(arrival_times[2]) > 59
                            || Integer.parseInt(time.split(":")[1]) != Integer.parseInt(arrival_times[1])
                            || Integer.parseInt(time.split(":")[2]) != Integer.parseInt(arrival_times[2])) {
                        add_to_list = false;
                    }

                    if (add_to_list) {
                        System.out.println(data);
                        s.add(data);
                    }
                    add_to_list = true;

                    // System.out.println(d[2]);// departure time
                    // System.out.println(d[3]);// stop id
                    // System.out.println(d[4]);// stop sequence
                    // System.out.println(d[5]);//
                    // stop_headsign,pickup_type,drop_off_type,shape_dist_traveled
                    // System.out.println(d[6]);//
                    // System.out.println(d[7]);//
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String[] res = new String[s.size()];
        s.toArray(res);
        // array = (String[]) s.toArray();
        return res;
    }

}