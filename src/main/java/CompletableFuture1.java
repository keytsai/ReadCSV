import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFuture1 {
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ReadCSV4 read1 = new ReadCSV4();
		ReadCSV4 read2 = new ReadCSV4();

		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			File currentDir = new File("/Users/ckts/Coding/newBilling3/amazon-billing/CUR/CUR1");
			read1.displayDirectoryFiles(currentDir);
		});

		future.get();
		File currentDir = new File("/Users/ckts/Coding/newBilling3/amazon-billing/CUR/CUR2");
		read2.displayDirectoryFiles(currentDir);
	}
}