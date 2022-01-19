package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import controller.Controller;


/**
 * @author raoul
 */
public class ProductStockAccessObject implements IProductStockDAO {

	/** The istuntotehdas. */
	private SessionFactory istuntotehdas = null;

	/**
	 * Instantiates a new product stock access object.
	 */
	public ProductStockAccessObject() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();

		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut. productStockAccessObject ");
			System.out.println(e);
			System.exit(-1);
		}
	}
	
	/**
	 * Creates the product stock.
	 *
	 * @param stock the stock
	 * @return true, if successful
	 */
	@Override
	public boolean createProductStock(ProductStock stock) {
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			if (stock != null) {
				transaction = istunto.beginTransaction();
				istunto.saveOrUpdate(stock);
				transaction.commit();
				Controller controller = Controller.Singleton();
				controller.update();
				return true;

			} else {
				return false;
			}
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("Error creating productStock: " + e.getMessage());
			System.out.println(e);
		}
		return false;
	}
	
	/**
	 * Read product stock.
	 *
	 * @param stockId the stock id
	 * @return the product stock
	 */
	@Override
	public ProductStock readProductStock(int stockId) {
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaction = istunto.beginTransaction();
			ProductStock productStock = new ProductStock();
			istunto.load(productStock, stockId);
			istunto.getTransaction().commit();

			return productStock;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("Error reading productStock: " + e.getMessage());
			System.out.println(e);

			return null;
		}
	}
	
	/**
	 * Update product stock.
	 *
	 * @param stock the stock
	 * @return true, if successful
	 */
	@Override
	public boolean updateProductStock(ProductStock stock) {
		try (Session istunto = istuntotehdas.openSession()) {			
			
			int quantity = stock.getQuantity();			
			istunto.beginTransaction();	
			
			/*stock = (ProductStock) istunto.get(ProductStock.class, stock.getStockId());
			if (stock != null) {
				stock.setQuantity(quantity);
			} else {
				System.out.println("Päivitys epäonnistui.");
				return false;
			}
			istunto.getTransaction().commit();*/
			Controller controller = Controller.Singleton();
			controller.update();
			return true;

		} catch (Exception e) {
			System.err.println("Error - update product");

			return false;
		}
	}
	
	/**
	 * Delete product stock.
	 *
	 * @param stockId the stock id
	 * @return true, if successful
	 */
	@Override
	public boolean deleteProductStock(int stockId) {
		ProductStock productStock = readProductStock(stockId);

		try (Session istunto = istuntotehdas.openSession()) {

			istunto.beginTransaction();

			if (productStock != null) {
				istunto.delete(productStock);
			} else {
				return false;
			}

			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;

		} catch (Exception e) {
			System.err.println("Error - Delete ProductStockS");
			System.out.println(e);

			return false;
		}
	}

}
