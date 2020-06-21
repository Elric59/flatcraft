package dut.flatcraft.resources;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dut.flatcraft.MineElement;
import dut.flatcraft.tools.ToolType;
import fr.univartois.migl.utils.DesignPattern;

/**
 * A resource is something on the map that the player can interact with. It may
 * dig it or leave it on the map.
 * 
 * @author leberre
 *
 */
@DesignPattern(name = "Type Object")
public class Resource implements MineElement {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final ImageIcon blockAppearance;
	private int hardness;
	private final ToolType toolType;
	private final String name;

	public Resource(String name, ImageIcon appearance, int hardness, ToolType toolType) {
		this.name = name;
		this.blockAppearance = appearance;
		if (hardness < 0) {
			throw new IllegalArgumentException("hardness should be non negative");
		}
		this.hardness = hardness;
		this.toolType = toolType;
	}

	/**
	 * Return the local name of the resource.
	 * 
	 * @return a unique identifier for that kind of resource
	 */
	public String getName() {
		return name;
	}

	@Override
	public ImageIcon getImage() {
		return blockAppearance;
	}

	/**
	 * Return the amount of effort needed to dig the resource.
	 * 
	 * @return a non negative number
	 */
	public int getHardness() {
		return hardness;
	}

	/**
	 * Change the hardness of the resource.
	 * 
	 * @param hardness a non negative number
	 */
	public void setHardness(int hardness) {
		if (hardness < 0) {
			throw new IllegalArgumentException("hardness should be non negative");
		}
		this.hardness = hardness;
	}

	/**
	 * Return the kind of resource to put in the inventory once it has been
	 * collected.
	 * 
	 * By default, a resource is unchanged when collected, i.e.
	 * this.equals(this.digBlock()).
	 * 
	 * @return
	 */
	public Resource digBlock() {
		return this;
	}

	/**
	 * Return the type of tool required to collect that resource on the map.
	 * 
	 * @return one instance of ToolType
	 */
	public ToolType getToolType() {
		return toolType;
	}

	/**
	 * Create an instance of that resource, i.e. an object which can be displayed on
	 * the map.
	 * 
	 * @return an instance of ResourceInstance representing that type
	 */
	@Override
	public ResourceInstance newInstance() {
		return new ResourceInstance(this);
	}

	/**
	 * Create an instance of that resource, i.e. an object which can be displayed on
	 * the map, given a JLabel as IU.
	 * 
	 * @param label a JLabel to reuse to display that resource
	 * @return an instance of ResourceInstance representing that type
	 */
	@Override
	public ResourceInstance newInstance(JLabel label) {
		return new ResourceInstance(this, label);
	}
}
