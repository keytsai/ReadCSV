package ken.read.csv;

import java.util.concurrent.atomic.AtomicInteger;

public class JavaToJson {
	String AccId ;
	AtomicInteger lineCount;
	
	public JavaToJson(String accId, AtomicInteger lineCount) {
		super();
		AccId = accId;
		this.lineCount = lineCount;
	}
	
	public String getAccId() {
		return AccId;
	}
	public void setAccId(String accId) {
		AccId = accId;
	}
	public AtomicInteger getLineCount() {
		return lineCount;
	}
	public void setLineCount(AtomicInteger lineCount) {
		this.lineCount = lineCount;
	}
	
	

}
