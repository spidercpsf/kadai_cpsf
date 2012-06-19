import java.io.IOException;
import java.util.logging.Handler;

abstract class sortable implements sortInterface{
	public abstract int[] sort(int[] array,int begin,int length,int sleepTime) throws InterruptedException, IOException;
}
