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

package org.arakhne.afc.math.geometry.d2.i;

import org.eclipse.xtext.xbase.lib.Pure;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.ai.Circle2ai;

/** A circle with 2 integer numbers.
 *
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public class Circle2i extends AbstractShape2i<Circle2i>
		implements Circle2ai<Shape2i<?>, Circle2i, PathElement2i, Point2i, Vector2i, Rectangle2i> {

	private static final long serialVersionUID = -7692549016859323986L;

	private int centerX;

	private int centerY;

	private int radius;

	/** Construct an empty circle.
	 */
	public Circle2i() {
		//
	}

	/** Construct a circle at the given position and with the given radius.
	 * @param center the center position of the circle.
	 * @param radius the radius of the circle.
	 */
	public Circle2i(Point2D<?, ?> center, int radius) {
		assert center != null : "Center point must be not null"; //$NON-NLS-1$
		set(center.ix(), center.iy(), radius);
	}

	/** Construct a circle at the given position and with the given radius.
	 * @param x x coordinat eof the the center position of the circle.
	 * @param y y coordinat eof the the center position of the circle.
	 * @param radius the radius of the circle.
	 */
	public Circle2i(int x, int y, int radius) {
		set(x, y, radius);
	}

	/** Construct a circle from a circle.
	 * @param circle the circle to copy.
	 */
	public Circle2i(Circle2ai<?, ?, ?, ?, ?, ?> circle) {
		assert circle != null : "Circle must be not null"; //$NON-NLS-1$
		set(circle.getX(), circle.getY(), circle.getRadius());
	}

	@Pure
	@Override
	public int hashCode() {
		int bits = 1;
		bits = 31 * bits + this.centerX;
		bits = 31 * bits + this.centerY;
		bits = 31 * bits + this.radius;
		return bits ^ (bits >> 32);
	}

	@Pure
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		b.append("["); //$NON-NLS-1$
		b.append(getX());
		b.append(";"); //$NON-NLS-1$
		b.append(getY());
		b.append(";"); //$NON-NLS-1$
		b.append(getRadius());
		b.append("]"); //$NON-NLS-1$
		return b.toString();
	}

	@Pure
	@Override
	public int getX() {
		return this.centerX;
	}

	@Pure
	@Override
	public int getY() {
		return this.centerY;
	}

	@Override
	public void setX(int x) {
		if (this.centerX != x) {
			this.centerX = x;
			fireGeometryChange();
		}
	}

	@Override
	public void setY(int y) {
		if (this.centerY != y) {
			this.centerY = y;
			fireGeometryChange();
		}
	}

	@Pure
	@Override
	public int getRadius() {
		return this.radius;
	}

	@Override
	public void setRadius(int radius) {
		assert radius >= 0 : "Radius must be positive or equal"; //$NON-NLS-1$
		if (this.radius != radius) {
			this.radius = radius;
			fireGeometryChange();
		}
	}

	@Override
	public void set(int x, int y, int radius) {
		assert radius >= 0 : "Radius must be positive or equal"; //$NON-NLS-1$
		if (this.centerX != x || this.centerY != y || this.radius != radius) {
			this.centerX = x;
			this.centerY = y;
			this.radius = radius;
			fireGeometryChange();
		}
	}

}