/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * JBoss, Home of Professional Open Source
 * Copyright 2013-2014 Red Hat Inc. and/or its affiliates and other contributors
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
package org.hibernate.ogm.datastore.document.options;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.hibernate.ogm.datastore.document.options.impl.AssociationStorageConverter;
import org.hibernate.ogm.options.spi.MappingOption;

/**
 * Define the association storage type for the annotated entity or property. When given for a property which doesn't
 * represent an association, this setting is ignored.
 *
 * @author Gunnar Morling
 */
@Target({ METHOD, FIELD, TYPE })
@Retention(RUNTIME)
@MappingOption(AssociationStorageConverter.class)
public @interface AssociationStorage {

	/**
	 * The strategy for storing associations of the annotated element
	 *
	 * @return the strategy for storing associations of the annotated element
	 */
	AssociationStorageType value();
}
