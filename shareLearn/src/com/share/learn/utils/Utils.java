package com.share.learn.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	public static final String DATE = "yyyy-MM-dd";

	public static final String DATE2 = "yyyy-MM-dd HH:mm:ss";

	public static String dateToString(Date date, String dateStyle) {

		return new SimpleDateFormat(dateStyle).format(date);

	}

	public static Date stringToDate(String dateStyle) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		return sdf.parse(dateStyle);

	}

	/**
	 * 上月最后一天
	 *
	 * @param my_month
	 *
	 * @return
	 */
	public static String getMonthLastMonth(Calendar my_month) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		// my_month.add(Calendar.DATE, 1);// 设为当前月的1号
		my_month.add(Calendar.MONTH, -1);// 月份减1
		// String ss1 = sdf.format(my_month.getTime());

		// my_month.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		// my_month.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		String ss2 = sdf.format(my_month.getTime());

		// return ss1 + "," + ss2;
		return ss2;
	}

	/**
	 * 下月最后一天
	 *
	 * @return
	 */
	public static String getMonthNextMonth(Calendar my_month) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		// my_month.set(Calendar.DATE, 1);// 设为当前月的1号
		// my_month.add(Calendar.MONTH, 1);// 月份加1
		// String ss1 = sdf.format(my_month.getTime());

		my_month.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		// my_month.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		String ss2 = sdf.format(my_month.getTime());

		// return ss1 + "," + ss2;
		return ss2;
	}

	public static String getTotalPrice(int count, double price) {
		return getPriceValue(count * price);
	}

	/**
	 * 商品价格保留两位小数
	 *
	 * @param price
	 * @return *.00
	 */
	public static String getPriceValue(double price) {
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		return decimalFormat.format(price);// format 返回的是字符串
	}

	public static void reload(Activity act) {
		Intent intent = act.getIntent();
		act.overridePendingTransition(0, 0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		act.finish();
		act.overridePendingTransition(0, 0);
		act.startActivity(intent);
	}

	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 50) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	/*
	 * * 清除指定应用缓存,即删除data/data/packageName/cache目录下文件
	 *
	 * @param mContext
	 *
	 * @param packageName
	 */

	public static void clearCache(final Context mContext, final String packageName) {

		(new Thread() {
			@Override
			public void run() {
				try {
					Context otherAppContext = mContext.createPackageContext(packageName, Context.CONTEXT_IGNORE_SECURITY);
					File path = otherAppContext.getCacheDir();
					if (path == null)
						return;
					// // #rm -r xxx 删除名字为xxx的文件夹及其里面的所有文件
					// String killer = " rm -r " + path.toString();
					// Process p = Runtime.getRuntime().exec("su");
					// DataOutputStream os = new
					// DataOutputStream(p.getOutputStream());
					// os.writeBytes(killer.toString() + "\n");
					// os.writeBytes("exit\n");
					// os.flush();
					DataCleanManager.cleanCustomCache(path.getAbsolutePath());
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();

	}

	/**
	 * 说明：压缩本地图片路径
	 * 
	 * @param srcPath
	 */
	public static void compress(String srcPath) {
		float hh = URLConstants.SCREENH;
		float ww =  URLConstants.SCREENW;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath, opts);
		opts.inJustDecodeBounds = false;
		int w = opts.outWidth;
		int h = opts.outHeight;
		int size = 0;
		if (w <= ww && h <= hh) {
			size = 1;
		} else {
			double scale = w >= h ? w / ww : h / hh;
			double log = Math.log(scale) / Math.log(2);
			double logCeil = Math.ceil(log);
			size = (int) Math.pow(2, logCeil);
		}
		opts.inSampleSize = size;
		bitmap = BitmapFactory.decodeFile(srcPath, opts);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int quality = 100;
		bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		while (baos.toByteArray().length > 50 * 1024) {
			baos.reset();
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			quality -= 20;
		}
		try {
			baos.writeTo(new FileOutputStream(srcPath));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				baos.flush();
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
