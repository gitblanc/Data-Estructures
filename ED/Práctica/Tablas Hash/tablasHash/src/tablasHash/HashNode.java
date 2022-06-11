/**
 * 
 */
package tablasHash;

/**
 * @author UO285176
 *
 */
public class HashNode<T> {
	// Estados posibles del nodo
	public static final int BORRADO = -1;
	public static final int VACIO = 0;
	public static final int LLENO = 1;

	private T info;// contenido de un elemento de la tabla hash
	private int estado;// estado del elemento de la tabla hash -> borrado, lleno o vacío

	public HashNode() {
		this.info = null;
		this.estado = VACIO;
	}

	public T getInfo() {
		return this.info;
	}

	public void setInfo(T elemento) {
		this.info = elemento;
		this.estado = LLENO;
	}

	public void remove() {
		// this.info = null; no se hace porque es borrado perezoso
		this.estado = BORRADO;
	}

	public int getStatus() {
		return this.estado;
	}

	public String toString() {
		StringBuilder cadena = new StringBuilder("{");
		switch (getStatus()) {
		case LLENO:
			cadena.append(info.toString());
			break;
		case VACIO:
			cadena.append("_E_");
			break;
		case BORRADO:
			cadena.append("_D_");
		}
		cadena.append("}");
		return cadena.toString();
	}

}
