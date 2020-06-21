package dut.flatcraft.resources;

import dut.flatcraft.tools.ToolType;

import javax.swing.*;

public class ExecutableAndTransformableResource extends Resource{

    private final Resource digBlock;

    public ExecutableAndTransformableResource(String name, ImageIcon appearance, Resource digBlock, int hardness, ToolType toolType) {
        super(name, appearance, hardness, toolType);
        this.digBlock = digBlock;
    }
    @Override
    public ResourceInstance newInstance() {
        return new ExecutableAndTransformableResourceInstance(this);
    }

    @Override
    public ResourceInstance newInstance(JLabel label) {
        return new ExecutableAndTransformableResourceInstance(this, label);
    }
    @Override
    public Resource digBlock() {
        return this.digBlock;
    }
}
