package dut.flatcraft.player;

import java.io.Serializable;
import java.util.Optional;

import javax.swing.ImageIcon;

import dut.flatcraft.Cell;

public interface Handable extends Serializable{

	ImageIcon getImage();
	
	Optional<Cell> action(Player p, Cell c);

	Coordinate toDig(Direction direction);
	
	boolean mustBeChanged();
}
