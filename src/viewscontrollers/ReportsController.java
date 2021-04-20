/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewscontrollers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import utils.DBConnection;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import model.User;
import model.Appointment;
import model.Dummy;

/**
 * FXML Controller class
 *
 * @author yamif
 */
public class ReportsController implements Initializable {

    @FXML
    private Tab contactTabController;
    @FXML
    private ComboBox<String> contactBox;
    @FXML
    private TableView<Appointment> contactTable;
    @FXML
    private TableColumn<Appointment, Integer> AppointmentIDColumn;
    @FXML
    private TableColumn<Appointment, String > TitleColumn;
    @FXML
    private TableColumn<Appointment, String> TypeColumn;
    @FXML
    private TableColumn<Appointment, String> DescriptionColumn;
    @FXML
    private TableColumn<Appointment, String> startColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn;
    @FXML
    private TableColumn<Appointment, Integer> customerColumn;
    @FXML
    private Tab ownTab;
    @FXML
    private Tab MonthTab;
    
    private static int contactID;
    
    ObservableList<Appointment> report1OL = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Set up the table for the first report
        PropertyValueFactory<Appointment, Integer> appointmentIDFactory = new PropertyValueFactory<>("appointmentID");
        AppointmentIDColumn.setCellValueFactory(appointmentIDFactory);
        
        PropertyValueFactory<Appointment, String> appointmentTitleFactory = new PropertyValueFactory<>("Title");
        TitleColumn.setCellValueFactory(appointmentTitleFactory);
        
        PropertyValueFactory<Appointment, String> appointmentTypeFactory = new PropertyValueFactory<>("Title");
        TypeColumn.setCellValueFactory(appointmentTypeFactory);
        
        PropertyValueFactory<Appointment, String> appointmentDescriptionFactory = new PropertyValueFactory<>("description");
        DescriptionColumn.setCellValueFactory(appointmentDescriptionFactory);
        
        PropertyValueFactory<Appointment, String> appoinmentStartFactory = new PropertyValueFactory<>("startTime");
        startColumn.setCellValueFactory(appoinmentStartFactory);
        
        PropertyValueFactory<Appointment, String> appointmentEnd = new PropertyValueFactory<>("endTime");
        endColumn.setCellValueFactory(appointmentEnd);
        
        PropertyValueFactory<Appointment, Integer> appointmentCustomerFactory = new PropertyValueFactory<>("CustomerID");
        customerColumn.setCellValueFactory(appointmentCustomerFactory);
                    //////////////////////////////////////
            //////////////////////////////////////////////////
            /////////////////////////////////
            
        try {


            contactBoxFill();
        } catch (Exception ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    
    public void contactBoxFill() throws SQLException, Exception {
        // Fill the contactBox to display only appoinrments for that contact!!!
        PreparedStatement ps;
        ps = DBConnection.startConnection().prepareStatement("SELECT Contact_Name from contacts");
        ResultSet result = ps.executeQuery();
        while (result.next()) {
            contactBox.getItems().add(result.getString("Contact_Name"));
        }
    }
    
    public void getContactID() throws SQLException, Exception {
        String contactName = contactBox.getValue();
        PreparedStatement ps;
        ps = DBConnection.startConnection().prepareStatement("SELECT Contact_ID from contacts WHERE Contact_NAME = ?");
        ps.setString(1, contactName);
        ResultSet result = ps.executeQuery();
        while (result.next()) {
            contactID = result.getInt("Contact_ID");
        }
        updateReportTable(contactID);
    }
    
    public void updateReportTable(int contactID) throws SQLException, Exception {
        System.out.println("Update report table");
        report1OL.clear();
        PreparedStatement ps;
        ps = DBConnection.startConnection().prepareStatement("SELECT Appointment_ID, Title, Type, Description, Start, End, Customer_ID "
                + "FROM appointments "
                + "WHERE Contact_ID = ?");
        ps.setInt(1, contactID);
        ResultSet result = ps.executeQuery();
        while (result.next()) {
            Appointment appointment = new Appointment();
            appointment.setAppointmentID(result.getInt("Appointment_ID"));
            appointment.setTitle(result.getString("Title"));
            appointment.setType(result.getString("Type"));
            appointment.setDescription(result.getString("Description"));
            appointment.setStartTime(result.getString("Start"));
            appointment.setEndTime(result.getString("End"));
            appointment.setCustomerID(result.getInt("Customer_ID"));
            report1OL.addAll(appointment);                   
        }
        contactTable.setItems(report1OL);
        System.out.println("Updated table");
    }
    
    @FXML
    private void contactBoxController(ActionEvent event) throws Exception {
        getContactID();
    }
    
}
