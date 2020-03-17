import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	
	private static Socket s;
	private static ServerSocket ss;
	private static InputStreamReader isr;
	private static BufferedReader br;
	private static String message;
	
	private static Robot robot;
	
	public static void main(String[] args) {
		System.out.println("Hello World");
		
		try {
			robot=new Robot();
			ss=new ServerSocket(7800);
			
			while (true) {
				s=ss.accept();
				isr=new InputStreamReader(s.getInputStream());
				br=new BufferedReader(isr);
				message=br.readLine();
				System.out.println("Received: "+message);
				
				switch (message) {
				
					case "Q":
						for (int x=0;x<100;x++)
							robot.keyPress(KeyEvent.VK_Q);
						break;
						
					case "W":
						for (int x=0;x<100;x++)
							robot.keyPress(KeyEvent.VK_W);
						break;
				}
			}
			
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
