package landlord;

import javafx.application.Application;
import javafx.stage.Stage;
import landlordApp.Landlord;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Nico
 * @version 22.1.2020
 * Sähköpostit:
 * niveseka: niveseka@student.jyu.fi
 * eetakoiv: eetakoiv@student.jyu.fi
 *
 */
public class LandlordMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("LandlordGUIView.fxml"));
            final Pane root = ldr.load();
            final LandlordGUIController landlordCtrl = (LandlordGUIController) ldr.getController();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("landlord.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Landlord");
            primaryStage.show();
            
            Landlord landlord = new Landlord();
            landlordCtrl.setLandlord(landlord);
            landlordCtrl.lueTiedostosta();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
        
    }
}