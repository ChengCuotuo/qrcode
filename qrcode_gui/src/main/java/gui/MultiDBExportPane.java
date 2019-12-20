package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MultiDBExportPane extends VBox {

    private GridPane gpChoose = new GridPane();

    private ComboBox<String> cbTable = new ComboBox<>();
    private ComboBox<String> cbContent = new ComboBox<>();
    private ComboBox<String> cbName = new ComboBox<>();
    private TextField tfExportPath = new TextField();
    private TextField tfWidth = new TextField();
    private TextField tfHeight = new TextField();
    private TextField tfFormat = new TextField();

    private Button btConfirm = new Button("确认导出");

    public MultiDBExportPane() {
        initGpChoose();
        initField();
        getChildren().addAll(gpChoose, btConfirm);
        setMargin(gpChoose, new Insets(10, 10, 10, 10));
        setMargin(btConfirm, new Insets(10, 10, 10, 10));
    }

    private void initGpChoose() {
        gpChoose.setHgap(10);
        gpChoose.setVgap(10);

        gpChoose.add(new Label("Table: "),0, 0);
        gpChoose.add(new Label("Content: "), 0, 1);
        gpChoose.add(new Label("Name"), 0, 2);
        gpChoose.add(new Label("输出路径: "), 0, 3);
        gpChoose.add(new Label("宽："), 0, 4);
        gpChoose.add(new Label("高："), 0, 5);
        gpChoose.add(new Label("格式："), 0, 6);

        gpChoose.add(cbTable, 1, 0);
        gpChoose.add(cbContent, 1, 1);
        gpChoose.add(cbName, 1, 2);
        gpChoose.add(tfExportPath, 1, 3);
        gpChoose.add(tfWidth, 1, 4);
        gpChoose.add(tfHeight, 1, 5);
        gpChoose.add(tfFormat, 1, 6);
    }

    private void initField() {
        cbTable.setPrefWidth(400);
        cbContent.setPrefWidth(400);
        cbName.setPrefWidth(400);
        tfExportPath.setPrefWidth(400);
        tfExportPath.setAlignment(Pos.BOTTOM_LEFT);
        tfExportPath.setText("D:/qrcode/");
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

    public String getTable() {
        return cbTable.getValue();
    }

    public void setTable(List<String> tables) {
        ObservableList<String> items = FXCollections.observableArrayList(tables);
        // 清空原有的内容
        cbTable.getItems().clear();
        cbTable.getItems().addAll(items);
        cbTable.setValue(tables.get(0));
    }

    // 返回 table 的下拉框
    public ComboBox<String> getCbTable() {
        return cbTable;
    }

    public String getContent() {
        return cbContent.getValue();
    }

    public void setContentAndName(List<String> columns) {
        ObservableList<String> items = FXCollections.observableArrayList(columns);
        // 清空原有的内容
        cbContent.getItems().clear();
        cbName.getItems().clear();
        cbContent.getItems().addAll(items);
        cbName.getItems().addAll(items);
        cbContent.setValue(columns.get(0));
        cbName.setValue(columns.get(0));
    }

    public String getName() {
        return cbName.getValue();
    }

    public String getExportPath() {
        String exportPath = tfExportPath.getText().trim();
        if (exportPath == null || "".equals(exportPath)) {
            return null;
        } else {
            return exportPath;
        }
    }

    public Button getBtConfirm() {
        return btConfirm;
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
}
