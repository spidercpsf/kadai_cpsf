import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

class boderBox{
	int begin;
	int end;
	int position;
	public boderBox(int x,int y,int position) {
		begin=x;
		end=y;
		this.position=position;
	}
}

public class AnimationPanel extends JPanel implements SortEventListener{
	int array[];
	int tmpArray[];
	int position[];
	int sizeX,sizeY;
	int max=100;
	ArrayList<boderBox> listBox = new ArrayList<boderBox>();
	public AnimationPanel(int array[]){
		this.array=new int[array.length];
		this.tmpArray= new int[array.length];
		this.position= new int[array.length];
		for(int i=0;i<this.position.length;i++){
			this.array[i]=array[i];
			this.position[i]=0;
			this.tmpArray[i]=-1;
		}
	}
	public void paint(Graphics g){
		 g.setColor(Color.white);
	     g.fillRect(0, 0,  this.getSize().width,this.getSize().height );
	     //draw
	     for(int i=0;i<array.length;i++){
	    	 int y= (int)(i*1.0/array.length* this.getSize().height);
	    	 int x= 60*this.position[i];
	    	 
	    	 int h= (int)( this.getSize().height*0.8/array.length);
	    	 int w= (int)(array[i]*1.0/max*50);
	    	 //System.out.println("HEa="+this.getSize().height+ " "+h +" "+w);
	    	 g.setColor(Color.DARK_GRAY);
	    	 
	    	 if(array[i]>0){
	    		 g.fillRect(x, y, w, h);
	    		 g.drawString(""+array[i], x+w, y+h/2);
	    	 }
	    	 
	    	 if(tmpArray[i]>0){
	    		 w= (int)(tmpArray[i]*1.0/max*50);
	    		 g.setColor(Color.BLUE);
	    		 g.fillRect(x-55, y, w, h);
	    		 g.drawString(""+tmpArray[i], x+w-55, y+h/2);
	    	 }
	     }
	     //draw box
	     for(int i=0;i<listBox.size();i++){
	    	 g.setColor(Color.black);
	    	 int begin= listBox.get(i).begin;
	    	 int end= listBox.get(i).end;
	    	 boolean check=true;
	    	 for(int j=begin;j<=end;j++) if(listBox.get(i).position!=position[j]) check=false;
	    	 if(!check)continue;
	    	 int y= (int)(begin*1.0/array.length* this.getSize().height)-2;
	    	 int x= 60*this.position[begin]-2;
	    	 int h= (int)( this.getSize().height*1.0/array.length)*(end-begin+1)+2;
	    	 int w= 52;
	    	 g.drawRect(x, y, w, h);
	    	 
	     }
	}

	@Override
	public void myEventOccurred(SortEvent evt) {
		statusArray sA=(statusArray) evt.getSource();
		System.out.println(sA.code+" "+sA.x1+" "+sA.x2);
		if(sA.code==0){//split
			//delete box, which have begin is sA.x2
			/*for(int i=0;i<listBox.size();i++){
				if(listBox.get(i).begin==sA.x2) listBox.remove(i);
			}*/
			//add 2 box
			listBox.add(new boderBox(sA.x2, sA.x1/2-1+sA.x2,position[sA.x2]+1));
			listBox.add(new boderBox(sA.x1/2+sA.x2,sA.x1+sA.x2-1,position[sA.x1/2+sA.x2]+1));
			//
			for(int i=sA.x2;i<sA.x1+sA.x2;i++){
				this.position[i]+=1;
			}
		}else if(sA.code==1){//merge
			tmpArray[sA.x2]= array[sA.x1];
			array[sA.x1]*=-1;
		}else if(sA.code==2){//finish merge
			for(int i=sA.x1;i<sA.x1+sA.x2;i++){
				System.out.println("	**"+i+":"+position[i]+" "+array[i]+" "+tmpArray[i]);
				this.position[i]-=1;
				this.array[i]=this.tmpArray[i];
				this.tmpArray[i]=-1;
				System.out.println("	->"+i+":"+position[i]+" "+array[i]+" "+tmpArray[i]);
			}
			//remove box
			/*for(int i=0;i<listBox.size();i++){
				if(listBox.get(i).begin==sA.x1) listBox.remove(i);
			}*/
		}
		this.repaint();
	}
	
}
