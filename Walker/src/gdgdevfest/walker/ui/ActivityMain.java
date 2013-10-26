package gdgdevfest.walker.ui;

import gdgdevfest.walker.R;
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
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
}
