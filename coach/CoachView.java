package coach;

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

public class CoachView {
    TableColumnFactory<Coach, String> columnFactory = new TableColumnFactory<>();

    TableView<Coach> table = new TableView<Coach>();
    final ObservableList<Coach> data = FXCollections.observableArrayList();

    final Callback<TableColumn<Coach, String>, TableCell<Coach, String>> cellFactory = new Callback<TableColumn<Coach, String>, TableCell<Coach, String>>() {
        public TableCell<Coach, String> call(TableColumn<Coach, String> p) {
            return new EditingCell<Coach, String>();
        }
    };

    public CoachView() {
    }

    public void reloadCoachs() {
        data.clear();
        data.addAll(DB.loadCoaches());
    }

    public Node getCoachTab() throws SQLException {
        reloadCoachs();

        final HBox hb = new HBox();

        final Label label = new Label("Coachs");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn<Coach, String> coachIDCol = columnFactory.getTableColumn("coachID", "Coach ID", false, 100,
                cellFactory);
        TableColumn<Coach, String> lastNameCol = columnFactory.getTableColumn("coachLastName", "Last Name", true,
                100,
                cellFactory);
        TableColumn<Coach, String> firstNameCol = columnFactory.getTableColumn("coachFirstName", "First Name",
                true, 100,
                cellFactory);
        TableColumn<Coach, String> phoneNbrCol = columnFactory.getTableColumn("coachPhoneNbr", "Phone Number", true,
                100,
                cellFactory);
        TableColumn<Coach, String> emailCol = columnFactory.getTableColumn("coachEmail", "Email", true, 100,
                cellFactory);

        // Insert Button
        TableColumn<Coach, Boolean> col_action = columnFactory.getActionColumn();

        table.getColumns().addAll(coachIDCol, lastNameCol, firstNameCol, phoneNbrCol, emailCol, col_action);
        table.setItems(data);

        final TextField addCoachLastName = new TextField();
        addCoachLastName.setMaxWidth(addCoachLastName.getPrefWidth());
        addCoachLastName.setPromptText("Last Name");

        final TextField addCoachFirstName = new TextField();
        addCoachFirstName.setMaxWidth(addCoachFirstName.getPrefWidth());
        addCoachFirstName.setPromptText("First Name");

        final TextField addCoachPhone = new TextField();
        addCoachPhone.setMaxWidth(addCoachPhone.getPrefWidth());
        addCoachPhone.setPromptText("Phone Number");

        final TextField addCoachEmail = new TextField();
        addCoachEmail.setMaxWidth(addCoachEmail.getPrefWidth());
        addCoachEmail.setPromptText("Email");

        final Button addButton = new Button("Add");
        addButton.setOnAction(e -> {

            DB.insertCoach(new Coach(addCoachLastName.getText(), addCoachFirstName.getText(), addCoachPhone.getText(), addCoachEmail.getText()));
            reloadCoachs();

            addCoachLastName.clear();
            addCoachFirstName.clear();
            addCoachPhone.clear();
            addCoachEmail.clear();
        });

        hb.getChildren().addAll(addCoachLastName, addCoachFirstName, addCoachPhone, addCoachEmail, addButton);
        hb.setSpacing(3);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hb);

        
        return vbox;
    }

}