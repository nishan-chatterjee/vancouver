import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class StopNameFinder {

    private static class Bus {
        private final int id;
        private final int code;
        private final String name;
        private final String desc;
        private final double lat;
        private final double lon;
        private final String zone_id;
        private final String url;
        private final int location_type;
        // private final byte parent_station;

        public Bus(int id, int code, String name, String desc, double lat, double lon, String zone_id, String url,
                int location_type) {
            this.id = id;
            this.code = code;
            this.name = name;
            this.desc = desc;
            this.lat = lat;
            this.lon = lon;
            this.zone_id = zone_id;
            this.url = url;
            this.location_type = location_type;
        }

        @Override
        public String toString() {
            String result = String.format(
                    "id: %d, code: %d, name %s, desc: %s, lat: %f, lon: %f, zone_id: %s url: %s, location_type: %d", id,
                    code, name, desc, lat, lon, zone_id, url, location_type);
            return result;
        }

        public static HashMap<String, Bus> getData(String filename) {
            HashMap<String, Bus> buses = new HashMap<>();
            File stops_txt = new File(filename);
            String vals[] = null;
            try {
                Scanner sc = new Scanner(stops_txt);
                sc.nextLine();
                while (sc.hasNext()) {
                    String line = sc.nextLine();
                    vals = line.split(",");
                    int index = 0;
                    int id = Integer.parseInt(vals[index++]);
                    int code = vals[index].equals(" ") ? -1 : Integer.parseInt(vals[index]);
                    index++;
                    String name = vals[index++];
                    String desc = vals[index++];
                    double lat = Double.parseDouble(vals[index++]);
                    double lon = Double.parseDouble(vals[index++]);
                    String zone_id = vals[index++];
                    String url = vals[index++];
                    int location_type = Integer.parseInt(vals[index++]);
                    if (name.length() > 2) {
                        String start = name.substring(0, 2);
                        if (start.equals("WB") || start.equals("NB") || start.equals("SB") || start.equals("EB")) {
                            String end = name.substring(3);
                            name = end + " " + start;
                        }
                    }
                    Bus b = new Bus(id, code, name, desc, lat, lon, zone_id, url, location_type);
                    buses.put(name, b);
                }
                sc.close();
            } catch (Exception e) {
                System.out.println(e);
                System.exit(0);
            }
            return buses;
        }
    }

    public static void findBusStops(String busName) {
        HashMap<String, Bus> buses = Bus.getData("../inputs/stops.txt");
        TST tst = new TST();
        for (Map.Entry<String, Bus> pair : buses.entrySet()) {
            tst.put(pair.getKey());
        }

        String[] stopnames = tst.getMultiple(busName);
        Bus[] bs = new Bus[stopnames.length];
        for (int i = 0; i < bs.length; i++)
            bs[i] = buses.get(stopnames[i]);

        for (final Bus b : bs)
            System.out.println(b);
    }

    public static void main(String... args) {
        findBusStops("FRASER");
    }
}