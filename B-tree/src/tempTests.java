import java.util.ArrayList;

public class tempTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BTree tree = new BTree(2); 
		 BTreeLatex manualTesto=new BTreeLatex(tree,"manualTest");
		tree.insert(new Block(4,null));
		tree.insert(new Block(2,null));
		tree.insert(new Block(1,null));
		tree.insert(new Block(3,null));
		tree.search(2);
		tree.search(3);
		tree.search(1);
		tree.search(4);





	
	}

}