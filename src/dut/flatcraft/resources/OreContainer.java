package dut.flatcraft.resources;

import java.awt.datatransfer.DataFlavor;

public class OreContainer extends ResourceContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final DataFlavor ORE_FLAVOR = new DataFlavor(ResourceContainer.class, "orecontainer");

	public OreContainer(Resource block, int quantity) {
		super(block, quantity);
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { ORE_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return ORE_FLAVOR.equals(flavor);
	}
	
	@Override
	public OreContainer clone() {
		return new OreContainer(getResource(), getQuantity());
	}
}
