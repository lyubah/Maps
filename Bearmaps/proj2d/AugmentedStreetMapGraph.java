package bearmaps.proj2d;

import bearmaps.proj2ab.WeirdPointSet;
import bearmaps.proj2c.streetmap.StreetMapGraph;
import bearmaps.proj2c.streetmap.Node;
import bearmaps.proj2ab.Point;
import bearmaps.proj2ab.PointSet;

import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {
    Map<Point, Node> pointNodeMap;
    List<Node> node;
    List<Point> point;
    PointSet pointSet;
    Map<String, Node> cleanNames;


    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        //Lets first initialize all of the tools

        //storing every Point node pair into a HashMap
        pointNodeMap = new HashMap<>();
        // You might find it helpful to uncomment the line below:
        node = this.getNodes();
        //for every node I want the associated points
        point = new ArrayList<>();

        //I want to store words efficiently for autocomplete
        cleanNames = new HashMap<String, Node>();


        // for each node I need to create (lon,lat) point
        //then I have to fill the respective data into each relevant ds
        for (Node n : node) {
//            String name = n.name();
//            if (name != null) {
//                cleanNames.put(name, n);
//            }
            if (neighbors(n.id()).size() > 0) {
                Point p = new Point(n.lon(), n.lat());
                pointNodeMap.put(p, n);
                point.add(p);
            }
        }
        pointSet = new WeirdPointSet(point);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     *
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return pointNodeMap.get(pointSet.nearest(lon, lat)).id();
    }

    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     *
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     * @source: https://beginnersbook.com/2013/12/java-string-startswith-method-example/#:~:text=The%20startsWith()%20method%20of,the%20specified%20letter%20or%20word.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        //for storing names if matched
        List<String> name = new ArrayList<>();
        //first I need to clean my string
        String cleaned = cleanString(prefix);
        //iterate through key set containing
        for (String n : cleanNames.keySet()) {
            String cleanedString = cleanString(n);
            //Using String Class
            if (cleanedString.startsWith(cleaned)) {
                name.add(n);
            }
        }
        return name;
    }


    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     *
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        ArrayList<Map<String, Object>> results = new ArrayList<>();
//
//        String cleanedName = cleanString(locationName);
//        for (String str : cleanNames.keySet()) {
//            String key = cleanString(str);
//
//            if (key.equals(cleanedName)) {
//                Map<String, Object> map = new HashMap<>();
//                Node n = cleanNames.get(locationName);
//                map.put("name", str);
//                map.put("id", n.id());
//                map.put("lat", n.lat());
//                map.put("lon", n.lon());
//                results.add(map);
//            }
//        }
        return null;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     *
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }


}

