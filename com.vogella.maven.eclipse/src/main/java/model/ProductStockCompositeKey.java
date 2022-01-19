package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/*
 * @Author Pauli Vuolle-Apiala; 26/03/2021
 */

@Embeddable
public class ProductStockCompositeKey implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -229715754602410765L;
	
	@Column(name = "productId")
	private int productId;
	
	@Column(name = "locationId")
	private int locationId;
	
	public ProductStockCompositeKey() {}
	
	public ProductStockCompositeKey(int productId, int locationId) {
		this.productId = productId;
		this.locationId = locationId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof ProductStockCompositeKey))
			return false;
		ProductStockCompositeKey that = (ProductStockCompositeKey) obj;
		return Objects.equals(getProductId(), that.getProductId())
				&& Objects.equals(getLocationId(), that.getLocationId());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getProductId(), getLocationId());
	}

}