/**
 * Kevin Dickerson
 * 
 * Main in XMLParser
 */

package behavior_tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Tree<Node> {
    private Node root;
    private Node current;

    public Tree(String d) {
        root = new Node();
        root.data = d;
        root.children = new ArrayList<Node>();
        current = root;
    }
    
    public Node getRoot() {
    	return root;
    }
    
    public Node getCurrent() {
    	return current;
    }
    
    public void setCurrent(Node c) {
    	current = c;
    }

    public static class Node {
        private String data;
        private Node parent;
        private List<Node> children = new ArrayList<Node>();
        
        public String getData() {
        	return data;
        }
        
        public void setData(String d) {
        	data = d;
        }
        
        public Node getParent() {
        	return parent;
        }
        
        public void setParent(Node n) {
        	parent = n;
        }
        
        public List<Node> getChildren() {
        	return children;
        }
        
        public void addChild(Node n) {
        	children.add(n);
        }
        
        public boolean isLeaf() {
        	return children.isEmpty();
        }
        
        // returns depth of node
        // literally made this method just to make it print prettier
        public int getDepth() {
        	int depth = 0;
        	Node c = this;
        	while (c.getParent() != null) {
        		depth++;
        		c = c.getParent();
        	}
        	return depth;
        }
        
        public void printTree() {
        	for (int i=0; i<getDepth(); i++) {
        		System.out.print("   ");
        	}
        	if (this.isLeaf())
        		System.out.print("response = ");
        	else
        		System.out.print("behavior = ");
        	System.out.print(getData());
        	System.out.println();
        	for (Node n : children) {
        		n.printTree();
        	}
        }
        
        // returns a random leaf descendant of a node
        public Node getRandomChild() {
        	Node rc = this;
        	while (!rc.getChildren().isEmpty()) {
        		Random r = new Random();
        		int Low = 0;
        		int High = rc.getChildren().size();
        		int R = r.nextInt(High-Low) + Low;
        		rc = rc.getChildren().get(R);
        	}
        	return rc;
        }
    }
    
    public Node breadthFirstSearch(Node r, String text) {
    	text = text.toLowerCase();
    	//System.out.println("Searching " + r.getData() + " for " + text);
    	
    	if (r.getData().toLowerCase().equals(text)) {
    		return r;
    	}
    	else {
    		Queue<Node> q1 = new LinkedList<Node>();
    		Queue<Node> q2 = new LinkedList<Node>();
    		
			for (Node n : r.getChildren()) {
				q1.add(n);
			}
			do {
				for (Node o : q1) {
					//System.out.println("Searching " + o.getData() + " for " + text);
					if (o.getData().toLowerCase().equals(text)) {
    					return o;
    				}
					else {
						for (Node p : o.getChildren()) {
							q2.add(p);
						}
					}
				}
				q1.clear();
				for (Node q : q2) {
					q1.add(q);
				}
				q2.clear();
			} while (!q1.isEmpty());
    	}
    	// if request isn't found, return random leaf node in tree
    	return r.getRandomChild();
    }
    
    public Node depthFirstSearch(Node r, String text) {
    	//System.out.println("Searching " + r.getData() + " for " + text);
    	text = text.toLowerCase();
    	if (r.getData().toLowerCase().equals(text)) {
    		return r;
    	}
    	else {
    		for (Node a : r.getChildren()) {
    			Node b = depthFirstSearch(a, text);
    			if (b.getData().toLowerCase().equals(text)) {
    				return b;
    			}
    		}
    	}
    	return r.getRandomChild();
    }
}