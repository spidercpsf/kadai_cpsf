import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Handler;


public class sortAnimation {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		Random rd= new Random(new Date().getTime());
		int array[] = new int[16];
		for(int i=0;i<array.length;i++) array[i] = (rd.nextInt(90))%90+10; 
		AnimationFrame aF= new AnimationFrame(array);
		sortAgorithm sA = new sortAgorithm(aF.getListenr());
		
		
		array=sA.sort(array,0,array.length,200);
		for(int i=0;i< array.length;i++){
			System.out.print(array[i]+" ");
		}
	}

}
