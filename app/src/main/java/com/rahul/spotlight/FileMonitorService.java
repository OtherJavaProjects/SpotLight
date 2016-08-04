package com.rahul.spotlight;

import java.io.File;
import java.util.Vector;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;

public class FileMonitorService extends Service {

	private DBHandler db = null;
	private Vector<Files> filesVector = null;
	private Vector<FileObserver> dirsVector = null;
	private BroadcastReceiver receiver = null;
	private IntentFilter filter = null;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("WERTY", "Service called");
		filesVector = new Vector<Files>();
		dirsVector = new Vector<FileObserver>();
		db = new DBHandler(getApplicationContext());
		new Thread(new Runnable() {

			public void run() {
				updateFiles();
				startObserving();
				db.insertFile(filesVector);
				Intent intent1 = new Intent();
				intent1.setAction("EXIT_SPLASH");
				sendBroadcast(intent1);
			}
		}).start();

		filter = new IntentFilter();
		filter.addAction("newfile");

		receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				int flag = 0;
				File file = new File(intent.getExtras().getString("path"));
				int event = intent.getExtras().getInt("event");
				event &= FileObserver.ALL_EVENTS;
				if (file.isDirectory()) {
					if (event == 256) {
						FileObserver f = getFileObserver(intent.getExtras()
								.getString("path"));
						dirsVector.add(f);
						f.startWatching();
					}
				} else {
					if (event == 256) {
						flag = 1;
						Vector<Files> v = new Vector<Files>();
						v.add(new Files(file.getName(), file.getAbsolutePath(),
								file.getName().substring(
										file.getName().lastIndexOf(".") + 1)));
						db.insertFile(v);
					} else if (event == 512) {
						flag = 2;
						db.deleteEvent(file.getAbsolutePath());
					}
				}
				System.out.println("File modified: "
						+ intent.getExtras().getInt("event") + "   "
						+ intent.getExtras().getString("path"));
			}
		};
		registerReceiver(receiver, filter);

		return Service.START_STICKY;
	}

	private void startObserving() {
		for (FileObserver fileObsrv : dirsVector) {
			fileObsrv.startWatching();
		}
	}

	private void updateFiles() {
		File f = new File("/mnt/sdcard");
		insertFiles(f);
	}

	private FileObserver getFileObserver(final String absolutePath) {
		return new FileObserver(absolutePath) {

			@Override
			public void onEvent(int event, String path) {
				Intent intent = new Intent();
				intent.setAction("newfile");
				intent.putExtra("event", event);
				intent.putExtra("path", absolutePath + "/" + path);
				sendBroadcast(intent);
			}
		};
	}

	private void insertFiles(final File f) {
		if (f.toString().startsWith("/mnt/sdcard/.")
				|| f.toString().contains("/mnt/sdcard/Android")
				|| f.toString().contains("/mnt/sdcard/android"))
			return;
		File files[] = f.listFiles();
		if (files != null && files.length > 0)
			for (File file : files) {
				if (file.isDirectory()) {
					FileObserver obsrv = getFileObserver(file.getAbsolutePath());
					dirsVector.add(obsrv);
					insertFiles(file.getAbsoluteFile());
				} else {
					String tempFile = file.getName();
					int index = file.getName().lastIndexOf(".");
					if (index > 0) {
						String ext = file.getName().substring(index + 1);
						filesVector.add(new Files(tempFile, file
								.getAbsolutePath(), ext));
					}
				}
			}
	}

	@Override
	public void onDestroy() {
		return;
	}

}
