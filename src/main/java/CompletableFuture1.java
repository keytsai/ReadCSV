import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFuture1 {
	public static void main(String[] args) throws InterruptedException, ExecutionException {

		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(1000);
				System.out.println("hello");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		future.get();
		System.out.println("world");
	}
}