/**
 * 
 * fr.utbm.v3g.core.math.Tuple3dTest.java
 *
 * Copyright (c) 2008-10, Multiagent Team - Systems and Transportation Laboratory (SeT)
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of the Systems and Transportation Laboratory ("Confidential Information").
 * You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with the SeT.
 * 
 * http://www.multiagent.fr/
 *
 * Primary author : Olivier LAMOTTE (olivier.lamotte@utbm.fr) - 2015
 *
 */
package org.arakhne.afc.math.geometry.d3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.arakhne.afc.math.AbstractMathTestCase;
import org.arakhne.afc.math.geometry.coordinatesystem.CoordinateSystem2DTestRule;
import org.arakhne.afc.math.geometry.d2.d.Tuple3d;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

@SuppressWarnings("all")
public abstract class AbstractUnmodifiableTuple3DTest<T extends Tuple3D, TT extends Tuple3D> extends AbstractTuple3DTest<T, TT> {
	
	@Test(expected = UnsupportedOperationException.class)
	public final void absolute() {
		getT().absolute();
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void absoluteT() {
		Tuple3D c = new Tuple3d();
		getT().absolute(c);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addIntInt() {
		getT().add(6, 7);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addDoubleDouble() {
		getT().add(6.5, 7.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addXInt() {
		getT().addX(6);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addXDouble() {
		getT().addX(6.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addYInt() {
		getT().addY(6);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addYDouble() {
		getT().addY(6.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void negateT() {
		Tuple3D c = new Tuple3d();
		getT().negate(c);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void negate() {
		getT().negate();
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void scaleIntT() {
		Tuple3D c = new Tuple3d(2, -1);
		getT().scale(4, c);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void scaleDoubleT() {
		Tuple3D c = new Tuple3d(2, -1);
		getT().scale(4.5, c);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void scaleInt() {
		getT().scale(4);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void scaleDouble() {
		getT().scale(4.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setTuple3D() {
		Tuple3D c = new Tuple3d(-45, 78);
		getT().set(c);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setIntInt() {
		getT().set(-45, 78);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setDoubleDouble() {
		getT().set(-45.5, 78.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setIntArray() {
		getT().set(new int[]{-45, 78});
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setDoubleArray() {
		getT().set(new double[]{-45.5, 78.5});
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setXInt() {
		getT().setX(45);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setXDouble() {
		getT().setX(45.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setYInt() {
		getT().setY(45);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setYDouble() {
		getT().setY(45.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subIntInt() {
		getT().sub(45, 78);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subXInt() {
		getT().subX(45);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subYInt() {
		getT().subY(78);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subDoubleDouble() {
		getT().sub(45.5, 78.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subXDouble() {
		getT().subX(45.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subYDouble() {
		getT().subY(78.5);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addDoubleDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().add(12.3, 4.56);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addDoubleDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().add(12.3, 4.56);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addXDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().addX(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addXDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().addX(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addYDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().addY(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void addYDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().addY(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void scaleDoubleT_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().scale(12.3, createTuple(1,2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void scaleDoubleT_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().scale(12.3, createTuple(1,2));
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void scaleDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().scale(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void scaleDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().scale(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setDoubleDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().set(12.3, 4.56);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setDoubleDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().set(12.3, 4.56);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setDoubleArray_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().set(new double[] {12.3, 4.56});
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setDoubleArray_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().set(new double[] {12.3, 4.56});
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setXDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().setX(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setXDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().setX(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setYDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().setY(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void setYDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().setY(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subDoubleDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().sub(12.3, 4.56);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subDoubleDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().sub(12.3, 4.56);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subXDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().subX(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subXDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().subX(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subYDouble_iffp() {
		Assume.assumeFalse(isIntCoordinates());
		getT().subY(12.3);
	}

	@Test(expected = UnsupportedOperationException.class)
	public final void subYDouble_ifi() {
		Assume.assumeTrue(isIntCoordinates());
		getT().subY(12.3);
	}

}
