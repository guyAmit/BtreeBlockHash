import java.util.ArrayList;

public class temp {
	public BNode<T> search(BNode<T> startSearch,Comparable<T> keys){
		int i=0;
		while(i < startSearch.size && keys.compareTo(startSearch.getKey(i))>0){
			i++;
		}
		if(i <= startSearch.size && keys.compareTo(startSearch.getKey(i))==0)
			return startSearch;
		else{
			if(startSearch.isLeaf)
				return null;
			else{
				return this.search(startSearch.getChild(i), keys);
			}
		}
	}
	
	public void split(BNode<T> x){
		if(x.parent==null) { // i.e. x is the root
			BNode<T> root= new BNode<>(null, order);
			root.keys.add(x.getKey(order-1));
			BNode<T> right = new BNode<>(root, order);
			for(int i=order-1; i < order; i++){
				right.keys.add(x.getKey(i));
				x.keys.remove(i);
			}
			root.childs.add(x);
			root.childs.add(right);
		}
		else if(x.isLeaf){ // x is one of the leafs
			
		}
		else{ // x is somewhere in the tree
			
		}
	}
	
	
	public int size;
	public BNode<T> parent;
	public ArrayList<T> keys;
	public ArrayList<BNode<T>> childs;
	static int t;
	public boolean isLeaf;
	
	public BNode(BNode<T> parent , int t){
		t=t;
		this.isLeaf=true;
		this.parent=parent;
		this.keys=new ArrayList<T>(2*t-1);
		this.childs= new ArrayList<BNode<T>>(2*t);
		this.size=0;
	}
	
	public T getKey(int index){
		return this.keys.get(index); 
	}
	
	public BNode<T> getChild(int index){
		return this.childs.get(index);
	}
	
}
