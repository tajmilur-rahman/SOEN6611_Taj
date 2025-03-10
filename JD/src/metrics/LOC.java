package metrics;

import java.util.List;
import java.util.Set;

import ast.ClassObject;
import ast.MethodObject;
import ast.SystemObject;
import ast.decomposition.CompositeStatementObject;
import ast.decomposition.MethodBodyObject;

public class LOC extends AbstractClassMetric {

	public LOC(SystemObject system) {
		super(system);
	}

	@Override
	protected void calculateMetric() {
		Set<ClassObject> classes = system.getClassObjects();
		
		for(ClassObject classObject : classes) {
			int loc = computeRelativeClassSize(classObject);
			metricValues.put(classObject.getName(), "" + loc);
		}		

	}

	private int computeRelativeClassSize(ClassObject classObject) {
		List<MethodObject> methods = classObject.getMethodList();
		
		int sum = 0;
		for(MethodObject m: methods) {
			if (!m.isStatic()) {
				MethodBodyObject mb = m.getMethodBody();
				if (null != mb) {
					CompositeStatementObject cs = mb.getCompositeStatement();
					sum += cs.count();
				}
			}
		}
		return sum;
	}
}
