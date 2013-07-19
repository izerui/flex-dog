package com.izerui.utils;

import org.apache.commons.io.FilenameUtils;

public class ExtendFilenameUtils extends FilenameUtils {
	public static String getLastFolderPathNoEndSeparator(String filename) {
		String path = getFullPathNoEndSeparator(filename);
		int index = indexOfLastSeparator(path);
		if (index < 0) {// 如果是根目录 Returns the length of the filename prefix,
						// such as C:/ or ~/.
			return filename.substring(0, getPrefixLength(filename));
		}
		return path.substring(index+1);
	}

}
