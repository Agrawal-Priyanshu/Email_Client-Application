package client;

import java.awt.EventQueue;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.event.ListSelectionEvent;

public class EmailClientGUI {

	private JFrame guiframe;
	private JTextField username = new JTextField(20);
	private JPasswordField password = new JPasswordField(20);
	private DefaultListModel<String> model = new DefaultListModel<>();
	private JList<String> emailList = new JList<>(model);
	Message[] messages;
	JTextArea contentarea = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmailClientGUI window = new EmailClientGUI();
					window.guiframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EmailClientGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		guiframe = new JFrame();
		guiframe.setBounds(300, 75, 1000, 700);
		guiframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentarea.setFont(new Font("Bell MT", Font.PLAIN, 16));
		contentarea.setLineWrap(true);
		contentarea.setWrapStyleWord(true);
		contentarea.setEditable(false);
		JScrollPane contentofemail = new JScrollPane(contentarea);
		
	    emailList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    emailList.addListSelectionListener(this::emailListSelectionChanged);
	    emailList.setBackground(new Color(135, 206, 250));
	    emailList.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 15));
	    JScrollPane ListOfemails = new JScrollPane(emailList);
	    
	    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    splitPane.setResizeWeight(0.5);
	    splitPane.setOneTouchExpandable(true);
	    splitPane.setLeftComponent(ListOfemails);
	    splitPane.setRightComponent(contentofemail);
	    guiframe.getContentPane().add(splitPane,BorderLayout.CENTER);
		
		JPanel Buttons = new JPanel(new GridLayout(1,3));
		guiframe.getContentPane().add(Buttons,BorderLayout.SOUTH);
		
		JButton Refresh = new JButton("Refresh");
		Refresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshInbox();
			}
		});
		Refresh.setBorder(new MatteBorder(0,0,0,1,(Color)new Color(248,248,255)));
		Refresh.setForeground(new Color(248, 248, 255));
		Refresh.setBackground(new Color(0, 0, 139));
		Refresh.setHorizontalAlignment(SwingConstants.CENTER);
		Refresh.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		JButton Compose = new JButton("Compose");
		Compose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				composewindow window = new composewindow();
				window.comframe.setVisible(true);
			}
		});
		Compose.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(248, 248, 255)));
		Compose.setForeground(new Color(248, 248, 255));
		Compose.setBackground(new Color(0, 0, 139));
		Compose.setHorizontalAlignment(SwingConstants.CENTER);
		Compose.setFont(new Font("Times New Roman", Font.BOLD, 15));
		
		JButton Logout = new JButton("LogOut");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
	                  if (EmailSessionManager.getInstance() != null) {
	                      EmailSessionManager.getInstance().close(); // Close the email session
	                      model.clear();
	                      contentarea.setText("");
	                      username.setText("");
	      				  password.setText("");
	                      showLoginDialog();
	                  }
	              } catch (MessagingException ex) {
	                  ex.printStackTrace();
	              }
			}
		});
		Logout.setBorder(new MatteBorder(0, 1, 0, 0, (Color) new Color(248, 248, 255)));
		Logout.setBackground(new Color(0, 0, 139));
		Logout.setForeground(new Color(248, 248, 255));
		Logout.setFont(new Font("Times New Roman", Font.BOLD, 15));
		Logout.setHorizontalAlignment(SwingConstants.CENTER);
		
		Buttons.add(Refresh);
		Buttons.add(Compose);
		Buttons.add(Logout);
		
		SwingUtilities.invokeLater(this::showLoginDialog);
	}
	
	private void refreshInbox() {
	      try {
	          messages = EmailSessionManager.getInstance().receiveEmail();
	          Collections.reverse(Arrays.asList(messages));
	          model.clear();
	          for (Message message : messages) {
	              model.addElement(message.getSubject() + " - From: " + InternetAddress.toString(message.getFrom()));
	          }
	      } catch (MessagingException e) {
	          JOptionPane.showMessageDialog(null,this, "Failed to fetch emails: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
	      }
	  }
	
	private void showLoginDialog() {
		JPanel Login = new JPanel(new GridLayout(2,2));
		JLabel id = new JLabel("Email : ");
		id.setFont(new Font("Times New Roman",Font.PLAIN,25));
		Login.add(id);
		Login.add(username);
		JLabel pword = new JLabel("Password : ");
		pword.setFont(new Font("Times New Roman",Font.PLAIN,25));
		Login.add(pword);
		Login.add(password);
		Login.setBackground(new Color(30, 144, 255));
		UIManager.put("OptionPane.background", new Color(30, 144, 255));
		UIManager.put("Panel.background",new Color(30, 144, 255));
		int result = JOptionPane.showConfirmDialog(null, Login, "Login", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
		if(result==JOptionPane.OK_OPTION) {
			String Uname = username.getText();
			String Pass  = new String(password.getPassword());
			try {
				EmailSessionManager.getInstance(Uname, Pass);
				refreshInbox();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(Login, this,"Login Failed "+e.getMessage()+" error", JOptionPane.ERROR_MESSAGE);
				showLoginDialog();
			}
		}
		else {
			JOptionPane.showMessageDialog(Login, "Login Cancel");
			System.exit(0);
		}
	}
	
	private void emailListSelectionChanged(ListSelectionEvent e) {
		if(!e.getValueIsAdjusting()&& emailList.getSelectedIndex()!=-1) {
			try {
				Message selectedmessage = messages[emailList.getSelectedIndex()];
				contentarea.setText("");
				contentarea.append("Subject :"+selectedmessage.getSubject()+"\n\n");
				contentarea.append("From :"+InternetAddress.toString(selectedmessage.getFrom())+"\n\n");
				contentarea.append(getTextmessage(selectedmessage));
			}
			catch(MessagingException | IOException ex){
				contentarea.setText("error loading message "+ex.getMessage());
			}
		}
	}
	
	private String getTextmessage(Message message)throws MessagingException,IOException {
		if(message.isMimeType("text/plain")) {
			return (String)message.getContent();
		}
		else if(message.isMimeType("multipart/*")) {
			MimeMultipart mimemultipart = (MimeMultipart) message.getContent();
		      for (int i = 0; i < mimemultipart.getCount(); i++) {
		          BodyPart bodyPart = mimemultipart.getBodyPart(i);
		          if (bodyPart.isMimeType("text/plain")) {
		              return (String) bodyPart.getContent();
		        }
		    }    
		}
		return "No readable content found."; 
	}
}
