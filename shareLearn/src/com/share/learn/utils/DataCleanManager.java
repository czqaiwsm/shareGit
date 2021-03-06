package com.share.learn.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

/**
 * 本应用数据清除管理器
 *
 * @Description 主要功能有清除内/外缓存，清除数据库，清除自定义目录
 * @author ccs7727@163.com
 * @date 2015年10月17日 下午4:55:38
 *
 */
public class DataCleanManager {

	/**
	 * 清除缓存调用此方法即可
	 *
	 * @param context
	 * @param filepath
	 */
	public static void cleanApplicationData(List<String> filepath) {
		if (filepath == null) {
			return;
		}
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/**
	 * 清除自定义目录，使用需小心，请不要误删。而且只支持目录下的文件删除
	 *
	 * @param filePath
	 */
	public static void cleanCustomCache(String filePath) {
		// deleteFilesByDirectory(new File(filePath));
		deleteFolderFile(filePath, true);
	}

	/**
	 * 删除文件，如果是目录则遍历目录，逐一删除
	 *
	 * @param directory
	 */
	private static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				if (item.isDirectory()) {
					for (File item2 : item.listFiles()) {
						item2.delete();
					}
				} else {
					item.delete();
				}
			}
		}
	}

	/**
	 * 获取文件大小
	 *
	 * @param file
	 * @return long类型
	 * @throws Exception
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			if (null != file && file.exists()) {
				File[] fileList = file.listFiles();
				for (int i = 0; i < fileList.length; i++) {
					// 如果下面还有文件
					if (fileList[i].isDirectory()) {
						size = size + getFolderSize(fileList[i]);
					} else {
						size = size + fileList[i].length();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 删除指定目录下文件及目录
	 *
	 * @param deleteThisPath
	 * @param filepath
	 * @return
	 */
	public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
		if (!TextUtils.isEmpty(filePath)) {
			try {
				File file = new File(filePath);
				if (file.isDirectory()) {// 如果下面还有文件
					File files[] = file.listFiles();
					for (int i = 0; i < files.length; i++) {
						deleteFolderFile(files[i].getAbsolutePath(), true);
					}
				}
				if (deleteThisPath) {
					if (!file.isDirectory()) {// 如果是文件，删除
						file.delete();
					} else {// 目录
						if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
							file.delete();
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 格式化单位
	 *
	 * @param size
	 * @return
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return size + "Byte";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
			return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
	}

	public static String getCacheSize(File file) throws Exception {
		return getFormatSize(getFolderSize(file));
	}

	/**
	 * * 按名字清除本应用数据库 * *
	 * 
	 * @param context
	 * @param dbName
	 */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

}