package dut.flatcraft;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class TransferableJLabel extends JLabel implements Transferable {

	public static final DataFlavor JLABEL_FLAVOR = new DataFlavor(Transferable.class,"jlabel");
	
	public TransferableJLabel(ImageIcon image) {
		super(image);
	}
	
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{JLABEL_FLAVOR};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return JLABEL_FLAVOR.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return getIcon();
	}

}
