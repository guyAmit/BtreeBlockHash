import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by nadav on 5/19/2017.
 */
public class Assignment4Tests {

    public static void main(String[] args) {
        System.out.println("-------------Start of Tests-------------");
     //   testInsert();
    //    testSearch();
        testDelete();
        System.out.println("-------------End of Tests-------------");
    }

    private static void testInsert() {
        BTree firstTree = new BTree(3);
        initTree(firstTree, 40);
        ArrayList<Integer> arrayListFirst = inorder(new ArrayList<>(), firstTree.getRoot());
        ArrayList<Integer> expectedFirst = getIntegers(41);
        Test(true, arrayListFirst.containsAll(expectedFirst), "Insert - Test 1 of 7", "some keys were not inserted ");
        Test(expectedFirst, arrayListFirst, "Insert - Test 2 of 7", "keys are in the wrong place in the tree");
        Test(true, checkDegree(firstTree, firstTree.getRoot()), "Insert - Test 3 of 7", "found nodes with less than t-1 blocks or with more than 2t-1 blocks");
        Test(true, checkNumOfChildren(firstTree, firstTree.getRoot()), "Insert - Test 4 of 7", "found nodes with less or more than numOfBlocks+1 children");

        //same test as the previous but with more nodes.
//        BTree SecondTree = new BTree(15);
//        initTree(SecondTree, 1000000);
//        ArrayList<Integer> arrayListSecond = inorder(new ArrayList<>(), SecondTree.getRoot());
//        ArrayList<Integer> expectedSecond = getIntegers(1000001);
//        Test(expectedSecond, arrayListSecond, "Insert - Test 5 of 7", "keys are in the wrong place in the tree");
//        Test(true, checkDegree(SecondTree, SecondTree.getRoot()), "Insert - Test 6 of 7", "found nodes with less than t-1 blocks or with more than 2t-1 blocks");
//        Test(true, checkNumOfChildren(SecondTree, SecondTree.getRoot()), "Insert - Test 7 of 7", "found nodes with less or more than numOfBlocks+1 children");

    }

    private static void testSearch() {
        BTree Tree = new BTree(3);
        initTree(Tree, 40);
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<Integer> expected = new ArrayList<>();
        for (int i = 1; i < 41; i++) {
            expected.add(i);
            arrayList.add(Tree.search(i).getKey());
        }

        Test(expected, arrayList, "Search - Test 1 of 2", "didn't find some keys");
        Test(null, Tree.search(1000), "Search - Test 2 of 2", "the key is not in the tree. null should be returned");
    }

    private static void testDelete() {
        BTree tree = new BTree(5);
        initTree(tree, 100);
        tree.delete(74);
        tree.delete(89);
        tree.delete(32);
        tree.delete(103);//should not change anything in the tree because the key is not in the tree.
        ArrayList<Integer> arrayList = inorder(new ArrayList<>(), tree.getRoot());
        ArrayList<Integer> expected = getIntegers(101);
        Test(null, tree.search(74), "Delete - Test 1 of 6", "the key 74 is still in the tree");
        Test(null, tree.search(32), "Delete - Test 2 of 6", "the key 32 is still in the tree");
        Test(null, tree.search(89), "Delete - Test 3 of 6", "the key 89 is still in the tree");
        expected.remove(73);
        expected.remove(expected.indexOf(89));
        expected.remove(31);
        Test(expected, arrayList, "Delete - Test 4 of 6", "keys are in the wrong place in the tree");
        tree.delete(41);
        tree.delete(74);//should not change anything in the tree because the key is not in the tree.
        tree.delete(11);
        tree.delete(3);
        tree.delete(89);
        tree.delete(22);
        tree.delete(98);
        tree.delete(84);
        tree.delete(12);
        tree.delete(4);
        tree.delete(64);
        tree.delete(42);
        tree.delete(7);
        tree.delete(38);
        tree.delete(60);
        tree.delete(96);
        tree.delete(17);
        tree.delete(58);
        tree.delete(44);
        tree.delete(75);
        tree.delete(90);
        tree.delete(47);
        tree.delete(26);
        tree.delete(24);
        Test(true, checkDegree(tree, tree.getRoot()), "Delete - Test 5 of 6", "found nodes with less than t-1 blocks or with more than 2t-1 blocks");
        Test(true, checkNumOfChildren(tree, tree.getRoot()), "Delete - Test 6 of 6", "found nodes with less or more than numOfBlocks+1 children");

    }



    private static ArrayList<Integer> getIntegers(int size) {
        ArrayList<Integer> expected = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            expected.add(i);
        }
        return expected;
    }

    private static void initTree(BTree secondTree, int toKey) {
        ArrayList<Block> SecondBlocks = Block.blockFactory(1, toKey);
      //  Collections.shuffle(SecondBlocks);
        for (Block block : SecondBlocks) {
            secondTree.insert(block);
        }
    }

    private static <T> void Test(T expected, T actual, String CallingMethod) {
        if (expected == null) {
            if (actual != null) {
                System.err.println(CallingMethod + ": was <" + actual + "> but should be <" + null + ">");
                return;
            }
            System.out.println(CallingMethod + "-Passed");
            return;
        }
        if (!(actual.equals(expected))) {
            System.err.println(CallingMethod + ": was <" + actual + "> but should be <" + expected + ">");
        } else System.out.println(CallingMethod + "-Passed");
    }

    private static <T> void Test(T expected, T actual, String CallingMethod, String ErrorMessage) {
        if (expected == null) {
            if (actual != null) {
                System.err.println(CallingMethod + " failed- " + ErrorMessage);
                return;
            }
            System.out.println(CallingMethod + "-Passed");
            return;
        }
        if (!(actual.equals(expected))) {
            System.err.println(CallingMethod + " failed- " + ErrorMessage);
        } else System.out.println(CallingMethod + "-Passed");

    }

    public static ArrayList<Integer> inorder(ArrayList<Integer> arrayList, BNode node) {
        if (node.isLeaf()) {
            for (int i = 0; i < node.getNumOfBlocks(); i++) {
                arrayList.add(node.getBlockKeyAt(i));
            }
        } else {
            for (int i = 0; i < node.getNumOfBlocks(); i++) {
                inorder(arrayList, node.getChildAt(i));
                arrayList.add(node.getBlockKeyAt(i));
            }
            inorder(arrayList, node.getChildAt(node.getNumOfBlocks()));
        }
        return arrayList;
    }

    public static boolean checkDegree(BTree tree, BNode node) {
        if (node.isLeaf())
            return DegreeRange(node);
        boolean validDegree = true;
        for (int i = 0; i <= node.getBlocksList().size(); i++) {
            validDegree = checkDegree(tree, node.getChildAt(i)) && (node.equals(tree.getRoot()) || DegreeRange(node));
        }
        return validDegree;
    }

    public static boolean checkNumOfChildren(BTree tree, BNode node) {
        if (node.isLeaf())
            return true;
        boolean validNumOfChildren = true;
        for (int i = 0; i <= node.getBlocksList().size(); i++) {
            validNumOfChildren = checkNumOfChildren(tree, node.getChildAt(i)) && (node.equals(tree.getRoot()) || node.getChildrenList().size()==node.getBlocksList().size()+1);
        }
        return validNumOfChildren;
    }

    private static boolean DegreeRange(BNode node) {
        return (node.getBlocksList().size() >= node.getT() - 1) & (node.getBlocksList().size()) <= 2 *( (node.getT()) - 1);
    }

}

