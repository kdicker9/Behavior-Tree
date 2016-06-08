/**
 * Kevin Dickerson
 */

/**
 * A behavior tree will be shown. Enter the name of a behavior and it will return
 * a random leaf branching from that node. Entering an invalid node name will
 * return a random leaf from the root.
 */

package behavior_tree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import behavior_tree.Tree;

public class XMLParser {
	private static Tree<Tree.Node> t = new Tree<Tree.Node>("ROOT");
	
	public static void main(String[] args) throws IOException {
		readXML("sample.xml");
		t.getRoot().printTree();
		System.out.println("-------------------------");
		System.out.print("Event ('quit' to exit) : ");
		Scanner scan = new Scanner(System.in);
		String input = scan.nextLine();
		while (!input.equals("quit")) {
			// BFS
			System.out.print("Response = " + t.breadthFirstSearch(t.getRoot(), input).getRandomChild().getData());
			// DFS
			//System.out.print("Response = " + t.depthFirstSearch(t.getRoot(), input).getRandomChild().getData());
			
			System.out.print("\nEvent ('quit' to exit) : ");
			input = scan.nextLine();
		}
		scan.close();
	}
	
	// Reads in the XML file line by line, parses, sends data to addLineToTree
	static void readXML(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String currentRow = br.readLine();
		while (currentRow != null) {
			String[] currentRowArray = currentRow.split("\"");
			addLineToTree(currentRowArray, currentRow);
			currentRow = br.readLine();
		}
		br.close();
		System.out.println("Behavior tree loaded...");
	}
	
	// Creates a node on the tree using a parsed line of XML
	static void addLineToTree(String[] lineArray, String line) {
		String text;
		// response nodes have a length of 5
		if (lineArray.length == 5) {
			text = lineArray[1];
			if (text.equals("")) {
				text = lineArray[3];
			}
		}
		// behavior nodes have a length of 3
		else if (lineArray.length == 3) {
			text = lineArray[1];
		}
		else {
			text = "";
		}
		
		// deals with non behavior/response lines
		if (text.equals("")) {
			// stops creating children for current node when node is closed
			if (line.contains("/")) {
				t.setCurrent(t.getCurrent().getParent());
			}
		}
		else {
			Tree.Node newNode = new Tree.Node();
			newNode.setData(text);
			newNode.setParent(t.getCurrent());
			t.getCurrent().addChild(newNode);
			t.setCurrent(newNode);
			
			if (line.contains("/")) {
				t.setCurrent(newNode.getParent());
			}
		}
	}
}
