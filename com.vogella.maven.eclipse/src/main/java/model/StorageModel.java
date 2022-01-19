package model;

/**
 * @author Pauli Vuole-Apiala The Class StorageModel.
 */
public class StorageModel implements IStorageModel {

	/** The product dao. */
	private IProductsDAO productDao = new ProductsAccessObject();

	/** The product stock dao. */
	private IProductStockDAO productStockDao = new ProductStockAccessObject();

	/** The product. */
	private Products product;

	/** The stock. */
	private ProductStock stock;

	/**
	 * Change qty.
	 *
	 * @param id  the id
	 * @param qty the qty
	 */
	public void changeQty(int id, int qty) {
		product = productDao.readProduct(id);
		System.out.println("model - productname: " + product.getName());
		System.out.println("model - productQTY: " + product.getQuantity());
		product.setQuantity(qty);
		try {
			productDao.updateProduct(product);
		} catch (Exception e) {
			System.err.println("Quantity change failed!");
			e.printStackTrace();
		}
		System.out.println("model - productname: " + product.getName());
		System.out.println("model - productQTY: " + product.getQuantity());
	}

	/**
	 * Change product stock.
	 *
	 * @param id  the id
	 * @param qty the qty
	 */
	public void changeProductStock(int id, int qty) {
		try {
			stock = productStockDao.readProductStock(id);
			stock.setQuantity(qty);
			productStockDao.updateProductStock(stock);
		} catch (Exception e) {
			System.err.println("Changing ProductStock quantity failed");
			e.printStackTrace();
		}
	}

	/**
	 * Adds the new product.
	 *
	 * @param newProduct the new product
	 */
	public int addNewProduct(Products newProduct) {
		try {
			int productId = productDao.createProduct(newProduct);
			if (productId > 0) {
				System.out.println("New product added successfully");
				return productId;
			}
		} catch (Exception e) {
			System.err.println("Error adding a new product to the database (Controller): " + e.getMessage());
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * Gets the products.
	 *
	 * @return the products
	 */
	public Products[] getProducts() {
		return productDao.readProducts();
	}

	/**
	 * Gets the product.
	 *
	 * @param id the id
	 * @return the product
	 */
	public Products getProduct(int id) {
		return productDao.readProduct(id);
	}

	/**
	 * Delete product.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean deleteProduct(int id) {
		boolean product = productDao.deleteProduct(id);
		// boolean stock = productStockDao.deleteProductStock(id);
		if (product /* && stock */) {
			return true;
		} else {
			return false;
		}
	}

}
