package dut.flatcraft;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dut.flatcraft.player.Player;
import dut.flatcraft.resources.Resource;

/**
 * A cell representing the absence of resource on the map.
 * 
 * @author leberre
 *
 */
public class EmptyCell implements Cell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ImageIcon image;
	private JLabel label;

	private static final Resource TYPE = MineUtils.getResourceByName("air");

	public EmptyCell(ImageIcon image, JLabel label) {
		this.image = image;
		this.label = label;
		label.setIcon(image);
		label.setToolTipText("Empty");
	}

	public EmptyCell(ImageIcon image) {
		this(image, new JLabel());
	}

	@Override
	public ImageIcon getImage() {
		return image;
	}

	@Override
	public boolean manage(Player p) {
		return p.lookingDown.next();
	}

	@Override
	public JLabel getUI() {
		return label;
	}

	@Override
	public String getName() {
		return "empty";
	}

	@Override
	public Resource getType() {
		return TYPE;
	}

	@Override
	public boolean dig(Player p) {
		p.next();
		return false;
	}

	@Override
	public boolean canBeReplacedBy(Cell c, Player p) {
		return true;
	}
}
