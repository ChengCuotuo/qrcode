package gui;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mysql.ConnectDataBase;
import entity.Entity;
import poi.ExtractExcel2Object;
import qrcode.CreateQRCode;
import qrcode.ParallelCreateQRCode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;


public class SinglePane extends Pane {

    private ImageView qrcodeImg = new ImageView();
    private HBox hbHead = new HBox(10);
    private HBox hbBody = new HBox(10);
    private BufferedImage bfqrcode = null;


    private  ComboBox<String> cbo = new ComboBox<>();
    private TextField input = new TextField();
    private Button btCreate = new Button("生成");
    private Button btExport = new Button("导出");

    // 导出单独文件的窗体
    private Stage exportStage = new Stage();
    private SingleExportPane exportPane = new SingleExportPane();

    // 从 excel 导出的窗体
    private Stage exportFromExcelStage = new Stage();
    private MultiExcelPane excelPane = new MultiExcelPane();

    // 连接数据库窗体
    private Stage connectStage = new Stage();
    private MultiDBConnectPane connectPane = new MultiDBConnectPane();
    private ConnectDataBase connectDataBase;

    // 从数据库中导出
    private Stage dbExportStage = new Stage();
    private MultiDBExportPane dbExportPane = new MultiDBExportPane();

    // 成功提示面板
    private Stage successStage = new Stage();
    private SuccessPane successPane = new SuccessPane();
    private Timeline loadAnimation;

    // 警告提示面板
    private Stage warningStage = new Stage();
    private WarningPane warningPane = new WarningPane();

    public SinglePane() {
        // 默认的 qrcode
        bfqrcode = CreateQRCode.create("Hello world!");
        // 添加主体
        VBox vb = new VBox();
        addHead();
        addBody();
        vb.getChildren().addAll(hbHead, hbBody);
        getChildren().add(vb);

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

    // hbHead 添加批量生成的下拉框
    private void addHead() {
        hbHead.setAlignment(Pos.BASELINE_RIGHT);
        ObservableList<String> items = FXCollections.observableArrayList("从 Excel", "从 MySQL");
        cbo.getItems().addAll(items);
        cbo.setValue("批量生成");
        cbo.setPrefWidth(200);
        hbHead.getChildren().addAll(new Label("QRCode_NianZuochen"), cbo);
        hbHead.setMargin(cbo, new Insets(5, 0, 10, 130));

        // 时间监听注册
        clickCbo();

        // 从 excel 导出的窗体
        Scene excelScene = new Scene(excelPane, 510, 270);
        exportFromExcelStage.setScene(excelScene);
        exportFromExcelStage.setTitle("QRCode_NianZuochen");
        exportFromExcelStage.setResizable(false);
        // 注册确认导出按钮
        clickExportFromExcelConfirm();

        // 连接数据库
        Scene connectScene = new Scene(connectPane, 320, 280);
        connectStage.setScene(connectScene);
        connectStage.setTitle("QRCode_NianZuochen");
        exportFromExcelStage.setResizable(false);
        // 连接事件监听
        clickConnectButton();

        // 从数据库中导出
        Scene dbScene = new Scene(dbExportPane, 510, 350);
        dbExportStage.setScene(dbScene);
        dbExportStage.setTitle("QRCode_NianZuochen");
        dbExportStage.setResizable(false);
        // 选择 table 的事件监听
        clickCbTable();
        // 确认导出的事件监听
        clickDbConfirmExport();
    }

    // 添加 hbbody 内容
    private void addBody() {
        Image image = new Image("/images/hello.jpg");
        hbBody.getChildren().addAll(bodyLeft(image), bodyRight());
        hbBody.setMargin(qrcodeImg, new Insets(0, 0, 10, 10));

        // 添加事件监听
        clickCreate();
        // 设置浮窗面板
        clickConfirmExport();
        Scene scene = new Scene(exportPane, 310, 270);
        exportStage.setScene(scene);
        exportStage.setTitle("QRCode_NianZuochen");
        exportStage.setResizable(false);
        // 导出事件监听
        clickExport();
    }

    // 修改显示的图片
    private ImageView bodyLeft(Image img) {
        qrcodeImg.setImage(img);
        return qrcodeImg;
    }

    // 主体右侧功能键
    private VBox bodyRight() {
        VBox bodyRight = new VBox(10);
        input.setAlignment(Pos.BASELINE_LEFT);
        input.setText("Hello world!");
        input.setPrefWidth(200);

        btCreate.setPrefWidth(200);
        btCreate.setStyle("-fx-border-color: gray; -fx-text-fill: #ffff; -fx-background-color: #3399FF;");

        btExport.setPrefWidth(200);
        btExport.setStyle("-fx-border-color: gray; -fx-text-fill: #ffff; -fx-background-color: #3399FF;");

        Label lb = new Label("Content:");
        bodyRight.getChildren().addAll(lb, input, btCreate, btExport);

        bodyRight.setMargin(lb, new Insets(10, 0, 0, 0));
        bodyRight.setMargin(btCreate, new Insets(20, 0, 10, 0));
        bodyRight.setMargin(btExport, new Insets(10, 0, 10, 0));
        return bodyRight;
    }

    // 监听事件
    // 点击生成
    private void clickCreate() {
        btCreate.setOnAction(e -> {
            String content = input.getText();
            if (content == null || "".equals(content)) {
                warningPane.setLbWarning("content 不能空");
                warningStage.show();
            } else {
                // 将 BufferedImage 读取成 javafx 的 image
                bfqrcode = CreateQRCode.create(content);
                Image image = SwingFXUtils.toFXImage(bfqrcode, null);
                qrcodeImg.setImage(image);
            }
        });
    }

    // 点击导出
    private void clickExport() {
        // 导出之前需要确认文件名，宽，高，保存路径
        btExport.setOnAction(e -> {
            String content = input.getText().trim();
            if (content == null || "".equals(content)) {
                warningPane.setLbWarning("content 不能空");
                warningStage.show();
            } else {
                exportStage.show();
            }

        });
    }

    // 确认导出的监听事件
    private void clickConfirmExport() {
        Button btConfirm = exportPane.getBtConfirm();
        btConfirm.setOnAction(e -> {
            // 获取参数
            String fileName = exportPane.getPFileName();
            String fileFormat = exportPane.getPFileFormat();
            Integer width = exportPane.getPWidth();
            Integer height = exportPane.getPHeight();
            String path = exportPane.getPPath();

            if (fileName == null || fileFormat == null || width == null || height == null || path == null) {
                warningPane.setLbWarning("信息不完整");
                warningStage.show();
            }else {
                File file = new File(path);
                if (!file.isDirectory()) {
                    warningPane.setLbWarning("指定存储路径不存在");
                    warningStage.show();
                } else {
                    //            System.out.println(fileName + ", " + fileFormat + ", " + width + ", " + height + ", " + path);
                    // 获取 BufferedImage 并写入文件
                    String newPath = path + File.separator + fileName + "." + fileFormat;
                    bfqrcode = CreateQRCode.create(input.getText(), width, height);
                    boolean result = CreateQRCode.storeImage(bfqrcode, newPath, fileFormat);
//            System.out.println(result);
                    exportStage.close();
                    successStage.show();
                }

            }
        });
    }

    // 监听下拉框选择
    private void clickCbo() {
        cbo.setOnAction(e -> {
            // 获取点击选择内容
            int index = cbo.getItems().indexOf(cbo.getValue());
            // 弹出指定窗体
            if (index == 0) {
                // 从 excel 中获取数据
                exportFromExcelStage.show();
            } else if (index == 1) {
                // 从 mysql 中获取数据
                connectStage.show();
            }
        });
    }

    // 从 excel 确认导出
    private void clickExportFromExcelConfirm() {
        Button btCofirm = excelPane.getBtConfirm();
        btCofirm.setOnAction(e -> {
            String sourceFile = excelPane.getSourceFilePath();
            String targetDirectory = excelPane.getExportDirectory();
            Integer width = excelPane.getPWidth();
            Integer height = excelPane.getPHeight();
            String format = excelPane.getPFileFormat();

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
                    Class clazz = new Entity().getClass();
                    ExtractExcel2Object<Entity> excel2Object = new ExtractExcel2Object<>(clazz);
                    File file = new File(sourceFile);
                    if (file.exists()) {
                        List<Entity> entities = excel2Object.extract(file);
                        ParallelCreateQRCode parallelCreateQRCode =
                                new ParallelCreateQRCode(width, height, format, targetDirectory);
                        parallelCreateQRCode.parallelCreate(entities);
//                        for (Entity entity : entities) {
//                            BufferedImage bufferedImage = CreateQRCode.create(entity.getContent(), width, height);
//                            String newPath = targetDirectory + File.separator + entity.getName() + "." + format;
//                            CreateQRCode.storeImage(bufferedImage, newPath, format);
//                        }
                    }
                    successPane.finishCreate();

                }
            }

        });
    }

    // 确认连接按钮
    private void clickConnectButton() {
        Button btConnect = connectPane.getBtConnect();
        btConnect.setOnAction(e -> {
            String ip = connectPane.getIp();
            Integer port = connectPane.getPort();
            String database = connectPane.getDatabase();
            String username = connectPane.getUsername();
            String password = connectPane.getPassword();
//            System.out.println(ip + ", " + port + ", " + database + ", " + username + ", " + password);
            // 取人连接结果，连接成功，关闭当前窗体，展示导出窗体，否则显示错误信息
            connectDataBase = new ConnectDataBase(ip, port, database, username, password);
            if(connectDataBase.connect()) {
                // 设置 table 中的数据
                List<String> tables = connectDataBase.getTables();
                dbExportPane.setTable(tables);
                dbExportPane.setContentAndName(connectDataBase.getClumns(tables.get(0)));
                connectStage.close();
                dbExportStage.show();
            } else {
                warningPane.setLbWarning("连接失败");
                warningStage.show();
            }
        });
    }

    // 选取 table 的下拉框事件
    private void clickCbTable() {
        ComboBox<String> cbTable = dbExportPane.getCbTable();
        cbTable.setOnAction(e -> {
            String table = cbTable.getValue();
            dbExportPane.setContentAndName(connectDataBase.getClumns(table));
        });
    }

    // 确认从数据库中导出数据
    public void clickDbConfirmExport() {
        Button confirm = dbExportPane.getBtConfirm();
        confirm.setOnAction(e -> {
            String tableName = dbExportPane.getTable();
            String content = dbExportPane.getContent();
            String name = dbExportPane.getName();
            List<Entity> entities = connectDataBase.getEntities(tableName, content, name);

            String exportPath = dbExportPane.getExportPath();
            Integer width = dbExportPane.getPWidth();
            Integer height = dbExportPane.getPHeight();
            String format = dbExportPane.getPFileFormat();

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
                    ParallelCreateQRCode parallelCreateQRCode =
                            new ParallelCreateQRCode(width, height, format, exportPath);
                    parallelCreateQRCode.parallelCreate(entities);
//                    for (Entity entity : entities) {
//                        BufferedImage bufferedImage = CreateQRCode.create(entity.getContent(), width, height);
//                        String newPath = exportPath + File.separator + entity.getName() + "." + format;
//                        boolean result = CreateQRCode.storeImage(bufferedImage, newPath, format);
////                        System.out.println(result);
//                    }
                    successPane.finishCreate();
                }
            }

        });
    }
}
