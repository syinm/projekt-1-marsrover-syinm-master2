package rover;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

public class Start {

    private static Random random = new Random();
    private static LinkedHashMap<int[], String> mars;

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

    private static void init() {
        mars = new LinkedHashMap<>();
        int width = 80;
        int hight = 20;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < hight; j++) {
                int[] position = new int[]{i, j};
                if (random.nextDouble() < 0.25 && !(width / 2 == i && hight / 2 == j))
                    mars.put(position, "#");
            }
        }
        mars.put(new int[]{width / 2, hight / 2}, "n");
        //output initial mars
        out();
    }

    private static int[] maximum(Set<int[]> set) {
        int[] x = new int[2];
        for (int[] e : set) {
            if (e[0] > x[0])
                x[0] = e[0];
            if (e[1] > x[1])
                x[1] = e[1];
        }
        return x;
    }

    private static String getObject(Map<int[], String> kloetze, int[] pos) {
        Set<Entry<int[], String>> entrySet = kloetze.entrySet();
        for (Entry<int[], String> entry : entrySet) {
            if (entry.getKey()[0] == pos[0] && entry.getKey()[1] == pos[1])
                return entry.getValue();
        }
        return null;
    }

    private static void out() {

        int[] max = maximum(mars.keySet());
        for (int j = 0; j < max[1]; j++) {
            for (int i = 0; i < max[0]; i++) {
                 System.out.print(outObject(j, i));
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
        //output free spaces
        if (null == getObject(mars, new int[]{i, j}))
            return(" ");
            // rover facing north
        else if ("n".equals(getObject(mars, new int[]{i, j})))
            return ("^");
            // rover facing south
        else if ("s".equals(getObject(mars, new int[]{i, j})))
            return ("V");
            // rover facing east
        else if ("e".equals(getObject(mars, new int[]{i, j})))
            return (">");
            // rover facing west
        else if ("w".equals(getObject(mars, new int[]{i, j})))
            return ("<");
            //output stones
        else if ("#".equals(getObject(mars, new int[]{i, j})))
            return ("#");
        return null;
    }

    private static void makeMove(char input) {

        int[] position = findRover();
        //Input FORWARD
        if (input == 'f')
            position = moveForward(position);
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

        if ("w".equals(getObject(mars, position)))
            mars.put(position, "n");
        else if ("e".equals(getObject(mars, position)))
            mars.put(position, "s");
        else if ("n".equals(getObject(mars, position)))
            mars.put(position, "e");
        else if ("s".equals(getObject(mars, position)))
            mars.put(position, "w");
    }

    private static void turnLeft(int[] position) {

        if ("n".equals(getObject(mars, position)))
            mars.put(position, "w");
        else if ("s".equals(getObject(mars, position)))
            mars.put(position, "e");
        else if ("e".equals(getObject(mars, position)))
            mars.put(position, "n");
        else if ("w".equals(getObject(mars, position)))
            mars.put(position, "s");
    }

    private static int[] moveForward(int[] position) {

        if ("n".equals(getObject(mars, position)) && !"#".equals(getObject(mars, new int[]{position[0], position[1] - 1})))
            return new int[]{position[0], position[1]--};
        else if ("s".equals(getObject(mars, position)) && !"#".equals(getObject(mars, new int[]{position[0], position[1] + 1})))
            return new int[]{position[0], position[1]++};
        else if ("e".equals(getObject(mars, position)) && !"#".equals(getObject(mars, new int[]{position[0] + 1, position[1]})))
            return new int[]{position[0]++, position[1]};
        else if ("w".equals(getObject(mars, position)) && !"#".equals(getObject(mars, new int[]{position[0] - 1, position[1]})))
            return new int[]{position[0]--, position[1]};
        else
            return position;
    }

    private static int[] moveBack(int[] position) {

        if ("s".equals(getObject(mars, position)) && !"#".equals(getObject(mars, new int[]{position[0], position[1] - 1})))
            return new int[]{position[0], position[1]--};
        else if ("n".equals(getObject(mars, position)) && !"#".equals(getObject(mars, new int[]{position[0], position[1] + 1})))
            return new int[]{position[0], position[1]++};
        else if ("w".equals(getObject(mars, position)) && !"#".equals(getObject(mars, new int[]{position[0] + 1, position[1]})))
            return new int[]{position[0]++, position[1]};
        else if ("e".equals(getObject(mars, position)) && !"#".equals(getObject(mars, new int[]{position[0] - 1, position[1]})))
            return new int[]{position[0]--, position[1]};
        else
            return position;
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
