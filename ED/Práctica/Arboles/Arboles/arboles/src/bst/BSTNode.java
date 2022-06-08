/**
 * 
 */
package bst;

/**
 * @author UO285176
 *
 */
public class BSTNode <T extends Comparable <T>>{

	private T info;//contenido del nodo de tipo gen�rico
	private BSTNode<T> left;//nodo hijo izquierdo
	private BSTNode<T> right;//nodo hijo derecho
	
	/*
	 * Constructor de la clase 
	 */
	public BSTNode(T clave) {
		setInfo(clave);
		this.left = null;
		this.right = null;
	}
	
	public void setInfo(T clave) {
		this.info = clave;
	}
	
	public T getInfo() {
		return this.info;
	}
	
	public void setLeft(BSTNode<T> nodo) {
		this.left = nodo;
	}
	
	public void setRight(BSTNode<T> nodo) {
		this.right = nodo;
	}
	
	public BSTNode<T> getLeft() {
		return this.left;
	}
	
	public BSTNode<T> getRight() {
		return this.right;
	}
	
	public String toString() {
		return info.toString();
	}
	
	
}
