package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.HashSet;
import java.util.Set;

public class Main extends Application {

    private void setupEventHandlers(Pane root, Set<Integer> usedFields, boolean[] drawCircle) {

        for (Node node : root.getChildren()) {
            if (node instanceof Rectangle) {
                Rectangle r = (Rectangle) node;
                final int finalI = (int) r.getX();
                final int finalJ = (int) r.getY();

                r.setOnMouseClicked(mouseEvent -> {
                    int fieldIndex = getFieldIndex(finalI, finalJ);

                    if (!usedFields.contains(fieldIndex)) {
                        usedFields.add(fieldIndex);

                        if (drawCircle[0]) {
                            Circle circle = new Circle(finalI + 30, finalJ + 30, 20);
                            circle.setFill(Color.RED);
                            Circle circle1 = new Circle(finalI + 30, finalJ + 30, 15);
                            circle1.setFill(Color.WHITE);
                            root.getChildren().add(circle);
                            root.getChildren().add(circle1);

                        } else {
                            Line line1 = new Line(finalI + 10, finalJ + 10, finalI + 50, finalJ + 50);
                            Line line2 = new Line(finalI + 10, finalJ + 50, finalI + 50, finalJ + 10);
                            line1.setStroke(Color.RED);
                            line2.setStroke(Color.RED);
                            root.getChildren().addAll(line1, line2);
                        }
                        drawCircle[0] = !drawCircle[0];
                    }
                });
            }
        }
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        Pane root = new Pane();
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 400, 400));

        MenuBar menuBar = new MenuBar();
        menuBar.setLayoutX(0);
        menuBar.setLayoutY(0);
        Menu buttonMenu = new Menu("settings");
        MenuItem clear_Item = new MenuItem("clear");
        MenuItem rules_Item = new MenuItem("rules");
        MenuItem exit_Item = new MenuItem("exit");
        buttonMenu.getItems().addAll(clear_Item, rules_Item, exit_Item);
        menuBar.getMenus().addAll(buttonMenu);
        root.getChildren().add(menuBar);

        final Set<Integer> usedFields = new HashSet<>();
        final boolean[] drawCircle = {true};

        for (int i = 110; i < 290; i += 60) {
            for (int j = 110; j < 290; j += 60) {
                final int centerX = i + 30;
                final int centerY = j + 30;

                Rectangle r = new Rectangle(i, j, 60, 60);
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);
                root.getChildren().add(r);
                final int finalI = i;
                final int finalJ = j;

                r.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        int fieldIndex = getFieldIndex(finalI, finalJ);

                        if (!usedFields.contains(fieldIndex)) {
                            usedFields.add(fieldIndex);

                            if (drawCircle[0]) {
                                Circle circle = new Circle(finalI + 30, finalJ + 30, 20);
                                circle.setFill(Color.RED);
                                Circle circle1 = new Circle(finalI + 30, finalJ + 30, 15);
                                circle1.setFill(Color.WHITE);
                                root.getChildren().add(circle);
                                root.getChildren().add(circle1);
                            } else {
                                Line line1 = new Line(finalI + 10, finalJ + 10, finalI + 50, finalJ + 50);
                                Line line2 = new Line(finalI + 10, finalJ + 50, finalI + 50, finalJ + 10);
                                line1.setStroke(Color.RED);
                                line2.setStroke(Color.RED);
                                root.getChildren().addAll(line1, line2);
                            }
                            drawCircle[0] = !drawCircle[0];
                        }
                    }
                });
            }
        }

        clear_Item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                root.getChildren().clear();

                for (int i = 110; i < 290; i += 60) {
                    for (int j = 110; j < 290; j += 60) {
                        final int centerX = i + 30;
                        final int centerY = j + 30;

                        Rectangle r = new Rectangle(i, j, 60, 60);
                        r.setFill(Color.WHITE);
                        r.setStroke(Color.BLACK);
                        root.getChildren().add(r);
                        setupEventHandlers(root, usedFields, drawCircle);
                    }
                }

                usedFields.clear();
                root.getChildren().add(menuBar);
            }
        });

        rules_Item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage rulesStage = new Stage();
                rulesStage.setTitle("Rules");
                VBox dialogLayout = new VBox();
                Label rulesLabel = new Label("Here are the rules of the game:");
                Text rulesText = new Text("1. Participants take turns placing signs on the \nfree cells of the field.\n2.One plays with crosses, the other with zeros.\n4.The winner is the one who is the first to line up 3\n of his figures vertically, horizontally or diagonally.\nGood luck!");
                dialogLayout.getChildren().addAll(rulesLabel, rulesText);
                Scene rulesScene = new Scene(dialogLayout, 300, 200);
                rulesStage.setScene(rulesScene);
                rulesStage.show();
            }
        });

        exit_Item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private int getFieldIndex(int x, int y) {
        return x + y * (400 / 60);
    }
}

