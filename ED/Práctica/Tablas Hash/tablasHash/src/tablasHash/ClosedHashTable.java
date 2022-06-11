/**
 * 
 */
package tablasHash;

import java.lang.reflect.Array;

/**
 * @author UO285176 Implementa una tabla hash cerrada
 *
 */
public class ClosedHashTable<T> extends AbstractHash<T> {
	// Constantes para los factores de carga
	private static final double MIN_LF = 0.16;
	private static final double MAX_LF = 0.5;

	private int numElementos;// número de elementos insertados hasta el momento
	private HashNode<T> tabla[];// tabla hash
	private int tipoExploracion;// 1-Lineal, 2-Cuadrática, 3-Dispersión doble
	private double minlf;// factor de carga mínimo
	private double maxlf;// factor de carga máximo

	/*
	 * Constructor con dos parámetros para el tamaño y el tipo de exploración. Pone
	 * el número de elementos a 0, el tipo de exploración al pasado como parámetro,
	 * un tamaño primo, crea una tabla vacía con ese tamaño y establece los límites
	 * máximo y mínimo.
	 */
	@SuppressWarnings("unchecked")
	public ClosedHashTable(int tam, int tipo) {
		this.numElementos = 0;
		this.tipoExploracion = tipo;// suponemos que el tipo se pase correctamente
		if (!isPositivePrime(tam))
			tam = nextPrimeNumber(tam);
		this.tabla = (HashNode<T>[]) Array.newInstance(HashNode.class, tam);
		// Llenamos la tabla hash
		for (int i = 0; i < tam; i++) {
			this.tabla[i] = new HashNode<T>();
		}
		this.minlf = MIN_LF;
		this.maxlf = MAX_LF;
	}
	
	/**
	 * Constructor con cuatro parámetros para el tamaño, el tipo de exploración, el
	 * máximo y el mínimo. Pone el número de elementos a 0, el tipo de exploración
	 * al pasado como parámetro, un tamaño primo, crea una tabla vacía con ese
	 * tamaño y establece los límites máximo y mínimo con los parámetros máximo y
	 * mínimo.
	 * 
	 * @param tam
	 * @param tipo
	 * @param min
	 * @param max
	 */
	@SuppressWarnings("unchecked")
	public ClosedHashTable(int tam, int tipo, double min, double max) {
		this.numElementos = 0;
		this.tipoExploracion = tipo;
		if (!isPositivePrime(tam)) {
			tam = nextPrimeNumber(tam);
		}
		this.tabla = (HashNode<T>[]) Array.newInstance(HashNode.class, tam);
		for (int i = 0; i < tam; i++) {
			tabla[i] = new HashNode<T>();
		}
		this.minlf = min;
		this.maxlf = max;
	}

	/*
	 * Devuelve el número de elementos
	 */
	@Override
	public int getNumOfElems() {
		return this.numElementos;
	}

	/*
	 * Método que devuelve la longitud de la tabla hash
	 */
	@Override
	public int getSize() {
		return this.tabla.length;
	}

	/*
	 * Añade un elemento a la tabla hash Si es null, devuelve -2 Si no cabe o ya
	 * existe, devuelve -1 Si lo inserta correctamente devuelve 0 No se admiten
	 * elementos repetidos
	 */
	@Override
	public int add(T elemento) {
		if (elemento == null)
			return -2;
		else if (find(elemento) != null)
			return -1;
		else {
			int pos = fHash(elemento);// obtenemos la posición del elemento
			int intento = 1;

			// Mientras que esa posición esté ocupada y llevemos menos intentos que el
			// tamaño de la tabla
			while (tabla[pos].getStatus() == HashNode.LLENO && intento < getSize()) {
				if (tipoExploracion == 1)// si es exploración lineal
					pos = (fHash(elemento) + intento) % getSize();
				else if (tipoExploracion == 2)// si es exploración cuadrática
					pos = (fHash(elemento) + (intento * intento)) % getSize();
				intento++;
			}
			if (pos == getSize())// si la posición se sale del tamaño de la tabla (no cabe), devuelve -1
				return -1;
			this.tabla[pos].setInfo(elemento);// guardamos el elemento
			this.numElementos++;
			if (calculateFactorCarga() >= MAX_LF)// si el factor de carga supera el máximo permitido, hacemos una
													// redispersión
				redispersion();
			return 0;// devolvemos 0;
		}
	}

	/*
	 * Realiza una redispersión de la tabla (multiplica su tamaño por 2 y reordena
	 * los elementos)
	 */
	@SuppressWarnings("unchecked")
	private void redispersion() {
		HashNode<T>[] aux = this.tabla;// se hace una copia de la tabla
		int tam = getSize() * 2;// calculamos el nuevo tamaño
		if (!isPositivePrime(tam))// comprobamos que el nuevo tamaño sea primo, y sino, lo recalculamos
			tam = nextPrimeNumber(tam);
		this.tabla = (HashNode<T>[]) Array.newInstance(HashNode.class, tam);// se amplía la tabla
		this.numElementos = 0;
		for (int i = 0; i < getSize(); i++) {
			this.tabla[i] = new HashNode<T>();
		}

		for (int k = 0; k < aux.length; k++) {
			if (aux[k].getStatus() == HashNode.LLENO)
				add(aux[k].getInfo());
		}
	}

	/*
	 * Calcula el factor de carga
	 */
	private double calculateFactorCarga() {
		return this.numElementos / (float) (getSize());
	}

	/*
	 * Busca un elemento en la tabla y lo devuelve. Si no lo encuentra o es null
	 * devuelve null
	 */
	@Override
	public T find(T elemento) {
		if (elemento == null)
			return null;
		else {
			int pos = fHash(elemento);// calculamos su posición
			int intento = 0;
			// Mientras que la posición sea válida y el intento sea menor que el tamaño de
			// la tabla
			while (pos < getSize() && intento < getSize()) {
				if (this.tabla[pos].getStatus() == HashNode.LLENO) {// si esa posición contiene un elemento
					if (this.tabla[pos].getInfo().equals(elemento)) // si ese elemento coincide con el parámetro
						return elemento;// devuelve el elemento
				} else if (this.tabla[pos].getStatus() == HashNode.BORRADO) {// si se ha borrado el elemento de esa
																				// posición
					if (this.tabla[pos].getInfo().equals(elemento))// pero el elemento coincidía
						return null;// devuelve null
				}
				if (tipoExploracion == 1)
					pos = (fHash(elemento) + intento) % getSize();
				else if (tipoExploracion == 2)
					pos = (fHash(elemento) + (intento * intento)) % getSize();
				intento++;
			}
			return null;// si no lo encuentra duevuelve null;
		}

	}

	/*
	 * Elimina un elemento de la tabla hash. Si es null, devuelve -2 Si no lo
	 * encuentra devuelve -1 Sino pone su estado a BORRADO(borrado perezoso), no lo
	 * elimina y devuelve 0
	 */
	@Override
	public int remove(T elemento) {
		if (elemento == null)
			return -2;
		else if (find(elemento) == null)
			return -1;
		else {
			int pos = fHash(elemento);
			int intento = 1;
			// mientras que aún queden elementos
			while (intento < getSize()) {
				if (this.tabla[pos].getStatus() == HashNode.LLENO) {// si hay un elemento en la posición
					if (this.tabla[pos].getInfo().equals(elemento)) {// si son el mismo elemento
						this.tabla[pos].remove();
						this.numElementos--;
						if (calculateFactorCarga() < MIN_LF)// si el factor de carga es menor que el mínimo
							redispersionInversa();
						return 0;
					}
				} else if (this.tabla[pos].getStatus() == HashNode.BORRADO) {// si el elemento ha sido borrado
					if (this.tabla[pos].getInfo().equals(elemento))// si son el mismo elemento
						return -1;// devuelve -1;
				}

				if (this.tipoExploracion == 1)
					pos = (fHash(elemento) + intento) % getSize();
				else if (this.tipoExploracion == 2)
					pos = (fHash(elemento) + (intento * intento)) % getSize();

				intento++;
			}
			return -1;// si no lo encuentra devuelve -1
		}
	}

	/*
	 * Calcula la redispersión inversa de la tabla (reduce su tamaño en 2 y reordena
	 * los elementos)
	 */
	@SuppressWarnings("unchecked")
	private void redispersionInversa() {
		HashNode<T>[] aux = this.tabla;
		int tam = getSize() / 2;
		if (!isPositivePrime(tam))
			tam = previousPrimeNumber(tam);
		this.tabla = (HashNode<T>[]) Array.newInstance(HashNode.class, tam);
		this.numElementos = 0;
		for (int i = 0; i < getSize(); i++)
			this.tabla[i] = new HashNode<T>();
		for (int k = 0; k < aux.length; k++) {
			if (aux[k].getStatus() == HashNode.LLENO)
				add(aux[k].getInfo());
		}

	}

	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		for (int i = 0; i < getSize(); i++) {
			cadena.append(this.tabla[i].toString());
			cadena.append(";");
		}
		cadena.append("[Size: ");
		cadena.append(getSize());
		cadena.append(" Num.Elems.: ");
		cadena.append(getNumOfElems());
		cadena.append("]");
		return cadena.toString();
	}

}
