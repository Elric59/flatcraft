package dut.flatcraft.map;

import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;
import fr.univartois.migl.utils.DesignPattern;

/**
 * Adds a "terril" somewhere in the map.
 * 
 * The "terril" is like a pyramid made of soil.
 * 
 * @author leberre
 *
 */
@DesignPattern(name = "Decorator")
public class TerrilDecorator implements MapGenerator {

	private int maxHeight;
	private MapGenerator decorated;

	/**
	 * 
	 * @param decorated the decorated MapGenerator
	 * @param maxHeight the maximum height of the terril
	 */
	public TerrilDecorator(MapGenerator decorated, int maxHeight) {
		this.decorated = decorated;
		this.maxHeight = maxHeight;
	}

	@Override
	public GameMap generate(int width, int height, CellFactory factory) {
		GameMap map = decorated.generate(width, height, factory);
		int terrilHeight = RAND.nextInt(maxHeight) + 1;
		int x = RAND.nextInt(width - terrilHeight) + terrilHeight;
		int y = height / 2;
		for (int j = 0; j < terrilHeight; j++) {
			for (int i = 0; i < 2 * j + 1; i++) {
				map.setAt(y - terrilHeight + j, x + i, factory.createSoil());
			}
			x--;
		}
		return map;
	}

}
