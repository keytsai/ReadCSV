
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ReadCSV4 {
	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		ReadCSV4 test = new ReadCSV4();
		File currentDir = new File("/Users/ckts/Coding/readTest/CURTest2/");
		test.displayDirectoryFiles(currentDir);
	}

	public static void displayDirectoryFiles(File dir) {
//		int countLine = 0;
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					displayDirectoryFiles(file);
				} else {
					FileInputStream fi = new FileInputStream(file);
					ZipInputStream zi = new ZipInputStream(new BufferedInputStream(fi));
					ZipEntry ze = null;

					while ((ze = zi.getNextEntry()) != null) {

						System.out.println("csv名稱：" + ze.getName());

						Map<String, Integer> map = new HashMap<>();

						int countLine = 0;
						String line = "";
						String splitBy = ",";

						try {
							BufferedReader br = new BufferedReader(new FileReader(file));
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

					zi.closeEntry();
					fi.close();
				}
			}
//			if (countLine > 0) {
//				System.out.println("有幾行：" + countLine);
//			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}