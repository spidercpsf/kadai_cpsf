package ht.cpsf.spider.cpsflinkedlist;

/**
 * Implement Double Link List
 * 
 * @author cpsf
 * Don't use fist object 
 *
 *  		O1 ->  O2 -> 03 -> null
 *  null <- O1 <-  O2 <- O3
 */
public class SpiderLinkedList implements CpsfLinkedList{
	Object data;
	SpiderLinkedList next;
	SpiderLinkedList previous;
	public SpiderLinkedList(){
		next=null;
		previous=null;
	}
	private SpiderLinkedList(Object data, SpiderLinkedList next, SpiderLinkedList previous){
		this.data=data;
		this.next=next;
		this.previous=previous;	
	}
	@Override
	public Object get(int index) {
		SpiderLinkedList nowObject=this.next;
		for(int count=0;count<index&&nowObject!=null;count++,nowObject = nowObject.next);
		return (nowObject!=null? nowObject.data: null);
	}

	@Override
	public void add(Object o) {
		SpiderLinkedList nowObject=this;
		for(nowObject=this;nowObject.next!=null;nowObject = nowObject.next);
		SpiderLinkedList newNode = new SpiderLinkedList(o ,null, nowObject);
		nowObject.next = newNode;
	}

	@Override
	public void remove(int index) {
		SpiderLinkedList nowObject=this.next;
		for(int count=0;count<index&&nowObject!=null;count++,nowObject = nowObject.next);
		if(nowObject!=null){
			if(nowObject.next!=null)nowObject.next.previous= nowObject.previous;
			if(nowObject.previous!=null)nowObject.previous.next= nowObject.next;
		}
	}

	@Override
	public int size() {
		int count=0;
		SpiderLinkedList nowObject=this.next;
		for(count=0;nowObject!=null;count++,nowObject = nowObject.next);
		return count;
	}

}
