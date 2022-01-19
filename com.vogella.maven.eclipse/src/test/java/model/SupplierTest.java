package model;

import static org.junit.jupiter.api.Assertions.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * @Author Pauli Vuolle-Apiala
 */

class SupplierTest {

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
	@DisplayName("Toimittajan luonti")
	void createSupplierTest() throws Exception {
		String toimittajaNimi = "S-Ryhmä";
		String osoite = "Jokutie 1, Helsinki";
		
		Supplier Sryhma = new Supplier(toimittajaNimi, osoite);
		
		istunto.beginTransaction();
		
		Integer supplierId = (Integer) istunto.save(Sryhma);
		
		istunto.getTransaction().commit();
		istunto.close();
		assertTrue(supplierId > 0);				
	}
	
	@Test
	@DisplayName("Toimittajan lukeminen")
	void readSupplierTest() throws Exception {
		String toimittajaNimi = "Kesko";
		String osoite = "Osoite 6, espoo";
		
		Supplier kesko = new Supplier(toimittajaNimi, osoite);
		
		istunto.beginTransaction();
		
		Integer supplierId = (Integer) istunto.save(kesko);
		
		istunto.getTransaction().commit();
		istunto.close();
		assertTrue(supplierId > 0);	
		
		istunto = istuntotehdas.openSession();
		istunto.beginTransaction();
		
		Supplier luettuToimittaja = istunto.load(Supplier.class, supplierId);
		assertNotNull(luettuToimittaja);
		assertEquals(luettuToimittaja.getNameOfSupplier(), kesko.getNameOfSupplier());
		assertEquals(luettuToimittaja.getSupplierAddress(), kesko.getSupplierAddress());
	}
	
	@Test
	@DisplayName("Toimittajan päivittäminen")
	void updateSupplierTest() throws Exception {
		Supplier veikonFirma = new Supplier("Veikko varastolta", "veikon kotikatu 3");
		
		istunto.beginTransaction();
		
		Integer supplierId = (Integer) istunto.save(veikonFirma);
		
		istunto.getTransaction().commit();
		assertTrue(supplierId > 0);
		
		istunto.beginTransaction();
		
		Supplier paivitettava = istunto.load(Supplier.class, supplierId);
		assertNotNull(paivitettava);
		assertEquals(paivitettava, veikonFirma);
		
		String uusiOsoite = "Veikon äidin osoite";
		String uusiNimi = "Veikon firma meni konkkaan";
		
		paivitettava.setAddress(uusiOsoite);
		paivitettava.setNameOfSupplier(uusiNimi);
		istunto.saveOrUpdate(paivitettava);
		istunto.getTransaction().commit();
		istunto.close();
		
		istunto = istuntotehdas.openSession();
		Supplier paivitetty = istunto.load(Supplier.class, supplierId);
		assertEquals(paivitetty.getNameOfSupplier(), uusiNimi);
		assertEquals(paivitetty.getSupplierAddress(), uusiOsoite);
	}
	
	@Test
	@DisplayName("Toimittajan poistaminen")
	void deleteSupplierTest() throws Exception {
		Supplier firma = new Supplier("Toimittava firma", "Firmatie 1");
		istunto.beginTransaction();
		
		Integer supplierId = (Integer) istunto.save(firma);
		
		istunto.getTransaction().commit();
		assertTrue(supplierId > 0);
		
		istunto.beginTransaction();
		Supplier poistettava = istunto.load(Supplier.class, supplierId);
		istunto.delete(poistettava);
		istunto.getTransaction().commit();
		
		Supplier poistettu = istunto.find(Supplier.class, supplierId);
		assertNull(poistettu);		
	}
}
