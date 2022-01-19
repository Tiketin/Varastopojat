package model;

import javax.persistence.*;


/**
 * The Class ProductStock.
 *
 * @author Pauli Vuolle-Apiala; 04/02/2021
 */

@Entity
@Table(name= "ProductStock")
public class ProductStock {
	
	/** The product id. */
	@EmbeddedId
	private ProductStockCompositeKey compositeId;
	
	/** The quantity. */
	@Column
	private int quantity;
	
	
	
	/**
	 * Instantiates a new product stock.
	 *
	 * @param stockId the stock id
	 * @param qty the qty
	 */
	public ProductStock(int qty) {
		this.quantity = qty;
	}
	
	/**
	 * Instantiates a new product stock.
	 */
	public ProductStock() {}
	
	
	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**
	 * Decrease quantity.
	 *
	 * @param decrement the decrement
	 */
	public void decreaseQuantity(int decrement) {
		this.quantity -= decrement;
	}
	
	/**
	 * Increase quantity.
	 *
	 * @param increment the increment
	 */
	public void increaseQuantity(int increment) {
		this.quantity += increment;
	}
	
	
	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return this.quantity;
	}
	
	public ProductStockCompositeKey getCompositeId() {
		return compositeId;
	}
	
	public void setCompositeId(ProductStockCompositeKey compositeKey) {
		this.compositeId = compositeKey;
	}
}
