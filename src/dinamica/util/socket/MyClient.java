package dinamica.util.socket;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MyClient extends JFrame implements ActionListener,KeyListener{

	 JPanel jp=null;
     JTextArea jta=null;
     JScrollPane jsp=null;
     JTextField jtf=null;
     JButton jb=null;
     
     PrintWriter pw=null;

	  public static void main(String[] args) {
		new  MyClient();
	}
	 
	 public MyClient(){
	
		 
		 jta=new JTextArea();
		 jsp=new JScrollPane(jta);
		 
		 jtf=new JTextField(15);
		 jtf.addKeyListener(this);
		 jb=new JButton("发送");
		 jb.addActionListener(this);
	 
		 jp=new JPanel();
		 jp.add(jtf);
		 jp.add(jb);
		  
	    

		 this.add(jsp,BorderLayout.CENTER);
		 this.add(jp,BorderLayout.SOUTH);
		 this.setSize(300,200);
		 this.setTitle("qq简易聊天");
		 this.setIconImage(new ImageIcon("image/icon.jpg").getImage());
	
	
		 
		 //设置窗体居中
		 Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
		 this.setLocation((int)((size.getWidth()-this.getWidth())/2),(int)((size.getHeight()-this.getHeight())/2));
		 
		 
		 this.setVisible(true);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	 
		 
		 try {
		   Socket s=new Socket("192.168.1.210",9999);//127.0.0.1
		    
		    //读取从客户端发来的信息
		    InputStreamReader isr=new InputStreamReader(s.getInputStream());
		    BufferedReader br=new BufferedReader(isr);
		    
		    pw=new PrintWriter(s.getOutputStream(),true);
		    while(true){
		    	try{
		    		String info=br.readLine();
			    	jta.append(info+"\n");
		    	}catch(Exception ex)
		    	{
		    		System.out.println("socket.getInputStream().available():"+s.getInputStream().available());
			    	System.out.println("socket.isClosed:"+s.isClosed());//false
			    	System.out.println("socket.isConnected:"+s.isConnected());//true
			    	System.out.println("socket.isInputShutdown:"+s.isInputShutdown());//false
			    	System.out.println("socket.isOutputShutdown:"+s.isOutputShutdown());//false
			    	throw ex;
		    	}
		    }
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==jb){
			String info=jtf.getText();
			pw.println("客户端："+info);
			jta.append("客户端："+info+"\n");
			jtf.setText("");
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			String info=jtf.getText();
			pw.println("客户端："+info);
			jta.append("客户端："+info+"\n");
			jtf.setText("");
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
