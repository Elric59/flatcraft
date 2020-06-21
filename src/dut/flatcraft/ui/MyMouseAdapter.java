package dut.flatcraft.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class MyMouseAdapter extends MouseAdapter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void mousePressed(MouseEvent me) {
		JComponent comp = (JComponent) me.getSource();
		TransferHandler handler = comp.getTransferHandler();
		if (me.getButton() == MouseEvent.BUTTON1) {
			handler.exportAsDrag(comp, me, TransferHandler.MOVE);
		} else {
			handler.exportAsDrag(comp, me, TransferHandler.COPY);
		}
	}
}
