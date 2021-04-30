import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class shortestPathsFinder {

    private int sA, sB, sC, numIntersections ;
    private Graph graph;
    private boolean allGood;

    public int sA() {
        return sA;
    }
    public int sB() {
        return sB;
    }
    public int sC() {
        return sC;
    }
    public int numIntersections() {
        return numIntersections;
    }
    public Graph graph() {
        return graph;
    }
    public boolean allGood() {
        return allGood;
    }

    public static void main(String[] args) {

        /*
        * Creating graph from stop_times
        * */
        String fileName = "..//inputs/stop_times.txt";
        if (filename != null) {
            try {
                File file = new File(fileName);
                Scanner inputStream = new Scanner(file);

                String data = inputStream.nextLine(); // skip the first line as it contains the column names

                data = inputStream.nextLine(); // take the second line
                String[] values = data.split(",");
                String currentTripID = values[0]; // store the first trip id
                String currentStopID = values[3]; // and the stop id

                String nextTripID, nextStopID;

                while (inputStream.hasNextLine()) {
                    data = inputStream.nextLine();
                    values = data.split(",");
                    nextTripID = values[0];
                    nextStopID = values[3];

                    // add directed edge
                    if (currentTripID == nextTripID)
                        graph.addEdge(currentStopID, nextStopID, 1);

                    // make next node as current node
                    nextTripID = currentTripID;
                    nextStopID = currentStopID;
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                allGood = false;
                graph = null;
            }
        }
        else {
            allGood = false;
            graph = null;
        }

        /*
        * Creating graph from transfers
        * */
        String filename = "..//inputs/transfers.txt";
        if (filename != null) {
            try {
                File file = new File(fileName);
                Scanner inputStream = new Scanner(file);

                String data = inputStream.nextLine(); // skip the first line as it contains the column names
                String[] values;
                String currentStopID;
                String nextStopID;

                while (inputStream.hasNextLine()) {
                    data = inputStream.nextLine();
                    values = data.split(",");
                    currentStopID = values[0];
                    nextStopID = values[1];

                    // add directed edge with transfer time
                    if (values[2] == 0)
                        graph.addEdge(currentStopID, nextStopID, 2);
                    else if (values[2] == 2)
                        graph.addEdge(currentStopID, nextStopID, values[3]/100);
                }
                inputStream.close();
            } catch (FileNotFoundException e) {
                allGood = false;
                graph = null;
            }
        }
        else {
            allGood = false;
            graph = null;
        }


    }
}