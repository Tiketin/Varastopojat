package model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import controller.Controller;

/**
 * The Class OrdersAccessObject.
 *
 * @author Nikke Tikka, Pauli Vuolle-Apiala
 */
public class OrdersAccessObject implements IOrdersDAO {


	private SessionFactory istuntotehdas = null;

	/**
	 * Instantiates a new orders access object.
	 */
	public OrdersAccessObject() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut. ordersAccessobject ");
			System.out.println(e);
			System.exit(-1);
		}
	}

	/**
	 * Creates the order.
	 *
	 * @param asiakasNimi the asiakas nimi
	 * @param osoite the osoite
	 * @param kaupunki the kaupunki
	 * @param tilatutTuotteet the tilatut tuotteet
	 * @return the int
	 */
	public int createOrder(String asiakasNimi, String osoite, String kaupunki, List<Products> tilatutTuotteet) {
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaction = istunto.beginTransaction();

			Orders tuoteTilaus = new Orders(LocalDateTime.now());
			Integer tilausId = (Integer) istunto.save(tuoteTilaus);			
			double tilausKokonaishinta = 0;		
			
			  for (Products tuote : tilatutTuotteet) {
            	
                OrderDetails tuoteTiedot = new OrderDetails(tuote.getQuantity(), tuote.getPrice());
                tuoteTiedot.setCompositeId(new OrderDetailsCompositeKey(tuote.getProductId(), tilausId));
                istunto.save(tuoteTiedot);
                Controller controller = Controller.Singleton();
				controller.update();
                tilausKokonaishinta += tuote.getProductTotalPrice();
            }

			Invoice tilauksenLasku = new Invoice(asiakasNimi, osoite, kaupunki, tilausKokonaishinta);
			tilauksenLasku.setOrders(tuoteTilaus);
			istunto.save(tilauksenLasku);

			transaction.commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return tilausId;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - Create order");
			System.out.println(e);
		}
		return -1;
	}

	/**
	 * Read order.
	 *
	 * @param id the id
	 * @return the orders
	 */
	public Orders readOrder(int id) {
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {			
			transaction = istunto.beginTransaction();
			Orders order = istunto.load(Orders.class, id);

			transaction.commit();

			return order;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - read order - single");
			System.out.println(e);

			return null;
		}
	}

	/**
	 * Read order details.
	 *
	 * @param orderId the order id
	 * @return the order details[]
	 */
	public OrderDetails[] readOrderDetails(int orderId) {
		OrderDetails[] orderDetails;
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();

			@SuppressWarnings("unchecked")
			Query<OrderDetails> query = istunto.createQuery("FROM OrderDetails where compositeId.orderId = :ordId");
			query.setParameter("ordId", orderId);

			List<OrderDetails> haetutTilausTiedot = query.getResultList();
			orderDetails = haetutTilausTiedot.toArray(new OrderDetails[haetutTilausTiedot.size()]);
		} catch (Exception e) {
			System.err.println("Error - Listing of all order details");
			System.out.println(e);
			orderDetails = null;
		}
		return orderDetails;
	}

	/**
	 * Read all orders.
	 *
	 * @return the orders[]
	 */
	public Orders[] readAllOrders() {
		Orders[] orders;
		try (Session istunto = istuntotehdas.openSession()) {

			@SuppressWarnings("unchecked")
			List<Orders> result = istunto.createQuery("from Orders").getResultList();
			orders = result.toArray(new Orders[result.size()]);

		} catch (Exception e) {
			System.err.println("Error - Listing of all orders");
			System.out.println(e);
			orders = null;
		}
		return orders;
	}

	/**
	 * Read all order details.
	 *
	 * @return the order details[]
	 */
	public OrderDetails[] readAllOrderDetails() {
		OrderDetails[] orderDetails;
		try (Session istunto = istuntotehdas.openSession()) {
			@SuppressWarnings("unchecked")
			List<OrderDetails> result = istunto.createQuery("from OrderDetails").getResultList();
			orderDetails = new OrderDetails[result.size()];
			orderDetails = result.toArray(new OrderDetails[result.size()]);
		} catch (Exception e) {
			System.err.println("Error - Listing of all order details");
			System.out.println(e);
			orderDetails = null;
		}

		return orderDetails;
	}

	/**
	 * Read orders invoice.
	 *
	 * @param orderId the order id
	 * @return the invoice
	 */
	public Invoice readOrdersInvoice(int orderId) {
		Invoice tilauksenLasku = null;
		try (Session istunto = istuntotehdas.openSession()) {
			tilauksenLasku = istunto.load(Invoice.class, orderId);
		} catch (Exception e) {
			System.err.println("Error - Reading an order's invoice");
			System.out.println(e);
		}
		return tilauksenLasku;
	}

	/**
	 * Delete order.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean deleteOrder(int id) {
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();
			
			Invoice poistettavaLasku = istunto.load(Invoice.class, id);
			istunto.delete(poistettavaLasku);	

			Orders poistettavaTilaus = istunto.load(Orders.class, id);
			istunto.delete(poistettavaTilaus);					

			@SuppressWarnings("unchecked")
			Query<OrderDetails> tilaustiedot = istunto
					.createQuery("FROM OrderDetails where compositeId.orderId = :ordId");
			tilaustiedot.setParameter("ordId", id);
			List<OrderDetails> poistettavatTilaustiedot = tilaustiedot.getResultList();

			for (OrderDetails tilaustieto : poistettavatTilaustiedot) {
				istunto.delete(tilaustieto);
			}

			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;

		} catch (Exception e) {
			System.err.println("Error - Delete order");
			System.out.println(e);

			return false;
		}
	}
}
