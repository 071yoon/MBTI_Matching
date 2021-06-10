import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.io.*;

import score.Score;				//HH

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMInterestEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEventField;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class CMWinServerEventHandler implements CMAppEventHandler {
	private CMWinServer m_server;
	private CMServerStub m_serverStub;
	
	public CMWinServerEventHandler(CMServerStub serverStub, CMWinServer server)
	{
		m_server = server;
		m_serverStub = serverStub;
	}
	
	@Override
	public void processEvent(CMEvent cme) {
		// TODO Auto-generated method stub
		switch(cme.getType())
		{
		case CMInfo.CM_SESSION_EVENT:
			processSessionEvent(cme);
			break;
		case CMInfo.CM_INTEREST_EVENT:
			processInterestEvent(cme);
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
		CMSessionEvent se = (CMSessionEvent) cme;
		boolean ret = false;
		switch(se.getID())
		{
		case CMSessionEvent.LOGIN:
			printMessage("["+se.getUserName()+"] requests login.\n");
			printMessage("id is ["+se.getUserName()+"] pw is ["+se.getPassword()+"].\n");
			String name = se.getUserName();
			
			try {
				File file = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\user.txt");
				FileReader filereader = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(filereader);
				String line = "";
				ArrayList<String> lists = new ArrayList<String>();
				int num = 0;
				while((line=bufReader.readLine()) != null) {
					num++;
					StringTokenizer st = new StringTokenizer(line, "\t");
					while(st.hasMoreTokens())
						lists.add(st.nextToken());
						
					
					
				}
				for(int i =1; i<num; i++) {
					System.out.println(lists.get(11*i));
					if(name.equals(lists.get(11*i))) ret = true;
				}
			
				bufReader.close();
				
			}catch (FileNotFoundException e) {
				System.out.println(e);
			}catch (IOException e) {
				System.out.println(e);
			}
			if(!ret)
			{
				printMessage("["+se.getUserName()+"] authentication fails!\n");
				
				CMUser myself = m_serverStub.getCMInfo().getInteractionInfo().getMyself();
				CMUserEvent ue = new CMUserEvent();
				
				ue.setStringID("requireRegister");
				
				ue.setHandlerSession(myself.getCurrentSession());
				ue.setHandlerGroup(myself.getCurrentGroup());
				
				ue.setEventField(CMInfo.CM_INT, "회원가입", String.valueOf(0));
				

				m_serverStub.send(ue, se.getUserName());
				ue.removeAllEventFields();
				ue = null;
				
			}
			else
			{
				printMessage("["+se.getUserName()+"] authentication succeeded.\n");
			}
			/*
		
			if(confInfo.isLoginScheme())
			{
				// user authentication...
				// CM DB must be used in the following authentication..
				//boolean ret = name.equals(name2);
				if(!ret)
				{
					printMessage("["+se.getUserName()+"] authentication fails!\n");
					m_serverStub.replyEvent(cme, 0);
				}
				else
				{
					printMessage("["+se.getUserName()+"] authentication succeeded.\n");
					m_serverStub.replyEvent(cme, 1);
				}
				
			}*/
			
			break;
		case CMSessionEvent.LOGOUT:
			printMessage("["+se.getUserName()+"] logs out.\n");
			break;
		case CMSessionEvent.JOIN_SESSION:
			printMessage("["+se.getUserName()+"] requests to join session("+se.getSessionName()+").\n");
			break;
		case CMSessionEvent.LEAVE_SESSION:
			printMessage("["+se.getUserName()+"] leaves a session("+se.getSessionName()+").\n");
			break;
		case CMSessionEvent.ADD_NONBLOCK_SOCKET_CHANNEL:
			printMessage("["+se.getChannelName()+"] request to add a nonblocking SocketChannel with key("
			+se.getChannelNum()+").\n");
			break;
		case CMSessionEvent.UNEXPECTED_SERVER_DISCONNECTION:
			m_server.printStyledMessage("Unexpected disconnection from ["
					+se.getChannelName()+"] with key["+se.getChannelNum()+"]!\n", "bold");
			break;
		case CMSessionEvent.INTENTIONALLY_DISCONNECT:
			m_server.printStyledMessage("Intentionally disconnected all channels from ["
					+se.getChannelName()+"]!\n", "bold");
			break;
		default:
			return;
		}
	}
	
	private void processInterestEvent(CMEvent cme)
	{
		CMInterestEvent ie = (CMInterestEvent) cme;
		switch(ie.getID())
		{
		case CMInterestEvent.USER_ENTER:
			printMessage("["+ie.getUserName()+"] enters group("+ie.getCurrentGroup()+") in session("
					+ie.getHandlerSession()+").\n");
			break;
		case CMInterestEvent.USER_LEAVE:
			printMessage("["+ie.getUserName()+"] leaves group("+ie.getHandlerGroup()+") in session("
					+ie.getHandlerSession()+").\n");
			break;
		case CMInterestEvent.USER_TALK:
			printMessage("("+ie.getHandlerSession()+", "+ie.getHandlerGroup()+")\n");
			printMessage("<"+ie.getUserName()+">: "+ie.getTalk()+"\n");
		break;
		default:
			return;
		}
	}
	
	public void sendCMDummyEvent(String msg, String sender, String session, String group)		//HH
	{
		String strMessage = msg;
		String strTarget = sender;
		String strSession = session;
		String strGroup = group;
		CMDummyEvent de = null;
		printMessage("====== send " + sender + " data \n");


			
			de = new CMDummyEvent();
			de.setDummyInfo(strMessage);
			de.setHandlerSession(strSession);
			de.setHandlerGroup(strGroup);
			
			if(!strTarget.isEmpty())
			{
				m_serverStub.send(de, strTarget);
			}
			else
			{
				if(strSession.isEmpty()) strSession = null;
				if(strGroup.isEmpty()) strGroup = null;
				m_serverStub.cast(de, strSession, strGroup);
			}			
		}
	
	private void processDummyEvent(CMEvent cme)		//HH, 이벤트 처리
	{
		CMDummyEvent due = (CMDummyEvent) cme;

		String str;
		str = due.getDummyInfo();					//str = 서비스 선택		
		if (str.equals("1")) {						//유저가 새로운 파트너 정보 원할 때 
					
			printMessage("["+due.getSender()+"] requested New Partner \n");			
			
	        try{
	          
	            File file = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\user.txt");		//유저 정보 파일 열기
	            
	            FileReader filereader_1 = new FileReader(file);
	            BufferedReader bufReader_1 = new BufferedReader(filereader_1);
	            String line_1 = new String("");
	            
	            
	            while((line_1 = bufReader_1.readLine()) != null){					// 문서 끝까지 한줄씩(엔터로 구분) 읽어오기
	            	//StringTokenizer st = new StringTokenizer (line, "\t");		// 탭을 구분으로 하나씩 토큰화
	            	//String user_name = st.nextToken();							// 첫번째 토큰 = 사용자 이름
	            	String [] user_info = line_1.split("\t");
	            	String user_name = user_info[0];
	            	String user_gender = user_info[2];
	    	        String user_age = user_info[3];
	    	        String user_height = user_info[4];
	    	    	String user_weight = user_info[5];
	            	String user_MBTI = user_info[6];
	            	String user_hobby = user_info[7];
	            	String user_page = user_info[8];
	            	String user_pheight = user_info[9];
	            	String user_pweight = user_info[10];
	            	
	            	if(user_name.equals("Name")) {
	            	String user = user_name  +"\t"+ user_age +"\t"+ user_height +"\t"+ user_weight +"\t"+ user_MBTI + "\t" + user_hobby + "\tMatch";
	            	sendCMDummyEvent(user, due.getSender(), due.getHandlerSession(), due.getHandlerGroup());
	            	}
	            	
	            	if(user_name.equals(due.getSender())) {
	            	
			            FileReader filereader_2 = new FileReader(file);
			            BufferedReader bufReader_2 = new BufferedReader(filereader_2);
			            String line_2 = new String("");
			            
			            line_2 = bufReader_2.readLine();
		            	
			            while((line_2 = bufReader_2.readLine()) != null) {
			            	String [] partner_info = line_2.split("\t");
			            	String partner_name = partner_info[0];
			            	String partner_gender = partner_info[2];
			    	        String partner_age = partner_info[3];
			    	        String partner_height = partner_info[4];
			    	    	String partner_weight = partner_info[5];
			            	String partner_MBTI = partner_info[6];
			            	String partner_hobby = partner_info[7];
			            	String partner = partner_name +"\t"+ partner_age +"\t"+ partner_height +"\t"+ partner_weight +"\t"+ partner_MBTI + "\t" + partner_hobby;
			            	int score = 0;
			           
			            	Score match_score = new Score();
			            	score += match_score.age(user_page, partner_age);
			            	score += match_score.height(user_pheight, partner_height);
			            	score += match_score.weight(user_pweight, partner_weight);
			            	score += match_score.MBTI(user_MBTI, partner_MBTI);
			            	score += match_score.hobby(user_hobby, partner_hobby);
			            	if(partner_name.equals(due.getSender()) == false && partner_gender.equals(user_gender) == false )
			            		sendCMDummyEvent(partner + "\t" + score +"%" , due.getSender(), due.getHandlerSession(), due.getHandlerGroup());
			            }
			            bufReader_2.close();

	            	}      
	            }
	            bufReader_1.close();
	        }
	        catch (FileNotFoundException e) {
	            // TODO: handle exception
	        }
	        catch(IOException e){
	            System.out.println(e);
	        }
			
		} 
				
		else if (str.equals("2")) {						// 유저가 매칭된 파트너 정보 원할 때
			
			printMessage("["+due.getSender()+"] requested Matched Partner \n");
					
	        try{
	            
	            File file = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\"+due.getSender()+"_partner.txt");	// 해당 유저의 파트너 선택 목록 파일 열기
	            
	            if(file.exists() == true) {					// 파일 존재 하면
	            
	            	FileReader filereader = new FileReader(file);
	           
	            	BufferedReader bufReader = new BufferedReader(filereader);
	            	String Check_Name = "";
	            	String check = "";
	            
	            	while((Check_Name = bufReader.readLine()) != null){					// 유저가 선택한 파트너 한명씩
	            	
	            		File file1 = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\"+Check_Name+"_partner.txt");	// 유저가 선택한 파트너의 파트너 선택 목록 파일 열기
	            		if(file1.exists() == false)
	    	            	sendCMDummyEvent("No Matched Partner for [" + due.getSender() + "]", due.getSender(), due.getHandlerSession(), due.getHandlerGroup());

	            		FileReader filereader1 = new FileReader(file1);
	            		BufferedReader bufReader1 = new BufferedReader(filereader1);
		            
	            		while((check = bufReader1.readLine()) != null)	{
		            
	            			if(check.equals(due.getSender())) {					//만약 서로 매칭 되었다면
	            				sendCMDummyEvent(Check_Name, due.getSender(), due.getHandlerSession(), due.getHandlerGroup());	// 해당 파트너 이름 보내기
	            			}
		            	
	            		}
	            		bufReader1.close();
	            	}
	            	bufReader.close();
	            }
	            
	            else {			// 파일 존재하지 않으면
	            	
	            	sendCMDummyEvent("This is end of Matched Partner list", due.getSender(), due.getHandlerSession(), due.getHandlerGroup());
	            }
	            
	        }
	        catch (FileNotFoundException e) {
	            // TODO: handle exception
	        }
	        catch(IOException e){
	            System.out.println(e);
	        }
	        
	    	}
		
		else {		// 유저가 파트너 선택하연
			
			File file = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\"+due.getSender()+"_partner.txt");					//해당 유저의 파트너 선택 목록 찾기 또는 생성
		
				
			
				try {    
				
					if(file.exists() == false){		// 입력된 (유저가 선택한) 파트너 정보 추가
		
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
						bufferedWriter.write(str);
						bufferedWriter.close();
						sendCMDummyEvent("You choosed [" + str + "]", due.getSender(), due.getHandlerSession(), due.getHandlerGroup());
					}
					else {
						BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));
						bufferedWriter.newLine();
						bufferedWriter.write(str);
						bufferedWriter.close();
						sendCMDummyEvent("You choosed [" + str + "]", due.getSender(), due.getHandlerSession(), due.getHandlerGroup());

					}

				}
				catch (IOException e) {
			    e.printStackTrace();
				}
			
		}
		
		return;
	}
	
	private void processUserEvent(CMEvent cme)
	{
		CMUserEvent ue = (CMUserEvent) cme;
		
		
		if(ue.getStringID().equals("sendmessage"))
		{
			System.out.println("event ID: "+ue.getStringID()+")\n");
			
			int nField = Integer.parseInt( ue.getEventField(CMInfo.CM_INT, "나이"));
			System.out.println("나이: "+ nField);
			printMessage("Received user event 'sendmessage', simulation num("+nField+")\n");
		}
		
		else if(ue.getStringID().equals("register"))
		{
			String nField = ue.getEventField(CMInfo.CM_STR, "이름");
			String pField = ue.getEventField(CMInfo.CM_INT, "비밀번호");
			String gField = ue.getEventField(CMInfo.CM_STR, "성별");
			String aField = ue.getEventField(CMInfo.CM_INT, "나이");
			String hField = ue.getEventField(CMInfo.CM_INT, "키");
			String wField = ue.getEventField(CMInfo.CM_INT, "몸무게");
			String mField = ue.getEventField(CMInfo.CM_STR, "mbti");
            String yField = ue.getEventField(CMInfo.CM_STR, "취미");
            String paField = ue.getEventField(CMInfo.CM_INT, "이상형 나이");
            String phField = ue.getEventField(CMInfo.CM_INT, "이상형 키");
			String pwField = ue.getEventField(CMInfo.CM_INT, "이상형 몸무게");
			
			String write =  nField+'\t'+pField+'\t'+gField+'\t'+aField+'\t'+hField+'\t'+wField+'\t'+mField+'\t'+yField+'\t'+paField+'\t'+phField+'\t'+pwField;
			
			boolean ret2 = false;
			
			
			try {
				File file = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\user.txt");
				FileReader filereader = new FileReader(file);
				BufferedReader bufReader = new BufferedReader(filereader);
				String line = "";
				ArrayList<String> lists = new ArrayList<String>();
				int num = 0;
				while((line=bufReader.readLine()) != null) {
					num++;
					StringTokenizer st = new StringTokenizer(line, "\t");
					while(st.hasMoreTokens())
						lists.add(st.nextToken());
						
					
					
				}
				lists.add("/t");	//HH
				for(int i =1; i<num; i++) {
					System.out.println(lists.get(11*i));
					
					if(nField.equals(lists.get(11*i))) ret2 = true;
				}
			
				bufReader.close();
				
			}catch (FileNotFoundException e) {
				System.out.println(e);
			}catch (IOException e) {
				System.out.println(e);
			}
			
			if(ret2) {
				
                printMessage("["+nField+"] 회원가입 실패!\n");
				
				CMUser myself = m_serverStub.getCMInfo().getInteractionInfo().getMyself();
				CMUserEvent ue2 = new CMUserEvent();
				
				ue2.setStringID("registrationFail");
				
				ue2.setHandlerSession(myself.getCurrentSession());
				ue2.setHandlerGroup(myself.getCurrentGroup());
				
				ue2.setEventField(CMInfo.CM_INT, "회원가입 실패", String.valueOf(0));
				

				m_serverStub.send(ue2, ue.getSender());
				ue2.removeAllEventFields();
				ue2 = null;
				
			}
			else {	
			    try {
				    File file = new File("C:\\Users\\플래시\\Desktop\\MBTI_FINAL_Simplified\\User_data\\user.txt");
				
				    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true));

				    bufferedWriter.newLine();//칸 내림
				    bufferedWriter.write(write);
				    bufferedWriter.write("	");		//HH
				    bufferedWriter.flush();
				
				    bufferedWriter.close();
				
			    }catch (FileNotFoundException z) {
				    System.out.println(z);
			    }catch (IOException z) {
				    System.out.println(z);
			    }
			    
                printMessage("["+nField+"] 회원가입 성공!\n");
			    
			    CMUser myself = m_serverStub.getCMInfo().getInteractionInfo().getMyself();
				CMUserEvent ue2 = new CMUserEvent();
				
				ue2.setStringID("registrationSuccess");
				
				ue2.setHandlerSession(myself.getCurrentSession());
				ue2.setHandlerGroup(myself.getCurrentGroup());
				
				ue2.setEventField(CMInfo.CM_INT, "회원가입 성공", String.valueOf(0));
				

				m_serverStub.send(ue2, ue.getSender());
				ue2.removeAllEventFields();
				ue2 = null;
			}
			
			printMessage("event ID: "+ue.getStringID()+"\n");
			
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
						// not yet
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
		return;
	}
	
	private void printMessage(String strText)
	{
		m_server.printMessage(strText);
	}
}