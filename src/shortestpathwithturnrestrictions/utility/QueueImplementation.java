package shortestpathwithturnrestrictions.utility;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class QueueImplementation<E extends Comparable<E>> implements QueueInterface<E> {
	
	// do not delete the definition of default constructor
    private LinkedList<E> llque;
	public QueueImplementation() {
            llque = new LinkedList();
	}
	
	@Override
	public void enqueue(E e) {
            try{
            llque.offer(e);
            }            
            catch( IllegalArgumentException ex){
                throw ex;
            }
	}

	@Override
	public E dequeue() {
        try{
	return llque.poll();
            }
            catch (NoSuchElementException ex){
            throw ex;
            }
	}

	@Override
	public E peekMedian() {
		 try{
 
        
        int median = (llque.size()/2)+1;
 @SuppressWarnings("unchecked")
 //        E[] eles=(E[])(Array.newInstance(getClass(),llque.size()));
           
        E[] eles =(E[])new Object[llque.size()];
   
        llque.toArray(eles);
        Arrays.sort(eles);
        return eles[median];
            }
            catch (java.util.NoSuchElementException ex){
            throw ex;
            }
            catch (Exception ex){
             ex.printStackTrace();
             return null;
            }
           
	}

	@Override
	public E peekMaximum()
        {   E max=llque.getFirst();
            for(E e : llque)
            {
            if(e.compareTo(max) >0)
                max=e;
            }
		return max;
	}

	@Override
	public E peekMinimum() {
		E min=llque.getFirst();
            for(E e : llque)
            {
            if(e.compareTo(min) <0)
                min=e;
            }
		return min;
	}

	@Override
	public int size() {
		return llque.size();
	}
        
        public boolean checkEntry(E e){
     
            return llque.contains(e);
            
        }
        

}
