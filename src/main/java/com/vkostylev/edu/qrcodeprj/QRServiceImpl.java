package com.vkostylev.edu.qrcodeprj;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Map;

@Service
public class QRServiceImpl implements QRService {

    @Override
    public BufferedImage getQRCodeImage(String contents, int size, ErrorCorrectionLevel correctionLevel) throws IllegalStateException {
        QRCodeWriter writer = new QRCodeWriter();
        Map<EncodeHintType, ?> hints = Map.of(EncodeHintType.ERROR_CORRECTION, correctionLevel);
        try {
            BitMatrix bitMatrix = writer.encode(contents, BarcodeFormat.QR_CODE, size, size, hints);
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
        } catch (WriterException e) {
            throw new IllegalStateException("Could not create QR Code");
        }
    }
}
