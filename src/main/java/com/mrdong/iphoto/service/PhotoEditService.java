package com.mrdong.iphoto.service;

import com.mrdong.iphoto.FileUtils;
import com.mrdong.iphoto.dto.PhotoEditDto;
import com.mrdong.iphoto.dto.PhotoGPSInfo;
import com.mrdong.iphoto.dto.PhotoMetaData;
import com.mrdong.iphoto.util.DateUtils;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.MicrosoftTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputField;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PhotoEditService {

    public void edit(PhotoEditDto photoEditDto, List<MultipartFile> photoFiles) throws Exception {

        for (MultipartFile photoFile : photoFiles) {

            File file = FileUtils.multipartFileToFile(photoFile);

            getInfo(file);

            ImageMetadata metadata = Imaging.getMetadata(file);
            JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;


            PhotoMetaData photoMetaData = getPhotoMetaData(jpegMetadata);

            TiffImageMetadata exif = jpegMetadata.getExif();
            TiffOutputSet out = exif.getOutputSet();
            //获取TiffOutputDirectory
            TiffOutputDirectory exifDirectory = out
                    .getOrCreateExifDirectory();

            //拍摄日期修改
            if (photoEditDto.getShootDate() != null) {
                Date shootDate = photoEditDto.getShootDate();

                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);

                String date = DateUtils.pattern(DateUtils.DATE_FORMAT_001, shootDate);

                exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, date);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED, date);
            }


            //gps信息修改
            if (photoEditDto.getGpsInfo() != null) {
                out.setGPSInDegrees( photoEditDto.getGpsInfo().getLongitude(),photoEditDto.getGpsInfo().getLatitude());
            }

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("/Users/liudong/Documents/bak/"+file.getName()));
            //写图片
            new ExifRewriter().updateExifMetadataLossless(file, bos, out);
        }

    }

    /**
     * 获取照片元数据
     * @param metadata
     * @throws ImageReadException
     * @throws ImageWriteException
     */
    private PhotoMetaData getPhotoMetaData(JpegImageMetadata metadata) throws ImageReadException, ImageWriteException {

        PhotoMetaData photoMetaData = new PhotoMetaData();

        if (metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL) != null) {
            Object dateValue = metadata.findEXIFValue(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL).getValue();
            Date date = DateUtils.getDate(dateValue.toString(), DateUtils.DATE_FORMAT_001);
            photoMetaData.setShootDate(date);
        }

        //标题
        if (metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPTITLE) != null) {
            Object titleValue = metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPTITLE).getValue();
            photoMetaData.setTitle(titleValue.toString());
        }

        //主题
        if (metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT) != null) {
            Object subjectValue = metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPSUBJECT).getValue();
            photoMetaData.setSubject(subjectValue.toString());
        }

        //备注
        if (metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPCOMMENT) != null) {
            Object commentValue = metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPCOMMENT).getValue();
            photoMetaData.setComments(commentValue.toString());
        }

        //标记
        if (metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS) != null) {
            Object keywordsValue = metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPKEYWORDS).getValue();
            photoMetaData.setKeywords(keywordsValue.toString());
        }


        TiffImageMetadata exif = metadata.getExif();

        //经纬度信息
        if (exif != null && exif.getGPS()!=null) {
            TiffImageMetadata.GPSInfo gpsInfo = exif.getGPS();
            double longitudeAsDegreesEast = gpsInfo.getLongitudeAsDegreesEast();
            double latitudeAsDegreesNorth = gpsInfo.getLatitudeAsDegreesNorth();

            PhotoGPSInfo photoGPSInfo = new PhotoGPSInfo(longitudeAsDegreesEast, latitudeAsDegreesNorth);
            photoMetaData.setPhotoGPSInfo(photoGPSInfo);
        }

        return photoMetaData;
    }


    public void getInfo(File file) throws IOException, ImageReadException, ImageWriteException {
        ImageMetadata imageMetadata = Imaging.getMetadata(file);
        JpegImageMetadata metadata = (JpegImageMetadata) imageMetadata;

        //遍历所有图片属性
        for (ImageMetadata.ImageMetadataItem item : metadata.getItems()) {
            System.out.println(item);
        }

        //获取标题属性的值
        if (metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPTITLE) != null) {
            Object titleValue = metadata.findEXIFValue(MicrosoftTagConstants.EXIF_TAG_XPTITLE).getValue();
            System.out.println(titleValue);
        }


        //获取经纬度信息
        TiffImageMetadata exif = metadata.getExif();

        if (exif != null && exif.getGPS() != null) {
            TiffImageMetadata.GPSInfo gpsInfo = exif.getGPS();
            double longitudeAsDegreesEast = gpsInfo.getLongitudeAsDegreesEast();
            double latitudeAsDegreesNorth = gpsInfo.getLatitudeAsDegreesNorth();
        }

        //修改某个属性值
        TiffOutputSet out = exif.getOutputSet();
        //获取TiffOutputDirectory
        TiffOutputDirectory exifDirectory = out.getOrCreateExifDirectory();

        //拍摄日期修改 注意日期格式为 yyyy:MM:dd HH:mm:ss
        exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
        exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED);

        exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, "2022:01:01 00:00:00");
        exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED, "2022:01:01 00:00:00");

        out.setGPSInDegrees(116.23128, 40.22077);

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("path"));
        //写图片
        new ExifRewriter().updateExifMetadataLossless(file, bos, out);

    }
}
