package com.google.android.apps.uvg.views;

import com.google.android.maps.mytracks.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class UVIHourlyFragment extends PreferenceFragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		return inflater.inflate(R.layout.uvg_fragment_uvi_hourly, container, false);
	}
}
