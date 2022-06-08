/**
 * 
 */
package priorityQueues;

/**
 * @author UO285176
 *
 */
public interface PriorityQueue<T extends Comparable<T>> {

	public int add(T element);

	public T poll();// sacar de la cola

	public int remove(T element);

	public boolean isEmpty();

	public void clear();

	public int cambiarPrioridad(int pos, T element);

	@Override
	public String toString();
}
