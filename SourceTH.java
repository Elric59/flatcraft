package dut.flatcraft;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

public class SourceTH extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getSourceActions(JComponent c) {
		return MOVE | COPY;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		return (TransferableJLabel)c;
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		TransferableJLabel tjlabel = (TransferableJLabel) source;
		if (action == MOVE) {
			tjlabel.setIcon(null);
		} else {
			// quand on copie
			System.out.println("Copié");
		}
	}

}
