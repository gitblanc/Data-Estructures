/**
 * 
 */
package avl;

/**
 * @author UO285176
 *
 */
public class AVLNode<T extends Comparable<T>> {

	private T info;// contenido del nodo de tipo gen?rico
	private AVLNode<T> left;// nodo hijo izquierdo
	private AVLNode<T> right;// nodo hijo derecho
	private int balanceFactor;// factor de balance del nodo
	private int height;// altura del nodo

	/*
	 * Constructor
	 */
	public AVLNode(T clave) {
		setInfo(clave);
		setLeft(null);
		setRight(null);
		this.balanceFactor = 0;
		this.height = 0;
	}

	public T getInfo() {
		return info;
	}

	public void setInfo(T info) {
		this.info = info;
	}

	public AVLNode<T> getLeft() {
		return left;
	}

	public void setLeft(AVLNode<T> left) {
		this.left = left;
	}

	public AVLNode<T> getRight() {
		return right;
	}

	public void setRight(AVLNode<T> right) {
		this.right = right;
	}

	public int getBF() {
		return balanceFactor;
	}

	public int getHeight() {
		return height;
	}

	/*
	 * M?todo que actualiza el factor de balance de un nodo AVL
	 */
	public void updateBFHeight() {
		if (getRight() != null && getLeft() == null) {// Si s?lo tiene un hijo derecho
			this.balanceFactor = getRight().getHeight() - (-1);
			this.height = 1 + Math.max(-1, getRight().getHeight());
		} else if (getRight() != null && getLeft() != null) {// si tiene dos hijos
			this.balanceFactor = getRight().getHeight() - getLeft().getHeight();
			this.height = 1 + Math.max(getRight().getHeight(), getLeft().getHeight());
		} else if (getRight() == null && getLeft() == null) {// si no tiene hijos (es un nodo hoja)
			this.balanceFactor = 0;
			this.height = 0;
		} else {
			this.balanceFactor = -1 - getLeft().getHeight();
			this.height = 1 + Math.max(getLeft().getHeight(), -1);
		}
	}

	public String toString() {
		return this.info.toString() + ":BF=" + getBF();
	}
}
