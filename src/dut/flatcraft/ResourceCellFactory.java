package dut.flatcraft;

import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import dut.flatcraft.resources.ExecutableAndTransformableResourceInstance;
import dut.flatcraft.resources.ExecutableResourceInstance;

public class ResourceCellFactory implements CellFactory {

	public static final Random RAND = new Random();

	@Override
	public Cell createSky() {
		if (RAND.nextInt(10) < 1) {
			return new EmptyCell(MineUtils.getImage("cloud"));
		}
		return new EmptyCell(MineUtils.getImage("ice"));
	}

	@Override
	public Cell createGrass() {
		if (RAND.nextInt(10) < 1) {
			return MineUtils.getResourceByName("junglegrass").newInstance();
		}
		if (RAND.nextInt(10) < 2) {
			return MineUtils.getResourceByName("water").newInstance();
		}
        if (RAND.nextInt(50) < 5) {
            ExecutableAndTransformableResourceInstance monster = (ExecutableAndTransformableResourceInstance) MineUtils.getResourceByName("monster").newInstance();
            monster.setRunnable(()->JOptionPane.showMessageDialog(null, new JLabel("I'm gonna kill you!"), "Attention", JOptionPane.PLAIN_MESSAGE));
            return monster;

        }
		return MineUtils.getResourceByName("grass").newInstance();
	}

	@Override
	public Cell createSoil() {
		if (RAND.nextInt(10) < 7) {
			return MineUtils.getResourceByName("dirt").newInstance();
		}
		if (RAND.nextInt(100) < 10) {
			return MineUtils.getResourceByName("coal").newInstance();
		}
		if (RAND.nextInt(100) < 5) {
			return MineUtils.getResourceByName("gold").newInstance();
		}
		if (RAND.nextInt(100) < 5) {
			return MineUtils.getResourceByName("iron").newInstance();
		}
		if (RAND.nextInt(100) < 3) {
			return MineUtils.getResourceByName("diamond").newInstance();
		}

		if (RAND.nextInt(100) < 5) {
			return MineUtils.getResourceByName("copper").newInstance();
		}
		if (RAND.nextInt(100) < 1) {
			ExecutableResourceInstance chest = (ExecutableResourceInstance) MineUtils.getResourceByName("chest")
					.newInstance();
			chest.setRunnable(() -> JOptionPane.showMessageDialog(null, new JLabel("Le beau coffre"), "Attention",
					JOptionPane.PLAIN_MESSAGE));
			return chest;
		}
		return MineUtils.getResourceByName("stone").newInstance();
	}

	@Override
	public Cell createTree() {
		return MineUtils.getResourceByName("tree").newInstance();
	}

	@Override
	public Cell createLeaves() {
		return MineUtils.getResourceByName("leaves").newInstance();
	}

	@Override
	public Cell createCrystalLeaves() {
		return MineUtils.getResourceByName("crystal_leaves").newInstance();
	}

	@Override
	public Cell createCrystalTree() {
		return MineUtils.getResourceByName("crystal_tree").newInstance();
	}

}
