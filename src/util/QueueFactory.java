package util;

import java.util.*;

/**
 * Factory class for queues. Changes made here will affect all queue based
 * search algorithms of this library.
 *
 */
public class QueueFactory {

	/**
	 * Returns a {@link LinkedList}.
	 */
	public static <E> Queue<E> createFifoQueue() {
		return new LinkedList<E>();
	}

	/**
	 * Returns a {@link LinkedList} which is extended by a {@link HashSet} for efficient containment checks. Elements
	 * are only added if they are not already present in the queue. Use only queue methods for access!
	 */
	public static <E> Queue<E> createFifoQueueNoDuplicates() {
		return new FifoQueueWithHashSet<E>();
	}

	private static class FifoQueueWithHashSet<E> extends LinkedList<E> implements Queue<E> {
		private HashSet<E> elements = new HashSet<>();

		@Override
		public boolean add(E e) {
			if (!elements.contains(e)) {
				elements.add(e);
				return super.add(e);
			}
			return false;
		}

		@Override
		public boolean offer(E e) {
			if (!elements.contains(e)) {
				elements.add(e);
				return super.offer(e);
			}
			return false;
		}

		@Override
		public E remove() {
			E result = super.remove();
			elements.remove(result);
			return result;
		}

		@Override
		public E poll() {
			E result = super.poll();
			if (result != null)
				elements.remove(result);
			return result;
		}

		@Override
		public boolean contains(Object e) {
			return elements.contains(e);
		}
	}
}
