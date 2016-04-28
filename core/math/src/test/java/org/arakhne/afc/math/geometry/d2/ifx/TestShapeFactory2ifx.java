/* 
 * $Id$
 * 
 * Copyright (c) 2006-10, Multiagent Team, Laboratoire Systemes et Transports, Universite de Technologie de Belfort-Montbeliard.
 * Copyright (C) 2012 Stephane GALLAND.
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
package org.arakhne.afc.math.geometry.d2.ifx;

import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.arakhne.afc.math.geometry.d2.ai.Circle2ai;
import org.arakhne.afc.math.geometry.d2.ai.Path2ai;
import org.arakhne.afc.math.geometry.d2.ai.Segment2ai;
import org.arakhne.afc.math.geometry.d2.ai.TestShapeFactory;

@SuppressWarnings("all")
public class TestShapeFactory2ifx implements TestShapeFactory<Rectangle2ifx> {
	
	public static final TestShapeFactory2ifx SINGLETON = new TestShapeFactory2ifx();
	
	public Segment2ai<?, ?, ?, ?, ?, Rectangle2ifx> createSegment(int x1, int y1, int x2, int y2) {
		return new Segment2ifx(x1, y1, x2, y2);
	}
	
	public Rectangle2ifx createRectangle(int x, int y, int width, int height) {
		return new Rectangle2ifx(x, y, width, height);
	}

	public Circle2ai<?, ?, ?, ?, ?, Rectangle2ifx> createCircle(int x, int y, int radius) {
		return new Circle2ifx(x, y, radius);
	}
	
	public Point2D createPoint(int x, int y) {
		return new Point2ifx(x, y);
	}

	public Vector2D createVector(int x, int y) {
		return new Vector2ifx(x, y);
	}

	public Path2ai<?, ?, ?, ?, ?, Rectangle2ifx> createPath() {
		return new Path2ifx();
	}
	
}