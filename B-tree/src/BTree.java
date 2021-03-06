import java.util.ArrayList;
import java.util.Arrays;

// SUBMIT
public class BTree implements BTreeInterface {

	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	// ///////////////////BEGIN DO NOT CHANGE ///////////////////
	private BNode root;
	private final int t;

	/**
	 * Construct an empty tree.
	 */
	public BTree(int t) { //
		this.t = t;
		this.root = null;
	}

	// For testing purposes.
	public BTree(int t, BNode root) {
		this.t = t;
		this.root = root;
	}

	@Override
	public BNode getRoot() {
		return root;
	}

	@Override
	public int getT() {
		return t;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((root == null) ? 0 : root.hashCode());
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
		BTree other = (BTree) obj;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (t != other.t)
			return false;
		return true;
	}
	
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////
	// ///////////////////DO NOT CHANGE END///////////////////


	@Override
	public Block search(int key) {
		// TODO Auto-generated method stub
		if(this.getRoot().isLeaf()){
			for (int i = 0; i < this.getRoot().getNumOfBlocks(); i++) {
				if(key==this.getRoot().getBlockAt(i).getKey())
					return this.getRoot().getBlockAt(i);
			}
			return null;
		}
		return this.root.search(key);
	}

	
	@Override
	public void insert(Block b) {
		// TODO Auto-generated method stub
		BNode r = this.root;
		if(r==null){
			this.root= new BNode(t, b);
		}
		else if(r.isFull()){
			BNode s= new BNode(t,false,0);
			this.root=s;
			this.root.setLeaf(false);
			s.addChild(r);
			s.parent=null;
			s.splitChild(0);
			s.insertNonFull(b);
		}
		else{
			r.insertNonFull(b);
		}
		
	}
	
	/*******************************************************************
	 *  			  ### delete method ###
	 *     #### and assisting methods for delete ###
	 *******************************************************************/

	@Override
	public void delete(int key) {
		// TODO Auto-generated method stub
		if(this.getRoot()!=null)
			this.getRoot().delete(key);
		if(this.getRoot().getBlocksList().isEmpty())
			this.root=null;
	}


	
	/*******************************************************************
	 *                       END
	 *  			  ### delete method ###
	 *     #### and assisting methods for delete ###
	 *******************************************************************/
	
	@Override
	public MerkleBNode createMBT() {
		// TODO Auto-generated method stub
		if(this.getRoot()!=null)
			return this.getRoot().createHashNode();
		return null;
	}
	
    private static ArrayList<String> getHashValues(MerkleBNode merkleBNode,ArrayList<String> arrayList) {
        if (merkleBNode.isLeaf()) {
                arrayList.add(Arrays.toString(merkleBNode.getHashValue()));
        } else {
            for (int i = 0; i < merkleBNode.getChildrenList().size(); i++) {
                getHashValues(merkleBNode.getChildrenList().get(i), arrayList);
            }
            arrayList.add(Arrays.toString(merkleBNode.getHashValue()));
        }
        return arrayList;
    }
	


	


}
