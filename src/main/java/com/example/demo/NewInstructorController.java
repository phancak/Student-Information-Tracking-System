package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewInstructorController implements Connectable {
    SchoolController schoolController; //Caller - SchoolController object
    private boolean newInstructor; //Is new student being generated or existing student being edited
    Instructor selectedInstructor;
    private ArrayList<String> educationList = new ArrayList<String>(); //Education levels (Bsc, Ms, Phd)

    //Pane title text
    @FXML
    private Text instructorTitle = new Text();

    //Section Information Fields
    @FXML private TextField instructorFirstName = new TextField();
    @FXML private TextField instructorLastName = new TextField();
    @FXML private ChoiceBox educationChoiceBox = new ChoiceBox();
    @FXML private TextField specializationTextField = new TextField();
    @FXML private DatePicker instructorDateOfBirth = new DatePicker();

    //Window buttons
    @FXML private Button addInstructorButton = new Button();

    //Pass SchoolController caller object
    public void initData(SchoolController schoolController, boolean newInstructor){
        this.schoolController = schoolController;
        this.newInstructor = newInstructor; //New Graded Item or Edit Section

        //Must initialize components from initData and makes initial selection
        this.getEducationData(); //Initializes the education level list
        this.populateEducationChoiceBox();

        //Existing graded item is being edited
        if(!newInstructor){
            this.instructorTitle.setText("Edit Instructor");
            this.addInstructorButton.setText("Modify Instructor Entry");

            this.instructorFirstName.setText(this.schoolController.instructorSelectedItems.get(0).getInstructorFirstName());
            this.instructorLastName.setText(this.schoolController.instructorSelectedItems.get(0).getInstructorLastName());
            this.specializationTextField.setText(this.schoolController.instructorSelectedItems.get(0).getInstructorSpecialization());
            this.educationChoiceBox.getSelectionModel().select(this.educationList.indexOf(this.schoolController.instructorSelectedItems.get(0).getInstructorEducation()));

            //Instructor date initialization
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate instructorDateCurrent = LocalDate.parse(schoolController.instructorSelectedItems.get(0).getInstructorDateOfBirth(), formatter);
            this.instructorDateOfBirth.setValue(instructorDateCurrent);
        }
    }

    @FXML
    public void initialize() {
        //Initialize in initData after receiving schoolController
        //Due to database login
    }

    //Process incoming data from the database
    @Override
    public void ProcessData(ResultSet rs, String opCode) {
        //Nothing to process
    }

    private void getEducationData() {
        educationList.add("College");
        educationList.add("BA");
        educationList.add("MS");
        educationList.add("Phd");
    }

    public void populateEducationChoiceBox() {
        for (String educationLevel : educationList) {
            this.educationChoiceBox.getItems().add(educationLevel);
        }
    }

    @FXML
    public void onCloseNewInstructorButton() {
        Stage currentStage = (Stage) this.instructorTitle.getScene().getWindow();
        currentStage.close();
    }

    public void onAddInstructorButton(){
        //Test for empty data fields
        if (this.educationChoiceBox.getSelectionModel().isEmpty() || this.specializationTextField.getText().isEmpty() ||
                this.instructorFirstName.getText().isEmpty() || this.instructorLastName.getText().isEmpty() ||
                this.instructorDateOfBirth.getValue()==null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert");
            alert.setHeaderText(null);
            alert.setContentText("Invalid Instructor Data. Must select valid first name, last name, specialization, education, and date of birth.");
            alert.showAndWait();
        } else {
            if (this.newInstructor) {
                //Format date data
                LocalDate instructorDateOfBValue = this.instructorDateOfBirth.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String instructorDateOfBString = instructorDateOfBValue.format(formatter);

                //Modifies data in database
                Database.getDatabaseData("INSERT INTO Instructors (Instructor_Last_Name, " +
                                "Instructor_First_Name, Instructor_Date_Of_Birth, Instructor_Specialization, Instructor_Education) " +
                                "VALUES ('" + this.instructorFirstName.getText() + "', '" +
                                this.instructorLastName.getText() + "', '" + instructorDateOfBString + "', '" +
                                this.specializationTextField.getText() + "', '" +
                                this.educationChoiceBox.getSelectionModel().getSelectedItem() + "');",
                        "newInstructor", this.schoolController.get_login_info(), this);

                //Close the new student window
                Stage currentStage = (Stage) this.educationChoiceBox.getScene().getWindow();
                currentStage.close();
                this.schoolController.onTabInstructorsSelection(); //Reacquire Instructor list
            } else {
                //Format date data
                LocalDate instructorDateOfBValue = this.instructorDateOfBirth.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String instructorDateOfBString = instructorDateOfBValue.format(formatter);

                //Request data from database
                Database.getDatabaseData("UPDATE Instructors " +
                                "SET Instructor_Last_Name='" + this.instructorLastName.getText() + "', Instructor_Specialization='" + this.specializationTextField.getText() +
                                "', Instructor_First_Name='" + this.instructorFirstName.getText() + "', Instructor_Education='" + this.educationChoiceBox.getSelectionModel().getSelectedItem() +
                                "', Instructor_Date_Of_Birth='" + instructorDateOfBString + "' " +
                                "WHERE Instructor_Id=" + schoolController.instructorSelectedItems.get(0).getInstructorId() + ";",
                        "updateInstructor", this.schoolController.get_login_info(), this);

                //Close the new student window
                Stage currentStage = (Stage) this.educationChoiceBox.getScene().getWindow();
                currentStage.close();
                this.schoolController.onTabInstructorsSelection(); //Reacquire Instructor list
            }
        }
    }

    @Override
    public SchoolController getSchoolController() {
        return this.schoolController;
    }
}
