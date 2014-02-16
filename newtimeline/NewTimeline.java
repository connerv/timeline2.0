/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *  @author Conner Vick
 */

package newtimeline;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class NewTimeline extends Application {
  
  private Stage primaryStage;
  private BorderPane rootLayout;
  
  @Override
  public void start(Stage primaryStage) throws Exception {
      this.primaryStage = primaryStage;
      this.primaryStage.setTitle("Timeline");
      
      
          // Load the root layout from the fxml file
         Parent root = FXMLLoader.load(getClass().getResource("newTimeline.fxml"));
          //rootLayout = (BorderPane) loader.load();
          Scene scene = new Scene(root,640,400);
          primaryStage.setScene(scene);
          primaryStage.show();  
  }
  
  /**
  * Returns the main stage.
  * @return
  */
  public Stage getPrimaryStage() {
      return primaryStage;
  }
  
  public static void main(String[] args) {
      launch(args);
  }
}