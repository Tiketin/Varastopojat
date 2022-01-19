package model;

import java.time.LocalDateTime;
import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Purchase.
 *
 * @author Raoul Osman
 */

@Entity
@Table(name = "Purchase")
public class Purchase {

	/** The purchase id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Purchase_id")
	private int purchaseId; // Primary key
	
	/** The qty. */
	@Column(name = "Quantity")
	private int qty;
	
	/** The product id. */
	@Column(name = "Purchased_Product")
	private int productId;
	
	/** The local date. */
	@Column(name = "Date")
	private LocalDateTime localDate;	

	/** The supplier id. */
	@Column(name = "Supplier")
	private int supplierId;
	
	/** The purchase cost. */
	@Column(name = "Purchase_cost")
	private double purchaseCost;
	
	

	/**
	 * Instantiates a new purchase.
	 */
	public Purchase() {}

	/**
	 * Instantiates a new purchase.
	 *
	 * @param qty the qty
	 * @param purchasedProductId the purchased product id
	 * @param localDate the local date
	 * @param supplier the supplier
	 * @param cost the cost
	 */
	public Purchase( int qty, int purchasedProductId, LocalDateTime localDate, int supplier, double cost) {
		this.qty = qty;
		this.localDate = localDate;
		this.supplierId = supplier;
		this.productId = purchasedProductId;
		this.purchaseCost = cost;
	}
	
	/**
	 * Instantiates a new purchase.
	 *
	 * @param qty the qty
	 * @param purchasedProductId the purchased product id
	 * @param localDate the local date
	 * @param supplier the supplier
	 */
	public Purchase( int qty, int purchasedProductId, LocalDateTime localDate, int supplier) {
		this.qty = qty;
		this.localDate = localDate;
		this.supplierId = supplier;
		this.productId = purchasedProductId;
	}

	/**
	 * Gets the qty.
	 *
	 * @return the qty
	 */
	public int getQty() {
		return qty;
	}

	/**
	 * Sets the qty.
	 *
	 * @param qty the new qty
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}

	/**
	 * Gets the local date.
	 *
	 * @return the local date
	 */
	public LocalDateTime getLocalDate() {
		return localDate;
	}

	/**
	 * Sets the local date.
	 *
	 * @param localDate the new local date
	 */
	public void setLocalDate(LocalDateTime localDate) {
		this.localDate = localDate;
	}

	/**
	 * Sets the supplier.
	 *
	 * @param supplier the new supplier
	 */
	public void setSupplier(int supplier) {
		this.supplierId = supplier;
	}
	
	/**
	 * Gets the supplier id.
	 *
	 * @return the supplier id
	 */
	public int getSupplierId() {
		return supplierId;
	}
	
	/**
	 * Gets the purchase id.
	 *
	 * @return the purchase id
	 */
	public int getPurchaseId() {
		return purchaseId;
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
	 * Gets the purchase cost.
	 *
	 * @return the purchase cost
	 */
	public double getPurchaseCost() {
		return purchaseCost;
	}
	
	/**
	 * Sets the purchase cost.
	 *
	 * @param cost the new purchase cost
	 */
	public void setPurchaseCost(double cost) {
		this.purchaseCost = (double) Math.floor( cost * 100.0d + .5d) / 100.0d;
	}
	
	/**
	 * Gets the purchased product id.
	 *
	 * @return the purchased product id
	 */
	public int getPurchasedProductId() {
		return productId;
	}
	
	/**
	 * Sets the purchased product id.
	 *
	 * @param productId the new purchased product id
	 */
	public void setPurchasedProductId(int productId) {
		this.productId = productId;
	}
}