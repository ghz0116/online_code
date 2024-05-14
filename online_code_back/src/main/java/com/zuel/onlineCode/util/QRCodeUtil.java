package com.zuel.onlineCode.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.util.UUID;

public class QRCodeUtil {

    public static void main(String[] args) {
        try {
            // 生成UUID
            String uuid = generateUUID();

            // 生成二维码
            BitMatrix matrix = generateQRCode(uuid);
            File outputFile = new File("qrcode.png");

            // 将二维码保存为图片
            MatrixToImageWriter.writeToFile(matrix, "PNG", outputFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static BitMatrix generateQRCode(String data) throws WriterException {
        int width = 300; // 二维码宽度
        int height = 300; // 二维码高度
        // 生成二维码
        Writer writer = new QRCodeWriter();
        return writer.encode(data, BarcodeFormat.QR_CODE, width, height);
    }

}
