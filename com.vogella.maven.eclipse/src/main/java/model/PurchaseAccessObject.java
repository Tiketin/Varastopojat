package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import controller.Controller;


/**
 * The Class PurchaseAccessObject.
 */
public class PurchaseAccessObject implements IPurchaseDAO {

	/** The istuntotehdas. */
	private SessionFactory istuntotehdas = null;

	/**
	 * Instantiates a new purchase access object.
	 */
	public PurchaseAccessObject() {
		try {
			istuntotehdas = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut. ");
			System.out.println(e);
			System.exit(-1);
		}
	}

	/**
	 * Creates the purchase.
	 *
	 * @param purchase the purchase
	 * @return the integer
	 */
	@Override
	public Integer createPurchase(Purchase purchase) {
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaction = istunto.beginTransaction();
			Integer purchaseId = (Integer) istunto.save(purchase);
			transaction.commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return purchaseId;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - Create Purchase");
			System.out.println(e);
		}
		return -1;
	}

	/**
	 * Read purchase.
	 *
	 * @param purchaseId the purchase id
	 * @return the purchase
	 */
	@Override
	public Purchase readPurchase(int purchaseId) {
		Purchase luettava = null;
		Transaction transaction = null;
		try (Session istunto = istuntotehdas.openSession()) {
			transaction = istunto.beginTransaction();
			luettava = istunto.find(Purchase.class, purchaseId);

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - read  purchase - single");
			System.out.println(e);
		}
		return luettava;
	}

	/**
	 * Read purchases.
	 *
	 * @return the purchase[]
	 */
	@Override
	public Purchase[] readPurchases() {
		Purchase[] purchases = null;
		try (Session istunto = istuntotehdas.openSession()) {

			@SuppressWarnings("unchecked")
			List<Purchase> result = istunto.createQuery("from Purchase").getResultList();
			purchases = result.toArray(new Purchase[result.size()]);

		} catch (Exception e) {
			System.err.println("Error - Listing of all");
			System.out.println(e);
			purchases = null;
		}
		return purchases;
	}

	/**
	 * Read purchases with supplier.
	 *
	 * @param supplierId the supplier id
	 * @return the purchase[]
	 */
	@Override
	public Purchase[] readPurchasesWithSupplier(int supplierId) {
		Purchase[] ostot = null;
		try (Session istunto = istuntotehdas.openSession()) {

			@SuppressWarnings("unchecked")
			Query<Purchase> query = istunto.createQuery("FROM Purchase where supplierId = :supId");
			query.setParameter("supId", supplierId);
			List<Purchase> result = query.getResultList();

			ostot = result.toArray(new Purchase[result.size()]);
		} catch (Exception e) {
			System.err.println("Error - Listing of all");
			System.out.println(e);
		}
		return ostot;
	}

	/**
	 * Update purchase.
	 *
	 * @param purchase the purchase
	 * @return true, if successful
	 */
	@Override
	public boolean updatePurchase(Purchase purchase) {
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();

			Purchase paivitettava = istunto.find(Purchase.class, purchase.getPurchaseId());

			if (paivitettava != null) {
				paivitettava.setQty(purchase.getQty());
				paivitettava.setSupplier(purchase.getSupplierId());
				paivitettava.setPurchasedProductId(purchase.getPurchasedProductId());
				paivitettava.setLocalDate(purchase.getLocalDate());
				paivitettava.setPurchaseCost(purchase.getPurchaseCost());

				istunto.getTransaction().commit();
				Controller controller = Controller.Singleton();
				controller.update();
				return true;
			}
		} catch (Exception e) {
			System.err.println("Error - update purchase");
		}
		return false;
	}

	/**
	 * Delete purchase.
	 *
	 * @param purchaseId the purchase id
	 * @return true, if successful
	 */
	@Override
	public boolean deletePurchase(int purchaseId) {
		try (Session istunto = istuntotehdas.openSession()) {
			istunto.beginTransaction();

			Purchase poistettavaOsto = istunto.load(Purchase.class, purchaseId);
			istunto.delete(poistettavaOsto);

			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;
		} catch (Exception e) {
			System.err.println("Error - Delete purchase");
			System.out.println(e);
			return false;
		}
	}
}
