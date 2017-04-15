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
	 * ************ copying constructor ****************
	 * this constructor receive two nodes from an previous dataStracture, and create a
	 * new Data Structure just from the nodes between the range given by "start" and "end".
	 * the copying constructor will be used in nearest pair.
	 */
	
	public DataStructure(ThreeSidedNode<Point> start,ThreeSidedNode<Point> end){
		this();		
		ThreeSidedNode<Point> pointer=start;
		while(((Point)pointer.getData()).getX()!=((Point)end.getData()).getX() &
				((Point)pointer.getData()).getY()!=((Point)end.getData()).getY()){
			this.addPoint((Point)pointer.getData());
		}
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
		ThreeSidedNode<Point> pointer;
		int counter=0;
		if(axis){
			Point minX = new Point(min,0);
			Point maxX = new Point(max,0);
			start= this.x.closestFromStart(minX);
			end= this.x.closestFromEnd(maxX);
			pointer= start;
			while(pointer!=null && (this.compX.compare((Point)pointer.getData(),(Point)end.getData())<=0)){
				counter++;
				pointer=pointer.getNext();
			}
			pointer=start;
		}
		else{
			Point minY = new Point(0,min);
			Point maxY = new Point(0,max);
			start= this.y.closestFromStart(minY);
			end= this.y.closestFromEnd(maxY);
			pointer=start;
			while(pointer!=null &&(this.compY.compare((Point)pointer.getData(),(Point)end.getData())<=0)){
				counter++;
				pointer=pointer.getNext();
			}
			pointer=start;
		}
		Point[] returnVal = new Point[counter];
		for (int i = 0; i < returnVal.length; i++) {
			returnVal[i]=(Point)pointer.getData();
			pointer=pointer.getNext();
		}
		return returnVal;
		
	}

	/*
	 * ************* getPointsInRangeOppAxis *****************
	 * this method gets all the points in the range, sorted according to the opposite of
	 * the given axis. it does so by marking all the points in the opposite list that are
	 * in the range, and than copy them according to there order in the opposite list to 
	 * an array.
	 */
	

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		// TODO Auto-generated method stub
		ThreeSidedNode<Point> start;
		ThreeSidedNode<Point> end;
		ThreeSidedNode<Point> pointer;
		int counter=0;
		if(axis){
			Point minX = new Point(min,0);
			Point maxX = new Point(max,0);
			start= this.x.closestFromStart(minX);
			end= this.x.closestFromEnd(maxX);
			pointer= start;
			while(pointer!=null && (this.compX.compare((Point)pointer.getData(),(Point)end.getData())<=0)){
				counter++;
				pointer.getTwin().marker=true;
				pointer=pointer.getNext();
			}
			Point[] returnVal = new Point[counter];
			ThreeSidedNode<Point> oppPointer=this.y.first;
			int index=0;
			while(oppPointer!=null){
				if(oppPointer.marker){
					returnVal[index]=(Point)oppPointer.getData();
					index++;
				}
				oppPointer=oppPointer.getNext();
			}
			this.resetMarkers(false);
			return returnVal;
		}
		else{
			Point minY = new Point(0,min);
			Point maxY = new Point(0,max);
			start= this.y.closestFromStart(minY);
			end= this.y.closestFromEnd(maxY);
			pointer=start;
			while(pointer!=null &&(this.compY.compare((Point)pointer.getData(),(Point)end.getData())<=0)){
				counter++;
				pointer.getTwin().marker=true;
				pointer=pointer.getNext();
			}
			Point[] returnVal = new Point[counter];
			ThreeSidedNode<Point> oppPointer=this.x.first;
			int index=0;
			while(oppPointer!=null){
				if(oppPointer.marker){
					returnVal[index]=(Point)oppPointer.getData();
					index++;
				}
				oppPointer=oppPointer.getNext();
			}
			this.resetMarkers(true);
			return returnVal;
		}
		
	}
	
	/*
	 *  *************resetMarkers**********************
	 *  this method reset all the "marker" fields in a given list
	 *  to false in order to use the "getPointsInRangeOppAxis"
	 *  again.
	 */

	private void resetMarkers(boolean axis){
		if(axis){
			ThreeSidedNode<Point> xPointer=this.x.first;
			while(xPointer!=null){
				if(xPointer.marker)
					xPointer.marker=false;
				xPointer=xPointer.getNext();
			}
		}
		else{
			ThreeSidedNode<Point> yPointer=this.y.first;
			while(yPointer!=null){
				if(yPointer.marker)
					yPointer.marker=false;
				yPointer=yPointer.getNext();
			}
		}
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
	 * median according to definition.
	 * ---this method is to be used only in checking the assignment---
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
	

	/*
	 * *****************Median********************
	 * going  threw the correct list and finding the 
	 * median according to definition.
	 * ---the method that should be used in actual programming---
	 */
	
	private ThreeSidedNode<Point> Median(Boolean axis) {
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
		return mid;
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
		if(this.size()==2)
			return this.makeArrayOfSize(2);
		else if(this.size()<2)
			return null;
		else{
			Point[] result;
			if(this.getLargestAxis()){
				ThreeSidedNode<Point> mid = this.Median(true);
				DataStructure smallerHalf = new DataStructure(this.x.first,mid);
				DataStructure biggerHalf = new DataStructure(mid.getNext(),this.x.last);
				Point[] smallerPair = smallerHalf.nearestPair();
				Point[] biggerPair = biggerHalf.nearestPair();
				if(smallerPair==null)
					result=biggerPair;
				else if(biggerPair==null)
					result=smallerPair;
				else{
					result=this.nearestPair(smallerPair, biggerPair);
					double minDis=this.distance(result[0], result[1]);
					Point[] midStrip=this.nearestPairInStrip(mid, 2*minDis, true);
					if(midStrip!=null)
						return midStrip;
					else
						return result;
					
				}
				
			}
			else{
				ThreeSidedNode<Point> mid = this.Median(false);
				DataStructure smallerHalf = new DataStructure(this.y.first,mid);
				DataStructure biggerHalf = new DataStructure(mid.getNext(),this.y.last);
				Point[] smallerPair = smallerHalf.nearestPair();
				Point[] biggerPair = biggerHalf.nearestPair();
				if(smallerPair==null)
					result=biggerPair;
				else if(biggerPair==null)
					result=smallerPair;
				else{
					result=this.nearestPair(smallerPair, biggerPair);
					double minDis=this.distance(result[0], result[1]);
					Point[] midStrip=this.nearestPairInStrip(mid, 2*minDis, true);
					if(midStrip!=null)
						return midStrip;
					else
						return result;
					
				}
			}
		}
	}
	
	/*
	 * *****************NearestPair-brute force for  two arrays of size 2********************
	 * Comparing all the pairs and returning the "nearest pair"
	 */
	private Point[] nearestPair(Point[] smaller,Point[] bigger){
		Point[] result =new Point[2];
		Point[] marge = new Point[4]; //Merging into one array
		marge[0]=smaller[0];
		marge[1]=smaller[1];
		marge[2]=bigger[0];
		marge[3] = bigger[1]; 
		double dMin =-1;         //the brute force algorithm
		for (int i = 0; i < marge.length-1; i++) {
			for (int j = i+1; j < marge.length; j++) {
				double dis=this.distance(marge[i],marge[j]);
				if(dis<dMin | dMin==-1){
					dMin=dis;
					result[0]=marge[i];
					result[1]=marge[j];
				}	
			}
		}
		return result;
	}

	/*
	 * *****************size********************
	 * returns the size of the DataStarcture
	 */

	public int size() {
		// TODO Auto-generated method stub
		return this.x.getSize();
	}
	
	/*
	 * ************ toString *************
	 * give a description of the data in the data structure
	 */
	
	@Override
	public String toString() {
		String s ="X: "+this.x.toString()+"\n"+"Y: "+this.y.toString();
		return s;
	}

	/*
	 * ************ assisting method *************
	 * --distance between points
	 * --creating an array from pointers
	 * --creating an array of  the "size" first points in the data structure according to X
	 */
	
	
	private double distance(Point p1, Point p2){
	        return Math.sqrt( Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(),2));
	}

	private Point[] createArrayFromPointers(ThreeSidedNode<Point> start,ThreeSidedNode<Point> end){
		int counter=0;
		ThreeSidedNode<Point> pointer=start;
		while(((Point)pointer.getData()).getX()!=((Point)end.getData()).getX() &
				((Point)pointer.getData()).getY()!=((Point)end.getData()).getY()){
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

	private Point[] makeArrayOfSize(int size){
		Point[] newArray = new Point[size];
		ThreeSidedNode<Point> pointer= this.x.first;
		int i=0;
		while(pointer!=null){
			newArray[i]= (Point)pointer.getData();
			i++;
		}
		return newArray;
	}
	
	
}

