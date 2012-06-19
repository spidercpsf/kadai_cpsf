package ht.cpsf.spider.cpsflinkedlist;
/***
 * Programming excercise of this week:

Please implement your original linked-list in Java.
Let your class implement this CpsfSimpleList interface.

public interface CpsfSimpleList {
	public Object get(int index);
	public void add(Object o);
	public void remove(int index);
	public int size();
}

So your class will be like:

public MyLinkedList implements CpsfSimpleList {
   ....
}
 * @author cpsf
 *
 */
public interface CpsfLinkedList {
	public Object get(int index);
	public void add(Object o);
	public void remove(int index);
	public int size();
}
