package ken.read.csv;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipInputStream;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadCSV4_1 {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {

		long startTime = System.currentTimeMillis();
		System.out.println("開始執行");

		File currentDir = new File("/Users/ckts/Coding/newBilling3/amazon-billing/CUR/");
		String writeToJsonFilePath = "/Users/ckts/Coding/readTest/ken_feb.json";

		ReadCSV4_1 writeToJsonFile = new ReadCSV4_1();
		CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {

			try {
				Map<String, AtomicInteger> map = new HashMap<>();
				map = writeToJsonFile.displayDirectoryFiles(currentDir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		task.get();
		writeToJsonFile.writeJson(currentDir, writeToJsonFilePath);

		long endTime = System.currentTimeMillis();
		long totalSeconds = TimeUnit.MILLISECONDS.toSeconds(endTime - startTime);
		System.out.println("完成，執行時間：" + totalSeconds + "秒");

	}

	Map<String, AtomicInteger> map = new HashMap<>();
	ObjectMapper objectMapper = new ObjectMapper();

	public void writeJson(File currentDir, String writeToJsonFilePath) throws IOException {

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

	public Map<String, AtomicInteger> displayDirectoryFiles(File dir) throws IOException {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					displayDirectoryFiles(file);
				} else {
					FileInputStream fi = new FileInputStream(file);
					ZipInputStream zi = new ZipInputStream(new BufferedInputStream(fi));

					while ((zi.getNextEntry()) != null) {
						map = readCSV(zi);
					}

					zi.closeEntry();
					fi.close();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public Map<String, AtomicInteger> readCSV(ZipInputStream zi) throws IOException {

		String line = "";
		String splitBy = ",";

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(zi));
			while ((line = br.readLine()) != null) {

				String[] cells = line.split(splitBy);
				AtomicInteger countLine = new AtomicInteger(0);
				countLine = map.containsKey(cells[8]) ? map.get(cells[8]) : countLine;
				map.put(cells[8], countLine);
				map.get(cells[8]).getAndIncrement();
			}
			map.remove("lineItem/UsageAccountId");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
