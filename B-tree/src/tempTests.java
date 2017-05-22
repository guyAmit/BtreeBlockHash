import java.util.ArrayList;

public class tempTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree t = new BTree(2);
		ArrayList<Block> blocks = Block.blockFactory(2, 4);
		t.insert(blocks.get(1));
		t.insert(blocks.get(0));
		t.insert(blocks.get(2));
		t.print(t.getRoot());
		t.delete(4);
		System.out.println();
		t.print(t.getRoot());
		BTree tree = new BTree(2); 
		 BTreeLatex manualTesto=new BTreeLatex(tree,"manualTest");
		tree.insert(new Block(2,null));
		tree.insert(new Block(1,null));
		tree.insert(new Block(4,null));
		tree.insert(new Block(3,null));
		tree.delete(1);
		tree.delete(2);
		tree.delete(4);
		System.out.println();
		tree.print(t.getRoot());
		 //System.out.println(pair.node.getsSuccessor(13));
		 
	}

}