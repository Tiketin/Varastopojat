package model;

import java.util.List;

/**
 * 
 * @author Nikke Tikka, Pauli Vuolle-Apiala
 *
 */
public interface IOrdersDAO {
	int createOrder(String nimi, String osoite, String kaupunki, List<Products> tilatutTuotteet);
	Orders readOrder(int id);
	OrderDetails[] readOrderDetails(int orderId);
	Orders[] readAllOrders();
	OrderDetails[] readAllOrderDetails();
	boolean deleteOrder(int id);
	public Invoice readOrdersInvoice(int id);
}
