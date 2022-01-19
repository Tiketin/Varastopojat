package model;

import static org.junit.jupiter.api.Assertions.*;

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
 * @Author Pauli Vuolle-Apiala
 */

class ProductStockTest {
	
	

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
	
	@Disabled
	@Test
	@DisplayName("ProductStock-olion luonti")
	void createProductStockTest() throws Exception {
		istunto.beginTransaction();

		Products uusiTuote = new Products("UusiTuote", 1.99);
		Integer productId = (Integer) istunto.save(uusiTuote);
		assertTrue(productId > 0);

		int tuotteenMaara = 5;
		ProductStock uudenTuotteenMaara = new ProductStock(tuotteenMaara);
		istunto.save(uudenTuotteenMaara);
		istunto.getTransaction().commit();

		istunto.beginTransaction();
		Products samaTuote = istunto.load(Products.class, productId);
		ProductStock samaMaara = istunto.load(ProductStock.class, productId);

		assertEquals(samaMaara.getQuantity(), tuotteenMaara);
	}
	
	@Disabled
	@Test
	@DisplayName("ProductStock-olion poisto")
	void deleteProductStockTest() throws Exception {
		istunto.beginTransaction();

		Products uusiTuote = new Products("UusiTuote", 1.99);
		Integer productId = (Integer) istunto.save(uusiTuote);
		assertTrue(productId > 0);

		int tuotteenMaara = 5;
		ProductStock uudenTuotteenMaara = new ProductStock(productId);
		istunto.save(uudenTuotteenMaara);
		istunto.getTransaction().commit();

		istunto.beginTransaction();
		ProductStock samaMaara = istunto.load(ProductStock.class, productId);
		istunto.delete(samaMaara);
		istunto.getTransaction().commit();

		istunto.beginTransaction();
		samaMaara = istunto.find(ProductStock.class, productId);
		assertNull(samaMaara);
	}

	@Disabled
	@Test
	@DisplayName("ProductStock-olion päivitys")
	void updateProductStockTest() throws Exception {
		istunto.beginTransaction();

		Products uusiTuote = new Products("UusiTuote", 1.99);
		Integer productId = (Integer) istunto.save(uusiTuote);
		assertTrue(productId > 0);

		int tuotteenMaara = 5;
		ProductStock uudenTuotteenMaara = new ProductStock(productId);
		istunto.save(uudenTuotteenMaara);
		istunto.getTransaction().commit();

		int paivitettavaTuotteenMaara = 999;
		istunto.beginTransaction();
		ProductStock samaMaara = istunto.load(ProductStock.class, productId);
		samaMaara.setQuantity(paivitettavaTuotteenMaara);
		istunto.update(samaMaara);
		istunto.getTransaction().commit();

		istunto.beginTransaction();
		ProductStock samaMaaraPaivitetty = istunto.find(ProductStock.class, productId);
		
		assertNotNull(samaMaaraPaivitetty);
		assertTrue(paivitettavaTuotteenMaara == samaMaaraPaivitetty.getQuantity());		
	}
	
	@Disabled
	@Test
	@DisplayName("ProductStock-olion haku")
	void getProductStockTest() throws Exception {
		istunto.beginTransaction();
		
		Products uusiTuote = new Products("lisattavaTuote", 9.99);
		Integer productId = (Integer) istunto.save(uusiTuote);
		assertTrue(productId > 0);
		
		int tuotteenMaara = 9;
		ProductStock uudenTuotteenMaara = new ProductStock(tuotteenMaara);
		istunto.save(uudenTuotteenMaara);
		istunto.getTransaction().commit();
		
		ProductStock luettavaMaara = istunto.load(ProductStock.class, productId);
		assertNotNull(luettavaMaara);
	}
	
}
