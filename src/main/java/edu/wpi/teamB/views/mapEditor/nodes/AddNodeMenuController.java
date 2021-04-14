package edu.wpi.teamB.views.mapEditor.nodes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import edu.wpi.teamB.database.DatabaseHandler;
import edu.wpi.teamB.entities.Node;
import edu.wpi.teamB.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddNodeMenuController implements Initializable {

    @FXML
    public JFXButton btnEmergency;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXRadioButton notRestricted;

    @FXML
    private JFXRadioButton restricted;

    @FXML
    private ToggleGroup areaGroup;

    @FXML
    private JFXTextField nodeID;

    @FXML
    private JFXTextField xCoord;

    @FXML
    private JFXTextField yCoord;

    @FXML
    private JFXTextField floor;

    @FXML
    private JFXTextField building;

    @FXML
    private JFXTextField nodeType;

    @FXML
    private JFXTextField longName;

    @FXML
    private JFXTextField shortName;

    @FXML
    private JFXButton btnAddNode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        notRestricted.setToggleGroup(areaGroup);
        restricted.setToggleGroup(areaGroup);
    }
    @FXML
    private void validateButton() throws NumberFormatException {
        if (!nodeID.getText().isEmpty() && !building.getText().isEmpty() && !nodeType.getText().isEmpty()
            && !longName.getText().isEmpty() && !shortName.getText().isEmpty() && !floor.getText().isEmpty()
            && !xCoord.getText().isEmpty() && !yCoord.getText().isEmpty()) {
            btnAddNode.setDisable(false);
        }
        else {
            btnAddNode.setDisable(true);
        }
        try {
            Integer.parseInt(floor.getText());
            Integer.parseInt(xCoord.getText());
            Integer.parseInt(yCoord.getText());
        } catch (NumberFormatException notInt) {
            btnAddNode.setDisable(true);
        }


    }
    @FXML
    private void handleButtonAction(ActionEvent e) throws IOException {
        JFXButton btn = (JFXButton) e.getSource();

        switch (btn.getId()) {
            case "btnCancel":
                SceneSwitcher.switchScene(getClass(), "/edu/wpi/teamB/views/mapeditor/nodes/editNodesListMenu.fxml");
                break;
            case "btnAddNode":
                String aNodeId = nodeID.getText();
                String aFloor = floor.getText();
                String aBuilding = building.getText();
                String aNodeType = nodeType.getText();
                String aLongName = longName.getText();
                String aShortName = shortName.getText();
                int aXCoord = Integer.parseInt(xCoord.getText());
                int aYCoord = Integer.parseInt(yCoord.getText());
                Node aNode = new Node(aNodeId, aXCoord, aYCoord, aFloor, aBuilding, aNodeType, aLongName, aShortName);
                DatabaseHandler.getDatabaseHandler("main.db").addNode(aNode);
                SceneSwitcher.switchScene(getClass(), "/edu/wpi/teamB/views/mapeditor/nodes/editNodesListMenu.fxml");
                break;
        }
    }
}
