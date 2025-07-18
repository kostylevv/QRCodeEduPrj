package com.vkostylev.edu.qrcodeprj;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ImageFormatConverter implements Converter<String, ImageFormat> {

    @Override
    public ImageFormat convert(String source) {
        return ImageFormat.valueOf(source.toUpperCase()); // Convert to uppercase safely
    }
}
