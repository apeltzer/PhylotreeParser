import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by peltzer on 16/11/2016.
 */
public class ViewTree extends Application{

    public static void main(String[] args) throws SAXException, ParserConfigurationException, IOException{
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws IOException, SAXException, ParserConfigurationException {
        primaryStage.setTitle("Tree View Sample");


        TreeItem<String> finalTree = TreeItemCreationContentHandler.getFinalTree();

        finalTree.setExpanded(true);

        TreeView<String> tree = new TreeView<String> (finalTree);
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
