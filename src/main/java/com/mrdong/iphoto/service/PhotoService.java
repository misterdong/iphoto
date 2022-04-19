package com.mrdong.iphoto.service;

import com.mrdong.iphoto.dto.PhotoInfoDto;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class PhotoService {

    public List<PhotoInfoDto> listPhoto() {
        List<PhotoInfoDto> photoInfoDtos = new ArrayList<>();
        String photoPath = "/Users/liudong/Documents/bak";

        File photoPathFile = new File(photoPath);

        int id = 0;
        if (photoPathFile.isDirectory()) {
            File[] files = photoPathFile.listFiles();
            for (File file : files) {
                PhotoInfoDto photoInfoDto = new PhotoInfoDto();
                photoInfoDto.setPhotoPath(file.getAbsolutePath());
                photoInfoDto.setId(++id);
                photoInfoDtos.add(photoInfoDto);
            }
        }
        return photoInfoDtos;
    }
}
