import java.io.IOException;
import java.util.EventListener;
import java.util.EventObject;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class sortAgorithm extends sortable{
	private SortEventListener sEL;
	public sortAgorithm(SortEventListener sEL){
		this.sEL=sEL;
	}
	private void fireEvent(int code,int x1,int x2){
		statusArray sA= new statusArray(code,x1,x2);
		sEL.myEventOccurred(new SortEvent(sA));
	}
	@Override
	public
	int[] sort(int[] array,int begin,int length,int sleepTime) throws InterruptedException, IOException {
		if(array.length>1){
			Thread.sleep(sleepTime);
			//System.in.read();
			int part1[]= new int[array.length/2];
			int part2[]= new int[array.length-array.length/2];
			for(int i=0;i<part1.length;i++) part1[i]=array[i];
			for(int i=0;i<part2.length;i++) part2[i]=array[i+array.length/2];
			//event split -> code 0
			fireEvent(0, length, begin);
			//System.in.read();
			//
			part1= sort(part1,begin,length/2,sleepTime);
			part2= sort(part2,begin+length/2,array.length-array.length/2,sleepTime);
			//System.out.print(part1.length+" "+part2.length+":");
			//event begin merge -> code 1
			//fireEvent(1, length, begin);
			//
			for(int i=0, n=0, m=0;i<array.length;i++){
				Thread.sleep(sleepTime);
				//System.in.read();
				if(n<part1.length&&(m>=part2.length||part1[n]<part2[m])){
					array[i] = part1[n++];
					//event merging -> code 1
					fireEvent(1, n-1+begin, i+begin);
					//
				}else{
					array[i] = part2[m++];
					//event merging -> code 1
					fireEvent(1, part1.length+m-1+begin, i+begin);
				}
				
				//System.out.print(array[i]+" ");
			}
			Thread.sleep(sleepTime);
			//System.in.read();
			fireEvent(2, begin, length);//finish event
			//System.out.println();
		}
		return array;
	}

}
