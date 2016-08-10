package containers;

public class Bean<T> {
	public static enum Scope {
		Application, Session, Local, Global, NoScope
	}

	private BeanDefination<T> bean;
	private T object;

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public BeanDefination<T> getBean() {
		return bean;
	}

	public void setBean(BeanDefination<T> bean) {
		this.bean = bean;
	}

	@SuppressWarnings("unchecked")
	public T newInstance() throws InstantiationException, IllegalAccessException {
		return (T) bean.getClassName().getClass().newInstance();
	}

}
