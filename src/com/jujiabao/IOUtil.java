package com.jujiabao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.jujiabao.bo.LogData;

/**
 * 该类提供若干读写方法为客户端使用
 * @author Hello.Ju
 *
 */
public class IOUtil {
	/**
	 * 从指定的文件中读取每一行字符串并存入一个集合中返回
	 * 该字符串应当是一条配对日志
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static List<String> readLogRec(File file) throws Exception{
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			List<String> list = new ArrayList<String>();
			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}finally{
			if (br != null) {
				br.close();
			}
		}
	}
	/**
	 * 从给定的文件中读取每一行日志，并转换为若干的LogData实例，然后把他们存入list集合后返回
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static List<LogData> readLogData(File file) throws Exception{
		BufferedReader bf = null;
		
		try {
			bf = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
			List<LogData> list = new ArrayList<LogData>();
			
			String line = null;
			while ((line = bf.readLine()) != null) {
				LogData logData = new LogData(line);
				list.add(logData);
			}
			return list;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}finally{
			if (bf != null) {
				bf.close();
			}
		}
	}
	
	/**
	 * 从给定的文件中读取一行字符串然后将其转换为一个long值返回
	 * 需要注意，该文件的第一行字符串的内容必须为一个整数。
	 * @param file
	 * @return
	 */
	public static long readLong(File file) throws Exception {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			return Long.parseLong(br.readLine().trim());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}finally{
			if (br != null) {
				br.close();
			}
		}
	}
	/**
	 * 从给定的RandomAccessFile的当前位置开始连续读取给定长度的字节，并转为字符串后返回
	 * @param raf
	 * @param length
	 * @return
	 */
	public static String readString(RandomAccessFile raf,int length) throws Exception {
		try {
			byte[] data = new byte[length];
			raf.read(data);
			return new String(data,"ISO8859-1");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 将给定的集合中的每个元素的toString返回的字符串以行为单位写入给定的文件中
	 * @param c
	 * @param file
	 * @throws Exception
	 */
	public static void saveCollection(Collection c,File file) throws Exception {
		PrintWriter pw = null;
		try {
			FileOutputStream fos = new FileOutputStream(file);//不要追加写进去
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			pw = new PrintWriter(osw);
			for (Object object : c) {
				pw.println(object);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}finally{
			if (pw != null) {
				pw.close();
			}
		}
		
	}
	/**
	 * 将给定的long型以字符串的形式写入文件中
	 * @param l
	 * @param file
	 * @throws Exception
	 */
	public static void saveLong(long l, File file) throws Exception {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(file);
			pw.print(l);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}finally{
			if (pw != null) {
				pw.close();
			}
		}
	}
}
