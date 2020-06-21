package dut.flatcraft;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dut.flatcraft.player.Player;

/**
 * A cell represents a unit space in the map. It can be filled with a resource
 * or empty (i.e. the sky, the air).
 * 
 * @author leberre
 *
 */
public interface Cell extends Serializable {

	/**
	 * Retrieve the graphical representation of the cell on the map.
	 * 
	 * @return
	 */
	ImageIcon getImage();

	/**
	 * Retrieve the graphical component for that cell.
	 * 
	 * @return
	 */
	JLabel getUI();

	/**
	 * Retrieve the name of the cell.
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Retrieve the type of the cell.
	 * 
	 * @return
	 */
	MineElement getType();

	/**
	 * Indicate if the cell changed the position of the player.
	 * 
	 * @param p a player
	 * @return true iff the position of the player has changed
	 */
	boolean manage(Player p);

	/**
	 * Dig the cell on the map.
	 * 
	 * @param p a player
	 * @return
	 */
	boolean dig(Player p);

	/**
	 * Execute something on that cell.
	 * 
	 * @return
	 */
	default boolean execute() {
		return false;
	}

	/**
	 * Check if the current cell can be replaced by another cell on the map.
	 * 
	 * @param c a new cell
	 * @param p a player
	 * @return true iff the new cell can replace the current one on the map
	 */
	boolean canBeReplacedBy(Cell c, Player p);
}
