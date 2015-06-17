/**
 * Developed by: The Open Systems Lab
 *             University of Illinois at Urbana-Champaign
 *             Department of Computer Science
 *             Urbana, IL 61801
 *             http://osl.cs.uiuc.edu
 *
 * Contact: http://osl.cs.uiuc.edu/af
 *
 * Copyright (c) 1998-2009
 * The University of Illinois Board of Trustees.
 *    All Rights Reserved.
 * 
 * Distributed under license: http://osl.cs.uiuc.edu/af/LICENSE
 * 
 */
package osl.examples.gui.downloader;

import java.io.File;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class DownloaderApp extends Actor implements IDownloader {

	/**
	 * 
	 */
	private static final long serialVersionUID = -352884640470121634L;

	private ActorName downloadManager;
	private DownloaderFrame downloader;

	@message
	public void init() throws RemoteCodeException {
		downloader = new DownloaderFrame(this);
		downloader.setVisible(true);
		downloadManager = create(DownloadManager.class, self());
	}

	@message
	public void done(String savedIn) {
		downloader.jLabel2
				.setText("Finished downloading. Saved in: " + savedIn);
		downloader.jButton1.setEnabled(true);
	}

	@message
	public void doneDL(String savedIn) {
		downloader.jLabel2.setText("Writing file in: " + savedIn);
	}

	@message
	public void percentComplete(Integer n) {
		downloader.jProgressBar1.setValue(n);
		downloader.jLabel2.setText(n + " %");
	}

	@message
	public void error(String error) {
		downloader.jLabel2.setText(error);
		downloader.jButton1.setEnabled(true);
	}

	@Override
	public void download(String url, File fileToSave) {
		downloader.jProgressBar1.setMinimum(0);
		downloader.jProgressBar1.setMaximum(100);
		downloader.jProgressBar1.setValue(0);
		send(downloadManager, "download", url, fileToSave.toString(), 4);
	}
}
