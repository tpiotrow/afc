/* 
 * $Id$
 * 
 * Copyright (C) 2011 Janus Core Developers
 * Copyright (C) 2012-13 Stephane GALLAND.
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
package org.arakhne.afc.math.geometry.d2.i;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.arakhne.afc.math.geometry.d2.ai.AbstractPath2aiTest;
import org.arakhne.afc.math.geometry.d2.ai.Circle2ai;

@SuppressWarnings("all")
public class Path2iTest extends AbstractPath2aiTest<Path2i, Rectangle2i> {

	@Override
	protected Segment2i createSegment(int x1, int y1, int x2, int y2) {
		return new Segment2i(x1, y1, x2, y2);
	}

	@Override
	protected Point2D createPoint(int x, int y) {
		return new Point2i(x, y);
	}

	@Override
	protected Vector2D createVector(int x, int y) {
		return new Vector2i(x, y);
	}

	@Override
	protected Rectangle2i createRectangle(int x, int y, int width, int height) {
		return new Rectangle2i(x, y, width, height);
	}

	@Override
	protected Circle2ai<?, ?, ?, ?, Rectangle2i> createCircle(int x, int y, int radius) {
		return new Circle2i(x, y, radius);
	}

	@Override
	protected Path2i createPath() {
		return new Path2i();
	}

}