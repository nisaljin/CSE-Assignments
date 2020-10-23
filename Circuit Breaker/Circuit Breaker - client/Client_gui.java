import java.awt.EventQueue;
//import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ConnectException;
//import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

public class Client_gui {

	private JFrame frmClient;

	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client_gui window = new Client_gui();
					window.frmClient.setVisible(true);
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
		
		
		/*Socket s=new Socket("localhost",6666); 
		if(s.isConnected()==true) {
			System.out.println("Connected");
		DataOutputStream dout=new DataOutputStream(s.getOutputStream());
		for( int i = 0 ; i <= 3 ; i++ ) {
			dout.writeUTF("Hello Server");  
            try {
				Thread.sleep( 500 );
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		dout.flush();  
		dout.close();  
		s.close();  
		}*/
	}

	/**
	 * Create the application.
	 */
	public Client_gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmClient = new JFrame();
		frmClient.setTitle("Client");
		frmClient.setBounds(100, 100, 450, 300);
		frmClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClient.getContentPane().setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 424, 185);
		frmClient.getContentPane().add(scrollPane);
		
		JTextArea ta = new JTextArea();
		scrollPane.setViewportView(ta);
		TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
		
		JButton btn_send = new JButton("Send Request");
		btn_send.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btn_send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Socket s=null;
				try {
					s = new Socket("localhost",6677);
					if(s.isConnected()==true) {
						System.out.println("Connected");
					DataOutputStream dout=null;
					try {
						dout = new DataOutputStream(s.getOutputStream());
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					for( int i = 0 ; i <= 3 ; i++ ) {
						try {
							dout.writeUTF("Hello Server");
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}  
			            try {
							Thread.sleep( 500 );
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        }
					
					try {
						dout.flush();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					try {
						dout.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					try {
						s.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}  
					}
					else {
						
						
					}
					
				} catch (UnknownHostException e2) {
					// TODO Auto-generated catch block
					//e2.printStackTrace();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					//System.out.println(e2);
					System.out.println("Server Unavailable");
					//e2.printStackTrace();
				}//catch (ConnectException e3) {}
				
			}
		});
		btn_send.setBounds(135, 196, 163, 43);
		frmClient.getContentPane().add(btn_send);
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );
	}
}
