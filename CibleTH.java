package dut.flatcraft;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.TransferHandler;

public class CibleTH extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean canImport(TransferSupport support) {
		return support.isDataFlavorSupported(TransferableJLabel.JLABEL_FLAVOR);
	}

	@Override
	public boolean importData(TransferSupport support) {
		System.err.println("Importing data");
		if (support.isDrop()) {
			// transtypage vers l'objet source (connu du programmeur)
			JLabel dest = (JLabel) support.getComponent();
			try {
				ImageIcon image = (ImageIcon) support.getTransferable().getTransferData(TransferableJLabel.JLABEL_FLAVOR);
				dest.setIcon(image);
				return true;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		} else {
			return false;
		}
	}

}
