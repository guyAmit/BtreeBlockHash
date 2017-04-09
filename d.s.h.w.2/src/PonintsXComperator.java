import java.util.Comparator;

public class PonintsXComperator implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		// TODO Auto-generated method stub
		return o1.getX()-o2.getX();
	}

}
