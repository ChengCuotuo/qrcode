package gui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import qrcode.CreateQRCode;

import java.io.File;

public class SingleExportPane extends VBox {
    private GridPane gpChoose = new GridPane();

    private TextField tfFileName = new TextField();
    private TextField tfFileFormat = new TextField();
    private TextField tfWidth = new TextField();
    private TextField tfHeight = new TextField();
    private TextField tfStorePath = new TextField();
    private Button btConfirm = new Button("确认导出");

    public SingleExportPane() {
        InitGpChoose();
        initField();
        getChildren().addAll(gpChoose, btConfirm);
        setMargin(gpChoose, new Insets(10, 10, 10, 10));
        setMargin(btConfirm, new Insets(10, 10, 10, 10));
    }

    private void InitGpChoose() {
        gpChoose.setHgap(10);
        gpChoose.setVgap(10);
        gpChoose.add(new Label("文件名："), 0, 0);
        gpChoose.add(new Label("文件格式："),0, 1);
        gpChoose.add(new Label("宽："), 0, 2);
        gpChoose.add(new Label("高："), 0, 3);
        gpChoose.add(new Label("路径："), 0, 4);

        gpChoose.add(tfFileName, 1, 0);
        gpChoose.add(tfFileFormat, 1, 1);
        gpChoose.add(tfWidth, 1, 2);
        gpChoose.add(tfHeight, 1, 3);
        gpChoose.add(tfStorePath, 1, 4);
    }

    // 设置个输入框的默认值
    private void initField() {
        tfFileName.setText("qrcode");
        tfFileName.setPrefWidth(200);
        tfFileName.setAlignment(Pos.BOTTOM_LEFT);
        tfFileFormat.setText("jpg");
        tfFileFormat.setPrefWidth(200);
        tfFileFormat.setAlignment(Pos.BOTTOM_LEFT);
        tfFileFormat.setDisable(true);
        tfWidth.setText("300");
        tfWidth.setPrefWidth(200);
        tfWidth.setAlignment(Pos.BOTTOM_LEFT);
        tfHeight.setText("300");
        tfHeight.setPrefWidth(200);
        tfHeight.setAlignment(Pos.BOTTOM_LEFT);
        tfStorePath.setText("D:/qrcode/");
        tfStorePath.setPrefWidth(200);
        tfStorePath.setAlignment(Pos.BOTTOM_LEFT);

        // 设置按钮的长度
        btConfirm.setPrefWidth(300);
        btConfirm.setStyle("-fx-border-color: gray; -fx-text-fill: #ffff; -fx-background-color: #3399FF;");
    }

    // 获取输入的文件名
    public String getPFileName() {
        String fileName = tfFileName.getText().trim();
        if (fileName == null || "".equals(fileName)) {
            return null;
        }
        return fileName;
    }

    // 获取输入的文件按格式
    public String getPFileFormat() {
        String format = tfFileFormat.getText().trim();
        if (format == null || "".equals(format)) {
            return null;
        }
        return format;
    }

    // 获取宽度
    public Integer getPWidth() {
        String width = tfWidth.getText().trim();
        if (width == null || "".equals(width)) {
            return null;
        } else {
            return Integer.parseInt(width);
        }
    }

    // 获取高度
    public Integer getPHeight() {
        String height = tfHeight.getText().trim();
        if (height == null || "".equals(height)) {
            return null;
        } else {
            return Integer.parseInt(height);
        }
    }

    // 获取路径
    public String getPPath() {
        StringBuilder result = new StringBuilder();
        String path = tfStorePath.getText().trim();
        if (path == null || "".equals(path)) {
            return null;
        }
        String[] pieces = path.split("[/ \\\\]");
        for (int i = 0; i < pieces.length - 1; i++) {
            if (pieces[i] != null && pieces[i].trim() != "") {
                result.append(pieces[i] + File.separator);
            }
        }
        result.append(pieces[pieces.length - 1]);
        return result.toString();
    }

    // 获取确认按钮
    public Button getBtConfirm() {
        return btConfirm;
    }
}

