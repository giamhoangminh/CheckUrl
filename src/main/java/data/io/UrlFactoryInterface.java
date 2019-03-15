package data.io;

import java.util.ArrayList;

import data.model.UrlModel;

interface UrlFactoryInterface {
	public abstract void saveUrl(UrlModel u) ;
		
	public abstract ArrayList<UrlModel> readData();
}
