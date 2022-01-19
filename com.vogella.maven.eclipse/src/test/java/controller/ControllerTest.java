package controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import model.Products;
import model.Purchase;
import model.Supplier;

class ControllerTest {
	private static Controller kontrolleri = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		kontrolleri = Controller.Singleton();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		kontrolleri = null;
	}
	
	@Test
	@DisplayName("Tuotteen lisääminen Controller-luokalla")
	void addProductTest() {
		 int productId = kontrolleri.addNewProduct("Uusi tuote", 5, 9.99);
		 assertTrue(productId > 0);
	}
	
	@Test
	@DisplayName("Tuotteen poistaminen Controller-luokalla")
	void deleteProductTest() {
		int productId = kontrolleri.addNewProduct("Sohva", 5, 599.99);
		assertTrue(productId > 0);
				
		boolean deleted = kontrolleri.deleteProduct(productId);
		assertTrue(deleted);
	}
	
	@Test
	@DisplayName("Tuotteen lukeminen Controller-luokalla")
	void readProductTest() {
		String nimi = "Ruokapöytä";
		int maara = 10;
		double hinta = 455.90;
		int productId = kontrolleri.addNewProduct(nimi, maara, hinta);
		assertTrue(productId > 0);
		
		Products ruokapoyta = kontrolleri.getProduct(productId);
		assertNotNull(ruokapoyta);
		assertEquals(ruokapoyta.getName(), nimi);
		assertEquals(ruokapoyta.getQuantity(), maara);
		assertEquals(ruokapoyta.getPrice(), hinta);
	}
	
	@Test
	@DisplayName("Tuotteen saldon päivittäminen Controller-luokalla")
	void updateProductTest() {
		int uusiMaara = 10;
		int vanhaMaara = 5;
		int productId = kontrolleri.addNewProduct("Poreamme", 5, 1699.99);
		assertTrue(productId > 0);
		
		Products luotuTuote = kontrolleri.getProduct(productId);
		assertEquals(luotuTuote.getQuantity(), vanhaMaara);
		
		kontrolleri.changeQty(productId, uusiMaara);		
		
		luotuTuote = kontrolleri.getProduct(productId);
		assertEquals(luotuTuote.getQuantity(), uusiMaara);	
	}
	
	@Test
	@DisplayName("Tilauksen luominen Controller-luokalla")
	void createOrderTest() {		
		int tuoteMaara = 5;
		int[] productIds = new int[tuoteMaara];
		List<Products> tilatutTuotteet = new ArrayList<>();
		
		for(int i = 0; i < 5; i++) {
			Products uusiTuote = new Products("Tilaustestituote", tuoteMaara, 9.99);
			tilatutTuotteet.add(uusiTuote);		
			int productId = kontrolleri.addNewProduct(uusiTuote.getName(), uusiTuote.getQuantity(), uusiTuote.getPrice());
			productIds[i] = productId;
		}
		tilatutTuotteet = new ArrayList<>();
		
		for( int i = 0; i < 5; i++) {
			Products tuote = kontrolleri.getProduct(productIds[i]);
			tilatutTuotteet.add(tuote);
		}
			
		int orderId = kontrolleri.createOrder("Veikko varastolta", "Veikon äidin osoite", "Härmä", tilatutTuotteet);
		assertTrue(orderId > 0);
	}
	
	@Test
	@DisplayName("Toimittajan luominen Controller-luokalla")
	void createSupplierTest() {
		int supplierId = kontrolleri.createSupplier("Kesko", "Helsinki 123");
		assertTrue(supplierId > 0);
	}
	
	@Test
	@DisplayName("Ostotilauksen luominen Controller-luokalla")
	void createPurchaseTest() {
		int supplierId = kontrolleri.createSupplier("S-ryhmä", "Vaasa 12");
		assertTrue(supplierId > 0);
		
		Products uusiTuote = new Products("Imuri", 20, 89.99);
		int productId = kontrolleri.addNewProduct(uusiTuote.getName(), uusiTuote.getQuantity(), uusiTuote.getPrice());
		kontrolleri.setProductId(productId);
		
		int purchaseId = kontrolleri.createPurchase(uusiTuote.getQuantity(), supplierId);
		assertTrue(purchaseId > 0);
	}
	
	@Test
	@DisplayName("Ostotilausten haku Controller-luokalla")
	void readPurchaseProducts() {
		int supplierId = kontrolleri.createSupplier("Lidl", "Espoontie 123");
		assertTrue(supplierId > 0);
		
		Products uusiTuote = new Products("Pesukone", 20, 89.99);
		int productId = kontrolleri.addNewProduct(uusiTuote.getName(), uusiTuote.getQuantity(), uusiTuote.getPrice());
		kontrolleri.setProductId(productId);
		
		int purchaseId = kontrolleri.createPurchase(uusiTuote.getQuantity(), supplierId);
		assertTrue(purchaseId > 0);
		
		List<Purchase> ostotilaukset = kontrolleri.returnOstoTuote();
		assertNotNull(ostotilaukset);
		assertTrue(ostotilaukset.size() > 0);
	}
	
	
}
