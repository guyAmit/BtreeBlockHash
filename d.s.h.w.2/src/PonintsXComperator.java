import java.util.Comparator;

public class PonintsXComperator implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		// TODO Auto-generated method stub
		if(o1 ==null | o2==null) //null check
			throw new IllegalArgumentException("this method is not defiend for null objects");
		if(o1.getX()==o2.getX())
			return 0;
		else if(o1.getX()>o2.getX())
			return 1;
		else
			return -1;
		
	}

}
