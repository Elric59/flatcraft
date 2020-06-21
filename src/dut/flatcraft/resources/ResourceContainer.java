package dut.flatcraft.resources;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;

import dut.flatcraft.Cell;
import dut.flatcraft.player.Coordinate;
import dut.flatcraft.player.Direction;
import dut.flatcraft.player.Handable;
import dut.flatcraft.player.Player;
import fr.univartois.migl.utils.DesignPattern;

/**
 * A resource container allows to collect resources of a given type.
 * 
 * The ResourceInstance objects are not kept in the container: they are created
 * on demand.
 * 
 * The interaction with the container is performed using Java Drap and Drop.
 * 
 * @author leberre
 *
 */
public class ResourceContainer implements Transferable, Cloneable, Handable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final DataFlavor RESOURCE_FLAVOR = new DataFlavor(ResourceContainer.class, "resourcecontainer");

	private int quantity;
	private final Resource resource;
	private List<ResourceContainerListener> listeners = new ArrayList<>();

	public ResourceContainer(Resource block, int quantity) {
		this.resource = block;
		this.quantity = quantity;
	}

	public void addListener(ResourceContainerListener listener) {
		this.listeners.add(listener);
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity > 99 || quantity < 0) {
			throw new IllegalArgumentException("Wrong quantity");
		}
		this.quantity = quantity;
	}

	public ImageIcon getImage() {
		return resource.getImage();
	}

	public void consume() {
		if (quantity > 0) {
			quantity--;
			notifyListeners();
		}
	}

	public void consume(int amount) {
		if (amount > quantity) {
			throw new IllegalStateException("Cannot consume more than I have!");
		}
		quantity -= amount;
		notifyListeners();
	}

	public void consumeAll() {
		if (quantity > 0) {
			quantity = 0;
			notifyListeners();
		}
	}

	public void inc() {
		if (quantity < 99) {
			quantity++;
			notifyListeners();
		}
	}

	public void inc(int qty) {
		if (quantity < 99) {
			quantity += qty;
			if (quantity > 99) {
				quantity = 99;
			}
			notifyListeners();
		}
	}

	private void notifyListeners() {
		for (ResourceContainerListener listener : listeners) {
			listener.update(this);
		}
	}

	public ResourceInstance getBlock() {
		if (quantity > 0) {
			return resource.newInstance();
		}
		return null;
	}

	public Resource getResource() {
		return resource;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { RESOURCE_FLAVOR };
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return RESOURCE_FLAVOR.equals(flavor);
	}

	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		return this;
	}

	@Override
	@DesignPattern(name = "Prototype")
	public ResourceContainer clone() {
		return new ResourceContainer(resource, quantity);
	}

	@Override
	public Optional<Cell> action(Player p, Cell c) {
		if (quantity > 0) {
			ResourceInstance instance = resource.newInstance();
			boolean result = c.canBeReplacedBy(instance, p);
			if (result) {
				instance.setUI(c.getUI());
				Coordinate toDig = p.toDig();
				consume();
				if (toDig.equals(p.getPosition())) {
					p.opposite().next();
				}
				return Optional.of(instance);
			}
		}
		return Optional.empty();
	}

	@Override
	public Coordinate toDig(Direction direction) {
		return direction.nextForResource();
	}

	@Override
	public boolean mustBeChanged() {
		return quantity == 0;
	}
}
