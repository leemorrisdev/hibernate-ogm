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
package org.hibernate.ogm.test.options.mapping;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.lang.annotation.ElementType;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.ogm.options.container.impl.OptionsContainer;
import org.hibernate.ogm.options.navigation.impl.AppendableConfigurationContext;
import org.hibernate.ogm.options.navigation.impl.ConfigurationContext;
import org.hibernate.ogm.options.navigation.source.impl.OptionValueSource;
import org.hibernate.ogm.options.navigation.source.impl.ProgrammaticOptionValueSource;
import org.hibernate.ogm.test.options.examples.EmbedExampleOption;
import org.hibernate.ogm.test.options.examples.ForceExampleOption;
import org.hibernate.ogm.test.options.examples.NameExampleOption;
import org.hibernate.ogm.test.options.examples.NamedQueryOption;
import org.hibernate.ogm.test.options.mapping.model.SampleOptionModel;
import org.hibernate.ogm.test.options.mapping.model.SampleOptionModel.SampleGlobalContext;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for putting/retrieving values into/from {@link ProgrammaticOptionValueSource}.
 *
 * @author Davide D'Alto <davide@hibernate.org>
 * @author Gunnar Morling
 */
public class ProgrammaticOptionValueSourceTest {

	private SampleGlobalContext configuration;
	private AppendableConfigurationContext configurationContext;

	@Before
	public void setupContexts() {
		configurationContext = new AppendableConfigurationContext();
		configuration = SampleOptionModel.createGlobalContext( new ConfigurationContext( configurationContext ) );
	}

	@Test
	public void shouldBeAbleToAddGlobalOption() throws Exception {
		configuration.force( true );

		assertThat( getSource().getGlobalOptions().getUnique( ForceExampleOption.class ) ).isTrue();
	}

	@Test
	public void shouldBeAbleToAddNonUniqueGlobalOption() throws Exception {
		configuration
			.namedQuery( "foo", "from foo" )
			.namedQuery( "bar", "from bar" );

		Map<String, String> queries = getSource().getGlobalOptions().getAll( NamedQueryOption.class );
		assertThat( queries )
			.hasSize( 2 )
			.includes(
				entry( "foo", "from foo" ),
				entry( "bar", "from bar" )
		);
	}

	@Test
	public void shouldBeAbleToAddEntityOption() throws Exception {
		configuration
			.entity( ContextExample.class )
				.force( true );

		OptionsContainer optionsContainer = getSource().getEntityOptions( ContextExample.class );
		assertThat( optionsContainer.getUnique( ForceExampleOption.class ) ).isTrue();
	}

	@Test
	public void shouldBeAbleToAddSeveralEntityOptions() throws Exception {
		configuration
			.entity( Refrigerator.class )
				.force( true )
				.name( "test" );

		OptionsContainer refrigatorOptions = getSource().getEntityOptions( Refrigerator.class );

		assertThat( refrigatorOptions.getUnique( ForceExampleOption.class ) ).isTrue();
		assertThat( refrigatorOptions.getUnique( NameExampleOption.class ) ).isEqualTo( "test" );
	}

	@Test
	public void shouldBeAbleToAddPropertyOption() throws Exception {
		configuration
			.entity( ContextExample.class )
				.property( "property", ElementType.FIELD )
					.embed( "Foo" );

		OptionsContainer optionsContainer = getSource().getPropertyOptions( ContextExample.class, "property" );
		assertThat( optionsContainer.getUnique( EmbedExampleOption.class ) ).isEqualTo( "Foo" );
	}

	@Test
	public void shouldBeAbleToAddPropertyOptionOnGetterLevel() throws Exception {
		configuration
			.entity( ContextExample.class )
				.property( "property", ElementType.METHOD )
					.embed( "Foo" );

		OptionsContainer optionsContainer = getSource().getPropertyOptions( ContextExample.class, "property" );
		assertThat( optionsContainer.getUnique( EmbedExampleOption.class ) ).isEqualTo( "Foo" );
	}

	@Test(expected = HibernateException.class)
	public void addingPropertyToNonExistingPropertyShouldRaiseException() throws Exception {
		configuration
			.entity( ContextExample.class )
				.property( "getProperty", ElementType.METHOD )
					.embed( "Foo" );
	}

	@Test
	public void shouldBeAbleToRetrieveUniqueEntityOptionViaGet() throws Exception {
		configuration
			.entity( Refrigerator.class )
				.force( true );

		OptionsContainer refrigatorOptions = getSource().getEntityOptions( Refrigerator.class );
		assertThat( refrigatorOptions.getUnique( ForceExampleOption.class ) ).isTrue();
	}

	@Test
	public void uniqueEntityOptionShouldHaveLastValueWhenSetSeveralTimes() throws Exception {
		configuration
			.entity( Refrigerator.class )
				.force( true )
				.force( false );

		OptionsContainer refrigatorOptions = getSource().getEntityOptions( Refrigerator.class );
		assertThat( refrigatorOptions.getUnique( ForceExampleOption.class ) ).isFalse();
	}

	private OptionValueSource getSource() {
		return new ProgrammaticOptionValueSource( configurationContext );
	}

	private static class ContextExample {

		private String property;

		@SuppressWarnings("unused")
		public String getProperty() {
			return property;
		}
	}

	public static class Refrigerator {

		public int temperature;
	}

	public static class Microwave {

		public int power;
	}
}
