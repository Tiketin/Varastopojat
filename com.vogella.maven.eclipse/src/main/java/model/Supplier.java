package model;

import javax.persistence.*;


/**
 * The Class Supplier.
 */
@Entity
@Table(name = "Supplier")
public class Supplier {
	
	/** The supplier id. */
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Supplier_Id")
	private int supplierId; 
	
	/** The name of supplier. */
	@Column(name = "Supplier_name")
	private String nameOfSupplier;	
	
	/** The supplier address. */
	@Column(name = "Supplier_address")
	private String supplierAddress;

	/**
	 * Instantiates a new supplier.
	 */
	public Supplier() {}

	/**
	 * Instantiates a new supplier.
	 *
	 * @param nameOfSupplier the name of supplier
	 * @param address the address
	 */
	public Supplier( String nameOfSupplier, String address) {
		this.nameOfSupplier = nameOfSupplier;
		this.supplierAddress = address;
	}
	
	/**
	 * Instantiates a new supplier.
	 *
	 * @param nameOfSupplier the name of supplier
	 */
	public Supplier(String nameOfSupplier) {
		this.nameOfSupplier = nameOfSupplier;
	}

	/**
	 * Gets the name of supplier.
	 *
	 * @return the name of supplier
	 */
	public String getNameOfSupplier() {
		return nameOfSupplier;
	}

	/**
	 * Sets the name of supplier.
	 *
	 * @param nameOfSupplier the new name of supplier
	 */
	public void setNameOfSupplier(String nameOfSupplier) {
		this.nameOfSupplier = nameOfSupplier;
	}
	
	/**
	 * Gets the supplier id.
	 *
	 * @return the supplier id
	 */
	public int getSupplierId() {
		return this.supplierId;
	}
	
	/**
	 * Gets the supplier address.
	 *
	 * @return the supplier address
	 */
	public String getSupplierAddress() {
		return supplierAddress;
	}
	
	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.supplierAddress = address;
	}

}