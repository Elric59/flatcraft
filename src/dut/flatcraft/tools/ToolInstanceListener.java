package dut.flatcraft.tools;

import java.io.Serializable;

public interface ToolInstanceListener extends Serializable {

	void update(ToolInstance source);
}
