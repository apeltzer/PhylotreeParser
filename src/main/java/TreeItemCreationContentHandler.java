import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

public class TreeItemCreationContentHandler extends DefaultHandler {
    private  static TreeItem<String> finalTree = null;

    private TreeItem<String> item = new TreeItem<>();


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // finish this node by going back to the parent
        if(qName.equals("haplogroup")){
            this.item = this.item.getParent();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // start a new node and use it as the current item
        if(qName.equals("haplogroup")){
            TreeItem<String> item = new TreeItem<>(attributes.getValue("name"));
            this.item.getChildren().add(item);
            this.item = item;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //Probably not required, we dont want the polymorphisms in our TreeItem<String>
       // String s = String.valueOf(ch, start, length).trim();
       // if (!s.isEmpty()) {
       //     // add text content as new child
       //     this.item.getChildren().add(new TreeItem<>(s));
       // }
    }


    private static TreeItem<String> readData() throws SAXException, ParserConfigurationException, IOException {
        File file = new File("/Users/peltzer/Desktop/phylotree17.xml");
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        XMLReader reader = parser.getXMLReader();
        TreeItemCreationContentHandler contentHandler = new TreeItemCreationContentHandler();

        // parse file using the content handler to create a TreeItem representation
        reader.setContentHandler(contentHandler);
        reader.parse(file.toURI().toString());

        // use first child as root (the TreeItem initially created does not contain data from the file)
        TreeItem<String> item = contentHandler.item.getChildren().get(0);
        contentHandler.item.getChildren().clear();
        finalTree = item;
        return item;
    }

    public static TreeItem<String> getFinalTree() throws IOException, SAXException, ParserConfigurationException {
        readData();
        return finalTree;
    }
}