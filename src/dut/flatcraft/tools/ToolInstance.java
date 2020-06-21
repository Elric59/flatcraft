package dut.flatcraft.tools;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dut.flatcraft.Cell;
import dut.flatcraft.EmptyCell;
import dut.flatcraft.MineElementInstance;
import dut.flatcraft.MineUtils;
import dut.flatcraft.player.Coordinate;
import dut.flatcraft.player.Direction;
import dut.flatcraft.player.Handable;
import dut.flatcraft.player.Player;
import dut.flatcraft.resources.Resource;

public class ToolInstance implements Transferable, MineElementInstance, Handable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Tool toolType;

    private int currentLife;

    private List<ToolInstanceListener> listeners = new ArrayList<>();

    public ToolInstance(Tool toolType) {
        this.toolType = toolType;
        this.currentLife = toolType.getInitialLife();

    }

    public ImageIcon getImage() {
        return toolType.getImage();
    }

    public int getCurrentLife() {
        return currentLife;
    }

    @Override
    public Tool getType() {
        return toolType;
    }

    public int getImpactWithBlock() {
        currentLife--;
        notifyListeners();
        return toolType.getDecrement();
    }

    public void addListener(ToolInstanceListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ToolInstanceListener listener : listeners) {
            listener.update(this);
        }
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { Tool.TOOL_FLAVOR };
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return Tool.TOOL_FLAVOR.equals(flavor);
    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return this;
    }

	@Override
	public Optional<Cell> action(Player p, Cell c) {
		if (((Resource)c.getType()).getToolType().compareTo(this.toolType.getTooltype())<=0) {
			boolean result = c.dig(p);			
			if (result) {
				return Optional.of(new EmptyCell(MineUtils.getImage("ice"), (JLabel)c.getUI()));
			}
		}
		return Optional.empty();
	}

	@Override
	public Coordinate toDig(Direction direction) {
		return direction.toDig();
	}

	@Override
	public boolean mustBeChanged() {
		return currentLife<=0;
	}
}
