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

package org.arakhne.afc.math.geometry.d3.afp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.d3.Point3D;
import org.arakhne.afc.math.geometry.d3.Vector3D;

@SuppressWarnings("all")
public abstract class AbstractPath3afpPointCollectionTest<P extends Point3D<? super P, ? super V>,
		V extends Vector3D<? super V, ? super P>,
		B extends RectangularPrism3afp<?, ?, ?, P, V, B>> extends AbstractMathTestCase {
	
	/** Is the shape to test.
	 */
	protected Path3afp<?, ?, ?, P, V, B> shape;
	
	/** Is the collection to test.
	 */
	protected Collection<P> collection;
	
	/** Shape factory.
	 */
	protected TestShapeFactory3afp<P, V, B> factory;

	protected abstract TestShapeFactory3afp<P, V, B> createFactory();

	@Before
	public void setUp() throws Exception {
		this.factory = createFactory();
		this.shape = (Path3afp<?, ?, ?, P, V, B>) this.factory.createPath(PathWindingRule.EVEN_ODD);
		this.shape.moveTo(1, 1, 0);
		this.shape.lineTo(2, 2, 0);
		this.shape.quadTo(3, 0, 0, 4, 3, 0);
		this.shape.curveTo(5, -1, 0, 6, 5, 0, 7, -5, 0);
		this.shape.closePath();
		this.collection = this.shape.toCollection();
	}
	
	/**
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.shape = null;
		this.collection = null;
		this.factory = null;
	}
	
	private void assertCoords(double... coords) {
		assertEquals(coords.length/3, this.shape.size());
		for(int i=0, j=0; i<this.shape.size(); ++i) {
			Point3D p = this.shape.getPointAt(i);
			assertEpsilonEquals(coords[j++], p.getX());
			assertEpsilonEquals(coords[j++], p.getY());
			assertEpsilonEquals(coords[j++], p.getZ());
		}
	}
	
    /**
     */
	@Test
    public void size() {
    	assertEquals(7, this.collection.size());
    	this.shape.removeLast();
    	assertEquals(7, this.collection.size());
    	this.shape.removeLast();
    	assertEquals(4, this.collection.size());
    	this.shape.clear();
    	assertEquals(0, this.collection.size());
    }

    /**
     */
	@Test
    public void isEmpty() {
    	assertFalse(this.collection.isEmpty());
    	this.shape.removeLast();
    	assertFalse(this.collection.isEmpty());
    	this.shape.removeLast();
    	assertFalse(this.collection.isEmpty());
    	this.shape.clear();
    	assertTrue(this.collection.isEmpty());
    }

    /**
     */
	@Test
	@Ignore
    public void containsObject() {
    	assertFalse(this.collection.contains(new Object()));
    	assertTrue(this.collection.contains(this.factory.createPoint(2, 2, 0)));
    	assertTrue(this.collection.contains(this.factory.createPoint(6, 5, 0)));
    	assertFalse(this.collection.contains(this.factory.createPoint(-1, 6, 0)));
    }

    /**
     */
	@Test
    public void iterator() {
    	Point3D p;
    	Iterator<P> iterator = this.collection.iterator();
    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertEpsilonEquals(1, p.getX());
    	assertEpsilonEquals(1, p.getY());
    	assertEpsilonEquals(0, p.getZ());
    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertEpsilonEquals(2, p.getX());
    	assertEpsilonEquals(2, p.getY());
    	assertEpsilonEquals(0, p.getZ());
    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertEpsilonEquals(3, p.getX());
    	assertEpsilonEquals(3, p.getX());
    	assertEpsilonEquals(0, p.getZ());
    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertEpsilonEquals(4, p.getX());
    	assertEpsilonEquals(3, p.getY());
    	assertEpsilonEquals(0, p.getZ());
    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertEpsilonEquals(5, p.getX());
    	assertEpsilonEquals(-1, p.getY());
    	assertEpsilonEquals(0, p.getZ());
    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertEpsilonEquals(6, p.getX());
    	assertEpsilonEquals(5, p.getY());
    	assertEpsilonEquals(0, p.getZ());
    	assertTrue(iterator.hasNext());
    	p = iterator.next();
    	assertEpsilonEquals(7, p.getX());
    	assertEpsilonEquals(-5, p.getY());
    	assertEpsilonEquals(0, p.getZ());
    	assertFalse(iterator.hasNext());
    }

    /**
     */
	@Test
    public void toArray() {
    	Object[] tab = this.collection.toArray();
    	assertEquals(7, tab.length);
    	assertTrue(tab[0] instanceof Point3D);
    	assertEpsilonEquals(1, ((Point3D) tab[0]).getX());
    	assertEpsilonEquals(1, ((Point3D) tab[0]).getY());
    	assertEpsilonEquals(0, ((Point3D) tab[0]).getZ());
    	assertTrue(tab[1] instanceof Point3D);
    	assertEpsilonEquals(2, ((Point3D) tab[1]).getX());
    	assertEpsilonEquals(2, ((Point3D) tab[1]).getY());
    	assertEpsilonEquals(0, ((Point3D) tab[1]).getZ());
    	assertTrue(tab[2] instanceof Point3D);
    	assertEpsilonEquals(3, ((Point3D) tab[2]).getX());
    	assertEpsilonEquals(0, ((Point3D) tab[2]).getY());
    	assertEpsilonEquals(0, ((Point3D) tab[2]).getZ());
    	assertTrue(tab[3] instanceof Point3D);
    	assertEpsilonEquals(4, ((Point3D) tab[3]).getX());
    	assertEpsilonEquals(3, ((Point3D) tab[3]).getY());
    	assertEpsilonEquals(0, ((Point3D) tab[3]).getZ());
    	assertTrue(tab[4] instanceof Point3D);
    	assertEpsilonEquals(5, ((Point3D) tab[4]).getX());
    	assertEpsilonEquals(-1, ((Point3D) tab[4]).getY());
    	assertEpsilonEquals(0, ((Point3D) tab[4]).getZ());
    	assertTrue(tab[5] instanceof Point3D);
    	assertEpsilonEquals(6, ((Point3D) tab[5]).getX());
    	assertEpsilonEquals(5, ((Point3D) tab[5]).getY());
    	assertEpsilonEquals(0, ((Point3D) tab[5]).getZ());
    	assertTrue(tab[6] instanceof Point3D);
    	assertEpsilonEquals(7, ((Point3D) tab[6]).getX());
    	assertEpsilonEquals(-5, ((Point3D) tab[6]).getY());
    	assertEpsilonEquals(0, ((Point3D) tab[6]).getZ());
    }

    /**
     */
	@Test
    public void toArrayArray() {
    	Point3D[] tab = new Point3D[5];
    	Point3D[] tab2 = this.collection.toArray(tab);
    	assertSame(tab, tab2);
    	assertEquals(5, tab.length);
    	assertEpsilonEquals(1, tab[0].getX());
    	assertEpsilonEquals(1, tab[0].getY());
    	assertEpsilonEquals(0, tab[0].getZ());
    	assertEpsilonEquals(2, tab[1].getX());
    	assertEpsilonEquals(2, tab[1].getY());
    	assertEpsilonEquals(0, tab[1].getZ());
    	assertEpsilonEquals(3, tab[2].getX());
    	assertEpsilonEquals(0, tab[2].getY());
    	assertEpsilonEquals(0, tab[2].getZ());
    	assertEpsilonEquals(4, tab[3].getX());
    	assertEpsilonEquals(3, tab[3].getY());
    	assertEpsilonEquals(0, tab[3].getZ());
    	assertEpsilonEquals(5, tab[4].getX());
    	assertEpsilonEquals(-1, tab[4].getY());
    	assertEpsilonEquals(0, tab[4].getZ());
    }

    /**
     */
	@Test
    public void add() {
    	assertTrue(this.collection.add(this.factory.createPoint(123, 456, 0)));
    	assertCoords(1, 1, 0, 2, 2, 0, 3, 0, 0, 4, 3, 0, 5, -1, 0, 6, 5, 0, 7, -5, 0, 123, 456, 0);
    	this.shape.clear();
    	assertCoords();
    	assertTrue(this.collection.add(this.factory.createPoint(123, 456, 0)));
    	assertCoords(123, 456, 0);
    	assertTrue(this.collection.add(this.factory.createPoint(789, 1011, 0)));
    	assertCoords(123, 456, 0, 789, 1011, 0);
    }

    /**
     */
	@Test
    public void remove() {
    	assertFalse(this.collection.remove(new Object()));
    	assertTrue(this.collection.remove(this.factory.createPoint(2, 2, 0)));
    	assertCoords(1, 1, 0, 3, 0, 0, 4, 3, 0, 5, -1, 0, 6, 5, 0, 7, -5, 0);
    	assertTrue(this.collection.remove(this.factory.createPoint(6, 5, 0)));
    	assertCoords(1, 1, 0, 3, 0, 0, 4, 3, 0);
    }


    /**
     */
	@Test
    public void containsAll() {
    	assertTrue(this.collection.containsAll(
    			Arrays.asList(this.factory.createPoint(1, 1, 0), this.factory.createPoint(6, 5, 0))));
    	assertFalse(this.collection.containsAll(
    			Arrays.asList(this.factory.createPoint(1, 1, 0), this.factory.createPoint(6, 6, 0))));
    }

    /**
     */
	@Test
    public void addAll() {
    	this.collection.addAll(
    			Arrays.asList(this.factory.createPoint(123, 456, 0), this.factory.createPoint(789, 1011, 0)));
    	assertCoords(1, 1, 0, 2, 2, 0, 3, 0, 0, 4, 3, 0, 5, -1, 0, 6, 5, 0, 7, -5, 0, 123, 456, 0, 789, 1011, 0);
    }

    /**
     */
	@Test
    public void removeAll() {
    	this.collection.removeAll(
    			Arrays.asList(this.factory.createPoint(123, 456, 0), this.factory.createPoint(2, 2, 0)));
    	assertCoords(1, 1, 0, 3, 0, 0, 4, 3, 0, 5, -1, 0, 6, 5, 0, 7, -5, 0);
    }

    /**
     */
	@Test
    public void retainAll() {
    	try {
    		this.collection.retainAll(Collections.emptyList());
    		fail("Expecting an exception"); //$NON-NLS-1$
    	}
    	catch(Throwable e1) {
    		e1.equals(e1);
    		// Expecting an exception
    	}
    }

    /**
     */
	@Test
    public void clear() {
    	this.collection.clear();
    	assertCoords();
    }
    
}
