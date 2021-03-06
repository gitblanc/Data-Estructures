/**
 * 
 */
package avl;

/**
 * @author UO285176
 *
 */
public class AVLTree<T extends Comparable<T>> {

	private AVLNode<T> raiz;// nodo raiz del ?rbol AVL

	public AVLTree() {
		this.raiz = null;
	}

	/*
	 * Si la ra?z o la clave es null devuelve null
	 * 
	 * Sino invoca a un m?todo recursivo con el nodo a partir del cual queremos
	 * buscar y la clave a buscar. Este m?todo busca la clave por la derecha o por
	 * la izquierda dependiendo de si la clave es mayor o menor que el nodo a partir
	 * del cual se busca. Devuelve el nodo si lo encuentra o null si no lo encuentra
	 */
	public AVLNode<T> searchNode(T clave) {
		if (this.raiz == null || clave == null)
			return null;
		return searchNodeRecursivo(this.raiz, clave);
	}

	private AVLNode<T> searchNodeRecursivo(AVLNode<T> nodoraiz, T clave) {
		if (nodoraiz == null)
			return null;
		else if (nodoraiz.getInfo().compareTo(clave) > 0)// si la raiz es mayor que la clave, busca por la izquierda
			return searchNodeRecursivo(nodoraiz.getLeft(), clave);
		else if (nodoraiz.getInfo().compareTo(clave) < 0)// si la raiz es menor que la clave, busca por la derecha
			return searchNodeRecursivo(nodoraiz.getRight(), clave);
		else if (nodoraiz.getInfo().compareTo(clave) == 0)// si la raiz y la clave coinciden, se devuelve el nodo raiz
			return nodoraiz;
		else// si no lo encuentra devuelve null
			return null;
	}

	/*
	 * Si la clave es null devuelve -2 Si la clave ya existe devuelve -1 Si la ra?z
	 * es null, se crea un nodo con la clave que se asigna a la ra?z y devuelve 0.
	 * 
	 * Sino invoca a un m?todo recursivo con el nodo a partir del cual queremos
	 * insertar y la clave a insertar. Se asigna a la ra?z lo que devuelve el m?todo
	 * recursivo y devuelve 0.
	 * 
	 * Este m?todo busca un hueco por la derecha o por la izquierda, dependiendo de
	 * si la clave es mayor o menor que el nodo a partir del cual se quiere
	 * insertar. Cuando lo encuentra, crea un nuevo nodo con la clave y lo asigna
	 * por la derecha o por la izquierda. Actualiza el nodo y lo devuelve
	 */
	public int addNode(T clave) {
		if (clave == null)
			return -2;
		else if (searchNode(clave) != null)
			return -1;
		else if (this.raiz == null) {
			this.raiz = new AVLNode<T>(clave);
			return 0;
		} else {
			this.raiz = addNodeRecursivo(this.raiz, clave);// se genera un nuevo nodo que se le asigna
			return 0;
		}
	}

	private AVLNode<T> addNodeRecursivo(AVLNode<T> nodoraiz, T clave) {
		if (nodoraiz.getInfo().compareTo(clave) > 0) {// si la raiz es mayor que la clave, busca por la izquierda
			if (nodoraiz.getLeft() != null) {// si tiene un hijo izquierdo
				nodoraiz.setLeft(addNodeRecursivo(nodoraiz.getLeft(), clave));// asigna por la izquierda un nuevo nodo
																				// recursivamente
				return updateAndBalanceIfNecesary(nodoraiz);// devuelve la raiz actualizada
			} else {// si no tiene hijo izquierdo
				nodoraiz.setLeft(new AVLNode<T>(clave));// se asigna el nodo al hijo izquierdo
				return updateAndBalanceIfNecesary(nodoraiz);// devuelve la raiz actualizada
			}
		} else if (nodoraiz.getInfo().compareTo(clave) < 0) {// si la raiz es menor que la clave, busca por la derecha
			if (nodoraiz.getRight() != null) {// si tiene un hijo derecho
				nodoraiz.setRight(addNodeRecursivo(nodoraiz.getRight(), clave));// se asigna por la derecha un nuevo
																				// nodo recursivamente
				return updateAndBalanceIfNecesary(nodoraiz);// devuelve la ra?z actualizada
			} else {
				nodoraiz.setRight(new AVLNode<T>(clave));// se asigna un nuevo nodo hijo derecho
				return updateAndBalanceIfNecesary(nodoraiz);// devuelve la raiz actualizada
			}
		} else // si ya existiese el nodo, no se inserta y devuelve null
			return null;
	}

	/**
	 * M?todo privado que llama a una serie de m?todos privados que realizan las
	 * rotaciones del ?rbol para su correcta organizaci?n. Consigue un ?rbol
	 * perfectamente equilibrado
	 * 
	 * @param nodo
	 * @return node
	 */
	private AVLNode<T> updateAndBalanceIfNecesary(AVLNode<T> nodo) {
		nodo.updateBFHeight();
		if (nodo.getBF() == -2)// si el factor de balance es -2
			if (nodo.getLeft().getBF() == 1)// si adem?s el factor de balance del hijo izquierdo es 1
				nodo = doubleLeftRotation(nodo);// se hace una rotaci?n doble a la izquierda
			else // si es -1 o cero
				nodo = singleLeftRotation(nodo);// se hace una rotaci?n simple a la izquierda
		else if (nodo.getBF() == 2)// si el factor de balance es 2
			if (nodo.getRight().getBF() == -1)// si adem?s el factor de balance del hijo izquierdo es -1
				nodo = doubleRightRotation(nodo);// se hace una rotaci?n doble a la derecha
			else // si es 1 o cero
				nodo = singleRightRotation(nodo);// se hace una rotaci?n simple a la derecha
		return nodo;// se devuelve el nodo equilibrado
	}

	/*
	 * Rotaci?n simple derecha
	 */
	private AVLNode<T> singleRightRotation(AVLNode<T> nodo) {
		AVLNode<T> aux = nodo.getRight();// se usa un nodo auxiliar que guarda el sub?rbol derecho del nodo
		nodo.setRight(aux.getLeft());// se asigna al nodo el sub?rbol izquierdo de aux
		nodo.updateBFHeight();// se actualiza el factor de balance del nodo
		aux.setLeft(nodo);// se asigna al sub?rbol izquierdo de aux el nodo
		aux.updateBFHeight();// se actualiza el factor de balance de aux
		return aux;// se devuelve el nodo auxiliar equilibrado
	}

	/*
	 * Rotaci?n doble derecha (dos rotaciones simples)
	 */
	private AVLNode<T> doubleRightRotation(AVLNode<T> nodo) {
		// Primero, hacemos una rotacion simple a la izquierda sobre el sub?rbol derecho
		// del nodo
		nodo.setRight(singleLeftRotation(nodo.getRight()));
		// Segundo, hacemos una rotaci?n simple a la derecha sobre el nodo y lo
		// devolvemos
		return singleRightRotation(nodo);
	}

	/*
	 * Rotaci?n simple a la izquierda
	 */
	private AVLNode<T> singleLeftRotation(AVLNode<T> nodo) {
		AVLNode<T> aux = nodo.getLeft();// Creamos un nodo auxiliar y le asignamos el sub?rbol izquierdo del nodo
		nodo.setLeft(aux.getRight());// le asignamos al hijo izquierdo del nodo el sub?rbol derecho de aux
		nodo.updateBFHeight();// actualizamos el factor de balance del nodo
		aux.setRight(nodo);// le asignamos al hijo derecho de aux el nodo
		aux.updateBFHeight();// actualizamos el factor de balance de aux
		return aux;// lo devolvemos
	}

	/*
	 * Rotaci?n doble a la izquierda
	 */
	private AVLNode<T> doubleLeftRotation(AVLNode<T> nodo) {
		// Primero, hacemos una rotacion simple a la derecha sobre el sub?rbol izquierdo
		// del nodo
		nodo.setLeft(singleRightRotation(nodo.getLeft()));
		// Segundo, hacemos una rotaci?n simple a la izquierda sobre el nodo y lo
		// devolvemos
		return singleLeftRotation(nodo);
	}

	/*
	 * Devuelve la cadena preOrden -> raiz, izquierda, derecha
	 */
	public String preOrder() {
		String cadena = recorridoPreOrderRecursivo(this.raiz);
		return cadena.substring(0, cadena.length() - 1);
	}

	private String recorridoPreOrderRecursivo(AVLNode<T> nodoraiz) {
		if (nodoraiz == null)// si la ra?z es nula devolver? la cadena vac?a
			return "";
		String cadena = nodoraiz.toString();
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

	private String recorridoPostOrderRecursivo(AVLNode<T> nodoraiz) {
		String cadena = "";
		if (nodoraiz != null) {
			cadena += recorridoPostOrderRecursivo(nodoraiz.getLeft());
			cadena += recorridoPostOrderRecursivo(nodoraiz.getRight());
			cadena += nodoraiz.toString() + "\t";
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

	private String recorridoInOrderRecursivo(AVLNode<T> nodoraiz) {
		String cadena = "";
		if (nodoraiz != null) {
			cadena += recorridoInOrderRecursivo(nodoraiz.getLeft());
			cadena += nodoraiz.toString() + "\t";
			cadena += recorridoInOrderRecursivo(nodoraiz.getRight());
		}
		return cadena;
	}

	/*
	 * Si la clave o la ra?z es null devuelve -2 Si la clave no existe devuelve -1
	 * 
	 * Sino invoca a un m?todo recursivo con el nodo a partir del cual queremos
	 * borrar y la clave a borrar. Se asigna a la ra?z lo que devuelve el m?todo
	 * recursivo y devuelve 0.
	 * 
	 * Este m?todo busca la clave por la derecha o por la izquierda, dependiendo de
	 * si la clave a borrar es mayor o menor que el nodo a partir del cual se quiere
	 * borrar. Cuando lo encuentra lo borra y devuelve el nodo actualizado.
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

	private AVLNode<T> removeNodeRecursivo(AVLNode<T> nodoraiz, T clave) {
		if (nodoraiz.getInfo().compareTo(clave) > 0) {// si la raiz es mayor que la clave, busca por la izquierda
			nodoraiz.setLeft(removeNodeRecursivo(nodoraiz.getLeft(), clave));// le asigna a la izquierda de la raiz el
																				// sub?rbol actualizado
			return updateAndBalanceIfNecesary(nodoraiz);// se devuelve el nodo actualizado
		} else if (nodoraiz.getInfo().compareTo(clave) < 0) {// si la raiz es menor que la clave, busca por la derecha
			nodoraiz.setRight(removeNodeRecursivo(nodoraiz.getRight(), clave));// se le asigna a la derecha de la ra?z
																				// el sub?rbol actualizado
			return updateAndBalanceIfNecesary(nodoraiz);// se devuelve el nodo actualizado
		} else {// se encuentra el nodo a borrar
			if (nodoraiz.getLeft() == null && nodoraiz.getRight() == null)// no tiene hijos
				return null;
			else if (nodoraiz.getLeft() != null && nodoraiz.getRight() == null) // tiene s?lo un hijo izquierdo
				return nodoraiz.getLeft();
			else if (nodoraiz.getLeft() == null && nodoraiz.getRight() != null)// s?lo tiene un hijo derecho
				return nodoraiz.getRight();
			else {// tiene dos hijos
				AVLNode<T> nodomax = searchMaxClave(nodoraiz.getLeft());// el hijo mayor
				nodoraiz.setLeft(removeNodeRecursivo(nodoraiz.getLeft(), nodomax.getInfo()));
				nodoraiz.setInfo(nodomax.getInfo());
				return updateAndBalanceIfNecesary(nodoraiz);
			}
		}

	}

	/*
	 * M?todo que busca el hijo mayor
	 */
	private AVLNode<T> searchMaxClave(AVLNode<T> nodoraiz) {
		if (nodoraiz.getRight() != null) {
			return searchMaxClave(nodoraiz.getRight());
		} else {
			return nodoraiz;
		}
	}

	// M?todos extra

	/*
	 * M?todo que devuelve el padre de un nodo. Devuelve null si el par?metro pasado
	 * es null. Sino devuelve al padre con un m?todo recursivo
	 */
	public AVLNode<T> padreDe(T clave) {
		if (clave == null)
			return null;
		else {
			return padreDeRecursivo(this.raiz, clave);
		}
	}

	private AVLNode<T> padreDeRecursivo(AVLNode<T> nodoraiz, T clave) {
		if (nodoraiz.getInfo().compareTo(clave) > 0) {// si la raiz es mayor que la clave
			if (nodoraiz.getLeft() != null && nodoraiz.getLeft().getInfo().equals(clave))// si el hijo izquierdo no es
																							// null y es el que buscamos
				return nodoraiz;// devolvemos la raiz, que ser? el padre
			else
				return padreDeRecursivo(nodoraiz.getLeft(), clave);// si no buscamos por la izquierda
		} else if (nodoraiz.getInfo().compareTo(clave) < 0) {// si la ra?z es menor que la clave
			if (nodoraiz.getRight() != null && nodoraiz.getRight().getInfo().equals(clave))// si el hijo derecho no es
																							// null y es igual que la
																							// clave
				return nodoraiz;// devolvemos al padre
			else
				return padreDeRecursivo(nodoraiz.getRight(), clave);// sino buscamos por la derecha
		} else
			return null;// si no lo encuentra devolver? null
	}

	/*
	 * M?todo que cuenta el n?mero de aristas desde un nodo inicial hasta un nodo
	 * final Si el nodo de inicio o el nodo destino o la raiz son null o los nodos
	 * son el mismo, devuelve 0 Sino devuelve su distancia
	 */
	public int numAristas(T source, T target) {
		if (source == null || target == null || this.raiz == null || source.equals(target))
			return 0;
		else {
			AVLNode<T> inicio = searchNode(source);
			AVLNode<T> destino = searchNode(target);
			return cuentaAltura(inicio, destino);
		}
	}

	private int cuentaAltura(AVLNode<T> source, AVLNode<T> target) {
		if (source.getInfo().compareTo(target.getInfo()) > 0) // si el origen es mayor que el destino
			return cuentaAltura(source.getLeft(), target) + 1;// busca por la izquierda sumando 1 cada vez
		else if (source.getInfo().compareTo(target.getInfo()) < 0)// si el origen es menor que el destino
			return cuentaAltura(source.getRight(), target) + 1;// busca por la derecha sumando 1 cada vez
		else // si son el mismo, encontrado
			return 0;// devuelve 0
	}

}
