package org.openyu.mix.wuxing.vo;

import static org.junit.Assert.assertNotNull;

import org.junit.Rule;
import org.junit.Test;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;

public class WuxingEditorTest {

	@Rule
	public BenchmarkRule benchmarkRule = new BenchmarkRule();

	@Test
	@Deprecated
	/**
	 * 只是為了模擬用,有正式excel後,不應再使用,以免覆蓋掉正式的excel
	 */
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToExcel() {
		WuxingEditor editor = WuxingEditor.getInstance();
		String result = null;
		//
		result = editor.writeToExcel();
		//
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	@BenchmarkOptions(benchmarkRounds = 1, warmupRounds = 0, concurrency = 1)
	public void writeToXml() {
		WuxingEditor editor = WuxingEditor.getInstance();
		String result = null;
		//
		result = editor.writeToXml();
		//
		System.out.println(result);
		assertNotNull(result);
	}

}
