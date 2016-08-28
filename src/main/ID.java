package main;

public class ID {

	private String registar;
	private String identification;
	private String value;
	private String type;

	public ID() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ID(String registar, String identification, String value, String type) {
		super();
		this.registar = registar;
		this.identification = identification;
		this.value = value;
		this.type = type;
	}

	public ID(String registar, String identification, String type) {
		super();
		this.registar = registar;
		this.identification = identification;
		this.type = type;
		this.value = null;
	}
	
	public String getRegistar() {
		return registar;
	}

	public void setRegistar(String registar) {
		this.registar = registar;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "ID [registar=" + registar + ", identification="
				+ identification + ", value=" + value + ", type=" + type + "]";
	}

}
