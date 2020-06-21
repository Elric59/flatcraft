package dut.flatcraft.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class MyMouseAdapterForInventory extends MouseAdapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void mousePressed(MouseEvent me) {
		if (!me.isControlDown()) {
			JComponent comp = (JComponent) me.getSource();
			TransferHandler handler = comp.getTransferHandler();
			handler.exportAsDrag(comp, me, TransferHandler.COPY);
		}
	}
}
