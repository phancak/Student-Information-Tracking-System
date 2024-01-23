package com.example.demo;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GradePlotController {
    //Grade plot variables
    @FXML CategoryAxis xAxis = new CategoryAxis();
    @FXML NumberAxis yAxis = new NumberAxis();
    @FXML LineChart<String,Number> gradeLineChart = new LineChart<String,Number>(xAxis,yAxis);
    @FXML XYChart.Series series = new XYChart.Series();

    //Student grade data
    ArrayList<ArrayList<Double>> sectionGradeData; // = new ArrayList<ArrayList<Double>>();
    ArrayList<String> sectionGradeDataStudentList; // = new ArrayList<String>();

    //Object observable list setup
    @FXML private ObservableList<Student> enrolmentViewStudentList = FXCollections.observableArrayList();

    //Choice Box (Section choice box)
    @FXML
    ChoiceBox studentChoiceBox = new ChoiceBox(); //Student selection for grade plot
    int selectedStudentIndex; //Current student selection

    //Enrolment View Buttons
    @FXML private Button sectionEnrolmentViewButton;

    public void OnStudentChoiceBoxSelectionChange(){
        //Plot grades for the selected student


    }

    public void initialize_Student_Enrolment() {

    }

    @FXML
    public void initialize() {
        //initData is called by the calling function
    }

    @FXML
    public void onCloseGradePlotViewButton() {
        Stage currentStage = (Stage) this.studentChoiceBox.getScene().getWindow();
        currentStage.close();
    }

    //Pass SchoolController caller object
    public void initData(ArrayList<ArrayList<Double>> sectionGradeData, ArrayList<String> sectionGradeDataStudentList, int selectedStudentIndex){
        this.selectedStudentIndex = selectedStudentIndex; //Receives initial student selection
        this.sectionGradeData = sectionGradeData;
        this.sectionGradeDataStudentList = sectionGradeDataStudentList;

        //this.gradeLineChart = new LineChart<String,Number>(xAxis,yAxis);
        this.gradeLineChart.getData().add(series);

        //Initialize the student choiceBox
        for (String studentInfo : this.sectionGradeDataStudentList)
            this.studentChoiceBox.getItems().add(studentInfo);

        //Adding action to the choice box
        this.studentChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    //Do this on ChoiceBox selection change
                    int newSelectedStudentIndex = new_val.intValue();

                    //Plot grade data for the selected student
                    this.PlotGrades(newSelectedStudentIndex);
                });

        //Set the student choice box initial selection
        this.studentChoiceBox.getSelectionModel().select(this.selectedStudentIndex);
    }

    private void PlotGrades(int selectedStudentIndex) {
        if(this.gradeLineChart.getData().get(0).getData().size() > 0)
            this.gradeLineChart.getData().get(0).getData().clear();

        //this.series.setName("My portfolio");
        System.out.println(("sectionGradeData.get(selectedStudentIndex).size(): " + sectionGradeData.get(selectedStudentIndex).size()));

        double dataPoint;

        //Tests if any data were in the line chart
        if(this.gradeLineChart.getData().size() > 0) {
            //Data were in the line chart
            if(this.gradeLineChart.getData().get(0).getData().size() < sectionGradeData.get(selectedStudentIndex).size()){
                //Fewer data points are present in the line chart than are to be written in
                int i = 0;
                while (i < sectionGradeData.get(selectedStudentIndex).size()) {
                    if(i < this.gradeLineChart.getData().get(0).getData().size()){
                        dataPoint = sectionGradeData.get(selectedStudentIndex).get(i);
                        if (dataPoint != 999)
                            series.getData().set(i,(new XYChart.Data<>("Item " + i, sectionGradeData.get(selectedStudentIndex).get(i))));
                    } else {
                        dataPoint = sectionGradeData.get(selectedStudentIndex).get(i);
                        if (dataPoint != 999)
                            series.getData().add(new XYChart.Data<>("Item " + i, sectionGradeData.get(selectedStudentIndex).get(i)));
                    }
                    i = i + 1;
                }
            } else if(this.gradeLineChart.getData().get(0).getData().size() > this.sectionGradeData.get(selectedStudentIndex).size()){
                //Fewer data points are present in the line chart than are to be written in
                int i = 0;
                while (i < this.gradeLineChart.getData().get(0).getData().size()) {
                    if(i < this.sectionGradeData.get(selectedStudentIndex).size()){
                        dataPoint = sectionGradeData.get(selectedStudentIndex).get(i);
                        if (dataPoint != 999)
                            series.getData().set(i,(new XYChart.Data<>("Item " + i, sectionGradeData.get(selectedStudentIndex).get(i))));
                    } else {
                        series.getData().remove(i);
                    }
                    i = i + 1;
                }
            } else if(this.gradeLineChart.getData().get(0).getData().size() == this.sectionGradeData.get(selectedStudentIndex).size()){
                //Same number of data points are present in the line chart as are to be written in
                int i = 0;
                while (i < this.gradeLineChart.getData().get(0).getData().size()) {
                    dataPoint = sectionGradeData.get(selectedStudentIndex).get(i);
                    if (dataPoint != 999)
                        series.getData().set(i,(new XYChart.Data<>("Item " + i, sectionGradeData.get(selectedStudentIndex).get(i))));
                    i = i + 1;
                }
            }
        } else {
            //No data were in the line chart
            for (int i = 0; i < sectionGradeData.get(selectedStudentIndex).size(); i++) {
                //populating the series with data
                dataPoint = sectionGradeData.get(selectedStudentIndex).get(i);
                if (dataPoint != 999)
                    series.getData().add(new XYChart.Data<>("Item " + i, sectionGradeData.get(selectedStudentIndex).get(i)));
            }
        }

        System.out.println("this.gradeLineChart.getData().size(): " + this.gradeLineChart.getData().size());
        //System.out.println(this.gradeLineChart.getData().get(0).getData().)

        //this.gradeLineChart.getData().clear();
        //this.gradeLineChart.getData().add(series);

        for (int i = 0; i<this.gradeLineChart.getData().get(0).getData().size();i++){
            System.out.println(this.gradeLineChart.getData().get(0).getData().get(i));
        }
    }
}
