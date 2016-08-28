package main;
public class Token {

	private String tokenName;
	private String attributeValue;

	public Token() {
		super();
	}

	public Token(String tokenName, String attributeValue) {
		super();
		this.tokenName = tokenName;
		this.attributeValue = attributeValue;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String toString() {
		return tokenName;
	}
	public void print() {
		System.out.println("Token: " + tokenName + "," + attributeValue);
	}

}
