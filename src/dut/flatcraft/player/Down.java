package dut.flatcraft.player;

public class Down extends AbstractDirection {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Down(Coordinate c) {
		super(c);
	}

	@Override
	public boolean next() {
		return c.incY();
	}

	@Override
	public Coordinate toDig() {
		if (c.getY() < c.height - 1) {
			return new Coordinate(c.getX(), c.getY() + 1, c.width, c.height);
		}
		return new Coordinate(c);
	}

	@Override
	public int dx(int dx) {
		return dx + 16;
	}

	@Override
	public int dy(int dy) {
		return dy + 20;
	}

	@Override
	public int dw(int dw) {
		return 8;
	}
}
