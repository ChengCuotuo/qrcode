package gui;

import entity.Entity;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mysql.ConnectDataBase;
import qrcode.ParallelCreateQRCode;

import java.io.File;
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

    private ConnectDataBase connectDataBase;

    private Button btConfirm = new Button("确认导出");

    // 成功提示面板
    private Stage successStage = new Stage();
    private SuccessPane successPane = new SuccessPane();

    // 警告提示面板
    private Stage warningStage = new Stage();
    private WarningPane warningPane = new WarningPane();

    public MultiDBExportPane() {
        initSuccessWarningPane();
        initGpChoose();
        initField();
        clickCbTable();
        clickDbConfirmExport();
        getChildren().addAll(gpChoose, btConfirm);
        setMargin(gpChoose, new Insets(10, 10, 10, 10));
        setMargin(btConfirm, new Insets(10, 10, 10, 10));

    }

    public void setConnectDataBase(ConnectDataBase connectDataBase) {
        this.connectDataBase = connectDataBase;
    }

    // 初始化成功和失败面板
    public void initSuccessWarningPane() {
        // 成功面板
        Scene successScene = new Scene(successPane,600, 400);
        successStage.setScene(successScene);
        successStage.setTitle("QRCode_NianZuochen");
        successStage.setResizable(false);


        // 警告面板
        Scene warningScene = new Scene(warningPane, 400, 400);
        warningStage.setScene(warningScene);
        warningStage.setTitle("QRCode_NianZuochen");
        warningStage.setResizable(false);
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

    // 选取 table 的下拉框事件
    private void clickCbTable() {
        ComboBox<String> cbTable = getCbTable();
        cbTable.setOnAction(e -> {
            String table = cbTable.getValue();
            setContentAndName(connectDataBase.getClumns(table));
        });
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

    // 确认从数据库中导出数据
    public void clickDbConfirmExport() {
        btConfirm.setOnAction(e -> {
            String tableName = getTable();
            String content = getContent();
            String name = getName();
            List<Entity> entities = connectDataBase.getEntities(tableName, content, name);

            String exportPath = getExportPath();
            Integer width = getPWidth();
            Integer height = getPHeight();
            String format = getPFileFormat();

            if (exportPath == null || width == null || height == null || format == null) {
                warningPane.setLbWarning("信息不完整");
                warningStage.show();
            } else {
                File exportFile = new File(exportPath);
                if (!exportFile.isDirectory()) {
                    warningPane.setLbWarning("指定存储路径不存在");
                    warningStage.show();
                } else {
                    successPane.waitingCreate();
                    successStage.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            ParallelCreateQRCode parallelCreateQRCode =
                                    new ParallelCreateQRCode(width, height, format, exportPath);
                            parallelCreateQRCode.parallelCreate(entities);
                            while (true) {
                                if (parallelCreateQRCode.getFinished()) {
                                    successPane.stopWaitingAnimation();
                                    // 非主线程修改面板要使用 Platform.runLater进行修改
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            successPane.finishCreate();
                                        }
                                    });
                                    break;
                                } else  {
                                    // 没 0.5s 检查一次
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                    }).start();
                }
            }

        });
    }
}
