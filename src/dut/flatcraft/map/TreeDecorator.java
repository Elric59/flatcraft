package dut.flatcraft.map;

import java.util.function.Supplier;

import dut.flatcraft.Cell;
import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;
import fr.univartois.migl.utils.DesignPattern;

/**
 * Randomly add a given number of trees on the map.
 * 
 * @author leberre
 *
 */
@DesignPattern(name = "Decorator")
public class TreeDecorator implements MapGenerator {

	private int nbTree;
	private int maxHeight;
	private MapGenerator decorated;

	/**
	 * 
	 * @param decorated the decorated MapGenerator
	 * @param nbTree    the number of trees to add
	 * @param maxHeight the maximum height of the trees
	 */
	public TreeDecorator(MapGenerator decorated, int nbTree, int maxHeight) {
		this.decorated = decorated;
		this.nbTree = nbTree;
		this.maxHeight = maxHeight;
	}

	@Override
	public GameMap generate(int width, int height, CellFactory factory) {
		GameMap map = decorated.generate(width, height, factory);
		for (int i = 0; i < nbTree; i++) {
			int x = RAND.nextInt(width);
			int y = height / 2 - 1;
			int treeHeight = RAND.nextInt(maxHeight) + 1;
			for (int j = 0; j < treeHeight; j++) {
				makeCell(map, y--, x, factory::createTree);
			}
			if (x > 0) {
				makeCell(map, y + 1, x - 1, factory::createLeaves);
				makeCell(map, y, x - 1, factory::createLeaves);
			}

			makeCell(map, y, x, factory::createLeaves);
			if (x + 1 < map.getWidth()) {
				makeCell(map, y + 1, x + 1, factory::createLeaves);
				makeCell(map, y, x + 1, factory::createLeaves);
			}
		}
		return map;
	}

	/*
	 * That utility method makes sure that trees are only rendered on empty cells.
	 */
	private void makeCell(GameMap map, int y, int x, Supplier<Cell> creator) {
		Cell cell = map.getAt(y, x);
		if ("empty".equals(cell.getName())) {
			map.setAt(y, x, creator.get());
		}

	}

}
