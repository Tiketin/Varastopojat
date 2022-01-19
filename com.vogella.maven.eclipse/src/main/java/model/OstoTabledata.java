package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 * The Class OstoTabledata.
 */
public class OstoTabledata {
	

	private final SimpleIntegerProperty purchaseId;

	private final SimpleIntegerProperty productId;
	
	/** The qty. */
	private final SimpleIntegerProperty qty;
	
	/** The date. */
	private final SimpleStringProperty date;
	
	/** The supplier id. */
	private final SimpleIntegerProperty supplierId;
	
	/**
	 * Instantiates a new osto tabledata.
	 *
	 * @param sID the s ID
	 * @param productId the product id
	 * @param sQty the s qty
	 * @param sDate the s date
	 * @param sSupplierId the s supplier id
	 */
	public OstoTabledata(int sID, int productId, int sQty, String sDate, int sSupplierId) {
		this.purchaseId = new SimpleIntegerProperty(sID);
		this.productId = new SimpleIntegerProperty(productId);
		this.qty = new SimpleIntegerProperty(sQty);
		this.date = new SimpleStringProperty(sDate);
		this.supplierId = new SimpleIntegerProperty(sSupplierId);
	}
	
	public Integer getPurchaseId() {
		return purchaseId.get();
	}
	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public Integer getProductId() {
		return productId.get();
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		return date.get();
	}
	
	/**
	 * Gets the qty.
	 *
	 * @return the qty
	 */
	public Integer getQty() {
		return qty.get();
	}
	
	public Integer getSupplierId() {

		return supplierId.get();
	}
	
	/**
	 * Sets the id.
	 *
	 * @param v the new id
	 */
	public void setId(Integer v) {
		purchaseId.set(v);
	}
	
	/**
	 * Sets the product id.
	 *
	 * @param v the new product id
	 */
	public void setProductId(Integer v) {
		productId.set(v);
	}
	
	/**
	 * Sets the date.
	 *
	 * @param v the new date
	 */
	public void setDate(String v) {
		date.set(v);
	}
	
	/**
	 * Sets the qty.
	 *
	 * @param v the new qty
	 */
	public void setQty(Integer v) {
		qty.set(v);
	}
	
	/**
	 * Sets the supplier ID.
	 *
	 * @param v the new supplier ID
	 */
	public void setSupplierID(Integer v) {
		supplierId.set(v);
	}
}
