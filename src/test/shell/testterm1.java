// Special thanks to this guy:
//http://code.google.com/p/market-enabler/wiki/ShellCommands
package test.shell;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class testterm1 extends Activity {
	TextView tv;
	DataOutputStream stdIn;
	DataInputStream stdOut;
	DataInputStream stdErr;
	outputThread outThread;
	Process process;
	private final static String SHELL = "/system/bin/sh";
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		try {
			process = Runtime.getRuntime().exec("/system/bin/sh");
			stdIn = new DataOutputStream(process.getOutputStream());
			stdOut = new DataInputStream(process.getInputStream());
			stdErr = new DataInputStream(process.getErrorStream());

        
	        tv = (TextView)findViewById(R.id.testtext);
	        tv.setText("some terminal tests \n");
	        
	        //outThread=new outputThread(stdOut,stdErr,tv);
	        //outThread.start();
	        
	        String cmd="mount";
			cmd+='\n';
			stdIn.writeBytes(cmd);
			stdIn.flush();
			
			dump();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tv.append("err:"+e.getLocalizedMessage());
		}        
		
    }

    public void dump(){
    	try{
	    	while( (stdOut.available()==0) && (stdErr.available()==0) ){}
			if(stdOut.available()>0){
				while(stdOut.available() > 0){
					tv.append(""+(char)stdOut.read());
				}						
			}
			
			if(stdErr.available() >0 ){
				while(stdErr.available() > 0){
				tv.append(""+(char)stdErr.read());
				}						
			}
    	}catch(IOException e){}
    }
}
