package dut.flatcraft.resources;

import java.awt.datatransfer.DataFlavor;

public class CombustibleContainer extends ResourceContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final DataFlavor COMBUSTIBLE_FLAVOR = new DataFlavor(CombustibleContainer.class, "combustibleflavor");

	public CombustibleContainer(Resource block, int quantity) {
		super(block, quantity);
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { COMBUSTIBLE_FLAVOR, RESOURCE_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return COMBUSTIBLE_FLAVOR.equals(flavor);
	}

	@Override
	public CombustibleContainer clone() {
		return new CombustibleContainer(getResource(), getQuantity());
	}
}
