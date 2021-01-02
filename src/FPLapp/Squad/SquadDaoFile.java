package FPLapp.Squad;

import FPLapp.Player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SquadDaoFile implements SquadDao{
    ArrayList<Squad> Squads;

    public SquadDaoFile(){
        Squads = new ArrayList<Squad>();
        loadSquads();
    }
    @Override
    public ArrayList<Squad> getAllSquads() {
        return Squads;
    }

    @Override
    public void updateSquad(Squad squad) {
        Squads.set(squad.getID(), squad);
    }

    @Override
    public void addSquad(Squad squad) {
        Squads.add(squad);
        saveSquads();
    }

    @Override
    public boolean deleteSquad(Squad squad) {
        try
        {
            Squads.remove(squad.getID());
            saveSquads();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    @Override
    public void addSquad(String userEmail, String Name , ArrayList<Player> playersList) {
        Squad squad = new Squad();
        squad.setName(Name);
        squad.setOwner(userEmail);
        squad.setPlayers(playersList);
        saveSquads();
    }
    public void loadSquads()
    {
        try {
            File db = new File("src\\FPLapp\\Database\\squads.txt");
            Scanner reader = new Scanner(db);
            while(reader.hasNextLine())
            {
                Squad squad = new Squad();
                squad.setOwner(reader.nextLine());
                squad.setName(reader.nextLine());
                squad.setTotalValue(Double.parseDouble(reader.nextLine()));
                addSquad(squad);
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void saveSquads()
    {
        try {
            Writer fileWriter = new FileWriter("src\\FPLapp\\Database\\squads.txt", false);
            for(Squad squad : Squads)
            {
                fileWriter.write(squad.getOwner() + "\n");
                fileWriter.write(squad.getName() + "\n");
                fileWriter.write(squad.getTotalValue() + "\n");
                for(Player player : squad.getPlayers()){
                    fileWriter.write(player.getName() + "\t");
                    fileWriter.write(String.valueOf(player.getPos()) + "\n");
                }
            }
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}