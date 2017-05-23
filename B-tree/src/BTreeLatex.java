
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author evyatar
 */
public class BTreeLatex {

    private static final File desktop = new File(System.getProperty("user.home"), "Desktop");
    private static final File dir = new File(desktop, "\\BTreeLatex");
    private StringBuilder statesHolder;
    private StringBuilder buffer;
    private StringBuilder longTermBuffer;
    private File readableFile;
    private BTree tree;
    private Integer[] insertions;
    private Integer[] deletions;

    public static void main(String args[]) {
        if (solvedLastBug()) {
            autoTest(20, 100, 20);
        } else {
            StartLastFailedTest();
        }

        //manualTest();
    }

    public static boolean manualTest() {

        boolean result = true;

        /*
        
        
        
        
        *****************Insert here the starting code before the bug****************************
        
        
        
        manualTesto.addTreeState("before bug");
        try{
        ************************************************Insert here the problematic line of code*************************
        }catch(Exception e){
            e.printStackTrace();
        }
        result=manualTesto.addTreeState("after bug");
        manualTesto.commitBufferedStates();
        manualTesto.finish();
        
         */
        return result;
    }

    public static void autoTest(int maxT, int maxNodes, int maxTests) {
        BTree testTree = null;
        BTreeLatex autoTesto = null;
        boolean flag = true;
        for (int t = 2; t <= maxT & flag; t++) {
            System.out.println("Testing trees with t = " + t);
            for (int i = 1; i <= maxNodes & flag; i++) {
                testTree = new BTree(t);
                autoTesto = new BTreeLatex(testTree, "BTreeInLatex");
                flag = autoTesto.initSimpleTree(testTree, i) && autoTesto.testDelete(testTree, i);
            }
        }
        if (flag) {
            new File(dir.getAbsolutePath() + "\\lastFailedTest.evya").delete();
            System.out.println("Succeeded autoTest");
        } else {
            System.out.println("Failed test");
            autoTesto.saveLastTest();
        }
    }

    public BTreeLatex(BTree tree, String filename) {
        deletions = new Integer[0];
        insertions = new Integer[0];
        statesHolder = new StringBuilder();
        buffer = new StringBuilder();
        longTermBuffer = new StringBuilder();
        this.tree = tree;
        readableFile = new File(dir.getAbsolutePath() + "\\" + filename + ".txt");
        try {
            dir.mkdir();
            readableFile.createNewFile();
            statesHolder.append("\\documentclass{standalone}\n"
                    + " \\usepackage{forest}\n"
                    + " \\usepackage{xcolor,colortbl}"
                    + "\n"
                    + "\\begin{document}\n"
                    + "\n"
                    + "\\begin{tabular}{@{}c@{}}\n"
                    + "\\\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean testDelete(BTree tree, int bound) {
        boolean result = true;
        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(dir.getAbsolutePath() + "\\manualCodeOfLastTest.txt"), true)));
            deletions = arrScrambler(bound);
            for (int i = 0; i < deletions.length - 1 & result; i++) {
                clearBuffer();
                addTreeState("before deleting " + deletions[i]);
                writer.println("tree.delete(" + deletions[i] + ");");
                tree.delete(deletions[i]);
                result = addTreeState("after deleting " + deletions[i]);
            }

            if (result) {
                clearBuffer();
                addTreeState("before deleting " + deletions[deletions.length - 1]);
                tree.delete(deletions[deletions.length - 1]);
                addTreeState("after deleting " + deletions[deletions.length - 1]);
                result = tree.getRoot() == null;
            }

            if (!result) {
                commitBufferedStates();
                finish();
            }
            writer.close();
            return result;
        } catch (Exception e) {
            commitBufferedStates();
            finish();
            e.printStackTrace();
            return false;
        }
        //return true;
    }

    private static Integer[] arrScrambler(int bound) {
        Integer[] arr = new Integer[bound];
        for (int i = 0; i < bound; i++) {
            arr[i] = i + 1;
        }
        List<Integer> list = Arrays.asList(arr);
        Collections.shuffle(list);
        arr = (Integer[]) list.toArray();
        return arr;
    }

    public boolean initSimpleTree(BTree tree, int bound) {
        boolean result = true;
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(new File(dir.getAbsolutePath() + "\\manualCodeOfLastTest.txt"))));
            writer.println("BTree tree = new BTree(" + tree.getT() + "); \n BTreeLatex manualTesto=new BTreeLatex(tree,\"manualTest\");");
            insertions = arrScrambler(bound);
            for (int i = 0; i < bound; i++) {
                clearBuffer();
                addTreeState("before inserting " + insertions[i]);
                writer.println("tree.insert(new Block(" + insertions[i] + ",null));");
                tree.insert(new Block(insertions[i], null));
                result = addTreeState("after inserting " + insertions[i]);
                if (!result) {
                    commitBufferedStates();
                    finish();
                    writer.close();
                    return result;
                }
            }
            writer.close();
        } catch (Exception e) {
            commitBufferedStates();
            finish();
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void finish() {
        try {
            PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(readableFile)));
            writer.println(statesHolder + "\\end{tabular}\n"
                    + "\n"
                    + "\\end{document}");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addTreeState(String stateName) {
        boolean result = true;
        buffer.setLength(0);
        buffer.append("\\qquad \\begin{forest}\n"
                + "label={" + stateName + "},\n"
                + "for tree={draw,l=20+level*5mm, s sep=3mm,anchor=north,child anchor=north,if={isodd(n_children())}{\n"
                + "      for children={\n"
                + "        if={\n"
                + "          equal(n,int((1+n_children(\"!u\"))/2))\n"
                + "        }{calign with current}{}\n"
                + "      }\n"
                + "    }{},align=center}");
        result = createNodes(tree.getRoot(), Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        buffer.append(" \\end{forest}"
                + "\n \\qquad \\mbox{} \\\\[0.4in] ");
        longTermBuffer.append(buffer);
        return result;
    }

    private boolean createNodes(BNode node, int min, int max, boolean isRoot) {
        boolean result = true;
        if (node == null) {
            buffer.append("[NULL ,color=red]");
            return false;
        }
        int currentKey = -1;
        if (node.getBlocksList().size() > 0) {
            currentKey = node.getBlockAt(0).getKey();
        } else {
            result = false;
        }

        String misplaced = "";
        if (currentKey > max | currentKey < min) {
            misplaced = "\\cellcolor{red!25} ";
            result = false;
        }
        buffer.append("[\\begin{tabular}{@{}c@{}} \\ {\\begin{tabular}{|*{" + Math.max(1, node.getBlocksList().size()) + "}{c|}} " + "\\hline ");
        buffer.append(currentKey + " " + misplaced);
        int localMin = misplaced == "" ? currentKey : min;
        misplaced = "";
        for (int i = 1; i < node.getBlocksList().size(); i++) {
            currentKey = node.getBlockAt(i).getKey();
            if (currentKey > max | currentKey < min | currentKey < localMin) {
                misplaced = "\\cellcolor{red!25} ";
                result = false;
            }
            buffer.append("& " + misplaced
                    + node.getBlockAt(i).getKey() + " ");
            localMin = misplaced == "" ? currentKey : localMin;
            misplaced = "";
        }

        String error = "";
        if ((!node.getChildrenList().isEmpty() & node.isLeaf()) | (node.getChildrenList().isEmpty() & !node.isLeaf())) {
            error += "\\\\ \\colorbox{red!50}{Leaf state and children}";
            result = false;
        }
        if (node.getNumOfBlocks() != node.getBlocksList().size()) {
            error = "\\\\ \\colorbox{red!50}{blocks $\\neq$ numOfBlocks} ";
            result = false;
        }
        if ((node.getChildrenList().size() != node.getNumOfBlocks() + 1) & !node.isLeaf()) {
            error = "\\\\ \\colorbox{red!50}{children $\\neq$ numOfBlocks+1} ";
            result = false;
        }
        if ((node.getChildrenList().size() != node.getBlocksList().size() + 1) & !node.isLeaf()) {
            error = "\\\\ \\colorbox{red!50}{children $\\neq$ blocks+1} ";
            result = false;
        }
        if (node.getChildrenList().size() > 2 * tree.getRoot().getT()) {
            error = "\\\\ \\colorbox{red!50}{children $>$ 2t} ";
            result = false;
        }
        if (node.getBlocksList().size() > 2 * tree.getRoot().getT() - 1) {
            error = "\\\\ \\colorbox{red!50}{blocks $>$ 2t-1} ";
            result = false;
        }
        if (node.getBlocksList().size() < tree.getRoot().getT() - 1 & !isRoot) {
            error = "\\\\ \\colorbox{red!50}{blocks $<$ t-1} ";
            result = false;
        }
        if (currentKey == -1) {
            error = "\\\\ \\colorbox{red!50}{blocks=0} ";
            result = false;
        }

        buffer.append("\\\\ \\hline \\end{tabular}} " + dataOfNode(node) + error
                + " \\end{tabular}");
        if (!node.isLeaf()) {
            for (int i = 0; i < node.getChildrenList().size(); i++) {
                int keyBefore = (node.getBlocksList().size() > 0 & i > 0) ? node.getBlockKeyAt(i - 1) : min;
                int keyAfter = (node.getBlocksList().size() > 0 & i < node.getChildrenList().size() - 1) ? node.getBlockKeyAt(i) : max;
                result = result & createNodes(node.getChildAt(i), keyBefore, keyAfter, false);
            }
        }

        buffer.append("]\n");
        return result;
    }

    public void clearBuffer() {
        longTermBuffer.setLength(0);
    }

    public void commitBufferedStates() {
        statesHolder.append(longTermBuffer);
    }

    private static boolean solvedLastBug() {
        return !new File(dir.getAbsolutePath() + "\\lastFailedTest.evya").exists();
    }

    private static String dataOfNode(BNode node) {
        String result = "";
        result += "\\\\  \\footnotesize{numOfBlocks: " + node.getNumOfBlocks() + "} "
                + "\\\\ \\footnotesize{blocks: " + node.getBlocksList().size() + "} "
                + "\\\\  \\footnotesize{children: " + node.getChildrenList().size() + "} "
                + " \\\\ \\footnotesize{isLeaf: " + node.isLeaf() + "} ";
        return result;
    }

    private void saveLastTest() {
        try {
            File file = new File(dir.getAbsolutePath() + "\\lastFailedTest.evya");
            file.createNewFile();
            FileOutputStream fileOut
                    = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeInt(tree.getT());
            out.writeInt(insertions.length);
            for (int i = 0; i < insertions.length; i++) {
                out.writeInt(insertions[i]);
            }
            out.writeInt(deletions.length);
            for (int i = 0; i < deletions.length; i++) {
                out.writeInt(deletions[i]);
            }
            out.close();
            fileOut.close();
            System.out.println("Saved last failed test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadLastFailedTest() {
        try {
            FileInputStream fileIn = new FileInputStream(new File(dir.getAbsolutePath() + "\\lastFailedTest.evya"));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            tree = new BTree(in.readInt());
            insertions = new Integer[in.readInt()];
            for (int i = 0; i < insertions.length; i++) {
                insertions[i] = in.readInt();
            }
            deletions = new Integer[in.readInt()];
            for (int i = 0; i < deletions.length; i++) {
                deletions[i] = in.readInt();
            }
            in.close();
            fileIn.close();
            System.out.println("Loaded last failed test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean useInsertions() {
        boolean result = true;
        for (int i = 0; i < insertions.length & result; i++) {
            clearBuffer();
            addTreeState("before inserting " + insertions[i]);
            tree.insert(new Block(insertions[i], null));
            result = addTreeState("after inserting " + insertions[i]);
        }
        return result;
    }

    public boolean useDeletions() {
        boolean result = true;
        for (int i = 0; i < deletions.length - 1 & result; i++) {
            clearBuffer();
            addTreeState("before deleting " + deletions[i]);
            tree.delete(deletions[i]);
            result = addTreeState("after deleting " + deletions[i]);
        }
        if (result) {
            clearBuffer();
            addTreeState("before deleting " + deletions[deletions.length - 1]);
            tree.delete(deletions[deletions.length - 1]);
            addTreeState("after deleting " + deletions[deletions.length - 1]);
            result = tree.getRoot() == null;
        }

        return result;
    }

    public static void StartLastFailedTest() {
        BTreeLatex latx = new BTreeLatex(null, "BTreeInLatex");
        latx.loadLastFailedTest();
        if (latx.useInsertions() && latx.useDeletions()) {
            System.out.println("Success!");
            new File(dir.getAbsolutePath() + "\\lastFailedTest.evya").delete();
        } else {
            latx.commitBufferedStates();
            latx.finish();
            System.out.println("Failed again");
        }
    }
}
