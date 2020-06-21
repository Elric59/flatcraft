package dut.flatcraft.resources;

import javax.swing.JLabel;

/**
 * Make an executable resource.
 *
 * @author leberre
 *
 */
public class ExecutableAndTransformableResourceInstance extends ResourceInstance {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Runnable runnable;


    public ExecutableAndTransformableResourceInstance(Resource type) {
        super(type);
    }

    public ExecutableAndTransformableResourceInstance(Resource type, JLabel label) {
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