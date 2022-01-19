package model;

import java.time.LocalDateTime;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;


/**
 * The Class Orders.
 *
 * @author Nikke Tikka, Pauli Vuolle-Apiala; 11/02/2021
 */

@Entity
@Table(name = "Orders")
public class Orders {

	
	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Order_Id", updatable = false, nullable = false)
	private int id;

	/** The invoice. */
	@OneToOne(mappedBy = "orders", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Invoice invoice;

	/** The date created. */
	@Column
	private LocalDateTime dateCreated;

	/**
	 * Instantiates a new orders.
	 */
	public Orders() {
	}

	/**
	 * Instantiates a new orders.
	 *
	 * @param creation the creation
	 */
	public Orders(LocalDateTime creation) {
		this.dateCreated = creation;
	}

	/**
	 * Sets the orders id.
	 *
	 * @param id the new orders id
	 */
	public void setOrdersId(int id) {
		this.id = id;
	}

	/**
	 * Gets the orders id.
	 *
	 * @return the orders id
	 */
	public int getOrdersId() {
		return this.id;
	}

	/**
	 * Gets the date created.
	 *
	 * @return the date created
	 */
	public LocalDateTime getDateCreated() {
		return this.dateCreated;
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creation the new creation date
	 */
	public void setCreationDate(LocalDateTime creation) {
		this.dateCreated = creation;
	}
	
	/**
	 * Sets the invoice.
	 *
	 * @param invoice the new invoice
	 */
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
}
