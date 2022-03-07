package team;

import coach.Coach;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import league.League;
import shared.DB;

public class Team {
 
    private final SimpleStringProperty teamID;
    private final SimpleStringProperty teamName;
    private final SimpleBooleanProperty canDelete = new SimpleBooleanProperty(true);
    private final SimpleObjectProperty<League> league;
    private final SimpleObjectProperty<Coach> coach;

    public Team(String teamID, String leagueID, String leagueName, String teamName, String coachID, String coachLastName, String coachFirstName) {
        // TODO
        this.teamID = new SimpleStringProperty(teamID);
        this.teamName = new SimpleStringProperty(teamName);
        this.league = new SimpleObjectProperty<>(new League(leagueID, leagueName));
        this.coach = new SimpleObjectProperty<>(new Coach(coachID, coachLastName, coachFirstName));
    }

    public Team(String leagueID, String teamName, String coachID) {
        this("-1", leagueID, "", teamName, coachID, "", "");
        // TODO
    }

    public String getTeamID() {
        return teamID.get();
    }

    public void setTeamID(String teamID) {
        this.teamID.set(teamID);
    }

    public League getLeague() {
        return league.get();
    }

    public void setLeague(League league) {
        this.league.set(league);
    }


    public String getTeamName() {
        return teamName.get();
    }

    public void setTeamName(String teamName) {
        this.teamName.set(teamName);
    }

    public Coach getCoach() {
        return coach.get();
    }

    public void setCoach(Coach coach) {
        this.coach.set(coach);
    }

    public boolean getCanDelete() {
        return canDelete.get();
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete.set(canDelete);
    }

    public void update() {
        DB.updateTeam(this);
    }

    public void delete() {
        DB.deleteTeam(this.getTeamID());
    }    
 }