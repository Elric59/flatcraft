package dut.flatcraft;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import fr.univartois.migl.utils.DesignPattern;

/**
 * The most basic element found in the game.
 * 
 * One instance of MineElement represents a specific "Type" using the Type
 * Object design pattern.
 * 
 * @author leberre
 *
 */
@DesignPattern(name = "Type Object")
public interface MineElement extends Serializable {

	/**
	 * Retrieve the image used to represent that element on the map.
	 * 
	 * @return
	 */
	ImageIcon getImage();

	/**
	 * Create a new instance of this type
	 * 
	 * @return a new instance of this type
	 */
	MineElementInstance newInstance();

	/**
	 * Create a new instance of this type using a specific UI element.
	 * 
	 * @param label a UI representation of the instance
	 * @return a new instance of this type
	 */
	MineElementInstance newInstance(JLabel label);
}
