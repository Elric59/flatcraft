package dut.flatcraft.map;

import java.util.function.Supplier;

import dut.flatcraft.Cell;
import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;
import fr.univartois.migl.utils.DesignPattern;

/**
 * Create a magicTree on the map
 */
@DesignPattern(name = "Decorator")
public class MagicTreeDecorator implements MapGenerator {

    private int maxHeight;
    private MapGenerator decorated;

    /**
     *
     * @param decorated the decorated MapGenerator
     * @param maxHeight the maximum height of the trees
     */
    public MagicTreeDecorator(MapGenerator decorated, int maxHeight) {
        this.decorated = decorated;
        this.maxHeight = maxHeight;
    }

    @Override
    public GameMap generate(int width, int height, CellFactory factory) {
        GameMap map = decorated.generate(width, height, factory);
            int x = RAND.nextInt(width);
            int y = height / 2 - 1;
            int treeHeight = maxHeight + 1;
            for (int j = 0; j < treeHeight; j++) {
                makeCell(map, y--, x, factory::createCrystalTree);
            }
            if (x > 0) {
                makeCell(map, y + 1, x - 1, factory::createCrystalLeaves);
                makeCell(map, y, x - 1, factory::createCrystalLeaves);
            }

            makeCell(map, y, x, factory::createCrystalLeaves);
            if (x + 1 < map.getWidth()) {
                makeCell(map, y + 1, x + 1, factory::createCrystalLeaves);
                makeCell(map, y, x + 1, factory::createCrystalLeaves);
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