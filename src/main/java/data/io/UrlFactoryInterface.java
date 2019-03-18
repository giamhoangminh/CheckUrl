package data.io;

import java.util.ArrayList;

import data.model.UrlModel;

interface UrlFactoryInterface {
	public abstract void saveUrl(UrlModel u) throws InterruptedException ;
		
	public abstract ArrayList<UrlModel> readData() throws InterruptedException;
}
