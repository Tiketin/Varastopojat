package view;

import java.io.IOException;

public interface IUpdateQtyGUI {

	public abstract int getUpdatedQTY() throws IOException;

	public abstract void updateQTY() throws IOException;
}
