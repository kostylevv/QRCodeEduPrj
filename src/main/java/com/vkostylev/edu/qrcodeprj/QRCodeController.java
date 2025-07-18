package com.vkostylev.edu.qrcodeprj;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@Validated
public class QRCodeController {

    QRService qrService;

    @Autowired
    public QRCodeController(QRService qrService) {
        this.qrService = qrService;
    }

    @GetMapping("/api/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/api/qrcode")
    public ResponseEntity<byte[]> qrcode(@Valid @ModelAttribute ImageRequestDTO imageRequestDTO)
            throws IOException {
        ImageRequestDTO withDefaults = applyDefaults(imageRequestDTO);

        BufferedImage bufferedImage = qrService.getQRCodeImage(withDefaults.contents(), withDefaults.size(),
                withDefaults.correction());

        try (var baos = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, withDefaults.type().toString(), baos);
            MediaType mediaType = switch (withDefaults.type()) {
                case PNG -> MediaType.IMAGE_PNG;
                case JPEG -> MediaType.IMAGE_JPEG;
                case GIF -> MediaType.IMAGE_GIF;
            };

            byte[] bytes = baos.toByteArray();
            return ResponseEntity
                    .ok()
                    .contentType(mediaType)
                    .body(bytes);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    private ImageRequestDTO applyDefaults(ImageRequestDTO dto) {
        return new ImageRequestDTO(
                dto.contents(),
                dto.size() != null ? dto.size() : 250,
                dto.type() != null ? dto.type() : ImageFormat.PNG,
                dto.correction() != null ? dto.correction() : ErrorCorrectionLevel.L
        );
    }



}
