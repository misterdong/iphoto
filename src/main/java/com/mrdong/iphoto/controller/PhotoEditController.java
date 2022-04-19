package com.mrdong.iphoto.controller;

import cn.hutool.json.JSONUtil;
import com.mrdong.iphoto.dto.PhotoEditDto;
import com.mrdong.iphoto.service.PhotoEditService;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfoGpsText;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

@RestController
public class PhotoEditController {

    @Autowired
    private PhotoEditService photoEditService;

    @PostMapping("/edit")
    public void edit(@RequestParam("photo") String photoEditJson, @RequestParam("file") List<MultipartFile> file) throws Exception {
        PhotoEditDto photoEditDto = JSONUtil.toBean(photoEditJson, PhotoEditDto.class);
        photoEditService.edit(photoEditDto,file);

    }
}
