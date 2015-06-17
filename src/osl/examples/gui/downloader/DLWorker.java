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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import osl.manager.Actor;
import osl.manager.ActorName;
import osl.manager.annotations.message;

public class DLWorker extends Actor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8070390419887885497L;

	@message
	public void downloadChunk(String url, Integer offset, Integer length,
			Long total, Integer chunkNumber, File tempDir, ActorName manager) {
		System.out.println("offset: " + offset + ", length: " + length);
		HttpClient client = new HttpClient();
		GetMethod request = new GetMethod(url);
		request.addRequestHeader("Range", "bytes=" + offset + "-"
				+ (offset + length - 1)
		// + "/" + total
				);
		try {
			if (client.executeMethod(request) != HttpStatus.SC_PARTIAL_CONTENT) {
				System.out.println("Error in download");
				return;
			}

			BufferedInputStream in = new BufferedInputStream(request
					.getResponseBodyAsStream());
			File tempFile = new File(tempDir, "_" + offset + "_"
					+ (new Random().nextInt(10000) + 1000) + ".part");
			FileOutputStream fos = new FileOutputStream(tempFile);

			// /*
			byte[] b = new byte[8192];
			int readSize = in.read(b);
			int totalW = 0;
			while (readSize != -1) {
				totalW += readSize;
				// System.out.println(chunkNumber+" <> "+readSize);
				fos.write(b, 0, readSize);
				readSize = in.read(b);
				// System.out.println(new Float(totalW+0.0)/(length+0.0));
				// if (((totalW+0.0)/(length+0.0)*100) % 8 < 3) {
				// System.out.println(new
				// Integer((int)((100.0*totalW)/(length+0.0))).toString());
				send(manager, "percentComplete", new Integer(
						(int) ((100.0 * totalW) / (length + 0.0))), chunkNumber);
				// }
			}
			// */
			/*
			 * int readSize = in.read(); int totalW = 0; while (readSize != -1)
			 * { fos.write(readSize); readSize = in.read(); totalW ++; }
			 */
			// System.out.println(chunkNumber + ", totalW: " + totalW);
			fos.close();
			in.close();
			send(manager, "done", chunkNumber, tempFile.toString());
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
