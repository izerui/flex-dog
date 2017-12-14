package com.github.izerui.file.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.util.Date

/**
 * Created by serv on 2017/4/8.
 */
@Entity
@Table(name = "deploy")
class DeployEntity {

    @Id
    var fileName: String? = null
    var deployTime: Date = Date()
}
