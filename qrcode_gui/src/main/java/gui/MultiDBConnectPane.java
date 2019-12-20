package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class MultiDBConnectPane extends VBox {
    private GridPane gpChoose = new GridPane();

    private TextField tfIp = new TextField();
    private TextField tfPort = new TextField();
    private TextField tfDatabase = new TextField();
    private TextField tfUsername = new TextField();
    private TextField tfPassword = new TextField();

    private Button btConnect = new Button("确认连接");

    public MultiDBConnectPane() {
        initGpChoose();
        initField();
        getChildren().addAll(gpChoose, btConnect);
        setMargin(gpChoose, new Insets(10, 10, 10, 10));
        setMargin(btConnect, new Insets(10, 10, 10, 10));
    }

    private void initGpChoose() {
        gpChoose.setHgap(10);
        gpChoose.setVgap(10);

        gpChoose.add(new Label("IP: "), 0, 0);
        gpChoose.add(new Label("Port: "), 0, 1);
        gpChoose.add(new Label("Database: "), 0, 2);
        gpChoose.add(new Label("Username: "), 0, 3);
        gpChoose.add(new Label("Password: "), 0, 4);

        gpChoose.add(tfIp, 1, 0);
        gpChoose.add(tfPort, 1, 1);
        gpChoose.add(tfDatabase, 1, 2);
        gpChoose.add(tfUsername, 1, 3);
        gpChoose.add(tfPassword, 1, 4);
    }

    private void initField() {
        tfIp.setPrefWidth(200);
        tfIp.setAlignment(Pos.BOTTOM_LEFT);
        tfIp.setText("localhost");
        tfPort.setPrefWidth(200);
        tfPort.setAlignment(Pos.BOTTOM_LEFT);
        tfPort.setText("3306");
        tfDatabase.setPrefWidth(200);
        tfDatabase.setAlignment(Pos.BOTTOM_LEFT);
        tfUsername.setPrefWidth(200);
        tfUsername.setAlignment(Pos.BOTTOM_LEFT);
        tfPassword.setPrefWidth(200);
        tfPassword.setAlignment(Pos.BOTTOM_LEFT);


        // 设置按钮的长度
        btConnect.setPrefWidth(500);
        btConnect.setStyle("-fx-border-color: gray; -fx-text-fill: #ffff; -fx-background-color: #3399FF;");
    }

    public String getIp() {
        return tfIp.getText();
    }

    public Integer getPort() {
        return Integer.parseInt(tfPort.getText());
    }

    public String getDatabase() {
        return tfDatabase.getText();
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public Button getBtConnect() {
        return btConnect;
    }
}
