package view;

import controller.Controller;
import interfaces.ClientServices;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.pojo.User;

import java.io.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Ahmed Ashraf
 */
public class ChatBoxController implements Initializable {

    Controller controller;
    User friend;

    @FXML
    private Label label;
    @FXML
    private TextField chatTextField;
    @FXML
    private TextArea textArea;

    //file transfere data
    byte[] FileToTransfere;
    int fileSize;
    int portNumber;

    //xml variables
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;
    Element rootElement;

    ClientServices clientServices;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void passUser(User user) {
        this.friend = user;
        label.setText(user.getEmail());
        //    createXMLTree();
    }

    public void passController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    public void handleSendButton() {
        if (!chatTextField.getText().equals("")) {
            controller.sendMsg(chatTextField.getText(), friend.getEmail());
            textArea.appendText("\n"+controller.getEmail() + ": " + chatTextField.getText());
            buildXMLElement(chatTextField.getText(), controller.getEmail());
            chatTextField.setText("");
        }
    }

    public void recieveMsg(String msg, String sender) {
        textArea.appendText(sender + ": " + msg);
        textArea.appendText("\n");
        buildXMLElement(msg, friend.getEmail());
    }

    public void onSendFile() {
        FileChooser fileChooser = new FileChooser();
        File myFile = fileChooser.showOpenDialog(label.getScene().getWindow());
        if (myFile != null) {
            readFile(myFile.getAbsolutePath());
        } else {

            System.out.println("you didnt choose file");
        }
        // readFile("src/view/amr.txt");
        System.out.println("iam in on send file Button");

        System.out.println("File size is : " + fileSize);
        System.out.println(myFile.getName());
        clientServices = controller.sendFileToUser(myFile.getName(), friend.getEmail(), controller.getEmail());
        if (clientServices != null) {
            try {
                clientServices.receiveFileFromUser(friend.getEmail(), controller.getEmail(), FileToTransfere, myFile.getName());
                System.out.println("sending file from sender to receiver peer to peer");
            } catch (RemoteException ex) {
                Logger.getLogger(ChatBoxController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
//            JOptionPane.showConfirmDialog(null,
//               friend.getEmail()+ "refused to download the file !", null, JOptionPane.OK_OPTION);
            System.out.println("the peer to peer client is equal null");
        }
    }

    public void readFile(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);

            fileSize = fis.available();
            FileToTransfere = new byte[fileSize];
            fis.read(FileToTransfere);
            fis.close();

        } catch (IOException e) {
            System.out.println("i cant read File");
            e.printStackTrace();
        }

    }

    /////////////////////////////////////////////////////////////
    public void buildXMLElement(String userMessage, String userName) {
        if (userMessage != null && !"".equals(userMessage)) {
            if (userName == null) {
                //append message
                System.out.println(userMessage);
                Element userMessageElement = document.createElement("Message");
                userMessageElement.setTextContent(userMessage);
                document.getLastChild().getLastChild().appendChild(userMessageElement);
            } else {
                //add message 
                System.out.println(userName + "  " + userMessage);
                if (document == null) {
                    createXMLTree();
                }

                Element userSaysElement = document.createElement("UserSays");

                Element userNameElement = document.createElement("Email");
                userNameElement.setTextContent(userName);
                userSaysElement.appendChild(userNameElement);

                Element userMessageElement = document.createElement("Message");
                userMessageElement.setTextContent(userMessage);
                userSaysElement.appendChild(userMessageElement);

                rootElement.appendChild(userSaysElement);
            }
        }
    }

    public void createXMLTree() {

        String user1 = controller.getEmail();

        String user2 = friend.getEmail();
        try {
            factory = DocumentBuilderFactory.newInstance();
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("src/view/MessageSchema.xsd"));

            factory.setSchema(schema);
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            rootElement = document.createElement("MessageArea");
            Attr attrSender = document.createAttribute("sender");
            attrSender.setTextContent(user1);
            rootElement.setAttributeNode(attrSender);
            Attr attrReciever = document.createAttribute("reciever");
            attrReciever.setTextContent(user2);
            rootElement.setAttributeNode(attrReciever);

            document.appendChild(rootElement);
        } catch (ParserConfigurationException | SAXException ex) {
            ex.printStackTrace();
        }
    }

    public NodeList getXMLTree(String path, Transformer transform) {
        try {
            DocumentBuilder builderAppend = factory.newDocumentBuilder();
            File file = new File(path);
            Document documentAppend = builder.parse(file);
            NodeList messages = documentAppend.getElementsByTagName("Message");
            return messages;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            return null;
        }
    }

    public void createXMLDocument(String fileName) {
        // fileName="test";
        TransformerFactory transformFactory = TransformerFactory.newInstance();
        try {

            FileChooser fileChooser = new FileChooser();
            File myFile = fileChooser.showSaveDialog(label.getScene().getWindow());
            if (myFile != null) {
                String path = myFile.getAbsolutePath()+ ".html";
                Source sourceXSLT = new StreamSource(new File("src/view/MessageXSLT.xsl"));
                Transformer transform = transformFactory.newTransformer(sourceXSLT);
                transform.setOutputProperty(OutputKeys.INDENT, "yes");
                File file = new File(path);
                file.createNewFile();
                //if(file.createNewFile()){
                //new file is created
                StreamResult result = new StreamResult(file);
                if (document != null) {
                    DOMSource source = new DOMSource(document.getDocumentElement());
                    transform.transform(source, result);
                } else {
                    System.out.println(document.getDocumentElement().getTagName());
                }
            } else {
                System.out.println("you didnt choose file");
            }
        } catch (TransformerConfigurationException ex) {
            System.out.println("transformer");
        } catch (TransformerException | IOException ex) {

            ex.printStackTrace();

        }
    }

    @FXML
    public void onSaveButton() {

        createXMLDocument(friend.getEmail());

    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

}
