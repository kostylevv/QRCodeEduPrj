package com.vkostylev.edu.qrcodeprj;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ImageRequestDTO(
        @NotNull
        @NotBlank
        String contents,

        @Min(150)
        @Max(350)
        Integer size,

        ImageFormat type,

        ErrorCorrectionLevel correction
) {
}
