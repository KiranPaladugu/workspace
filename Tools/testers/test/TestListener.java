package test;

import com.lyte.core.api.PluginEventListener;

public class TestListener implements PluginEventListener {

	@Override
	public void onEvent(Object event) {
		System.out.println(Thread.currentThread().getName());
		System.out.println("GOT EVENT :"+event);
	}

}
