package dut.flatcraft.ui;

import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.TransferHandler;

import dut.flatcraft.resources.OreContainer;
import dut.flatcraft.resources.ResourceContainer;

final class AcceptOreTransfert extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Furnace furnace;

	public AcceptOreTransfert(Furnace furnace) {
		this.furnace = furnace;
	}

	@Override
	public boolean canImport(TransferSupport support) {
		return support.isDataFlavorSupported(OreContainer.ORE_FLAVOR);
	}

	@Override
	public boolean importData(TransferSupport support) {
		if (support.isDrop()) {
			JPanel source = (JPanel) support.getComponent();
			try {
				ResourceContainerUI comp;
				ResourceContainer rc = (ResourceContainer) support.getTransferable()
						.getTransferData(OreContainer.ORE_FLAVOR);
				if (support.getDropAction() == MOVE || rc.getQuantity() == 1) {
					comp = new ResourceContainerUI(rc);
				} else {
					comp = new ResourceContainerUI(rc.getBlock().getType(), rc.getQuantity() / 2);
				}
				source.removeAll();
				source.add(comp);
				furnace.processCooking();
				source.revalidate();
				source.repaint();
				return true;
			} catch (Exception e) {
				Logger.getAnonymousLogger().warning(e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}
}