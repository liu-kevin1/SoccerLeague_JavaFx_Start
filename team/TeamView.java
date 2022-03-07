package team;

import java.sql.SQLException;

import coach.Coach;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import league.League;
import shared.DB;
import shared.EditingCell;
import shared.TableColumnFactory;

public class TeamView {

    final ObservableList<League> leagueList = FXCollections.observableArrayList(DB.loadLeagues());
    final ObservableList<Coach> coachList = FXCollections.observableArrayList(DB.loadCoaches());

    TableView<Team> table = new TableView<Team>();
    final ObservableList<Team> data = FXCollections.observableArrayList();

    final Callback<TableColumn<Team, String>, TableCell<Team, String>> cellFactory = new Callback<TableColumn<Team, String>, TableCell<Team, String>>() {
        public TableCell<Team, String> call(TableColumn<Team, String> p) {
            return new EditingCell<Team, String>();
        }
    };

    
    public TeamView() {
    }

    public void reloadTeams() {
        // reload the dropdowns in the add section
        leagueList.clear();
        leagueList.addAll(DB.loadLeagues());
        coachList.clear();
        coachList.addAll(DB.loadCoaches());

        // reload the table
        data.clear();
        data.addAll(DB.loadTeams());
    }

    public void loadTeamPageData() {
        reloadTeams();
        table.getColumns().clear();


        TableColumnFactory<Team, String> columnFactory = new TableColumnFactory<>();
        TableColumnFactory<Team, League> leagueColumnFactory = new TableColumnFactory<>();
        TableColumnFactory<Team, Coach> coachColumnFactory = new TableColumnFactory<>();
    
        TableColumn<Team, String> teamIDCol = columnFactory.getTableColumn("teamID", "Team ID", 
                false, 100,
                cellFactory);
        TableColumn<Team, String> teamNameCol = columnFactory.getTableColumn("teamName", "Name", 
                true, 100,
                cellFactory);
        TableColumn<Team, League> leagueCol = leagueColumnFactory.getTableColumn("league", "League",
                true, 100,
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(DB.loadLeagues())));
        TableColumn<Team, Coach> coachCol = coachColumnFactory.getTableColumn("coach", "Coach",
                true, 100,
                ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(DB.loadCoaches())));

        // Insert Button
        TableColumn<Team, Boolean> col_action = columnFactory.getActionColumn();

        table.getColumns().addAll(teamIDCol, teamNameCol, leagueCol, coachCol, col_action);
    }

    public Node getTeamTab() throws SQLException {
        final HBox hb = new HBox();

        final Label label = new Label("Teams");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);
        loadTeamPageData();

        table.setItems(data);

        //
        // Add / Modify Section
        //


        final TextField addTeamName = new TextField();
        addTeamName.setMaxWidth(addTeamName.getPrefWidth());
        addTeamName.setPromptText("Team Name");

        final ComboBox<League> selectLeague = new ComboBox<>(leagueList);
        selectLeague.setPromptText("League");
        selectLeague.getSelectionModel().selectFirst();

        final ComboBox<Coach> selectCoach = new ComboBox<>(coachList);
        selectCoach.setPromptText("Coach");

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {
			String leagueID = selectLeague.getValue() == null ? null : selectLeague.getValue().getLeagueID();
			String coachID = selectCoach.getValue() == null ? null : selectCoach.getValue().getCoachID();

            DB.insertTeam(leagueID, addTeamName.getText(), coachID);
            loadTeamPageData();

            // Clear the input fields
            addTeamName.clear();
            selectLeague.getSelectionModel().selectFirst();
            selectCoach.getSelectionModel().clearSelection();
            selectCoach.setValue(null);            
        });

        hb.getChildren().addAll(addTeamName,selectLeague, selectCoach, addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        return vbox;
    }

}