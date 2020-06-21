package dut.flatcraft.player;

public class Up extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Up(Coordinate c) {
		super(c);
	}

	@Override
	public boolean next() {
		return c.decY();
	}

	@Override
	public int dx(int dx) {
		return dx + 16;
	}

	@Override
	public int dw(int dw) {
		return 8;
	}

	@Override
	public Coordinate toDig() {
		if (c.getY() > 0) {
			return new Coordinate(c.getX(), c.getY() - 1, c.width, c.height);
		}
		return new Coordinate(c);
	}
}
