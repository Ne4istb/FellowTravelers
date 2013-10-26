package gdgdevfest.walker.ui.fragments;

import gdgdevfest.walker.R;
import gdgdevfest.walker.ui.ActivityAuto;
import gdgdevfest.walker.ui.ActivityMan;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentChoice extends Fragment implements OnClickListener {

	private View mViewContent;

	private Button mBtnAuto;
	private Button mBtnMan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mViewContent = inflater.inflate(R.layout.fragment_chooise, null, false);

		mBtnAuto = (Button) mViewContent.findViewById(R.id.chooise_btn_auto);
		mBtnAuto.setOnClickListener(this);

		mBtnMan = (Button) mViewContent.findViewById(R.id.chooise_btn_man);
		mBtnMan.setOnClickListener(this);

		return mViewContent;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.chooise_btn_auto:

			Intent intentAuto = new Intent(getActivity(), ActivityAuto.class);
			startActivity(intentAuto);
			break;

		case R.id.chooise_btn_man:

			Intent intentMan = new Intent(getActivity(), ActivityMan.class);
			startActivity(intentMan);
			break;
		}
	}
}
