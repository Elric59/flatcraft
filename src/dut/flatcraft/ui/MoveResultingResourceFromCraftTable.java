package dut.flatcraft.ui;

import java.awt.Container;
import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

final class MoveResultingResourceFromCraftTable extends TransferHandler {
	/**
	 * 
	 */
	private final CraftTable craftTable;

	/**
	 * @param craftTable
	 */
	MoveResultingResourceFromCraftTable(CraftTable craftTable) {
		this.craftTable = craftTable;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getSourceActions(JComponent c) {
	    return MOVE;
	}

	@Override
	protected Transferable createTransferable(JComponent c) {
	    if (c instanceof ResourceContainerUI) {
	        return ((ResourceContainerUI) c).getResourceContainer();
	    }
	    return ((ToolInstanceUI) c).getMineTool();
	}

	@Override
	protected void exportDone(JComponent source, Transferable data, int action) {
	    this.craftTable.consumeOneItem();
	    Container container = source.getParent();
	    container.remove(source);
	    this.craftTable.processCrafting();
	    container.revalidate();
	    container.repaint();
	}
}