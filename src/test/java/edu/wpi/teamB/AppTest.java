package edu.wpi.teamB;

import static org.testfx.api.FxAssert.verifyThat;

import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.robot.ScrollRobot;
import org.testfx.robot.impl.BaseRobotImpl;
import org.testfx.robot.impl.MouseRobotImpl;
import org.testfx.robot.impl.ScrollRobotImpl;

/**
 * This is an integration test for the entire application. Rather than running a single scene
 * individually, it will run the entire application as if you were running it.
 */
@ExtendWith(ApplicationExtension.class)
public class AppTest extends FxRobot {

  /** Setup test suite. */
  @BeforeAll
  public static void setup() throws Exception {
    FxToolkit.registerPrimaryStage();
    FxToolkit.setupApplication(App.class);
  }

  @AfterAll
  public static void cleanup() {}

  @Test
  public void testButton() {
//    verifyThat("Service Request Menu", Node::isVisible);
//    clickOn("Blank Form");
//    verifyThat("Back", Node::isVisible);
//    clickOn("Back");
//    verifyThat("Service Request Menu", Node::isVisible);
  }

  @Test
  public void testSecurityRequestForm(){
    verifyThat("Service Request Menu", Node::isVisible);
    clickOn("Security Services");
    verifyThat("Security Services Request Form", Node::isVisible);
    clickOn("Cancel");
    verifyThat("Service Request Menu", Node::isVisible);
  }

}
