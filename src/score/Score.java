package score;


public class Score {
	public int MBTI(String userMBTI, String partnerMBTI) {
		int score = 0;
		if (userMBTI.equals("INFP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) || 
				(partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("INTP")) || (partnerMBTI.equals("ENTP")))
				score += 35;
			if ((partnerMBTI.equals("ENFJ")) || (partnerMBTI.equals("ENTJ")))
				score += 50;
			else
				score += 0;
		}
		else if (userMBTI.equals("ENFP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INTJ")) || 
				(partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("INTP")) || (partnerMBTI.equals("ENTP")))
				score += 35;
			if ((partnerMBTI.equals("INFJ")) || (partnerMBTI.equals("INTJ")))
				score += 50;
			else
				score += 0;
		}
		else if (userMBTI.equals("INFJ")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("INFJ")) || (partnerMBTI.equals("ENFJ")) || 
				(partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("INTP")))
				score += 35;
			if ((partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("ENTP")))
				score += 50;
			else
				score += 0;
		}
		else if (userMBTI.equals("ENFJ")) {
			if ((partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) || (partnerMBTI.equals("ENFJ")) || 
					(partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("INTP")))
					score += 35;
				if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ISFP")))
					score += 50;
				else
					score += 0;
		}
		else if (userMBTI.equals("INTJ")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("INFJ")) || (partnerMBTI.equals("ENFJ")) || 
				(partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("INTP")))
				score += 35;
			if ((partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("ENTP")))
				score += 50;
			if ((partnerMBTI.equals("ISFP")) || (partnerMBTI.equals("ESFP")) || (partnerMBTI.equals("ISTP")) || 
				(partnerMBTI.equals("ESTP")))
				score += 25;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ISTJ")) || 
				(partnerMBTI.equals("ESTJ")))
				score += 10;
			else
				score += 0;
		}
		else if (userMBTI.equals("ENTJ")) {
			if ((partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) || (partnerMBTI.equals("ENFJ")) || 
				(partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("ENTP")))
				score += 35;
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("INTP")))
				score += 50;
			else
				score += 25;
		}
		else if (userMBTI.equals("INTP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) || 
				(partnerMBTI.equals("ENFJ")) || (partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("INTP")) ||
				(partnerMBTI.equals("ENTP")))
				score += 35;
			if ((partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("ESTJ")))
				score += 50;
			if ((partnerMBTI.equals("ISFP")) || (partnerMBTI.equals("ESFP")) || (partnerMBTI.equals("ISTP")) || 
				(partnerMBTI.equals("ESTP")))
				score += 25;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ISTJ")))
				score += 10;
			else
				score += 0;
		}
		else if (userMBTI.equals("INTP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) || 
				(partnerMBTI.equals("ENFJ")) || (partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("INTP")) ||
				(partnerMBTI.equals("ENTP")))
				score += 35;
			if ((partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("ESTJ")))
				score += 50;
			if ((partnerMBTI.equals("ISFP")) || (partnerMBTI.equals("ESFP")) || (partnerMBTI.equals("ISTP")) || 
				(partnerMBTI.equals("ESTP")))
				score += 25;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ISTJ")))
				score += 10;
			else
				score += 0;
		}
		else if (userMBTI.equals("ENTP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("ENFJ")) || 
				(partnerMBTI.equals("ENFJ")) || (partnerMBTI.equals("INTP")) || (partnerMBTI.equals("ENTP")))
				score += 35;
			if ((partnerMBTI.equals("INFJ")) || (partnerMBTI.equals("INTJ")))
				score += 50;
			if ((partnerMBTI.equals("ISFP")) || (partnerMBTI.equals("ESFP")) || (partnerMBTI.equals("ISTP")) || 
				(partnerMBTI.equals("ESTP")))
				score += 25;
			else
				score += 10;
		}
		else if (userMBTI.equals("ISFP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")))
				score += 0;
			if ((partnerMBTI.equals("ENFJ")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ESTJ")))
				score += 50;
			if ((partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("INTP")) || 
				(partnerMBTI.equals("ENTP")) || (partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ISTJ")))
				score += 25;
			else
					score += 10;
		}
		else if (userMBTI.equals("ESFP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) ||
				(partnerMBTI.equals("ENFJ")))
				score += 0;
			if ((partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("INTP")) || 
				(partnerMBTI.equals("ENTP")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ESTJ")))
					score += 25;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ISTJ")))
				score += 50;
			else
				score += 10;
		}
		else if (userMBTI.equals("ISTP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) ||
				(partnerMBTI.equals("ENFJ")))
				score += 0;
			if ((partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("INTP")) || 
				(partnerMBTI.equals("ENTP")) || (partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ISTJ")))
					score += 25;
			if ((partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ESTJ")))
				score += 50;
			else
				score += 10;
		}
		else if (userMBTI.equals("ESTP")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) ||
				(partnerMBTI.equals("ENFJ")))
				score += 0;
			if ((partnerMBTI.equals("INTJ")) || (partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("INTP")) || 
				(partnerMBTI.equals("ENTP")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ESTJ")))
					score += 5;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ISTJ")))
				score += 50;
			else
				score += 10;
		}
		else if (userMBTI.equals("ISFJ")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) ||
				(partnerMBTI.equals("ENFJ")))
				score += 0;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ISTJ")) ||
				(partnerMBTI.equals("ESTJ")))
				score += 35;
			if ((partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("ISFP")) || (partnerMBTI.equals("ISTP")))
					score += 5;
			if ((partnerMBTI.equals("ESFP")) || (partnerMBTI.equals("ESTP")))
				score += 50;
			else
				score += 10;
		}
		else if (userMBTI.equals("ESFJ")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) ||
				(partnerMBTI.equals("ENFJ")))
				score += 0;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ISTJ")) ||
				(partnerMBTI.equals("ESTJ")))
				score += 35;
			if ((partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("ESFP")) || (partnerMBTI.equals("ESTP")))
					score += 25;
			if ((partnerMBTI.equals("ISFP")) || (partnerMBTI.equals("ISTP")))
				score += 50;
			else
				score += 10;
		}
		else if (userMBTI.equals("ISTJ")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) ||
				(partnerMBTI.equals("ENFJ")))
				score += 0;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ISTJ")) ||
				(partnerMBTI.equals("ESTJ")))
				score += 35;
			if ((partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("ISFP")) || (partnerMBTI.equals("ISTP")))
					score += 5;
			if ((partnerMBTI.equals("ESFP")) || (partnerMBTI.equals("ESTP")))
				score += 50;
			else
				score += 10;
		}
		else if (userMBTI.equals("ESTJ")) {
			if ((partnerMBTI.equals("INFP")) || (partnerMBTI.equals("ENFP")) || (partnerMBTI.equals("INFJ")) ||
				(partnerMBTI.equals("ENFJ")))
				score += 0;
			if ((partnerMBTI.equals("ISFJ")) || (partnerMBTI.equals("ESFJ")) || (partnerMBTI.equals("ISTJ")) ||
				(partnerMBTI.equals("ESTJ")))
				score += 35;
			if ((partnerMBTI.equals("ENTJ")) || (partnerMBTI.equals("ESFP")) || (partnerMBTI.equals("ESTP")))
					score += 5;
			if ((partnerMBTI.equals("INTP")) || (partnerMBTI.equals("ISFP")) || (partnerMBTI.equals("ISTP")))
				score += 50;
			else
				score += 10;
		}

		return score;
	}
	
	public int age(String User_age, String Partner_age) {
		int x = Integer.parseInt(User_age);
		int y = Integer.parseInt(Partner_age);
		int score = 1;
		
		if(y>=(x-2)&&y<=(x+2))
			score = 10;
		return score;
	}
	
	public int height(String User_height, String Partner_height) {
		int x = Integer.parseInt(User_height);
		int y = Integer.parseInt(Partner_height);
		int score = 1;
		
		if(y>=(x-3)&&y<=(x+3))
			score = 10;
		return score;
	}
	
	public int weight(String User_weight, String Partner_weight) {
		int x = Integer.parseInt(User_weight);
		int y = Integer.parseInt(Partner_weight);
		int score = 0;
		
		if(y>=(x-3)&&y<=(x+3))
			score = 10;
		return score;
		
	}
	
	public int hobby(String User_hobby, String Partner_hobby) {
		int score = 0;
		
		if(User_hobby.equals(Partner_hobby))
			score = 10;
		return score;
	}
	
}
