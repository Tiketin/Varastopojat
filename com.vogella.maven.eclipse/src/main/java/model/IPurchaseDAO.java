package model;

public interface IPurchaseDAO {
	
	Integer createPurchase(Purchase purchase);
	Purchase readPurchase(int purchaseId);
	Purchase[] readPurchases();
	boolean updatePurchase(Purchase purchase);
	boolean deletePurchase(int purchaseId);
	Purchase[] readPurchasesWithSupplier(int supplierId);
}
