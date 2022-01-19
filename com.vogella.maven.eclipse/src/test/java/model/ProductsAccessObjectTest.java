package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

/*
 * @Author Pauli Vuolle-Apiala
 */

class ProductsAccessObjectTest {
	
	private static IProductsDAO productsDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		productsDAO = new ProductsAccessObject();
	}

	@Test
	@DisplayName("Tuotteen lisääminen")
	void addProductDAOTest() throws Exception {
		Products lisattavaTuote = new Products("TestiTuote123", 15, 9.99);
		Integer productId = productsDAO.createProduct(lisattavaTuote);
		assertTrue(productId > 0);
	}
	
	@Test
	@DisplayName("Tuotteet hakeminen")
	void readProductDAOTest() throws Exception {
		Products luettavaTuote = new Products("Luettava tuote", 1, 0.99);
		Integer productId = productsDAO.createProduct(luettavaTuote);
		assertTrue(productId > 0);
		
		Products luettuTuote = productsDAO.readProduct(productId);
		assertNotNull(luettuTuote);
		
		assertEquals(luettavaTuote.getName(), luettuTuote.getName());
		assertEquals(luettavaTuote.getPrice(), luettuTuote.getPrice());
	}
	
	@Test
	@DisplayName("Tuotteen poistaminen")
	void deleteProductDAOTest() throws Exception {
		Products poistettavaTuote = new Products("Poistettava", 15, 19.99);
		Integer productId = productsDAO.createProduct(poistettavaTuote);
		assertTrue(productId > 0);
		
		boolean deletion = productsDAO.deleteProduct(productId);
		assertTrue(deletion);
	}
	
	@Test
	@DisplayName("Tuotteen päivittäminen")
	void updateProductDAOTest() throws Exception {
		
		Products lisattavaTuote = new Products("HK Sininen Lenkki", 5, 3.30);		
		final Integer productId = productsDAO.createProduct(lisattavaTuote);
		assertTrue(productId > 0);
		
		Products muokattavaTuote = productsDAO.readProduct(productId);
		assertEquals(lisattavaTuote.getName(), muokattavaTuote.getName());
		
		muokattavaTuote.setName("Kabanossi");
		muokattavaTuote.setPrice(2.70);
		muokattavaTuote.setQuantity(50);
		
		boolean updated = productsDAO.updateProduct(muokattavaTuote);
		assertTrue(updated);
		
		Products muokattuTuote = productsDAO.readProduct(productId);	
		assertEquals(muokattavaTuote.getName(), muokattuTuote.getName());
		assertEquals(muokattavaTuote.getPrice(), muokattuTuote.getPrice());	
		assertEquals(muokattavaTuote.getQuantity(), muokattuTuote.getQuantity());
	};
	
	@Test
	@DisplayName("Olemattoman tuotteen päivitys")
	void updateNonExistentProductDAOTest() throws Exception {	
		Products paivitettavaTuote = null;
		assertFalse(productsDAO.updateProduct(paivitettavaTuote));		
	}
	
	@Test
	@DisplayName("Kaikkien tuotteiden hakeminen")
	void getAllProductsDAOTest() throws Exception {
		Products[] tuotteet = productsDAO.readProducts();
		assertNotNull(tuotteet);
		assertTrue(tuotteet.length > 0);
	}
}
