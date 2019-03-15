package data.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import data.model.UrlModel;

public class UrlFileFactory implements UrlFactoryInterface {

	public static final Logger LOG = LogManager.getLogger(UrlFileFactory.class);
	
	String path;
	
	public UrlFileFactory(String path) {
		this.path = path;
	}

	public void saveUrl(UrlModel url) {
		try
		{
			File file = new File(path);
			if(!file.exists())
			{
				file.createNewFile();
			}
			
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(url.toString());
			bw.newLine();
			
			bw.close();
			fw.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public ArrayList<UrlModel> readData() {
		ArrayList<UrlModel> arr = new ArrayList<UrlModel>();
		try
		{
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader bfr = new BufferedReader(isr);
			
			String line = bfr.readLine();
			while( line != null)
			{
				UrlModel url = new UrlModel();
				url.setUrl(line);
				arr.add(url);
				line = bfr.readLine();
			}
			bfr.close();
			isr.close();
			fis.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		return arr;
	}
	
	public boolean writeOutPutToFile(ArrayList<UrlModel> arr, String path)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(path);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			
			for(UrlModel url : arr)
			{
				bw.write(url.toString());
				bw.newLine();
			}
			
			bw.close();
			osw.close();
			fos.close();	
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return false;
	}

}
