package rover;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Start {

    static Random random = new Random();
    static LinkedHashMap<int[], String> mars;

    public static void main(String[] args) {

        //set random seed if seed given
        if (args.length > 1) {
            long seed = Long.parseLong(args[1]);
            random.setSeed(seed);
        }
        //initialise mars field
        init();
        String command = args[0];
        //do all commands and output
        for (int i = 0; i < command.length(); i++) {
            makeMove(command.charAt(i));
            out();
        }
    }

    public static void init() {
        mars = new LinkedHashMap<>();
        int width = 80;
        int hight = 20;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < hight; j++) {
                int[] p = new int[]{i, j};
                if (random.nextDouble() < 0.25 && !(width / 2 == i && hight / 2 == j))
                    mars.put(p, "#");
            }
        }
        mars.put(new int[]{width / 2, hight / 2}, "n");
        //output initial mars
        out();
    }

    public static int[] maximum(Set<int[]> set) {
        int[] x = new int[2];
        for (int[] e : set) {
            if (e[0] > x[0])
                x[0] = e[0];
            if (e[1] > x[1])
                x[1] = e[1];
        }
        return x;
    }

    public static String get(Map<int[], String> kloetze, int[] p) {
        Set<Entry<int[], String>> entrySet = kloetze.entrySet();
        for (Entry<int[], String> entry : entrySet) {
            if (entry.getKey()[0] == p[0] && entry.getKey()[1] == p[1])
                return entry.getValue();
        }
        return null;
    }

    public static void out() {

        int[] max = maximum(mars.keySet());
        for (int j = 0; j < max[1]; j++) {
            for (int i = 0; i < max[0]; i++) {
                //output free spaces
                if (get(mars, new int[]{i, j}) == null) {
                    System.out.print(" ");
                    continue;
                } else System.out.print(outObject(j, i));
            }
            System.out.println();
        }
        outputSeperation(max[0]);
    }

    private static void outputSeperation(int max) {
        for (int i = 0; i < max; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    private static String outObject(int j, int i) {
        // rover facing north
        if (get(mars, new int[]{i, j}).equals("n"))
            return ("^");
            // rover facing south
        else if (get(mars, new int[]{i, j}).equals("s"))
            return ("V");
            // rover facing east
        else if (get(mars, new int[]{i, j}).equals("e"))
            return (">");
            // rover facing west
        else if (get(mars, new int[]{i, j}).equals("w"))
            return ("<");
            //output stones
        else if (get(mars, new int[]{i, j}).equals("#"))
            return ("#");
        return null;
    }

    public static void makeMove(char input) {
        int[] position = findRover();
        //Input FORWARD
        if (input == 'f')
            moveForward(position);
            //Input BACK
        else if (input == 'b')
            moveBack(position);
            //Input LEFT
        else if (input == 'l')
            turnLeft(position);
            //Input RIGHT
        else if (input == 'r')
            turnRight(position);

    }

    private static void turnRight(int[] position) {
        if (get(mars, position).equals("w"))
            mars.put(position, "n");
        else if (get(mars, position).equals("e"))
            mars.put(position, "s");
        else if (get(mars, position).equals("n"))
            mars.put(position, "e");
        else if (get(mars, position).equals("s"))
            mars.put(position, "w");
    }

    private static void turnLeft(int[] position) {
        if (get(mars, position).equals("n"))
            mars.put(position, "w");
        else if (get(mars, position).equals("s"))
            mars.put(position, "e");
        else if (get(mars, position).equals("e"))
            mars.put(position, "n");
        else if (get(mars, position).equals("w"))
            mars.put(position, "s");
    }

    private static void moveForward(int[] position) {
        if (get(mars, position).equals("n")) {
            position[1]--;
            if (get(mars, position).equals("#")) {
                position[1]++;
            }
        } else if (get(mars, position).equals("s")) {
            position[1]++;
            if (get(mars, position).equals("#")) {
                position[1]--;
            }
        } else if (get(mars, position).equals("e")) {
            position[0]++;
            if (get(mars, position).equals("#")) {
                position[0]--;
            }
        } else if (get(mars, position).equals("w"))
            position[0]--;
        if (get(mars, position).equals("#")) {
            position[0]++;
        }
    }

    private static void moveBack(int[] position) {

        if (get(mars, position).equals("s")) {
            position[1]--;
            if (get(mars, position).equals("#")) {
                position[1]++;
            }
        } else if (get(mars, position).equals("n")) {
            position[1]++;
            if (get(mars, position).equals("#")) {
                position[1]--;
            }
        } else if (get(mars, position).equals("w")) {
            position[0]++;
            if (get(mars, position).equals("#")) {
                position[0]--;
            }
        } else if (get(mars, position).equals("e")) {
            position[0]--;
            if (get(mars, position).equals("#")) {
                position[0]++;
            }
        }


    }

    private static int[] findRover() {
        Set<Entry<int[], String>> entrySet = mars.entrySet();
        for (Entry<int[], String> entry : entrySet) {
            if (entry.getValue() != null && !entry.getValue().equals("#"))
                return entry.getKey();
        }
        throw new IllegalStateException("Rover missing in action");
    }

}
