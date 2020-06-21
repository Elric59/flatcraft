package dut.flatcraft;

import fr.univartois.migl.utils.DesignPattern;

/**
 * Abstraction to create the different kinf of cell found in the map.
 * 
 * @author leberre
 *
 */
@DesignPattern(name = "Abstract Factory")
public interface CellFactory {

	/**
	 * Those cells do not contain anything: there is nothing to dig, you cannot stay
	 * on that cell.
	 * 
	 * @return
	 */
	Cell createSky();

	/**
	 * The separation between the sky and the soil.
	 * 
	 * @return
	 */
	Cell createGrass();

	/**
	 * The place to dig.
	 * 
	 * @return
	 */
	Cell createSoil();

	/**
	 * The wood found in the map. Useful to craft. Useful to heat the furnace.
	 * 
	 * @return
	 */
	Cell createTree();

	/**
	 * The leaves of the tree, useful for the furnace.
	 * 
	 * @return
	 */
	Cell createLeaves();
	/**
	 * The leaves of the Magic Tree,it give Crystal ore.
	 *
	 * @return
	 */
	Cell createCrystalLeaves();
	/**
	 * The wood of the Magic Tree,it give Crystal ore.
	 *
	 * @return
	 */
	Cell createCrystalTree();


}
