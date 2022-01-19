package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/*
 * @Author Pauli Vuolle-Apiala; 5/3/2021
 */

/**
 * The Class OrderDetailsCompositeKey.
 */
@Embeddable
public class OrderDetailsCompositeKey implements Serializable {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 910135675225208014L;
	
	/** The product id. */
	@Column(name = "productId")
	private int productId;

	/** The order id. */
	@Column(name = "orderId")
	private int orderId;

	/**
	 * Instantiates a new order details composite key.
	 */
	public OrderDetailsCompositeKey() {
	};

	/**
	 * Instantiates a new order details composite key.
	 *
	 * @param productId the product id
	 * @param orderId the order id
	 */
	public OrderDetailsCompositeKey(int productId, int orderId) {
		this.productId = productId;
		this.orderId = orderId;
	}

	/**
	 * Gets the product id.
	 *
	 * @return the product id
	 */
	public int getProductId() {
		return productId;
	}

	/**
	 * Sets the product id.
	 *
	 * @param productId the new product id
	 */
	public void setProductId(int productId) {
		this.productId = productId;
	}

	/**
	 * Gets the order id.
	 *
	 * @return the order id
	 */
	public int getOrderId() {
		return orderId;
	}

	/**
	 * Sets the order id.
	 *
	 * @param orderId the new order id
	 */
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof OrderDetailsCompositeKey))
			return false;
		OrderDetailsCompositeKey that = (OrderDetailsCompositeKey) obj;
		return Objects.equals(getProductId(), that.getProductId())
				&& Objects.equals(getOrderId(), that.getOrderId());
	}
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getProductId(), getOrderId());
	}

}
