/**
 * 
 */
package tablasHash;

/**
 * @author UO285176
 *
 */
public abstract class AbstractHash<T> {

	abstract public int getNumOfElems();

	abstract public int getSize();

	abstract public int add(T elemento);

	abstract public T find(T elemento);

	abstract public int remove(T elemento);

	abstract public String toString();

	// Métodos comunes
	protected int fHash(T elemento) {
		int pos = elemento.hashCode() % getSize();
		if (pos < 0)
			return pos + getSize();
		else
			return pos;
	}

	/*
	 * Método que averigua si un número es primo o no suponiendo que este número es
	 * positivo
	 */
	protected boolean isPositivePrime(int numero) {
		for (int i = 2; i < numero; i++) {
			if (numero % i == 0)
				return false;
		}
		return true;
	}

	/*
	 * Método que busca el siguiente número primo al pasado como parámetro
	 */
	protected int nextPrimeNumber(int numero) {
		numero++;
		while (true) {
			if (isPositivePrime(numero))
				return numero;
			numero++;
		}
	}

	/*
	 * Método que busca el número primo anterior al pasado como parámetro
	 */
	protected int previousPrimeNumber(int numero) {
		numero--;
		for (int i = numero; i > 3; i--) {
			if (isPositivePrime(i))
				return i;
		}
		return 3;// 3 es el primer número primo
	}

}
