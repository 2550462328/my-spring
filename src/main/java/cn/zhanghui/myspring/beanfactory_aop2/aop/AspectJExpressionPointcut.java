package cn.zhanghui.myspring.beanfactory_aop2.aop;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParameter;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

import lombok.Getter;
import lombok.Setter;
import cn.zhanghui.myspring.util.ClassUtils;
import cn.zhanghui.myspring.util.StringUtils;

@Getter
@Setter
public class AspectJExpressionPointcut implements Pointcut, MethodMatcher {
	private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<>();

	static {
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
		SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
	}

	private String expression;

	private PointcutExpression pointcutExpression;

	private ClassLoader pointcutClassLoader;

	public AspectJExpressionPointcut() {
	}
	
	@Override
	public boolean match(Method method) {
		//参数校验并解析expression为PointcutExpression
		checkReadytoMatch();
		
		//返回method是否匹配PointcurExpression
		ShadowMatch shadowMatch = getShadowMatch(method);

		if (shadowMatch.alwaysMatches()) {
			return true;
		}
		return false;
	}
	
	private void checkReadytoMatch() {
		if (getExpression() == null) {
			throw new IllegalStateException("Must set property 'expression' before attempting to match");
		}
		if (this.pointcutExpression == null) {
			this.pointcutClassLoader = ClassUtils.getDefaultClassLoader();
			this.pointcutExpression = buildPointcutExpression(this.pointcutClassLoader);
		}
	}
	
	/**
	 * 解析expression为PointcutExpression
	 * @param classLoader
	 * @return
	 */
	private PointcutExpression buildPointcutExpression(ClassLoader classLoader) {
		PointcutParser parser = PointcutParser
				.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
						SUPPORTED_PRIMITIVES, classLoader);
		
		return parser.parsePointcutExpression(replaceBooleanOperators(getExpression()), null, new PointcutParameter[0]);
	}
	
	
	private String replaceBooleanOperators(String expression) {
		String result = StringUtils.replace(expression, " and ", " && ");
		result = StringUtils.replace(expression, " or ", " || ");
		result = StringUtils.replace(expression, " not ", " ! ");
		return result;
	}
	
	private ShadowMatch getShadowMatch(Method method) {
		ShadowMatch shadowMatch = null;
		shadowMatch = this.pointcutExpression.matchesMethodExecution(method);
		
		return shadowMatch;
	}
	
	@Override
	public MethodMatcher getMethodMatcher() {
		return this;
	}
}
