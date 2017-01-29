package handleInvariantFiles;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class RunProcess {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
//		String[] command = {"C:/cygwin64/bin/bash.exe", "-c", "/bin/ls ..; pwd; javac C:/Users/yxy/Documents/daikonparent/quicksort.java;"};
		String[] commands = new String[]{
                "C:/cygwin64/bin/bash",
                "C:/Users/yxy/Documents/daikonparent/Process.bash" 
             };
        ProcessBuilder probuilder = new ProcessBuilder( commands );
        //You can set up your work directory
        probuilder.directory(new File("c:/Users/yxy"));
        probuilder.redirectErrorStream(true);
        Process process = probuilder.start();
        
        //Read out dir output
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        System.out.printf("Output of running %s is:\n",
                Arrays.toString(commands));
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        
        //Wait to get exit value
        try {
            int exitValue = process.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}

}
