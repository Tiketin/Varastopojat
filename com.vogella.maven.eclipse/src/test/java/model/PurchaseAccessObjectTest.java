package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PurchaseAccessObjectTest {
	
	private static IPurchaseDAO purchaseDAO;
	private static IProductsDAO productsDAO;
	private static ISupplierDAO supplierDAO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		purchaseDAO = new PurchaseAccessObject();
		productsDAO = new ProductsAccessObject();
		supplierDAO = new SupplierAccessObject();
	}

	@Test
	@DisplayName("Uuden ostotapahtuman luonti")
	void createPurchaseTest() throws Exception {		
		Products uusiTuote = new Products("Kola", 2, 1.99);
		Integer productId = productsDAO.createProduct(uusiTuote);
		
		Supplier toimittaja = new Supplier("R-kioski", "Kauniaistentie 1");
		Integer supplierId = supplierDAO.createSupplier(toimittaja);
		
		Purchase uusiOsto = new Purchase(10, productId, LocalDateTime.now(), supplierId);
		uusiOsto.setPurchaseCost( uusiOsto.getQty() * uusiTuote.getPrice());
		Integer purchaseId = purchaseDAO.createPurchase(uusiOsto);
		assertTrue(purchaseId > 0);	
	}
	
	@Test
	@DisplayName("Ostotapahtuman lukeminen tietokannasta")
	void readPurchaseTest() throws Exception {
		Products uusiTuote = new Products("Ahven", 2, 1.99);
		Integer productId = productsDAO.createProduct(uusiTuote);
		
		Supplier toimittaja = new Supplier("Alko", "Sompsanperä 3");
		Integer supplierId = supplierDAO.createSupplier(toimittaja);
		
		Purchase uusiOsto = new Purchase(10, productId, LocalDateTime.now(), supplierId);
		uusiOsto.setPurchaseCost( uusiOsto.getQty() * uusiTuote.getPrice());
		Integer purchaseId = purchaseDAO.createPurchase(uusiOsto);
		assertTrue(purchaseId > 0);
		
		Purchase luettuOsto = purchaseDAO.readPurchase(purchaseId);
		
		assertNotNull(luettuOsto);
		assertEquals(uusiOsto.getPurchaseCost(), luettuOsto.getPurchaseCost());
		assertEquals(uusiOsto.getQty(), luettuOsto.getQty());
		assertEquals(uusiOsto.getSupplierId(), luettuOsto.getSupplierId());
	}
	
	@Test
	@DisplayName("Kaikkien ostotapahtumien lukeminen tietokannasta")
	void readAllPurchasesTest() throws Exception {
		Products ensimmainenTuote = new Products("Lohi", 3, 21.30);
		Products toinenTuote = new Products("Taimen", 5, 25.50);
		
		Integer ensimmainenId = productsDAO.createProduct(ensimmainenTuote);
		Integer toinenId = productsDAO.createProduct(toinenTuote);
		assertTrue(ensimmainenId > 0 && toinenId > 0);
		
		Supplier toimittaja = new Supplier("Makuuni", "Konkurssikuja 3");
		Integer supplierIdA = supplierDAO.createSupplier(toimittaja);
		
		Supplier toinenToimittaja = new Supplier("Filmtown", "Konkurssikuja 4");
		Integer supplierIdB = supplierDAO.createSupplier(toinenToimittaja);
		assertTrue(supplierIdA > 0 && supplierIdB > 0);	
		
		Purchase ensimmainenOsto = new Purchase(15, ensimmainenId, LocalDateTime.now(), supplierIdA);
		ensimmainenOsto.setPurchaseCost( ensimmainenOsto.getQty() * ensimmainenTuote.getPrice() );
		Integer purchaseIdA = purchaseDAO.createPurchase(ensimmainenOsto);
		
		Purchase toinenOsto = new Purchase(10, toinenId, LocalDateTime.now(), supplierIdB); 
		toinenOsto.setPurchaseCost( toinenOsto.getQty() * toinenTuote.getPrice() );
		Integer purchaseIdB = purchaseDAO.createPurchase(toinenOsto);	
		assertTrue(purchaseIdA > 0 && purchaseIdB > 0);
		
		Purchase[] luetutOstot = purchaseDAO.readPurchases();
		assertTrue(luetutOstot.length >= 2);
			
	}
	
	@Test
	@DisplayName("Ostotapahtumien lukeminen samalla toimittajan ID:llä")
	void readAllPurchasesWithSameSupplierTest() throws Exception {
		
		Products ensimmainenTuote = new Products("Koiranruoka", 3, 21.30);
		Products toinenTuote = new Products("Pihvi", 5, 25.50);
		
		Integer ensimmainenId = productsDAO.createProduct(ensimmainenTuote);
		Integer toinenId = productsDAO.createProduct(toinenTuote);
		assertTrue(ensimmainenId > 0 && toinenId > 0);
		
		Supplier toimittaja = new Supplier("Sale", "Kaupantie 3");
		Integer supplierId = supplierDAO.createSupplier(toimittaja);
		assertTrue(supplierId > 0);
		
		Purchase eka = new Purchase(20, ensimmainenId, LocalDateTime.now(), supplierId);
		Purchase toka = new Purchase(25, toinenId, LocalDateTime.now(), supplierId);
		Purchase kolmas = new Purchase(40, ensimmainenId, LocalDateTime.now(), supplierId);
		
		eka.setPurchaseCost( eka.getQty() * ensimmainenTuote.getPrice() );
		toka.setPurchaseCost( toka.getQty() * toinenTuote.getPrice() );
		kolmas.setPurchaseCost( kolmas.getQty() * ensimmainenTuote.getPrice() );
		
		List<Purchase> ostotapahtumat = new ArrayList<>();
		ostotapahtumat.add(eka);
		ostotapahtumat.add(toka);
		ostotapahtumat.add(kolmas);
		
		for(Purchase osto : ostotapahtumat) {
			purchaseDAO.createPurchase(osto);
		}
		
		Purchase[] samanToimittajanOstot = purchaseDAO.readPurchasesWithSupplier(supplierId);
		assertTrue(samanToimittajanOstot.length > 0);
		assertEquals(samanToimittajanOstot.length, ostotapahtumat.size());
		
		int index = 0;
		for(Purchase osto : ostotapahtumat) {
			assertEquals(osto.getQty(), samanToimittajanOstot[index].getQty());
			assertEquals(osto.getPurchaseCost(), samanToimittajanOstot[index].getPurchaseCost());
			assertEquals(osto.getSupplierId(), samanToimittajanOstot[index].getSupplierId());
			assertEquals(osto.getPurchaseId(), samanToimittajanOstot[index].getPurchaseId());
			index++;
		}
	}
	
	@Test
	@DisplayName("Ostotapahtuman poistaminen") 
	public void deletePurchaseTest() throws Exception {
		Products ensimmainenTuote = new Products("Pakastepizza", 3, 3.65);
		Products toinenTuote = new Products("Jauheliha", 3, 4.35);
		
		Integer ensimmainenId = productsDAO.createProduct(ensimmainenTuote);
		Integer toinenId = productsDAO.createProduct(toinenTuote);
		assertTrue(ensimmainenId > 0 && toinenId > 0);
		
		Supplier toimittaja = new Supplier("Citymarket", "Leppävaarantie 1");
		Integer supplierId = supplierDAO.createSupplier(toimittaja);
		assertTrue(supplierId > 0);
		
		Purchase ostotapahtuma = new Purchase(20, ensimmainenId, LocalDateTime.now(), supplierId);
		Integer purchaseId = purchaseDAO.createPurchase(ostotapahtuma);
		assertTrue(purchaseId > 0);
		
		purchaseDAO.deletePurchase(purchaseId);
		Purchase poistettu = purchaseDAO.readPurchase(purchaseId);
		assertNull(poistettu);		
	}
	
	@Test
	@DisplayName("Ostotapahtuman päivittäminen")
	public void updatePurchaseTest() throws Exception {
		Products ensimmainenTuote = new Products("Sohvapöytä", 10, 250);
		Products toinenTuote = new Products("Jakkara", 5, 39.99);
		
		Integer ensimmainenId = productsDAO.createProduct(ensimmainenTuote);
		Integer toinenId = productsDAO.createProduct(toinenTuote);
		assertTrue(ensimmainenId > 0 && toinenId > 0);
		
		Supplier toimittaja = new Supplier("Citymarket", "Leppävaarantie 1");
		Integer supplierId = supplierDAO.createSupplier(toimittaja);
		assertTrue(supplierId > 0);
		
		Purchase ostotapahtuma = new Purchase(20, ensimmainenId, LocalDateTime.now(), supplierId);
		Integer purchaseId = purchaseDAO.createPurchase(ostotapahtuma);
		assertTrue(purchaseId > 0);
		
		Purchase samatapahtuma = purchaseDAO.readPurchase(purchaseId);
		int uusiMaara = 5;
		samatapahtuma.setPurchasedProductId(toinenId);
		samatapahtuma.setQty(uusiMaara);
		samatapahtuma.setPurchaseCost( samatapahtuma.getQty() * toinenTuote.getPrice() );
		
		boolean updated = purchaseDAO.updatePurchase(samatapahtuma);
		assertTrue(updated);
		
		Purchase paivitetty = purchaseDAO.readPurchase(purchaseId);
		assertEquals(paivitetty.getPurchasedProductId(), toinenId);
		assertEquals(paivitetty.getQty(), uusiMaara);
		
	}
}
