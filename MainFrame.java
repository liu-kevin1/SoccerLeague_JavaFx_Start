
import java.sql.SQLException;

import coach.CoachView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import league.LeagueView;
// import team.TeamView;
import team.TeamView;

public class MainFrame extends Application {

    public static void main(String[] args) {
        launch(args);
    }



    public void start(Stage primaryStage) throws SQLException {
        LeagueView lv = new LeagueView();
        CoachView cv = new CoachView();
        TeamView tv = new TeamView();
        TabPane tabPane = new TabPane();

        Tab tab1 = new Tab("Leagues", lv.getLeagueTab());
        Tab tab2 = new Tab("Coaches", cv.getCoachTab());
        Tab tab3 = new Tab("Teams" , tv.getTeamTab());

        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);

        tab1.setOnSelectionChanged(ev -> {
            lv.reloadLeagues();
        });

        tab2.setOnSelectionChanged(ev -> {
            cv.reloadCoachs();
        });

        tab3.setOnSelectionChanged(ev -> {
            tv.reloadTeams();
        });

        VBox vBox = new VBox(tabPane);
        Scene scene = new Scene(vBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Recreation Soccer League");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
}