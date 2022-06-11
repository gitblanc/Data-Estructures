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

	// M�todos comunes
	protected int fHash(T elemento) {
		int pos = elemento.hashCode() % getSize();
		if (pos < 0)
			return pos + getSize();
		else
			return pos;
	}

	/*
	 * M�todo que averigua si un n�mero es primo o no suponiendo que este n�mero es
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
	 * M�todo que busca el siguiente n�mero primo al pasado como par�metro
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
	 * M�todo que busca el n�mero primo anterior al pasado como par�metro
	 */
	protected int previousPrimeNumber(int numero) {
		numero--;
		for (int i = numero; i > 3; i--) {
			if (isPositivePrime(i))
				return i;
		}
		return 3;// 3 es el primer n�mero primo
	}

}
