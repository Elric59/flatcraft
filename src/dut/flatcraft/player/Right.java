package dut.flatcraft.player;

public class Right extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Right(Coordinate c) {
		super(c);
	}

	@Override
	public int dx(int dx) {
		return dx + 20;
	}

	@Override
	public int dy(int dy) {
		return dy + 16;
	}

	@Override
	public int dh(int dh) {
		return 8;
	}

	@Override
	public boolean next() {
		return c.incX();
	}

	@Override
	public Coordinate toDig() {
		if (c.getX() < c.width - 1) {
			return new Coordinate(c.getX() + 1, c.getY(), c.width, c.height);
		}
		return new Coordinate(c);
	}
}
