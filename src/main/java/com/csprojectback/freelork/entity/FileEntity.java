package com.csprojectback.freelork.entity;

import com.csprojectback.freelork.model.ViewModel;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
public class FileEntity {

    private String name;

    private String url;

    public FileEntity(String name, String url) {
        this.name = name;
        this.url = url;
    }



}
