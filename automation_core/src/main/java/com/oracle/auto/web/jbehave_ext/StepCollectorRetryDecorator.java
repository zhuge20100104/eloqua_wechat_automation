package com.oracle.auto.web.jbehave_ext;

import com.oracle.auto.web.utility.PropertyConfiger;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.model.Lifecycle;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.steps.CandidateSteps;
import org.jbehave.core.steps.Step;
import org.jbehave.core.steps.StepCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StepCollectorRetryDecorator implements StepCollector {
    private StepCollector stepCollector = null;
    private int max_retry_count = 0;

    public StepCollectorRetryDecorator(StepCollector stepCollector) {
        this.stepCollector = stepCollector;
        max_retry_count = PropertyConfiger.instance().getEnvData("retry.count.when.failure", 0);
    }

    private List<Step> decorateWithRetryStep(List<Step> list, Map<String, String> parameters) {
//		if (max_retry_count <= 0)
//			return list;  // no retry, so directly return it.

        List<Step> result = new ArrayList<Step>();
        for (Step step : list)
            result.add(new StepRetryDecorator(step, max_retry_count, parameters));

        return result;
    }

    @Override
    public List<Step> collectBeforeOrAfterStoriesSteps(
            List<CandidateSteps> candidateSteps, Stage stage) {
        return stepCollector.collectBeforeOrAfterStoriesSteps(candidateSteps, stage);
    }

    @Override
    public List<Step> collectBeforeOrAfterStorySteps(
            List<CandidateSteps> candidateSteps, Story story, Stage stage,
            boolean givenStory) {
        return stepCollector.collectBeforeOrAfterStorySteps(candidateSteps, story, stage, givenStory);
    }

    @Override
    public List<Step> collectBeforeOrAfterScenarioSteps(
            List<CandidateSteps> candidateSteps, Meta storyAndScenarioMeta,
            Stage stage, ScenarioType type) {
        return stepCollector.collectBeforeOrAfterScenarioSteps(candidateSteps, storyAndScenarioMeta, stage, type);
    }

    private class PreviousScenarioStepsCache {
        private List<CandidateSteps> candidateSteps;
        private Scenario scenario;
        private Map<String, String> parameters;
        private List<Step> steps;

        public PreviousScenarioStepsCache(List<CandidateSteps> candidateSteps,
                                          Scenario scenario, Map<String, String> parameters, List<Step> steps) {
            this.candidateSteps = candidateSteps;
            this.scenario = scenario;
            this.parameters = parameters;
            this.steps = steps;
        }

        public boolean isDuplicate(List<CandidateSteps> candidateSteps,
                                   Scenario scenario, Map<String, String> parameters) {
            return this.candidateSteps == candidateSteps && this.scenario == scenario && this.parameters == parameters;
        }

        public List<Step> getCachedSteps() {
            return steps;
        }

    }

    private ThreadLocal<PreviousScenarioStepsCache> cache = new ThreadLocal<PreviousScenarioStepsCache>();

    private List<Step> isRetryScenarioSteps(List<CandidateSteps> candidateSteps,
                                            Scenario scenario, Map<String, String> parameters) {
        if (cache.get() != null && cache.get().isDuplicate(candidateSteps, scenario, parameters)) {
            return cache.get().getCachedSteps();
        }

        return null;
    }

    private void setCahceSteps(List<CandidateSteps> candidateSteps,
                               Scenario scenario, Map<String, String> parameters, List<Step> steps) {
        cache.set(new PreviousScenarioStepsCache(candidateSteps, scenario, parameters, steps));
    }

    @Override
    public List<Step> collectScenarioSteps(List<CandidateSteps> candidateSteps,
                                           Scenario scenario, Map<String, String> parameters) {
        List<Step> retriedSteps = isRetryScenarioSteps(candidateSteps, scenario, parameters);
        if (retriedSteps == null) {
            List<Step> list = stepCollector.collectScenarioSteps(candidateSteps, scenario, parameters);
            retriedSteps = decorateWithRetryStep(list, parameters);
            setCahceSteps(candidateSteps, scenario, parameters, retriedSteps);
        }

        return retriedSteps;
    }

    @Override
    public List<Step> collectLifecycleSteps(
            List<CandidateSteps> candidateSteps, Lifecycle lifecycle,
            Meta storyAndScenarioMeta, Stage stage) {
        return stepCollector.collectLifecycleSteps(candidateSteps, lifecycle, storyAndScenarioMeta, stage);
    }

}
