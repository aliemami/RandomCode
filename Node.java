public	class Node {
	public Node left = null;
	public Node right = null;
	public Node next = null;
	public Long value= null;
	public Long heavyWeight = null;
	public Long lightWeight = null;
	public Node(Long val) {	this.value = val;}
	public String toString() { return value+"";}
}
