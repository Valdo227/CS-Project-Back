package com.csprojectback.freelork.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.csprojectback.freelork.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImp implements CloudinaryService {

    Cloudinary cloudinary;

    public CloudinaryServiceImp(){
        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("cloud_name","dieascwb6");
        valuesMap.put("api_key","555555578926797");
        valuesMap.put("api_secret","SJbKPxL7FpnU50Q_RNC6XowMq6s");
        cloudinary = new Cloudinary(valuesMap);
    }

    @Override
    public Map uploadFile(MultipartFile multipartFile, String folder) throws IOException{
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.asMap("folder",folder));
        file.delete();
        return result;
    }

    @Override
    public Map deleteFile(String id) throws IOException{
        return cloudinary.uploader().destroy(id,ObjectUtils.emptyMap());
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        FileOutputStream fileOutput = new FileOutputStream(file);
        fileOutput.write(multipartFile.getBytes());
        fileOutput.close();
        return file;
    }

}
