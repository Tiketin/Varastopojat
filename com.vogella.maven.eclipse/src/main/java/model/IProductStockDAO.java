package model;

public interface IProductStockDAO {
	
	boolean createProductStock(ProductStock stock);
	
	ProductStock readProductStock(int stockId);

	boolean updateProductStock(ProductStock stock);

	boolean deleteProductStock(int stockId);
}
