/* 
 * $Id$
 * 
 * Copyright (C) 2010-2012 Stephane GALLAND.
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
package org.arakhne.afc.math.geometry.d2;

/** Unmodifiable 2D Vector.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public interface UnmodifiableVector2D extends UnmodifiableTuple2D<Vector2D>, Vector2D {

	@Override
	default void add(Vector2D vector1, Vector2D vector2) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void add(Vector2D vector) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void scaleAdd(int scale, Vector2D vector1, Vector2D vector2) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void scaleAdd(double scale, Vector2D vector1, Vector2D vector2) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void scaleAdd(int scale, Vector2D vector) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void scaleAdd(double scale, Vector2D vector) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void sub(Vector2D vector1, Vector2D vector2) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void sub(Point2D point1, Point2D point2) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void sub(Vector2D vector1) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void perpendicularize() {
		throw new UnsupportedOperationException();
	}

	@Override
	default void normalize(Vector2D vector) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void normalize() {
		throw new UnsupportedOperationException();
	}


	@Override
	default void turn(double angle) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void turnLeft(double angle) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void turnRight(double angle) {
		throw new UnsupportedOperationException();
	}

	@Override
	default void setLength(double newLength) {
		throw new UnsupportedOperationException();
	}

	@Override
	default Vector2D toUnmodifiable() {
		return this;
	}

}
