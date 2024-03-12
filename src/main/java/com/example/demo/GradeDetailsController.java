package com.example.demo;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GradeDetailsController implements Connectable{
    Login loginInfo; //Database login info
    SchoolController schoolController; //Caller - SchoolController object

    //Student grade data
    ArrayList<String> sectionGradeDataStudentList; // = new ArrayList<String>();
    ArrayList<String> sectionGradeDataStudentIdList;

    @FXML private TableView<Student> gradeTableView = new TableView<>();
    @FXML public ObservableList<ObservableList> gradeBookData = FXCollections.observableArrayList();
    @FXML private Text titleText = new Text();

    //Object observable list setup
    @FXML private ObservableList<Student> studentGradeList = FXCollections.observableArrayList();

    @FXML private TableColumn<Student, String> gradedItemIdColumn = new TableColumn<>("Item Id");
    @FXML private TableColumn<Student, String> gradeSectionIdColumn = new TableColumn<>("Section Id");
    @FXML private TableColumn<Student, String> gradedItemTitleColumn = new TableColumn<>("Item Title");
    @FXML private TableColumn<Student, String> gradedItemDateColumn = new TableColumn<>("Date");
    @FXML private TableColumn<Student, String> studentGradeColumn = new TableColumn<>("Grade");
    @FXML private TableColumn<Student, String> classAverageColumn = new TableColumn<>("Class Average");

    //Choice Box (Section choice box)
    @FXML
    ChoiceBox studentChoiceBox = new ChoiceBox(); //Student selection for grade plot
    int selectedStudentIndex; //Current student selection

    //Enrolment View Buttons
    @FXML private Button sectionEnrolmentViewButton;

    public void updateGradeStudentData(String sectionId, String studentId){
        //Request data from database
        System.out.println("CALL get_student_grades_from_section(" + sectionId + ", " + studentId + ");");
        Database.getDatabaseData("CALL get_student_grades_from_section(" + sectionId + ", " + studentId + ");",
                "sectionStudentGrades", this.loginInfo, this);
        //Database.getDatabaseData("CALL get_student_grades_from_section(" + this.schoolController.sectionSelectedItems.get(0).getSectionId() + ", " + this.schoolController.studentSelectedItems.get(0).getStudentId() + ");",
               // "sectionStudents", this.schoolController.get_login_info(), this);
    }

    @FXML
    public void initialize() {

        this.sectionEnrolmentViewButton = new Button();
    }

    @FXML
    public void onCloseGradeDetailsButton() {
        Stage currentStage = (Stage) this.gradeTableView.getScene().getWindow();
        currentStage.close();
    }

    //Pass SchoolController caller object
    public void initData(ArrayList<String> sectionGradeDataStudentList, ArrayList<String> sectionGradeDataStudentIdList,
                         int selectedStudentIndex, String selectedSectionId, Login loginInfo, SchoolController schoolController){
        this.selectedStudentIndex = selectedStudentIndex; //Receives initial student selection
        this.sectionGradeDataStudentList = sectionGradeDataStudentList;
        this.sectionGradeDataStudentIdList = sectionGradeDataStudentIdList;
        this.loginInfo = loginInfo;
        this.schoolController = schoolController;

        //Initializes the title
        titleText.setText("Grade Details for Section " + selectedSectionId);

        //Initialize the student choiceBox
        for (String studentInfo : this.sectionGradeDataStudentList)
            this.studentChoiceBox.getItems().add(studentInfo);

        //Adding action to the choice box
        this.studentChoiceBox.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
                    //Do this on ChoiceBox selection change
                    int newSelectedStudentIndex = new_val.intValue();

                    //Display grade data for the selected student
                    this.updateGradeStudentData(selectedSectionId, sectionGradeDataStudentIdList.get(newSelectedStudentIndex));
                });

        //Set the student choice box initial selection
        this.studentChoiceBox.getSelectionModel().select(this.selectedStudentIndex);
    }

    @Override
    public void ProcessData(ResultSet rs, String opCode) {
        switch (opCode) {
            case "sectionStudentGrades":
                System.out.println("Processing Enrolment View table");
                GradedItem.processGradeDetailsColumns(rs, gradeTableView, gradeBookData);
                //this.schoolController.updateStatusTextFlow("Finished processing Student Table");
                break;
        }
    }

    @Override
    public SchoolController getSchoolController() {
        return this.schoolController;
    }
}
