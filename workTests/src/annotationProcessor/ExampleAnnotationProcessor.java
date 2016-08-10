package annotationProcessor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;

import annotation.Example;
import containers.Bean;
import containers.BeanContainer;
import containers.BeanDefination;

@SupportedAnnotationTypes({ "annotation.Example" })
public class ExampleAnnotationProcessor extends AbstractProcessor {

	private BeanContainer beanContainer;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean process(Set<? extends TypeElement> arg0, RoundEnvironment arg1) {
		System.out.println("Running annotation processor...");
		System.out.println(arg1.getRootElements());
		Set<? extends Element> annotedWith = arg1.getElementsAnnotatedWith(Example.class);
		Set<VariableElement> fields = ElementFilter.fieldsIn(annotedWith);
		for(VariableElement field: fields){
			TypeMirror fieldType = field.asType();
			String fullTypeClassName = fieldType.toString();
			try {
				Class<?> beanClass = Class.forName(fullTypeClassName);
				BeanDefination beanDef = new BeanDefination<>();
				beanDef.setClassName(beanClass);
				beanDef.setScope(Bean.Scope.NoScope);
				try {
					Object obj = beanContainer.getBean(beanDef);
					if (obj != null) {
						System.out.println("Crating bean class for :" + beanClass.getName());
						
					}
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		/*for (Element element : annotedWith) {
			if (element instanceof TypeElement) {
				final TypeElement typeElement = (TypeElement) element;
				for(final Element enclosedElement : typeElement.getEnclosedElements()){
					if(enclosedElement instanceof VariableElement){
						final VariableElement variableElement = (VariableElement) enclosedElement;
						
						Class beanClass = element.getClass();
						BeanDefination beanDef = new BeanDefination<>();
						beanDef.setClassName(beanClass);
						beanDef.setScope(Bean.Scope.NoScope);
						try {
							Object obj = beanContainer.getBean(beanDef);
							if (obj != null) {
								System.out.println("Crating bean class for :" + beanClass.getName());

							}
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}*/
		return true;
	}

	@Override
	public void init(ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
		beanContainer = new BeanContainer();
		System.out.println("Called init");

	}

}
