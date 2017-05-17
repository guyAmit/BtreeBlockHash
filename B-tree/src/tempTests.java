import java.util.ArrayList;

public class tempTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree t = new BTree(3);
		ArrayList<Block> blocks = Block.blockFactory(11, 31);
		for (Block block : blocks) {
			t.insert(block);
		}
		
		 System.out.println(t.search(20));
	}

}