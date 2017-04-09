
public class DataStructure implements DT {

	private TwinSortedList<Point> x;
	private TwinSortedList<Point> y;
    private PonintsYComperator compY;
    private PonintsXComperator compX;
    
    
	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure()
	{
		//TODO
		this.compX = new PonintsXComperator();
		this.compY = new PonintsYComperator();
		this.x=new TwinSortedList<Point>(compX);
		this.y = new TwinSortedList<Point>(compY);
		this.x.SetTwin(y);
		this.y.SetTwin(x);
	}

	@Override
	public void addPoint(Point point) {
		// TODO Auto-generated method stub
		ThreeSidedNode xLink = this.x.add(point);
		ThreeSidedNode yLink = this.y.add(point);
		yLink.setTwin(xLink);
		xLink.setTwin(yLink);
	}

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/*
	 *  *************getDensity method**********************
	 *  Do to the fact that both of the lists are sorted, and they
	 *  are doubly linked, we can access with O(1) the Max and Min
	 *  values of the data structure. thus calculating the density with
	 *  O(1)
	 */

	@Override
	public double getDensity() {
		// TODO Auto-generated method stub
		ThreeSidedNode maxX = this.x.last;
		ThreeSidedNode maxY=this.y.last;
		ThreeSidedNode minX  =this.x.first;
		ThreeSidedNode minY = this.y.first;
		int Xmax =((Point)maxX.getData()).getX();
		int Ymax = ((Point)maxY.getData()).getY();
		int Xmin = ((Point)maxX.getData()).getX();
		int Ymin = ((Point)maxY.getData()).getY();
		return (this.x.getSize()/((Xmax-Xmin)*(Ymax-Ymin)));
	}
	
	
	/*
	 *  *************getLargestAxis method**********************
	 *  Do to the fact that both of the lists are sorted, and they
	 *  are doubly linked, we can access with O(1) the Max and Min
	 *  values of the data structure. thus calculating the largestAxix with
	 *  O(1)
	 */

	@Override
	public Boolean getLargestAxis() {
		// TODO Auto-generated method stub
		ThreeSidedNode maxX = this.x.last;
		ThreeSidedNode maxY=this.y.last;
		ThreeSidedNode minX  =this.x.first;
		ThreeSidedNode minY = this.y.first;
		return ((Point)maxX.getData()).getX()-((Point)minX.getData()).getX()>=
		((Point)maxY.getData()).getY()-((Point)maxY.getData()).getY();
	}
	
	
	@Override
	public void narrowRange(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		if(axis==true){
			this.x.delete(new Point(max,0), new Point(min,0));
		}
		else{
			this.y.delete(new Point(0,max), new Point(0,min));
		}
	}



	@Override
	public Container getMedian(Boolean axis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point[] nearestPairInStrip(Container container, int width,
			Boolean axis) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point[] nearestPair() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		String s ="X: "+this.x.toString()+"\n"+"Y: "+this.y.toString();
		return s;
	}

	
	//TODO: add members, methods, etc.
	
}

