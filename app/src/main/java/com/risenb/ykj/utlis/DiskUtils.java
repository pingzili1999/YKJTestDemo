package com.risenb.ykj.utlis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
*/
public class DiskUtils {

	private static Context context;
	public static void getContext(Context mApplicationContent){
		context=mApplicationContent;
	}
	private static String FILENAME="ymdbData";
	public static final String CROPIMAGEPATH = "cropImage";


	public static String getPhotoFileUrl(String photoFileName){
		String subForder = getCachePath()+File.separator + CROPIMAGEPATH+File.separator+"picSave"+File.separator+photoFileName.concat(".jpg");
		File file = new File(subForder);
		if(file.exists()){
			return subForder;
		}else {
			return "no";
		}
	}
	public static File getPhotoFile(String photoFileName){

		String subForder = getCachePath()+File.separator + CROPIMAGEPATH+File.separator+"picSave"+File.separator+photoFileName.concat(".jpg");
		File file = new File(subForder);
		if(file.exists()){
			return file;
		}else {
			return null;
		}
	}

	public static File generatePhotoFile(){
		File file = getDiskCacheDir();
		String cameraImgSavePath = file.getPath() + File.separator
				+ getPhotoFileName();
		return new File(cameraImgSavePath);
	}


	public static File getDiskCacheDir() {
		File file=null;
		file = new File(getCachePath() + File.separator + CROPIMAGEPATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}
	/**
	 * 获得缓存目录
	 * @return
	 */
	public static String getCachePath(){
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		return cachePath;
	}

	/**
	 * 用当前时间给图片命名
	 * @return
     */
	private static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	/**
	 * 保存图片
	 * @param bm
	 * @throws IOException
	 */

	public static void saveFile(Bitmap bm,String photoFileName) {
		String subForder = getCachePath()+File.separator + CROPIMAGEPATH+File.separator+"picSave";
		File foder = new File(subForder);
		if (!foder.exists()) {
			foder.mkdirs();
		}
		File myCaptureFile;
		if(photoFileName==null){
			myCaptureFile = new File(subForder, getPhotoFileName());
		}else{
			myCaptureFile = new File(subForder, photoFileName.concat(".jpg") );
		}
		if (!myCaptureFile.exists()) {
			try {
				myCaptureFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
		try {
			bos.flush();
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if(photoFileName==null||photoFileName.equals("myQrCode")){
			CommonUtil.showToast("保存成功");
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(foder);
			intent.setData(uri);
			context.sendBroadcast(intent);//这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
		}
	}


	/**
	 * 获得缓存文件大小
	 * @return
	 * @throws Exception
	 */
	public static String getTotalCacheSize() throws Exception {
		long cacheSize = getFolderSize(getDiskCacheDir());
		return getFormatSize(cacheSize);
	}

	/**
	 * 清除缓存文件
	 */
	public static void clearAllCache() {
		File file =  new File(getCachePath()); //+File.separator+CROPIMAGEPATH);
		if(file.exists()){
			if(delAllFile(getCachePath())){
				CommonUtil.showToast("清除缓存成功");
			}
		}

	}

	/**
	 * 删除目录下的所有文件和文件夹
	 * @param path
	 * @return
     */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
	/**
	 *  删除文件夹
	 *  param folderPath 文件夹完整绝对路径
	 */

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 缓存文件大小计算
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++) {
				// 如果下面还有文件
				if (fileList[i].isDirectory()) {
					size = size + getFolderSize(fileList[i]);
				} else {
					size = size + fileList[i].length();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return size;
	}

	/**
	 * 格式化单位
	 * @param size
	 */
	public static String getFormatSize(double size) {
		double kiloByte = size / 1024;
		if (kiloByte < 1) {
			return "0M";
		}

		double megaByte = kiloByte / 1024;
		if (megaByte < 1) {
			return  "0.1M";
		}

		double gigaByte = megaByte / 1024;
		if (gigaByte < 1) {
			BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
			return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "M";
		}

		double teraBytes = gigaByte / 1024;
		if (teraBytes < 1) {
			BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
			return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
					.toPlainString() + "GB";
		}
		BigDecimal result4 = new BigDecimal(teraBytes);
		return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
				+ "TB";
	}

	/**
	 * 删除userInfo 对象
	 */
	public static void deleteUserInfo(){
		SharedPreferences sharedPreferences = context.getSharedPreferences
				("userInfoData", Context.MODE_PRIVATE);
		sharedPreferences.edit().clear().commit();
	}
	/**
	 * desc:保存对象
	 * @param key
	 * @param obj 要保存的对象，只能保存实现了serializable的对象
	 * modified:
	 */
	public static void saveObject(String key ,Object obj){
		try {
			// 保存对象
			SharedPreferences.Editor sharedata;
			if(key.equals("userInfo")){
				sharedata = context.getSharedPreferences("userInfoData", Context.MODE_PRIVATE).edit();
			}else{
				// 保存对象
				sharedata = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE).edit();
			}
			//先将序列化结果写到byte缓存中，其实就分配一个内存空间
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			ObjectOutputStream os=new ObjectOutputStream(bos);
			//将对象序列化写入byte缓存
			os.writeObject(obj);
			//将序列化的数据转为16进制保存
			String bytesToHexString = bytesToHexString(bos.toByteArray());
			//保存该16进制数组
			sharedata.putString(key, bytesToHexString);
			sharedata.commit();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("", "保存obj失败");
		}
	}
	/**
	 * desc:将数组转为16进制
	 * @param bArray
	 * @return
	 * modified:
	 */
	public static String bytesToHexString(byte[] bArray) {
		if(bArray == null){
			return null;
		}
		if(bArray.length == 0){
			return "";
		}
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
	/**
	 * desc:获取保存的Object对象到 sp中
	 * @param key
	 * @return
	 * modified:
	 */
	public static Object readObject(String key ){
		try {
			SharedPreferences sharedata;
			if(key.equals("userInfo")){
				sharedata = context.getSharedPreferences("userInfoData", Context.MODE_PRIVATE);
			}else{
				sharedata = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
			}

			if (sharedata.contains(key)) {
				String string = sharedata.getString(key, "");
				if(TextUtils.isEmpty(string)){
					return null;
				}else{
					//将16进制的数据转为数组，准备反序列化
					byte[] stringToBytes = StringToBytes(string);
					ByteArrayInputStream bis=new ByteArrayInputStream(stringToBytes);
					ObjectInputStream is=new ObjectInputStream(bis);
					//返回反序列化得到的对象
					Object readObject = is.readObject();
					return readObject;
				}
			}
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//所有异常返回null
		return null;

	}
	/**
	 * desc:将16进制的数据转为数组
	 */
	public static byte[] StringToBytes(String data){
		String hexString=data.toUpperCase().trim();
		if (hexString.length()%2!=0) {
			return null;
		}
		byte[] retData=new byte[hexString.length()/2];
		for(int i=0;i<hexString.length();i++)
		{
			int int_ch;  // 两位16进制数转化后的10进制数
			char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
			int int_ch1;
			if(hex_char1 >= '0' && hex_char1 <='9')
				int_ch1 = (hex_char1-48)*16;   //// 0 的Ascll - 48
			else if(hex_char1 >= 'A' && hex_char1 <='F')
				int_ch1 = (hex_char1-55)*16; //// A 的Ascll - 65
			else
				return null;
			i++;
			char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
			int int_ch2;
			if(hex_char2 >= '0' && hex_char2 <='9')
				int_ch2 = (hex_char2-48); //// 0 的Ascll - 48
			else if(hex_char2 >= 'A' && hex_char2 <='F')
				int_ch2 = hex_char2-55; //// A 的Ascll - 65
			else
				return null;
			int_ch = int_ch1+int_ch2;
			retData[i/2]=(byte) int_ch;//将转化后的数放入Byte里
		}
		return retData;
	}
	/**
	 * 删除保存Object的文件
	 */
	public static void deleteObject() {
		SharedPreferences sharedPreferences = context.getSharedPreferences
				(FILENAME, Context.MODE_PRIVATE);
		sharedPreferences.edit().clear().commit();
	}
	/**
	 * 删除保存Object的文件的某个key对应的Value
	 */
	public static void removeKey(String key) {
		SharedPreferences sharedPreferences = context.getSharedPreferences
				(FILENAME, Context.MODE_PRIVATE);
		sharedPreferences.edit().remove(key).commit();
	}
	/**
	 * 读取assets 文件下的文件数据
	 * @param fileName
	 * @return
     */
	public static String readAsset(String fileName) {
		AssetManager am = context.getAssets();
		Log.e("assets","读取开始"+System.currentTimeMillis());
		InputStream is = null;
		try {
			// 根据上文的 fileName文件名，拼成一个路径，用AssetManager打开一个输入流，读写数据。
			is = am.open(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		String data = readDataFromInputStream(is);
		Log.e("assets","读取开始"+System.currentTimeMillis());
		try {
			is.close();
			Log.e("assets","is.close"+System.currentTimeMillis());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	public static String readDataFromInputStream(InputStream is) {
		BufferedInputStream bis = new BufferedInputStream(is);

		String str = "", s = "";

		int c = 0;
		byte[] buf = new byte[64];
		while (true) {
			try {
				c = bis.read(buf);
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (c == -1)
				break;
			else {
				try {
					s = new String(buf, 0, c, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				str += s;
			}
		}
		try {
			bis.close();
			Log.e("assets","bis.close"+System.currentTimeMillis());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return str;
	}

}
