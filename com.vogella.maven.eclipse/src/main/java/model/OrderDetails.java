package model;


import javax.persistence.*;


/*
 * @Author Pauli Vuolle-Apiala; 20/02/2021
 */

/**
 * The Class OrderDetails.
 */
@Entity
@Table(name = "Order_Details")
public class OrderDetails{
	
	/** The composite id. */
	@EmbeddedId
	private OrderDetailsCompositeKey compositeId;

	/** The product quantity. */
	@Column(name = "Product_Quantity")
	private int productQuantity;

	/** The product price. */
	@Column(name = "Product_Total_Price")
	private double productPrice;

	/**
	 * Instantiates a new order details.
	 *
	 * @param productQty the product qty
	 * @param productPrice the product price
	 */
	public OrderDetails(int productQty, double productPrice) {		
		this.productQuantity = productQty;
		this.productPrice = productPrice;
	}
	
	/**
	 * Instantiates a new order details.
	 */
	public OrderDetails() {}


	/**
	 * Gets the product quantity.
	 *
	 * @return the product quantity
	 */
	public int getProductQuantity() {
		return productQuantity;
	}

	/**
	 * Sets the product quantity.
	 *
	 * @param productQuantity the new product quantity
	 */
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	/**
	 * Gets the product price.
	 *
	 * @return the product price
	 */
	public double getProductPrice() {
		return productPrice;
	}

	/**
	 * Sets the product price.
	 *
	 * @param productPrice the new product price
	 */
	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}
	
	/**
	 * Gets the composite id.
	 *
	 * @return the composite id
	 */
	public OrderDetailsCompositeKey getCompositeId() {
		return this.compositeId;
	}
	
	/**
	 * Sets the composite id.
	 *
	 * @param compKey the new composite id
	 */
	public void setCompositeId(OrderDetailsCompositeKey compKey) {
		this.compositeId = compKey;
	}
	
	/**
	 * Gets the order detail total cost.
	 *
	 * @return the order detail total cost
	 */
	public double getOrderDetailTotalCost() {
		return productQuantity * productPrice;
	}

}
