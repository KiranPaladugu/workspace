package com.lyte.core.code;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ForkedEventSender implements Runnable {
	private Thread thread;
	private Object listener;
	private String methodToCall;
	private Object[] objectsTosend;
	private static int count=1;
	private String name="EventThread-";

	public ForkedEventSender(Object listener, String methodName, Object... objectsTosend) {
		this.listener = listener;
		this.objectsTosend = objectsTosend;
		methodToCall = methodName;
		createForkSender();
	}

	private void createForkSender() {
		thread = new Thread(this);
		thread.setName(name+count++);
		thread.start();
	}

	public ForkedEventSender(Object listener, Object... objectsTosend) {
		this.listener = listener;
		this.objectsTosend = objectsTosend;
		this.methodToCall = "onEvent";
		createForkSender();

	}

	@Override
	public void run() {
		Class<? extends Object> listenerClass = listener.getClass();
		for (Method method : listenerClass.getMethods()) {
			if (method.getName().equals(methodToCall)) {
				try {
					method.invoke(listener, objectsTosend);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
			}
		}

	}

}
