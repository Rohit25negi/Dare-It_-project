import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Champ on 2/29/2016.
 */
public class OnYo extends Application {

    public void start(Stage primaryStage)
    {

    }

static Stage sttt;
    static String name;
    public void oncreate(String name)throws Exception
    {this.name=name;

        Pane p= FXMLLoader.load(getClass().getResource("sexo.fxml"));
        sttt=new Stage();
        sttt.setTitle("show me you boobies");
        sttt.setScene(new Scene(p, 200, 100));
        sttt.show();


    }
    public void yes()
    {  new Main2().yes(name);
        sttt.close();
    }
    public void no()
    {   while(!new File(name).delete());
        sttt.close();
    }

}
