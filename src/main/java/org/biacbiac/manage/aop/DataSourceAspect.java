package org.biacbiac.manage.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.biacbiac.manage.utils.DynamicDataSource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Aspect
@Component 
public class DataSourceAspect {

	@Before(value = "execution(* org.biacbiac.manege.service.impl.*.*(..))")
	public void beofre(JoinPoint jp) {
		Object[] args=jp.getArgs();
		BigDecimal workMode=new BigDecimal(-1);
		
		if(args.length>0){
			Object arg = args[args.length-1];
	        String name = arg.getClass().getName();
	        if(name.endsWith("BigDecimal")){
				try{
				    workMode = (BigDecimal)arg;
				}catch(Exception e){
				}
	        }
		}		
	    DynamicDataSource.setDataSource(workMode);
//		DynamicDataSource.setDataSource(new BigDecimal(-1));
	  }
	
	@After(value = "execution(* org.biacbiac.manege.service.impl.*.*(..))")
	public void after(JoinPoint jp){
		DynamicDataSource.clearDataSource();
	}
}
