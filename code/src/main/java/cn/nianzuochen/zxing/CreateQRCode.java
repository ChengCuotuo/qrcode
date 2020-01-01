package cn.nianzuochen.zxing;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

// 生成 二维码
public class CreateQRCode {
    public static void main(String[] args) {
        int width = 300;
        int height = 300;

        String format = "jpg";
        String content = "Hello world!";
        // 个人名片
//        String content ="BEGIN:VCARD\n" +
//                "VERSION:2.1\n" +
//                "N:姓;名\n" +
//                "FN:姓名\n" +                       // 姓名
//                "NICKNAME:nickName\n" +             // 昵称
//                "ORG:公司;部门\n" +                 // 组织
//                "TITLE:职位\n" +                    // 职位
//                "TEL;WORK;VOICE:电话1\n" +
//                "TEL;WORK;VOICE:电话2\n" +
//                "TEL;HOME;VOICE:电话1\n" +
//                "TEL;HOME;VOICE:电话2\n" +
//                "TEL;CELL;VOICE:213231231\n" +      // 移动电话
//                "TEL;PAGER;VOICE:0775\n" +          // 电话
//                "TEL;WORK;FAX:传真\n" +
//                "TEL;HOME;FAX:传真\n" +
//                "ADR;WORK:;;单位地址:深圳;广东;43000;国家\n" +
//                "ADR;HOME;POSTAL;PARCEL:;;街道地址;深圳;广东;43000;中国\n" +
//                "URL:网址\n" +
//                "EMAIL;PREF;INTERNET:邮箱地址\n" +
//                "X-QQ:11111111\n" +
//                "END:VCARD";


        // 定义二维码参数
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        // 确定字符集的编码
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 确定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        // 设置边距，默认为 5
        hints.put(EncodeHintType.MARGIN, 2);

        // 生成
        try {
            // 确定内容，编码格式，宽，高，其他配置参数，获取二维码矩阵值
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            // 将生成的图片保存在指定路径下
            Path file = new File("F:/qrcode/123.jpg").toPath();
            MatrixToImageWriter.writeToPath(bitMatrix, format, file);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
