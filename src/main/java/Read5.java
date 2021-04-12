
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Read5 {
	public static void main(String[] args) {
		File currentDir = new File("/Users/ckts/Coding/readTest/CURTest2/"); // current directory
		displayDirectoryFiles(currentDir);
	}

	public static void displayDirectoryFiles(File dir) {
		int countLine = 0;
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					displayDirectoryFiles(file);
				} else {
					FileInputStream input = new FileInputStream(file);
					ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(input));
					ZipEntry ze = null;

					while ((ze = zipInputStream.getNextEntry()) != null) {

						System.out.println("csv名稱：" + ze.getName());

						BufferedReader br = new BufferedReader(new InputStreamReader(zipInputStream));

						@SuppressWarnings("unused")
						String line;

						while ((line = br.readLine()) != null) {
							countLine++;
						}

					}

					zipInputStream.closeEntry();
					input.close();
				}
				System.out.println("有幾行：" + countLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
