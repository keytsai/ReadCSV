import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class ReadCSV1 {
	public static void main(String[] args) throws CsvValidationException {

		String fileName = "/Users/ckts/Coding/readTest/CostUsageReport-overwrite-CSV-00001.csv";

		test(fileName);
	}

	public static void test(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis);
			CSVReader reader = new CSVReader(isr);

			@SuppressWarnings("unused")
			String[] nextLine;

			int countLine = 0;

			try {
				while ((nextLine = reader.readNext()) != null) {
					countLine++;
				}
			} catch (CsvValidationException e) {
				e.printStackTrace();
			}
			System.out.format("有" + countLine + "行");

			reader.close();
			isr.close();
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}

//FileInputStream fis = new FileInputStream(file);
//GZIPInputStream gis = new GZIPInputStream(fis);
//InputStreamReader isr = new InputStreamReader(gis);
//BufferedReader br = new BufferedReader(isr);
//CSVReader reader = new CSVReader(br);
