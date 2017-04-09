public class ThreeSidedNode {
	
	private Object data;
	private ThreeSidedNode next;
	private ThreeSidedNode back;
	private ThreeSidedNode Twin;


	

	public ThreeSidedNode(Object data, ThreeSidedNode next,ThreeSidedNode back){
	    this.data = data;
	    this.next = next;
	    this.back = back;
	}
     
	public ThreeSidedNode(Object data){
	    this(data, null,null);
	}
	
	public Object getData(){
		return data;
	}
	
	public void setData(Object data){
		this.data = data; 
	}
	
	public ThreeSidedNode getNext(){
		return next;
	}
	
	public void setNext(ThreeSidedNode next){
		this.next = next;
	}
	
    public ThreeSidedNode getBack() {
		return back;
	}

	public void setBack(ThreeSidedNode back) {
		this.back = back;
	}
	
	public ThreeSidedNode getTwin() {
		return Twin;
	}

	public void setTwin(ThreeSidedNode twin) {
		Twin = twin;
	}
	
	public String toString(){ 
		return data.toString();
	}
	
   	public boolean equals(Object other){
   		return data.equals(((ThreeSidedNode)other).getData());
   	}

}
