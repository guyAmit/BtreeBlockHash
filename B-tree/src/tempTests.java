import java.util.ArrayList;

public class tempTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BTree tree = new BTree(3); 
		 BTreeLatex manualTesto=new BTreeLatex(tree,"manualTest");
		tree.insert(new Block(2,null));
		tree.insert(new Block(1,null));
		tree.delete(1);
	
	}

}