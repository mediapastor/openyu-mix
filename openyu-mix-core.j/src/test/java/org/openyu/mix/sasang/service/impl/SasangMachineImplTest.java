package org.openyu.mix.sasang.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;

import org.openyu.mix.sasang.SasangTestSupporter;
import org.openyu.mix.sasang.vo.Outcome;
import org.openyu.mix.sasang.vo.Outcome.OutcomeType;
import org.openyu.mix.sasang.vo.SasangType;
import org.openyu.commons.lang.NumberHelper;
import org.openyu.commons.lang.SystemHelper;

public class SasangMachineImplTest extends SasangTestSupporter
{

	@Test
	//1000000 times: 1542 mills.
	//1000000 times: 1385 mills.
	//1000000 times: 1611 mills.
	public void calcSasangTypeWeights()
	{
		boolean result = false;
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.calcSasangTypeWeights();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println(sasangMachine.getSasangWeights());
		//
		int weight = sasangMachine.getSasangTypeWeight(SasangType.AZURE_DRAGON, 0);
		System.out.println(weight);
		assertEquals(4, weight);
		//
		weight = sasangMachine.getSasangTypeWeight(SasangType.VERMILION_BIRD, 2);
		System.out.println(weight);
		assertEquals(2, weight);
	}

	@Test
	//1000000 times: 297 mills. 
	//1000000 times: 298 mills. 
	//1000000 times: 289 mills. 
	public void calcRoundWeightSums()
	{
		boolean result = false;
		int count = 1000000;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.calcRoundWeightSums();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println(sasangMachine.getRoundWeightSums());
		//
		int sum = sasangMachine.getRoundWeightSum(0);
		System.out.println(sum);//64
		assertEquals(64, sum);
		//
		sum = sasangMachine.getRoundWeightSum(1);
		System.out.println(sum);//64
		assertEquals(64, sum);
		//
		sum = sasangMachine.getRoundWeightSum(2);
		System.out.println(sum);//64
		assertEquals(64, sum);
		//
		sum = sasangMachine.getRoundWeightSum(3);
		System.out.println(sum);//0
	}

	@Test
	public void calcSameThreeProbs()
	{
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.calcSameThreeProbs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		//{AZURE_DRAGON=4.57764E-5, WHITE_TIGER=2.746582E-4, VERMILION_BIRD=1.525879E-4, BLACK_TORTOISE=4.577637E-4, YIN=8.010864E-4, YANG=0.0010986328, NOTHING=0.1659851074}
		System.out.println(result);
		System.out.println(sasangMachine.getSameThreeProbs());
		//
		double prob = sasangMachine.getSameThreeProb(SasangType.AZURE_DRAGON);//4.57764E-5,1/10w
		System.out.println(NumberHelper.toString(prob, "#,##0.##########"));
		assertEquals(Double.doubleToLongBits(4.57764E-5), Double.doubleToLongBits(prob));
		//
		prob = sasangMachine.getSameThreeProb(SasangType.WHITE_TIGER);//2.746582E-4,1/1w
		System.out.println(NumberHelper.toString(prob, "#,##0.##########"));
		assertEquals(Double.doubleToLongBits(2.746582E-4), Double.doubleToLongBits(prob));
		//
		prob = sasangMachine.getSameThreeProb(SasangType.VERMILION_BIRD);//1.525879E-4,1/1w
		System.out.println(NumberHelper.toString(prob, "#,##0.##########"));
		assertEquals(Double.doubleToLongBits(1.525879E-4), Double.doubleToLongBits(prob));
		//
		prob = sasangMachine.getSameThreeProb(SasangType.BLACK_TORTOISE);//4.577637E-4,1/1w
		System.out.println(NumberHelper.toString(prob, "#,##0.##########"));
		assertEquals(Double.doubleToLongBits(4.577637E-4), Double.doubleToLongBits(prob));
	}

	@Test
	public void calcSameTwoProbs()
	{
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.calcSameTwoProbs();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		//{AZURE_DRAGON=0.0045013428, WHITE_TIGER=0.0123596191, VERMILION_BIRD=0.0088195801, BLACK_TORTOISE=0.0166931152, YIN=0.0237197876, YANG=0.0289306641, NOTHING=0.4214782715}
		System.out.println(result);
		System.out.println(sasangMachine.getSameTwoProbs());
		//
		double prob = sasangMachine.getSameTwoProb(SasangType.AZURE_DRAGON);//0.0045013428
		System.out.println(prob);
		assertEquals(Double.doubleToLongBits(0.0045013428), Double.doubleToLongBits(prob));
		//
		prob = sasangMachine.getSameTwoProb(SasangType.WHITE_TIGER);//0.0123596191
		System.out.println(prob);
		assertEquals(Double.doubleToLongBits(0.0123596191), Double.doubleToLongBits(prob));
		//
		prob = sasangMachine.getSameTwoProb(SasangType.VERMILION_BIRD);//0.0088195801
		System.out.println(prob);
		assertEquals(Double.doubleToLongBits(0.0088195801), Double.doubleToLongBits(prob));
		//
		prob = sasangMachine.getSameTwoProb(SasangType.BLACK_TORTOISE);//0.0166931152
		System.out.println(prob);
		assertEquals(Double.doubleToLongBits(0.0166931152), Double.doubleToLongBits(prob));
	}

	@Test
	public void calcTotalProbSums()
	{
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.calcTotalProbSums();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		double[] probSums = sasangMachine.getProbSums();
		SystemHelper.println(probSums);//0.0028305054, 0.0950241089, 0.9021453857

		double sum = 0d;
		for (double prob : probSums)
		{
			sum += prob;
		}
		System.out.println(sum);//1.0
		assertEquals(Double.doubleToLongBits(1.0), Double.doubleToLongBits(sum));
	}

	//
	@Test
	//1000000 times: 45 mills. 
	//1000000 times: 39 mills. 
	//1000000 times: 55 mills. 
	public void startByType()
	{
		boolean result = false;
		int times = 0;
		int count = 1000000;//100w
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.playByType(SasangType.AZURE_DRAGON, 3);//3個相同青龍
			if (result)
			{
				times += 1;
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		System.out.println("times: " + times);//4.57764E-5, 46
		//
		times = 0;
		beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.playByType(SasangType.AZURE_DRAGON, 2);//2個相同青龍
			if (result)
			{
				times += 1;
			}
		}
		//
		end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		System.out.println("times: " + times);//0.0045013428, 4605
		//
		times = 0;
		beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.playByType(SasangType.WHITE_TIGER, 3);//3個相同白虎
			if (result)
			{
				times += 1;
			}
		}
		//
		end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");
		System.out.println("times: " + times);//2.746582E-4, 262
	}

	@Test
	public void buildOutcomes()
	{
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.buildOutcomes();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println(sasangMachine.getOutcomes());

		int size = sasangMachine.getOutcomes().size();
		System.out.println(size);
		assertEquals(343, size);//3輪^7=343個
	}

	@Test
	public void calcOutcomesProbSum()
	{
		boolean result = false;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.calcOutcomesProbSum();
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println(sasangMachine.getOutcomesProbSum());//1.0000000005
	}

	@Test
	//1000000 times: 5032 mills. 
	//1000000 times: 4986 mills. 
	//1000000 times: 4867 mills. 
	public void start()
	{
		Outcome result = null;
		//
		int count3same = 0;
		int count2same = 0;
		int countStandAlone = 0;

		int count = 1000000;//100w
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.start();
			OutcomeType outcomeType = result.getOutcomeType();
			if (outcomeType == OutcomeType.SAME_THREE)
			{
				count3same += 1;
			}
			else if (outcomeType == OutcomeType.SAME_TWO)
			{
				count2same += 1;
			}
			else if (outcomeType == OutcomeType.STAND_ALONE)
			{
				countStandAlone += 1;
			}
			else
			{
				System.out.println(result.getOutcomeType());
			}
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
		System.out.println("3個相同總計: " + count3same);//2867
		System.out.println("2個相同總計: " + count2same);//94811
		System.out.println("安慰獎總計: " + countStandAlone);//902322
		//
		System.out.println("中獎總計: " + (count3same + count2same + countStandAlone));
		//
		//
		result = sasangMachine.start();
		System.out.println(result);
		assertNotNull(result);
		//
		result = sasangMachine.start();
		System.out.println(result);
		assertNotNull(result);
	}

	@Test
	//1000000 times: 4929 mills.
	//1000000 times: 5041 mills.
	//1000000 times: 5059 mills.
	public void startByTimes()
	{
		List<Outcome> result = null;

		int count = 100000;//100w
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.start(10);
		}
		//
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result.size() + ", " + result);
		assertEquals(10, result.size());
		//
		result = sasangMachine.start(10);
		System.out.println(result.size() + ", " + result);
		assertEquals(10, result.size());
	}

	@Test
	public void createOutcome()
	{
		Outcome result = null;
		int count = 1;
		long beg = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
		{
			result = sasangMachine.createOutcome("111");
		}
		long end = System.currentTimeMillis();
		System.out.println(count + " times: " + (end - beg) + " mills. ");

		System.out.println(result);
	}

}