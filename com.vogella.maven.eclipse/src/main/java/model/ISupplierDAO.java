package model;

public interface ISupplierDAO {

	Supplier readSupplier(int supplierId);
	Supplier[] readSuppliers();
	boolean updateSupplier(Supplier supplier);
	boolean deleteSupplier(int supplierId);
	Integer createSupplier(Supplier toimittaja);

}
