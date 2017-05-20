
public class KeyPair {

	public int index;
	public BNode node;
	
	public KeyPair(int index,BNode node){
		this.node=node;
		this.index=index;
	}
	
	@Override 
	public String toString(){
		return "["+node.toString()+","+index+"]";
	}
}
