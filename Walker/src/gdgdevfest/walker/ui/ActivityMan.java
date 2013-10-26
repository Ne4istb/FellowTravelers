package gdgdevfest.walker.ui;

import gdgdevfest.walker.R;
import gdgdevfest.walker.ui.fragments.FragmentOrderStatus;
import gdgdevfest.walker.ui.fragments.FragmentRegisterOrder;
import gdgdevfest.walker.utils.HelperUtils;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

public class ActivityMan extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_man);

		if (HelperUtils.isHasOrder(this)) {

			Fragment fragmentOrderStatus = getFragmentManager()
					.findFragmentByTag(FragmentOrderStatus.class.getName());
			if (fragmentOrderStatus != null)
				fragmentOrderStatus.onDestroy();

			fragmentOrderStatus = new FragmentOrderStatus();
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.frame_lt_content, fragmentOrderStatus,
							FragmentOrderStatus.class.getName()).commit();
		}

		else {

			Fragment fragmentReg = getFragmentManager().findFragmentByTag(
					FragmentRegisterOrder.class.getName());
			if (fragmentReg != null)
				fragmentReg.onDestroy();

			fragmentReg = new FragmentRegisterOrder();
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.frame_lt_content, fragmentReg,
							FragmentRegisterOrder.class.getName()).commit();
		}
	}
}
