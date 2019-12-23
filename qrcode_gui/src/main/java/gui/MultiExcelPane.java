package gui;

import entity.Entity;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import poi.ExtractExcel2Object;
import qrcode.ParallelCreateQRCode;

import java.io.File;
import java.util.List;


public class MultiExcelPane extends VBox {
    private GridPane gpChoose = new GridPane();

    private TextField tfSourceFile = new TextField();
    private TextField tfExportDirectory = new TextField();
    private TextField tfWidth = new TextField();
    private TextField tfHeight = new TextField();
    private TextField tfFormat = new TextField();
    private Button btConfirm = new Button("确认导出");

    private ParallelCreateQRCode parallelCreateQRCode;
    // 成功提示面板
    private Stage successStage = new Stage();
    private SuccessPane successPane = new SuccessPane();

    // 警告提示面板
    private Stage warningStage = new Stage();
    private WarningPane warningPane = new WarningPane();

    public MultiExcelPane() {
        initSuccessWarningPane();
        initGpChoose();
        initField();
        getChildren().addAll(gpChoose, btConfirm);
        // 注册事件
        clickExportFromExcelConfirm();
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

    // 从 excel 确认导出
    private void clickExportFromExcelConfirm() {
        btConfirm.setOnAction(e -> {
            String sourceFile = getSourceFilePath();
            Integer width = getPWidth();
            Integer height = getPHeight();
            String format = getPFileFormat();
            String targetDirectory = getExportDirectory();

            if (sourceFile == null || targetDirectory == null || width == null || height == null || format == null) {
                warningPane.setLbWarning("信息不完整");
                warningStage.show();
            } else {
                File source = new File(sourceFile);
                File target = new File(targetDirectory);
                if (!source.isFile()) {
                    warningPane.setLbWarning("数据源文件错误");
                    warningStage.show();
                } else if (!target.isDirectory()) {
                    warningPane.setLbWarning("指定存储路径不存在");
                    warningStage.show();
                } else {
                    successPane.waitingCreate();
                    successStage.show();
                    // 在新的线程中完成并行导出操作，此时主线程在控制面板
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Class clazz = new Entity().getClass();
                            ExtractExcel2Object<Entity> excel2Object = new ExtractExcel2Object<>(clazz);
                            File file = new File(sourceFile);
                            if (file.exists()) {
                                List<Entity> entities = excel2Object.extract(file);
                                parallelCreateQRCode =
                                        new ParallelCreateQRCode(width, height, format, targetDirectory);
                                parallelCreateQRCode.parallelCreate(entities);
                            }
                            // 当 parallelCreateQRCode.getFinished() 为 true 表示导出完成
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
