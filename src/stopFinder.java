
/*
    Searching for a bus stop by full name or by the first few characters in the name, 
    using a ternary search tree (TST), returning the full stop information for each stop
    matching the search criteria (which can be zero, one or more stops).
*/

import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

import java.io.FileNotFoundException;

public class stopFinder {

    /**
     * returns busall data associated with the given arrival time in increasing
     * order of bus stop id
     **/

    public static String[] getStopByArival(String time) {
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

                    // split the arrival time
                    String[] arrival_times = d[1].split(":");

                    // split the hours into unit and seconds
                    String[] hours = arrival_times[0].split(" ");
                    if (hours[0] == "" || hours[0] == "0") {
                        int t = Integer.parseInt(time.split(":")[0].split(" ")[0]);
                        if (Integer.parseInt(hours[1]) < 0 || Integer.parseInt(hours[1]) != t
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
                        // System.out.println(data);
                        s.add(data);
                    }
                    add_to_list = true;

                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (s.size() == 0) {
            System.out.println("no data was found for the inserted arrival time.");
            String[] a = { "null" };
            return a;

        } else {
            String[] res = new String[s.size()];
            s.toArray(res);
            return mergeSort(res);

        }
    }

    public static String[] mergeSort(String[] array) {
        if (array.length <= 1) {
            return array;
        }

        int mid = array.length / 2;
        String[] lo = new String[mid];
        String[] hi = new String[array.length - mid];

        for (int i = 0; i < hi.length; i++) {
            hi[i] = array[i];
        }
        for (int j = 0; j < lo.length; j++) {
            lo[j] = array[j + hi.length];
        }

        lo = mergeSort(lo);
        hi = mergeSort(hi);

        return merge(lo, hi);
    }

    private static String[] merge(String[] hi, String[] lo) {
        String[] result = new String[hi.length + lo.length];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < lo.length || j < hi.length) {
            if (i < lo.length && j < hi.length) {
                int lo_ptr = Integer.parseInt(lo[i].split(",")[0]);
                int hi_ptr = Integer.parseInt(hi[j].split(",")[0]);
                if (lo_ptr < hi_ptr) {
                    result[k++] = lo[i++];
                } else {
                    result[k++] = hi[j++];
                }
            } else if (i < lo.length) {
                result[k++] = lo[i++];
            } else if (j < hi.length) {
                result[k++] = hi[j++];
            }
        }

        return result;
    }
}