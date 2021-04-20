package edu.wpi.teamB.views.requestForms;

import com.jfoenix.controls.*;
import edu.wpi.teamB.App;
import edu.wpi.teamB.database.DatabaseHandler;
import edu.wpi.teamB.entities.requests.CaseManagerRequest;
import edu.wpi.teamB.entities.requests.ExternalTransportRequest;
import edu.wpi.teamB.entities.requests.Request;
import edu.wpi.teamB.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.UUID;

public class ExternalTransportationRequestFormController extends DefaultServiceRequestFormController implements Initializable {

    @FXML
    private JFXTextField name;

    @FXML
    private JFXComboBox<Label> comboTranspType;

    @FXML
    private JFXTextField destination;

    @FXML
    private JFXTextArea description;

    @FXML
    private JFXTextArea allergies;

    @FXML
    private JFXCheckBox unconscious;

    @FXML
    private JFXCheckBox infectious;

    @FXML
    private JFXCheckBox outNetwork;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        comboTranspType.getItems().add(new Label("Bus"));
        comboTranspType.getItems().add(new Label("Ambulance"));
        comboTranspType.getItems().add(new Label("Helicopter"));

        if (SceneSwitcher.peekLastScene().equals("/edu/wpi/teamB/views/menus/serviceRequestDatabase.fxml")) {
            String id = (String) App.getPrimaryStage().getUserData();
            ExternalTransportRequest externalTransportRequest = (ExternalTransportRequest) DatabaseHandler.getDatabaseHandler("main.db").getSpecificRequestById(id, Request.RequestType.EXTERNAL_TRANSPORT);
            name.setText(externalTransportRequest.getPatientName());
            getLocationIndex(externalTransportRequest.getLocation());
            int index = -1;
            if (externalTransportRequest.getTransportType().equals("Bus")) {
                index = 0;
            } else if (externalTransportRequest.getTransportType().equals("Ambulance")) {
                index = 1;
            } else if (externalTransportRequest.getTransportType().equals("Helicopter")) {
                index = 2;
            }
            comboTranspType.getSelectionModel().select(index);
            destination.setText(externalTransportRequest.getDestination());
            description.setText(externalTransportRequest.getDescription());
            allergies.setText(externalTransportRequest.getPatientAllergies());
            unconscious.setSelected(externalTransportRequest.getUnconscious().equals("T"));
            infectious.setSelected(externalTransportRequest.getInfectious().equals("T"));
            outNetwork.setSelected(externalTransportRequest.getOutNetwork().equals("T"));
        }
    }

    public void handleButtonAction(ActionEvent actionEvent) {
        super.handleButtonAction(actionEvent);

        JFXButton btn = (JFXButton) actionEvent.getSource();
        if (btn.getId().equals("btnSubmit")) {
            String givenPatientName = name.getText();
            String givenTransportType = comboTranspType.getValue().getText();
            String givenDestination = destination.getText();
            String givenPatientAllergies = allergies.getText();
            String givenOutNetwork = outNetwork.isSelected() ? "T" : "F";
            String givenInfectious = infectious.isSelected() ? "T" : "F";
            String givenUnconscious = unconscious.isSelected() ? "T" : "F";

            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateInfo = new Date();

            String requestID = UUID.randomUUID().toString();
            String time = timeFormat.format(dateInfo); // Stored as HH:MM (24 hour time)
            String date = dateFormat.format(dateInfo); // Stored as YYYY-MM-DD
            String complete = "F";
            String employeeName = null; // fix
            String givenDescription = description.getText();

            ExternalTransportRequest request = new ExternalTransportRequest(givenPatientName, givenTransportType, givenDestination, givenPatientAllergies, givenOutNetwork, givenInfectious, givenUnconscious,
                    requestID, time, date, complete, employeeName, getLocation(), givenDescription);

            DatabaseHandler.getDatabaseHandler("main.db").addRequest(request);
        }
    }

    @FXML
    private void validateButton() {
        btnSubmit.setDisable(
                name.getText().isEmpty() || loc.getValue() == null || comboTranspType.getValue() == null ||
                        description.getText().isEmpty() || allergies.getText().isEmpty() || destination.getText().isEmpty()
        );
    }
}
