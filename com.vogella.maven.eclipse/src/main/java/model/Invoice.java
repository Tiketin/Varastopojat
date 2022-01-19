package model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;


/**
 * The Class Invoice.
 */
/*
 * @author Pauli Vuolle-Apiala; 11/02/2021
 */
@Entity
@Table(name = "Invoice")
public class Invoice implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The orders. Primary key */
	@Id
	@OneToOne
	@JoinColumn(name = "Order_id")
	private Orders orders;

	/** The customer name. */
	@Column(name = "Customer_Name")
	private String customerName;

	/** The total cost. */
	@Column(name = "Total_cost")
	private double totalCost;
	
	/** The address. */
	@Column(name = "Address")
	private String address;

	/** The city. */
	@Column(name = "City")
	private String city;

	/**
	 * Instantiates a new invoice.
	 *
	 * @param name the name
	 * @param address the address
	 * @param city the city
	 * @param cost the cost
	 */
	public Invoice(String name, String address, String city, double cost) {
		this.customerName = name;
		this.totalCost = cost;
		this.address = address;
		this.city = city;
	}
	
	/**
	 * Instantiates a new invoice.
	 */
	public Invoice() {};

	/**
	 * Gets the orders.
	 *
	 * @return the orders
	 */
	public Orders getOrders() {
		return orders;
	}

	/**
	 * Sets the orders.
	 *
	 * @param orders the new orders
	 */
	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	/**
	 * Gets the customer name.
	 *
	 * @return the customer name
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the customer name.
	 *
	 * @param customerName the new customer name
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the total cost.
	 *
	 * @return the total cost
	 */
	public double getTotalCost() {
		return totalCost;
	}

	/**
	 * Sets the total cost.
	 *
	 * @param totalCost the new total cost
	 */
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
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
		Invoice that = (Invoice) obj;
		return Objects.equals(getOrders().getOrdersId(), that.getOrders().getOrdersId());
	}
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getOrders().getOrdersId());
	}
	
	
}
