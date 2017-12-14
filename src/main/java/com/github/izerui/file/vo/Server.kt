package com.github.izerui.file.vo

import java.util.ArrayList

class Server {
    var server: String? = null
    var services: List<Service> = ArrayList()

    class Service {
        var file: String? = null
        var type: String? = null
    }
}
