package main;

import model.*;
import java.util.Scanner;


/**
 * The Class ProductMain.
 *
 * @author raoul
 */



public class ProductMain {
	
	/** The product DAO. */
	static IProductsDAO productDAO = new ProductsAccessObject();

	/** The scanner. */
	static Scanner scanner = new Scanner(System.in);
	
	/** The product. */
	private static Products product;
	
	/** The tuotteet. */
	private static Products[] tuotteet;

	
	
	
	/**
	 * List products.
	 */
	public static void listProducts() {
		tuotteet = productDAO.readProducts();

		for (Products p : tuotteet) {
			System.out.println(p.getProductId() + ", " + p.getName() + ", " + p.getQuantity() + ", " + p.getPrice());
		}

	}

	/**
	 * Adds the product.
	 */
	public static void addProduct() {

		System.out.println("Give Product name: ");
		String name = scanner.nextLine();

		System.out.println("QTY: ");
		int qty = Integer.parseInt(scanner.nextLine());

		System.out.println("Give product price: ");
		double price = Double.parseDouble(scanner.nextLine());

		product = new Products(name, qty, price);

		if (productDAO.createProduct(product) > 0) {
			System.out.println("Product is added into the database.");
		} else {
			System.out.println("Failure, cannot add product.");
		}
	}

	/**
	 * Update product.
	 */
	public static void updateProduct() {
		System.out.println("Give product ID (numbers only):");

		int productId = Integer.valueOf(scanner.nextLine());

		product = productDAO.readProduct(productId);

		System.out.println("Change  " + product.getName() + "name, qty, price or all (N/Q/P/A)");
		char answer = scanner.nextLine().toUpperCase().charAt(0);

		switch (answer) {
		case 'N':
			System.out.println("Give product name:");
			product.setName(scanner.nextLine());
			break;
		case 'Q':
			System.out.println("Give new qty:");
			try {
				product.setQuantity(Integer.parseInt(scanner.nextLine()));
			} catch (NumberFormatException nfe) {
				System.err.println("Only numbers, please.");
			}
			break;
		case 'P':
			System.out.println("Give new price:");
			try {
				product.setPrice(Double.parseDouble(scanner.nextLine()));
			} catch (NumberFormatException nfe) {
				System.err.println("Only numbers, please.");
			}
			break;

		case 'A':
			System.out.println("New name of product:");
			product.setName(scanner.nextLine());

			System.out.println("Set first qty and then price");
			try {
				product.setQuantity(Integer.parseInt(scanner.nextLine()));
				product.setPrice(Double.parseDouble(scanner.nextLine()));
			} catch (NumberFormatException nfe) {
				System.err.println("Only numbers, please..");
			}
			break;
		default:
			System.out.println("Error, please try again");
		}

		if (productDAO.updateProduct(product)) {
			System.out.println("Update is ok");
		} else {
			System.out.println("Failure.");
		}

	}

	/**
	 * Delete product.
	 */
	public static void deleteProduct() {
		System.out.println("Give product ID (numbers only): ");
		int productId = Integer.valueOf(scanner.nextLine());

		if (productDAO.deleteProduct(productId)) {
			System.out.println("OK, deleted.");
		} else {
			System.out.println("Failure.");
		}

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		char crud;
		final char CREATE = 'C', READ = 'R', UPDATE = 'U', DELETE = 'D', QUIT = 'Q';

		do {

			System.out.println("C: Create new product to database \n" + "R: Show all products at the database.\n"
					+ "U: Update data row.\n" + "D: Delete row from the database.\n" + "Q: Quit.");

			System.out.print("Key pressed: : ");

			crud = (scanner.nextLine().toUpperCase()).charAt(0);
			switch (crud) {
			case CREATE:
				addProduct();
				break;

			case READ:
				listProducts();
				break;

			case UPDATE:
				updateProduct();
				break;

			case DELETE:
				deleteProduct();
				break;
			}
		} while (crud != QUIT);
	}

}
