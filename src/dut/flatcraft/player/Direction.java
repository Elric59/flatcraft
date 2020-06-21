package dut.flatcraft.player;

import java.awt.Graphics;
import java.io.Serializable;

import fr.univartois.migl.utils.DesignPattern;

@DesignPattern(name = "State")
public interface Direction extends Serializable {
	/**
	 * Move the player to the next position
	 * 
	 * @return true iff the player can move
	 */
	boolean next();

	/**
	 * Compute the coordinate of the block to dig
	 * 
	 * @return the coordinate in front of the player
	 */
	Coordinate toDig();

	/**
	 * Render the direction
	 * 
	 * @param g the graphical context
	 */
	void paint(Graphics g);

	/**
	 * Compute the position for putting a resource on the map
	 * 
	 * @return the coordinate where to put a resource
	 */
	Coordinate nextForResource();
}
