/**
 * 
 */
package bst;

/**
 * @author UO285176
 *
 */
public class BSTree<T extends Comparable<T>> {
	private BSTNode<T> raiz;// nodo ra?z del ?rbol BST

	/*
	 * Constructor
	 */
	public BSTree() {
		this.raiz = null;
	}

	/*
	 * Si la ra?z o la clave es null devuelve null Si no lo son invoca a un m?todo
	 * recursivo que busca la clave por la derecha o por la izquierda, dependiendo
	 * si la clave es mayor o menor que el nodo a partir del cual se busca. Devuelve
	 * el nodo completo o null si no lo encuentra.
	 */
	public BSTNode<T> searchNode(T clave) {
		if (this.raiz == null || clave == null)
			return null;
		else
			return searchNodeRecursivo(this.raiz, clave);
	}

	private BSTNode<T> searchNodeRecursivo(BSTNode<T> nodoraiz, T clave) {
		if (nodoraiz == null)
			return null;
		else if (nodoraiz.getInfo().compareTo(clave) == 0)// la raiz contiene la misma informacion que la clave
			return nodoraiz;
		else if (nodoraiz.getInfo().compareTo(clave) > 0)// si la clave es menor que el nodo, busca por la izquierda
			return searchNodeRecursivo(nodoraiz.getLeft(), clave);
		else if (nodoraiz.getInfo().compareTo(clave) < 0)// si el nodo es menor que la clave, busca por la derecha
			return searchNodeRecursivo(nodoraiz.getRight(), clave);
		else// si no lo encuentra devuelve null
			return null;
	}

	/*
	 * Si la clave es null devuelve -2 Si la clave ya existe devuelve -1 Si la ra?z
	 * es null, se crea un nodo con la clave que asignamos a la raiz y devuelve 0
	 * 
	 * Sino se invoca a un m?todo recursivo con el nodo a partir del cual queremos
	 * insertar y la clave a insertar. El m?todo busca un hueco por la derecha o por
	 * la izquierda, dependiendo de si la clave es mayor o menor que el nodo raiz.
	 * Cuando lo encuentra, crea un nuevo nodo con la clave y lo asigna por la
	 * derecha o por la izquierda y devuelve 0.
	 */
	public int addNode(T clave) {
		if (clave == null)
			return -2;
		else if (searchNode(clave) != null)
			return -1;
		else if (this.raiz == null) {
			this.raiz = new BSTNode<T>(clave);
			return 0;
		} else {
			return addNodeRecursivo(this.raiz, clave);
		}

	}

	private int addNodeRecursivo(BSTNode<T> nodoraiz, T clave) {
		if (nodoraiz.getInfo().compareTo(clave) > 0) {// si la raiz es mayor que la clave, busca por la izquierda
			if (nodoraiz.getLeft() != null)
				return addNodeRecursivo(nodoraiz.getLeft(), clave);
			else {
				nodoraiz.setLeft(new BSTNode<T>(clave));
				return 0;
			}
		} else if (nodoraiz.getInfo().compareTo(clave) < 0) {// si la raiz es menor que la clave, busca por la derecha
			if (nodoraiz.getRight() != null)
				return addNodeRecursivo(nodoraiz.getRight(), clave);
			else {
				nodoraiz.setRight(new BSTNode<T>(clave));
				return 0;
			}
		}
		return -1;// si ya existiese la clave, devolver?a -1
	}

	/*
	 * Devuelve la cadena preOrden -> raiz, izquierda, derecha
	 */
	public String preOrder() {
		String cadena = recorridoPreOrderRecursivo(this.raiz);
		return cadena.substring(0, cadena.length() - 1);
	}

	private String recorridoPreOrderRecursivo(BSTNode<T> nodoraiz) {
		if (nodoraiz == null)// si la ra?z es nula devolver? la cadena vac?a
			return "";
		String cadena = nodoraiz.getInfo().toString();
		cadena += "\t";
		cadena += recorridoPreOrderRecursivo(nodoraiz.getLeft());
		cadena += recorridoPreOrderRecursivo(nodoraiz.getRight());
		return cadena;
	}

	/*
	 * Devuelve la cadena postOrden -> izquierda, derecha, ra?z
	 */
	public String postOrder() {
		String cadena = recorridoPostOrderRecursivo(this.raiz);
		return cadena.substring(0, cadena.length() - 1);
	}

	private String recorridoPostOrderRecursivo(BSTNode<T> nodoraiz) {
		String cadena = "";
		if (nodoraiz != null) {
			cadena += recorridoPostOrderRecursivo(nodoraiz.getLeft());
			cadena += recorridoPostOrderRecursivo(nodoraiz.getRight());
			cadena += nodoraiz.getInfo().toString() + "\t";
		}
		return cadena;
	}

	/*
	 * Devuelve la cadena inOrden -> izquierda, ra?z, derecha
	 */
	public String inOrder() {
		String cadena = recorridoInOrderRecursivo(this.raiz);
		return cadena.substring(0, cadena.length() - 1);
	}

	private String recorridoInOrderRecursivo(BSTNode<T> nodoraiz) {
		String cadena = "";
		if (nodoraiz != null) {
			cadena += recorridoInOrderRecursivo(nodoraiz.getLeft());
			cadena += nodoraiz.getInfo().toString() + "\t";
			cadena += recorridoInOrderRecursivo(nodoraiz.getRight());
		}
		return cadena;
	}

	/*
	 * Si la clave o la ra?z es null devuelve -2 Si la clave no existe devuelve -1
	 * 
	 * Sino invoca a un m?todo recursivo con el nodo a partir del cual queremos
	 * borrar y la clave a borrar. Se asigna a la ra?z dicho nodo y se devuelve 0 El
	 * m?todo busca la clave por la derecha o por la izquierda dependiendo de si la
	 * clave a borrar es mayor o menor que el nodo a partir del cual se quiere
	 * borrar. Cuando lo encuentra lo borra y devuelve el nodo actualizado
	 */
	public int removeNode(T clave) {
		if (clave == null || this.raiz == null)
			return -2;
		else if (searchNode(clave) == null)
			return -1;
		else {
			this.raiz = removeNodeRecursivo(this.raiz, clave);
			return 0;
		}
	}

	private BSTNode<T> removeNodeRecursivo(BSTNode<T> nodoraiz, T clave) {
		if (nodoraiz.getInfo().compareTo(clave) > 0) {// si la raiz es mayor que la clave, busca por la izquierda el
														// nodo y se asigna
			BSTNode<T> nodo = removeNodeRecursivo(nodoraiz.getLeft(), clave);
			nodoraiz.setLeft(nodo);
			return nodoraiz;
		} else if (nodoraiz.getInfo().compareTo(clave) < 0) {// si la raiz es menor que la clave, busca por la derecha
																// el nodo y se asigna
			BSTNode<T> nodo = removeNodeRecursivo(nodoraiz.getRight(), clave);
			nodoraiz.setRight(nodo);
			return nodoraiz;
		} else {// si son iguales(el nodo a borrar es encontrado)
			if (nodoraiz.getLeft() == null && nodoraiz.getRight() == null)// si la raiz no tiene hijos
				return null;
			else if (nodoraiz.getLeft() != null && nodoraiz.getRight() == null)// si la raiz s?lo tiene un hijo
																				// izquierdo
				return nodoraiz.getLeft();
			else if (nodoraiz.getLeft() == null && nodoraiz.getRight() != null)// si la raiz solo tiene un hijo derecho
				return nodoraiz.getRight();
			else {// la raiz tiene dos hijos
				BSTNode<T> nodemax = searchMaxClave(nodoraiz.getLeft());// se busca el hijo mayor
				nodoraiz.setLeft(removeNodeRecursivo(nodoraiz.getLeft(), nodemax.getInfo()));
				nodoraiz.setInfo(nodemax.getInfo());
				return nodoraiz;
			}
		}
	}

	/*
	 * M?todo que busca el hijo mayor
	 */
	private BSTNode<T> searchMaxClave(BSTNode<T> nodoraiz) {
		if (nodoraiz.getRight() != null)// busca por los hijos derechos hasta llegar al ?ltimo
			return searchMaxClave(nodoraiz.getRight());
		return nodoraiz;
	}

}
