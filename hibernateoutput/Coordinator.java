// default package
// Generated Feb 3, 2015 12:02:04 PM by Hibernate Tools 4.0.0

/**
 * Coordinator generated by hbm2java
 */
public class Coordinator implements java.io.Serializable {

	private int id;
	private String ieeeAddress;
	private String serialNumber;
	private Integer validation;

	public Coordinator() {
	}

	public Coordinator(int id) {
		this.id = id;
	}

	public Coordinator(int id, String ieeeAddress, String serialNumber,
			Integer validation) {
		this.id = id;
		this.ieeeAddress = ieeeAddress;
		this.serialNumber = serialNumber;
		this.validation = validation;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIeeeAddress() {
		return this.ieeeAddress;
	}

	public void setIeeeAddress(String ieeeAddress) {
		this.ieeeAddress = ieeeAddress;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getValidation() {
		return this.validation;
	}

	public void setValidation(Integer validation) {
		this.validation = validation;
	}

}
