package containers;

import containers.Bean.Scope;

public class BeanDefination<T> {
	private T className;
	private boolean singleton;
	private Scope scope = Scope.NoScope;

	public T getClassName() {
		return className;
	}

	public void setClassName(T className) {
		this.className = className;
	}

	
	public boolean isSingleton() {
		return singleton;
	}

	public void setSingleton(boolean singleton) {
		this.singleton = singleton;
	}

	public Scope getScope() {
		return scope;
	}

	public void setScope(Scope scope) {
		this.scope = scope;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + (singleton ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BeanDefination<?> other = (BeanDefination<?>) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		if (scope != other.scope)
			return false;
		if (singleton != other.singleton)
			return false;
		return true;
	}
	
	

}
