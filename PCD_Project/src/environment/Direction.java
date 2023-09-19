package environment;

public enum Direction {
	UP(0,-1),DOWN(0,1),LEFT(-1,0),RIGHT(1,0);
	private Coordinate vector;
	Direction(int x, int y) {
		vector=new Coordinate(x, y);
	}
	public Coordinate getVector() {
		return vector;
	}
	public static Direction stringToDirection(String s) {
        for(Direction d : values())
            if(d.name().equals(s)) return d;
        return null;
    }
}