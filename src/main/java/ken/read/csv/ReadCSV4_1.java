package ken.read.csv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipInputStream;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadCSV4_1 {
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {

		ReadCSV4_1 test = new ReadCSV4_1();
//		File currentDir = new File("/Users/ckts/Coding/readTest/0001");
//		File currentDir = new File("/Users/ckts/Coding/newBilling3/amazon-billing/CUR/");
		File currentDir = new File("/Users/ckts/Coding/readTest/CURTest2/");
//		File currentDir = new File("/Users/ckts/Coding/newBilling3/amazon-billing/CUR/CUR1/");
		test.displayDirectoryFiles(currentDir);
	}
	

	public static void displayDirectoryFiles(File dir) throws IOException {
		Map<String, Integer> map = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		FileOutputStream fos = new FileOutputStream(new File("/Users/ckts/Coding/readTest/kkk.json"));
		JsonGenerator g = objectMapper.getFactory().createGenerator(fos);
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
			for (String i : map.keySet()) {
				System.out.println("UsageAccountId：" + i + "   行數：" + map.get(i));
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		objectMapper.writeValue(g, map);
	}
	
}