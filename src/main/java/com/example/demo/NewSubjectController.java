package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;

public class NewSubjectController implements Connectable{

    SchoolController schoolController; //Caller - SchoolController object

    private boolean newSubject; //Is new student being generated or existing student being edited

    //Pane title text
    @FXML
    private Text title = new Text();

    //Student Information Fields
    @FXML private TextField subjectName = new TextField();
    @FXML private TextField subjectNumber = new TextField();
    @FXML private TextArea subjectDescription = new TextArea();

    //Window buttons
    @FXML private Button addSubjectButton = new Button();

    //Pass SchoolController caller object
    public void initData(SchoolController schoolController, boolean newSubject){
        this.schoolController = schoolController;

        this.newSubject = newSubject;

        //Existing student is being edited
        if(!newSubject){
            this.title.setText("Edit Subject");
            this.addSubjectButton.setText("Modify Subject Entry");
            this.subjectName.setText(schoolController.subjectsSelectedItems.get(0).getSubjectName());
            this.subjectNumber.setText(schoolController.subjectsSelectedItems.get(0).getSubjectNumber());
            this.subjectDescription.setText(schoolController.subjectsSelectedItems.get(0).getSubjectDescription());
        }

        subjectDescription.setWrapText(true);
    }

    @FXML
    public void initialize() {

        //this.getCountryData();
        //this.populateStudentHomeCountryChoiceBox();
    }

    public void onAddSubjectButton(){
        //Test for empty data fields
        if (subjectName.getText().isEmpty() || subjectNumber.getText().isEmpty() ||
                subjectDescription.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            //alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(false);
            alert.setContentText("Invalid subject data.");

            alert.showAndWait();
        } else {
            if (newSubject) {
                //Request data from database
                Database.getDatabaseData("INSERT INTO Subjects (Subject_Name, Subject_Number, Subject_Description) " +
                                "VALUES ('" + subjectName.getText() + "', '" +
                                subjectNumber.getText() + subjectDescription.getText() + "');",
                        "newSubject", this.schoolController.get_login_info(), this);

                //Close the new student window
                Stage currentStage = (Stage) this.subjectName.getScene().getWindow();
                currentStage.close();
                this.schoolController.onTabStudentSelection(); //Reacquire Student list
            } else {
                //Request data from database
                Database.getDatabaseData("UPDATE Subjects " +
                                "SET Subject_Name='" + subjectName.getText() +
                                "', Subject_Number='" + subjectNumber.getText() +
                                "', Student_Home_Town='" + subjectDescription.getText() + "'" +
                                "WHERE Subject_Id=" + schoolController.subjectsSelectedItems.get(0).getSubjectId() + ";",
                        "update", this.schoolController.get_login_info(), this);

                //Close the new student window
                Stage currentStage = (Stage) this.subjectName.getScene().getWindow();
                currentStage.close();
                this.schoolController.onTabStudentSelection(); //Reacquire Student list
            }
        }
    }

    @FXML
    public void onCloseNewSubjectButton() {
        Stage currentStage = (Stage) this.subjectName.getScene().getWindow();
        currentStage.close();
    }

    @Override
    public void ProcessData(ResultSet rs, String opCode) {
        //No data to process
    }

    @Override
    public SchoolController getSchoolController() {
        return this.schoolController;
    }
}
