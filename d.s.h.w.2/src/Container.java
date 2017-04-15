
//Don't change the class name
public class Container{
	private Point data;//Don't delete or change this field;
	private Container next;
	private Container back;
	private Container Twin;
	public boolean marker; // used in gerPointsInRangeOpp

	
	public Container(Point data, Container next,Container back){
	    this.data = data;
	    this.next = next;
	    this.back = back;
	    this.marker=false;
	}
	
	public Container(Point data){
		this(data,null,null);
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
