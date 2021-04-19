package edu.wpi.teamB.views.covidSurvey;

// Java program to create a popup and add it to the stage
import com.jfoenix.controls.JFXButton;
import edu.wpi.teamB.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class CovidSubmittedNoSympController {

    @FXML
    private JFXButton btnDirections;

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton btnEmergency;

    @FXML
    void handleButtonAction(ActionEvent e) {
        JFXButton btn = (JFXButton) e.getSource();

        switch (btn.getId()) {
            case "btnBack":
                SceneSwitcher.goBack(getClass(), 1);
                break;
            case "btnDirections":
                SceneSwitcher.switchScene(getClass(), "/edu/wpi/teamB/views/covidSurvey/covidFormSubmittedNoSymp.fxml", "/edu/wpi/teamB/views/mapEditor/pathfindingMenu.fxml");
                break;
            case "btnEmergency":
                SceneSwitcher.switchToTemp(getClass(), "/edu/wpi/teamB/views/requestForms/emergencyForm.fxml");
                break;
        }
    }
}