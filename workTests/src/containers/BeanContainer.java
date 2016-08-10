package containers;

import java.util.HashMap;
import java.util.Map;

public class BeanContainer {
	private Map<BeanDefination<?>, Bean<?>> beanMap = new HashMap<>();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getBean(BeanDefination<?> beanDefination) throws InstantiationException, IllegalAccessException {
		Bean<?> bean = beanMap.get(beanDefination);
		if (bean != null) {
			if (beanDefination.isSingleton() || beanDefination.getScope() == Bean.Scope.Application
					|| beanDefination.getScope() == Bean.Scope.Global) {
				return bean.getObject();
			} else if(beanDefination.getScope() == Bean.Scope.NoScope || beanDefination.getScope() == Bean.Scope.Local){
				return bean.newInstance();
			}
		}
		if(beanDefination.getScope() == Bean.Scope.NoScope || beanDefination.getScope() == Bean.Scope.Local){
			return bean.newInstance();
		}
		if (beanDefination.isSingleton() || beanDefination.getScope() == Bean.Scope.Application
				|| beanDefination.getScope() == Bean.Scope.Global) {
			Object obj = beanDefination.getClassName().getClass().newInstance();
			Bean newBean = new Bean();
			newBean.setBean(beanDefination);
			newBean.setObject(obj);
			beanMap.put(beanDefination, newBean);
			return obj;
		}
		return null;
	}

}
