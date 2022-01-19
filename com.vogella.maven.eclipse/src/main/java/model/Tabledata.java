package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/**
 * The Class Tabledata.
 */
public class Tabledata {

	/** The id. */
	private final SimpleIntegerProperty id;
	
	/** The name. */
	private final SimpleStringProperty name;
	
	/** The qty. */
	private final SimpleIntegerProperty qty;
	
	/** The price. */
	private final SimpleDoubleProperty price;
			
	/**
	 * Instantiates a new tabledata.
	 *
	 * @param sID the s ID
	 * @param sName the s name
	 * @param sQty the s qty
	 * @param sPrice the s price
	 */
	public Tabledata(int sID, String sName, int sQty, Double sPrice) {
		this.id = new SimpleIntegerProperty(sID);
		this.name = new SimpleStringProperty(sName);
		this.qty = new SimpleIntegerProperty(sQty);
		this.price = new SimpleDoubleProperty(sPrice);
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id.get();
	}
	
	/**
	 * Sets the id.
	 *
	 * @param v the new id
	 */
	public void setId(Integer v) {
		id.set(v);
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name.get();
	}
	
	/**
	 * Sets the name.
	 *
	 * @param v the new name
	 */
	public void setName(String v) {
		name.set(v);
	}
	
	/**
	 * Gets the qty.
	 *
	 * @return the qty
	 */
	public Integer getQty() {
		return qty.get();
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
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price.get();
	}
	
	/**
	 * Sets the price.
	 *
	 * @param v the new price
	 */
	public void setPrice (Double v) {
		price.set(v);
	}
}
