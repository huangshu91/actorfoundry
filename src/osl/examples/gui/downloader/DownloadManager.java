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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.HeadMethod;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.RemoteCodeException;
import osl.manager.annotations.message;

public class DownloadManager extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8590880454948040393L;

	private int numOfWorkers = 5;

	private String[] partFiles = null;

	private Integer chunkReceived = 0;

	private String localFile;

	private ActorName app;

	public DownloadManager(ActorName app) {
		this.app = app;
	}

	/**
	 * 
	 * @param urlString
	 * @return "OK" on success or and error string upon error.
	 */
	private String checkValidUrl(String urlString) {
		URL url;
		try {
			url = new URL(urlString);
			URLConnection connection = url.openConnection();
			if (connection instanceof HttpURLConnection) {
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.connect();
				int response = httpConnection.getResponseCode();
				if (response == 200) {
					return "OK";
				} else {
					return "Invalid URL. HTTP response: " + response + ".";
				}
			} else {
				return "Invalid connection.";

			}
		} catch (MalformedURLException e) {
			return "Malformed URL.";
		} catch (IOException e) {
			return "Unable to connect.";
		}
	}

	@message
	public void download(String url, String localFile, Integer numOfParts) {
		String checkUrlResult = checkValidUrl(url);
		if (checkUrlResult.equals("OK")) {
			this.numOfWorkers = numOfParts;
			this.partFiles = new String[this.numOfWorkers];
			this.completePerWorker = new Integer[this.numOfWorkers];
			chunkReceived = 0;
			this.localFile = localFile;
			File tempDir = new File(System.getProperty("java.io.tmpdir"));
			HttpClient client = new HttpClient();
			HeadMethod request = new HeadMethod(url);
			try {
				if (client.executeMethod(request) != HttpStatus.SC_OK) {
					System.out.println("NOT OK!!");
				} else {
					Header contentLengthHeader = request
							.getResponseHeader("content-length");
					if (contentLengthHeader == null) {
						send(app, "error",
								"Invalid Content Length. Unable to determine the file size.");
						return;
					}
					long remoteFileSize = Long.parseLong(contentLengthHeader
							.getValue());

					System.out.println("Remote file size is " + remoteFileSize
							+ " bytes.");

					int chunkSize = (int) (remoteFileSize / numOfWorkers);
					for (int i = 0; i < numOfWorkers - 1; i++) {
						ActorName a = create(DLWorker.class);
						send(a, "downloadChunk", url, i * chunkSize, chunkSize,
								remoteFileSize, i, tempDir, self());
					}
					ActorName a = create(DLWorker.class);
					send(a, "downloadChunk", url, (int) (numOfWorkers - 1)
							* chunkSize, (int) remoteFileSize
							- (numOfWorkers - 1) * chunkSize, remoteFileSize,
							(numOfWorkers - 1), tempDir, self());
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (RemoteCodeException e) {
				e.printStackTrace();
			}
		} else {
			send(app, "error", checkUrlResult);
		}
	}

	@message
	public void done(Integer chunkNum, String partFile) {
		partFiles[chunkNum] = partFile;
		chunkReceived++;
		if (chunkReceived == numOfWorkers) {
			try {
				send(app, "doneDL", localFile);
				FileOutputStream fos = new FileOutputStream(localFile);
				byte[] b = new byte[8192];
				for (int i = 0; i < numOfWorkers; i++) {
					File f = new File(partFiles[i]);
					BufferedInputStream in = new BufferedInputStream(
							new FileInputStream(f));
					int readSize = in.read(b);
					while (readSize != -1) {
						fos.write(b, 0, readSize);
						readSize = in.read(b);
					}
					in.close();
					f.delete();
				}
				fos.close();
				send(app, "done", localFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private Integer[] completePerWorker;

	@message
	public void percentComplete(Integer p, Integer chunkNum) {
		double totalPercentComplete = 0.0;
		completePerWorker[chunkNum] = new Integer(p.intValue());
		for (int i = 0; i < completePerWorker.length; i++) {
			totalPercentComplete += completePerWorker[i];
		}
		totalPercentComplete = totalPercentComplete / (numOfWorkers + 0.0);
		send(app, "percentComplete", new Integer((int) totalPercentComplete));
	}
}
