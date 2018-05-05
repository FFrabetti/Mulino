package mulino;

public class Position {

	private int x;
	private int y;
	
	public Position(int x, int y) {
//		int absX = Math.abs(x);
//		int absY = Math.abs(y);
//		if((x==0 && y==0) || absX>3 || absY>3 || (x*y!=0 && absX!=absY))
//			throw new IllegalArgumentException("Invalid position: " + x + "," + y);
			
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Position))
			return false;
		
		Position p = (Position)o;	
		return this.x==p.x && this.y==p.y;
	}
	
	@Override
	public int hashCode() {
		return x ^ y;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}
	
}
