package com.bramyeh.buttonsaroundcircle.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bramyeh.buttonsaroundcircle.R;

public class DemoFragment extends Fragment {

	private CustumizedButton mCustumizedButton;
	private View mView;

	public DemoFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		mCustumizedButton = (CustumizedButton) rootView
				.findViewById(R.id.custumizedButton);
		mCustumizedButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int color = mCustumizedButton.getCurrentColor();
				if (mView != null && color != Color.TRANSPARENT) {
					mView.setBackgroundColor(color);
				}
			}
		});

		mView = (View) rootView.findViewById(R.id.view);
		mView.setBackgroundColor(Color.BLACK);

		return rootView;
	}

}
