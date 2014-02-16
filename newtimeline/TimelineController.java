package newtimeline;
/*
//@author Kurt Andres
//@author Conner Vick
*/

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
          //delete timeline event from treeMap re-render timeline
  void onDeleteEvent(ActionEvent event){
     Iterator iter = timeline.timelineEvents.keySet().iterator();
        String name = deleteEventName.getText();
        while(iter.hasNext()){
            Event next = timeline.timelineEvents.get(iter.next());
            if (next.getName().equals(name)){
                timeline.removeEvent(next);
                deleteEventStatus.setText(name+" deleted");
                //placed check inside update timeline to make sure it's not empty which results in null pointer exception
                updateTimeline();
                
                deleteEventName.setText("Enter Event to Delete");
                return; 
                
            }else{
                updateTimeline();
                deleteEventStatus.setText("Timeline Empty");
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
  /*extensive testing shows this new thread can cause error if
  //"Process manager already initialized: can't fully enable headless mode."
  //on mac OS X
  //not problem on linux
  */
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
        //listener for adding event
        try{
           //trys to add start events if integer input
            //sets end dates to 1 which would get set over if duration event
         Event eve;
         int sy = Integer.parseInt(startYear.getText());
         int ey =1;
         int sm = Integer.parseInt(startMonth.getText());
         int em = 1;
         int sd = Integer.parseInt(startDay.getText());
         int ed = 1;
        
         //if isDuration box is selected we set end date fields
         if(isDuration){
             ey = Integer.parseInt(endYear.getText());
             em = Integer.parseInt(endMonth.getText());
             ed = Integer.parseInt(endDay.getText());
         }
         
         //now we reset all text boxes so they're empty
         endDay.setText("");
         endMonth.setText("");
         endYear.setText("");
         startDay.setText("");
         startMonth.setText("");
         startYear.setText("");
        //slightly unwieldy but working sanity check for valid inputs
         //might have what we called a 'bad smell'
                         
         if(sy>0 && sy<2015 && ey>0 && ey<2015 && sm>0 && sm<13 && em>0 && em<13 && sd>0 && sd<=31 && ed>0 && ed<=31 && (((sm == 9 || sm == 4 || sm == 6 || sm == 11) && sd<=30)||(sm==2 && sd<30)|| ((sm == 1 || sm == 3 || sm == 5 || sm == 7 || sm==8 || sm==10 || sm==12)&&sd<=31 )) && (((em == 9 || em == 4 || em == 6 || em == 11) && ed<=30)||(em==2 && ed<30)|| ((em == 1 || em == 3 || em == 5 || em == 7 || em==8 || em==10 || em==12)&&ed<=31 ))){
             if((ey-sy)<80){
         
            if(isDuration){
                if((ey-sy)>78){
               addEventStatus.setText("Too Long for Memory");
               //this is a simple check to ensure its not greater than 80yrs because this overflows memory for some reason
               return;
                }
                else if(ey>sy|| (ey==sy && em==sm && ed>sy)|| (ey==sy && em>sm)){
                eve = new DurationEvent();
                eve.setEvent(title.getText(), sy, sm, sd, ey, em, ed);
                //update min max for memory checks
                
                addEventStatus.setText(title.getText()+ " added.");
                }else{
                    addEventStatus.setText("not a valid Event");
                    return;
                }
            }else{
                eve = new AtomicEvent();
                eve.setEvent(title.getText(), sy, sm, sd, 0, 0, 0);
                addEventStatus.setText(title.getText()+ " added.");
            }
            timeline.addEvent(eve);
            
            if((timeline.max-timeline.min)<79){
            updateTimeline();
            }else{
                //Timeline is too spread out, larger than 80yrs so memory error
                //need to remove last event 
                timeline.removeEvent(eve);
                timeline.max=timeline.oldmax;
                timeline.min=timeline.oldmin;
                System.out.println("Timeline grew too large for memory, event not added");
                addEventStatus.setText("Too large");
            }
           }
             
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
      //more than likely do nothing here
      //empty constructor
  }
  
  /**
  * Initializes the controller class. 
  */
  //call to upate timeline
  public void updateTimeline(){
      //make sure before we update timeline isn't empty causing null pointer
         if(timeline.timelineNotEmpty()){
             
         timelineRender tlr = new timelineRender();
         Canvas can = tlr.getTimelineRender(timeline);
         scrollPane.setContent(can);
             }
        
            else{
             //if here, timeline empty
            Canvas canvas = new Canvas(100+(50)*100, 1000);
            //clear canvas and set to blank canvas
            scrollPane.setContent(canvas);
         }
   }
  //new timeline thread
    class newThread extends Thread{
      public void run(){
            String name;
            name = JOptionPane.showInputDialog("Enter Timeline Name:");
            timeline = new Timeline(name);
            JOptionPane.showMessageDialog(null,"timeline started, add new event to render");
      }
  }
    //dialogue to load timeline
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
    
    //opens dialogue to savetimeline form menu
 class savesThread extends Thread{
     
        public void run(){
            String name;
           name = JOptionPane.showInputDialog("Enter Save Name:");
           IO.saveFile(timeline, name);
           JOptionPane.showMessageDialog(null,"Timeline: "+name+".tl saved.");
            
      }
  }
 
 /*extensive testing shows this new thread can cause error if
  //"Process manager already initialized: can't fully enable headless mode."
  //on mac OS X
  //not problem on linux
  */
 
 //simple class to display credits when selected from menu
  class creditsThread extends Thread{
     
        public void run(){
           
           JOptionPane.showMessageDialog(null,"Created by Conner Vick and Kurt Andres");
            
      }
  }
    
  @Override
  public void initialize(URL url, ResourceBundle rb) {
       
  }

  
 
  
    
}