import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;
import javax.swing.text.*;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class CMWinClient extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JTextPane m_outTextPane;	
	private JTextPane m_outTextPane2;
	private JTextField m_inTextField;		
	private JButton m_startStopButton;
	private JButton m_loginLogoutButton;
	private JButton m_matchButton;
	private CMClientStub m_clientStub;
	private CMWinClientEventHandler m_eventHandler;
	
	CMWinClient()
	{		
		
		MyActionListener cmActionListener = new MyActionListener();
		setTitle("CM Client");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setMenus();
		setLayout(new BorderLayout(0, 2));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(new Color(220,220,220));
		centerPanel.setLayout(new GridLayout());
		add(centerPanel, BorderLayout.CENTER);	

		m_outTextPane = new JTextPane();
		m_outTextPane.setBackground(new Color(255,255,255));
		m_outTextPane.setEditable(false);

		StyledDocument doc = m_outTextPane.getStyledDocument();
		addStylesToDocument(doc);
		centerPanel.add(m_outTextPane);
		
		JScrollPane centerScroll = new JScrollPane (m_outTextPane, 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane();
		centerPanel.add(centerScroll);
		
		m_outTextPane2 = new JTextPane();
		m_outTextPane2.setBackground(new Color(255,255,255));
		m_outTextPane2.setEditable(false);

		
		StyledDocument doc2 = m_outTextPane2.getStyledDocument();
		addStylesToDocument(doc2);
		centerPanel.add(m_outTextPane2);
		
		JScrollPane centerScroll2 = new JScrollPane (m_outTextPane2, 
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		getContentPane();
		centerPanel.add(centerScroll2);

		m_inTextField = new JTextField();
		m_inTextField.addActionListener(cmActionListener);
		add(m_inTextField, BorderLayout.SOUTH);
		

		JPanel topButtonPanel = new JPanel();
		topButtonPanel.setBackground(new Color(207,240,204));
		topButtonPanel.setLayout(new FlowLayout());
		add(topButtonPanel, BorderLayout.NORTH);
		
		m_startStopButton = new JButton("Start Client CM");
		m_startStopButton.addActionListener(cmActionListener);
		m_startStopButton.setEnabled(false);
		topButtonPanel.add(m_startStopButton);
		
		m_loginLogoutButton = new JButton("Login");
		m_loginLogoutButton.addActionListener(cmActionListener);
		m_loginLogoutButton.setEnabled(false);
		topButtonPanel.add(m_loginLogoutButton);
		
		m_matchButton = new JButton("Start Match");
		m_matchButton.addActionListener(cmActionListener);
		m_matchButton.setEnabled(true);
		topButtonPanel.add(m_matchButton);
		
		setVisible(true);
		
		m_clientStub = new CMClientStub();
		m_eventHandler = new CMWinClientEventHandler(m_clientStub, this);
		
		testStartCM();
		
		m_inTextField.requestFocus();
		
		loginExample();
	}
	
	private void addStylesToDocument(StyledDocument doc)
	{
		Style defStyle = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);

		Style regularStyle = doc.addStyle("regular", defStyle);
		StyleConstants.setFontFamily(regularStyle, "SansSerif");
		
		Style boldStyle = doc.addStyle("bold", defStyle);
		StyleConstants.setBold(boldStyle, true);
		
		Style linkStyle = doc.addStyle("link", defStyle);
		StyleConstants.setForeground(linkStyle, Color.RED);
		StyleConstants.setUnderline(linkStyle, true);
	}
	
	public CMClientStub getClientStub()
	{
		return m_clientStub;
	}
	
	public CMWinClientEventHandler getClientEventHandler()
	{
		return m_eventHandler;
	}
	
	// set menus
	public void loginExample()
	{
		String strUserName = null;
		String strPassword = null;
		boolean bRequestResult = false;

		printMessage("====== login to default server\n");
		JTextField userNameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		Object[] message = {
				"User Name:", userNameField,
				"Password:", passwordField
		};
		int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION)
		{
			strUserName = userNameField.getText();
			strPassword = new String(passwordField.getPassword()); // security problem?
			
			m_eventHandler.setStartTime(System.currentTimeMillis());
			bRequestResult = m_clientStub.loginCM(strUserName, strPassword);
			if(bRequestResult)
			{
				printMessage("successfully sent the login request.\n");
			}
			else
			{
				printStyledMessage("failed the login request!\n", "bold");
				m_eventHandler.setStartTime(0);
			}
		}
		
		printMessage("======\n");
	}
	
	
	private void setMenus()
	{
		MyMenuListener menuListener = new MyMenuListener();
		JMenuBar menuBar = new JMenuBar();
		
		
		JMenu list = new JMenu("Partner");		// HH
		JMenuItem list1 = new JMenuItem("Start Match");
		JMenuItem list2 = new JMenuItem("Matched Partner");
		JMenuItem list3 = new JMenuItem("Choose Partner");

		
		list1.addActionListener(menuListener);
		list2.addActionListener(menuListener);
		list3.addActionListener(menuListener);

		
		list.add(list1);
		list.add(list2);
		list.add(list3);

		menuBar.add(list);
	
		setJMenuBar(menuBar);

	}
	
	public void initializeButtons()
	{
		m_startStopButton.setText("Start Client CM");
		m_loginLogoutButton.setText("Login");
		revalidate();
		repaint();
	}
	
	public void setButtonsAccordingToClientState()
	{
		int nClientState;
		nClientState = m_clientStub.getCMInfo().getInteractionInfo().getMyself().getState();
		
		switch(nClientState)
		{
		case CMInfo.CM_INIT:
			m_startStopButton.setText("Stop Client CM");
			m_loginLogoutButton.setText("Login");
			break;
		case CMInfo.CM_CONNECT:
			m_startStopButton.setText("Stop Client CM");
			m_loginLogoutButton.setText("Login");
			break;
		case CMInfo.CM_LOGIN:
			m_startStopButton.setText("Stop Client CM");
			m_loginLogoutButton.setText("Logout");
			break;
		case CMInfo.CM_SESSION_JOIN:
			m_startStopButton.setText("Stop Client CM");
			m_loginLogoutButton.setText("Logout");
			break;
		default:
			m_startStopButton.setText("Start Client CM");
			m_loginLogoutButton.setText("Login");
			break;
		}
		revalidate();
		repaint();
	}
	
	public void printMessage(String strText)
	{
		StyledDocument doc = m_outTextPane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), strText, null);
			m_outTextPane.setCaretPosition(m_outTextPane.getDocument().getLength());

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	public void printMessage2(String strText)
	{
		StyledDocument doc = m_outTextPane2.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), strText, null);
			m_outTextPane2.setCaretPosition(m_outTextPane2.getDocument().getLength());

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	public void printStyledMessage(String strText, String strStyleName)
	{
		StyledDocument doc = m_outTextPane.getStyledDocument();
		try {
			doc.insertString(doc.getLength(), strText, doc.getStyle(strStyleName));
			m_outTextPane.setCaretPosition(m_outTextPane.getDocument().getLength());

		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return;
	}
	
	public void example3()
	{
		String name = null;
		String password = null;
		String gender = null;
		String age = null;
		String height = null;
		String weight = null;
		String mbti = null;
		String hobby = null;
		String p_age =null;
		String p_height =null;
		String p_weight =null;
		
		CMUser myself = m_clientStub.getCMInfo().getInteractionInfo().getMyself();
		
		if(myself.getState() != CMInfo.CM_SESSION_JOIN)
		{
			printMessage("You should join a session and a group!\n");
			return;
		}

		printMessage("====== test CMUserEvent\n");
		
		JTextField nameField = new JTextField();
		JTextField passwordField = new JTextField();
		JTextField ageField = new JTextField();
		JTextField heightField = new JTextField();
		JTextField weightField = new JTextField();
		JTextField p_ageField = new JTextField();
		JTextField p_heightField = new JTextField();
		JTextField p_weightField = new JTextField();
		
		
		String[] genders = {"MALE", "FEMALE"};
		String[] mbtis = {"INTJ", "INFJ", "ISTJ", "ISTP", "INTP", "INFP", "ISFJ", "ISFP", "ENTJ", "ENFJ", "ESTJ", "ESTP", "ENTP", "ENFP", "ESFJ", "ESFP"};
		String[] hobbys = {"sports", "reading", "game", "movie", "music", "cooking", "running", "fashion"};
		@SuppressWarnings("unchecked")
		JComboBox<String>[] dataTypeBoxes = new JComboBox[3]; 
		JTextField[] eventFields = new JTextField[2];
		Object[] message = new Object[22];
		
		dataTypeBoxes[0] = new JComboBox<String>(genders);
		dataTypeBoxes[1] = new JComboBox<String>(mbtis);
		dataTypeBoxes[2] = new JComboBox<String>(hobbys);
		
		for(int i = 0; i < 1*2; i++)
		{
			eventFields[i] = new JTextField();
		}
		
		message[0] = "name: ";
		message[1] = nameField;
		message[2] = "password: ";
		message[3] = passwordField;	
		message[4] = "gender: ";
		message[5] = dataTypeBoxes[0];
		message[6] = "age: ";
		message[7] = ageField;
		message[8] = "height: ";
		message[9] = heightField;
		message[10] = "weight: ";
		message[11] = weightField;
		message[12] = "mbti: ";
		message[13] = dataTypeBoxes[1];
		message[14] = "hobby: ";
		message[15] = dataTypeBoxes[2];
		message[16] = "이상형의 나이: ";
		message[17] = p_ageField;
		message[18] = "이상형의 키: ";
		message[19] = p_heightField;
		message[20] = "이상형의 몸무게: ";
		message[21] = p_weightField;
		
		int option = JOptionPane.showConfirmDialog(null, message, "회원가입", JOptionPane.OK_CANCEL_OPTION);
		if(option == JOptionPane.OK_OPTION)
		{
			name = nameField.getText();
			if(name.equals("b")) {
				printMessage("부적합");
				int option2 = JOptionPane.showConfirmDialog(null, "b는 적합한 아이디가 아닙니다.", "경고", JOptionPane.OK_CANCEL_OPTION);
				if(option2 == JOptionPane.OK_OPTION) 
			    {example3();
				return;
			    }
				else if(option2 == JOptionPane.CANCEL_OPTION) 
			    {
				return;
			    }
			}
			if(name.equals("g")) {
				printMessage("부적합");
				int option2 = JOptionPane.showConfirmDialog(null, "g는 적합한 아이디가 아닙니다.", "경고", JOptionPane.OK_CANCEL_OPTION);
				if(option2 == JOptionPane.OK_OPTION) 
			    {example3();
				return;
			    }
				else if(option2 == JOptionPane.CANCEL_OPTION) 
			    {
				return;
			    }
			}
			if(name.equals("s")) {
				printMessage("부적합");
				int option2 = JOptionPane.showConfirmDialog(null, "s는 적합한 아이디가 아닙니다.", "경고", JOptionPane.OK_CANCEL_OPTION);
				if(option2 == JOptionPane.OK_OPTION) 
			    {example3();
				return;
			    }
				else if(option2 == JOptionPane.CANCEL_OPTION) 
			    {
				return;
			    }
			}
			password = passwordField.getText();
			gender = (String) dataTypeBoxes[0].getSelectedItem();
			age = ageField.getText();
			height = heightField.getText();
			weight = weightField.getText();
			mbti = (String) dataTypeBoxes[1].getSelectedItem();
			hobby = (String) dataTypeBoxes[2].getSelectedItem();
			p_age = ageField.getText();
			p_height = heightField.getText();
			p_weight = weightField.getText();
			
			CMUserEvent ue = new CMUserEvent();
			
			ue.setStringID("register");
			ue.setHandlerSession(myself.getCurrentSession());
			ue.setHandlerGroup(myself.getCurrentGroup());

			
			ue.setEventField(CMInfo.CM_STR, "이름", name);
			ue.setEventField(CMInfo.CM_INT, "비밀번호", password);
			ue.setEventField(CMInfo.CM_STR, "성별", gender);
			ue.setEventField(CMInfo.CM_INT, "나이", String.valueOf(age));
			ue.setEventField(CMInfo.CM_INT, "키", String.valueOf(height));
			ue.setEventField(CMInfo.CM_INT, "몸무게", String.valueOf(weight));
			ue.setEventField(CMInfo.CM_STR, "mbti", mbti);
			ue.setEventField(CMInfo.CM_STR, "취미", hobby);
			ue.setEventField(CMInfo.CM_INT, "이상형 나이", String.valueOf(p_age));
			ue.setEventField(CMInfo.CM_INT, "이상형 키", String.valueOf(p_height));
			ue.setEventField(CMInfo.CM_INT, "이상형 몸무게", String.valueOf(p_weight));
			

			m_clientStub.send(ue, "SERVER");
			ue.removeAllEventFields();
			ue = null;
		}
		
		printMessage("======\n");
		
		return;
		
	}
	
	public void exampleMatch()
	{
		
		printMessage("====== 매치 시작\n");
		
		CMUser myself = m_clientStub.getCMInfo().getInteractionInfo().getMyself();
		
		CMUserEvent ue = new CMUserEvent();
		
		ue.setStringID("exampleMatch");
		ue.setHandlerSession(myself.getCurrentSession());
		ue.setHandlerGroup(myself.getCurrentGroup());
		
		m_clientStub.send(ue, "SERVER");
		ue = null;
		
	}
	
	
	public void testLoginDS()
	{
		String strUserName = null;
		String strPassword = null;
		boolean bRequestResult = false;

		printMessage("====== login to default server\n");
		JTextField userNameField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		Object[] message = {
				"User Name:", userNameField,
				"Password:", passwordField
		};
		int option = JOptionPane.showConfirmDialog(null, message, "Login Input", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION)
		{
			strUserName = userNameField.getText();
			strPassword = new String(passwordField.getPassword()); 
			
			m_eventHandler.setStartTime(System.currentTimeMillis());
			bRequestResult = m_clientStub.loginCM(strUserName, strPassword);
			if(bRequestResult)
			{
				printMessage("successfully sent the login request.\n");
			}
			else
			{
				printStyledMessage("failed the login request!\n", "bold");
				m_eventHandler.setStartTime(0);
			}
		}
		
		printMessage("======\n");
	}
	
	public void testLogoutDS()
	{
		boolean bRequestResult = false;
		printMessage("====== logout from default server\n");
		bRequestResult = m_clientStub.logoutCM();
		if(bRequestResult)
			printMessage("successfully sent the logout request.\n");
		else
			printStyledMessage("failed the logout request!\n", "bold");
		printMessage("======\n");

		// Change the title of the login button
		setButtonsAccordingToClientState();
		setTitle("CM Client");
	}

	public void testStartCM()
	{
		boolean bRet = false;
		
		// get current server info from the server configuration file
		String strCurServerAddress = null;
		int nCurServerPort = -1;
		
		strCurServerAddress = m_clientStub.getServerAddress();
		nCurServerPort = m_clientStub.getServerPort();
		
		// ask the user if he/she would like to change the server info
		JTextField serverAddressTextField = new JTextField(strCurServerAddress);
		JTextField serverPortTextField = new JTextField(String.valueOf(nCurServerPort));
		Object msg[] = {
				"Server Address: ", serverAddressTextField,
				"Server Port: ", serverPortTextField
		};
		int option = JOptionPane.showConfirmDialog(null, msg, "Server Information", JOptionPane.OK_CANCEL_OPTION);

		// update the server info if the user would like to do
		if (option == JOptionPane.OK_OPTION) 
		{
			String strNewServerAddress = serverAddressTextField.getText();
			int nNewServerPort = Integer.parseInt(serverPortTextField.getText());
			if(!strNewServerAddress.equals(strCurServerAddress) || nNewServerPort != nCurServerPort)
				m_clientStub.setServerInfo(strNewServerAddress, nNewServerPort);
		}
		
		bRet = m_clientStub.startCM();
		if(!bRet)
		{
			printStyledMessage("CM initialization error!\n", "bold");
		}
		else
		{
			m_startStopButton.setEnabled(true);
			m_loginLogoutButton.setEnabled(true);
			printStyledMessage("Client CM starts.\n", "bold");
			setButtonsAccordingToClientState();
		}
	}
	
	public void testTerminateCM()
	{
		
		m_clientStub.terminateCM();
		printMessage("Client CM terminates.\n");
		initializeButtons();
		setTitle("CM Client");
	}

	public void Start_Match()		//HH
	{
		CMInteractionInfo interInfo = m_clientStub.getCMInfo().getInteractionInfo();
		CMUser myself = interInfo.getMyself();
		String strInput = null;
		
		
		
		if(myself.getState() != CMInfo.CM_SESSION_JOIN)
		{
			printMessage("You should join a session and a group!\n");
			return;
		}
		

		strInput = ("1");
		
		CMDummyEvent due = new CMDummyEvent();
		due.setHandlerSession(myself.getCurrentSession());
		due.setHandlerGroup(myself.getCurrentGroup());
		due.setDummyInfo(strInput);
		m_clientStub.cast(due, myself.getCurrentSession(), myself.getCurrentGroup());
		due = null;
		
		printStyledMessage("\n====== New partner \n\n", "bold");
	}
	
	public void Matched_Partner()		//HH
	{
		CMInteractionInfo interInfo = m_clientStub.getCMInfo().getInteractionInfo();
		CMUser myself = interInfo.getMyself();
		String strInput = null;
		
		if(myself.getState() != CMInfo.CM_SESSION_JOIN)
		{
			printMessage("You should join a session and a group!\n");
			return;
		}
		

		strInput = ("2");
		
		CMDummyEvent due = new CMDummyEvent();
		due.setHandlerSession(myself.getCurrentSession());
		due.setHandlerGroup(myself.getCurrentGroup());
		due.setDummyInfo(strInput);
		m_clientStub.cast(due, myself.getCurrentSession(), myself.getCurrentGroup());
		due = null;
		
		printStyledMessage("\n====== Matched Partner \n\n", "bold");
	}
	
	public void Choose_Partner()		//HH
	{
		CMInteractionInfo interInfo = m_clientStub.getCMInfo().getInteractionInfo();
		CMUser myself = interInfo.getMyself();
		String strInput = null;
		
		if(myself.getState() != CMInfo.CM_SESSION_JOIN)
		{
			printMessage("You should join a session and a group!\n");
			return;
		}
		

		strInput = JOptionPane.showInputDialog("Choose Partner: ");
		if(strInput == null) return;
		
		
		CMDummyEvent due = new CMDummyEvent();
		due.setHandlerSession(myself.getCurrentSession());
		due.setHandlerGroup(myself.getCurrentGroup());
		due.setDummyInfo(strInput);
		m_clientStub.cast(due, myself.getCurrentSession(), myself.getCurrentGroup());
		due = null;
		
		printStyledMessage("\n======Choose Partner \n\n", "bold");

	}

	public class MyActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
			Object obj = e.getSource();
			String msg = m_inTextField.getText();
		  
			if(obj==m_inTextField) {
				
				String strTarget = null;
				String strMessage = null;
				String strSender = null;
				String[] msg2 = msg.split("/");
				
				strTarget = msg2[0];			//이름 찾기 위해 '/' 삭제
				strMessage = msg2[1];
				CMInteractionInfo interInfo = m_clientStub.getCMInfo().getInteractionInfo();
				strSender = interInfo.getMyself().getName();
				int flag = 0;
				
				try {
					File file = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\" + strSender + "_partner.txt");
					
					if(file.exists() == true) {
						FileReader filereader = new FileReader(file);
				           
		            	BufferedReader bufReader = new BufferedReader(filereader);
		            	String line = "";
		            	while((line = bufReader.readLine()) != null) {
		            		if(line.equals(strTarget)) {
		            			flag++;

		            		}	
		            	}
		            	bufReader.close();

					}
					
				}	       
				catch (FileNotFoundException a) {
		            // TODO: handle exception
		        }	        
				catch(IOException a){
		            System.out.println(a);
		        }
				try {
					File file = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\" + strTarget + "_partner.txt");
					if(file.exists() == true) {
						FileReader filereader = new FileReader(file);
				           
		            	BufferedReader bufReader = new BufferedReader(filereader);
		            	String line = "";
		            	while((line = bufReader.readLine()) != null) {
		            		if(line.equals(strSender)) {
		            			flag++;
		            		}
		            			

		            	}
		            	bufReader.close();
					}
						
				}	        
				catch (FileNotFoundException a) {
		            // TODO: handle exception
		        }
		        catch(IOException a){
		            System.out.println(a);
		        }
				if(flag ==2) {
					printMessage2("SEND TO <"+ strTarget+ "> : " +msg2[1]+"\n");

			        m_clientStub.chat('/' + strTarget, strMessage);
			        flag = 0;
				}
				else {
					printStyledMessage("[" + strTarget + "] is not matched partner! \n", "bold");
					flag = 0;
				}

		        m_inTextField.setText("");
				}
				
			
			
			else {
			JButton button = (JButton) e.getSource();
			
			
			if(button.getText().equals("Start Client CM"))
			{
				testStartCM();
			}
			else if(button.getText().equals("Stop Client CM"))
			{
				testTerminateCM();
			}
			else if(button.getText().equals("Login"))
			{
				testLoginDS();
			}
			else if(button.getText().equals("Logout"))
			{
				testLogoutDS();
			}
			else if(button.getText().equals("Start Match"))
			{
				Start_Match();
			}			m_inTextField.requestFocus();
			}
		}
	}
	
	public class MyMenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e)
		{
			String strMenu = e.getActionCommand();
			switch(strMenu)
			{
			case "Start Match":		//HH
				Start_Match();
				break;
			case "Matched Partner":
				Matched_Partner();
				break;
			case "Choose Partner":
				Choose_Partner();
				break;
			case "start CM":
				testStartCM();
				break;
			case "terminate CM":
				testTerminateCM();
				break;
			case "login to default server":
				testLoginDS();
				break;
			case "logout from default server":
				testLogoutDS();
				break;

			}
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CMWinClient client = new CMWinClient();
		CMClientStub cmStub = client.getClientStub();
		cmStub.setAppEventHandler(client.getClientEventHandler());
	}

}
