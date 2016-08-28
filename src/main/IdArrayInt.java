package main;
public class IdArrayInt {
	String id;
	int idLength;

	public IdArrayInt(String id, int idLength) {
		super();
		this.id = id;
		this.idLength = idLength;
	}

	public IdArrayInt() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getIdLength() {
		return idLength;
	}

	public void setIdLength(int idLength) {
		this.idLength = idLength;
	}

}
