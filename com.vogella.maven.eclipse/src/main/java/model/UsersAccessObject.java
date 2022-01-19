package model;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import controller.Controller;

/**
 * The Class UsersAccessObject.
 *
 * @author Nikke Tikka
 */
public class UsersAccessObject implements IUsersDAO{
	
	/** The session factory. */
	private SessionFactory sessionFactory = null;
	
	/**
	 * Instantiates a new users access object.
	 */
	public UsersAccessObject() {
		try {
			
			sessionFactory = new Configuration().configure().buildSessionFactory();

		} catch (Exception e) {
			System.err.println("Istuntotehtaan luominen ei onnistunut. UsersAccessObject ");
			System.out.println(e);
			System.exit(-1);
		}
	}
	
	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	@Override
	public boolean createUser(Users user) {
		Transaction transaction = null;
		try (Session istunto = sessionFactory.openSession()) {
			if (user != null){
				transaction = istunto.beginTransaction();
				istunto.saveOrUpdate(user);
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
			System.err.println("ERROR - Create User");
			System.out.println(e);
		}

		return false;
	}
	
	/**
	 * Read user.
	 *
	 * @param userId the user id
	 * @return the users
	 */
	@Override
	public Users readUser(int userId) {

		Transaction transaction = null;
		try (Session istunto = sessionFactory.openSession()) {
			transaction = istunto.beginTransaction();
			Users user = new Users();
			istunto.load(user, userId);

			istunto.getTransaction().commit();

			return user;

		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			System.err.println("ERROR - read user - single");
			System.out.println(e);

			return null;
		}
	}
	
	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	@Override
	public boolean deleteUser(int userId) {
		Users user = readUser(userId);

		try (Session istunto = sessionFactory.openSession()) {

			istunto.beginTransaction();

			if (user != null) {
				istunto.delete(user);
			} else {
				return false;
			}

			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;

		} catch (Exception e) {
			System.err.println("Error - Delete User");
			System.out.println(e);

			return false;
		}

	}
	
	/**
	 * Update password.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	@Override
	public boolean updatePassword(Users user) {
		try (Session istunto = sessionFactory.openSession()) {

			String password = user.getPassword();

			istunto.beginTransaction();

			user = (Users) istunto.get(Users.class, user.getUserId());

			if (user != null) {
				user.setPassword(password);

			} else {
				System.out.println("Salasanan päivitys epäonnistui.");
				return false;
			}

			istunto.getTransaction().commit();
			Controller controller = Controller.Singleton();
			controller.update();
			return true;

		} catch (Exception e) {
			System.err.println("Error - update password");

			return false;
		}
	}
}
