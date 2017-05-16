import java.util.ArrayList;

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
	
	
	
	@Override
	public Block search(int key) {
		// TODO Auto-generated method stub
		int i=0;
		while(i <= this.numOfBlocks && this.getBlockAt(i).getKey()<key){
			i++;
		}
		if(i<=this.numOfBlocks && this.getBlockAt(i).getKey()==key)
			return this.getBlockAt(i);
		else{
			if(this.isLeaf)
				return null;
			else
				return this.getChildAt(i).search(key);
		}
	}

	public void splitChild(int i){
		BNode y= this.childrenList.get(i);
		BNode z= new BNode(t,new Block(0,null));
		z.isLeaf=y.isLeaf;
		z.numOfBlocks=t-1;
		z.blocksList.remove(0);
		for (int j = 0; j < t-1; j++) {
			z.blocksList.add(y.getBlockAt(j+t));
		}
		if(!y.isLeaf){
			for (int j = 0; j < t; j++) {
				z.childrenList.add(y.childrenList.get(j+t));
			}
		}
		for (int j = this.numOfBlocks+1; j < i+1; j--) {
		   this.childrenList.set(j+1, this.getChildAt(j));
		}
		this.childrenList.set(i+1, z);
		for (int j = this.numOfBlocks; j < i; j--) {
			this.blocksList.set(j+1, this.getBlockAt(j));
		}
		this.blocksList.set(i, y.blocksList.get(t));
		this.numOfBlocks++;
		y.numOfBlocks=t-1;
	}
	
	
	@Override
	public void insertNonFull(Block d) {
		// TODO Auto-generated method stub
		int i = this.numOfBlocks;
		if(this.isLeaf){
			while(i>=1 && d.getKey()< this.getBlockKeyAt(i)){
				this.blocksList.set(i+1, this.getBlockAt(i));
				i--;
			}
		this.blocksList.set(i+1, d);
		this.numOfBlocks++;
		}
		else{
			while(i>=1 && d.getKey()< this.getBlockKeyAt(i)){
				this.blocksList.set(i+1, this.getBlockAt(i));
				i--;
			}
			i++;
			if(this.childrenList.get(i).numOfBlocks==2*t-1){
				this.childrenList.get(i).splitChild(i);
				if(d.getKey()>=this.getBlockKeyAt(i))
					i++;
			}
			this.insertNonFull(d);	
		}
	}

	@Override
	public void delete(int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MerkleBNode createHashNode() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

}
