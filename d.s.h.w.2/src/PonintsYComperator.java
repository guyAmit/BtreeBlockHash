import java.util.Comparator;

public class PonintsYComperator implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		// TODO Auto-generated method stub
		if(o1 ==null | o2==null) //null check
			throw new IllegalArgumentException("this method is not defiend for null objects");
		if(o1.getY()==o2.getY())
			return 0;
		else if(o1.getY()>o2.getY())
			return 1;
		else
			return -1;
	}

}
