/**
 * 
 */
package grafo;

import java.text.DecimalFormat;

/**
 * @author UO285176
 *
 */
public class Graph<T> {

	private double[][] weights;// pesos de las aristas
	private boolean[][] edges;// matriz de aristas
	private T[] nodes;// Nodos:Lista de nodos
	private int size;// n?mero de v?rtices

	// Floyd costes(a) y caminos(p)
	private double[][] a;
	private T[][] p;

	// Recorrido en profundidad
	String cadena = "";

//-------------------GRAFOS B?SICO-------------------
	@SuppressWarnings("unchecked")
	public Graph(int dimension) {
		this.nodes = (T[]) new Object[dimension];
		this.edges = new boolean[dimension][dimension];
		this.weights = new double[dimension][dimension];
		this.size = 0;// no hay v?rtices
	}

	/*
	 * M?todo que devuelve el n?mero de v?rtices
	 */
	public int getSize() {
		return this.size;
	}

	/*
	 * Devuelve la matriz de pesos
	 */
	public double[][] getWeights() {
		return weights;
	}

	/*
	 * Devuelve la matriz de aristas
	 */
	public boolean[][] getEdges() {
		return edges;
	}

	/*
	 * Devuelve la posici?n del nodo pasado como par?metro dentro del vector de
	 * nodos y -1 si el nodo no existe
	 */
	protected int getNode(T node) {
		for (int i = 0; i < nodes.length; i++) {
			if (node.equals(this.nodes[i]))
				return i;
		}
		return -1;
	}

	/*
	 * Indica si existe (true) o no (false) el nodo en el grafo Tambi?n se comprueba
	 * que el nodo no sea null
	 */
	public boolean existsNode(T node) {
		if (node == null)
			return false;
		if (getNode(node) != -1)
			return true;
		return false;
	}

	/*
	 * Inserta un nuevo nodo que se le pasa como par?metro Si lo inserta devuelve 0
	 * Si ya existe y adem?s no cabe devuelve -3 Si ya existe pero s? cabr?a, no lo
	 * inserta y devuelve -1 Si no existe pero no cabe, no lo inserta y devuelve -2
	 * Si el nodo a insertar no es v?lido devuelve -4 en cualquier caso
	 */
	public int addNode(T node) {
		if (existsNode(node) && getSize() == this.nodes.length)
			return -3;
		else if (existsNode(node) && getSize() < this.nodes.length)
			return -1;
		else if (!existsNode(node) && getSize() == this.nodes.length)
			return -2;
		else if (getSize() < this.nodes.length && !existsNode(node)) {
			nodes[size] = node;
			for (int i = 0; i < getSize(); i++) {
				this.edges[size][i] = false;
				this.edges[i][size] = false;
				this.weights[size][i] = 0;
				this.weights[i][size] = 0;
			}
			size++;
			return 0;
		} else
			return -4;

	}

	/*
	 * Devuelve true si existe una arista entre los nodos origen y destino y false
	 * en caso contrario o no existen los nodos
	 */
	public boolean existEdge(T source, T target) {
		int i = getNode(source);
		int j = getNode(target);
		if (i >= 0 && j >= 0)
			return this.edges[i][j];
		else
			return false;
	}

	/*
	 * Devuelve el peso de la arista que une el nodo origen y el nodo destino.
	 * Devuelve -1 si no existe origen Devuelve -2 si no existe destino Devuelve -3
	 * si no existen ambos Devuelve -4 si no existe la arista
	 */
	public double getEdge(T source, T target) {
		if (!existsNode(source) && !existsNode(target))
			return -3;
		else if (!existsNode(source))
			return -1;
		else if (!existsNode(target))
			return -2;
		else if (!existEdge(source, target))
			return -4;
		else {
			int i = getNode(source);
			int j = getNode(target);
			return this.weights[i][j];// Devuelve el peso de la arista que une el nodo origen y el nodo destino.
		}
	}

	/*
	 * Inserta una arista con el peso indicado (mayor que 0) entre dos nodos
	 * Devuelve 0 si la inserta Devuelve -1 si no existe el nodo origen Devuelve -2
	 * si no existe el nodo destino Devuelve -3 si no existen ambos Devuelve -4 si
	 * ya existe la arista Devuelve -8 si el peso no es v?lido
	 */
	public int addEdge(T source, T target, double weight) {
		if (weight <= 0)
			return -8;
		else if (getEdge(source, target) == -3)
			return -3;
		else if (getEdge(source, target) == -1)
			return -1;
		else if (getEdge(source, target) == -2)
			return -2;
		else if (existEdge(source, target))
			return -4;
		else {
			int i = getNode(source);
			int j = getNode(target);
			this.edges[i][j] = true;
			this.weights[i][j] = weight;
			return 0;
		}
	}

	/*
	 * Borra una arista del grafo que conecta dos nodos. Devuelve -1 si no existe el
	 * nodo origen Devuelve -2 si no existe el destino Devuelve -3 si no existen
	 * ambos Devuelve -4 si no existe la arista Devuelve 0 si la borra
	 */
	public int removeEdge(T source, T target) {
		if (getEdge(source, target) == -3)
			return -3;
		else if (getEdge(source, target) == -1)
			return -1;
		else if (getEdge(source, target) == -2)
			return -2;
		else if (getEdge(source, target) == -4)
			return -4;
		else {
			int i = getNode(source);
			int j = getNode(target);
			this.edges[i][j] = false;
			this.weights[i][j] = 0;
			return 0;
		}
	}

	/*
	 * Devuelve 0 si borra el nodo y -1 en caso contrario
	 */
	public int removeNode(T node) {
		int i = getNode(node);
		if (i >= 0) {
			size--;
			if (i != size + 1)// no es el ?ltimo nodo
				nodes[i] = nodes[size];

			for (int j = 0; j <= getSize(); j++) {
				this.edges[i][j] = edges[size][j];
				this.edges[j][i] = edges[j][size];
				this.weights[i][j] = weights[size][j];
				this.weights[j][i] = weights[j][size];
			}

			this.edges[i][i] = edges[size][size];// elimina el codo
			this.weights[i][i] = weights[size][size];
			return 0;
		}
		return -1;
	}

	/*
	 * toString()
	 */
	public String toString() {
		DecimalFormat df = new DecimalFormat("#.##");
		String cadena = "";
		cadena += "NODOS\n";
		for (int i = 0; i < size; i++) {
			cadena += nodes[i].toString() + "\t";
		}
		cadena += "\n\nARISTAS\n";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (edges[i][j])
					cadena += "T\t";
				else
					cadena += "F\t";
			}
			cadena += "\n";
		}
		cadena += "\nPESOS\n";
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cadena += (edges[i][j] ? df.format(weights[i][j]) : "-") + "\t";
			}
			cadena += "\n";
		}
		return cadena;
	}
//-------------------GRAFOS B?SICO-------------------

//DIJKSTRA -> Complejidad - O(n^2)

	/*
	 * Algoritmo que calcula la ruta m?s barata desde un nodo origen dado hasta el
	 * sumidero
	 */
	public double[] dijkstra(T origen) {
		if (!existsNode(origen))
			return null;
		double[] costes = inicializaCostes(origen);// matriz de costes
		T[] caminos = inicializaCaminos(origen);// matriz de caminos
		boolean[] visitados = new boolean[this.size];// matriz de nodos visitados
		visitados[getNode(origen)] = true;// ponemos a true el nodo origen
		int w = getPivote(visitados, costes);// se coge el nodo con menor coste y no alcanzado

		while (w != -1) {// mientras que a?n queden nodos sin visitar
			visitados[w] = true;
			for (int m = 0; m < this.size; m++) {
				if (!visitados[m] && existEdge(nodes[w], nodes[m])) {// si no ha sido visitado y existe arista
					if ((costes[w] + this.weights[w][m]) < costes[m]) {// si el nuevo coste es menor se sustituye
						costes[m] = costes[w] + this.weights[w][m];
						caminos[m] = nodes[w];
					}
				}
			}
			w = getPivote(visitados, costes);
		}
		return costes;
	}

	/*
	 * M?todo que devuelve el pivote para dijkstra
	 */
	private int getPivote(boolean[] s, double[] costes) {
		double masbarato = Double.POSITIVE_INFINITY;
		int posicion = -1;// devuelve -1 cuando todos los nodos est?n visitados
		for (int i = 0; i < this.size; i++) {
			if (costes[i] < masbarato && !s[i]) {
				posicion = i;
				masbarato = costes[i];
			}
		}
		return posicion;
	}

	/*
	 * Inicializa el vector de costes. Ir de un nodo a s? mismo cuesta 0, si ya
	 * existe la arista se corresponde con su peso y si no existe es infinito.
	 */
	public double[] inicializaCostes(T origen) {
		double[] costes = new double[this.size];
		for (int i = 0; i < getSize(); i++) {
			if (origen.equals(nodes[i]))
				costes[i] = 0;
			else if (existEdge(origen, nodes[i]))
				costes[i] = getEdge(origen, nodes[i]);
			else {
				costes[i] = Double.POSITIVE_INFINITY;// no hay camino directo
			}
		}
		return costes;
	}

	/*
	 * Si el destino es igual al origen o existe la arista origen y destino, el
	 * camino es el nodo origen, sino es null
	 */
	public T[] inicializaCaminos(T origen) {
		@SuppressWarnings("unchecked")
		T[] caminos = (T[]) new Object[this.size];
		for (int i = 0; i < this.size; i++) {
			if (origen.equals(nodes[i]) || existEdge(origen, nodes[i]))
				caminos[i] = origen;
		}
		return caminos;
	}

//FLOYD -> Complejidad O(n^3)

	/*
	 * Implementa el algoritmo de Floyd. Devuelve 0 si genera las matrices A(costes)
	 * y P(caminos) y -1 en caso contrario
	 */
	public int floyd() {
		if (this.size <= 0)
			return -1;
		double[][] matrizA = inicializaFloydA();
		T[][] matrizP = inicializaFloydP();
		for (int pivote = 0; pivote < this.size; pivote++) {
			for (int source = 0; source < this.size; source++) {
				for (int target = 0; target < this.size; target++) {
					if ((matrizA[source][pivote] + matrizA[pivote][target]) < matrizA[source][target]) {
						matrizA[source][target] = matrizA[source][pivote] + matrizA[pivote][target];
						matrizP[source][target] = this.nodes[pivote];
					}
				}
			}
		}
		this.a = matrizA;
		this.p = matrizP;
		return 0;
	}

	/*
	 * M?todo que inicializa la matriz P (caminos) con todos ellos a null
	 */
	private T[][] inicializaFloydP() {
		@SuppressWarnings("unchecked")
		T[][] p = (T[][]) new Object[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				p[i][j] = null;
			}
		}
		return p;
	}

	/*
	 * M?todo que inicializa la matriz A (costes) colocando un 0 en la diagonal
	 * principal, el peso si es mayor que 0 o infinito en otro caso
	 */
	private double[][] inicializaFloydA() {
		double[][] matrizA = new double[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				if (i == j)// la diagonal
					matrizA[i][j] = 0;
				else if (this.weights[i][j] > 0)
					matrizA[i][j] = this.weights[i][j];
				else
					matrizA[i][j] = Double.POSITIVE_INFINITY;
			}
		}
		return matrizA;
	}

	/*
	 * M?todo que devuelve la matriz A o de costes
	 */
	protected double[][] getFloydA() {
		return this.a;

	}

	/*
	 * M?todo que devuelve la matriz P o de caminos
	 */
	protected T[][] getFloydP() {
		return this.p;
	}

	/*
	 * M?todo que devuelve el coste m?nimo entre el nodo origen y destino Si el
	 * origen o el destino no existen o son nulos devuelve infinito
	 */
	protected double minCostPath(T origen, T destino) {
		if (destino == null || origen == null || !existsNode(origen) || !existsNode(destino))
			return Double.POSITIVE_INFINITY;
		else if (this.a == null)
			floyd();
		return this.a[getNode(origen)][getNode(destino)];// devuelve el coste m?nimo de ida
	}

	/*
	 * M?todo que devuelve una cadena con el camino de coste m?nimo entre el nodo
	 * origen y el nodo destino Si el nodo origen es igual al destino, la cadena
	 * tendr? tan s?lo el origen Si no hay camino entre el origen y el destino
	 * tendr? el formato "infinity" En otro caso tendr? un formato espec?fico
	 */
	public String path(T origen, T destino) {
		String cadena = "";
		if (origen.equals(destino))
			cadena = origen.toString();
		else if (minCostPath(origen, destino) == Double.POSITIVE_INFINITY)
			cadena = origen.toString() + "\t" + Double.POSITIVE_INFINITY + "\t" + destino.toString();
		else
			cadena += origen.toString() + pathRecursivo(origen, destino) + destino.toString();

		return cadena;
	}

	/*
	 * M?todo privado que se autollama X veces hasta que haya un coste en la arista
	 * entre nodo origen y destino, habiendo un camino directo
	 */
	private String pathRecursivo(T origen, T destino) {
		String cadena = "";
		if (this.p == null)// si la matriz de caminos es null se llama a floyd
			floyd();
		T intermedio = this.p[getNode(origen)][getNode(destino)];
		if (intermedio != null && origen != intermedio && destino != intermedio)
			cadena += pathRecursivo(origen, intermedio) + intermedio.toString() + pathRecursivo(intermedio, destino);
		else// si intermedio es null llama a minCostPath porque al ser p null hay un coste
			// en esa arista (hay un camino directo)
			cadena += "\t(" + minCostPath(origen, destino) + ")\t";

		return cadena;
	}

	/*
	 * toString() para algoritmo floyd()
	 */
	public String floydToString() {
		int numNodes = size;
		String cadena = " ";
		DecimalFormat df = new DecimalFormat("#.##");

		double[][] aFloyd = getFloydA();
		if (aFloyd != null) {
			cadena += "\nAFloyd\n";
			for (int i = 0; i < numNodes; i++) {
				for (int j = 0; j < numNodes; j++) {
					cadena += df.format(aFloyd[i][j]) + "\t";
				}
				cadena += "\n";
			}
		}

		T[][] pFloyd = getFloydP();
		if (pFloyd != null) {
			cadena += "\nPFloyd\n";
			for (int i = 0; i < numNodes; i++) {
				for (int j = 0; j < numNodes; j++) {
					if (pFloyd[i][j] == null)
						cadena += "-" + "\t";

					else
						cadena += pFloyd[i][j].toString() + "\t";
				}
				cadena += "\n";
			}
		}
		return cadena;

	}

//Recorrido en profundidad

	/*
	 * M?todo que lanza el recorrido en profundidad de un grafo desde un nodo
	 * determinado. Si no existe el nodo par?metro devuelve una cadena vac?a.
	 */
	public String recorridoProfundidad(T nodo) {
		if (getNode(nodo) == -1 || nodo == null) {
			return "";
		}
		boolean[] visitados = new boolean[this.size];
		recursivoProfundidad(nodo, visitados);
		return cadena;
	}

	/*
	 * M?todo privado que pone el nodo pasado como par?metro a True(visitado) y
	 * comprueba si existe caminos para el resto de nodos que no hayan sido
	 * visitados. Va guardando el recorrido en un String que finalmente devuelve
	 */
	private void recursivoProfundidad(T source, boolean[] visited) {
		visited[getNode(source)] = true;
		cadena += source.toString() + "\t";
		// for each node w accessible from v do
		for (int i = 0; i < this.size; i++) {
			if (!visited[i] && existEdge(source, this.nodes[i]))
				recursivoProfundidad(nodes[i], visited);
		}
	}

}