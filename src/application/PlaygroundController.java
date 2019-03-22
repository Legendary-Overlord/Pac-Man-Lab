package application;

import java.io.File;
import java.util.Optional;

import Threads.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import model.Level;

public class PlaygroundController {
	Level level;
	FileChooser route;
	Timeline timelineAnimation;
	boolean gameDone;
	

	@FXML
    private Label labelRebounds;

    @FXML
    private Pane playArea;
    
    public void initialize() {
		level=new Level();
		route= new FileChooser();
		route.setInitialDirectory(new File("../Pac-Man"));
		gameDone=false;
		timelineAnimation = new Timeline(
		         new KeyFrame(Duration.millis(10), 
		            new EventHandler<ActionEvent>() {
		               // move the circle by the dx and dy amounts
		               @Override
		               public void handle(final ActionEvent e) {
		            	   Bounds bounds = playArea.getBoundsInLocal();
		            	   playArea.getChildren().clear();
		            	   int countDead=0;
		            	   for (int i=0;i<level.getPacs().size();i++) {
		               		Arc a = new Arc();
		               		a.setCenterX((double)level.getPacs().get(i).getPosX());
		               		a.setCenterY((double)level.getPacs().get(i).getPosY());
		               		a.setRadiusX((double)level.getPacs().get(i).getRad());
		               		a.setRadiusY(a.getRadiusX());
		               		a.setStartAngle(45.0f);
		               		a.setLength(270);
		               		a.setType(ArcType.ROUND);
		               		a.setFill(Color.YELLOW);
		               		a.setStroke(Color.BLACK);
		               		a.setVisible(!level.getPacs().get(i).isDead());
		               		playArea.getChildren().add(a);
		               		if(level.getPacs().get(i).isDead())
		               			countDead++;
		               	}
		            	   labelRebounds.setText("Rebounds: "+level.getRebounds());
		            	   if(countDead==level.getPacs().size()) {
		            		   timelineAnimation.stop();
		            		   
		            	   }
		            		   
		               }
		            }
		         )
		      );
		timelineAnimation.setCycleCount(Timeline.INDEFINITE);
		timelineAnimation.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				TextInputDialog dialog = new TextInputDialog("");
     		   dialog.setTitle("Score Data");
     		   dialog.setHeaderText("Congrats! Please Register your score!");
     		   dialog.setContentText("Name:");
     		   Optional<String> name = dialog.showAndWait();
     		   if(name.isPresent())
     			   level.submitScore(name.get()); 
			}
			
		});
		
			
	}
	
    public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

    public Pane getPlayArea() {
		return playArea;
	}

	public Label getLabelRebounds() {
		return labelRebounds;
	}

	public void setLabelRebounds(Label labelRebounds) {
		this.labelRebounds = labelRebounds;
	}

	@FXML
    void menuAbout(ActionEvent event) {
		Alert a = new Alert(AlertType.INFORMATION,"Made by Santiago Zuñiga Garcia.");
    	a.showAndWait();
    }

    @FXML
    void menuClose(ActionEvent event) {
    	
    }

    @FXML
    void menuDisplayScores(ActionEvent event) {
    	Alert a = new Alert(AlertType.INFORMATION,level.displayScore());
    	a.showAndWait();
    }

    @FXML
    void menuLoad(ActionEvent event) {
    	File data = route.showOpenDialog(null);
    	level.getPacs().clear();
    	level.load(data);
    	//start rebounds counter
    	ReboundThread reb = new ReboundThread(this);
    	reb.start();
    	//create Pac-Mans
    	for (int i=0;i<level.getPacs().size();i++) {
    		PacsThread th = new PacsThread(this,i);
    		th.start();
    	}
    	timelineAnimation.play();
    }

    @FXML
    void menuSave(ActionEvent event) {
    	//pause game
    	timelineAnimation.pause();
    	route.setInitialFileName("data.txt");
    	File saveData = route.showSaveDialog(null);
    	level.save(saveData);
    	//resume game
    	timelineAnimation.play();
    	
    	
    	
    }

    @FXML
    void paneMouseClick(MouseEvent event) {
    	double playerPosX = event.getX();
    	double playerPosY = event.getY();
    	//check overlap with all Pacs
    	for (int i=0;i<level.getPacs().size();i++) {
    		if(level.killPacMan(i, playerPosX,playerPosY))
    			level.getPacs().get(i).setDead(true);
    	}
    }
    public void close() {
    	try {
    		level.saveScores();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }

}
