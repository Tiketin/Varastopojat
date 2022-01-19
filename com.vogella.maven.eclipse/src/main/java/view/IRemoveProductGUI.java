package view;

import java.io.IOException;

public interface IRemoveProductGUI {

	public abstract int getDeleteProductID();

	public abstract void deleteProduct() throws IOException;
}
