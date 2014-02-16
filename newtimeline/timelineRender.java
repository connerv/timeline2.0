package newtimeline;

import java.util.*;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
//import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

/**
 * @author Kurt Andres
 * @author Conner Vick
 */
public class timelineRender {
    static Timeline tl;
    //static ArrayList<clickSpace> csArray = new ArrayList<clickSpace>();
    static clickSpace cs;
    static HashMap<Integer,Integer> yearMap;
    static HashMap<Integer,Integer> yearMap2;
    int spacer = 0;
       
    public Canvas getTimelineRender(Timeline timeline){
        //Canvas canvas = new Canvas(100+(5)*100, 1000);
        tl = timeline;
        //if(tl.timelineEvents.size()<2) return canva;
        yearMap = new HashMap<Integer,Integer>();
        yearMap2 = new HashMap<Integer,Integer>();
        /*
        Event ev;
        
        tl = new drawingtest.Timeline("Timeline Name");
        for(int i = 0; i<25; i++){
            ev = new drawingtest.AtomicEvent();
            ev.setEvent("190"+i+" info","190"+i,""+i,""+i);
            tl.addEvent(ev);
        }
        */
     
      
        
        int begin = tl.timelineEvents.firstEntry().getValue().getStartYear();
        int end = tl.timelineEvents.lastEntry().getValue().getStartYear();
        Iterator it = tl.timelineEvents.keySet().iterator();
        
        while(it.hasNext()){
            Event next = tl.timelineEvents.get(it.next());
            if (next.getDuration()){
                if(next.getEndYear() > end) end = next.getEndYear();
            }
        }
        int length = end - begin;
        
        Canvas canvas = new Canvas(100+(length+2)*100, 1500);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawShapes(gc);
        
        /*
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){
 
            @Override
            public void handle(MouseEvent event) {
               int x = (int)event.getX();
               int y = (int)event.getY();
               for(int i =0; i<csArray.size(); i++){
                   cs = csArray.get(i);
                   if(x > cs.x && x < cs.x+15 && y > cs.y && y < cs.y+15){
                       
                       (new Thread(new PopUp())).start();
                        

                   }
               }
                
            }
        });
        */
        return canvas;
       
    }
    private void drawShapes(GraphicsContext gc) {
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        gc.fillText(tl.name, 40, 20);
        int begin = tl.timelineEvents.firstEntry().getValue().getStartYear();
        int end = tl.timelineEvents.lastEntry().getValue().getStartYear();
        Iterator iter = tl.timelineEvents.keySet().iterator();
        
        while(iter.hasNext()){
            Event next = tl.timelineEvents.get(iter.next());
            if (next.getDuration()){
                if(next.getEndYear() > end) end = next.getEndYear();
            }
        }
        int length = end - begin;
        
         System.out.println("begin "+begin+" end "+end+" length "+length);
         
        gc.strokeLine(40, 100, (length+2)*100, 100);
        for(int i = 0; i < length+2; i++){
            gc.strokeLine(40+i*100, 80, 40+i*100, 120);
            gc.fillText(""+(begin+i), 40+i*100, 60);
            
        }
        int vOffSet = 0;
        int vOffSet2 = 0;
        Iterator it = tl.timelineEvents.keySet().iterator();
        for(int i = 0; i < tl.timelineEvents.size(); i++ ){
            
            
            
            Event next = tl.timelineEvents.get(it.next());
            if(!yearMap.containsKey(next.getStartYear())) yearMap.put(next.getStartYear(), 0);
            vOffSet+=40*yearMap.get(next.getStartYear());
            
            //not duration event
            if(next.getDuration() == false){
                gc.setStroke(Color.RED);
                gc.setFill(Color.RED);
                int offset = (next.getStartDay()+(next.getStartMonth()*30))/4;
                
                gc.strokeLine(40+(next.getStartYear()-begin)*100+offset, 90, 40+(next.getStartYear()-begin)*100+offset, 110);
                //clickSpace cls = new clickSpace(35+i*100, 25, next);
                //csArray.add(cls);
                gc.fillText(""+next.getStartYear()+"/"+next.getStartMonth()+"/"+next.getStartDay(), 50+(next.getStartYear()-begin)*100, 135+vOffSet);
                gc.fillText(next.getName(), 50+(next.getStartYear()-begin)*100, 150+vOffSet);
          //make purple duration event
            }else{
                if(!yearMap.containsKey(next.getEndYear())) yearMap.put(next.getEndYear(), 0);
                vOffSet2+=40*yearMap.get(next.getEndYear());
                gc.setStroke(Color.PURPLE);
                gc.setFill(Color.PURPLE);
                int boffset = (next.getStartDay()+(next.getStartMonth()*30))/4;
                int eoffset = (next.getEndDay()+(next.getEndMonth()*30))/4;
                
                 //spaces duration events so they don't pile on top eachother
                spacer +=20;
                
                //draws the duration event timeline
                gc.strokeLine(40+(next.getStartYear()-begin)*100+boffset, 90+vOffSet+spacer, 40+(next.getStartYear()-begin)*100+boffset, 110+vOffSet+spacer);
                gc.strokeLine(40+(next.getEndYear()-begin)*100+eoffset, 90+vOffSet+spacer, 40+(next.getEndYear()-begin)*100+eoffset, 110+vOffSet+spacer);
                gc.strokeLine(40+(next.getStartYear()-begin)*100+boffset, 99+vOffSet+spacer, 40+(next.getEndYear()-begin)*100+eoffset, 99+vOffSet+spacer);
                
                System.out.println(""+next.getEndYear());
                gc.fillText(""+next.getStartYear()+"/"+next.getStartMonth()+"/"+next.getStartDay(), 50+(next.getStartYear()-begin)*100, 135+vOffSet);
                gc.fillText(next.getName(), 50+(next.getStartYear()-begin)*100, 150+vOffSet);
                gc.fillText(""+next.getEndYear()+"/"+next.getEndMonth()+"/"+next.getEndDay(), 50+(next.getEndYear()-begin)*100, 135+vOffSet);
                gc.fillText(next.getName(), 50+(next.getEndYear()-begin)*100, 150+vOffSet);
                     
                
                int k = yearMap.get(next.getEndYear());
                k = k+1;
                yearMap.put(next.getEndYear(), k);
            }
            int k = yearMap.get(next.getStartYear());
            k = k+1;
            yearMap.put(next.getStartYear(), k);
            
        }   
        
    }
    /*
     public class PopUp implements Runnable {
         
     public void run() {
        //showStage();
        JOptionPane.showMessageDialog(null, cs.ev.getDescription());
     }

     }
     public static void showStage(){
        Stage newStage = new Stage();
        VBox comp = new VBox(8);
        //TextField month = new TextField("Month");
        //TextField month = new TextField("Month");
        TextField sYear = new TextField("Start Year");
        TextField eYear = new TextField("End Year");
        TextField month = new TextField("Month");
        //TextField month = new TextField("Month");
        //TextField month = new TextField("Month");
        //TextField month = new TextField("Month");
        comp.getChildren().add(sYear);
        comp.getChildren().add(month);

        Scene stageScene = new Scene(comp, 300, 300);
        newStage.setScene(stageScene);
        newStage.show();
    
}
    */
}
