package dut.flatcraft.resources;

import fr.univartois.migl.utils.DesignPattern;

@DesignPattern(name = "Observer")
public interface ResourceContainerListener {

	void update(ResourceContainer source);
}
