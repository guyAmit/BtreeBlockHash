
//Don't change the class name
public class Container{
	private Point data;//Don't delete or change this field;
	private Container next;
	private Container back;
	private Container Twin;
	public boolean marker; // used in gerPointsInRangeOpp
    public int index; //used in getContinaersInRangeOpp
	
	public Container(Point data, Container next,Container back){
	    this.data = data;
	    this.next = next;
	    this.back = back;
	    this.marker=false;
	}
	
	public Container(Point data){
		this(data,null,null);
	} 
	
	public Container(Container other){
		this.data=other.data;
		this.next=other.next;
		this.back=other.back;
		this.Twin=other.Twin;
		this.marker=other.marker;
	}
		
	//Don't delete or change this function
	public Point getData()
	{
		return data;
	}
	
	public void setData(Point data){
		this.data=data;
	}
	
	public Container getNext(){
		return next;
	}
	
	public void setNext(Container next){
		this.next = next;
	}
	
    public Container getBack() {
		return back;
	}

	public void setBack(Container back) {
		this.back = back;
	}
	
	public Container getTwin() {
		return Twin;
	}

	public void setTwin(Container twin) {
		Twin = twin;
	}
	
	public String toString(){ 
		return data.toString();
	}
	
   	public boolean equals(Container other){
   		return data.equals(other.getData());
   	}
}
