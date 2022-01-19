package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/*
 * @Author Pauli Vuolle-Apiala
 */

class ProductsTest {

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
	@DisplayName("Tuotteen lisäys")
	void addProductsTest() throws Exception {
		istunto.beginTransaction();

		Products lisattyTuote = new Products("Testituote", 10, 9.99);
		Integer id = (Integer) istunto.save(lisattyTuote);
		
		istunto.getTransaction().commit();
		assertTrue(id > 0);
	}

	@Test
	@DisplayName("Tuotteen hakeminen")
	void getProductsTest() throws Exception  {
		istunto.beginTransaction();

		Products lisattyTuote = new Products("Testituote", 10, 9.99);
		Integer id = (Integer) istunto.save(lisattyTuote);
		istunto.getTransaction().commit();
		
		Products haettuTuote = (Products) istunto.find(Products.class, id);

		assertNotNull(haettuTuote);
		assertEquals(haettuTuote.getName(), lisattyTuote.getName());
	}
	
	@Test
	@DisplayName("Olemattoman tuotteen hakeminen")
	void getNonExistentProductTest() throws Exception {
		istunto.beginTransaction();
		Products olematon = (Products) istunto.find(Products.class, 9999);
		assertNull(olematon);
	}

	@Test
	@DisplayName("Tuotteen päivitys")
	void updateProductsTest() throws Exception {
		istunto.beginTransaction();		
		
		Products lisattyTuote = new Products("Testituote", 10, 9.99);
		Integer id = (Integer) istunto.save(lisattyTuote);
		istunto.getTransaction().commit();
		assertTrue(id > 0);
			
		Products haettuTuote = (Products) istunto.find(Products.class, id);		
		assertNotNull(haettuTuote);		
		
		String uusiTuoteNimi = "Päivitetty Nimi";
		haettuTuote.setName(uusiTuoteNimi);	
		istunto.beginTransaction();
		istunto.update(haettuTuote);
		istunto.getTransaction().commit();
		
		Products päivitettyTuote = istunto.find(Products.class, id);		
		assertEquals(päivitettyTuote.getName(), uusiTuoteNimi);
	}
	
	@Test
	@DisplayName("Useamman tuotteen hakeminen")
	public void getMultipleProductsTest() throws Exception {
		istunto.beginTransaction();		
		
		Query<Products> query = istunto.createQuery("from Products", Products.class);
		List<Products> tulokset = query.getResultList();
		
		assertFalse(tulokset.isEmpty());		
	}
	
	@Test
	@DisplayName("Tuotteen poistaminen")
	public void deleteProductsTest() throws Exception {
		istunto.beginTransaction();	
		
		Products lisattava = new Products("lisattava tuote", 5, 9.99);
		Integer id = (Integer) istunto.save(lisattava);
		istunto.getTransaction().commit();
		
		istunto.beginTransaction();
		Products poistettava = (Products) istunto.find(Products.class, id);
		istunto.delete(poistettava);
		istunto.getTransaction().commit();
		
		Products poistettu = (Products) istunto.find(Products.class, id);		
		assertNull(poistettu);	
	}
	
	@ParameterizedTest (name="Tuotemäärä {0} kerrottuna hinnalla {1} on {2}")
	@CsvSource({ "4, 8.88, 35.52", "4, 13.57, 54.28", "7, 22.89, 160.23", "21, 44.35, 931.35", "33, 45.67, 1507.11", "66, 98.177, 6479.68", "80, 3.457, 276.56"})
	@DisplayName("Sisäänoston hinnan laskeminen ja pyöristäminen")
	public void calculatePurchaseCost(int tuoteMaara, double tuotteenHinta, double odotettuTulos) throws Exception {
		
		Products tuote = new Products("Bitcoin", tuoteMaara,  tuotteenHinta);	
		//Pyöristys tapahtuu Products-luokan getPrice()-metodissa
		assertEquals(odotettuTulos, tuote.getProductTotalPrice(), DELTA, "Virhe sisäänoston hinnan laskemisessa ja/tai pyöristämisessä.");
		
		
	}
}
