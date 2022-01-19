package model;

import java.util.*;

import javax.persistence.*;


/**
 * The Class Products.
 *
 * @author Raoul Osman
 */

@Entity
@Table(name = "Products")
public class Products{

	/** The product id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int productId;

	/** The name. */
	@Column
	private String name;
	
	/** The quantity. */
	@Column
	private int quantity;
	
	/** The price. */
	@Column
	private double price;

	/** The product stock. */
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "productId")
	private Set<ProductStock> productStock;

	/**
	 * Instantiates a new products.
	 */
	public Products() { 
		
	} 
	
	/**
	 * Instantiates a new products.
	 *
	 * @param id the id
	 * @param name the name
	 * @param qty the qty
	 * @param price the price
	 */
	public Products(int id, String name, int qty, double price) {
		this.productId = id;
		this.name =  name;
		this.quantity = qty;
		this.price = price;
	}
	
	/**
	 * Instantiates a new products.
	 *
	 * @param name the name
	 * @param qty the qty
	 * @param price the price
	 * @param productStock the product stock
	 */
	public Products(String name, int qty, double price, Set<ProductStock> productStock) {
		super();
		this.quantity = qty;
		this.name = name;
		this.price = price;
		this.productStock = productStock;
	}
	
	/**
	 * Instantiates a new products.
	 *
	 * @param name the name
	 * @param qty the qty
	 * @param price the price
	 */
	public Products(String name, int qty, double price) {
		super();
		this.quantity = qty;
		this.name = name;
		this.price = price;
	}
	
	/**
	 * Instantiates a new products.
	 *
	 * @param name the name
	 * @param price the price
	 */
	public Products(String name, double price) {
		this.name = name;
		this.price = price;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the quantity.
	 *
	 * @return the quantity
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the quantity.
	 *
	 * @param quantity the new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price the new price
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * Gets the product stock.
	 *
	 * @return the product stock
	 */
	public Set<ProductStock> getProductStock() {
		return productStock;
	}

	/**
	 * Sets the product stock.
	 *
	 * @param productStock the new product stock
	 */
	public void setProductStock(Set<ProductStock> productStock) {
		this.productStock = productStock;
	}
	
	/**
	 * Gets the product total price.
	 *
	 * @return the product total price
	 */
	public double getProductTotalPrice() {
		return (double) Math.floor( quantity * price * 100.0d + .5d) / 100.0d;
	}
	
}