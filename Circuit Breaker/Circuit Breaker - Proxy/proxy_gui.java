import java.awt.EventQueue;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Choice;
import javax.swing.JSpinner;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JTextField;
import javax.swing.JButton;
public class proxy_gui {

	private JFrame frmProxy;
	private static JTextField txt_servSts;
	//protected static boolean Serverstatus;
	private static JTextField txt_failcount;
	private static int fail_count;
	private static int succs_count;
	private static String proxy_mode;
	private JButton btn_refresh;
	private static JTextField txt_succscount;
	/**
	 * Launch the application.
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings({ "unused", "resource" })
	public static void main(String[] args) throws IOException, InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					proxy_gui window = new proxy_gui();
					window.frmProxy.setVisible(true);
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
		proxy_mode="closed";
		fail_count=0;
		succs_count=0;
		
		ServerSocket s2=new ServerSocket(6677); 
		while(true) {
			
				System.out.println("Listening:-");
			Socket s=s2.accept();//establishes connection
			if(s.isConnected() == true && s.isInputShutdown()==false ) {
				
				System.out.println("Client Connected");
				
				DataInputStream dis=new DataInputStream(s.getInputStream());  
				try {
					while(s.isConnected()==true) {
					String  str=(String)dis.readUTF();
					//System.out.println("message= "+str);
					if(proxy_mode=="closed") {
						try {
							succs_count=0;
							Socket ss=new Socket("localhost",6666);
							DataOutputStream dout=new DataOutputStream(ss.getOutputStream());
							dout.writeUTF(str);
							dout.close();
							ss.close();
							}
							catch(IOException e){
								fail_count++;
								System.out.println("Server Down");
								txt_failcount.setText(Integer.toString(fail_count));
							}
						}
					else if(proxy_mode=="half"){
						try {
							Thread.sleep(500);
							Socket ss=new Socket("localhost",6666);
							if(ss.isConnected()==true) {
								succs_count++;
								txt_succscount.setText(Integer.toString(succs_count));
								}
							DataOutputStream dout=new DataOutputStream(ss.getOutputStream());
							dout.writeUTF(str);
							dout.close();
							ss.close();
							}
							catch(IOException e){
								//fail_count++;
								proxy_mode="open";
								System.out.println("System Recovering");
								
							}
						}
					else if(proxy_mode=="open"){
						try {
							Thread.sleep(1000);
							Socket ss=new Socket("localhost",6666);
							if(ss.isConnected()==true) {
								proxy_mode="half";
								txt_servSts.setText("Proxy Mode: - HALF OPEN");
								succs_count++;
								txt_succscount.setText(Integer.toString(succs_count));
								}
							DataOutputStream dout=new DataOutputStream(ss.getOutputStream());
							dout.writeUTF(str);
							dout.close();
							ss.close();
							}
							catch(IOException e){
								//fail_count++;
								System.out.println("System Recovering");
								
							}
						}
						if(fail_count>=6 && proxy_mode!="open"&&proxy_mode!="half") {
							proxy_mode="open";
							txt_servSts.setText("Proxy Mode: - OPEN");
							Thread.sleep(2500);
							//txt_servSts.setText("Proxy Mode: - HALF OPEN");
						}
						if(succs_count>=6) {
							fail_count=0;
							proxy_mode="closed";
							txt_servSts.setText("Proxy Mode: - CLOSED");
							System.out.println("System Recovered");
						}
					}
				}
				catch(EOFException e) {
				//ss.close();  
						}
				}
			
		}	
		
	}

	/**
	 * Create the application.
	 */
	public proxy_gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmProxy = new JFrame();
		frmProxy.setTitle("Proxy");
		frmProxy.setBounds(100, 100, 450, 300);
		frmProxy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmProxy.getContentPane().setLayout(null);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 434, 139);
		frmProxy.getContentPane().add(scrollPane);
		
		JTextArea ta = new JTextArea();
		scrollPane.setViewportView(ta);
		TextAreaOutputStream taos = new TextAreaOutputStream( ta, 60 );
		txt_servSts = new JTextField();
		txt_servSts.setBackground(new Color(50, 205, 50));
		txt_servSts.setEditable(false);
		txt_servSts.setFont(new Font("Tahoma", Font.BOLD, 14));
		txt_servSts.setText("Proxy Mode: - CLOSED");
		txt_servSts.setBounds(226, 175, 184, 23);
		frmProxy.getContentPane().add(txt_servSts);
		txt_servSts.setColumns(10);
		
		txt_failcount = new JTextField();
		txt_failcount.setBounds(78, 178, 46, 20);
		frmProxy.getContentPane().add(txt_failcount);
		txt_failcount.setColumns(10);
		txt_failcount.setText(null); //
		
		btn_refresh = new JButton("Refresh");
		btn_refresh.setFont(new Font("Tahoma", Font.BOLD, 12));
		btn_refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt_failcount.setText(Integer.toString(fail_count));
				txt_succscount.setText(Integer.toString(succs_count));
			}
		});
		btn_refresh.setBounds(165, 209, 103, 41);
		frmProxy.getContentPane().add(btn_refresh);
		
		txt_succscount = new JTextField();
		txt_succscount.setText((String) null);
		txt_succscount.setColumns(10);
		txt_succscount.setBounds(78, 209, 46, 20);
		frmProxy.getContentPane().add(txt_succscount);
		
		JLabel lblNewLabel = new JLabel("Faliures");
		lblNewLabel.setBounds(8, 181, 46, 14);
		frmProxy.getContentPane().add(lblNewLabel);
		
		JLabel lblSuccess = new JLabel("Success");
		lblSuccess.setBounds(8, 212, 52, 14);
		frmProxy.getContentPane().add(lblSuccess);
        PrintStream ps = new PrintStream( taos );
        System.setOut( ps );
        System.setErr( ps );
	}
}
