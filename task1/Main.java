public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java CircularArrayPath <n> <m>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i + 1;
        }

        StringBuilder path = new StringBuilder();
        int index = 0;

        do {
            path.append(array[index]);
            index = (index + m - 1) % n;
        }
        while (array[index] != 1);

        System.out.println(path);
    }
}