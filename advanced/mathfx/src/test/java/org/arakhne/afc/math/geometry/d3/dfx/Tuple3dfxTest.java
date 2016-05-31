/*
 * $Id$
 * This file is a part of the Arakhne Foundation Classes, http://www.arakhne.org/afc
 *
 * Copyright (c) 2000-2012 Stephane GALLAND.
 * Copyright (c) 2005-10, Multiagent Team, Laboratoire Systemes et Transports,
 *                        Universite de Technologie de Belfort-Montbeliard.
 * Copyright (c) 2013-2016 The original authors, and other authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.arakhne.afc.math.geometry.d3.dfx;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.arakhne.afc.math.geometry.d3.AbstractTuple3DTest;
import org.junit.Test;

import javafx.beans.property.DoubleProperty;

@SuppressWarnings("all")
public class Tuple3dfxTest extends AbstractTuple3DTest<Tuple3dfx, Tuple3dfx> {

	@Override
	public boolean isIntCoordinates() {
		return false;
	}
	
	@Override
	public Tuple3dfx createTuple(double x, double y, double z) {
		return new Tuple3dfx(x, y, z);
	}
	
	@Test
	public void xProperty() {
		DoubleProperty property = this.t.xProperty();
		assertNotNull(property);
		DoubleProperty property2 = this.t.xProperty();
		assertSame(property, property2);
		assertEpsilonEquals(1, property.get());
	}
	
	@Test
	public void yProperty() {
		DoubleProperty property = this.t.yProperty();
		assertNotNull(property);
		DoubleProperty property2 = this.t.yProperty();
		assertSame(property, property2);
		assertEpsilonEquals(-2, property.get());
	}

	@Test
	public void zProperty() {
		DoubleProperty property = this.t.zProperty();
		assertNotNull(property);
		DoubleProperty property2 = this.t.zProperty();
		assertSame(property, property2);
		assertEpsilonEquals(5, property.get());
	}

	@Test
	public void xPropertySetter() {
		assertEpsilonEquals(1, this.t.getX());
		assertEpsilonEquals(-2, this.t.getY());
		assertEpsilonEquals(5, this.t.getZ());
		DoubleProperty property = this.t.xProperty();
		property.set(345.);
		assertEpsilonEquals(345., this.t.getX());
		assertEpsilonEquals(-2, this.t.getY());
		assertEpsilonEquals(5, this.t.getZ());
	}
	
	@Test
	public void yPropertySetter() {
		assertEpsilonEquals(1, this.t.getX());
		assertEpsilonEquals(-2, this.t.getY());
		assertEpsilonEquals(5, this.t.getZ());
		DoubleProperty property = this.t.yProperty();
		property.set(345.);
		assertEpsilonEquals(1, this.t.getX());
		assertEpsilonEquals(345., this.t.getY());
		assertEpsilonEquals(5, this.t.getZ());
	}

	@Test
	public void zPropertySetter() {
		assertEpsilonEquals(1, this.t.getX());
		assertEpsilonEquals(-2, this.t.getY());
		assertEpsilonEquals(5, this.t.getZ());
		DoubleProperty property = this.t.zProperty();
		property.set(345.);
		assertEpsilonEquals(1, this.t.getX());
		assertEpsilonEquals(-2, this.t.getY());
		assertEpsilonEquals(345., this.t.getZ());
	}

}
