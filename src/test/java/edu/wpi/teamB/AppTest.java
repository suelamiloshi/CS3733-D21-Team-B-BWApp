package edu.wpi.teamB;

import static org.testfx.api.FxAssert.verifyThat;

import javafx.geometry.VerticalDirection;
import javafx.scene.Node;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.stream.Stream;

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


  @ParameterizedTest
  @MethodSource("textProvider")
  void recognizeSingleToken(String button, String title)
  {
    verifyThat("Service Request Directory", Node::isVisible);
    clickOn(button);
    verifyThat(title, Node::isVisible);
    clickOn("Cancel");
    verifyThat("Service Request Directory", Node::isVisible);
  }

  private static Stream<Arguments> textProvider()
  {
    return Stream.of(
            Arguments.of("Security Services", "Security Services Request Form"),
            Arguments.of("Sanitation Services", "Sanitation Services Request Form"),
            Arguments.of("Floral Delivery", "Floral Delivery Request Form"),
            Arguments.of("Medicine Delivery", "Medicine Delivery Request Form"),
            Arguments.of("Internal Transportation", "Internal Transportation Request Form"),
            Arguments.of("External Patient Transport", "External Transportation Request Form"),
            Arguments.of("Religious Service", "Religious Request Form")
    );
  }
}
