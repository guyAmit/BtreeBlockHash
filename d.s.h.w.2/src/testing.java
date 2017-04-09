

public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DataStructure ds = new DataStructure();
		ds.addPoint(new Point(-3,3));
		ds.addPoint(new Point(-2,2));
		ds.addPoint(new Point(-1,1));
		ds.addPoint(new Point(-0,0));
		ds.addPoint(new Point(1,-1));
		ds.addPoint(new Point(2,-2));
		ds.addPoint(new Point(3,-3));
		System.out.println(ds.toString());
		
		ds.narrowRange(-1, 1, true);
		System.out.println();
		System.out.println(ds.toString());
	}

}
