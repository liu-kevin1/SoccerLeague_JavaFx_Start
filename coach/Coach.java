package coach;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import shared.DB;

public class Coach {
 
    private final SimpleStringProperty coachID;
    private final SimpleStringProperty coachLastName;
    private final SimpleStringProperty coachFirstName;
    private final SimpleStringProperty coachPhoneNbr;
    private final SimpleStringProperty coachEmail;
    private final SimpleBooleanProperty canDelete = new SimpleBooleanProperty(false);;

    public Coach(String coachID, String coachLastName, String coachFirstName, String coachPhoneNbr, String coachEmail) {
        this.coachID = new SimpleStringProperty(coachID);
        this.coachLastName = new SimpleStringProperty(coachLastName);
        this.coachFirstName = new SimpleStringProperty(coachFirstName);
        this.coachPhoneNbr = new SimpleStringProperty(coachPhoneNbr);
        this.coachEmail = new SimpleStringProperty(coachEmail);
    }

    public Coach(String coachID, String coachLastName, String coachFirstName) {
        this (coachID, coachLastName, coachFirstName, "","");
    }

    public Coach(String coachLastName, String coachFirstName, String coachPhoneNbr, String coachEmail) {
        this ("-1", coachLastName, coachFirstName, coachPhoneNbr, coachEmail);
    }

    public String getCoachID() {
        return coachID.get();
    }

    public void setCoachID(String coachID) {
        this.coachID.set(coachID);
    }

    public String getCoachLastName() {
        return coachLastName.get();
    }

    public void setCoachLastName(String coachLastName) {
        this.coachLastName.set(coachLastName);
    }

    public String getCoachFirstName() {
        return coachFirstName.get();
    }

    public void setCoachFirstName(String coachFirstName) {
        this.coachFirstName.set(coachFirstName);
    }

    public String getCoachPhoneNbr() {
        return coachPhoneNbr.get();
    }

    public void setCoachPhoneNbr(String coachPhoneNbr) {
        this.coachPhoneNbr.set(coachPhoneNbr);
    }

    public String getCoachEmail() {
        return coachEmail.get();
    }

    public void setCoachEmail(String coachEmail) {
        this.coachEmail.set(coachEmail);
    }

    public boolean getCanDelete() {
        return canDelete.get();
    }

    public void setCanEdit(boolean canDelete) {
        this.canDelete.set(canDelete);
    }


    public void update() {
        System.out.println("Updating coach");
        DB.updateCoach(this);
    }

    public void delete() {
        System.out.println("Deleting coach");
        DB.deleteCoach(this.getCoachID());
    }    

    public String toString() {
        if (coachID.get() == null) { 
            return "";
        }
        else {
            return coachLastName.get() + ", " + coachFirstName.get();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coach other = (Coach) obj;
        if (coachID == null) {
            if (other.coachID != null)
                return false;
        } else if (!coachID.equals(other.coachID))
            return false;
        return true;
    }

    
 }