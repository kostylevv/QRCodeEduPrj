package com.vkostylev.edu.qrcodeprj;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface QRService {
    BufferedImage getQRCodeImage(String contents, int size, ErrorCorrectionLevel correctionLevel) throws IOException;
}
