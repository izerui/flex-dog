package com.github.izerui.file.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "st_ignore_type", indexes = {
        @Index(name = "application", columnList = "application"),
        @Index(name = "type", columnList = "type"),
        @Index(name = "name", columnList = "name")
})
public class StIgnoreType {
    @Id
    private long id;
    private String application;
    private String type;
    private String name;
}
