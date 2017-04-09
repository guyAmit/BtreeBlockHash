

public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PonintsYComperator compY = new PonintsYComperator();
		PonintsXComperator compX = new PonintsXComperator();

		TwinSortedList<Point> X = new TwinSortedList<>(compX);
		X.add(new Point(1 ,4));
		X.add(new Point(2 ,3));
		X.add(new Point(3 ,2));
		X.add(new Point(4 ,1));
		System.out.println(X);
		
		TwinSortedList<Point> Y = new TwinSortedList<>(compY);
		Y.add(new Point(1 ,4));
		Y.add(new Point(2 ,3));
		Y.add(new Point(3 ,2));
		Y.add(new Point(4 ,1));
		System.out.println(Y);
		
		X.SetTwin(Y);
		Y.SetTwin(X);
		
		Point max = new Point(2,0);
		Point min =new Point(3,0);
		X.delete(max, min);
		System.out.println();
		System.out.println(X);
		System.out.println(Y);
	}

}
