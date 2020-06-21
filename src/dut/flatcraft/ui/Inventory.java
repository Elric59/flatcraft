package dut.flatcraft.ui;

import java.awt.datatransfer.Transferable;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import dut.flatcraft.Cell;
import dut.flatcraft.MineUtils;
import dut.flatcraft.player.Handable;
import dut.flatcraft.resources.CombustibleContainer;
import dut.flatcraft.resources.OreContainer;
import dut.flatcraft.resources.Resource;
import dut.flatcraft.resources.ResourceContainer;
import dut.flatcraft.tools.Tool;
import dut.flatcraft.tools.ToolInstance;

public class Inventory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, ResourceContainer> containers = new HashMap<>();

	private JPanel ui = new JPanel();

	private int current = 0;

	private List<Handable> handables = new ArrayList<>();

	private TransferHandler handler;
	private MouseListener mouselistener = new MyMouseAdapterForInventory();

	public Inventory() {
		ui.setBorder(BorderFactory.createEmptyBorder());
		handler = createTransfertFrom();
		createOreContainer(MineUtils.getResourceByName("iron_lump"));
		createCombustibleContainer(MineUtils.getResourceByName("wood"));
		createCombustibleContainer(MineUtils.getResourceByName("leaves"));
		createCombustibleContainer(MineUtils.getResourceByName("coal_lump"));
		ToolInstance tool = MineUtils.createToolByName("woodaxe").newInstance();
		handables.add(tool);
		ui.add(new ToolInstanceUI(tool));
		tool = MineUtils.createToolByName("woodpick").newInstance();
		handables.add(tool);
		ui.add(new ToolInstanceUI(tool));
		current = handables.size() - 2;
	}

	public Handable getElementInTheHand() {
		return handables.get(current);
	}

	public void next() {
		current++;
		if (current == handables.size()) {
			current = 0;
		}
	}

	public void previous() {
		if (current == 0) {
			current = handables.size() - 1;
		} else {
			current--;
		}
	}

	public void add(Cell cell) {
		ResourceContainer container = containers.get(cell.getName());
		if (container == null) {
			// create container
			container = createResourceContainer((Resource) cell.getType());
		}
		container.inc();
	}

	public void add(ResourceContainer rcontainer) {
		ResourceContainer container = containers.get(rcontainer.getResource().getName());
		if (container == null) {
			// create container
			container = createResourceContainer(rcontainer.getResource());
		}
		container.inc(rcontainer.getQuantity());
	}

	public void add(ToolInstance tool) {
		handables.add(tool);
		ui.add(new ToolInstanceUI(tool));
	}

	public void add(Handable handable) {
		if (handable instanceof ResourceContainer) {
			add((ResourceContainer) handable);
		} else {
			add((ToolInstance) handable);
		}
		ui.revalidate();
		ui.repaint();
	}

	public JComponent getUI() {
		return ui;
	}

	private TransferHandler createTransfertFrom() {
		return new TransferHandler() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public int getSourceActions(JComponent c) {
				return COPY;
			}

			@Override
			protected Transferable createTransferable(JComponent c) {
				ResourceContainer rc = ((ResourceContainerUI) c).getResourceContainer();
				return rc.clone();
			}

			@Override
			protected void exportDone(JComponent source, Transferable data, int action) {
				ResourceContainer container = ((ResourceContainerUI) source).getResourceContainer();
				if (action == MOVE) {
					container.consumeAll();
				} else if (action == COPY) {
					if (container.getQuantity() == 1) {
						container.consume(1);
					} else {
						container.consume(container.getQuantity() / 2);
					}
				}
			}

			// partie to
			@Override
			public boolean canImport(TransferSupport support) {
				return support.isDataFlavorSupported(ResourceContainer.RESOURCE_FLAVOR)
						|| support.isDataFlavorSupported(Tool.TOOL_FLAVOR);
			}

			@Override
			public boolean importData(TransferSupport support) {
				if (support.isDrop()) {
					JComponent source = (JComponent) support.getComponent();
					try {
						Handable transferedHandable = (Handable) support.getTransferable()
								.getTransferData(ResourceContainer.RESOURCE_FLAVOR);
						if (transferedHandable instanceof ToolInstance) {
							handables.add(transferedHandable);
							ui.add(new ToolInstanceUI((ToolInstance) transferedHandable));
							ui.revalidate();
							ui.repaint();
						} else {
							ResourceContainer sourceContainer = (ResourceContainer) transferedHandable;
							addResource(sourceContainer);
							source.revalidate();
							source.repaint();
						}
						return true;
					} catch (Exception e) {
						Logger.getAnonymousLogger().info(e.getMessage());
						return false;
					}
				} else {
					return false;
				}
			}
		};
	}

	private ResourceContainer createResourceContainer(Resource resource) {
		ResourceContainerUI cui = new ResourceContainerUI(resource);
		register(resource, cui);
		return cui.getResourceContainer();
	}

	private void createOreContainer(Resource resource) {
		ResourceContainerUI cui = new ResourceContainerUI(new OreContainer(resource, 0));
		register(resource, cui);
	}

	private void createCombustibleContainer(Resource resource) {
		ResourceContainerUI cui = new ResourceContainerUI(new CombustibleContainer(resource, 0));
		register(resource, cui);
	}

	private void register(Resource resource, ResourceContainerUI cui) {
		ui.add(cui);
		ui.revalidate();
		cui.setTransferHandler(handler);
		cui.addMouseListener(mouselistener);
		ResourceContainer container = cui.getResourceContainer();
		containers.put(resource.getName(), container);
		handables.add(container);
	}

	private void addResource(ResourceContainer container) {
		ResourceContainer mcontainer = containers.get(container.getResource().getName());
		if (mcontainer == null) {
			// create container
			mcontainer = createResourceContainer(container.getResource());
		}
		mcontainer.inc(container.getQuantity());
		container.consumeAll();
	}
}
