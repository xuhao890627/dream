//package com.st.dream.utils;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.commons.codec.binary.Base64;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.MultiFormatWriter;
//import com.google.zxing.common.BitMatrix;
//
//public class QRCodeUtil {
//
//	public static byte[] build(int width, int height, String content) throws Exception {
//
//		String format = "jpg";
//		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//		byte[] imgStream = MatrixToImageWriter.writeToStream(bitMatrix, format);
//		return new Base64().encode(imgStream);
//	}
//
//	public static void buildfile(int width, int height, String content) throws Exception {
//
//		String format = "jpg";
//		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
//		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
//		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
//		File file = new File("D:\\jpg\\gldj_download.jpg");
//		if (!file.getParentFile().exists()) {
//			file.getParentFile().mkdirs();
//		}
//
//		if (!file.exists()) {
//			file.createNewFile();
//		}
//
//		MatrixToImageWriter.writeToFile(bitMatrix, format, file);
//	}
//
//	public static void main(String[] args) {
//		try {
//			buildfile(1024, 1024,
//					"http://gldj.chenkuo.com.cn/helpCenter/html/downloadApp.html");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
