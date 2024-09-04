import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java TestReportGenerator <values_file> <tests_file> <report_file>");
            return;
        }

        String valuesFile = args[0];
        String testsFile = args[1];
        String reportFile = args[2];

        try {
            BufferedReader valuesReader = new BufferedReader(new FileReader(valuesFile));
            StringBuilder valuesContent = new StringBuilder();
            String line;
            while ((line = valuesReader.readLine()) != null) {
                valuesContent.append(line);
            }
            valuesReader.close();

            Matcher valuesMatcher = Pattern.compile("id\": (.*?),\\s*\"value\": \"(.*?)\"\\s*}").matcher(valuesContent);

            HashMap valuesMap = new HashMap();

            while (valuesMatcher.find()) {
                valuesMap.put(valuesMatcher.group(1), valuesMatcher.group(2));
            }

            BufferedReader testsReader = new BufferedReader(new FileReader(testsFile));
            StringBuilder testsContent = new StringBuilder();
            while ((line = testsReader.readLine()) != null) {
                testsContent.append(line);
            }
            testsReader.close();

            String report = testsContent.toString();

            Matcher testsBaseMatcher = Pattern.compile("(\"id\": (\\d+),[^\\{]*\"value\": \"\")").matcher(testsContent);

            HashMap testsMap = new HashMap();
            ArrayList testsList = new ArrayList();

            while (testsBaseMatcher.find()) {
                testsList.add(testsBaseMatcher.group(2));
                testsMap.put(testsBaseMatcher.group(2), testsBaseMatcher.group(1));
            }

            for (int i = 0; i < testsList.size(); i++) {
                if (valuesMap.containsKey(testsList.get(i))) {
                    report = report.replace(testsMap.get(testsList.get(i)).toString(),
                            testsMap.get(testsList.get(i)).toString().substring(0, testsMap.get(testsList.get(i)).toString().length() - 1)
                                    + valuesMap.get(testsList.get(i)) + "\"");
                }
            }

            FileWriter reportWriter = new FileWriter(reportFile);
            reportWriter.write(report);
            reportWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}