package cn.nianzuochen.zxing;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ReadQRCode {
    public static void main(String[] args) {
        // 读取二维码的对象
        MultiFormatReader formatReader = new MultiFormatReader();
        // 二维码存储位置
        File file = new File("F:/zxing/image.jpg");

        try {
            // 读取图片信息
            BufferedImage image = ImageIO.read(file);
            // 获取二进制矩阵值
            BinaryBitmap binaryBitmap =
                    new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
            // 定义二维码参数
            HashMap<DecodeHintType, Object> hints = new HashMap();
            // 确定字符集的编码
            hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
            // 解码
            Result result = formatReader.decode(binaryBitmap, hints);
            // 打印解码结果
            System.out.println("解析结果：" + result.toString());
            System.out.println("二维码格式类型：" + result.getBarcodeFormat());
            System.out.println("二维码文本内容：" + result.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }


    }
}
