package gdgdevfest.walker.ui.fragments;

import gdgdevfest.walker.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentOrderStatus extends Fragment {

	private View mViewContent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mViewContent = inflater.inflate(R.layout.fragment_has_order, null,
				false);

		return mViewContent;
	}
}
