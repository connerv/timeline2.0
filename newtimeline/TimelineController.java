package newtimeline;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javax.swing.*;


public class TimelineController implements Initializable {
   private Timeline timeline = new Timeline("newTimeline"); 
   private boolean isDuration = false;
   
   
  @FXML
  private ScrollPane scrollPane;
  @FXML
  private TextField startYear;
  @FXML
  private TextField startMonth;
  @FXML
  private TextField startDay;
  @FXML
  private TextField endYear;
  @FXML
  private TextField endMonth;
  @FXML
  private TextField endDay;
  @FXML
  private TextField title;
  @FXML
  private TextField deleteEventName;
  @FXML
  private Label addEventStatus;
  @FXML
  private Label deleteEventStatus;
  @FXML
  private Button addEvent;
   @FXML
  private Button deleteEvent;
  @FXML
  private CheckBox durationEvent;
  @FXML
  private MenuItem openMenu;
  @FXML
  private MenuItem newMenu;
  @FXML
  private MenuItem saveMenu;
    @FXML
  private MenuItem credits;
  @FXML
  void onCredits(ActionEvent event){
     creditsThread ot = new creditsThread();
            ot.start();
  }
  @FXML
  void onDeleteEvent(ActionEvent event){
     Iterator iter = timeline.timelineEvents.keySet().iterator();
        String name = deleteEventName.getText();
        while(iter.hasNext()){
            Event next = timeline.timelineEvents.get(iter.next());
            if (next.getName().equals(name)){
                timeline.removeEvent(next);
                deleteEventStatus.setText(name+" deleted");
                updateTimeline();
                return;
            }
        }
        deleteEventStatus.setText("Unable to find "+name);
  }
  @FXML
  void onOpenMenu(ActionEvent event){
            openThread ot = new openThread();
            ot.start();
            
            
  }
  @FXML
  void onNewMenu(ActionEvent event){
      newThread nt = new newThread();
      nt.start();

  }
  @FXML
  void onSaveMenu(ActionEvent event){
      savesThread st = new savesThread();
      st.start();
  }
  @FXML
  void onDurationEvent(ActionEvent event){
      isDuration = !isDuration;
  }
  @FXML
    void onAddEvent(ActionEvent event) {
        
        try{
         Event eve;
         int sy = Integer.parseInt(startYear.getText());
         int ey =1;
         int sm = Integer.parseInt(startMonth.getText());
         int em = 1;
         int sd = Integer.parseInt(startDay.getText());
         int ed = 1;
         if(isDuration){
             ey = Integer.parseInt(endYear.getText());
             em = Integer.parseInt(endMonth.getText());
             ed = Integer.parseInt(endDay.getText());
         }
        
         if(sy>0 && sy<2015 && ey>0 && ey<2015 && sm>0 && sm<13 && em>0 && em<13 && sd>0 && sd<31 && ed>0 && ed<31){
             
         
            if(isDuration){
                eve = new DurationEvent();
                eve.setEvent(title.getText(), sy, sm, sd, ey, em, ed, "");
            }else{
                eve = new AtomicEvent();
                eve.setEvent(title.getText(), sy, sm, sd, 0, 0, 0, "");
            }
            timeline.addEvent(eve);
            updateTimeline();
        }else{
          addEventStatus.setText("not a valid Event");
        }
        }catch(Exception e){
            addEventStatus.setText("not a valid Event. Must be a number");
        }
    }
  /**
  * The constructor.
  * The constructor is called before the initialize() method.
  */
  public TimelineController() {
      
  }
  
  /**
  * Initializes the controller class. 
  */
  public void updateTimeline(){
         timelineRender tlr = new timelineRender();
         Canvas can = tlr.getTimelineRender(timeline);
         scrollPane.setContent(can);
   }
    class newThread extends Thread{
      public void run(){
            String name;
            name = JOptionPane.showInputDialog("Enter Timeline Name:");
            timeline = new Timeline(name);
            JOptionPane.showMessageDialog(null,"timeline started, add new event to render");
      }
  }
    class openThread extends Thread{
      public void run(){
            Timeline tl = IO.getFile();
            if(tl != null){
                timeline = tl;
                JOptionPane.showMessageDialog(null,"timeline loaded, add new event to render");
            }else{
                JOptionPane.showMessageDialog(null,"Load Failed");
            }
      }
    }   
 class savesThread extends Thread{
     
        public void run(){
            String name;
           name = JOptionPane.showInputDialog("Enter Save Name:");
           IO.saveFile(timeline, name);
           JOptionPane.showMessageDialog(null,"Timeline: "+name+".tl saved.");
            
      }
  }
  class creditsThread extends Thread{
     
        public void run(){
           
           JOptionPane.showMessageDialog(null,"Created by Conner Vick and Kurt Andres");
            
      }
  }
    
  @Override
  public void initialize(URL url, ResourceBundle rb) {
       
  }

  
 
  
    
}