package ken.read.csv;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadCSV3 {
	public static void main(String[] args) {

		Map<String, Integer> map = new HashMap<>();
		int countLine = 0;

		String line = "";
		String splitBy = ",";
		try {
			BufferedReader br = new BufferedReader(
					new FileReader("/Users/ckts/Coding/readTest/CostUsageReport-overwrite-Athena-00035.csv"));
			while ((line = br.readLine()) != null) {

				String[] cells = line.split(splitBy);
				countLine = map.containsKey(cells[8]) ? map.get(cells[8]) : 0;
				map.put(cells[8], countLine + 1);

			}
			map.remove("lineItem/UsageAccountId");
			for (String i : map.keySet()) {
				System.out.println("UsageAccountId：" + i + "   行數：" + map.get(i));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}