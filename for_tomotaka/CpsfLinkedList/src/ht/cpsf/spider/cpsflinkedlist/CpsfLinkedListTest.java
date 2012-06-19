package ht.cpsf.spider.cpsflinkedlist;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.LinkedList;
import java.util.Random;

import org.junit.Test;

public class CpsfLinkedListTest {
	private boolean checkTwoList(SpiderLinkedList spLL, LinkedList<Integer> trueLL){
		int a,b;
		for(int i=0;i<spLL.size()||i<trueLL.size();i++){
			a=trueLL.get(i);
			b=(Integer)spLL.get(i);
			//System.out.println("Check "+ i + ":" + a+  " vs " + b+" -> "+ (a==b));
			if(a!=b) return false;
		}
		return true;
	}
	@Test
	public void testGet() {
		SpiderLinkedList spLL = new SpiderLinkedList();
		LinkedList<Integer> trueLL = new LinkedList<Integer>();
		for(int i=0;i<1000;i++){
			spLL.add(i);
			trueLL.add(i);
		}
		//check
		assertEquals("Result", true, checkTwoList(spLL, trueLL));
		
	}

	@Test
	public void testAdd() {
		SpiderLinkedList spLL = new SpiderLinkedList();
		LinkedList<Integer> trueLL = new LinkedList<Integer>();
		for(int i=0;i<1000;i++){
			spLL.add(i);
			trueLL.add(i);
		}
		//check
		assertEquals("Result", true, checkTwoList(spLL, trueLL));
	}

	@Test
	public void testRemove() {
		Random rd = new Random(new Date().getTime());
		SpiderLinkedList spLL = new SpiderLinkedList();
		LinkedList<Integer> trueLL = new LinkedList<Integer>();
		for(int i=0;i<1000;i++){
			spLL.add(i);
			trueLL.add(i);
		}
		//check
		int idRm;
		boolean check;
		for(int i=0;i<500;i++) {
			idRm= rd.nextInt(trueLL.size());
			if(idRm<0) System.out.println("Warning");
			spLL.remove(idRm);
			trueLL.remove(idRm);
			check =checkTwoList(spLL, trueLL);
			System.out.println("Remove:"+idRm +" :"+check);
			assertEquals("Result", true, check);
		}
		
	}

	@Test
	public void testSize() {
		Random rd = new Random(new Date().getTime());
		SpiderLinkedList spLL = new SpiderLinkedList();
		LinkedList<Integer> trueLL = new LinkedList<Integer>();
		for(int i=0;i<1000;i++){
			spLL.add(i);
			trueLL.add(i);
			assertEquals("Result", spLL.size(), trueLL.size());
		}
		//check
		int idRm;
		boolean check;
		for(int i=0;i<1000;i++) {
			idRm= rd.nextInt(trueLL.size());
			if(idRm<0) System.out.println("Warning");
			spLL.remove(idRm);
			trueLL.remove(idRm);
			//check =checkTwoList(spLL, trueLL);
			//System.out.println("Remove:"+idRm +" :"+check);
			assertEquals("Result", spLL.size(), trueLL.size());
		}
	}
	@Test
	public void testCombination(){
		Random rd = new Random(new Date().getTime());
		SpiderLinkedList spLL = new SpiderLinkedList();
		LinkedList<Integer> trueLL = new LinkedList<Integer>();
		int rdV,rdV2;
		boolean check;
		for(int i=0;i<1000;i++){
			rdV= rd.nextInt();
			spLL.add(rdV);
			trueLL.add(rdV);
			//assertEquals("Result", spLL.size(), trueLL.size());
		}
		for(int i=0;i<2000;i++){
			rdV= rd.nextInt(4);
			switch (rdV) {
			case 0://check size
				System.out.println(	"	Size: step"+i);
				assertEquals("Result", spLL.size(), trueLL.size());
				break;
			case 1://check get
				System.out.println(	"	Get: step"+i);
				if(trueLL.size()!=0) {
					rdV2 = rd.nextInt(trueLL.size());
					assertEquals("Result", spLL.get(rdV2), trueLL.get(rdV2));
				}else{
					assertEquals("Result", spLL.size(), 0);
				}
			case 2://check add
				System.out.println(	"	Add: step"+i);
				rdV2 = rd.nextInt();
				spLL.add(rdV2);
				trueLL.add(rdV2);
				check =checkTwoList(spLL, trueLL);
				assertEquals("Result",true, check);
			case 3://check remove
				System.out.println(	"	Remove: step"+i);
				//System.out.println(	"	Size="+trueLL.size());
				if(trueLL.size()!=0) {
					rdV2 = rd.nextInt(trueLL.size());
					spLL.remove(rdV2);
					trueLL.remove(rdV2);
					check =checkTwoList(spLL, trueLL);
					assertEquals("Result",true, check);
				}
			default:
				break;
			}
		}
		
	}

}
