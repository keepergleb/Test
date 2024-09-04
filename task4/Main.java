import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Usage: java MinMovesToEqualArray <input_file>");
            return;
        }

        List<Integer> nums = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                nums.add(Integer.parseInt(line));
            }
        }

        System.out.println(minMoves(nums));
    }

    public static int minMoves(List<Integer> nums) {
        Collections.sort(nums);
        int median = nums.get(nums.size() / 2);
        int moves = 0;
        for (int i = 0; i < nums.size(); i++) {
            moves = moves + Math.abs(nums.get(i) - median);
        }
        return moves;
    }
}