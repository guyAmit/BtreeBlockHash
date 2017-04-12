import java.util.Arrays;

public class DataStructure implements DT {

	private TwinSortedList<Point> x; //list sorted according to the x value's of the points
	private TwinSortedList<Point> y; //list sorted according to the y value's of the points
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

	/*
	 * ************ addPoint ****************
	 * this method add a point to the data structure.
	 * this method do so using the fact that the add method in TwinSortedList returns
	 * the node which the data was inserted into. in this method the data is inserted to both lists
	 *  and than connects the corresponding returning nodes. 
	 */
	
	@Override
	public void addPoint(Point point) {
		// TODO Auto-generated method stub
		ThreeSidedNode xLink = this.x.add(point);
		ThreeSidedNode yLink = this.y.add(point);
		yLink.setTwin(xLink);
		xLink.setTwin(yLink);
	}
	
	/*
	 * ************** getPointsInRangeRegAxis ***************
	 * this method return an array with the values between min and max.
	 * this method do so by using the closestFromStart/closestFromEnd methods from the
	 * TwinSortedList class that allow access to the nodes closed to min and max.
	 * first the method determine the size of the array and then fill it with the 
	 * correct values.
	 */

	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		ThreeSidedNode<Point> start;
		ThreeSidedNode<Point> end;
		if(axis){
			Point minX = new Point(min,0);
			Point maxX = new Point(max,0);
			start= this.x.closestFromStart(minX);
			end= this.x.closestFromEnd(maxX);
		}
		else{
			Point minY = new Point(0,min);
			Point maxY = new Point(0,max);
			start= this.y.closestFromStart(minY);
			end= this.y.closestFromEnd(maxY);
		}
		int counter=0;
		ThreeSidedNode<Point> pointer= start;
		while(this.compX.compare((Point)pointer.getData(),(Point)end.getData())!=0 & 
				this.compY.compare((Point)pointer.getData(), (Point)end.getData())!=0){
			counter++;
			pointer=pointer.getNext();
		}
		pointer=start;
		Point[] returnVal = new Point[counter];
		for (int i = 0; i < returnVal.length; i++) {
			returnVal[i]=(Point)pointer.getData();
			pointer=pointer.getNext();
		}
		return returnVal;
		
	}

	/*
	 * ************* getPointsInRangeOppAxis *****************
	 * using the method above, this method gets the values sorted according to axis.
	 * then it uses a quick sort or a merge sort to sort them according to the opposite axis.
	 * note that this method is O(n), because the method above is O(n) and sorting those not
	 * cost O(n*log(n)) because we are sorting a smaller array. thus exist n0 and c s.t.
	 * O(n) + O(|B|log(|b|)) < c*n where |B| is the size of the array. thus this method is O(n).
	 */
	

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		Point[] returnVal = this.getPointsInRangeRegAxis(min, max, axis);
		if(axis)
			Arrays.sort(returnVal, compY);
		else
			Arrays.sort(returnVal, compX);
		return returnVal;
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
		double Xmax =((Point)maxX.getData()).getX();
	    double Ymax = ((Point)maxY.getData()).getY();
		double Xmin = ((Point)minX.getData()).getX();
		double Ymin = ((Point)minY.getData()).getY();
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
	
	/*
	 *  *****************narrowRange********************
	 *  if axis is true begging the deletion in the x list
	 *  otherwise begin in the y list.
	 *  see explanation about the delete method in the TwinSortedList
	 */
	
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


	
	/*
	 * *****************getMedian********************
	 * going  threw the correct list and finding the 
	 * median according to definition
	 */

	@Override
	public Container getMedian(Boolean axis) {
		// TODO Auto-generated method stub
		int midIndex = this.x.getSize()/2;
		ThreeSidedNode<Point> mid;
		if(axis)
			mid = this.x.first;
		else
			mid = this.y.first;
		
		while(midIndex>0){
			mid = mid.getNext();
			midIndex--;
		}
		Container ans= new Container();
		ans.setData((Point)mid.getData());
		return ans;
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
	
	/*
	 * ************ toString *************
	 * give a description of the data in the data structure
	 */

	public int size() {
		// TODO Auto-generated method stub
		return this.x.getSize();
	}
	
	@Override
	public String toString() {
		String s ="X: "+this.x.toString()+"\n"+"Y: "+this.y.toString();
		return s;
	}



	
	//TODO: add members, methods, etc.
	
}

