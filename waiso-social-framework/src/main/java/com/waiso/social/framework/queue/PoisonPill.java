package com.waiso.social.framework.queue;

public final class PoisonPill {

	public static final PoisonPill SINGLETON = new PoisonPill();

	private PoisonPill() {

	}
}
