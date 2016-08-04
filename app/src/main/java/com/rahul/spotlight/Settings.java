package com.rahul.spotlight;

import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends Activity {
	private CheckBox apps = null;
	private CheckBox songs = null;
	private CheckBox videos = null;
	private CheckBox images = null;
	private CheckBox contacts = null;
	private CheckBox docs = null;
	private CheckBox pdf = null;
	private CheckBox xls = null;
	private CheckBox ppt = null;
	private CheckBox txt = null;
	private CheckBox browser = null;
	private EditText otherChoices = null;
	private Button ok = null;
	private Button cancel = null;
	private boolean updated = false;

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.settings);

		final Vector<Boolean> indexes = (Vector<Boolean>) SpotlightActivity.indexVector
				.clone();
		final Vector<String> others = (Vector<String>) SpotlightActivity.otherIndexes
				.clone();
		apps = (CheckBox) findViewById(R.id.appscb);
		songs = (CheckBox) findViewById(R.id.songscb);
		videos = (CheckBox) findViewById(R.id.videoscb);
		images = (CheckBox) findViewById(R.id.imagescb);
		contacts = (CheckBox) findViewById(R.id.contactscb);
		docs = (CheckBox) findViewById(R.id.documentscb);
		pdf = (CheckBox) findViewById(R.id.pdfcb);
		xls = (CheckBox) findViewById(R.id.excelscb);
		ppt = (CheckBox) findViewById(R.id.pptcb);
		txt = (CheckBox) findViewById(R.id.txtcb);
		browser = (CheckBox) findViewById(R.id.browsercb);
		otherChoices = (EditText) findViewById(R.id.otherchoices);
		ok = (Button) findViewById(R.id.settingsok);
		cancel = (Button) findViewById(R.id.settingscancel);

		apps.setChecked(indexes.get(0));
		songs.setChecked(indexes.get(1));
		videos.setChecked(indexes.get(2));
		images.setChecked(indexes.get(3));
		contacts.setChecked(indexes.get(4));
		docs.setChecked(indexes.get(5));
		pdf.setChecked(indexes.get(6));
		xls.setChecked(indexes.get(7));
		ppt.setChecked(indexes.get(8));
		txt.setChecked(indexes.get(9));
		browser.setChecked(indexes.get(10));

		String o = "";
		if (others.size() > 0) {
			for (String other : others)
				o += other + ",";
			o = o.substring(0, o.length() - 1);
		}
		otherChoices.setText(o);

		apps.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(0, true);
				else
					SpotlightActivity.indexVector.set(0, false);
			}
		});

		songs.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(1, true);
				else
					SpotlightActivity.indexVector.set(1, false);
			}
		});

		videos.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(2, true);
				else
					SpotlightActivity.indexVector.set(2, false);
			}
		});

		images.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(3, true);
				else
					SpotlightActivity.indexVector.set(3, false);
			}
		});

		contacts.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(4, true);
				else
					SpotlightActivity.indexVector.set(4, false);
			}
		});

		docs.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(5, true);
				else
					SpotlightActivity.indexVector.set(5, false);
			}
		});

		pdf.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(6, true);
				else
					SpotlightActivity.indexVector.set(6, false);
			}
		});

		xls.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(7, true);
				else
					SpotlightActivity.indexVector.set(7, false);
			}
		});

		ppt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(8, true);
				else
					SpotlightActivity.indexVector.set(8, false);
			}
		});

		txt.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(9, true);
				else
					SpotlightActivity.indexVector.set(9, false);
			}
		});

		browser.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked)
					SpotlightActivity.indexVector.set(10, true);
				else
					SpotlightActivity.indexVector.set(10, false);
			}
		});

		ok.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String[] s = otherChoices.getText().toString().split(",");
				SpotlightActivity.otherIndexes.clear();
				for (String arg : s)
					SpotlightActivity.otherIndexes.add(arg);
				updated = true;
				finish();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				SpotlightActivity.indexVector = (Vector<Boolean>) indexes
						.clone();
				SpotlightActivity.otherIndexes = (Vector<String>) others
						.clone();
				updated = false;
				finish();
			}
		});

	}

	@Override
	public void finish() {
		Intent intent = new Intent();
		intent.putExtra("update", updated);
		setResult(RESULT_OK, intent);
		super.finish();
		overridePendingTransition(R.anim.rotationin, R.anim.rotationin1);
	}
}