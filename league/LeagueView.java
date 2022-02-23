package league;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import shared.DB;
import shared.TableColumnFactory;
import shared.EditingCell;


public class LeagueView {
    TableColumnFactory<League, String> columnFactory = new TableColumnFactory<>();

    TableView<League> table = new TableView<League>();
    final ObservableList<League> data = FXCollections.observableArrayList();
    final Callback<TableColumn<League, String>, TableCell<League, String>> cellFactory = new Callback<TableColumn<League, String>, TableCell<League, String>>() {
        public TableCell<League, String> call(TableColumn<League, String> p) {
            return new EditingCell<League, String>();
        }
    };

    public LeagueView() {
    }

    public void reloadLeagues() {
        data.clear();
        data.addAll(DB.loadLeagues());
    }

    public Node getLeagueTab() throws SQLException {
        reloadLeagues();

        final HBox hb = new HBox();

        final Label label = new Label("Leagues");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<League, String> leagueIDCol = columnFactory.getTableColumn("leagueID", "League ID", false, 100,
                cellFactory);
        TableColumn<League, String> leagueNameCol = columnFactory.getTableColumn("leagueName", "League Name", true, 100,
                cellFactory);

        // Insert Button
        TableColumn<League, Boolean> col_action = columnFactory.getActionColumn();

        table.getColumns().addAll(leagueIDCol, leagueNameCol, col_action);
        table.setItems(data);

        final TextField addLeagueName = new TextField();
        addLeagueName.setMaxWidth(addLeagueName.getPrefWidth());
        addLeagueName.setPromptText("League Name");

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {

            DB.insertLeague(new League(addLeagueName.getText()));
            reloadLeagues();

            addLeagueName.clear();
        });

        hb.getChildren().addAll(addLeagueName, addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        return vbox;
    }

}