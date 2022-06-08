/**
 * 
 */
package priorityQueues;

/**
 * @author UO285176. Implementa una cola de prioridad mediante un montículo de
 *         mínimos donde se admiten claves repetidas.
 */
public class BinaryHeapMin<T extends Comparable<T>> implements PriorityQueue<T> {

	private T[] monticulo;// vector de elementos de un determinado tamaño
	private int numElementos;// numero de elementos insertados en el vector

	/*
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public BinaryHeapMin(int n) {
		this.monticulo = (T[]) new Comparable[n];
		this.numElementos = 0;
	}

	/*
	 * Añade un elemento a la cola de prioridad. Devuelve -2 si la clave es null
	 * Devuelve -1 si no hay espacio en el vector Hace un filtrado ascendente y
	 * devuelve 0 si lo añade correctamente
	 */
	@Override
	public int add(T element) {
		if (element == null)
			return -2;
		else if (this.numElementos == this.monticulo.length)
			return -1;
		else {
			this.numElementos++;
			this.monticulo[this.numElementos - 1] = element;
			filtradoAscendente(this.numElementos - 1);
			return 0;
		}
	}

	/*
	 * Si el elemento insertado es menor que su padre entonces intercambiarlos. Esta
	 * operación se realiza mientras que el padre sea mayor que el hijo o elemento
	 * insertado y no hayamos alcanzado la raíz. El padre estará en la posición
	 * E[(i-1)/2] El hijo izquierdo se almacena en la posición 2*pos +1 El hijo
	 * derecho se almacena en la posición 2*pos + 2
	 */
	private void filtradoAscendente(int pos) {
		while (pos != 0) {// mientras que no se llegue al nodo más prioritario (el primero)
			int posPadre = Math.abs((pos - 1) / 2);
			int posHijoIzq = pos;
			T padre = this.monticulo[posPadre];
			T hijoIzq = this.monticulo[posHijoIzq];
			if (padre.compareTo(hijoIzq) > 0) {
				this.monticulo[posPadre] = hijoIzq;
				this.monticulo[posHijoIzq] = padre;
				filtradoAscendente(posPadre);
			}
			break;
		}
	}

	/*
	 * Valida que las posiciones de los hijos sean válidas (sea menor que el número
	 * de elementos). Devuelve 0 si las dos son válidas Si sólo es válida la del
	 * hijo izquierdo devuelve 1 Sino devuelve -1
	 */
	private int posicionesValidas(int posHijoIzq, int posHijoDcho) {
		if (posHijoIzq < this.numElementos && posHijoDcho < this.numElementos)
			return 0;
		else if (posHijoIzq < this.numElementos && posHijoDcho >= this.numElementos)
			return 1;
		else
			return -1;
	}

	/*
	 * Obtiene el elemento con mayor prioridad (el de la posición 0). Devuelve null
	 * si la cola está vacía. Si no está vacía asigna en la posición 0 el último
	 * elemento del montículo, realiza un filtrado descendente y devuelve el
	 * elemento sacado
	 */
	@Override
	public T poll() {
		if (isEmpty())
			return null;
		else {
			T element = this.monticulo[0];// elemento de mayor prioridad
			this.monticulo[0] = this.monticulo[this.numElementos - 1];
			this.monticulo[this.numElementos - 1] = null;
			this.numElementos--;
			filtradoDescendente(0);
			return element;
		}
	}

	/*
	 * Si el padre es mayor que uno de sus hijos y no es un nodo hoja, entonces se
	 * cambia. Si el padre tiene dos hijos y es mayor que los dos, entonces se
	 * intercambia por el menor de los hijos El hijo izquierdo estará en la posición
	 * 2*pos + 1 El hijo derecho estará en la posición 2*pos + 2
	 */
	private void filtradoDescendente(int pos) {
		int posHijoIzq = Math.abs(2 * pos + 1);
		int posHijoDcho = Math.abs(2 * pos + 2);
		int posiciones = posicionesValidas(posHijoIzq, posHijoDcho);// se comprueba la validez de las posiciones de los
																	// hijos
		while (posiciones != -1) {// mientras que aún queden nodos por recorrer
			int posPadre = pos;
			T padre = this.monticulo[posPadre];
			T hijoIzq = this.monticulo[posHijoIzq];
			T hijoDer = this.monticulo[posHijoDcho];

			if (posiciones == 0 && hijoIzq != null && hijoDer != null) {// si el nodo padre tiene dos hijos
				if (hijoIzq.compareTo(hijoDer) < 0) {// si el hijo izquierdo es menor que el hijo derecho
					if (padre.compareTo(hijoIzq) > 0) {// si el padre es mayor que el hijo izquierdo
						this.monticulo[posPadre] = hijoIzq;// el padre se intercambia por el hijo
						this.monticulo[posHijoIzq] = padre;// el hijo se intrercambia por el padre
						filtradoDescendente(posHijoIzq);// se realiza un filtrado descendente desde la nueva posición
					}
				} else if (hijoDer.compareTo(hijoIzq) < 0) {// si el hijo derecho es menor que el hijo izquierdo
					if (padre.compareTo(hijoDer) > 0) {// si el padre es mayor que el hijo derecho
						this.monticulo[posPadre] = hijoDer;// el padre se intercambia por el hijo
						this.monticulo[posHijoDcho] = padre;// el hijo se intrercambia por el padre
						filtradoDescendente(posHijoDcho);// se realiza un filtrado descendente desde la nueva posición
					}
					break;
				}
			} else if (posiciones == 1 && hijoIzq != null) {// si el padre tiene un hijo izquierdo
				if (padre.compareTo(hijoIzq) > 0) {// si el padre es mayor que el hijo izquierdo
					this.monticulo[posPadre] = hijoIzq;// el padre se intercambia por el hijo
					this.monticulo[posHijoIzq] = padre;// el hijo se intrercambia por el padre
					filtradoDescendente(posHijoIzq);// se realiza un filtrado descendente desde la nueva posición
				}
			}
			break;
		}
	}

	/*
	 * Elimina un elemento especificado de la cola de prioridad. Devuelve -2 si la
	 * clave es null o la cola está vacía Devuelve -1 si la clave no existe Sino lo
	 * elimina, disminuye el número de elementos, realiza un filtrado descendente y
	 * devuelve 0
	 */
	@Override
	public int remove(T element) {
		if (element == null || isEmpty())
			return -2;
		else {
			int pos = getPosElement(element);
			if (pos == -1)// si no existe
				return -1;
			else {
				this.monticulo[pos] = this.monticulo[this.numElementos - 1];
				this.numElementos--;
				filtradoDescendente(pos);
				return 0;
			}
		}
	}

	/*
	 * Devuelve la posición de un elemento en la cola
	 */
	private int getPosElement(T element) {
		for (int i = 0; i < this.numElementos; i++) {
			if (this.monticulo[i].equals(element))
				return i;
		}
		return -1;
	}

	/*
	 * Devuelve si el montículo está vacío o no
	 */
	@Override
	public boolean isEmpty() {
		return this.numElementos == 0;
	}

	/*
	 * Borra todos los elementos de la cola de prioridad
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.numElementos; i++) {
			this.monticulo[i] = null;
		}
		this.numElementos = 0;

	}

	/*
	 * Busca el elemento pasado como parámetro, lo compara con el de la posición
	 * pasada y en función de si es mayor o menor realiza un filtrado descendente o
	 * ascendente respectivamente. Si el montículo está vacío devuelve -1 Si la
	 * posición no es válida devuelve -2 Sino sustituye el elemento que había en la
	 * posición por el nuevo, realiza un filtrado ascendente o descendente y
	 * devuelve 0
	 */
	@Override
	public int cambiarPrioridad(int pos, T element) {
		if (isEmpty())
			return -1;
		else if (pos < 0 || pos >= this.numElementos)
			return -2;
		else {
			T original = this.monticulo[pos];
			this.monticulo[pos] = element;
			if (element.compareTo(original) > 0)// si el nuevo elemento es mayor que el original, realiza un filtrado
												// descendente
				filtradoDescendente(pos);
			else if (element.compareTo(original) < 0)// si el nuevo elemento es menor que el original, realiza un
														// filtrado ascendente
				filtradoAscendente(pos);
			return 0;
		}
	}

	public String toString() {
		String cadena = "";
		if (!isEmpty()) {
			for (int i = 0; i < numElementos - 1; i++) {
				cadena += this.monticulo[i] + "\t";
			}
			cadena += this.monticulo[numElementos - 1];
		}
		return cadena;
	}
}
