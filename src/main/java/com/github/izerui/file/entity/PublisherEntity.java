package com.github.izerui.file.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "publisher")
public class PublisherEntity {

    @Id
    private String id;
    private String name;
    private String phone;
}
