package model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.DecimalFormat;

/*
 * @author Pauli Vuolle-Apiala
 */

import java.time.LocalDateTime;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PurchaseTest {

	private static SessionFactory istuntotehdas;
	private Session istunto;
	private final double DELTA = 0.01;

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
	@DisplayName("Sisäänoston luominen")
	public void createPurchaseTest() throws Exception {
		Supplier toimittaja = new Supplier("Uusi toimittaja", "Toimittajantie 1");
		Products tilattavaTuote = new Products("Sohva", 5, 399.99);
		
		istunto.beginTransaction();
		
		Integer productId = (Integer) istunto.save(tilattavaTuote);
		Integer supplierId = (Integer) istunto.save(toimittaja);
		assertTrue(productId > 0 && supplierId > 0);
		
		Purchase sisaanosto = new Purchase(10, productId, LocalDateTime.now(), supplierId);
		sisaanosto.setPurchaseCost(sisaanosto.getQty() * tilattavaTuote.getPrice());
		Integer purchaseId = (Integer) istunto.save(sisaanosto);
		assertTrue(purchaseId > 0);
	}
	
	@Test
	@DisplayName("Sisäänoston lukeminen")
	public void readPurchaseTest() { 
		Supplier toimittaja = new Supplier("Veikon veli", "Veikon kellari");
		Products tilattavaTuote = new Products("Kala", 10, 29.99);
		
		istunto.beginTransaction();
		
		Integer productId = (Integer) istunto.save(tilattavaTuote);
		Integer supplierId = (Integer) istunto.save(toimittaja);
		assertTrue(productId > 0 && supplierId > 0);
		
		Purchase sisaanosto = new Purchase(10, productId, LocalDateTime.now(), supplierId);
		sisaanosto.setPurchaseCost( sisaanosto.getQty() * tilattavaTuote.getPrice());
		Integer purchaseId = (Integer) istunto.save(sisaanosto);
		assertTrue(purchaseId > 0);
		
		istunto.getTransaction().commit();
		
		istunto.beginTransaction();
		
		Purchase luettavaOsto = istunto.find(Purchase.class, purchaseId);
		assertNotNull(luettavaOsto);
		assertEquals(luettavaOsto.getLocalDate(), sisaanosto.getLocalDate());
		assertEquals(luettavaOsto, sisaanosto);
	}
	
	@Test
	@DisplayName("Sisäänoston poistaminen")
	public void deletePurchaseTest() throws Exception {
		Supplier uusiToimittaja = new Supplier("Lidl", "Katutie 1");
		Products tilattavaTuote = new Products("Kiuas", 1, 299);
		
		istunto.beginTransaction();
		
		Integer productId = (Integer) istunto.save(tilattavaTuote);
		Integer supplierId = (Integer) istunto.save(uusiToimittaja);		
		assertTrue(productId > 0 && supplierId > 0);
		
		istunto.getTransaction().commit();
		istunto.beginTransaction();
		
		Products ostettavaTuote = istunto.find(Products.class, productId);
		Purchase kiuasOsto = new Purchase(10, productId, LocalDateTime.now(), supplierId);
		
		double ostonKokonaishinta = kiuasOsto.getQty() * ostettavaTuote.getPrice();		
		kiuasOsto.setPurchaseCost(ostonKokonaishinta);
		
		Integer purchaseId = (Integer) istunto.save(kiuasOsto);
		assertTrue(purchaseId > 0);
		
		istunto.getTransaction().commit();
		istunto.beginTransaction();
		
		Purchase poistettava = istunto.load(Purchase.class, purchaseId);
		istunto.delete(poistettava);
		
		istunto.getTransaction().commit();
		istunto.beginTransaction();
		
		Purchase poistettu = istunto.find(Purchase.class, purchaseId);
		assertNull(poistettu);		
	}
	@Test
	@DisplayName("Sisäänoston muokkaaminen")
	public void updatePurchasetest() throws Exception {
		Supplier toimittaja = new Supplier("Prisma", "kauppatie 3");
		Products kala = new Products("lohi", 10,  21.00);
		
		istunto.beginTransaction();
		
		Integer productId = (Integer) istunto.save(kala);
		Integer supplierId = (Integer) istunto.save(toimittaja);		
		assertTrue(productId > 0 && supplierId > 0);
		
		Purchase uusiOsto = new Purchase(15, productId, LocalDateTime.now(), supplierId);
		
		double vanhaHinta = uusiOsto.getQty() * kala.getPrice();
		uusiOsto.setPurchaseCost(vanhaHinta);
		
		Integer purchaseId = (Integer) istunto.save(uusiOsto);
		assertTrue(purchaseId > 0);
		
		istunto.getTransaction().commit();
		istunto.beginTransaction();
		
		Purchase luettavaOsto = istunto.find(Purchase.class, purchaseId);
		Products ostettuTuote = istunto.find(Products.class, productId);
		luettavaOsto.setQty(10);
		luettavaOsto.setPurchaseCost(luettavaOsto.getQty() * ostettuTuote.getPrice());
		assertNotEquals(luettavaOsto.getPurchaseCost(), vanhaHinta);
		istunto.update(ostettuTuote);
		istunto.getTransaction().commit();
		
		istunto.beginTransaction();
		Purchase paivitettyOsto = istunto.find(Purchase.class, purchaseId);
		assertNotEquals(paivitettyOsto.getPurchaseCost(), vanhaHinta);	
	}
	
	@ParameterizedTest (name="Tuotemäärä {0} kerrottuna hinnalla {1} on {2}")
	@CsvSource({ "3, 17.5, 52.5", "88, 0.1123, 9.88", "25, 0.6969, 17.42", "32, 99.99, 3199.68", "50, 65.45, 3272.5", "99, 67.33,6665.67", "105, 45.4544, 4772.71"})
	@DisplayName("Sisäänoston hinnan laskeminen ja pyöristäminen")
	public void calculatePurchaseCost(int tuoteMaara, double tuotteenHinta, double odotettuTulos) throws Exception {
		
		Products tuote = new Products("lohi", tuoteMaara,  tuotteenHinta);	
		Purchase uusiOsto = new Purchase(tuoteMaara, 1, LocalDateTime.now(), 2);
		
		//Pyöristys tapahtuu Purchase-luokassa
		uusiOsto.setPurchaseCost(uusiOsto.getQty() * tuote.getPrice());
		
		assertEquals(odotettuTulos, uusiOsto.getPurchaseCost(), DELTA, "Virhe sisäänoston hinnan laskemisessa ja/tai pyöristämisessä.");
		
		
	}
}
