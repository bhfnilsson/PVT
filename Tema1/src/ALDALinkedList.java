import java.util.*;

/**
 * Filip Nilsson 890925-3570
 * fini8850@student.su.se
 */

public class ALDALinkedList<E> implements ALDAList<E>
{
	// The node class
	private static class Node<E>
	{
		E element;
		Node<E> next;
		
		public Node (E element){
			this.element = element;
		}
	}
	
	private Node<E> first;
	private Node<E> last;
	private int size;
	
	// Add the element to the end of the list
	public void add(E element)
	{
		if(first == null)
		{
			first = new Node<E>(element);
			last = first;
		}
		else
		{
			last.next = new Node<E>(element);
			last = last.next;
		}
		
		size++;
	}
	
	// Add the element to the index slot
	public void add(int index, E element)
	{
		if(index < 0)
		{
			throw new IndexOutOfBoundsException("Index can not be negative");
		}
		else if(index > size)
		{
			throw new IndexOutOfBoundsException("Index exceeds list size");
		}
		if(index == 0)
		{
			Node<E> node = new Node<E>(element);
			node.next = first;
			first = node;
			size++;
			
			if(last == null)
			{
				last = first;
			}
		}
		else if(index >= size)
		{
			Node<E> node = new Node<E>(element);
			if(last == null)
			{
				last = node;
				first = last;
			}
			else
			{
				last.next = node;
				last = last.next;
			}
			
			size++;
		}
		else
		{
			Node<E> current = first;
			for(int i = 1; i < index; i++)
			{
				current = current.next;
			}
			Node<E> node = current.next;
			current.next = new Node<E>(element);
			(current.next).next = node;
			size++;
		}
	}

	// Remove the element at the index slot
	public E remove(int index)
	{
		if(index < 0)
		{
			throw new IndexOutOfBoundsException("Index can not be negative");
		}
		else if(index >= size)
		{
			throw new IndexOutOfBoundsException("Index exceeds list size");
		}
		
		if(index == 0)
		{
			return removeFirst();
		}
		else if(index == size - 1)
		{
			return removeLast();
		}
		else
		{
			Node<E> previous = first;
			for(int i = 1; i < index; i++)
			{
				previous = previous.next;
			}
			Node<E> current = previous.next;
			previous.next = current.next;
			size--;
			return (E)current.element;
		}
	}
	
	// Removes the first element in the list
	public E removeFirst()
	{
		Node<E> n = first;
		first = first.next;
		size--;
		
		if(first == null)
		{
			last = null;
		}
		return n.element;
	}
	
	// Removes the last element in the list
	public E removeLast()
	{
		if(size == 1)
		{
			Node<E> n = first;
			last = null;
			first = null;
			size = 0;
			return n.element;
		}
		else
		{
			Node<E> current = first;
			
			for (int i = 2; i < size; i++)
			{
				current = current.next;
			}
			
			Node<E> n = last;
			last = current;
			last.next = null;
			size--;
			return n.element;
		}
	}
	
	// Removes the first occurrence of the element in the list
	public boolean remove(E element)
	{
		if(element == first.element)
		{
			removeFirst();
			return true;
		}
		else
		{
			Node<E> previous = first;
			for(Node<E> n = first.next; n != null; n = n.next)
			{
				if(n.element == element)
				{
					if(n.next == null)
					{
						removeLast();
					}
					else
					{
						previous.next = n.next;
						size--;
					}
					return true;
				}
				previous = previous.next;
			}
		}
		return false;
	}

	// Return the element at the index slot
	public E get(int index)
	{
		if(first == null)
		{
			throw new IndexOutOfBoundsException("List is empty");
		}
		else if(index < 0)
		{
			throw new IndexOutOfBoundsException("Index can not be negative");
		}
		else if(index >= size)
		{
			throw new IndexOutOfBoundsException("Index exceeds list size");
		}
		
		Node<E> current = first;
		for(int i = 0; i < index; i++)
		{
			if(current.next == null)
			{
				return null;
			}
			current = current.next;
		}
		return current.element;
	}

	// Returns true if the element exists in the list
	public boolean contains(E element)
	{
		for(Node<E> n = first; n != null; n = n.next)
		{
			if(n.element == element)
			{
				return true;
			}
		}
		return false;
	}

	// Return the index slot of the first occurrence of the element in the list
	public int indexOf(E element)
	{
		int i = 0;
		for(Node<E> n = first; n != null; n = n.next)
		{
			if(n.element == element)
			{
				return i;
			}
			i++;
		}
		return -1;
	}
	
	// Clear the list
	public void clear()
	{
		first = null;
		last = null;
		size = 0;
	}
	
	// Return the list size
	public int size()
	{
		return size;
	}
	

	public Iterator<E> iterator()
	{
		return new ALDALinkedListIterator();
	}

	// The iterator class
	private class ALDALinkedListIterator implements Iterator<E>
	{
		private Node<E> nextNode = first;
		private Node<E> currentNode;


	    public boolean hasNext()
	    {
	        return nextNode != null;
	    }

	    public E next()
	    {
	    	if (!hasNext()) 
	    	{
	    		throw new NoSuchElementException("This is the last element in the list");
	    	}
	    	
	        E element = nextNode.element;
	        currentNode = nextNode;
	        nextNode = nextNode.next;
	        return element;
	    }

	    public void remove()
	    {
	    	if(currentNode == null)
	    	{
	    		throw new IllegalStateException("Element already removed");
	    	}
	    	ALDALinkedList.this.remove(currentNode.element);
	    	currentNode = null;
	    }
	}
	
	// The toString method
	public String toString()
	{
		String str = "";
		
		if(size == 0)
		{
			return "[]";
		}
		else
		{
			
			str += first.element;
			Node<E> current = first.next;
			while(current != null)
			{
				str += ", " + current.element;
				current = current.next;
			}
			
			return "[" + str + "]";
		}
	}
}
