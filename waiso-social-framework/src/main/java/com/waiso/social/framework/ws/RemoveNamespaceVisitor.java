package com.waiso.social.framework.ws;

import java.util.ListIterator;

import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.VisitorSupport;

public class RemoveNamespaceVisitor extends VisitorSupport {
	private Namespace toRemove;

	public RemoveNamespaceVisitor(Namespace toRemove) {
		this.toRemove = toRemove;
	}

	public void visit(Element node) {
		ListIterator namespaces = node.additionalNamespaces().listIterator();
		while (namespaces.hasNext()) {
			Namespace additionalNamespace = (Namespace) namespaces.next();
			if (additionalNamespace.getURI().equals(toRemove.getURI())) {
				namespaces.remove();
			}
		}
	}

}
