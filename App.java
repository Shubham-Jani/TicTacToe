package com.tictactoe;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class App extends Application {

    public void setBox(GridPane pane,Button[][] nodes){
        for(int i =0;i<3;i++){
            for(int j =0;j<3;j++){
                nodes[i][j] = new Button("");
                nodes[i][j].setMinSize(100,100);
                GridPane.setConstraints(nodes[i][j],i,j);
                pane.getChildren().add(nodes[i][j]);
            }
        }

    }


    @Override
    public void start(Stage stage) throws IOException {
        // Stage settings
        stage.setHeight(600);
        stage.setWidth(600);
        stage.setTitle("TicTacToe");


        // Main node
        VBox root = new VBox();
        root.setPadding(new Insets(15,15,15,15));


        //Grid node(( boxes )
        Button[][] nodes = new Button[3][3];
        GridPane mainGrid = new GridPane();
        mainGrid.setAlignment(Pos.valueOf("CENTER"));
        mainGrid.setHgap(10);
        mainGrid.setVgap(10);
        setBox(mainGrid,nodes);
        root.getChildren().add(mainGrid);

        //text to show turn and winner
        Text turnText = new Text();
        turnText.setText("Turn:Player X");
        turnText.setFont(Font.font("Times New Roman",FontWeight.BLACK,FontPosture.REGULAR,30));
        root.getChildren().add(turnText);
        Text winnerText = new Text();
        winnerText.setFont(Font.font("Times New Roman",FontWeight.NORMAL,FontPosture.REGULAR,69));
        winnerText.setFill(Color.INDIANRED);
        root.getChildren().add(winnerText);

        //control stuff
        for(int i=0;i<3;i++) {
            for (int j = 0; j < 3; j++) {
                ArrayList<Integer> id = new ArrayList<>();
                id.add(i);
                id.add(j);
                final int fi = i;
                final int fj = j;
                nodes[i][j].setOnAction(actionEvent -> {
                playGame(id,nodes[fi][fj],turnText,winnerText);
                });
            }
        }
        //reset button
        Button reset = new Button("RESET(use only before finishing a match)");
        root.getChildren().add(reset);

        reset.setOnAction(actionEvent -> {
           resetStuff(nodes,player1,player2,winnerText);
        });

        // Setting up the scene
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    int activePlayer = 1;
    HashSet<List<Integer>> player1 = new HashSet<>();
    HashSet<List<Integer>> player2 = new HashSet<>();
    void playGame(List<Integer> cellID, Button selectedButton,Text turnText,Text winnerText){
        System.out.println(cellID);
        if(activePlayer == 1){
            turnText.setText("Turn:Player O");
            selectedButton.setText("X");
            selectedButton.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.REGULAR,25));
            player1.add(cellID);
            if(setWinner(player1)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Playing Information");
                alert.setContentText("X won the match");
                alert.setHeaderText("Winner declared");
                alert.show();
                winnerText.setText("X won the match");
                return;
            }
            activePlayer = 2;
        }else  if(activePlayer == 2){
            turnText.setText("Turn:Player X");
            selectedButton.setText("O");
            selectedButton.setFont(Font.font("Times New Roman",FontWeight.BOLD,FontPosture.REGULAR,25));
            player2.add(cellID);
            if(setWinner(player2)){
                winnerText.setText("O won the match");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Playing Information Info");
                alert.setContentText("O won the match");
                alert.setHeaderText("Winner declared");
                alert.show();
                return;
            }
            activePlayer = 1;
        }
        selectedButton.setDisable(true);
    }

    void resetStuff(Button[][] nodes,HashSet<List<Integer>> player1, HashSet<List<Integer>> player2,Text winnerText){
        for(int i=0; i<3; i++){
            for(int j=0;j<3;j++){
                nodes[i][j].setText("");
                nodes[i][j].setDisable(false);
            }
        }
        winnerText.setText("");
        player1 = new HashSet<>();
        player2 = new HashSet<>();
    }

    public boolean setWinner(HashSet<List<Integer>> player){
        HashSet<List<Integer>> set1 = new HashSet<>();
        HashSet<List<Integer>> set2 = new HashSet<>();
        HashSet<List<Integer>> set3 = new HashSet<>();
        HashSet<List<Integer>> set4 = new HashSet<>();
        HashSet<List<Integer>> set5 = new HashSet<>();
        HashSet<List<Integer>> set6 = new HashSet<>();
        HashSet<List<Integer>> set7 = new HashSet<>();
        HashSet<List<Integer>> set8 = new HashSet<>();

        //diagonal
        set1.add(List.of(0,0));
        set1.add(List.of(1,1));
        set1.add(List.of(2,2));

        set2.add(List.of(0,2));
        set2.add(List.of(1,1));
        set2.add(List.of(2,0));

        //horizontal

        set3.add(List.of(0,0));
        set3.add(List.of(0,1));
        set3.add(List.of(0,2));

        set4.add(List.of(1,0));
        set4.add(List.of(1,1));
        set4.add(List.of(1,2));

        set5.add(List.of(2,0));
        set5.add(List.of(2,1));
        set5.add(List.of(2,2));

        //vertical

        set6.add(List.of(0,0));
        set6.add(List.of(1,0));
        set6.add(List.of(2,0));

        set7.add(List.of(0,1));
        set7.add(List.of(1,1));
        set7.add(List.of(2,1));

        set8.add(List.of(0,2));
        set8.add(List.of(1,2));
        set8.add(List.of(2,2));

        if(player.size() >=3){
            return  player.containsAll(set1) || player.containsAll(set2) || player.containsAll(set3) ||
                    player.containsAll(set4) || player.containsAll(set5) || player.containsAll(set6) ||
                    player.containsAll(set7) || player.containsAll(set8);
        }
        return false;
    }


    public static void main(String[] args) {
        launch();
    }
}
