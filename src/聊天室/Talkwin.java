package 聊天室;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;

public class Talkwin extends JFrame {

	private JPanel contentPane;
	InetAddress inet = null;
	DatagramPacket dp;
	
	public Talkwin() throws IOException {
		setTitle("聊天室");
		setVisible(true);
		DatagramSocket ds=new DatagramSocket();
		DatagramSocket ds1 =new DatagramSocket(6000);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 491);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaptionBorder);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(Color.WHITE);
		panel.setBounds(10, 10, 514, 295);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(10, 10, 494, 275);
		panel.add(textArea_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBackground(SystemColor.text);
		panel_1.setBounds(10, 318, 514, 88);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.isControlDown()&&arg0.getKeyChar()==KeyEvent.VK_ENTER) {
					
					String str= textArea.getText();
					byte[] data;
					try {
						data = str.getBytes("utf-8");
					
					dp=new DatagramPacket(data, data.length,inet, 6000);
					} catch (UnsupportedEncodingException e) {
						
						e.printStackTrace();
					}
					try {
						ds.send(dp);
					} catch (IOException e1) {
							
						e1.printStackTrace();
					}
					textArea_1.append("我：\n"+textArea.getText()+"\n");
					textArea.setText("");
				}
			}

			
		});
		textArea.setBounds(10, 10, 494, 68);
		panel_1.add(textArea);
		
		
		JButton btnNewButton = new JButton("关闭");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnNewButton.setBounds(328, 416, 93, 33);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("发送");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str= textArea.getText();
				byte[] data;
				try {
					data = str.getBytes("utf-8");
				
				dp=new DatagramPacket(data, data.length,inet, 6000);
				} catch (UnsupportedEncodingException e2) {
					
					e2.printStackTrace();
				}
				try {
					ds.send(dp);
				} catch (IOException e1) {
						
					e1.printStackTrace();
				}
				textArea_1.append("我：\n"+textArea.getText()+"\n");
				textArea.setText(null);	
			
			}
		});
		
		btnNewButton_1.setBounds(431, 416, 93, 33);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("确认ip");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ip=textArea.getText();
				try {
					inet = InetAddress.getByName(ip);
				} catch (UnknownHostException e2) {
					
					e2.printStackTrace();
				}
				textArea.setText(null);
			}
		});
		
		btnNewButton_2.setBounds(20, 418, 93, 28);
		contentPane.add(btnNewButton_2);
		
		JScrollPane scrollPane = new JScrollPane(textArea_1);
		scrollPane.setBounds(10, 10, 511, 295);
		contentPane.add(scrollPane);
		byte[] data1 =new byte[1024];
		while(true) {
			DatagramPacket dp1=new DatagramPacket(data1, data1.length);
			try {
				ds1.receive(dp1);
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
			String ip =dp1.getAddress().getHostAddress();
			int port=dp1.getPort();
			int length=dp1.getLength();
			textArea_1.append(ip+":"+port+"\n"+new String(data1,0,length,"utf-8")+"\n");
		}
	}
}
