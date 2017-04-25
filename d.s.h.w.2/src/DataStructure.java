import java.util.Arrays;

public class DataStructure implements DT {

	private TwinSortedList x; //list sorted according to the x value's of the points
	private TwinSortedList y; //list sorted according to the y value's of the points
    private PonintsYComperator compY; //Comparator that inflicts an partial order according to the Y coordinate of the points
    private PonintsXComperator compX; //Comparator that inflicts an partial order according to the X coordinate of the points
    
    
	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure()
	{
		//TODO
		this.compX = new PonintsXComperator();
		this.compY = new PonintsYComperator();
		this.x=new TwinSortedList(compX);
		this.y = new TwinSortedList(compY);
		this.x.SetTwin(y);
		this.y.SetTwin(x);
	}
	
	/*
	 * ************ copying constructor ****************
	 * this constructor receive two nodes from an previous dataStructure, and create a
	 * new Data Structure just from the nodes between the range given by "start" and "end".
	 * the copying constructor will be used in nearest pair.
	 * runtime: O(|b|) where |b| is the length of the desired list.
	 * note that if we find the median (O(n)), we can easily split the dataStructure into two smaller dataStructures
	 * with O(n) runtime.-the bonus question 6.5
	 */
	
	public DataStructure(Container start,Container end,boolean axis,int size,TwinSortedList otherAxis){
		this();		
		if(axis){
			this.x.first=start;
			this.x.last=end;
			this.x.setSize(size);
			this.y=otherAxis;
			Container[] ySorted= this.getContinaersInRangeOppAxis(start, end, axis);
			this.y=new TwinSortedList(compY);
			this.x=new TwinSortedList(compX);
			Container pointer=start;
			for (int i = 0; i < ySorted.length; i++) {
				Container yLink = y.addLast(ySorted[i]);// O(1)
				Container xLink = this.x.addLast(start); // O(1)
				xLink.setTwin(yLink);
				start=start.getNext();
			}
			this.y.SetTwin(this.x);
		}
		else{
			this.y.first=start;
			this.y.last=end;
			this.y.setSize(size);
			this.x=otherAxis;
			Container[] xSorted= this.getContinaersInRangeOppAxis(start, end, axis);
			this.x=new TwinSortedList(compX);
			this.x=new TwinSortedList(compX);
			Container pointer=start;
			for (int i = 0; i < xSorted.length; i++) {
				Container xLink = x.addLast(xSorted[i]);
				Container yLink = this.y.addLast(start);
				yLink.setTwin(xLink);
				start=start.getNext();
			}
			this.x.SetTwin(this.y);
		}
	}
	
	
	public boolean isEmpty(){
		return this.size()==0;
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
		if(point!=null){ //null check
			Container xLink = this.x.add(point);
			Container yLink = this.y.add(point);
			yLink.setTwin(xLink);
			xLink.setTwin(yLink);
		}
		else
			throw new NullPointerException("can not insert null point");
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
		if(max<min) //arguments check
			throw new IllegalArgumentException("max must be bigger then min");
		if(this.isEmpty()) //empty check
			throw new IllegalAccessError("this method requiers that the data structure will not be empty");
		Container start;
		Container end;
		Container pointer;
		int counter=0;
		if(axis){
			Point minX = new Point(min,0);
			Point maxX = new Point(max,0);
			start= this.x.closestFromStart(minX);
			end= this.x.closestFromEnd(maxX);
			pointer= start;
			while(pointer!=null && (this.compX.compare(pointer.getData(),end.getData())<=0)){
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
			while(pointer!=null &&(this.compY.compare(pointer.getData(),end.getData())<=0)){
				counter++;
				pointer=pointer.getNext();
			}
			pointer=start;
		}
		Point[] returnVal = new Point[counter];
		for (int i = 0; i < returnVal.length; i++) {
			returnVal[i]=pointer.getData();
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
		if(max<min) //arguments check
			throw new IllegalArgumentException("max must be bigger then min");
		if(this.isEmpty()) //empty check
			throw new IllegalAccessError("this method requiers that the data structure will not be empty");
		Container start;
		Container end;
		Container pointer;
		int counter=0;
		if(axis){
			Point minX = new Point(min,0);
			Point maxX = new Point(max,0);
			start= this.x.closestFromStart(minX);
			end= this.x.closestFromEnd(maxX);
			pointer= start;
			while(pointer!=null && (this.compX.compare(pointer.getData(),end.getData())<=0)){
				counter++;
				pointer.getTwin().marker=true;
				pointer=pointer.getNext();
			}
			Point[] returnVal = new Point[counter];
			Container oppPointer=this.y.first;
			int index=0;
			while(oppPointer!=null){
				if(oppPointer.marker){
					returnVal[index]=oppPointer.getData();
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
			while(pointer!=null &&(this.compY.compare(pointer.getData(),end.getData())<=0)){
				counter++;
				pointer.getTwin().marker=true;
				pointer=pointer.getNext();
			}
			Point[] returnVal = new Point[counter];
			Container oppPointer=this.x.first;
			int index=0;
			while(oppPointer!=null){
				if(oppPointer.marker){
					returnVal[index]=oppPointer.getData();
					index++;
				}
				oppPointer=oppPointer.getNext();
			}
			this.resetMarkers(true);
			return returnVal;
		}
		
	}
	
	public Container[] getContinaersInRangeOppAxis(Container min,Container max,boolean axis){
//		if(max<min) //arguments check
//			throw new IllegalArgumentException("max must be bigger then min");
//		if(this.isEmpty()) //empty check
//			throw new IllegalAccessError("this method requiers that the data structure will not be empty");
		Container start=min;
		Container end=max;
		Container pointer;
		int counter=0;
		if(axis){
			pointer= start;
			while(pointer!=null && (this.compX.compare(pointer.getData(),end.getData())<=0)){
				counter++;
				pointer.getTwin().marker=true;
				pointer=pointer.getNext();
			}
			Container[] returnVal = new Container[counter];
			Container oppPointer=this.y.first;
			int index=0;
			while(oppPointer!=null){
				if(oppPointer.marker){
					returnVal[index]=oppPointer;
					index++;
				}
				oppPointer=oppPointer.getNext();
			}
			this.resetMarkers(false);
			return returnVal;
		}
		else{
			pointer=start;
			while(pointer!=null &&(this.compY.compare(pointer.getData(),end.getData())<=0)){
				counter++;
				pointer.getTwin().marker=true;
				pointer=pointer.getNext();
			}
			Container[] returnVal = new Container[counter];
			Container oppPointer=this.x.first;
			int index=0;
			while(oppPointer!=null){
				if(oppPointer.marker){
					returnVal[index]=oppPointer;
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
			Container xPointer=this.x.first;
			while(xPointer!=null){
				if(xPointer.marker)
					xPointer.marker=false;
				xPointer=xPointer.getNext();
			}
		}
		else{
			Container yPointer=this.y.first;
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
		if(this.size()<2) // Density is not defined for dataStruycture with size lesser then two
			throw new IllegalAccessError("this method requiers that the data structure will contain at least two points");
		Container maxX = this.x.last;
		Container maxY=this.y.last;
		Container minX  =this.x.first;
		Container minY = this.y.first;
		double Xmax =(maxX.getData()).getX();
	    double Ymax = (maxY.getData()).getY();
		double Xmin = (minX.getData()).getX();
		double Ymin = (minY.getData()).getY();
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
		if(this.isEmpty()) //empty check
			throw new IllegalAccessError("this method requiers that the data structure will not be empty");
		Container maxX = this.x.last;
		Container maxY=this.y.last;
		Container minX  =this.x.first;
		Container minY = this.y.first;
		return (maxX.getData()).getX()-(minX.getData()).getX()>=
		(maxY.getData()).getY()-(maxY.getData()).getY();
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
		if(this.isEmpty()) //empty check
			throw new IllegalAccessError("this method requiers that the data structure will not be empty");
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
	 */

	@Override
	public Container getMedian(Boolean axis) {
		// TODO Auto-generated method stub
		if(this.isEmpty()) // empty check
			throw new IllegalAccessError("this method requiers that the data structure will not be empty");
		int midIndex = this.x.getSize()/2;
		Container mid;
		if(axis)
			mid = this.x.first;
		else
			mid = this.y.first;
		for (int i = 0; i < midIndex; i++) {
			mid=mid.getNext();
		}
		return mid;
	}
	
	
	
	/*
	 * *****************nearestPairInStrip********************
	 * this method first copy all the points into an array,
	 * then sort it according to the  opposite axis.
	 *  then find the closest pair using what is seems like an
	 *  O(n^2) algorithm, but actually it it has been proven that
	 *  the inner loop runs at most 6 times, which means it is an
	 *  O(n) algorithm 
	 */

	@Override
	public Point[] nearestPairInStrip(Container container, Double width, Boolean axis) {
		// TODO Auto-generated method stub
		if(axis){
			int size=0;
			Container start = container;	
			Point leftVal =new Point((int)(container.getData().getX()-(width)/2), container.getData().getY());
			while(start.getBack()!=null && compX.compare(start.getData(), leftVal)>=0){
				start=start.getBack();
				size++;
			}
			Container end = container;	
			Point rightVal =new Point((int)(container.getData().getX()+(width)/2), container.getData().getY());
			while(end.getNext()!=null && compX.compare(start.getData(), rightVal)<=0){
				end=end.getNext();
				size++;
			}
			size++;
			Point[] array;
			if(this.size()<=size()*Math.log(size)){
				array=this.getPointsInRangeOppAxis(start.getData().getX(), end.getData().getX(), axis);
			}
			else{
			   array= this.createArrayFromPointers(start, end);
				Arrays.sort(array, compY);
			}
			if(array.length<2) return null;
			double min = width/2;
			Point[] result = new Point[2];
			for (int i = 0; i < array.length; ++i) {
				for (int j = i+1; j < array.length && (array[j].getY()-array[i].getY())<width/2; j++) {
					if(this.distance(array[i], array[j])<min){
						min=distance(array[i], array[j]);
						result[0]=array[i];
						result[1]=array[j];
					}
				}
			}
			return result;
		}
		else{
			int size=0;
			Container start = container;	
			Point leftVal =new Point( container.getData().getX(),(int)(container.getData().getY()-(width)/2));
			while(start!=null && compX.compare(start.getData(), leftVal)>=0){
				start=start.getBack();
				size++;
			}
			Container end = container;	
			Point rightVal =new Point(container.getData().getX(),(int)(container.getData().getY()+(width)/2));
			while(compX.compare(start.getData(), leftVal)<=0){
			     end=end.getNext();
			     size++;
			}
			size++;
			Point[] array;
			if(this.size()<=size()*Math.log(size)){
				array=this.getPointsInRangeOppAxis(start.getData().getX(), end.getData().getX(), axis);
			}
			else{
			   array= this.createArrayFromPointers(start, end);
				Arrays.sort(array, compY);
			}
			if(array.length<2) return null;
			double min = width/2;
			Point[] result = new Point[2];
			for (int i = 0; i < array.length; ++i) {
				for (int j = i+1; j < array.length && (array[j].getX()-array[i].getX())<width/2; j++) {
					if(this.distance(array[i], array[j])<min){
						min=distance(array[i], array[j]);
						result[0]=array[i];
						result[1]=array[j];
					}
				}
			}
			return result;
		}
		
	}
	
	/*
	 * *****************nearestPair********************
	 * this method calculate the nearest pair using the following algorithm
	 * 1) if there are just two points -> return them
	 * 2) if there are less then two points -> return null i.e. there are no pairs
	 * 3) determine who is the bigger axis
	 * 4) create two DataStructures, one for each half of the points
	 * 5) applying nearestPair() two each of the new the DataStuctures recursively
	 * 6) if there is a solution to just one of the half -> return it.
	 * 7) else calculate the nearest pairs in all the area's that the algorithm missed during the division
	 * 8) if from line 7 there is a solution -> return it
	 * 9) else return the nearest pair from the 4 points from earlier calculation
	 */

	@Override
	public Point[] nearestPair() {
		// TODO Auto-generated method stub
		if(this.size()==2)
			return this.makeArrayOfSize(2);
		else if(this.size()<2)
			return null;
		else{
			Point[] result;
			boolean axis=this.getLargestAxis();
			if(axis){
				Object[] midAndIndex = this.getMedianAndMinIndex(true);
				Container mid = (Container)midAndIndex[0];
				int midPoss = (Integer)midAndIndex[1];
				DataStructure smallerHalf = new DataStructure(this.x.first,mid,axis,midPoss,this.y);
				DataStructure biggerHalf = new DataStructure(mid.getNext(),this.x.last,axis,this.size()-midPoss,this.y);
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
					if(midStrip[0]==null & midStrip[1]==null)
						return result;
					else
						return midStrip;
					
				}
				return result;
			}
			else{
				Object[] midAndIndex = this.getMedianAndMinIndex(true);
				Container mid = (Container)midAndIndex[0];
				int midPoss = (Integer)midAndIndex[1];
				DataStructure smallerHalf = new DataStructure(this.y.first,mid,axis,midPoss,this.x);
				DataStructure biggerHalf = new DataStructure(mid.getNext(),this.y.last,axis,this.size()-midPoss,this.x);
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
				return result;
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
		double dMin = Double.POSITIVE_INFINITY;         //the brute force algorithm
		for (int i = 0; i < marge.length-1; i++) {
			for (int j = i+1; j < marge.length; j++) {
				double dis=this.distance(marge[i],marge[j]);
				if(dis<dMin){
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
	 * ************ assisting methods*************
	 * **those methods will be used only inside other methods, after null and empty checks/
	 * --distance between points
	 * --creating an array from pointers
	 * --creating an array of  the "size" first points in the data structure according to X
	 */
	
	
	private double distance(Point p1, Point p2){
	        return Math.sqrt( Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(),2));
	}

	private Point[] createArrayFromPointers(Container start,Container end){
		int counter=0;
		Container pointer=start;
		while((pointer.getData()).getX()!=(end.getData()).getX() &
				(pointer.getData()).getY()!=(end.getData()).getY()){
			counter++;
			pointer=pointer.getNext();
		}
		counter++;
		pointer=start;
		Point[] returnVal = new Point[counter];
		for (int i = 0; i < returnVal.length; i++) {
			returnVal[i]=pointer.getData();
			pointer=pointer.getNext();
		}
		return returnVal;
	}

	private Point[] makeArrayOfSize(int size){
		Point[] newArray = new Point[size];
		Container pointer= this.x.first;
		int i=0;
		while(pointer!=null){
			newArray[i]= pointer.getData();
			i++;
			pointer=pointer.getNext();
		}
		return newArray;
	}
	
	public Object[] getMedianAndMinIndex(Boolean axis) {
		if(this.isEmpty()) // empty check
			throw new IllegalAccessError("this method requiers that the data structure will not be empty");
		Object[] returnVal = new Object[2];
		int midPoss=1;
		int midIndex = this.x.getSize()/2;
		Container mid;
		if(axis)
			mid = this.x.first;
		else
			mid = this.y.first;
		for (int i = 0; i < midIndex; i++) {
			mid=mid.getNext();
			midPoss++;
		}
		returnVal[0]=mid;
		returnVal[1]=midPoss;
		return returnVal;
	}
	
	
}

