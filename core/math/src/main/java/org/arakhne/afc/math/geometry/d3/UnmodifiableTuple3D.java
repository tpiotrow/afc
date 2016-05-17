/* 
 * $Id$
 * 
 * Copyright (C) 2010-2016 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package org.arakhne.afc.math.geometry.d3;

import org.arakhne.afc.math.geometry.d2.Tuple2D;

/** TODO
 * @author $Author: tpiotrow$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public interface UnmodifiableTuple3D<RT extends Tuple3D<? super RT>> extends Tuple3D<RT> {
	@Override
	default void absolute() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void absolute(Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void add(int x, int y, int z) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void add(double x, double y, double z) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void addX(int x) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void addX(double x) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void addY(int y) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void addY(double y) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void addZ(int z) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void addZ(double z) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void clamp(int min, int max) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clamp(double min, double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clampMin(int min) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clampMin(double min) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clampMax(int max) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clampMax(double max) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clamp(int min, int max, Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clamp(double min, double max, Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clampMin(int min, Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clampMin(double min, Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clampMax(int max, Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void clampMax(double max, Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void negate(Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void negate() {
		throw new UnsupportedOperationException();
	}

	@Override
	default void scale(int scale, Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void scale(double scale, Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void scale(int scale) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void scale(double scale) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void set(Tuple3D<?> tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void set(int x, int y, int z) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void set(double x, double y, double z) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void set(int[] tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void set(double[] tuple) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void setX(int x) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void setX(double x) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setY(int y) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void setY(double y) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void setZ(int z) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void setZ(double z) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void sub(int x, int y, int z) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void sub(double x, double y, double z) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void subX(int x) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void subX(double x) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void subY(int y) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	default void subY(double y) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void subZ(int z) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void subZ(double z) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void interpolate(Tuple3D<?> tuple1, Tuple3D<?> tuple2, double alpha) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void interpolate(Tuple3D<?> tuple, double alpha) {
		throw new UnsupportedOperationException();
	} 

}
