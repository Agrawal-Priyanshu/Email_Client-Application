package client;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class composewindow {

    JFrame comframe;
	private JTextField totextField;
	private JTextField cctextField;
	private JTextField subjecttextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					composewindow window = new composewindow();
					window.comframe.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public composewindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		comframe = new JFrame("New Message");
		comframe.getContentPane().setBackground(new Color(245, 245, 245));
		comframe.getContentPane().setLayout(new BorderLayout(0, 0));
		comframe.setDefaultCloseOperation(comframe.DISPOSE_ON_CLOSE);
		
		JPanel uperpanel = new JPanel();
		comframe.getContentPane().add(uperpanel, BorderLayout.NORTH);
		uperpanel.setLayout(new BoxLayout(uperpanel, BoxLayout.Y_AXIS));
		comframe.setBounds(360, 50, 650, 750);		
		uperpanel.setBackground(new Color(245, 245, 245));
		
		JLabel tolabel = new JLabel("TO");
		tolabel.setBounds(0, 0, 0, 0);
		tolabel.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 15));
		uperpanel.add(tolabel);
		
		totextField = new JTextField();
		totextField.setBounds(0, 0, 0, 0);
		totextField.setFont(new Font("Arial", Font.PLAIN, 14));
		totextField.setColumns(10);
		totextField.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(245, 245, 245)));
		totextField.setBackground(new Color(245, 245, 245));
		uperpanel.add(totextField);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(new Color(0, 0, 0));
		separator.setBounds(0, 0, 0, 0);
		separator.setBackground(new Color(255, 255, 255));
		uperpanel.add(separator);
		
		JLabel cclabel = new JLabel("CC");
		cclabel.setBounds(0, 0, 0, 0);
		cclabel.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 15));
		uperpanel.add(cclabel);
		
		cctextField = new JTextField();
		cctextField.setBounds(0, 0, 0, 0);
		cctextField.setFont(new Font("Arial", Font.PLAIN, 14));
		cctextField.setColumns(10);
		cctextField.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(245, 245, 245)));
		cctextField.setBackground(new Color(245, 245, 245));
		uperpanel.add(cctextField);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(new Color(0, 0, 0));
		separator_1.setBounds(0, 0, 0, 0);
		uperpanel.add(separator_1);
		
		JLabel subjectlabel = new JLabel("Subject");
		subjectlabel.setBounds(0, 0, 0, 0);
		subjectlabel.setFont(new Font("Yu Gothic UI Semilight", Font.PLAIN, 15));
		uperpanel.add(subjectlabel);
		
		subjecttextField = new JTextField();
		subjecttextField.setBounds(0, 0, 636, 18);
		subjecttextField.setFont(new Font("Arial", Font.PLAIN, 14));
		subjecttextField.setColumns(10);
		subjecttextField.setBorder(new MatteBorder(0, 0, 0, 0, (Color) new Color(245, 245, 245)));
		subjecttextField.setBackground(new Color(245, 245, 245));
		uperpanel.add(subjecttextField);
		
		
		JTextArea contenttextArea = new JTextArea();
		contenttextArea.setLineWrap(true);
		contenttextArea.setWrapStyleWord(true);
		contenttextArea.setBackground(new Color(225, 241, 253));
		comframe.getContentPane().add(new JScrollPane(contenttextArea), BorderLayout.CENTER);
		
		JPanel buttonpanel = new JPanel();
		buttonpanel.setBackground(new Color(255, 250, 250));
		comframe.getContentPane().add(buttonpanel, BorderLayout.SOUTH);
		buttonpanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton AttachmentButton = new JButton("Add Attachment");
		List<File>attachedfile=new ArrayList<>();
		AttachmentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File[] files = AttachmentChooser.chooseAttachments();
				attachedfile.addAll(Arrays.asList(files));
				AttachmentButton.setText(attachedfile.size()+" files added");
			}
		});
		AttachmentButton.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		AttachmentButton.setBackground(new Color(0, 0, 139));
		AttachmentButton.setForeground(new Color(245, 255, 250));
		buttonpanel.add(AttachmentButton);
		
		JButton SendButton = new JButton("Send");
		SendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String totext = totextField.getText();
				String [] torec = totext.split(",");
				String cctext = cctextField.getText();
				String [] ccrec = cctext.split("\\s*,\\s*");
				String subject = subjecttextField.getText();
				String body = contenttextArea.getText();
				File[] attachment = attachedfile.toArray(new File[0]);
				try {
				EmailSender.sendEmailWithAttachment(torec,ccrec,subject,body,attachment);
				JOptionPane.showMessageDialog(comframe,"Email Successfully sent","Successfull", JOptionPane.INFORMATION_MESSAGE, null);
				}
				catch(Exception s) {
					JOptionPane.showMessageDialog(comframe,"invalid address","failed", JOptionPane.ERROR_MESSAGE, null);
				}
				finally {
					comframe.dispose();
				}
			}
		});
		SendButton.setBackground(new Color(0, 0, 139));
		SendButton.setFont(new Font("Yu Gothic UI Semilight", Font.BOLD, 14));
		SendButton.setForeground(new Color(245, 255, 250));
		buttonpanel.add(SendButton);
	}
}
