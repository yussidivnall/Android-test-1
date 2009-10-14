package test.shell;

import java.io.DataInputStream;
import java.io.IOException;

import android.widget.TextView;

public class outputThread extends Thread {
	DataInputStream stdOut;
	DataInputStream stdErr;
	TextView tv;
	
	outputThread(DataInputStream out,DataInputStream err, TextView t){
		stdOut = out;
		stdErr = err;
		tv = t;
	}
	
	public char parseChar(int c){
		if((char)c=='\n'){
			//tv.setText("current:"+c);
			//tv.append("got a new line \n");
			return 'N';
		}else{
			return (char)'E';
		}
	}
	
	
	
	public void run(){
		try{
			synchronized(this){
				while (true){
					tv.append("ggg");
					String msg="";
					while ((stdOut.available()==0) && (stdErr.available()==0)){Thread.sleep(10);} // wait for something...
					if (stdOut.available() > 0){
						while(stdOut.available() >0){
							msg+=""+(char)stdOut.read();
						}
						//System.out.println(msg);
						//testterm1.newText(msg);
						
						tv.append(msg.substring(0, 17));
					}
					if (stdErr.available() > 0){}
					
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}