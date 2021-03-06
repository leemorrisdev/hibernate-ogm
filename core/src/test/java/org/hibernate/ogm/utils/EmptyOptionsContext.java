/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2014 Red Hat Inc. and/or its affiliates and other contributors
 * as indicated by the @authors tag. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA  02110-1301, USA.
 */
package org.hibernate.ogm.utils;

import java.util.Collections;
import java.util.Map;

import org.hibernate.ogm.options.spi.Option;
import org.hibernate.ogm.options.spi.OptionsContext;
import org.hibernate.ogm.options.spi.UniqueOption;

/**
 * An empty {@link OptionsContext} for testing purposes.
 *
 * @author Gunnar Morling
 */
public class EmptyOptionsContext implements OptionsContext {

	public static final OptionsContext INSTANCE = new EmptyOptionsContext();

	private EmptyOptionsContext() {
	}

	@Override
	public <I, V, O extends Option<I, V>> V get(Class<O> optionType, I identifier) {
		return null;
	}

	@Override
	public <V, O extends UniqueOption<V>> V getUnique(Class<O> optionType) {
		return null;
	}

	@Override
	public <I, V, O extends Option<I, V>> Map<I, V> getAll(Class<O> optionType) {
		return Collections.emptyMap();
	}
}
