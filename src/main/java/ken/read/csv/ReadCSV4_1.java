package ken.read.csv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadCSV4_1 {

	public static void main(String[] args) throws IOException {

		File currentDir = new File("/Users/ckts/Coding/newBilling3/amazon-billing/CUR/");
		String writeToJsonFilePath = "/Users/ckts/Coding/readTest/ken_feb.json";

		ReadCSV4_1 writeToJsonFile = new ReadCSV4_1();
		writeToJsonFile.writeJson(currentDir, writeToJsonFilePath);

	}

	Map<String, Integer> map = new HashMap<>();
	ObjectMapper objectMapper = new ObjectMapper();

	public void writeJson(File currentDir, String writeToJsonFilePath) throws IOException {
		displayDirectoryFiles(currentDir);
//		System.out.println(map);

		try {
			objectMapper.writeValue(new File(writeToJsonFilePath), map);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void displayDirectoryFiles(File dir) throws IOException {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					displayDirectoryFiles(file);
				} else {
					FileInputStream fi = new FileInputStream(file);
					ZipInputStream zi = new ZipInputStream(new BufferedInputStream(fi));

					while ((zi.getNextEntry()) != null) {
						readCSV(zi);
					}

					zi.closeEntry();
					fi.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void readCSV(ZipInputStream zi) throws IOException {

		int countLine = 0;
		String line = "";
		String splitBy = ",";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(zi));
			while ((line = br.readLine()) != null) {

				String[] cells = line.split(splitBy);
				countLine = map.containsKey(cells[8]) ? map.get(cells[8]) : 0;
				map.put(cells[8], countLine + 1);
			}
			map.remove("lineItem/UsageAccountId");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
