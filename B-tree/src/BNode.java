import java.util.ArrayList;
import java.util.Arrays;

//SUBMIT
public class BNode implements BNodeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private final int t;
	private int numOfBlocks;
	private boolean isLeaf;
	private ArrayList<Block> blocksList;
	private ArrayList<BNode> childrenList;

	/**
	 * Constructor for creating a node with a single child.<br>
	 * Useful for creating a new root.
	 */
	public BNode(int t, BNode firstChild) {
		this(t, false, 0);
		this.childrenList.add(firstChild);
	}

	/**
	 * Constructor for creating a <b>leaf</b> node with a single block.
	 */
	public BNode(int t, Block firstBlock) {
		this(t, true, 1);
		this.blocksList.add(firstBlock);
	}

	public BNode(int t, boolean isLeaf, int numOfBlocks) {
		this.t = t;
		this.isLeaf = isLeaf;
		this.numOfBlocks = numOfBlocks;
		this.blocksList = new ArrayList<Block>();
		this.childrenList = new ArrayList<BNode>();
	}

	// For testing purposes.
	public BNode(int t, int numOfBlocks, boolean isLeaf,
			ArrayList<Block> blocksList, ArrayList<BNode> childrenList) {
		this.t = t;
		this.numOfBlocks = numOfBlocks;
		this.isLeaf = isLeaf;
		this.blocksList = blocksList;
		this.childrenList = childrenList;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int getNumOfBlocks() {
		return numOfBlocks;
	}

	@Override
	public boolean isLeaf() {
		return isLeaf;
	}

	@Override
	public ArrayList<Block> getBlocksList() {
		return blocksList;
	}

	@Override
	public ArrayList<BNode> getChildrenList() {
		return childrenList;
	}

	@Override
	public boolean isFull() {
		return numOfBlocks == 2 * t - 1;
	}

	@Override
	public boolean isMinSize() {
		return numOfBlocks == t - 1;
	}
	
	@Override
	public boolean isEmpty() {
		return numOfBlocks == 0;
	}
	
	@Override
	public int getBlockKeyAt(int indx) {
		return blocksList.get(indx).getKey();
	}
	
	@Override
	public Block getBlockAt(int indx) {
		return blocksList.get(indx);
	}

	@Override
	public BNode getChildAt(int indx) {
		return childrenList.get(indx);
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((blocksList == null) ? 0 : blocksList.hashCode());
		result = prime * result
				+ ((childrenList == null) ? 0 : childrenList.hashCode());
		result = prime * result + (isLeaf ? 1231 : 1237);
		result = prime * result + numOfBlocks;
		result = prime * result + t;
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BNode other = (BNode) obj;
		if (blocksList == null) {
			if (other.blocksList != null)
				return false;
		} else if (!blocksList.equals(other.blocksList))
			return false;
		if (childrenList == null) {
			if (other.childrenList != null)
				return false;
		} else if (!childrenList.equals(other.childrenList))
			return false;
		if (isLeaf != other.isLeaf)
			return false;
		if (numOfBlocks != other.numOfBlocks)
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "BNode [t=" + t + ", numOfBlocks=" + numOfBlocks + ", isLeaf="
				+ isLeaf + ", blocksList=" + blocksList + ", childrenList="
				+ childrenList + "]";
	}

	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	
	
	/*******************************************************************
	 *  			  ### new constructor ###
	 *******************************************************************/
	public BNode parent;

	public BNode(int t, boolean isLeaf, int numOfBlocks,BNode parent) {
		this.t = t;
		this.isLeaf = isLeaf;
		this.numOfBlocks = numOfBlocks;
		this.blocksList = new ArrayList<Block>();
		this.childrenList = new ArrayList<BNode>();
		this.parent=parent;
	}
	
	/*******************************************************************
	 * 							END
	 *  			  ### new constructors ###
	 *******************************************************************/
	
	@Override
	public Block search(int key) {
		// TODO Auto-generated method stub
		int i=0;
		while(i < this.numOfBlocks && this.getBlockAt(i).getKey()<key){
			i++;
		}
		if(i<this.numOfBlocks && this.getBlockAt(i).getKey()==key)
			return this.getBlockAt(i);
		else{
			if(this.isLeaf)
				return null;
			else
				return this.getChildAt(i).search(key);
		}
	}
	/*******************************************************************
	 *  			  ### insert ###
	 *******************************************************************/
	public void splitChild(int i){
		BNode y= this.childrenList.get(i);
		BNode z= new BNode(t,y.isLeaf,t-1,this);
		for (int j = 0; j < t-1; j++) {
			z.blocksList.add(y.getBlockAt(j+t));
		}
		y.blocksList.removeAll(z.blocksList);
		if(!y.isLeaf){
			for (int j = 0; j < t; j++) {
				y.getChildAt(j+t).parent=z;
				z.addChild(y.getChildAt(j+t));
			}
			y.childrenList.removeAll(z.childrenList);
		}
		this.childrenList.add(i+1, z);
		
		if(this.blocksList.size()==0)
			this.blocksList.add(y.blocksList.get(t-1));
		else{
			this.blocksList.add(i, y.blocksList.get(t-1));
		}
		y.blocksList.remove(t-1);
		
		this.numOfBlocks++;
		
		y.numOfBlocks=t-1;
		y.parent=this;
		z.parent=this;
	}
	
	
	@Override
	public void insertNonFull(Block d) {
		int i = this.numOfBlocks-1;
		if(this.isLeaf){
			this.blocksList.add(null);
			while(i>=0 && d.getKey() < this.getBlockKeyAt(i)){
				this.blocksList.set(i+1, this.getBlockAt(i));
				i--;
			}
			this.blocksList.set(i+1, d);
			this.numOfBlocks++;
		}
		else{
			while(i>=0 && d.getKey() < this.getBlockKeyAt(i)){
				i--;
			}
			i++;
			if(this.childrenList.get(i).isFull()){
				this.splitChild(i);
				if(d.getKey()>this.getBlockKeyAt(i))
					i++;
			}
			this.childrenList.get(i).insertNonFull(d);	
		}
	}

	/*******************************************************************
	 * 						END
	 *  			  ### insert ###
	 *******************************************************************/
	
	public void increseNumberOfBlocks(){
		this.numOfBlocks++;
	}
	
	public void addChild(BNode node){
		this.childrenList.add(node);
	}

	/*******************************************************************
	 *  			  ### delete method ###
	 *     #### and assisting methods for delete ###
	 *******************************************************************/
	
	@Override
	public void delete(int key) {
		// TODO Auto-generated method stub
		
		//getting to the node and modifying the tree for removal
		KeyPair x = this.getNodeAndIndex(key);
					
		//case 0 : key is not in the tree
		if (x==null) return;
		int index  = x.node.indexOfKey(key);
		
		//if x became the new root
		if(x.node.parent!=null && x.node.parent.blocksList.isEmpty()){
			this.blocksList=x.node.blocksList;
			this.numOfBlocks=x.node.numOfBlocks;
			this.isLeaf=x.node.isLeaf;
			for (BNode child : x.node.childrenList) {
				child.parent=this;
			}
			this.childrenList=x.node.childrenList;
		}
		else if(this.blocksList.isEmpty() && this.childrenList.size()==1){
			this.blocksList=this.childrenList.get(0).blocksList;
			this.numOfBlocks=this.childrenList.get(0).numOfBlocks;
			this.isLeaf=this.childrenList.get(0).isLeaf;
			for (BNode child : this.childrenList.get(0).childrenList) {
				child.parent=this;
			}
			this.childrenList=this.childrenList.get(0).childrenList;
		}
		
		//case 1: x is the root, but there is more then one block in it
		if(x.node.isLeaf && x.node.parent==null & x.node.blocksList.size()>1){
			x.node.blocksList.remove(index);
			x.node.numOfBlocks--;
			return;
		}
		
		//case 2: x is root, and there is only one block
		if(x.node.isLeaf & x.node.parent==null & x.node.blocksList.size()==1
				& x.node.childrenList.isEmpty()){
			this.blocksList=new ArrayList<Block>();
			this.numOfBlocks=0;
			return;
		}
		
		
	   // case 3 : leaf with more then t-1 blocks		
		if(x.node.isLeaf() && x.node.getNumOfBlocks()>this.t-1){
			x.node.getBlocksList().remove(index);
			x.node.numOfBlocks--;
			if(this.blocksList==x.node.blocksList)
				this.numOfBlocks=x.node.numOfBlocks;
		}
		//cases 4-6: x is not a leaf
		if(!x.node.isLeaf()){
			KeyPair y=new KeyPair(index,x.node.getChildAt(index));
			KeyPair z= new KeyPair(index+1,x.node.getChildAt(index+1));
			
			//case 4: need the replace with predecessor
			if(y.node.getNumOfBlocks()>=t){
			   KeyPair pred= x.node.getPredecessor(key);
			   Block predBlock = pred.node.getBlockAt(pred.index); 
			   y.node.delete(pred.node.getBlockKeyAt(pred.index));
			   x.node.getBlocksList().set(index, predBlock);
			  }
			
			//case 5: need the replace with successor
			else if(y.node.getNumOfBlocks()==t-1 & z.node.getNumOfBlocks()>=t){
				   KeyPair succ= x.node.getSuccessor(key);
				   Block succBlock=succ.node.getBlockAt(succ.index);
				   z.node.delete(succ.node.getBlockKeyAt(succ.index));
				   x.node.getBlocksList().set(index, succBlock);
			  }
			
			//case 6: merge
			else if(y.node.getNumOfBlocks()==t-1 && z.node.getNumOfBlocks()==t-1){
			   x.node.merge(y, z);
			   
			   //if y became x
              if(x.node.parent==null && x.node.childrenList.size()==1 
						& x.node.blocksList.isEmpty()){
				x.node.blocksList=y.node.blocksList;
				x.node.isLeaf=y.node.isLeaf;
				x.node.childrenList.remove(0);
				for (BNode child : y.node.childrenList) {
					child.parent=x.node;
				}
				x.node.childrenList=y.node.childrenList;
				x.node.numOfBlocks=y.node.numOfBlocks;
				x.node.delete(key);
			  }            
              else
            	  y.node.delete(key);
				
			}
			
		}		
		
	}
	
	//gets the Predecessor of the block with the index @key
	public KeyPair getPredecessor(int key){
		int index=this.indexOfKey(key);
		if(this.isLeaf()){
			//not relevant
			return null;
		}
		else{
			BNode pointer=this.getChildAt(index);
			while( !pointer.childrenList.isEmpty() && pointer.getChildAt(pointer.numOfBlocks)!=null){ //while the biggest child is not null
				pointer=pointer.getChildAt(pointer.numOfBlocks); //go to the biggest child
			}
			return new KeyPair(pointer.numOfBlocks-1, pointer);
		}
	}
	
	//gets the Successor of the block with the index @key
	public KeyPair getSuccessor(int key){
		int index=this.indexOfKey(key);
		if(this.isLeaf()){
			//not relevant
			return null;
		}
		else{
			BNode pointer=this.getChildAt(index+1);
			while( !pointer.childrenList.isEmpty() && pointer.getChildAt(0)!=null){ //while the smallest child is not null
				pointer=pointer.getChildAt(0); //go to the biggest child				
			}
			return new KeyPair(0, pointer);
		}
	}
	
	//y and z are brothers. 
	//key pairs s.t. the index is their child number according to there parent
	//this method merge the nodes into the node @y
	public void merge(KeyPair y, KeyPair z){
		BNode p = y.node.parent;
		if(y.index<z.index){
			y.node.blocksList.add(p.getBlockAt(y.index));//add the middle block
			y.node.numOfBlocks++;
			for (Block block : z.node.blocksList) { //add all blocks of z into the end of y
				y.node.blocksList.add(block);
				y.node.numOfBlocks++;
			
			}
			for (BNode child : z.node.childrenList) {
				child.parent=y.node;
				y.node.childrenList.add(child);
			}
			p.blocksList.remove(p.getBlockAt(y.index));
		}
		else{
			y.node.blocksList.add(0,p.getBlockAt(y.index-1));//add the middle block
			y.node.numOfBlocks++;
			for (int i = z.node.blocksList.size()-1; i >= 0; i--) { //add all blocks into the start of y
				y.node.blocksList.add(0,z.node.getBlockAt(i));
				y.node.numOfBlocks++;
			}
			for (int i = z.node.childrenList.size()-1; i >= 0; i--) { 
				z.node.getChildAt(i).parent=y.node;
				y.node.childrenList.add(0,z.node.getChildAt(i));
			}
			p.blocksList.remove(p.getBlockAt(y.index-1));
		}
		
		p.childrenList.remove(z.index);
		p.numOfBlocks--;
	}
	
	//pair s.t. the index is the index of the node according to the parent
	public void shift(KeyPair pair){
		//checks if any modifications are needed
	  if(pair.node.getNumOfBlocks()==t-1){
		  //case 1: shift left
		  if(pair.index-1>=0 && pair.index-1<pair.node.parent.childrenList.size()){
		    BNode u = pair.node.parent.getChildAt(pair.index-1);
		  	if(u.numOfBlocks>=t){
			  	pair.node.blocksList.add(0, parent.getBlockAt(pair.index-1));
			  	pair.node.numOfBlocks++;
			  	parent.blocksList.set(pair.index-1,u.getBlockAt(u.getNumOfBlocks()-1));
			  	u.blocksList.remove(u.numOfBlocks-1);
			  	u.numOfBlocks--;
			  	if(!u.childrenList.isEmpty()){
			  		u.getChildAt(u.childrenList.size()-1).parent=pair.node;
			  		 pair.node.childrenList.add(0,u.getChildAt(u.childrenList.size()-1));
			  		 u.childrenList.remove(u.childrenList.size()-1);
			  	}
			  	return;
		  	}
		  }
		  //case 2: shift right
		  if(pair.index+1>=0 && pair.index+1<pair.node.parent.childrenList.size()){
			 BNode w=pair.node.parent.getChildAt(pair.index+1);
			  if(w.numOfBlocks>=t){
				  pair.node.blocksList.add(parent.getBlockAt(pair.index));
				  pair.node.numOfBlocks++;
				  parent.blocksList.set(pair.index,w.getBlockAt(0));
				  w.blocksList.remove(0);
				  w.numOfBlocks--;
				  if(w.childrenList.size()>0){
					  w.getChildAt(0).parent=pair.node;
					  pair.node.childrenList.add(w.getChildAt(0));
				  	  w.childrenList.remove(0);
				  }
				  return;
			  }
		  }
		  //case 3 and 4: merge, we will always merge into pair i.e. delete the other node
		if(pair.index-1>=0 && pair.index-1<pair.node.parent.childrenList.size()){
			BNode u = pair.node.parent.getChildAt(pair.index-1);
		    merge(pair,new KeyPair(pair.index-1, u));
		 }
	   else if(pair.index+1>=0 && pair.index+1<pair.node.parent.childrenList.size()){
		   BNode w=pair.node.parent.getChildAt(pair.index+1);
		   merge(pair,new KeyPair(pair.index+1, w));
		}
	  }
	
	}
	
	//returns the index in the node of key, and the node her self
	//in the process of getting to the node, this method modifies the tree s.t. it will be ready 
	//removal of the block with the key @key
	protected KeyPair getNodeAndIndex(int key) {
		// TODO Auto-generated method stub
		int i=0;
		while(i < this.numOfBlocks && this.getBlockAt(i).getKey()<key){
			i++;
		}
		if(i<this.numOfBlocks && this.getBlockAt(i).getKey()==key){
			if(this.parent!=null){
				int indexInParent=0;
				if(i>0)
					indexInParent=this.parent.closesetIndexOfKey(this.getBlockKeyAt(i-1));
				else
					indexInParent=this.parent.closesetIndexOfKey(this.getBlockKeyAt(0));
				shift(new KeyPair(indexInParent, this));
			}
			int j=this.closesetIndexOfKey(key);
			return new KeyPair(j, this);
		}
		else{
			if(this.isLeaf)
				return null;
			else{
				if(this.parent!=null){
					
					int indexInParent=0;
					if(i>0)
						indexInParent=this.parent.closesetIndexOfKey(this.getBlockKeyAt(i-1));
					else
						indexInParent=this.parent.closesetIndexOfKey(this.getBlockKeyAt(0));
					shift(new KeyPair(indexInParent, this));
				}
				int j=this.closesetIndexOfKey(key);
				return this.getChildAt(j).getNodeAndIndex(key);
			}
		}
	}
	
	
	/*******************************************************************
	 *                       END
	 *  			  ### delete method ###
	 *     #### and assisting methods for delete ###
	 *******************************************************************/
	

	/*******************************************************************
	 *  			  ### Hash node ###
	 *******************************************************************/
	@Override
	public MerkleBNode createHashNode() {
		// TODO Auto-generated method stub
		if(this.isLeaf){
			ArrayList<byte[]> data=new ArrayList<byte[]>();
			for (Block block : this.getBlocksList()) {
				data.add(block.getData());
			}
			MerkleBNode returnVal = new MerkleBNode(HashUtils.sha1Hash(data));
			return returnVal;
		}
		else{
			ArrayList<byte[]> data=new ArrayList<byte[]>();
			ArrayList<MerkleBNode> childs= new ArrayList<MerkleBNode>();
			for (int i = 0; i < this.getBlocksList().size(); i++) {
				MerkleBNode node= this.childrenList.get(i).createHashNode();
				childs.add(node);
				data.add(node.getHashValue());
				data.add(this.getBlocksList().get(i).getData());				
			}
			MerkleBNode node= this.childrenList.get(this.childrenList.size()-1).createHashNode();
			childs.add(node);
			data.add(node.getHashValue());
			return new MerkleBNode(HashUtils.sha1Hash(data),childs);
		}
	}
	
	/*******************************************************************              
	 * 			    #### and assisting methods###
	 *******************************************************************/
	
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	
	
	private int indexOfKey(int key){
		int i=0;
		while(i < this.numOfBlocks && this.getBlockAt(i).getKey()<key){
			i++;
		}
		if(i<this.numOfBlocks && this.getBlockAt(i).getKey()==key)
			return i;
		throw new IllegalArgumentException("key is not in that node");
		
	}
	
	private int closesetIndexOfKey(int key){
		int i=0;
		while(i < this.numOfBlocks && this.getBlockAt(i).getKey()<key){
			i++;
		}
		return i;
		
	}
	
	

}
