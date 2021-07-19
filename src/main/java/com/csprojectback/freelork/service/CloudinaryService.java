package com.csprojectback.freelork.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {

    Map uploadFile(MultipartFile multipartfile,String folder) throws IOException;

    Map deleteFile(String id) throws IOException;

}
