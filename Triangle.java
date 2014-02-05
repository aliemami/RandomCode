

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.StringTokenizer;

public class Triangle {
	class Node {
		public Node left = null;
		public Node right = null;
		public Node next = null;
		public Long value= null;
		public Long heavyWeight = null;
		public Long lightWeight = null;
		public Node(Long val) {	this.value = val;}
		public String toString() { return value+"";}
	}

	private Node head = null;
	private Node rowHead = null;
	private Node parent = null;
	private long min = 0;
	private long max = 0;

	private int level =0;
	private int numNodesInRow = 0;

	private void insert(Long value) {
		Node myNode = new Node(value);
		//if the head 
		if (head == null) {
			head = myNode;
			rowHead = head;
			parent = head; 
			level=1;
			numNodesInRow++;
		} 

		else if (numNodesInRow == level) {
			//upgrade the level
			level++;
			numNodesInRow = 1;
			parent=rowHead;
			parent.left =myNode;
			rowHead=myNode;
		}

		else {
			numNodesInRow++;
			parent.left.next = myNode;
			parent.right = myNode;
			if (parent.next != null) {
				parent = parent.next;
				parent.left = myNode;
			}
		}
	}

	public void print() {
		Node node = head;
		while (node != null) {
			printRow(node);
			node = node.left;
		}
	}

	private void printRow(Node node) {
		while(node != null) {
			System.out.print(node + " " );
			node = node.next;
		};
		System.out.println("");
	}

	private void calcWeight(){
		calcWeightHelper(head);
	}
	private void calcWeightHelper(Node node) {
		if (node.left !=null) {
			calcWeightHelper(node.left);
		}
		calcWeightHelperRow(node);

	}

	private void calcWeightHelperRow(Node node) {
		while(node != null) {
			if (node.left!= null && node.right != null) { 
				Long lhw = node.left.heavyWeight;
				Long rhw = node.right.heavyWeight;

				Long llw = node.left.lightWeight;
				Long rlw = node.right.lightWeight;

				if (lhw > rhw) {
					node.heavyWeight = node.value + lhw;
				}else {
					node.heavyWeight = node.value + rhw;
				}

				if (llw < rlw) {
					node.lightWeight = node.value + llw;
				}else {
					node.lightWeight = node.value + rlw;
				}

			} else if (node.left != null && node.right == null) {
				Long lhw = node.left.heavyWeight;
				node.heavyWeight = node.value + lhw;

				Long llw = node.left.lightWeight;
				node.lightWeight = node.value + llw;

			} else if (node.right != null && node.left == null) {
				Long rhw = node.right.heavyWeight;
				node.heavyWeight = node.value + rhw;

				Long rlw = node.right.lightWeight;
				node.lightWeight = node.value + rlw;
			}
			else {
				node.heavyWeight = node.value;
				node.lightWeight = node.value;
			}
			node = node.next;
		};
	}

	private long calcPathMaximum () {
		Node node = head;
		while(node!=null) {
			System.out.print(node.value + "->");
			max = node.value + max;
			if (node.right!= null && node.left !=null && node.right.heavyWeight >= node.left.heavyWeight) {
				node = node.right;
			} else if (node.left!= null && node.right !=null && node.left.heavyWeight > node.right.heavyWeight) {
				node = node.left;
			} else {
				break;
			}

		}
		System.out.println("");

		return max;
	}


	private long calcPathMinimum () {
		Node node = head;
		while(node!=null) {
			System.out.print(node.value + "->");
			if (node.right!= null && node.left !=null && node.right.lightWeight <= node.left.lightWeight) {
				node = node.right;
			} else if (	node.left!= null && node.right !=null && node.right.lightWeight > node.left.lightWeight) {
				node = node.left;
			} else if (node.right == null && node.left !=null) {
				System.out.println("structure error: right is null left is not");
			} else if (node.left == null && node.right !=null) {
				System.out.println("structure error: left is null right is not");
			} else {
				break;
			}
			min = node.value + min;
		}
		System.out.println("");
		return min;
	}


	public static void main(String [] args) {
		try {
			System.out.print("Enter the Path to the Input File: ");		
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String filepath = null;
			filepath = br.readLine();
			System.out.println("You Entered: : " + filepath);

			Triangle tri = new Triangle();
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(filepath));
			long startTime = (new Date()).getTime();

			String ln="";

			while ((ln = in.readLine() )!= null) {
				if(ln.trim().length() > 0) {
					StringTokenizer st = new StringTokenizer(ln);
					while(st.hasMoreTokens()){
						String token =  st.nextToken();
						tri.insert(Long.valueOf(token));
					}
				}
			}

			long endTime = (new Date()).getTime();
			long duration = ((endTime - startTime));
			System.out.println("Building the structure took:" + duration + " milisec.");

			System.out.println("");

			startTime = (new Date()).getTime();
			tri.calcWeight();
			endTime = (new Date()).getTime();
			duration = ((endTime - startTime));
			System.out.println("Calculating weight took :" + duration + " milisec.");

			System.out.println("");

			startTime = (new Date()).getTime();
			System.out.println("Maximum value: " + tri.calcPathMaximum() );
			System.out.println("Minimum value: " + tri.calcPathMinimum() );
			System.out.println("");

			endTime = (new Date()).getTime();
			duration = ((endTime - startTime));
			System.out.println("Showing path to min/max took :" + duration + " milisec.");
		} catch (FileNotFoundException e) {
			System.out.println("File not found - try again");
		} catch (NumberFormatException e) {
		} catch (IOException e) {
		}
	}

}
