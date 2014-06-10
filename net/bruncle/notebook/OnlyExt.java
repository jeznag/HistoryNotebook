package net.bruncle.notebook;

import java.io.File;
public class OnlyExt extends javax.swing.filechooser.FileFilter
{
	private String ext;
	private String description;
	
	public OnlyExt(String ext, String description){ 
		this.ext = "." + ext; 
		this.description = description;
	}
	public boolean accept(File dir){ 
		if (dir.isDirectory())
			return true;
		String extension = "";
		try{
			extension = dir.toString().substring(dir.toString().lastIndexOf("."));
		}
		catch (StringIndexOutOfBoundsException e){ 
			return false; 
		}
		return extension.endsWith(ext); 
	}
	public String getDescription() {
	        return description;
	}
}