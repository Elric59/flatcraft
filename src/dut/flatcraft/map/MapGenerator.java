package dut.flatcraft.map;

import java.util.Random;

import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;

/**
 * Generates a GameMap of a given size with a given CellFactory.
 * 
 * @author leberre
 *
 */
public interface MapGenerator {
	Random RAND = new Random();

	/**
	 * Generates a new map.
	 * 
	 * @param width   width in number of cells
	 * @param heigh   height in number of cells
	 * @param factory a CellFactory
	 * @return
	 */
	GameMap generate(int width, int heigh, CellFactory factory);
}
