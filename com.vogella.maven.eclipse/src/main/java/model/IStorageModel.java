package model;

public interface IStorageModel {
	public abstract void changeQty(int id, int qty);
	public abstract void changeProductStock(int id, int qty);
	public abstract int addNewProduct(Products newProduct);
	public abstract Products[] getProducts();
	public abstract Products getProduct(int id);
	public abstract boolean deleteProduct(int id);

}
