package dut.flatcraft.resources;

import javax.swing.ImageIcon;

import dut.flatcraft.tools.ToolType;

/**
 * A transformable resource is a resource found on the map which is transformed
 * once taken from the map (think about coal for instance).
 * 
 * In this case, we do not have this.equals(this.digBlock()).
 * 
 * @author leberre
 *
 */
public class TransformableResource extends Resource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final Resource digBlock;

	public TransformableResource(String name, ImageIcon blockAppearance, Resource digBlock, int hardness,
			ToolType toolType) {
		super(name, blockAppearance, hardness, toolType);
		this.digBlock = digBlock;
	}

	@Override
	public Resource digBlock() {
		return this.digBlock;
	}
}
