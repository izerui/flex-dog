package com.github.izerui.file.utils

enum class VideoMimeTypes private constructor(val value: Int) {
    FLV(0), F4V(1), M4V(2), MOV(3), MP3(4), MP4(5), OGV(6), AAC(7), M4A(8), F4A(9), OGG(10), OGA(11);

    val mine: String
        get() {
            when (value) {
                0 -> return "video/x-flv"
                1 -> return "video/x-f4v"
                2 -> return "video/x-m4v"
                3 -> return "video/vnd.sealedmedia.softseal.mov"
                4 -> return "audio/mpeg"
                5 -> return "audio/mp4"
                6 -> return "video/ogg"
                7 -> return "audio/vnd.dolby.heaac.1"
                8 -> return "audio/mp4a-latm"
                9 -> return "audio/mp4"
                10 -> return "video/ogg"
                11 -> return "video/ogg"
            }
            return "video/x-flv"
        }

    companion object {

        fun getVideoMimeTypes(ext: String): VideoMimeTypes? {
            when (ext.toUpperCase()) {
                "FLV" -> return FLV;
                "F4V" -> return F4V;
                "M4V" -> return M4V;
                "MOV" -> return MOV;
                "MP3" -> return MP3;
                "MP4" -> return MP4;
                "OGV" -> return OGV;
                "AAC" -> return AAC;
                "M4A" -> return M4A;
                "F4A" -> return F4A;
                "OGG" -> return OGG;
                "OGA" -> return OGA;
                else -> return null;
            }
        }
    }

}