package com.mrdong.iphoto.controller;

import com.mrdong.iphoto.common.Response;
import com.mrdong.iphoto.dto.PhotoInfoDto;
import com.mrdong.iphoto.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PhotoManageController {

    @Autowired
    private PhotoService photoService;

    @GetMapping("list_photos")
    public Response listPhoto() {
        List<PhotoInfoDto> photoInfoDtos = photoService.listPhoto();

        return new Response().success().data(photoInfoDtos);
    }
}
