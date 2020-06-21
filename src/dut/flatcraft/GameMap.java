package dut.flatcraft;

import java.io.Serializable;

import dut.flatcraft.player.Coordinate;

/**
 * Represents a generic game map.
 * 
 * @author leberre
 *
 */
public interface GameMap extends Serializable {

	/**
	 * 
	 * @return the height of the map in number of cells.
	 */
	int getHeight();

	/**
	 * 
	 * @return the width of the map in number of cells.
	 */
	int getWidth();

	/**
	 * Retrieve the cell at a given coordinate.
	 * 
	 * @param i the y axis (starting from 0)
	 * @param j the x axis (starting from 0)
	 * @return the cell at the (j,i) coordinate
	 */
	Cell getAt(int i, int j);

	/**
	 * Set a cell at a given coordinate
	 * 
	 * @param i    the y axis (starting from 0)
	 * @param j    the x axis (starting from 0)
	 * @param cell a cell
	 */
	void setAt(int i, int j, Cell cell);

	/**
	 * Find the coordinate of a given cell.
	 * 
	 * @param cell a Cell
	 * @return the coordinate of that cell in the map.
	 */
	Coordinate findCell(Cell cell);
}
