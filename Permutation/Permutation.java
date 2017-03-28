import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> q = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        String[] s = StdIn.readAllStrings();
        StdRandom.shuffle(s);
        for (int i = 0; i < k; i++) {
            q.enqueue(s[i]);
        }

        while (k-- > 0) {
            StdOut.println(q.dequeue());
        }
    }
}
