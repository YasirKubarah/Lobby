package ricks;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;




public class MainLobby {
	
private List<GameLobby> ListGameLobby ;

private List<ActiveUsers> ListUsers ;


public String FinishGame(int GameID) {	//will be given to Game Engine delete the game lobby from the list
	if(GameIDCheck(GameID)) {
		return "Game with given ID does not exist";
		
	}else {
		for (GameLobby temp : ListGameLobby) {
			
			if(temp.getGameID()==GameID) 
			{ListGameLobby.remove(temp);
			return "Action completed successfully";	
			}
			}
		return "ListgameLobby list loop fail";
			
	}
}
public String NewGameLobby(int playerNumber,String username) {//add new Game lobby
	if(playerNumber>4||playerNumber<0) {
		return "Wrong number of Players";
	}
	if(CheckUserGameLobby(username,0) == false) {
		return "User already in Game";
	}
	int newGameID= GenarateGameID();
	GameLobby newone=new GameLobby(newGameID, playerNumber);
	newone.JoinGameLobby(GetActiveUser(username));
	GetActiveUser(username).setGameLobby(newGameID);
	ListGameLobby.add(newone);
	return  "Action completed successfully";
}

public String JoinGameLobby(String username,int GameID) {// add user to game lobby (join game lobby)
	boolean flag1= false;
	
			if(!GameIDCheck(GameID)) {
				return "INvalid GameID";
			}else {
				flag1=true;
			}
			//the specific game ID exists otherwise INvalid GameID
		
			if(CheckUserGameLobby(username,0) == false) {
				return "user already in game";
			}
	if(CheckUserGameLobby(username,0) == true) {//confirm user not in any game
		
		if(flag1==true) {
			ActiveUsers one = GetActiveUser(username);
			for (GameLobby temp : ListGameLobby) {
				
				if(temp.getGameID()==GameID) {
					one.setGameLobby(GameID);//Changing the gameID of user 
					temp.JoinGameLobby(one);//telling gameLobbby to add the user to its list
					return "Success";
				}}
		}
	}
	return "Fail";
}
private boolean GameIDCheck(int GameIDtest) {//checks if GameID exists
	for (GameLobby temp : ListGameLobby) {
		
		if(temp.getGameID()==GameIDtest) {
			return true;
		}
}
	return false;
	}

private int GenarateGameID() {

Random rand = new Random();
int number;
number=rand.nextInt();
for (GameLobby temp : ListGameLobby) {
	
	if(temp.getGameID()==number || number==0) {
		number = GenarateGameID();
	}	
}
	
	return number;
}
private ActiveUsers GetActiveUser(String username) {
	
	for (ActiveUsers temp : ListUsers) {
		if(temp.getUsername()==username) {
			return temp;
		}}
	return null;
}

private boolean CheckUserGameLobby(String username, int GameID) {
	
	for (ActiveUsers temp : ListUsers) {
		if(temp.getUsername()==username) {
			if(temp.getGameLobby()==GameID)
			{
				return true;
			}
		}
	}
	return false;
}

public String toJson() {
	
	Gson gson = new Gson();
	String json = gson.toJson(this);  
	return json; 
}
public void loadFromJson(String json) throws Throwable {
	Gson gson = new Gson();
	MainLobby new1=gson.fromJson(json, MainLobby.class);
	this.ListGameLobby=new1.ListGameLobby;
	this.ListUsers=new1.ListUsers;
	new1.finalize();
}
/*
public JSONObject GetGameList(String username) {
	
	JSONObject   value =  	new JSONObject();
	try {
		value.put("NumberOfGames", ListGameLobby.size());
	JSONArray Games=new JSONArray();
	for (GameLobby temp : ListGameLobby) {
		Games.put(temp.GetGameLobbyData());
	}
	value.put("GamesList", Games);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return value;
}
*/

}
