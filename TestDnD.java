package dut.flatcraft;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class TestDnD {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,3));
		JLabel dest = new JLabel("Dest");
		dest.setTransferHandler(new CibleTH());
		JLabel rien = new JLabel("Rien");
		JLabel source = new TransferableJLabel(MineUtils.BRICK);
		source.setTransferHandler(new SourceTH());
		MouseListener mouselistener = new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JComponent comp = (JComponent) me.getSource();
                TransferHandler handler = comp.getTransferHandler();
                if (me.getButton() == MouseEvent.BUTTON1) {
                    handler.exportAsDrag(comp, me, TransferHandler.MOVE);
                } else {
                    handler.exportAsDrag(comp, me, TransferHandler.COPY);
                }
            }
        };
        source.addMouseListener(mouselistener);
		panel.add(dest);
		panel.add(rien);
		panel.add(source);
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
	}
}
