package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;


public class MultiExcelPane extends VBox {
    private GridPane gpChoose = new GridPane();

    private TextField tfSourceFile = new TextField();
    private TextField tfExportDirectory = new TextField();
    private TextField tfWidth = new TextField();
    private TextField tfHeight = new TextField();
    private TextField tfFormat = new TextField();
    private Button btConfirm = new Button("确认导出");

    public MultiExcelPane() {
        initGpChoose();
        initField();
        getChildren().addAll(gpChoose, btConfirm);
        setMargin(gpChoose, new Insets(10, 10, 10, 10));
        setMargin(btConfirm, new Insets(10, 10, 10, 10));
    }

    private void initGpChoose() {
        gpChoose.setHgap(10);
        gpChoose.setVgap(10);

        gpChoose.add(new Label("源文件："), 0, 0);
        gpChoose.add(new Label("导出文件夹："), 0, 1);
        gpChoose.add(new Label("宽："), 0, 2);
        gpChoose.add(new Label("高："), 0, 3);
        gpChoose.add(new Label("格式："), 0, 4);

        gpChoose.add(tfSourceFile, 1, 0);
        gpChoose.add(tfExportDirectory, 1, 1);
        gpChoose.add(tfWidth, 1, 2);
        gpChoose.add(tfHeight, 1, 3);
        gpChoose.add(tfFormat, 1, 4);
    }

    private void initField() {
        tfSourceFile.setPrefWidth(400);
        tfSourceFile.setAlignment(Pos.BOTTOM_LEFT);
        tfExportDirectory.setText("D:/qrcode/");
        tfExportDirectory.setPrefWidth(400);
        tfExportDirectory.setAlignment(Pos.BOTTOM_LEFT);
        tfWidth.setPrefWidth(400);
        tfWidth.setText("300");
        tfWidth.setAlignment(Pos.BOTTOM_LEFT);
        tfHeight.setPrefWidth(400);
        tfHeight.setText("300");
        tfHeight.setAlignment(Pos.BOTTOM_LEFT);
        tfFormat.setPrefWidth(400);
        tfFormat.setText("jpg");
        tfFormat.setAlignment(Pos.BOTTOM_LEFT);
        tfFormat.setDisable(true);

        // 设置按钮的长度
        btConfirm.setPrefWidth(500);
        btConfirm.setStyle("-fx-border-color: gray; -fx-text-fill: #ffff; -fx-background-color: #3399FF;");
    }

    // 获取源文件路径
    public String getSourceFilePath() {
        String sourceFilePath = tfSourceFile.getText().trim();
        if (sourceFilePath == null || "".equals(sourceFilePath)) {
            return null;
        } else {
            return sourceFilePath;
        }
    }

    // 获取导出文件夹路径
    public String getExportDirectory() {
        String exportDirectory = tfExportDirectory.getText().trim();
        if (exportDirectory == null || "".equals(exportDirectory)) {
            return null;
        } else {
            return exportDirectory;
        }
    }

    // 获取输入的文件按格式
    public String getPFileFormat() {
        String format = tfFormat.getText().trim();
        if (format == null || "".equals(format)) {
            return null;
        } else {
            return format;
        }
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

    // 获取点击按钮
    public Button getBtConfirm() {
        return btConfirm;
    }
}
