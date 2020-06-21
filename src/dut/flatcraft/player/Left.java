package dut.flatcraft.player;

public class Left extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Left(Coordinate c) {
		super(c);
	}

	@Override
	public boolean next() {
		return c.decX();
	}

	@Override
	public Coordinate toDig() {
		if (c.getX() > 0) {
			return new Coordinate(c.getX() - 1, c.getY(), c.width, c.height);
		}
		return new Coordinate(c);
	}

	@Override
	public int dy(int dy) {
		return dy + 16;
	}

	@Override
	public int dh(int dh) {
		return 8;
	}
}
