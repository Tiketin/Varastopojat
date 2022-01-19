package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * @Author Pauli Vuolle-Apiala
 */

class SupplierAccessObjectTest {
	private static ISupplierDAO supplierDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		supplierDAO = new SupplierAccessObject();
	}

	@Test
	@DisplayName("Uuden toimittajan luonti")
	void createSupplierTest() throws Exception {
		Supplier uusiToimittaja = new Supplier("Veikon veli", "Sörkän vankila");
		Integer supplierId = supplierDAO.createSupplier(uusiToimittaja);
		assertTrue(supplierId > 0);
	}
	
	@Test 
	@DisplayName("Toimittajan lukeminen tietokannasta")
	void readSupplierTest() throws Exception {
		Supplier toimittaja = new Supplier("Stadium", "Urheilutie 12");
		Integer supplierId = supplierDAO.createSupplier(toimittaja);
		assertTrue(supplierId > 0);
		
		Supplier luettuToimittaja = supplierDAO.readSupplier(supplierId);
		assertEquals(toimittaja.getNameOfSupplier(), luettuToimittaja.getNameOfSupplier());
		assertEquals(toimittaja.getSupplierAddress(), luettuToimittaja.getSupplierAddress());	
	}
	
	@Test
	@DisplayName("Kaikkien toimittajien lukeminen tietokannasta")
	void readAllSuppliersTest() throws Exception {
		Supplier uusiToimittaja = new Supplier("Verkkokauppa", "Jätkäsaari 1");
		Supplier toinenToimittaja = new Supplier("Dressman", "Leppävaarantie 2");
		Supplier kolmasToimittaja = new Supplier("S-market", "Vihdintie 3");
		
		List<Supplier> toimittajat = new ArrayList<>();
		toimittajat.add(uusiToimittaja);
		toimittajat.add(toinenToimittaja);
		toimittajat.add(kolmasToimittaja);
		
		for(Supplier toimittaja : toimittajat) {
			Integer supplierId = supplierDAO.createSupplier(toimittaja);
			assertTrue(supplierId > 0);
		}
		
		Supplier[] kaikkiToimittajat = supplierDAO.readSuppliers();
		assertTrue(kaikkiToimittajat.length >= toimittajat.size());
	}
	
	@Test
	@DisplayName("Toimittajan päivittäminen")
	void updateSupplierTest() throws Exception {
		String vanhaNimi = "K-market";
		String vanhaOsoite =  "Kuopiontie 4";
		Supplier uusiToimittaja = new Supplier(vanhaNimi, vanhaOsoite);
		Integer id = supplierDAO.createSupplier(uusiToimittaja);
		assertTrue(id > 0);
		
		Supplier paivitettava = supplierDAO.readSupplier(id);
		String uusiNimi = "Jaakon kalakauppa";
		String uusiOsoite = "Kivikoskentie 3";
		paivitettava.setAddress(uusiOsoite);
		paivitettava.setNameOfSupplier(uusiNimi);
		boolean paivitetty = supplierDAO.updateSupplier(paivitettava);
		assertTrue(paivitetty);
		
		Supplier paivitettyToimittaja = supplierDAO.readSupplier(id);	
		assertNotEquals(paivitettyToimittaja.getNameOfSupplier(), vanhaNimi);
		assertNotEquals(paivitettyToimittaja.getSupplierAddress(), vanhaOsoite);
		
		assertEquals(paivitettyToimittaja.getNameOfSupplier(), uusiNimi);
		assertEquals(paivitettyToimittaja.getSupplierAddress(), uusiOsoite);			
	}
	
	@Test
	@DisplayName("Toimittajan poistaminen")
	void deleteSupplierTest() throws Exception {
		Supplier uusiToimittaja = new Supplier("Konkurssipesä", "Hietaniementie 2");
		Integer id = supplierDAO.createSupplier(uusiToimittaja);
		assertTrue(id > 0);
		
		boolean poistettu = supplierDAO.deleteSupplier(id);
		assertTrue(poistettu);
		
		Supplier lukuyritys = supplierDAO.readSupplier(id);
		assertNull(lukuyritys);	
	}
}
