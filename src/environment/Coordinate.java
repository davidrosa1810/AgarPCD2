package environment;

public class Coordinate {
	public final int x;
	public final int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		Coordinate other = (Coordinate) obj;
		return other.x==x && other.y == y;
	}
	
	public double distanceTo(Coordinate other) {
		double dx = y - other.y;
		double dy = x - other.x;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public Coordinate translate(Coordinate vector) {
		return new Coordinate(x+vector.x, y+vector.y);
	}
	
	public boolean outOfBounds() {
	    if(x >= 30 || y >= 30 || x < 0 || y < 0) return true;
	    return false;
	}
}
