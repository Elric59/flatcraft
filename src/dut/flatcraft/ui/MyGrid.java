package dut.flatcraft.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Optional;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

import dut.flatcraft.Cell;
import dut.flatcraft.CellFactory;
import dut.flatcraft.GameMap;
import dut.flatcraft.MineUtils;
import dut.flatcraft.map.MapGenerator;
import dut.flatcraft.player.Coordinate;
import dut.flatcraft.player.Player;

public class MyGrid extends JComponent implements KeyListener {

	private static final int CELL_SIZE = MineUtils.DEFAULT_IMAGE_SIZE;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameMap map;
	private Player player;

	public MyGrid(int height, int width, CellFactory factory, MapGenerator generator) {

		map = generator.generate(width, height, factory);
		Player.createPlayer(map);
		player = Player.instance();
		setLayout(new GridLayout(height, width));
		for (int i = 0; i < map.getHeight(); i++) {
			for (int j = 0; j < map.getWidth(); j++) {
				add(map.getAt(i, j).getUI());
			}
		}
		checkPhysics();
		addKeyListener(this);
		setFocusable(true);
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(map.getWidth() * CELL_SIZE, map.getHeight() * CELL_SIZE);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		player.paint(g);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// we are only interested in keyPressed()
	}

	@Override
	public void keyPressed(KeyEvent e) {
		boolean needsUpdate = false;
		boolean needsToCheckVisible = false;
		Coordinate old = player.getPosition();
		switch (e.getKeyCode()) {
		case KeyEvent.VK_KP_UP:
		case KeyEvent.VK_UP:
			player.up();
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_DOWN:
		case KeyEvent.VK_DOWN:
			player.down();
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_LEFT:
		case KeyEvent.VK_LEFT:
			if (e.isShiftDown()) {
				player.previousInHand();
			} else {
				player.left();
			}
			needsUpdate = true;
			break;
		case KeyEvent.VK_KP_RIGHT:
		case KeyEvent.VK_RIGHT:
			if (e.isShiftDown()) {
				player.nextInHand();
			} else {
				player.right();
			}
			needsUpdate = true;
			break;
		case KeyEvent.VK_SPACE:
			needsUpdate = player.next();
			needsToCheckVisible = true;
			break;
		case KeyEvent.VK_CONTROL:
			digOrFill();
			needsUpdate = true;
			needsToCheckVisible = true;
			break;
		case KeyEvent.VK_E:
			if (execute()) {
				needsUpdate = true;
				needsToCheckVisible = true;
			}
			break;
		case KeyEvent.VK_F1:
			displayHelp();
			break;
		default:
			// do nothing
		}
		if (needsUpdate) {
			e.consume();

			checkPhysics();

			Coordinate current = player.getPosition();

			map.getAt(old.getY(), old.getX()).getUI().repaint();
			map.getAt(current.getY(), current.getX()).getUI().repaint();

			repaint();

			if (needsToCheckVisible) {
				scrollMap(current);
			}

		}
	}

	private void scrollMap(Coordinate current) {
		Rectangle visible = getVisibleRect();
		if (current.getX() * CELL_SIZE > visible.x + visible.width) {
			Logger.getAnonymousLogger().fine(() -> current.getX() * CELL_SIZE + "/" + (visible.x + visible.width));
			scrollRectToVisible(new Rectangle(visible.x + CELL_SIZE, visible.y, visible.width, visible.height));
		}
		if (current.getX() * CELL_SIZE < visible.x) {
			Logger.getAnonymousLogger().fine(() -> current.getX() * CELL_SIZE + "/" + (visible.x + visible.width));
			scrollRectToVisible(new Rectangle(visible.x - CELL_SIZE, visible.y, visible.width, visible.height));
		}
	}

	private void displayHelp() {
		String help = "<html><h1>Comment jouer ?</h1>" + "C'est simple : <ul>"
				+ "<li>on se dirige à l'aide des touches directionnelles.</li>"
				+ "<li>on utilise un outil ou on dépose une ressource à l'aide de la touche <pre>CTRL</pre></li>"
				+ "<li>on change d'objet en main à l'aide de <pre>CTRL</pre>+flèche droite ou gauche</li>"
				+ "<li>on déplace les ressources de l'inventaire à la table de craft par glissé/déplacé</li>"
				+ "<li>on déplace les ressources sur table de craft par glissé/déplacé avec ou sans <pre>CTRL</pre></li>"
				+ "<li>on exécute une action sur une cellule qui se trouve devant soi à l'aide de la touche <pre>e</pre></li>"
				+ "</ul>";
		JOptionPane.showMessageDialog(null, help);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// we are only interested in keyPressed()
	}

	private boolean digOrFill() {
		Coordinate toDig = player.toDig();
		Cell cellToDig = map.getAt(toDig.getY(), toDig.getX());
		Optional<Cell> result = player.getHand().action(player, cellToDig);
		if (result.isPresent()) {
			map.setAt(toDig.getY(), toDig.getX(), result.get());
			if (player.getHand().mustBeChanged()) {
				player.nextInHand();
			}
			return true;
		}
		return false;
	}

	private boolean execute() {
		Coordinate toExecute = player.toDig();
		Cell cellToExecute = map.getAt(toExecute.getY(), toExecute.getX());
		return cellToExecute.execute();
	}

	private void checkPhysics() {
		Coordinate down;
		do {
			down = player.lookingDown.toDig();
		} while (map.getAt(down.getY(), down.getX()).manage(player));

	}
}
