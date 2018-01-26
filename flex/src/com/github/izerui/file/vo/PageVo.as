package com.github.izerui.file.vo {
import mx.collections.ArrayCollection;

[Bindable]
[RemoteClass(alias="com.ecworking.commons.vo.PageVo")]
public class PageVo {
    public function PageVo() {
    }

    public var number:Number = 0;

    public var size:Number = 50;

    public var totalPages:Number = 0;

    public var totalElements:Number = 0;

    public var content:ArrayCollection;
}
}
