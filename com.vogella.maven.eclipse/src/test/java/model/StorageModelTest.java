package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/*
 * @Author Pauli Vuolle-Apiala
 */

class StorageModelTest {

	private static StorageModel model = null;
	private static ProductsAccessObject productDAO = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		model = new StorageModel();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		model = null;
	}	

	@BeforeEach
	void setUp() throws Exception {		
		productDAO = new ProductsAccessObject();
	}

	@AfterEach
	void tearDown() throws Exception {
		productDAO = null;
	}
	
	@Test
	@DisplayName("Tuotteen saldon muuttaminen StorageModel-luokan avulla")
	void changeProductQuantityTest() {
		int vanhaMaara = 10;
		Products uusiTuote = new Products("Lisättävä tuote", vanhaMaara, 9.99);
		Integer productId = productDAO.createProduct(uusiTuote);
		
		int uusiMaara = 15;		
		model.changeQty(productId, uusiMaara);
		
		Products luettuTuote = productDAO.readProduct(productId);
		
		assertNotNull(luettuTuote);
		assertEquals(luettuTuote.getQuantity(), uusiMaara);
	}
	
	@Test
	@DisplayName("Tuotteen lisääminen StorageModel-luokan avulla")
	void addProductTest() {
		Products uusiTuote = new Products("uusiTuote", 20, 15.80);
		int productId = model.addNewProduct(uusiTuote);
		
		Products luettuTuote = productDAO.readProduct(productId);
		assertNotNull(luettuTuote);
		assertEquals(uusiTuote.getProductId(), luettuTuote.getProductId());
		assertEquals(uusiTuote.getName(), luettuTuote.getName());
		assertEquals(uusiTuote.getPrice(), luettuTuote.getPrice());
	}
	
	@Test
	@DisplayName("Tuotteen lukeminen StorageModel-luokan avulla")
	void readProductTest() {
		Products uusiTuote = new Products("Whirlpool pyykinpesukone", 3, 450.0);
		int productId = model.addNewProduct(uusiTuote);
		
		Products luettuTuote = model.getProduct(productId);
		
		assertEquals(uusiTuote.getName(), luettuTuote.getName());
		assertEquals(uusiTuote.getPrice(), luettuTuote.getPrice());
		assertEquals(uusiTuote.getQuantity(), luettuTuote.getQuantity());
	}
	
	@Test
	@DisplayName("Tuotteen poistaminen StorageModel-luokan avulla")
	void deleteProductTest() {
		Products uusiTuote = new Products("Imuri", 15, 189.90);
		int productId = model.addNewProduct(uusiTuote);
		
		boolean deleted = model.deleteProduct(productId);
		assertTrue(deleted);
		Products poistettu = model.getProduct(productId);
		assertNull(poistettu);
	}
	@Test
	@DisplayName("Kaikkien tuotteiden lukeminen StorageModel-luokan avulla")
	void readAllProductsTest() {
		
		List<Products> tuotteet = new ArrayList<>();
		
		int productAmount = 5;
		for(int i = 0; i < productAmount; i++) {
			Products uusiTuote = new Products("Mikroaaltouuni", 10, 89.99);
			tuotteet.add(uusiTuote);
			model.addNewProduct(uusiTuote);
		}
		
		Products[] lisatytTuotteet = model.getProducts();
		assertTrue(lisatytTuotteet.length > 0);
		assertTrue(lisatytTuotteet.length >= productAmount);		
	}
	
	

}
