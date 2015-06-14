package com.rick.base.context.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceAOP {
	private static final transient Logger logger = LoggerFactory.getLogger(ServiceAOP.class);  
	
	
	private ThreadLocal<Long> etLocal = new ThreadLocal<Long>();
	
	 /** 
     * 在核心业务执行前执行，不能阻止核心业务的调用。 
     * @param joinPoint 
     */  
    public void doBefore(JoinPoint joinPoint) {  
    	long startTime = System.currentTimeMillis();
    	etLocal.set(startTime);
    }  
      
    /** 
     * 手动控制调用核心业务逻辑，以及调用前和调用后的处理, 
     *  
     * 注意：当核心业务抛异常后，立即退出，转向After Advice 
     * 执行完毕After Advice，再转到Throwing Advice 
     * @param pjp 
     * @return 
     * @throws Throwable 
     */  
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {  
//        System.out.println("-----doAround().invoke-----");  
//        System.out.println(" 此处可以做类似于Before Advice的事情");  
//          
//        //调用核心逻辑  
        Object retVal = pjp.proceed();  
//          
//        System.out.println(" 此处可以做类似于After Advice的事情");  
//        System.out.println("-----End of doAround()------");  
        return retVal;  
    }  
  
    /** 
     * 核心业务逻辑退出后（包括正常执行结束和异常退出），执行此Advice 
     * @param joinPoint 
     */  
    public void doAfter(JoinPoint joinPoint) { 
    	long endTime = System.currentTimeMillis();
    	logger.info(joinPoint.getSignature().getName() +" execute Time:" + (endTime-etLocal.get()) + "ms");
    }  
      
    /** 
     * 核心业务逻辑调用正常退出后，不管是否有返回值，正常退出后，均执行此Advice 
     * @param joinPoint 
     */  
    public void doReturn(JoinPoint joinPoint) {  
//        System.out.println("-----doReturn().invoke-----");  
//        System.out.println(" 此处可以对返回值做进一步处理");  
//        System.out.println(" 可通过joinPoint来获取所需要的内容");  
//        System.out.println("-----End of doReturn()------");  
    }  
      
    /** 
     * 核心业务逻辑调用异常退出后，执行此Advice，处理错误信息 
     * @param joinPoint 
     * @param ex 
     */  
    public void doThrowing(JoinPoint joinPoint,Throwable ex) {  
//        System.out.println("-----doThrowing().invoke-----");  
//        System.out.println(" 错误信息："+ex.getMessage());  
//        System.out.println(" 此处意在执行核心业务逻辑出错时，捕获异常，并可做一些日志记录操作等等");  
//        System.out.println(" 可通过joinPoint来获取所需要的内容");  
//        System.out.println("-----End of doThrowing()------");  
    } 
}
