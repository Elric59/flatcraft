package dut.flatcraft.map;

import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;

/**
 * Generates a map in three parts:
 * 
 * <ul>
 * <li>first half of the map is the sky</li>
 * <li>then we have one line of grass</li>
 * <li>then the remaining of the map is for the soil</li>
 * </ul>
 * 
 * @author leberre
 *
 */
public class SimpleGenerator implements MapGenerator {

	@Override
	public GameMap generate(int width, int heigh, CellFactory factory) {
		SimpleGameMap map = new SimpleGameMap(width, heigh);
		int halfHeigh = heigh / 2;
		for (int i = 0; i < halfHeigh; i++) {
			for (int j = 0; j < width; j++) {
				map.setAt(i, j, factory.createSky());
			}
		}
		for (int j = 0; j < width; j++) {
			map.setAt(halfHeigh, j, factory.createGrass());

		}
		for (int i = halfHeigh + 1; i < heigh; i++) {
			for (int j = 0; j < width; j++) {
				map.setAt(i, j, factory.createSoil());
			}
		}
		return map;
	}

}
