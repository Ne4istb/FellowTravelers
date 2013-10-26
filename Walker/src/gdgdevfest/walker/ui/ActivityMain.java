package gdgdevfest.walker.ui;

import gdgdevfest.walker.R;
import gdgdevfest.walker.dao.PointDAO;
import gdgdevfest.walker.net.api.OrderPostAPI;
import gdgdevfest.walker.ui.fragments.FragmentChoice;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;

public class ActivityMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		

		Fragment fragment = getFragmentManager().findFragmentByTag(
				FragmentChoice.class.getName());
		if (fragment != null)
			fragment.onDestroy();
		fragment = new FragmentChoice();
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.frame_lt_content, fragment,
						FragmentChoice.class.getName()).commit();
	}

	@Override
	protected void onStart() {
		super.onStart();
		
		new Thread() {

			public void run() {

				OrderPostAPI orderPostAPI = new OrderPostAPI(new PointDAO(10.1, 20.3), new PointDAO(40.3,
						66.3), 20.3, "380931207128");
				int res = orderPostAPI.sendData();
				int b = res;
			};
		}.start();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
