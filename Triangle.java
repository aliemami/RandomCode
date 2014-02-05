
public class Triangle {

	private Node head = null;
	private Node rowHead = null;
	private Node parent = null;

	private long min = 0;
	private long max = 0;

	private int level =0;
	private int numNodesInRow = 0;

	public void insert(Long value) {
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

	public void calcWeight(){
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

	public long calcPathMaximum () {
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


	public long calcPathMinimum () {
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


	

}