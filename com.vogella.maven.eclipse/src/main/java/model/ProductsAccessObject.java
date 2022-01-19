package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import controller.Controller;


/**
 * @author raoul
 * 
 */
public class ProductsAccessObject implements IProductsDAO {

	/** The istuntotehdas. */
	private SessionFactory istuntotehdas = null;

	/**
	 * Instantiates a new products access object.
	 */
	public ProductsAccessObject() {

		try {
			
			istuntotehdas = new Configuration().configure().buildSessionFactory();

		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut. productAccessobject ");
			System.out.println(e);
			System.exit(-1);
		}
	}
	
	

	/**
	 * Creates the product.
	 *
	 * @param product the product
	 * @return the integer
	 */
	@Override
	public Integer createProduct(Products product) {
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			if (product != null){
				transaction = istunto.beginTransaction();
				Integer id = (Integer) istunto.save(product);
				transaction.commit();
				Controller controller = Controller.Singleton();
				controller.update();
				return id;
			} 
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - Create Product");
			System.out.println(e);
		}
		return -1;
	}

	/**
	 * Read product.
	 *
	 * @param productId the product id
	 * @return the products
	 */
	@Override
	public Products readProduct(int productId) {
		System.out.println(productId);
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaction = istunto.beginTransaction();
			Products product = new Products();
			try {
				istunto.load(product, productId);
			} catch (Exception e) {
				if (transaction != null)
					transaction.rollback();
				System.err.println("ERROR - read product - single");
				System.out.println(e);
				System.out.println("null");
				return null;
			}
			//istunto.load(product, productId);
			istunto.getTransaction().commit();

			return product;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - read product - single");
			System.out.println(e);
			System.out.println("null");
			return null;
		}
	}

	/**
	 * Read products.
	 *
	 * @return the products[]
	 */
	@Override
	public Products[] readProducts() {
		Products[] products;
		try (Session istunto = istuntotehdas.openSession()) {
			
			@SuppressWarnings("unchecked")
			List<Products> result = istunto.createQuery("from Products").getResultList();
			products = result.toArray(new Products[result.size()]);

		} catch (Exception e) {
			System.err.println("Error - Listing of all products");
			System.out.println(e);
			products = null;

		}

		return products;
	}

	/**
	 * Update product.
	 *
	 * @param product the product
	 * @return true, if successful
	 */
	@Override
	public boolean updateProduct(Products product) {
		try (Session istunto = istuntotehdas.openSession()) {
			int qty = product.getQuantity();
			String name = product.getName();
			double price = product.getPrice();

			istunto.beginTransaction();

			product = (Products) istunto.find(Products.class, product.getProductId());

			if (product != null) {
				product.setQuantity(qty);
				product.setName(name);
				product.setPrice(price);			
			} else {
				System.out.println("Päivitys epäonnistui.");
				return false;
			}
			
			istunto.saveOrUpdate(product);

			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;

		} catch (Exception e) {
			System.err.println("Error - update product");

			return false;
		}
	}

	/**
	 * Delete product.
	 *
	 * @param productId the product id
	 * @return true, if successful
	 */
	@Override
	public boolean deleteProduct(int productId) {
		Products product = readProduct(productId);
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();
			if (product != null) {
				istunto.delete(product);
			} else {
				return false;
			}
			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;

		} catch (Exception e) {
			System.err.println("Error - Delete Product");
			System.out.println(e);

			return false;
		}

	}

}
