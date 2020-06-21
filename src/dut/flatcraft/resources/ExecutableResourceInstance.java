package dut.flatcraft.resources;

import javax.swing.JLabel;

/**
 * Make an executable resource.
 * 
 * @author leberre
 *
 */
public class ExecutableResourceInstance extends ResourceInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Runnable runnable;

	public ExecutableResourceInstance(Resource type) {
		super(type);
	}

	public ExecutableResourceInstance(Resource type, JLabel label) {
		super(type, label);
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}

	@Override
	public boolean execute() {
		if (runnable == null) {
			return false;
		}
		runnable.run();
		return true;
	}

}
