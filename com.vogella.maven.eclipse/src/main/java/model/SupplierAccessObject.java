package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import controller.Controller;


/**
 * The Class SupplierAccessObject.
 */
public class SupplierAccessObject implements ISupplierDAO {


	private SessionFactory istuntotehdas = null;

	/**
	 * Instantiates a new supplier access object.
	 */
	public SupplierAccessObject() {

		try {

			istuntotehdas = new Configuration().configure().buildSessionFactory();

		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut. SupplierAccessObject ");
			System.out.println(e);
			System.exit(-1);
		}
	}

	/**
	 * Creates the supplier.
	 *
	 * @param toimittaja the toimittaja
	 * @return the integer
	 */
	@Override
	public Integer createSupplier(Supplier toimittaja) {
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaction = istunto.beginTransaction();
			Integer supplierId = (Integer) istunto.save(toimittaja);
			transaction.commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return supplierId;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - Create Supplier");
			System.out.println(e);
			return -1;
		}
	}

	/**
	 * Read supplier.
	 *
	 * @param supplierId the supplier id
	 * @return the supplier
	 */
	@Override
	public Supplier readSupplier(int supplierId) {
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaction = istunto.beginTransaction();
			
			Supplier supplier = istunto.find(Supplier.class, supplierId);
			
			istunto.getTransaction().commit();
			return supplier;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - readSupplier - single");
			System.out.println(e);
			return null;
		}
	}

	/**
	 * Read suppliers.
	 *
	 * @return the supplier[]
	 */
	@Override
	public Supplier[] readSuppliers() {
		Supplier[] suppliers = null;
		try (Session istunto = istuntotehdas.openSession()) {

			@SuppressWarnings("unchecked")
			List<Supplier> result = istunto.createQuery("from Supplier").getResultList();
			suppliers = result.toArray(new Supplier[result.size()]);
		} catch (Exception e) {
			System.err.println("Error - Listing of all supplierS");
			System.out.println(e);
		}

		return suppliers;
	}

	/**
	 * Update supplier.
	 *
	 * @param paivitetty the paivitetty
	 * @return true, if successful
	 */
	@Override
	public boolean updateSupplier(Supplier paivitetty) {
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();

			Supplier paivitettava = (Supplier) istunto.get(Supplier.class, paivitetty.getSupplierId());

			if (paivitettava != null) {
				paivitettava.setNameOfSupplier(paivitetty.getNameOfSupplier());
				paivitettava.setAddress(paivitetty.getSupplierAddress());
			} else {
				System.out.println("Päivitys epäonnistui.");
				return false;
			}
			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;
		} catch (Exception e) {
			System.err.println("Error - update Supplier");
			return false;
		}
	}

	/**
	 * Delete supplier.
	 *
	 * @param supplierId the supplier id
	 * @return true, if successful
	 */
	@Override
	public boolean deleteSupplier(int supplierId) {

		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();

			Supplier poistettava = istunto.load(Supplier.class, supplierId);
			istunto.delete(poistettava);
			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;
		} catch (Exception e) {
			System.err.println("Error - Delete Supplier");
			System.out.println(e);
			return false;
		}
	}
}
