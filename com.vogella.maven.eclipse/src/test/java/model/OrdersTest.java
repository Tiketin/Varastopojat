package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * @Author Pauli Vuolle-Apiala; 05/03/2021
 */

class OrdersTest {

	private static SessionFactory istuntotehdas;
	private Session istunto;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.err.println("ProductsTest: Istuntotehtaan luominen ei onnistunut. ");
			System.out.println(e);
			System.exit(-1);
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		if (istuntotehdas != null)
			istuntotehdas.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		istunto = istuntotehdas.openSession();
	}

	@AfterEach
	void tearDown() throws Exception {
		if (istunto != null)
			istunto.close();
	}

	@Test
	@DisplayName("Tilauksen luonti ja tallentaminen")
	void createOrderTest() throws Exception {
		istunto.beginTransaction();

		LocalDateTime paivays = LocalDateTime.now();
		Orders luotavaTilaus = new Orders(paivays);
		Integer orderId = (Integer) istunto.save(luotavaTilaus);
		istunto.getTransaction().commit();

		Orders luettuTilaus = istunto.load(Orders.class, orderId);
		assertNotNull(luettuTilaus);
		assertEquals(luettuTilaus.getDateCreated(), paivays);
		assertEquals(luettuTilaus.getOrdersId(), orderId);
	}

	@Test
	@DisplayName("Tilauksen ja laskun luonti ja tallentaminen")
	void createOrderAndInvoiceTest() throws Exception {
		istunto.beginTransaction();
		Orders luotavaTilaus = new Orders();
		luotavaTilaus.setCreationDate(LocalDateTime.now());
		Integer id = (Integer) istunto.save(luotavaTilaus);

		assertTrue(id > 0);
		Invoice tilauksenLasku = new Invoice("Matti testaaja", "Testaajankuja 1", "Helsinki", 900.00);
		tilauksenLasku.setOrders(luotavaTilaus);
		istunto.save(tilauksenLasku);
		istunto.getTransaction().commit();

		Invoice luotuLasku = istunto.load(Invoice.class, id);
		assertNotNull(luotuLasku);
		assertEquals(tilauksenLasku, luotuLasku);
		assertEquals(luotuLasku.getOrders(), luotavaTilaus);
	}

	@Test
	@DisplayName("Tilauksen, laskun sekä tilattujen tuotteiden tallentaminen")
	void createTotalOrderTest() throws Exception {

		List<Products> tilatutTuotteet = new ArrayList<>();
		tilatutTuotteet.add(new Products("HK Sininen", 5, 2.30));
		tilatutTuotteet.add(new Products("Kabanossi", 10, 1.90));
		tilatutTuotteet.add(new Products("Oltermanni", 1, 3.50));

		istunto.beginTransaction();

		Orders tuoteTilaus = new Orders();
		tuoteTilaus.setCreationDate(LocalDateTime.now());
		Integer tilauksenId = (Integer) istunto.save(tuoteTilaus);
		assertTrue(tilauksenId > 0);

		double kokonaisHinta = 0;

		for (Products tuote : tilatutTuotteet) {
			Integer productId = (Integer) istunto.save(tuote);
			assertTrue(productId > 0);
			OrderDetails tilauksenTuoteTiedot = new OrderDetails(tuote.getQuantity(), tuote.getProductTotalPrice());
			kokonaisHinta += tuote.getProductTotalPrice();

			OrderDetailsCompositeKey komposiittiAvain = new OrderDetailsCompositeKey(tuote.getProductId(),
					tuoteTilaus.getOrdersId());
			tilauksenTuoteTiedot.setCompositeId(komposiittiAvain);
			istunto.save(tilauksenTuoteTiedot);
		}

		Invoice tuotteidenLasku = new Invoice("Matti testaaja", "Testaajankuja 1", "Helsinki", kokonaisHinta);
		tuotteidenLasku.setOrders(tuoteTilaus);
		istunto.save(tuotteidenLasku);
		
		istunto.getTransaction().commit();
	}

	@Test
	@DisplayName("Tilauksen, laskun sekä tilattujen tuotteiden hakeminen tietokannasta")
	void readTotalOrderTest() throws Exception {

		List<Products> tilatutTuotteet = new ArrayList<>();
		tilatutTuotteet.add(new Products("Tiskikone", 5, 485));
		tilatutTuotteet.add(new Products("Ruohonleikkuri", 10, 399.99));
		tilatutTuotteet.add(new Products("Pyykinpesukone", 1, 699));

		istunto.beginTransaction();
		Orders tuoteTilaus = new Orders(LocalDateTime.now());
		Integer tilausId = (Integer) istunto.save(tuoteTilaus);
		assertTrue(tilausId > 0);

		double kokonaisHinta = 0;
		List<OrderDetails> tilausTiedot = new ArrayList<>();

		for (Products tuote : tilatutTuotteet) {
			Integer productId = (Integer) istunto.save(tuote);
			assertTrue(productId > 0);

			OrderDetails tuotetiedot = new OrderDetails(tuote.getQuantity(), tuote.getProductTotalPrice());
			tilausTiedot.add(tuotetiedot);
			kokonaisHinta += tuote.getProductTotalPrice();

			OrderDetailsCompositeKey komposiittiAvain = new OrderDetailsCompositeKey(productId, tilausId);
			tuotetiedot.setCompositeId(komposiittiAvain);
			assertNotNull(tuotetiedot.getCompositeId());
			istunto.save(tuotetiedot);
		}

		Invoice tuotteidenLasku = new Invoice("Uusi asiakas", "Asiakastie 1B", "Helsinki", kokonaisHinta);
		tuotteidenLasku.setOrders(tuoteTilaus);
		istunto.save(tuotteidenLasku);
		istunto.getTransaction().commit();

		Orders luettuTilaus = istunto.load(Orders.class, tilausId);
		assertEquals(luettuTilaus, tuoteTilaus);

		Invoice luetunTilauksenLasku = istunto.load(Invoice.class, luettuTilaus.getOrdersId());
		assertEquals(luetunTilauksenLasku.getTotalCost(), kokonaisHinta);

		@SuppressWarnings("unchecked")
		Query<OrderDetails> query = istunto.createQuery("FROM OrderDetails where compositeId.orderId = :ordid");
		query.setParameter("ordid", luettuTilaus.getOrdersId());
		List<OrderDetails> haetutTilausTiedot = query.getResultList();

		assertEquals(tilausTiedot.size(), haetutTilausTiedot.size());

		List<Products> haetutTuotteet = new ArrayList<>();
		for (OrderDetails details : tilausTiedot) {
			assertEquals(details.getCompositeId().getOrderId(), tuoteTilaus.getOrdersId());
			Products haettuTuote = istunto.load(Products.class, details.getCompositeId().getProductId());
			haetutTuotteet.add(haettuTuote);
		}

		assertEquals(haetutTuotteet.size(), tilatutTuotteet.size());

		int index = 0;
		for (Products tuote : haetutTuotteet) {
			assertEquals(tuote.getProductId(), tilatutTuotteet.get(index).getProductId());
			index++;
		}
	}
	
	@Test
	@DisplayName("Tilauksen, laskun sekä tuotetietojen poistaminen")
	public void deleteTotalOrder() throws Exception {
		ArrayList<Products> tuotteet = new ArrayList<>();
		tuotteet.add(new Products("Reissumies", 4, 0.55));
		tuotteet.add(new Products("Juhla mokka", 1, 4.85));
		
		istunto.beginTransaction();
		
		Orders tilaus = new Orders(LocalDateTime.now());
		Integer orderId = (Integer) istunto.save(tilaus);
		assertTrue(orderId > 0);
		
		double tilauksenHinta = 0;
		for(Products tuote : tuotteet) {
			Integer tuoteId = (Integer) istunto.save(tuote);
			assertTrue(tuoteId > 0);
			
			OrderDetails tuotetieto = new OrderDetails(tuote.getQuantity(), tuote.getProductTotalPrice());
			tuotetieto.setCompositeId(new OrderDetailsCompositeKey(tuoteId, orderId));
			assertNotNull(tuotetieto.getCompositeId());
			istunto.save(tuotetieto);
			tilauksenHinta += tuote.getProductTotalPrice();
		}
		Invoice tuotteidenLasku = new Invoice("Veikko Varastosta", "Pöhinäkatu 6", "Vantaa", tilauksenHinta);
		tuotteidenLasku.setOrders(tilaus);
		istunto.save(tuotteidenLasku);
		istunto.getTransaction().commit();
		
		istunto.beginTransaction();
		
		@SuppressWarnings("unchecked")
		Query<OrderDetails> load = istunto.createQuery("FROM OrderDetails where compositeId.orderId = :ordid");
		load.setParameter("ordid", tilaus.getOrdersId());
		List<OrderDetails> haetutTilausTiedot = load.getResultList();		
		assertFalse(haetutTilausTiedot.isEmpty());		
		
		for(OrderDetails details : haetutTilausTiedot) {
			istunto.delete(details);
		}
		
		Invoice luettuLasku = istunto.load(Invoice.class, orderId);
		assertEquals(luettuLasku, tuotteidenLasku);
		assertEquals(luettuLasku.getCustomerName(), tuotteidenLasku.getCustomerName());
		istunto.delete(luettuLasku);
		
		Orders luettuTilaus = istunto.load(Orders.class, orderId);
		assertEquals(tilaus, luettuTilaus);
		assertEquals(tilaus.getOrdersId(), luettuTilaus.getOrdersId());
		istunto.delete(luettuTilaus);
		
		istunto.getTransaction().commit();
		
			
		@SuppressWarnings("unchecked")
		Query<OrderDetails> loadAfterDeletion = istunto.createQuery("FROM OrderDetails where compositeId.orderId = :ordid");
		load.setParameter("ordid", tilaus.getOrdersId());
		List<OrderDetails> poistetutTilausTiedot = load.getResultList();		
		assertTrue(poistetutTilausTiedot.isEmpty());	
		
		Invoice poistettuLasku = istunto.find(Invoice.class, orderId);
		assertNull(poistettuLasku);
		
		Orders poistettuTilaus = istunto.find(Orders.class, orderId);
		assertNull(poistettuTilaus);		
	}
}
