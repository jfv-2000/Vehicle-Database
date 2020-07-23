import java.util.*;
import java.lang.*;
public class AVL <K, V> extends TreeMap<K, V>
{
	class Node
	{
		private Vehicle v;
		int height;
		private Node left;
		private Node right;
		private Node parent;

		Node(Vehicle v1)
		{
			v = v1;
			height = 1;
			left = null;
			right = null;
			parent = null;
		}
		public String toString()
		{
			return v.toString();
		}

		public Vehicle getVehicle()
		{
			return v;
		}
	}

	Node root;

	int max(int x, int y)
	{
		return(x > y)? x : y;
	}

	int height(Node n)
	{
		if (n == null)
			return 0;
		return n.height;
	}

	Node rightRotate(Node n) { 
		Node left = n.left; 
		Node rightOfLeft = left.right; 

		left.right = n; 
		n.left = rightOfLeft; 

		n.height = max(height(n.left), height(n.right)) + 1; 
		left.height = max(height(left.left), height(left.right)) + 1; 

		return left; 
	} 

	Node leftRotate(Node n) { 
		Node right = n.right; 
		Node leftOfRight = right.left; 

		right.left = n; 
		n.right = leftOfRight; 

		n.height = max(height(n.left), height(n.right)) + 1; 
		right.height = max(height(right.left), height(right.right)) + 1; 

		return right; 
	} 

	int getBalance(Node n) { 
		if (n == null) 
			return 0; 

		return height(n.left) - height(n.right); 
	} 

	Node insert(Node node, Vehicle v) { 
		Node t = null;
		if (node == null) 
			return (new Node(v)); 
		if (v.getVIN().compareTo(node.v.getVIN()) < 0) 
		{
			t = insert(node.left, v);
			node.left = t;
			t.parent = node;

		}
		else if (v.getVIN().compareTo(node.v.getVIN()) > 0)
		{
			t = insert(node.right, v);
			node.right = t;
			t.parent = node;
		}
		else 
		{
			return node; 
		}

		node.height = 1 + max(height(node.left), height(node.right)); 

		int balance = getBalance(node); 

		if (balance > 1 && v.getVIN().compareTo(node.left.v.getVIN()) < 0) 
			return rightRotate(node); 

		if (balance < -1 && v.getVIN().compareTo(node.right.v.getVIN()) > 0) 
			return leftRotate(node); 

		if (balance > 1 && v.getVIN().compareTo(node.left.v.getVIN()) > 0) { 
			node.left = leftRotate(node.left); 
			return rightRotate(node); 
		} 

		if (balance < -1 && v.getVIN().compareTo(node.right.v.getVIN()) < 0) { 
			node.right = rightRotate(node.right); 
			return leftRotate(node); 
		} 
		return node; 
	}

	Node minValueNode(Node n)  
	{  
		Node current = n;  

		while (current.left != null)  
			current = current.left;  

		return current;  
	}  

	Node maxValueNode(Node n)  
	{  
		Node current = n;  

		while (current.right != null)  
			current = current.right;  

		return current;  
	}  

	Node removeNode(Node root, String key)  
	{  	
		if (root == null)  
			return root;  
		if (key.compareTo(root.v.getVIN()) < 0)
			root.left = removeNode(root.left, key);  
		else if (key.compareTo(root.v.getVIN()) > 0)  
			root.right = removeNode(root.right, key);  
		else
		{  
			if ((root.left == null) || (root.right == null))  
			{  
				Node temp = null;  
				if (temp == root.left)  
					temp = root.right;  
				else
					temp = root.left;  
				if (temp == null)  
				{  
					temp = root;  
					root = null;  
				}  
				else
					root = temp; 
			}  
			else
			{  
				Node temp = minValueNode(root.right);  
				root.v = temp.v;  
				root.right = removeNode(root.right, root.v.getVIN());  
			}  
		}  
		if (root == null)  
			return root;  
		root.height = max(height(root.left), height(root.right)) + 1;  
		int balance = getBalance(root);  

		if (balance > 1 && getBalance(root.left) > 0)  
			return rightRotate(root);  

		if (balance > 1 && getBalance(root.left) < 0)  
		{  
			root.left = leftRotate(root.left);  
			return rightRotate(root);  
		}  

		if (balance < -1 && getBalance(root.right) < 0)  
			return leftRotate(root);  

		if (balance < -1 && getBalance(root.right) > 0)  
		{  
			root.right = rightRotate(root.right);  
			return leftRotate(root);  
		}  
		return root;  
	}  
	public void inorder()
	{
		inorder(root);
	}

	private void inorder(Node r)
	{
		if(r != null)
		{
			inorder(r.left);
			System.out.println(r.v + " ");
			inorder(r.right);
		}
	}

	Node getNodeValue(Node root, String key)  
	{  	
		if (root == null)  
			return root;  
		if (key.compareTo(root.v.getVIN()) < 0)
			root = getNodeValue(root.left, key);  
		else if (key.compareTo(root.v.getVIN()) > 0)  
			root = getNodeValue(root.right, key);
		else if(key.compareTo(root.v.getVIN()) == 0)
			return root;
		return root;  
	}  

	Node getNext(Node root, String key)
	{
		Node parent = null;
		Node n = root;
		if (root == null)  
			return root;  
		if (key.compareTo(root.v.getVIN()) < 0)
		{
			parent = getNext(root.left, key);
		}
		else if (key.compareTo(root.v.getVIN()) > 0)  
		{
			parent = getNext(root.right, key);
		}
		else if (key.compareTo(root.v.getVIN()) == 0)
		{  
			n = root;
			if(n.right != null)
			{
				return minValueNode(n.right);
			}
			parent = n.parent;
			while (parent != null && n == parent.right)
			{
				n = parent;
				parent = parent.parent;
			}
			return parent;
		}  
		return parent;
	}

	Node getPrev(Node root, String key)
	{
		Node parent = null;
		Node n = root;
		if (root == null)  
			return root;  
		if (key.compareTo(root.v.getVIN()) < 0)
			parent = getPrev(root.left, key);  
		else if (key.compareTo(root.v.getVIN()) > 0)  
			parent = getPrev(root.right, key);  
		else
		{  
			n = root;
			if(n.left != null)
			{
				return maxValueNode(n.left);
			}
			parent = n.parent;
			while(parent != null && n == parent.left)
			{
				n = parent;
				parent = parent.parent;
			}
		}  
		return parent;
	}

	public static void main(String[] args) { 
		AVL tree = new AVL(); 

		/* Constructing tree given in the above figure */
		Vehicle v1 = new Vehicle("JMQMMU4UK2CCHDR");
		Vehicle v2 = new Vehicle("OBEXKNZURTGZRFI");
		Vehicle v3 = new Vehicle("KCQXWGOULQRC0L1");
		Vehicle v4 = new Vehicle("A1Y1G8FREXYHGOG");
		Vehicle v5 = new Vehicle("XVVIVF1PZEYJAMR");
		Vehicle v6 = new Vehicle("VHULDLYZJAA6CLS");
		System.out.println("hel");
		tree.root = tree.insert(tree.root, v1); 
		System.out.println("hel");
		tree.root = tree.insert(tree.root, v2); 
		System.out.println("hel");
		tree.root = tree.insert(tree.root, v3); 
		System.out.println("hel");
		tree.root = tree.insert(tree.root, v4); 
		System.out.println("hel");
		tree.root = tree.insert(tree.root, v5); 
		System.out.println("hel");
		tree.root = tree.insert(tree.root, v6); 

		System.out.println("inorder traversal" + 
				" of constructed tree is : "); 
		tree.inorder(tree.root);
		System.out.println("Content of tree: "+tree);

	} 
}
