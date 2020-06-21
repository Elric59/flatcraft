package dut.flatcraft.player;

import java.awt.Color;
import java.awt.Graphics;

public abstract class AbstractDirection implements Direction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected final Coordinate c;

	public AbstractDirection(Coordinate c) {
		this.c = c;
	}

	@Override
	public void paint(Graphics g) {
		int dx = c.getX() * 40;
		int dy = c.getY() * 40;
		int dw = 20;
		int dh = 20;
		dx = dx(dx);
		dy = dy(dy);
		dw = dw(dw);
		dh = dh(dh);
		g.setColor(Color.PINK);
		g.fill3DRect(dx, dy, dw, dh, true);
	}

	protected int dx(int dx) {
		return dx;
	}

	protected int dy(int dy) {
		return dy;
	}

	protected int dw(int dw) {
		return dw;
	}

	protected int dh(int dh) {
		return dh;
	}

	@Override
	public Coordinate nextForResource() {
		return new Coordinate(c);
	}
}
