package com.oracle.auto.web.jbehave_ext;

import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.log4j.Logger;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.failures.RestartingScenarioFailure;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.steps.AbstractStepResult.Failed;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepResult;

import java.util.Map;

public class StepRetryDecorator implements Step {
	private static Logger log = Logger.getLogger(StepRetryDecorator.class);

	private Step step = null;
	private int max_retry_count = 0;
	private Map<String, String> parameters;
	
	private ThreadLocal<Integer> thread_retry_count = new ThreadLocal<Integer>();
	
	// return original value
	private int postIncrementRetry() {
		int retry_count = (thread_retry_count.get() == null) ? 0 : thread_retry_count.get();
		thread_retry_count.set(retry_count+1);
		return retry_count+1;
	}
	
	// reset
	private void resetRetry() {
		thread_retry_count.set(0);
	}
	
	public StepRetryDecorator(Step step, int max_retry_count, Map<String, String> parameters) {
		this.step = step;
		this.max_retry_count = max_retry_count;
		this.parameters = parameters;
	}
	
	@Override
	public StepResult perform(UUIDExceptionWrapper storyFailureIfItHappened) {
		// set thread context list
		PropertyConfiger.instance().setThreadContext(parameters);
		
		StepResult result = step.perform(storyFailureIfItHappened);

		int retrycount = 0;
		if (result instanceof Failed && ((retrycount = postIncrementRetry()) <= max_retry_count)) {

			log.warn("will retry the step, current count = " + retrycount + ", max retry count = " + max_retry_count, result.getFailure());
			String message = result.getFailure().getMessage() + " (FAILED - Retry " + retrycount + ")";
			throw new RestartingScenarioFailure(message, result.getFailure().getCause());
		}

		resetRetry();
		return result;
	}

	@Override
	public StepResult doNotPerform(UUIDExceptionWrapper storyFailureIfItHappened) {
		return step.doNotPerform(storyFailureIfItHappened);
	}

	@Override
	public String asString(Keywords keywords) {
		return step.asString(keywords);
	}

}
