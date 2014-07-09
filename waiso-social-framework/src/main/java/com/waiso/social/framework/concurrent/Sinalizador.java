package com.waiso.social.framework.concurrent;

import java.util.concurrent.CountDownLatch;

class Sinalizador
{
	private CountDownLatch startSignal;

	private CountDownLatch doneSignal;

	public void createSignals(int nThreads)
	{
		this.startSignal = new CountDownLatch(1);
		this.doneSignal = new CountDownLatch(nThreads);
	}

	public void doneWait()
	{
		try
		{
			doneSignal.await();
		}
		catch (InterruptedException e)
		{
			// do nothing. (leaving the current thread).
		}
	}

	public void startWait()
	{
		try
		{
			startSignal.await();
		}
		catch (InterruptedException e)
		{
			// do nothing. (leaving the current thread).
		}
	}

	public void doneCountDown()
	{
		doneSignal.countDown();
	}

	public void startCountDown()
	{
		startSignal.countDown();
	}
}
