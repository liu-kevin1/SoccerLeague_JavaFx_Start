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

}