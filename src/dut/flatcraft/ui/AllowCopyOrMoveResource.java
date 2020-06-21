package dut.flatcraft.ui;

import java.awt.Container;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import dut.flatcraft.resources.ResourceContainer;

final class AllowCopyOrMoveResource extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private final CraftTable craftTable;

	/**
	 * @param craftTable
	 */
	AllowCopyOrMoveResource(CraftTable craftTable) {
		this.craftTable = craftTable;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return MOVE | COPY;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
		return ((ResourceContainerUI) c).getResourceContainer().clone();
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
		Container container = source.getParent();
		ResourceContainer rc = ((ResourceContainerUI) source).getResourceContainer();
		if (action == MOVE || rc.getQuantity() == 0) {
			container.remove(source);
			container.revalidate();
			container.repaint();
			this.craftTable.processCrafting();
		} else if (action == COPY) {
			if (rc.getQuantity() == 1) {
				rc.consume(1);
				container.removeAll();
			} else {
				rc.consume(rc.getQuantity() / 2);
			}
			container.revalidate();
			container.repaint();
			this.craftTable.processCrafting();
		}
	}
}