package common;

import java.util.*;
public class BlockingQueue<E> {
  /**
    It makes logical sense to use a linked list for a FIFO queue,
    although an ArrayList is usually more efficient for a short
    queue (on most VMs).
   */
  private final LinkedList<E> queue = new LinkedList<E>();
  /**
    This method pushes an object onto the end of the queue, and
    then notifies one of the waiting threads.
   */
  public void push(E o) {
    synchronized(queue) {
      queue.add(o);
      queue.notify();
    }
  }
  /**
    The pop operation blocks until either an object is returned
    or the thread is interrupted, in which case it throws an
    InterruptedException.
   */
  public E pop() throws InterruptedException {
    synchronized(queue) {
      while (queue.isEmpty()) {
        queue.wait();
      }
      return queue.removeFirst();
    }
  }
  /** Return the number of elements currently in the queue. */
  public int size() {
    return queue.size();
  }
}