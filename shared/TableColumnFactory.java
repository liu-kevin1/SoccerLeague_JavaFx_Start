package shared;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javafx.beans.value.WritableObjectValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TableColumnFactory<S, T> {

    
    public TableColumn<S, T> getTableColumn(String propName, String label, boolean editable, int width,
            Callback<TableColumn<S, T>, TableCell<S, T>> cellFactory) {
        TableColumn<S, T> col = new TableColumn<>(label);
        col.setEditable(editable);
        col.setMinWidth(width);
        col.setCellValueFactory(new PropertyValueFactory<S, T>(propName));
        col.setCellFactory(cellFactory);

        col.setOnEditCommit(
                t -> {
                    try {
                        Object obj = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());

                        // updated the database
                        Field f = obj.getClass().getDeclaredField(propName);
                        f.setAccessible(true);
                        ((WritableObjectValue) f.get(obj)).set(t.getNewValue());
                        Method m = obj.getClass().getDeclaredMethod("update");
                        m.invoke(obj);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    // reloadLeagues();
                });

        return col;
    }

    public TableColumn<S, Boolean> getActionColumn() {
        TableColumn<S, Boolean> col_action = new TableColumn<>("Action");
        col_action.setSortable(false);
        col_action.setCellValueFactory(new PropertyValueFactory<S, Boolean>("canDelete"));

        col_action.setCellFactory(
                p -> new ButtonCell(p.getTableView()));

        return col_action;
    }

    // Define the button cell
    class ButtonCell extends TableCell<S, Boolean> {
        final Button cellButton = new Button("Delete");

        ButtonCell(final TableView<S> tblView) {

            cellButton.setOnAction(t -> {

                try {
                    int selectdIndex = getTableRow().getIndex();
                    S obj = (S) tblView.getItems().get(selectdIndex);

                    // remove from database
                    Method m = obj.getClass().getDeclaredMethod("delete");
                    m.invoke(obj);

                    // remove from the tableview
                    tblView.getItems().remove(obj);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        // Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);

            if (!empty && t) {
                setGraphic(cellButton);
            }
            else {
                setGraphic(null);
            }
        }
    }
}
