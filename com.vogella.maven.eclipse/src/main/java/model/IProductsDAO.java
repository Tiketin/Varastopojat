package model;

	
	public interface IProductsDAO {

		Integer createProduct(Products product);
		Products readProduct(int productId);
		Products[] readProducts();
		boolean updateProduct(Products product);
		boolean deleteProduct(int productId);

	}

