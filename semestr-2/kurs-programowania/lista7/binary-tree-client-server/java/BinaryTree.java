/**
 * Klasa reprezentujaca drzewo poszukiwan binarnych (BST).
 * @param <T> Typ elementow przechowywanych w drzewie (musi implementowac Comparable).
 */
public class BinaryTree<T extends Comparable<T>>{
	
	private static class Node<T>{
		T key;
		Node<T> left;
		Node<T> right;
	
		public Node(T k){
			key = k;
			left = null;
			right = null;            
		}
	}

	private Node<T> root;

	public BinaryTree(){
		initTree();
	}

	private void initTree(){
		root=null;
	}

	/**
	 * Szuka elementu w drzewie.
	 * @param key Szukana wartosc.
	 * @return true jesli element istnieje, false w przeciwnym razie.
	 */
	public boolean search(T key){
		return searchRec(root, key);
	}

	private boolean searchRec(Node <T> node, T key){
		if(node==null){
			return false;
		}
		if(key.compareTo(node.key)==0){
			return true;
		}
		else if(key.compareTo(node.key)<0){
			return searchRec(node.left, key);
		}
		else{
			return searchRec(node.right, key);
		}
	}

	/**
	 * Wstawia nowy element do drzewa.
	 * @param key Wartosc do wstawienia.
	 */
	public void insert(T key){
		root = insertRec(root, key);
	}

	private Node<T> insertRec(Node<T> node, T key){
		if(node==null){
			return new Node<T>(key);
		}
		if(key.compareTo(node.key)<0){
			node.left = insertRec(node.left, key);
		}
		else if(key.compareTo(node.key)>0){
			node.right = insertRec(node.right,key);
		}
		return node;
	}

	/**
	 * Usuwa element z drzewa.
	 * @param key Wartosc do usuniecia.
	 */
	public void delete(T key){
		root = deleteRec(root,key);
	}

	private Node<T> deleteRec(Node<T> node, T key){
		if(node==null){
			return null;
		}
		if(key.compareTo(node.key)<0){
			node.left = deleteRec(node.left, key);
		}
		else if(key.compareTo(node.key)>0){
			node.right = deleteRec(node.right, key);
		}
		else{
			if(node.left==null && node.right==null){
				return null;
			}
			else if(node.left==null){
				return node.right;
			}
			else if(node.right==null){
				return node.left;
			}
			else{
				Node<T> minNode = findMin(node.right);
				node.key = minNode.key;
				node.right = deleteRec(node.right, minNode.key);
			}
		}
		return node;
	}

	private Node<T> findMin(Node<T> node){
		while(node.left!=null){
			node=node.left;
		}
		return node;
	}

	public void draw(java.io.PrintWriter out){
		drawRec(root, 0, out);
	}

	private void drawRec(Node<T> node, int space, java.io.PrintWriter out){
		if(node==null){
			return;
		}
		int count=5;
		space+=count;

		drawRec(node.right,space, out);
		
		for(int i=count;i<space;i++){
			out.print(" ");
		}
		out.println(node.key);
		
		drawRec(node.left,space, out);
	}
}