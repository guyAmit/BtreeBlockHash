import java.util.ArrayList;

public class tempTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BTree tree = new BTree(2); 
		tree.insert(new Block(3,null));
		tree.insert(new Block(1,null));
		tree.insert(new Block(2,null));
		tree.insert(new Block(4,null));
		tree.delete(1);
		tree.delete(4);
		tree.delete(3);



	
	}

}