package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * @Author Pauli Vuolle-Apiala
 */

class OrdersAccessObjectTest {
	
	private static IOrdersDAO orderDAO;
	private static IProductsDAO productDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		orderDAO = new OrdersAccessObject();
		productDAO = new ProductsAccessObject();
	}
	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
		orderDAO = null;
	}

	@Test
	@DisplayName("Tilauksen, laskun sekä tilaustietojen luominen")
	void createTotalOrderTest() {
		List<Products> tilatutTuotteet = new ArrayList<>();
		tilatutTuotteet.add(new Products("Pesukone", 11, 599.99));
		tilatutTuotteet.add(new Products("Ruokapöytä", 22, 289));
		tilatutTuotteet.add(new Products("Lipasto", 33, 399.99));
		
		for(Products tuote : tilatutTuotteet) {
			productDAO.createProduct(tuote);
		}
		
		String nimi = "Veikko varastolta";
		String kaupunki = "Espoo";
		String osoite = "Jokukatu 1B";
		
		int tilausId = orderDAO.createOrder(nimi, osoite, kaupunki, tilatutTuotteet);
		assertTrue(tilausId > 0);
	}
	
	@Test
	@DisplayName("Tilauksen, laskun sekä tilaustietojen lukeminen")
	void readTotalOrderTest() {
		List<Products> tilatutTuotteet = new ArrayList<>();
		tilatutTuotteet.add(new Products("Tiskikone", 44, 599.99));
		tilatutTuotteet.add(new Products("Nojatuoli", 55, 289));
		tilatutTuotteet.add(new Products("Hylly", 66, 399.99));
		
		for(Products tuote : tilatutTuotteet) {
			productDAO.createProduct(tuote);
		}
				
		String nimi = "Veikko kotoa";
		String kaupunki = "Vantaa";
		String osoite = "Raudikkokuja 1";
		
		int tilausId = orderDAO.createOrder(nimi, osoite, kaupunki, tilatutTuotteet);
		assertTrue(tilausId > 0);
		
		Orders luettuTilaus = orderDAO.readOrder(tilausId);
		assertNotNull(luettuTilaus);
		
		Invoice luettuLasku = orderDAO.readOrdersInvoice(tilausId);
		assertNotNull(luettuLasku);	
		
		OrderDetails[] luetutTilaustiedot = orderDAO.readOrderDetails(tilausId);
		assertNotNull(luetutTilaustiedot);
		
		int index = 0;
		for(OrderDetails luettuTieto : luetutTilaustiedot) {
			assertEquals(luettuTieto.getCompositeId().getProductId(), tilatutTuotteet.get(index).getProductId());
			index++;
		}
		
		assertEquals(luettuLasku.getAddress(), osoite);
		assertEquals(luettuLasku.getCustomerName(), nimi);
		assertEquals(luettuLasku.getCity(), kaupunki);
		
	}
	
	
	@Test
	@DisplayName("Tilauksen, laskun sekä tilaustietojen poistaminen")
	void deleteTotalOrderTest() {
		List<Products> tilatutTuotteet = new ArrayList<>();
		tilatutTuotteet.add(new Products("Pesukone", 77, 599.99));
		tilatutTuotteet.add(new Products("Ruokapöytä", 88, 289));
		tilatutTuotteet.add(new Products("Lipasto", 99, 399.99));
		
		for(Products tuote : tilatutTuotteet) {
			productDAO.createProduct(tuote);
		}
		
		String nimi = "Veikko muualta";
		String kaupunki = "Sompsanperä";
		String osoite = "Pöhinätie 3";
		
		int tilausId = orderDAO.createOrder(nimi, osoite, kaupunki, tilatutTuotteet);
		assertTrue(tilausId > 0);
		
		boolean poistettu = orderDAO.deleteOrder(tilausId);
		assertTrue(poistettu);
	}
}
