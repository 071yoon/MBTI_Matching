import java.util.Iterator;
import javax.swing.JOptionPane;
import java.io.*;
import kr.ac.konkuk.ccslab.cm.entity.CMSessionInfo;
import kr.ac.konkuk.ccslab.cm.event.CMDataEvent;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMInterestEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEventField;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class CMWinClientEventHandler implements CMAppEventHandler{
	private CMWinClient m_client;
	private CMClientStub m_clientStub;

	private long m_lStartTime;
	private int m_nSimNum;		// for simulation of multiple sns content downloading
	private FileOutputStream m_fos;	// for storing downloading delay of multiple SNS content
	private PrintWriter m_pw;		//
	private int m_nCurrentServerNum;	// for distributed file processing
	private int m_nRecvPieceNum;		// for distributed file processing
	private boolean m_bDistFileProc;	// for distributed file processing
	private String m_strExt;			// for distributed file processing
	private String[] m_filePieces;		// for distributed file processing
	private boolean m_bReqAttachedFile;	// for storing the fact that the client requests an attachment
	private int m_nMinNumWaitedEvents;  // for checking the completion of asynchronous castrecv service
	private int m_nRecvReplyEvents;		// for checking the completion of asynchronous castrecv service
	
	private String m_strFileSender;
	private String m_strFileReceiver;
	private File[] m_arraySendFiles;
	private int m_nTotalNumFTPSessions;
	private int m_nCurNumFTPSessions;
	private boolean m_bStartC2CFTPSession;
	private int m_nTotalNumFilesPerSession;
	private int m_nCurNumFilesPerSession;
	
	private static int validity = 0;
	
	
	
	
	
	public CMWinClientEventHandler(CMClientStub clientStub, CMWinClient client)
	{
		m_client = client;
		m_clientStub = clientStub;
		m_lStartTime = 0;
		m_nSimNum = 0;
		m_fos = null;
		m_pw = null;
		m_nCurrentServerNum = 0;
		m_nRecvPieceNum = 0;
		m_bDistFileProc = false;
		m_strExt = null;
		m_filePieces = null;
		m_bReqAttachedFile = false;
		m_nMinNumWaitedEvents = 0;
		m_nRecvReplyEvents = 0;
		
		m_strFileSender = null;
		m_strFileReceiver = null;
		m_arraySendFiles = null;
		m_nTotalNumFTPSessions = 0;
		m_nCurNumFTPSessions = 0;
		m_bStartC2CFTPSession = false;
		m_nTotalNumFilesPerSession = 0;
		m_nCurNumFilesPerSession = 0;
	}
	
	
	public void setStartTime(long time)
	{
		m_lStartTime = time;
	}
	
	public long getStartTime()
	{
		return m_lStartTime;
	}
	
	public void setFileOutputStream(FileOutputStream fos)
	{
		m_fos = fos;
	}
	
	public FileOutputStream getFileOutputStream()
	{
		return m_fos;
	}
	
	public void setPrintWriter(PrintWriter pw)
	{
		m_pw = pw;
	}
	
	public PrintWriter getPrintWriter()
	{
		return m_pw;
	}
	
	public void setSimNum(int num)
	{
		m_nSimNum = num;
	}
	
	public int getSimNum()
	{
		return m_nSimNum;
	}
	
	public void setCurrentServerNum(int num)
	{
		m_nCurrentServerNum = num;
	}
	
	public int getCurrentServerNum()
	{
		return m_nCurrentServerNum;
	}
	
	public void setRecvPieceNum(int num)
	{
		m_nRecvPieceNum = num;
	}
	
	public int getRecvPieceNum()
	{
		return m_nRecvPieceNum;
	}
	
	public void setDistFileProc(boolean b)
	{
		m_bDistFileProc = b;
	}
	
	public boolean isDistFileProc()
	{
		return m_bDistFileProc;
	}
	
	public void setFileExtension(String ext)
	{
		m_strExt = ext;
	}
	
	public String getFileExtension()
	{
		return m_strExt;
	}
	
	public void setFilePieces(String[] pieces)
	{
		m_filePieces = pieces;
	}
	
	public String[] getFilePieces()
	{
		return m_filePieces;
	}
	
	public void setReqAttachedFile(boolean bReq)
	{
		m_bReqAttachedFile = bReq;
	}
	
	public boolean isReqAttachedFile()
	{
		return m_bReqAttachedFile;
	}

	public void setMinNumWaitedEvents(int num)
	{
		m_nMinNumWaitedEvents = num;
	}
	
	public int getMinNumWaitedEvents()
	{
		return m_nMinNumWaitedEvents;
	}
	
	public void setRecvReplyEvents(int num)
	{
		m_nRecvReplyEvents = num;
	}
	
	public int getRecvReplyEvents()
	{
		return m_nRecvReplyEvents;
	}
	
	public void setFileSender(String strFileSender)
	{
		m_strFileSender = strFileSender;
	}
	
	public String getFileSender()
	{
		return m_strFileSender;
	}
	
	public void setFileReceiver(String strFileReceiver)
	{
		m_strFileReceiver = strFileReceiver;
	}
	
	public String getFileReceiver()
	{
		return m_strFileReceiver;
	}
	
	public void setSendFileArray(File[] arraySendFiles)
	{
		m_arraySendFiles = arraySendFiles;
	}
	
	public File[] getSendFileArray()
	{
		return m_arraySendFiles;
	}
	
	public void setTotalNumFTPSessions(int nNum)
	{
		m_nTotalNumFTPSessions = nNum;
	}
	
	public int getTotalNumFTPSessions()
	{
		return m_nTotalNumFTPSessions;
	}
	
	public void setCurNumFTPSessions(int nNum)
	{
		m_nCurNumFTPSessions = nNum;
	}
	
	public int getCurNumFTPSessions()
	{
		return m_nCurNumFTPSessions;
	}
	
	public void setIsStartC2CFTPSession(boolean bStart)
	{
		m_bStartC2CFTPSession = bStart;
	}
	
	public boolean isStartC2CFTPSession()
	{
		return m_bStartC2CFTPSession;
	}
	
	public void setTotalNumFilesPerSession(int nNum)
	{
		m_nTotalNumFilesPerSession = nNum;
	}
	
	public int getTotalNumFilesPerSession()
	{
		return m_nTotalNumFilesPerSession;
	}
	
	public void setCurNumFilesPerSession(int nNum)
	{
		m_nCurNumFilesPerSession = nNum;
	}
	
	public int getCurNumFilesPerSession()
	{
		return m_nCurNumFilesPerSession;
	}
		
	//////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void processEvent(CMEvent cme) {
		switch(cme.getType())
		{
		case CMInfo.CM_SESSION_EVENT:
			processSessionEvent(cme);
			break;
		case CMInfo.CM_INTEREST_EVENT:
			processInterestEvent(cme);
			break;
		case CMInfo.CM_DATA_EVENT:
			processDataEvent(cme);
			break;
		case CMInfo.CM_DUMMY_EVENT:
			processDummyEvent(cme);
			break;
		case CMInfo.CM_USER_EVENT:
			processUserEvent(cme);
			break;
		default:
			return;
		}	
	}
	
	private void processSessionEvent(CMEvent cme)
	{
		long lDelay = 0;
		CMSessionEvent se = (CMSessionEvent)cme;
		switch(se.getID())
		{
		case CMSessionEvent.LOGIN_ACK:
			if(se.isValidUser() == 0)
			{
				printMessage("등록된 아이디가 아닙니다. \n");
				printMessage("회원가입 후에 서비스를 이용해주세요. (^_^) \n");
				
			}
			else if(se.isValidUser() == -1)
			{
				printMessage("다른 클라이언트에서 이미 로그인 중인 계정입니다.\n");
			}
			else
			{
				printMessage("디폴트 서버에 인증 성공하였습니다.\n");
				CMInteractionInfo interInfo = m_clientStub.getCMInfo().getInteractionInfo();
				
				// Change the title of the client window
				m_client.setTitle("사용자 ["+interInfo.getMyself().getName()+"]");

				// Set the appearance of buttons in the client frame window
				m_client.setButtonsAccordingToClientState();
			}
			break;
		case CMSessionEvent.RESPONSE_SESSION_INFO:
			lDelay = System.currentTimeMillis() - m_lStartTime;
			printMessage("RESPONSE_SESSION_INFO delay: "+lDelay+" ms.\n");
			processRESPONSE_SESSION_INFO(se);
			break;
		case CMSessionEvent.SESSION_TALK:
			//채팅창에 채팅 내용 출력 
			printMessage2("<"+se.getUserName()+">: "+se.getTalk()+"\n");
			break;
		case CMSessionEvent.JOIN_SESSION_ACK:
			lDelay = System.currentTimeMillis() - m_lStartTime;
			m_client.setButtonsAccordingToClientState();
			break;
		case CMSessionEvent.ADD_NONBLOCK_SOCKET_CHANNEL_ACK:
			if(se.getReturnCode() == 0)
			{
				printMessage("Adding a nonblocking SocketChannel("+se.getChannelName()+","+se.getChannelNum()
						+") failed at the server!\n");
			}
			else
			{
				printMessage("Adding a nonblocking SocketChannel("+se.getChannelName()+","+se.getChannelNum()
						+") succeeded at the server!\n");
			}
			break;
		case CMSessionEvent.ADD_BLOCK_SOCKET_CHANNEL_ACK:
			if(se.getReturnCode() == 0)
			{
				printMessage("Adding a blocking socket channel ("+se.getChannelName()+","+se.getChannelNum()
					+") failed at the server!\n");
			}
			else
			{
				printMessage("Adding a blocking socket channel("+se.getChannelName()+","+se.getChannelNum()
					+") succeeded at the server!\n");
			}
			break;
		case CMSessionEvent.REMOVE_BLOCK_SOCKET_CHANNEL_ACK:
			if(se.getReturnCode() == 0)
			{
				printMessage("Removing a blocking socket channel ("+se.getChannelName()+","+se.getChannelNum()
					+") failed at the server!\n");
			}
			else
			{
				printMessage("Removing a blocking socket channel("+se.getChannelName()+","+se.getChannelNum()
					+") succeeded at the server!\n");
			}
			break;
		case CMSessionEvent.UNEXPECTED_SERVER_DISCONNECTION:
			m_client.printStyledMessage("Unexpected disconnection from ["
					+se.getChannelName()+"] with key["+se.getChannelNum()+"]!\n", "bold");
			m_client.setButtonsAccordingToClientState();
			m_client.setTitle("CM Client");
			break;
		case CMSessionEvent.INTENTIONALLY_DISCONNECT:
			m_client.printStyledMessage("Intentionally disconnected all channels from ["
					+se.getChannelName()+"]!\n", "bold");
			m_client.setButtonsAccordingToClientState();
			m_client.setTitle("CM Client");
			break;
		default:
			return;
		}
	}
	
	private void processRESPONSE_SESSION_INFO(CMSessionEvent se)
	{
		Iterator<CMSessionInfo> iter = se.getSessionInfoList().iterator();

		printMessage(String.format("%-60s%n", "------------------------------------------------------------"));
		printMessage(String.format("%-20s%-20s%-10s%-10s%n", "name", "address", "port", "user num"));
		printMessage(String.format("%-60s%n", "------------------------------------------------------------"));

		while(iter.hasNext())
		{
			CMSessionInfo tInfo = iter.next();
			printMessage(String.format("%-20s%-20s%-10d%-10d%n", tInfo.getSessionName(), tInfo.getAddress(), 
					tInfo.getPort(), tInfo.getUserNum()));
		}
	}
	
	private void processInterestEvent(CMEvent cme)
	{
		CMInterestEvent ie = (CMInterestEvent) cme;
		switch(ie.getID())
		{
		case CMInterestEvent.USER_TALK:
			printMessage2("<"+ie.getUserName()+">: "+ie.getTalk()+"\n");
			break;
		default:
			return;
		}
	}
	
	private void processDataEvent(CMEvent cme)		//HH
	{
		CMDataEvent de = (CMDataEvent) cme;
		switch(de.getID())
		{
		case CMDataEvent.NEW_USER:
			m_client.printStyledMessage("["+de.getUserName()+"] entered the room! \n", "bold");
			if(validity==1) {
				example3();//회원가입 기능 
				validity = 0;
			}
			
			break;
		case CMDataEvent.REMOVE_USER:
			m_client.printStyledMessage("["+de.getUserName()+"] left the room! \n", "bold");

			break;
		default:
			return;
		}
	}
	
	private void processDummyEvent(CMEvent cme)	//HH 
	{
		CMDummyEvent due = (CMDummyEvent) cme;

		if(due.getSender().equals("SERVER"))
		printMessage(due.getDummyInfo()+"\n");
		return;
	}
	
	private void processUserEvent(CMEvent cme)
	{
		
		CMUserEvent ue = (CMUserEvent) cme;

		if(ue.getStringID().equals("requireRegister"))
		{
			printMessage("등록된 아이디가 아닙니다.\n");
			printMessage("회원가입 후에 서비스를 이용해주세요. (^_^) \n");
			validity = 1;
			
		}
		else if(ue.getStringID().equals("registrationFail"))
		{
			int option2 = JOptionPane.showConfirmDialog(null, "중복된 아이디입니다.", "경고", JOptionPane.OK_CANCEL_OPTION);
			if(option2 == JOptionPane.OK_OPTION) 
		    {example3();
			return;
		    }
			printMessage("중복된 아이디가 아닙니다.\n");
			printMessage("회원가입 후에 서비스를 이용해주세요. (^_^) \n");
			
		}
		else if(ue.getStringID().equals("registrationSuccess"))
		{
			int option2 = JOptionPane.showConfirmDialog(null, "회원가입 완료하였습니다. 로그인 후 사용해주세요", "축하합니다", JOptionPane.OK_CANCEL_OPTION);
			if(option2 == JOptionPane.OK_OPTION) {
				testLogoutDS();
				loginExample();
			    return;
		    }
			if(option2 == JOptionPane.CANCEL_OPTION) {
				return;
		    }
		else
		{
			printMessage("CMUserEvent received from ["+ue.getSender()+"], strID("+ue.getStringID()+")\n");
			printMessage(String.format("%-5s%-20s%-10s%-20s%n", "Type", "Field", "Length", "Value"));
			printMessage("-----------------------------------------------------\n");
			Iterator<CMUserEventField> iter = ue.getAllEventFields().iterator();
			while(iter.hasNext())
			{
				CMUserEventField uef = iter.next();
				if(uef.nDataType == CMInfo.CM_BYTES)
				{
					printMessage(String.format("%-5s%-20s%-10d", uef.nDataType, uef.strFieldName, 
										uef.nValueByteNum));
					for(int i = 0; i < uef.nValueByteNum; i++)
					{
						//not yet
					}
					printMessage("\n");
				}
				else
				{
					printMessage(String.format("%-5d%-20s%-10d%-20s%n", uef.nDataType, uef.strFieldName, 
							uef.strFieldValue.length(), uef.strFieldValue));
				}
			}
		}
		}
		
	}
	
	private void loginExample() {
		m_client.loginExample();
		
	}

	private void testLogoutDS() {
		m_client.testLogoutDS();
		
	}

	private void printMessage(String strText)
	{
		m_client.printMessage(strText);
		
		return;
	}
	private void printMessage2(String strText)
	{
		m_client.printMessage2(strText);
		
		return;
	}
	
	private void example3()
	{
		m_client.example3();
	}

}
