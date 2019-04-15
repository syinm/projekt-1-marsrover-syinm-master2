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

        if (args.length > 1) {
            long seed = Long.parseLong(args[1]);
            random.setSeed(seed);
        }
        init();
        String pg = args[0];
        out();
        for (int i = 0; i < pg.length(); i++) {
            makeMove(pg.charAt(i));
            out();
        }
    }

	public static void init() {
		mars = new LinkedHashMap<>();
		int width = 80;
		int hight = 20;
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < hight; j++) {
				int[] p = new int[] { i, j };
				if (random.nextDouble() < 0.25 && !(width/2 == i && hight/2 == j))
					mars.put(p, "#");
			}
		}
		mars.put(new int[] { width/2, hight/2 }, "n");
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
        // Set<int[]> keySet = mars.keySet();
        // for (int[] e : keySet) {
        // if (e[0] == 39 && e[1] == 10)
        // System.err.println(mars.get(e) + " " + e.hashCode());
        // }

        int[] max = maximum(mars.keySet());
        for (int j = 0; j < max[1]; j++) {
            for (int i = 0; i < max[0]; i++) {
                // System.out.println(i + "," + j + ": " + get(mars, new int[] { i, j }));

                //output freespaces
                if (get(mars, new int[]{i, j}) == null) {
                    System.out.print(" ");
                    continue;
                }
                //output stones
                if (get(mars, new int[]{i, j}).equals("#"))
                    System.out.print("#");
                //output rover facing north
                else if (get(mars, new int[]{i, j}).equals("n"))
                    System.out.print("^");
                //output rover facing south
                else if (get(mars, new int[]{i, j}).equals("s"))
                    System.out.print("V");
                //output rover facing east
                else if (get(mars, new int[]{i, j}).equals("e"))
                    System.out.print(">");
                //output rover facing west
                if (get(mars, new int[]{i, j}).equals("w"))
                    System.out.print("<");

            }
            System.out.println();
        }
        //output seperation line
        for (int i = 0; i < max[0]; i++) {
            System.out.print("=");
        }
        System.out.println();
    }

    public static void makeMove(char input) {
	    //Input FORWARD
        if (input == 'f') {
            int[] position = findRover();
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
            //Input BACK
        } else if (input == 'b') {
            int[] position = findRover();
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
            //Input LEFT
        } else if (input == 'l') {
            int[] position = findRover();
            if (get(mars, position).equals("n"))
                mars.put(position, "w");
            else if (get(mars, position).equals("s"))
                mars.put(position, "e");
            else if (get(mars, position).equals("e"))
                mars.put(position, "n");
            else if (get(mars, position).equals("w"))
                mars.put(position, "s");
            //Input RIGHT
        } else if (input == 'r') {
            int[] position = findRover();
            if (get(mars, position).equals("w"))
                mars.put(position, "n");
            else if (get(mars, position).equals("e"))
                mars.put(position, "s");
            else if (get(mars, position).equals("n"))
                mars.put(position, "e");
            else if (get(mars, position).equals("s"))
                mars.put(position, "w");
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
