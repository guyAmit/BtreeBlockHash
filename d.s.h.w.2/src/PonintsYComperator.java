import java.util.Comparator;

public class PonintsYComperator implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		// TODO Auto-generated method stub
		return o1.getY()-o2.getY();
	}

}
