package gdgdevfest.walker.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import gdgdevfest.walker.R;

public class ActivityMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
