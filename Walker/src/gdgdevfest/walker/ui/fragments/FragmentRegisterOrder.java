package gdgdevfest.walker.ui.fragments;

import gdgdevfest.walker.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentRegisterOrder extends Fragment {

	private View mViewContent = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mViewContent = inflater.inflate(R.layout.fragment_register_order, null,
				false);

		return mViewContent;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}
}
