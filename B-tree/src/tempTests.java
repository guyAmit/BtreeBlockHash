import java.util.ArrayList;

public class tempTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree t = new BTree(3);
		ArrayList<Block> blocks = Block.blockFactory(11, 31);
		for (Block block : blocks) {
			t.insert(block);
		}
		
		 KeyPair pair= t.getNodeAndIndex(13);
		 System.out.println(pair.node.getPredecessor(13));
		 //System.out.println(pair.node.getsSuccessor(13));
		 
	}

}