import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class Main extends Application{

    private final ObservableList<Data> data =
            FXCollections.observableArrayList(
                    new Data("Basic Empty Weight", "", "", ""),
                    new Data("Pilot and Passenger", "", "", ""),
                    new Data("Rear Passenger", "", "", ""),
                    new Data("Baggage", "", "", ""),
                    new Data("ZERO FUEL WEIGHT", "", "", ""),
                    new Data("Fuel", "", "", ""),
                    new Data("Start/Taxi/Run-Up", "", "", ""),
                    new Data("TAKEOFF WEIGHT", "", "", ""),
                    new Data("Fuel Burn", "", "", ""),
                    new Data("LANDING WEIGHT", "", "", ""));

    DecimalFormat df = new DecimalFormat("#.0");

    public Data updateWeight(Data row){
        if (row.getWeight().equals("")){
            try{
                row.setWeight(df.format(Double.parseDouble(row.getMoment())/Double.parseDouble(row.getArm())));
            } catch (NumberFormatException e) {}
        }
        return row;
    }

    public Data updateArm(Data row){
        if (row.getArm().equals("")){
            try{
                row.setSubTitle(row.getSubTitle());
                row.setArm(df.format(Double.parseDouble(row.getMoment())/Double.parseDouble(row.getWeight())));
            } catch (NumberFormatException e) {}
        }
        return row;
    }

    public Data updateMoment(Data row){
        if (row.getMoment().equals("")){
            try{
                row.setMoment(df.format(Double.parseDouble(row.getWeight())*Double.parseDouble(row.getArm())));
            } catch (NumberFormatException e) {}
        }
        return row;
    }

    public Data changeWeight (Data row, String newVal){
        row.setWeight(newVal);
        return row;
    }

    public Data changeMoment (Data row, String newVal){
        row.setMoment (newVal);
        return row;
    }

    public void setMenu (Stage primaryStage){
        BorderPane main =  new BorderPane();
        Label title = new Label ("Weight and Balance Calculator");
        BorderPane border = new BorderPane();

        Button wbchart =  new Button ("Weight and Balance Chart");
        wbchart.setPadding(new Insets(15, 20, 15, 20));
        wbchart.setFont(new Font("Rockwell", 24));
        wbchart.setPrefSize(375, 75);
        wbchart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setCalc(primaryStage);
            }
        });

        Button ibutton = new Button ("Instructions");
        ibutton.setPadding(new Insets(15, 20, 15, 20));
        ibutton.setFont(new Font("Rockwell", 24));
        ibutton.setPrefSize(375, 75);
        ibutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setInstructions(primaryStage);
            }
        });

        Button exit = new Button ("Exit");
        exit.setPadding(new Insets(15, 20, 15, 20));
        exit.setFont(new Font("Rockwell", 24));
        exit.setPrefSize(375, 75);
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                primaryStage.close();
            }
        });

        VBox menu = new VBox();

        menu.setSpacing(50);
        menu.getChildren().addAll(wbchart, ibutton, exit);
        menu.setAlignment(Pos.TOP_CENTER);
        menu.setPadding(new Insets(20, 0, 20, 0));

        title.setFont(new Font("Cooper Black", 28));
        title.setTextFill(Color.web("#F5F5F5"));

        border.setCenter(title);
        border.setPadding (new Insets(10,0,10,0));

        main.setTop(border);
        main.setCenter(menu);
        main.setStyle("-fx-background-color: #303030");

        primaryStage.setTitle("Weight and Balance Calculator");
        primaryStage.setScene(new Scene(main,1000,550));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setCalc (Stage primaryStage){
        primaryStage.setTitle("Weight and Balance Calculator");

        // Creating the table
        TableView<Data> table = new TableView <Data> ();

        table.setEditable(true);

        // Columns
        TableColumn title = new TableColumn ("");
        title.setMinWidth(150);
        title.setStyle("-fx-alignment: CENTER; -fx-font-weight: bold;");
        title.setCellValueFactory(
                new PropertyValueFactory<Data, String>("subTitle"));

        TableColumn weightCol = new TableColumn("Weight");
        weightCol.setStyle("-fx-alignment: CENTER;");
        weightCol.setCellValueFactory(
                new PropertyValueFactory <Data, String> ("weight"));
        weightCol.setCellFactory(TextFieldTableCell.forTableColumn());
        weightCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Data, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Data, String> t) {
                        Data temp = ((Data) t.getTableView().getItems().get(
                                t.getTablePosition().getRow()));
                        if (!(temp.getSubTitle().equals("ZERO FUEL WEIGHT") || temp.getSubTitle().equals("TAKEOFF WEIGHT") || temp.getSubTitle().equals("LANDING WEIGHT"))) {
                            temp.setWeight(t.getNewValue());
                        }
                        table.refresh();
                    }
                });

        TableColumn armCol = new TableColumn("Arm");
        armCol.setCellValueFactory(
                new PropertyValueFactory <Data, String> ("arm"));
        armCol.setStyle("-fx-alignment: CENTER;");
        armCol.setCellFactory(TextFieldTableCell.forTableColumn());
        armCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Data, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Data, String> t) {
                        Data temp = ((Data) t.getTableView().getItems().get(
                                t.getTablePosition().getRow()));
                        if (!(temp.getSubTitle().equals("ZERO FUEL WEIGHT") || temp.getSubTitle().equals("TAKEOFF WEIGHT") || temp.getSubTitle().equals("LANDING WEIGHT"))) {
                            temp.setArm(t.getNewValue());
                        }
                        table.refresh();
                    }
                });

        TableColumn momentCol = new TableColumn("Moment");
        momentCol.setCellValueFactory(
                new PropertyValueFactory <Data, String> ("moment"));
        momentCol.setStyle("-fx-alignment: CENTER;");
        momentCol.setCellFactory(TextFieldTableCell.forTableColumn());
        momentCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Data, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Data, String> t) {
                        Data temp = ((Data) t.getTableView().getItems().get(
                                t.getTablePosition().getRow()));
                        if (!(temp.getSubTitle().equals("ZERO FUEL WEIGHT") || temp.getSubTitle().equals("TAKEOFF WEIGHT") || temp.getSubTitle().equals("LANDING WEIGHT"))) {
                            temp.setMoment(t.getNewValue());
                        }
                        table.refresh();
                    }
                });

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(title, weightCol, armCol, momentCol);
        table.setFixedCellSize(37.25);
        table.setItems(data);

        Button calcButton = new Button("Calculate");
        calcButton.setFont(new Font("Rockwell", 14));
        calcButton.setPrefSize(150, 30);
        calcButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                double tweight = 0, tmoment = 0;
                for (int x = 0; x < data.size();x++){
                    data.set(x, updateWeight (data.get(x)));
                    data.set(x, updateArm(data.get(x)));
                    data.set(x, updateMoment (data.get(x)));
                }

                for (int x = 0; x < data.size(); x++){
                    if (data.get(x).getSubTitle().equals("ZERO FUEL WEIGHT") || data.get(x).getSubTitle().equals("TAKEOFF WEIGHT") || data.get(x).getSubTitle().equals("LANDING WEIGHT")){
                        changeWeight(data.get(x), Double.toString (tweight));
                        changeMoment(data.get(x), Double.toString (tmoment));
                        data.set(x, updateArm(data.get(x)));
                    }
                    else {
                        if (data.get(x).getSubTitle().equals("Start/Taxi/Run-Up") || data.get(x).getSubTitle().equals("Fuel Burn")) {
                            tweight = tweight - Double.parseDouble(data.get(x).getWeight().equals("") ? "0" : data.get(x).getWeight());
                            tmoment = tmoment - Double.parseDouble(data.get(x).getMoment().equals("") ? "0" : data.get(x).getMoment());
                        } else {
                            tweight = tweight + Double.parseDouble(data.get(x).getWeight().equals("") ? "0" : data.get(x).getWeight());
                            tmoment = tmoment + Double.parseDouble(data.get(x).getMoment().equals("") ? "0" : data.get(x).getMoment());
                        }
                    }
                }

            }
        });

        Button goBack = new Button("Main Menu");
        goBack.setFont(new Font("Rockwell", 14));
        goBack.setPrefSize(150, 30);
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setMenu(primaryStage);
            }
        });

        Label name = new Label("Weight and Balance Calculator");
        name.setFont(new Font("Cooper Black", 28));

        StackPane stack = new StackPane();
        BorderPane s2 = new BorderPane();
        BorderPane border = new BorderPane();
        VBox menu = new VBox();

        s2.setCenter(name);
        s2.setPadding(new Insets(0,0,10,0));

        stack.getChildren().addAll(table);

        menu.getChildren().addAll(calcButton,goBack);
        menu.setAlignment(Pos.TOP_CENTER);
        menu.setSpacing(10);
        menu.setPadding(new Insets(15, 0, 0, 0));

        border.setPadding(new Insets(10, 250, 10, 250));
        border.setBottom(menu);
        border.setTop(s2);
        border.setCenter(stack);

        Scene calc = new Scene (border, 1000, 550);

        primaryStage.setScene(calc);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void setInstructions(Stage primaryStage){
        Label title = new Label ("Instructions");
        title.setFont(new Font("Cooper Black", 28));

        VBox top = new VBox();
        top.getChildren().addAll(title);
        top.setAlignment(Pos.TOP_CENTER);
        top.setPadding(new Insets(10,0,10,0));

        Label para1 = new Label ("1. To get to the W & B table, go back to the main menu and click the Weight and Balance button.");
        para1.setFont(new Font("Constantia", 20));
        Label para2 = new Label ("2. Once the table shows up, to enter values in the cells of the table, double-click the intended cell and submit\n    the numeric value.");
        para2.setFont(new Font("Constantia",20));
        Label para3 = new Label("3. Fill in all the known numeric values in the table, and once completed, click the 'Calculate' button");
        para3.setFont(new Font ("Constantia",20));
        Label para4 = new Label("4. The unknown values that are possible to compute will be filled in by the computer. Keep in mind that an\n    empty cell shows that there is insufficient information.");
        para4.setFont(new Font("Constantia",20));
        Label para5 = new Label("NOTE: If any of the cells are empty after the CALCULATE button has been pressed, the results of the Weight\n and Balance are not complete and therefore should not be used to fly.");
        para5.setFont(new Font("Constantia",20));

        Button goBack = new Button("Main Menu");
        goBack.setFont(new Font("Rockwell", 14));
        goBack.setPrefSize(150, 30);
        goBack.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                setMenu(primaryStage);
            }
        });

        StackPane s = new StackPane();
        s.getChildren().addAll(goBack);
        s.setAlignment(Pos.TOP_CENTER);
        s.setPadding(new Insets(10,0,100,0));

        VBox paragraph = new VBox();
        paragraph.setSpacing(20);
        paragraph.getChildren().addAll(para1, para2, para3, para4, para5);
        paragraph.setPadding(new Insets(0,10,0,30));

        BorderPane border = new BorderPane();
        border.setTop(top);
        border.setCenter(paragraph);
        border.setBottom(s);

        primaryStage.setScene(new Scene (border, 1000, 550));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void start (Stage primaryStage){
        setMenu(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
