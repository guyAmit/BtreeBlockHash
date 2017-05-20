import java.util.ArrayList;

public class tempTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree t = new BTree(3);
		ArrayList<Block> blocks = Block.blockFactory(11, 31);
		System.out.println(blocks);
		blocks.remove(blocks.size()-1);
		System.out.println(blocks);
		
		
		 //System.out.println(pair.node.getsSuccessor(13));
		 
	}

}