package shortestpathwithturnrestrictions.utility;

/**
 * The Queue class represents a first-in-first-out (FIFO) queue of objects. 
 * And this class can look at the object which has the highest (or lowest or so) value.
 * So every object onto the queue must be comparable to each other.
 * @param <E>
 */
public interface QueueInterface<E extends Comparable<E>> {

	/**
	 * Adds an item into the tail of this queue. 
	 * If the Element e is null, throws IllegalArgumentException.
	 * @param e
	 * @throws IllegalArgumentException
	 */
	public void enqueue(E e);
	
	/**
	 * Removes the object at the head of this queue and returns that object as the value of this function.
	 * If the queue is empty, throws java.util.NoSuchElementException.
	 * @return
	 * @throws java.util.NoSuchElementException
	 */
	public E dequeue();
	
	/**
	 * Looks at the object which has the median value among the all objects without removing it from the queue.
	 * Returns the object which has the value of <code>((size() / 2) + 1)</code>th lowest.
	 * <pre>
	 *  e.g.
	 *    When the queue has the following values (1, 1, 1, 2, 3)
	 *    this method returns 1 and doesn't remove that object. 
	 *
	 *    When the queue has the following values (2, 1, 4, 3)
	 *    this method returns 3 and doesn't remove that object. 
	 *
	 *    When the queue has the following values (2, 1, 2, 2, 6, 4, 2, 5)
	 *    this method returns 2 and doesn't remove that object. 
	 *
	 *    When the queue has the following values (7, 1, 3, 3, 5, 1, 2, 4, 3)
	 *    this method returns 3 and doesn't remove that object. 
	 * </pre>
	 * If the queue is empty, throws java.util.NoSuchElementException.
	 * @return 
	 * @throws java.util.NoSuchElementException
	 */
	public E peekMedian();
	
	/**
	 * Looks at the object which has the maximum value among the all objects without removing it from the queue.
	 * <pre>
	 *  e.g.
	 *    When the queue has the following values (2, 1, 2, 2, 6, 4, 2, 5)
	 *    this method returns 6 and doesn't remove that object. 
	 * </pre>
	 * If the queue is empty, throws java.util.NoSuchElementException.
	 * @return 
	 * @throws java.util.NoSuchElementException
	 */
	public E peekMaximum();
	
	/**
	 * Looks at the object which has the minimum value among the all objects without removing it from the queue.
	 * <pre>
	 *  e.g.
	 *    When the queue has the following values (2, 1, 2, 2, 6, 4, 2, 5)
	 *    this method returns 1 and doesn't remove that object. 
	 * </pre>
	 * If the queue is empty, throws java.util.NoSuchElementException.
	 * @return 
	 * @throws java.util.NoSuchElementException
	 */
	public E peekMinimum(); 
	
	/**
	 * Returns the number of objects in this queue.
	 * @return
	 */
	public int size();
}
