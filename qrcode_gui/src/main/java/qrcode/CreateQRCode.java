package qrcode;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.io.IOUtils;
import sun.nio.ch.IOUtil;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;

// 生成 二维码
public class CreateQRCode {
   public static BufferedImage create(String content) {
       return create(content, 300, 300);
   }

   public static BufferedImage create(String content, int width, int height) {
        // 定义二维码参数
        HashMap hints = new HashMap();
        // 确定字符集的编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 确定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置边距
        hints.put(EncodeHintType.MARGIN, 2);


        try {
            // 确定内容，编码格式，宽，高，其他配置参数，获取二维码矩阵值
            // 开始的时候是将图片写入文件之后再读取，无法实现，文件还没有写入磁盘的时候就已经读取了，完全读取不到
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }
            return image;

        } catch (WriterException e) {
            e.printStackTrace();
        }
       return null;
   }

   // 存储图片
   public static boolean storeImage(BufferedImage bi, String path, String format) {
       try {
           ByteArrayOutputStream bos = new ByteArrayOutputStream();
           ImageOutputStream imageOut = ImageIO.createImageOutputStream(bos);
           ImageIO.write(bi, format, imageOut);
           InputStream inputStream = new ByteArrayInputStream(bos.toByteArray());

           OutputStream outputStream = new FileOutputStream(path);
           IOUtils.copy(inputStream, outputStream);
           inputStream.close();
           outputStream.close();
           return true;
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }
   }
}
