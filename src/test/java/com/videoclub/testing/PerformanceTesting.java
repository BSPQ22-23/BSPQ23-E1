package com.videoclub.testing;

import org.junit.Rule;
import org.junit.Test;

import com.github.noconnor.junitperf.JUnitPerfRule;
import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;
import com.videoclub.dao.MovieDAO;
import com.videoclub.dao.UserDAO;
import com.videoclub.pojo.Movie;
import com.videoclub.pojo.MovieGenre;
import com.videoclub.pojo.User;
import com.videoclub.pojo.typeUser;

public class PerformanceTesting {

	@Rule
	public JUnitPerfRule rule = new JUnitPerfRule(new HtmlReportGenerator("Performance/report.html"));
	
	@Test
	@JUnitPerfTest(durationMs= 10000, threads= 20)
	@JUnitPerfTestRequirement(meanLatency = 500)
	public void testUserSaving() throws Exception{
		User user = new User("testPerf1", "123", "testPerf@test.com", "test", "perf", typeUser.CLIENT);
		UserDAO.getInstance().save(user);
	}
	@Test
	@JUnitPerfTest(durationMs= 2000, threads= 20)
	@JUnitPerfTestRequirement(maxLatency = 10)
	public void testErrorSaving() {
		Movie movie = new Movie("a", MovieGenre.ANIMATION, 10, 10, "tirano", 767.9);
		MovieDAO.getInstance().save(movie);
	}
}
