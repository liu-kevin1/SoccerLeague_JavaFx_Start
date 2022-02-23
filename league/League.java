package league;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import shared.DB;

public class League {
 
    private final SimpleStringProperty leagueID;
    private final SimpleStringProperty leagueName;
    private final SimpleBooleanProperty canDelete = new SimpleBooleanProperty(true);

    public League(String leagueID, String leagueName) {
        this.leagueID = new SimpleStringProperty(leagueID);
        this.leagueName = new SimpleStringProperty(leagueName);
    }

    public League(String leagueName) {
        this.leagueID = new SimpleStringProperty("-1");
        this.leagueName = new SimpleStringProperty(leagueName);
    }

    public String getLeagueID() {
        return leagueID.get();
    }

    public void setLeagueID(String leagueID) {
        this.leagueID.set(leagueID);
    }

    public String getLeagueName() {
        return leagueName.get();
    }

    public void setLeagueName(String leagueName) {
        this.leagueName.set(leagueName);
    }

    public boolean getCanDelete() {
        return canDelete.get();
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete.set(canDelete);
    }

    public void update() {
        System.out.println("Updating league");
        DB.updateLeague(this);
    }

    public void delete() {
        System.out.println("Deleting league");
        DB.deleteLeague(this.getLeagueID());
    }    

    public String toString() {
        return getLeagueName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        League other = (League) obj;
        if (leagueID == null) {
            if (other.leagueID != null)
                return false;
        } else if (!leagueID.equals(other.leagueID))
            return false;
        return true;
    }
 }