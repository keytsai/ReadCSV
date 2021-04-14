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

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadCSV4_1 {
	public static void main(String[] args) throws IOException {

		ReadCSV4_1 test = new ReadCSV4_1();
		File currentDir = new File("/Users/ckts/Coding/newBilling3/amazon-billing/CUR/");
//		File currentDir = new File("/Users/ckts/Coding/readTest/CURTest2/");
//		File currentDir = new File("/Users/ckts/Coding/newBilling3/amazon-billing/CUR/CUR1/");
		test.displayDirectoryFiles(currentDir);
	}
	Map<String, Integer> map = new HashMap<>();

	public void displayDirectoryFiles(File dir) throws IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
//		FileOutputStream fos = new FileOutputStream(new File("/Users/ckts/Coding/readTest/kkk.json"));
//		JsonGenerator g = objectMapper.getFactory().createGenerator(fos);
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					displayDirectoryFiles(file);
				} else {
					FileInputStream fi = new FileInputStream(file);
					ZipInputStream zi = new ZipInputStream(new BufferedInputStream(fi));

					while ((zi.getNextEntry()) != null) {

//						System.out.println("csv名稱：" + ze.getName());

						int countLine = 0;
//						AtomicInteger count = new AtomicInteger(0);
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
					zi.closeEntry();
					fi.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println(map);
		objectMapper.writeValue(new File("/Users/ckts/Coding/readTest/kkk.json"), map);
	}
	
}