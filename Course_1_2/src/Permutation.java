import edu.princeton.cs.algs4.StdIn;

/**
 * Created by pchen on 2/4/2017.
 */

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> randomString = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randomString.enqueue(str);
        }
        int counter = 0;
        while (counter < Integer.parseInt(args[0])) {
            counter++;
            System.out.println(randomString.dequeue());
        }
    };
}
