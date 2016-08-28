package main;

public class ForOperation {
	String operation;
	String mipsOperation;
	public ForOperation(String operation, String mipsOperation) {
		super();
		this.operation = operation;
		this.mipsOperation = mipsOperation;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getMipsOperation() {
		return mipsOperation;
	}
	public void setMipsOperation(String mipsOperation) {
		this.mipsOperation = mipsOperation;
	}
	@Override
	public String toString() {
		return "ForOperation [operation=" + operation + ", mipsOperation="
				+ mipsOperation + "]";
	}
	
	
}
