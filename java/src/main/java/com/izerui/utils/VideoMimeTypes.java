package com.izerui.utils;
public enum VideoMimeTypes{
		FLV(0),F4V(1),M4V(2),MOV(3),MP3(4),MP4(5),OGV(6),AAC(7),M4A(8),F4A(9),OGG(10),OGA(11);
		
		private int value;
		
		public int getValue() {
			return value;
		}
		VideoMimeTypes(int value) {
			this.value = value;
		}
		public String getMine() {
			switch (value) {
			case 0:
				return "video/x-flv";
			case 1:
				return "video/x-f4v";
			case 2:
				return "video/x-m4v";
			case 3:
				return "video/vnd.sealedmedia.softseal.mov";
			case 4:
				return "audio/mpeg";
			case 5:
				return "audio/mp4";
			case 6:
				return "video/ogg";
			case 7:
				return "audio/vnd.dolby.heaac.1";
			case 8:
				return "audio/mp4a-latm";
			case 9:
				return "audio/mp4";
			case 10:
				return "video/ogg";
			case 11:
				return "video/ogg";
			}
			return "video/x-flv";
		}
		
		public static VideoMimeTypes getVideoMimeTypes(String ext){
			if(ext.equalsIgnoreCase("FLV")){
				return FLV;
			}else if(ext.equalsIgnoreCase("F4V")){
				return F4V;
			}else if(ext.equalsIgnoreCase("M4V")){
				return M4V;
			}else if(ext.equalsIgnoreCase("MOV")){
				return MOV;
			}else if(ext.equalsIgnoreCase("MP3")){
				return MP3;
			}else if(ext.equalsIgnoreCase("MP4")){
				return MP4;
			}else if(ext.equalsIgnoreCase("OGV")){
				return OGV;
			}else if(ext.equalsIgnoreCase("AAC")){
				return AAC;
			}else if(ext.equalsIgnoreCase("M4A")){
				return M4A;
			}else if(ext.equalsIgnoreCase("F4A")){
				return F4A;
			}else if(ext.equalsIgnoreCase("OGG")){
				return OGG;
			}else if(ext.equalsIgnoreCase("OGA")){
				return OGA;
			}else{
				return null;
			}
		}
		
	}