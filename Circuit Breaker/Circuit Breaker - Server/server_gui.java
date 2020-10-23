import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;


public class server_gui {

	private JFrame frmServer;
	private JTextField txt_servSts;
	protected static boolean Serverstatus;
	private JTextField txt_srvVar;

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					server_gui window = new server_gui();
					window.frmServer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		for( int i = 0 ; i <= 3 ; i++ ) {
            //System.out.print( i );
            try {
				Thread.sleep( 500 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		System.out.println("");
		Serverstatus=true;
		ServerSocket ss=new ServerSocket(6666); 
		
		while(true) {
				while(Serverstatus==true) {
					
					System.out.println("Listening:-");
				if(Serverstatus==false) {
					//ss.wait();
					break;
					}
				Socket s=ss.accept();//establishes connection
				if(s.isConnected() == true && s.isInputShutdown()==false && Serverstatus==true) {
					
					System.out.println("Client Connected");
					
					DataInputStream dis=new DataInputStream(s.getInputStream());  
					try {
					while(s.isConnected()==true && Serverstatus==true) {
					String  str=(String)dis.readUTF();  
					System.out.println("message= "+str);
					}
					ss.close();
					}
					catch(EOFException e) {
					//ss.close(); 
						
					}
				}
				}
			
			while(Serverstatus==false) {
				
				System.out.println("ServerDown");
				ss.close();
				Thread.sleep( 1500 );
				
				
				
				
			}
		}
	}

	/**
	 * Create the application.
	 */
	public server_gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmServer = new JFrame();
		frmServer.setTitle("Server");
		frmServer.setBounds(100, 100, 450, 300);
		frmServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmServer.getContentPane().setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 139);
		frmServer.getContentPane().add(scrollPane);
		
		JTextArea ta = new JTextArea();
		scrollPane.setViewportView(ta);
		TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
		
		JToggleButton btn_serverstatus = new JToggleButton("Simulate Server Failure");
		btn_serverstatus.setSelected(true);
		btn_serverstatus.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange()!=ItemEvent.SELECTED) {
					txt_servSts.setBackground(Color.red);
					txt_servSts.setText("Server Status: -OFF");
					Serverstatus=false;
					System.out.println("Server Failure");
					txt_srvVar.setText(Boolean.toString(Serverstatus));
				}
				else {
					txt_servSts.setBackground(Color.green);
					txt_servSts.setText("Server Status: -ON");
					Serverstatus=true;
					txt_srvVar.setText(Boolean.toString(Serverstatus));
				}
			}
		});
		btn_serverstatus.setBackground(Color.GRAY);
		btn_serverstatus.setBounds(117, 169, 200, 37);
		frmServer.getContentPane().add(btn_serverstatus);
		txt_servSts = new JTextField();
		txt_servSts.setBackground(new Color(50, 205, 50));
		txt_servSts.setEditable(false);
		txt_servSts.setFont(new Font("Tahoma", Font.BOLD, 14));
		txt_servSts.setText("Server Status: -ON");
		txt_servSts.setBounds(143, 217, 147, 23);
		frmServer.getContentPane().add(txt_servSts);
		txt_servSts.setColumns(10);
		
		txt_srvVar = new JTextField();
		txt_srvVar.setBounds(24, 220, 86, 20);
		frmServer.getContentPane().add(txt_srvVar);
		txt_srvVar.setColumns(10);
		txt_srvVar.setText(Boolean.toString(Serverstatus));
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );
	}
	
}
