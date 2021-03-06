package FPLapp.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDaoFile implements UserDao{

	private ArrayList<User> Users;
	
	public UserDaoFile() {
		Users = new ArrayList<>();
		loadUsers();
	}

	@Override
    public ArrayList<User> getAllUsers() {
        return Users;
    }

	@Override
    public void updateUser(User user) {
    	Users.set(user.getID(), user);
    	saveUsers();
    }

	@Override
    public void addUser(User user) {
    	Users.add(user);
    }

	public void addUser(String name, String email, String password,String favTeam,  int id)
	{
		User user = new User();
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setID(id);
		user.setFavTeam(favTeam);
		Users.add(user);
		saveUsers();
	}

	@Override
    public boolean deleteUser(User user) {
        try
        {
        	Users.remove(user.getID());
        	int size = Users.size();
        	for(int id = 0; id < size; id++)
        	{
        		Users.get(id).setID(id);
        	}
        	saveUsers();
        	return true;
        }
        catch(Exception e)
        {
        	return false;
        }
    }

	public void loadUsers()
	{
		try {
			File db = new File("src\\FPLapp\\Database\\users.txt");
			Scanner reader = new Scanner(db);
			while(reader.hasNextLine())
			{
				User user = new User();
				user.setName(reader.nextLine());
				user.setEmail(reader.nextLine());
				user.setPassword(reader.nextLine());
				user.setRole(reader.nextLine());
				user.setFavTeam(reader.nextLine());
				user.setHasSquad(reader.nextLine());
				user.setID(Integer.parseInt(reader.nextLine()));
				addUser(user);
			}
			reader.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void saveUsers()
	{
		try {
			Writer fileWriter = new FileWriter("src\\FPLapp\\Database\\users.txt", false);
			for(User user : Users)
			{
				fileWriter.write(user.getName() + "\n");
				fileWriter.write(user.getEmail() + "\n");
				fileWriter.write(user.getPassword() + "\n");
				fileWriter.write(user.getRole() + "\n");
				fileWriter.write(user.getFavTeam() + "\n");
				if(user.HasSquad())
					fileWriter.write("true" + "\n");
				else
					fileWriter.write("false" + "\n");
				fileWriter.write(user.getID() + "\n");
			}
			fileWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
