/* 
 * $Id$
 * 
 * Copyright (C) 2010-2013 Stephane GALLAND.
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

package org.arakhne.afc.math.geometry.d2.afp;

import java.util.NoSuchElementException;

import org.arakhne.afc.math.MathConstants;
import org.arakhne.afc.math.MathUtil;
import org.arakhne.afc.math.Unefficient;
import org.arakhne.afc.math.geometry.PathWindingRule;
import org.arakhne.afc.math.geometry.d2.Point2D;
import org.arakhne.afc.math.geometry.d2.Transform2D;
import org.arakhne.afc.math.geometry.d2.Vector2D;
import org.arakhne.afc.math.geometry.d2.afp.Circle2afp.AbstractCirclePathIterator;
import org.arakhne.afc.math.geometry.d2.afp.Path2afp.CrossingComputationType;
import org.eclipse.xtext.xbase.lib.Pure;

/** Fonctional interface that represented a 2D round rectangle on a plane.
 *
 * @param <ST> is the type of the general implementation.
 * @param <IT> is the type of the implementation of this shape.
 * @param <IE> is the type of the path elements.
 * @param <P> is the type of the points.
 * @param <V> is the type of the vectors.
 * @param <B> is the type of the bounding boxes.
 * @author $Author: sgalland$
 * @author $Author: hjaffali$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
public interface RoundRectangle2afp<
		ST extends Shape2afp<?, ?, IE, P, V, B>,
		IT extends RoundRectangle2afp<?, ?, IE, P, V, B>,
		IE extends PathElement2afp,
		P extends Point2D<? super P, ? super V>,
		V extends Vector2D<? super V, ? super P>,
		B extends Rectangle2afp<?, ?, IE, P, V, B>>
		extends RectangularShape2afp<ST, IT, IE, P, V, B> {

	/** Replies if a rectangle is inside in the round rectangle.
	 * 
	 * @param rx1 is the lowest corner of the round rectangle.
	 * @param ry1 is the lowest corner of the round rectangle.
	 * @param rwidth1 is the width of the round rectangle.
	 * @param rheight1 is the height of the round rectangle.
	 * @param awidth is the width of the arc of the round rectangle.
	 * @param aheight is the height of the arc of the round rectangle.
	 * @param rx2 is the lowest corner of the inner-candidate rectangle.
	 * @param ry2 is the lowest corner of the inner-candidate rectangle.
	 * @param rwidth2 is the width of the inner-candidate rectangle.
	 * @param rheight2 is the height of the inner-candidate rectangle.
	 * @return <code>true</code> if the given rectangle is inside the ellipse;
	 * otherwise <code>false</code>.
	 */
	@Pure
	static boolean containsRoundRectangleRectangle(double rx1, double ry1, double rwidth1, double rheight1,
			double awidth, double aheight, double rx2, double ry2, double rwidth2, double rheight2) {
		assert (rwidth1 >= 0.) : "Round rectangle width must be positive or zero"; //$NON-NLS-1$
		assert (rheight1 >= 0.) : "Round rectangle height must be positive or zero"; //$NON-NLS-1$
		assert (awidth >= 0.) : "Round rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (aheight >= 0.) : "Round rectangle arc height must be positive or zero"; //$NON-NLS-1$
		assert (rwidth2 >= 0.) : "Rectangle width must be positive or zero"; //$NON-NLS-1$
		assert (rheight2 >= 0.) : "Rectangle height must be positive or zero"; //$NON-NLS-1$
		double rcx1 = rx1 + rwidth1 / 2;
		double rcy1 = ry1 + rheight1 / 2;
		double rcx2 = rx2 + rwidth2 / 2;
		double rcy2 = ry2 + rheight2 / 2;
		double farX; 
		if (rcx1 <= rcx2) {
			farX = rx2 + rwidth2;
		} else {
			farX = rx2;
		}
		double farY;
		if (rcy1 <= rcy2) {
			farY = ry2 + rheight2;
		} else {
			farY = ry2;
		}
		return containsRoundRectanglePoint(rx1, ry1, rwidth1, rheight1, awidth, aheight, farX, farY);
	}

	/** Replies if a point is inside in the round rectangle.
	 * 
	 * @param rx is the lowest corner of the round rectangle.
	 * @param ry is the lowest corner of the round rectangle.
	 * @param rwidth is the width of the round rectangle.
	 * @param rheight is the height of the round rectangle.
	 * @param awidth is the width of the arc of the round rectangle.
	 * @param aheight is the height of the arc of the round rectangle.
	 * @param px is the point.
	 * @param py is the point.
	 * @return <code>true</code> if the given rectangle is inside the ellipse;
	 * otherwise <code>false</code>.
	 */
	@Pure
	static boolean containsRoundRectanglePoint(double rx, double ry, double rwidth, double rheight, double awidth, double aheight, double px, double py) {
		assert (rwidth >= 0.) : "Round rectangle width must be positive or zero"; //$NON-NLS-1$
		assert (rheight >= 0.) : "Round rectangle height must be positive or zero"; //$NON-NLS-1$
		assert (awidth >= 0.) : "Round rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (aheight >= 0.) : "Round rectangle arc height must be positive or zero"; //$NON-NLS-1$
		if (rwidth<=0 && rheight<=0) {
			return rx==px && ry==py;
		}
		double rx0 = rx;
		double ry0 = ry;
		double rrx1 = rx0 + rwidth;
		double rry1 = ry0 + rheight;
		// Check for trivial rejection - point is outside bounding rectangle
		if (px < rx0 || py < ry0 || px >= rrx1 || py >= rry1) {
			return false;
		}
		double aw = Math.min(rwidth, Math.abs(awidth)) / 2;
		double ah = Math.min(rheight, Math.abs(aheight)) / 2;
		// Check which corner point is in and do circular containment
		// test - otherwise simple acceptance
		if (px >= (rx0 += aw) && px < (rx0 = rrx1 - aw)) {
			return true;
		}
		if (py >= (ry0 += ah) && py < (ry0 = rry1 - ah)) {
			return true;
		}
		double xx = (px - rx0) / aw;
		double yy = (py - ry0) / ah;
		return (xx * xx + yy * yy <= 1);
	}

	/** Replies if the round rectangle is intersecting the segment.
	 * 
	 * @param rx1 is the first corner of the rectangle.
	 * @param ry1 is the first corner of the rectangle.
	 * @param rx2 is the second corner of the rectangle.
	 * @param ry2 is the second corner of the rectangle.
	 * @param aw is the width of the arcs of the rectangle.
	 * @param ah is the height of the arcs of the rectangle.
	 * @param sx1 is the first point of the segment.
	 * @param sy1 is the first point of the segment.
	 * @param sx2 is the second point of the segment.
	 * @param sy2 is the second point of the segment.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	@Pure
	static boolean intersectsRoundRectangleSegment(double rx1, double ry1, double rx2, double ry2, double aw, double ah, double sx1, double sy1, double sx2, double sy2) {
		assert (rx1 <= rx2) : "rx1 must be lower or equal rx2"; //$NON-NLS-1$
		assert (ry1 <= ry2) : "ry1 must be lower or equal ry2"; //$NON-NLS-1$
		assert (aw >= 0.) : "Round rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (ah >= 0.) : "Round rectangle arc height must be positive or zero"; //$NON-NLS-1$
		double segmentX1 = sx1;
		double segmentY1 = sy1;
		double segmentX2 = sx2;
		double segmentY2 = sy2;
		
		int code1 = MathUtil.getCohenSutherlandCode(segmentX1, segmentY1, rx1, ry1, rx2, ry2);
		int code2 = MathUtil.getCohenSutherlandCode(segmentX2, segmentY2, rx1, ry1, rx2, ry2);

		while (true) {
			if ((code1 | code2) == 0) {
				// Bitwise OR is 0. Trivially accept and get out of loop
				// Special case: if the segment is empty, it is on the border => no intersection.
				if (segmentX1 != segmentX2 || segmentY1 != segmentY2) {
					// Special case: intersecting outside the corner's ellipse.
					double ellipseMinX = rx1 + aw; 
					if (segmentX1 <= ellipseMinX && segmentX2 <= ellipseMinX) {
						double ellipseMinY = ry1 + ah;
						if (segmentY1 <= ellipseMinY && segmentY2 <= ellipseMinY) {
							return Ellipse2afp.intersectsEllipseSegment(
									rx1, ry1, aw * 2, ah * 2,
									sx1, sy1, sx2, sy2, false);
						}
						double ellipseMaxY = ry2 - ah;
						if (segmentY1 >= ellipseMaxY && segmentY2 >= ellipseMaxY) {
							double ellipseHeight = ah * 2;
							return Ellipse2afp.intersectsEllipseSegment(
									rx1, ry1 - ellipseHeight, aw * 2, ellipseHeight,
									sx1, sy1, sx2, sy2, false);
						}
					} else {
						double ellipseMaxX = rx2 - aw;
						if (segmentX1 >= ellipseMaxX && segmentX2 >= ellipseMaxX) {
							double ellipseMinY = ry1 + ah;
							double ellipseWidth = aw * 2;
							if (segmentY1 <= ellipseMinY && segmentY2 <= ellipseMinY) {
								return Ellipse2afp.intersectsEllipseSegment(
										rx2 - ellipseWidth, ry1, ellipseWidth, ah * 2,
										sx1, sy1, sx2, sy2, false);
							}
							double ellipseMaxY = ry2 - ah;
							if (segmentY1 >= ellipseMaxY && segmentY2 >= ellipseMaxY) {
								double ellipseHeight = ah * 2;
								return Ellipse2afp.intersectsEllipseSegment(
										rx2 - ellipseWidth, ry2 - ellipseHeight, ellipseWidth, ellipseHeight,
										sx1, sy1, sx2, sy2, false);
							}
						}
					}
					return true;
				}
				return false;
			}
			if ((code1 & code2) != 0) {
				// Bitwise AND is not 0. Trivially reject and get out of loop
				return false;
			}

			// failed both tests, so calculate the line segment intersection

			// At least one endpoint is outside the clip rectangle; pick it.
			int code3 = (code1 != 0) ? code1 : code2;

			double x = 0;
			double y = 0;
			
			// Now find the intersection point;
			// use formulas y = y0 + slope * (x - x0), x = x0 + (1 / slope) * (y - y0)
			if ((code3 & MathConstants.COHEN_SUTHERLAND_TOP) != 0) {
				// point is above the clip rectangle
				x = segmentX1 + (segmentX2 - segmentX1) * (ry2 - segmentY1) / (segmentY2 - segmentY1);
				y = ry2;
			}
			else if ((code3 & MathConstants.COHEN_SUTHERLAND_BOTTOM) != 0) {
				// point is below the clip rectangle
				x = segmentX1 + (segmentX2 - segmentX1) * (ry1 - segmentY1) / (segmentY2 - segmentY1);
				y = ry1;
			}
			else if ((code3 & MathConstants.COHEN_SUTHERLAND_RIGHT) != 0) { 
				// point is to the right of clip rectangle
				y = segmentY1 + (segmentY2 - segmentY1) * (rx2 - segmentX1) / (segmentX2 - segmentX1);
				x = rx2;
			}
			else if ((code3 & MathConstants.COHEN_SUTHERLAND_LEFT) != 0) {
				// point is to the left of clip rectangle
				y = segmentY1 + (segmentY2 - segmentY1) * (rx1 - segmentX1) / (segmentX2 - segmentX1);
				x = rx1;
			}
			else {
				code3 = 0;
			}

			if (code3 != 0) {
				// Now we move outside point to intersection point to clip
				// and get ready for next pass.
				if (code3 == code1) {
					segmentX1 = x;
					segmentY1 = y;
					code1 = MathUtil.getCohenSutherlandCode(segmentX1, segmentY1, rx1, ry1, rx2, ry2);
				}
				else {
					segmentX2 = x;
					segmentY2 = y;
					code2 = MathUtil.getCohenSutherlandCode(segmentX2, segmentY2, rx1, ry1, rx2, ry2);
				}
			}
		}
	}

	/** Replies if two round rectangles are intersecting.
	 * 
	 * @param r1x1 is the first corner of the first rectangle.
	 * @param r1y1 is the first corner of the first rectangle.
	 * @param r1x2 is the second corner of the first rectangle.
	 * @param r1y2 is the second corner of the first rectangle.
	 * @param r1aw is the width of the arcs of the first rectangle.
	 * @param r1ah is the height of the arcs of the first rectangle.
	 * @param r2x1 is the first corner of the second rectangle.
	 * @param r2y1 is the first corner of the second rectangle.
	 * @param r2x2 is the second corner of the second rectangle.
	 * @param r2y2 is the second corner of the second rectangle.
	 * @param r2aw is the width of the arcs of the second rectangle.
	 * @param r2ah is the height of the arcs of the second rectangle.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	@Pure
	@Unefficient
	static boolean intersectsRoundRectangleRoundRectangle(double r1x1, double r1y1, double r1x2,
			double r1y2, double r1aw, double r1ah, 
			double r2x1, double r2y1, double r2x2, double r2y2, double r2aw, double r2ah) {
		assert (r1x1 <= r1x2) : "r1x1 must be lower or equal r1x2"; //$NON-NLS-1$
		assert (r1y1 <= r1y2) : "r1y1 must be lower or equal r1y2"; //$NON-NLS-1$
		assert (r1aw >= 0.) : "First round rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (r1ah >= 0.) : "First round rectangle arc height must be positive or zero"; //$NON-NLS-1$
		assert (r2x1 <= r2x2) : "r2x1 must be lower or equal r2x2"; //$NON-NLS-1$
		assert (r2y1 <= r2y2) : "r2y1 must be lower or equal r2y2"; //$NON-NLS-1$
		assert (r2aw >= 0.) : "Second round rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (r2ah >= 0.) : "Second round rectangle arc height must be positive or zero"; //$NON-NLS-1$
		if (Rectangle2afp.intersectsRectangleRectangle(r1x1, r1y1, r1x2, r1y2, r2x1, r2y1, r2x2, r2y2)) {
			// Boundings rectangles are intersecting
			
			// Test internal rectangles
			double r1InnerMinX = r1x1 + r1aw;
			double r1InnerMaxX = r1x2 - r1aw;
			double r1InnerMinY = r1y1 + r1ah;
			double r1InnerMaxY = r1y2 - r1ah;
			double r2InnerMinX = r2x1 + r2aw;
			double r2InnerMaxX = r2x2 - r2aw;
			double r2InnerMinY = r2y1 + r2ah;
			double r2InnerMaxY = r2y2 - r2ah;
			if (Rectangle2afp.intersectsRectangleRectangle(
					r1InnerMinX, r1y1, r1InnerMaxX, r1y2,
					r2InnerMinX, r2y1, r2InnerMaxX, r2y2)
				|| Rectangle2afp.intersectsRectangleRectangle(
						r1InnerMinX, r1y1, r1InnerMaxX, r1y2,
						r2x1, r2InnerMinY, r2x2, r2InnerMaxY)
				|| Rectangle2afp.intersectsRectangleRectangle(
						r1x1, r1InnerMinY, r1x2, r1InnerMaxY,
						r2InnerMinX, r2y1, r2InnerMaxX, r2y2)
				|| Rectangle2afp.intersectsRectangleRectangle(
						r1x1, r1InnerMinY, r1x2, r1InnerMaxY,
						r2x1, r2InnerMinY, r2x2, r2InnerMaxY)) {
				return true;
			}
			
			// Test closest corner ellipses
			double ex1, ey1;
			double ex2, ey2;
			
			if (r1InnerMaxX <= r2x1) {
				ex1 = r1InnerMaxX - r1aw;
				ex2 = r2x1;
			} else {
				ex1 = r1x1;
				ex2 = r2InnerMaxX - r2aw;
			}
			
			if (r1InnerMaxY < r2y1) {
				ey1 = r1InnerMaxY - r1ah;
				ey2 = r2y1;
			} else {
				ey1 = r1y1;
				ey2 = r2InnerMaxY - r2ah;
			}

			return Ellipse2afp.intersectsEllipseEllipse(
					ex1, ey1, r1aw * 2, r1ah * 2,
					ex2, ey2, r2aw * 2, r2ah * 2);
		}
		return false;
	}

	/** Replies if a round rectangle and a circle are intersecting.
	 * 
	 * @param rx1 is the first corner of the rectangle.
	 * @param ry1 is the first corner of the rectangle.
	 * @param rx2 is the second corner of the rectangle.
	 * @param ry2 is the second corner of the rectangle.
	 * @param aw is the width of the arcs of the rectangle.
	 * @param ah is the height of the arcs of the rectangle.
	 * @param cx is the center of the circle.
	 * @param cy is the center of the circle.
	 * @param radius is the radius of the circle.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	@Pure
	@Unefficient
	static boolean intersectsRoundRectangleCircle(double rx1, double ry1, double rx2,
			double ry2, double aw, double ah, 
			double cx, double cy, double radius) {
		assert (rx1 <= rx2) : "rx1 must be lower or equal rx2"; //$NON-NLS-1$
		assert (ry1 <= ry2) : "ry1 must be lower or equal ry2"; //$NON-NLS-1$
		assert (aw >= 0.) : "Round rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (ah >= 0.) : "Round rectangle arc height must be positive or zero"; //$NON-NLS-1$
		assert (radius >= 0.) : "Circle radius must be positive or zero"; //$NON-NLS-1$
		
		double rInnerMinX = rx1 + aw;
		double rInnerMaxX = rx2 - aw;
		double rInnerMinY = ry1 + ah;
		double rInnerMaxY = ry2 - ah;
		
		if (cx < rInnerMinX) {
			if (cy < rInnerMinY) {
				return Ellipse2afp.intersectsEllipseCircle(rx1, ry1, aw * 2, ah * 2, cx, cy, radius);
			}
			if (cy > rInnerMaxY) {
				return Ellipse2afp.intersectsEllipseCircle(rx1, rInnerMaxY - ah, aw * 2, ah * 2, cx, cy, radius);
			}
			return cx > (rx1 - radius) && cx < (rx2 + radius);
		}
		if (cx > rInnerMaxX) {
			if (cy < ry1) {
				return Ellipse2afp.intersectsEllipseCircle(rInnerMaxX - aw, ry1, aw * 2, ah * 2, cx, cy, radius);
			}
			if (cy > rInnerMaxY) {
				return Ellipse2afp.intersectsEllipseCircle(rInnerMaxX - aw, rInnerMaxY - ah, aw * 2, ah * 2, cx, cy, radius);
			}
			return cx > (rx1 - radius) && cx < (rx2 + radius);
		}
		return cy > (ry1 - radius) && cy < (ry2 + radius);
	}

	/** Replies if a round rectangle and a rectangle are intersecting.
	 * 
	 * @param r1x1 is the first corner of the first rectangle.
	 * @param r1y1 is the first corner of the first rectangle.
	 * @param r1x2 is the second corner of the first rectangle.
	 * @param r1y2 is the second corner of the first rectangle.
	 * @param r1aw is the width of the arcs of the first rectangle.
	 * @param r1ah is the height of the arcs of the first rectangle.
	 * @param r2x1 is the first corner of the second rectangle.
	 * @param r2y1 is the first corner of the second rectangle.
	 * @param r2x2 is the second corner of the second rectangle.
	 * @param r2y2 is the second corner of the second rectangle.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	@Pure
	@Unefficient
	static boolean intersectsRoundRectangleRectangle(double r1x1, double r1y1, double r1x2,
			double r1y2, double r1aw, double r1ah, 
			double r2x1, double r2y1, double r2x2, double r2y2) {
		assert (r1x1 <= r1x2) : "r1x1 must be lower or equal r1x2"; //$NON-NLS-1$
		assert (r1y1 <= r1y2) : "r1y1 must be lower or equal r1y2"; //$NON-NLS-1$
		assert (r1aw >= 0.) : "Round rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (r1ah >= 0.) : "Round rectangle arc height must be positive or zero"; //$NON-NLS-1$
		assert (r2x1 <= r2x2) : "r2x1 must be lower or equal r2x2"; //$NON-NLS-1$
		assert (r2y1 <= r2y2) : "r2y1 must be lower or equal r2y2"; //$NON-NLS-1$
		if (Rectangle2afp.intersectsRectangleRectangle(r1x1, r1y1, r1x2, r1y2, r2x1, r2y1, r2x2, r2y2)) {
			// Boundings rectangles are intersecting
			
			// Test internal rectangles
			double r1InnerMinX = r1x1 + r1aw;
			double r1InnerMaxX = r1x2 - r1aw;
			double r1InnerMinY = r1y1 + r1ah;
			double r1InnerMaxY = r1y2 - r1ah;
			if (Rectangle2afp.intersectsRectangleRectangle(
					r1InnerMinX, r1y1, r1InnerMaxX, r1y2,
					r2x1, r2y1, r2x2, r2y2)
				|| Rectangle2afp.intersectsRectangleRectangle(
					r1x1, r1InnerMinY, r1x2, r1InnerMaxY,
					r2x1, r2y1, r2x2, r2y2)) {
				return true;
			}
			
			// Test closest corner ellipses
			double ex1, ey1;
			
			if (r1InnerMaxX <= r2x1) {
				ex1 = r1InnerMaxX - r1aw;
			} else {
				ex1 = r1x1;
			}
			
			if (r1InnerMaxY < r2y1) {
				ey1 = r1InnerMaxY - r1ah;
			} else {
				ey1 = r1y1;
			}

			return Ellipse2afp.intersectsEllipseRectangle(
					ex1, ey1, r1aw * 2, r1ah * 2,
					r2x1, r2y1, r2x2, r2y2);
		}
		return false;
	}

	/** Replies if a round rectangle and an ellipse are intersecting.
	 * 
	 * @param rx1 is the first corner of the rectangle.
	 * @param ry1 is the first corner of the rectangle.
	 * @param rx2 is the second corner of the rectangle.
	 * @param ry2 is the second corner of the rectangle.
	 * @param aw is the width of the arcs of the rectangle.
	 * @param ah is the height of the arcs of the rectangle.
	 * @param ex is the coordinate of the ellipse corner.
	 * @param ey is the coordinate of the ellipse corner.
	 * @param ew is the width of the ellipse.
	 * @param eh is the height of the ellipse.
	 * @return <code>true</code> if the two shapes are intersecting; otherwise
	 * <code>false</code>
	 */
	@Pure
	@Unefficient
	static boolean intersectsRoundRectangleEllipse(double rx1, double ry1, double rx2,
			double ry2, double aw, double ah, 
			double ex, double ey, double ew, double eh) {
		assert (rx1 <= rx2) : "rx1 must be lower or equal rx2"; //$NON-NLS-1$
		assert (ry1 <= ry2) : "ry1 must be lower or equal ry2"; //$NON-NLS-1$
		assert (aw >= 0.) : "Round rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (ah >= 0.) : "Round rectangle arc height must be positive or zero"; //$NON-NLS-1$
		assert (ew >= 0.) : "Ellipse width must be positive or zero"; //$NON-NLS-1$
		assert (eh >= 0.) : "Ellipse height must be positive or zero"; //$NON-NLS-1$
		
		double rInnerMinX = rx1 + aw;
		double rInnerMaxX = rx2 - aw;
		double rInnerMinY = ry1 + ah;
		double rInnerMaxY = ry2 - ah;
		
		double radius1 = ew / 2;
		double radius2 = eh / 2;
		double centerX = ex + radius1;
		double centerY = ey + radius2;
		
		if (centerX < rInnerMinX) {
			if (centerY < rInnerMinY) {
				return Ellipse2afp.intersectsEllipseEllipse(rx1, ry1, aw * 2, ah * 2, ex, ey, ew, eh);
			}
			if (centerY > rInnerMaxY) {
				return Ellipse2afp.intersectsEllipseEllipse(rx1, rInnerMaxY - ah, aw * 2, ah * 2, ex, ey, ew, eh);
			}
			return centerX > (rx1 - radius1) && centerX < (rx2 + radius1);
		}
		if (centerX > rInnerMaxX) {
			if (centerY < ry1) {
				return Ellipse2afp.intersectsEllipseEllipse(rInnerMaxX - aw, ry1, aw * 2, ah * 2, ex, ey, ew, eh);
			}
			if (centerY > rInnerMaxY) {
				return Ellipse2afp.intersectsEllipseEllipse(rInnerMaxX - aw, rInnerMaxY - ah, aw * 2, ah * 2, ex, ey, ew, eh);
			}
			return centerX > (rx1 - radius1) && centerX < (rx2 + radius1);
		}
		return centerY > (ry1 - radius2) && centerY < (ry2 + radius2);
	}

	@Pure
	@Override
	default boolean equalsToShape(IT shape) {
		if (shape == null) {
			return false;
		}
		if (shape == this) {
			return true;
		}
		return getMinX() == shape.getMinX()
				&& getMinY() == shape.getMinY()
				&& getMaxX() == shape.getMaxX()
				&& getMaxY() == shape.getMaxY()
				&& getArcWidth() == shape.getArcWidth()
				&& getArcHeight() == shape.getArcHeight();
	}

	/**
	 * Gets the width of the arc that rounds off the corners.
	 * @return the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2afp</code>.
	 */
	@Pure
	double getArcWidth();

	/**
	 * Gets the height of the arc that rounds off the corners.
	 * @return the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2afp</code>.
	 */
	@Pure
	double getArcHeight();

	/**
	 * Set the width of the arc that rounds off the corners.
	 * @param a is the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2afp</code>.
	 */
	void setArcWidth(double a);

	/**
	 * Set the height of the arc that rounds off the corners.
	 * @param a is the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2afp</code>.
	 */
	void setArcHeight(double a);

	/** Change the frame of the rectangle.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param arcWidth is the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2afp</code>.
	 * @param arcHeight is the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2afp</code>.
	 */
	default void set(double x, double y, double width, double height, double arcWidth, double arcHeight) {
		assert (width >= 0.) : "Rectangle width must be positive or zero"; //$NON-NLS-1$
		assert (height >= 0.) : "Rectangle geight must be positive or zero"; //$NON-NLS-1$
		assert (arcWidth >= 0.) : "Rectangle arc width must be positive or zero"; //$NON-NLS-1$
		assert (arcHeight >= 0.) : "Rectangle arc height must be positive or zero"; //$NON-NLS-1$
		setFromCorners(x, y, x + width, y + height, arcWidth, arcHeight);
	}

	/** Change the frame of the rectangle.
	 * 
	 * @param x1 is the coordinate of the first corner.
	 * @param y1 is the coordinate of the first corner.
	 * @param x2 is the coordinate of the second corner.
	 * @param y2 is the coordinate of the second corner.
	 * @param arcWidth is the width of the arc that rounds off the corners
	 * of this <code>RoundRectangle2afp</code>.
	 * @param arcHeight is the height of the arc that rounds off the corners
	 * of this <code>RoundRectangle2afp</code>.
	 */
	void setFromCorners(double x1, double y1, double x2, double y2, double arcWidth, double arcHeight);

	@Override
	default void set(IT s) {
		assert (s != null) : "Shape must be not null"; //$NON-NLS-1$
		setFromCorners(s.getMinX(), s.getMinY(), s.getMaxX(), s.getMaxY(), s.getArcWidth(), s.getArcHeight());
	}

	@Override
	default boolean contains(double x, double y) {
		return containsRoundRectanglePoint(
				getMinX(), getMinY(), getWidth(), getHeight(), getArcWidth(), getArcHeight(),
				x, y);
	}

	@Override
	default boolean contains(Rectangle2afp<?, ?, ?, ?, ?, ?> r) {
		assert (r != null) : "Rectangle must be not null"; //$NON-NLS-1$
		return containsRoundRectangleRectangle(
				getMinX(), getMinY(), getWidth(), getHeight(), getArcWidth(), getArcHeight(),
				r.getMinX(), r.getMinY(), r.getWidth(), r.getHeight());
	}

	@Pure
	@Override
	default double getDistanceSquared(Point2D<?, ?> p) {
		assert (p != null) : "Point must be not null"; //$NON-NLS-1$
		Point2D<?, ?> n = getClosestPointTo(p);
		return n.getDistanceSquared(p);
	}

	@Pure
	@Override
	default double getDistanceL1(Point2D<?, ?> p) {
		assert (p != null) : "Point must be not null"; //$NON-NLS-1$
		Point2D<?, ?> n = getClosestPointTo(p);
		return n.getDistanceL1(p);
	}

	@Pure
	@Override
	default double getDistanceLinf(Point2D<?, ?> p) {
		assert (p != null) : "Point must be not null"; //$NON-NLS-1$
		Point2D<?, ?> n = getClosestPointTo(p);
		return n.getDistanceLinf(p);
	}

	@Override
	default boolean intersects(Circle2afp<?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "Circle must be not null"; //$NON-NLS-1$
		return intersectsRoundRectangleCircle(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				getArcWidth(), getArcHeight(),
				s.getX(), s.getY(),
				s.getRadius());
	}

	@Override
	default boolean intersects(Ellipse2afp<?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "Ellipse must be not null"; //$NON-NLS-1$
		return intersectsRoundRectangleEllipse(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				getArcWidth(), getArcHeight(),
				s.getMinX(), s.getMinY(),
				s.getWidth(), s.getHeight());
	}

	@Override
	default boolean intersects(OrientedRectangle2afp<?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "Oriented rectangle must be not null"; //$NON-NLS-1$
		return OrientedRectangle2afp.intersectsOrientedRectangleRoundRectangle(
				s.getCenterX(), s.getCenterY(), 
				s.getFirstAxisX(), s.getFirstAxisY(), s.getFirstAxisExtent(),
				s.getSecondAxisExtent(),
				getMinX(), getMinY(), getWidth(), getHeight(), getArcWidth(), getArcHeight());
	}

	@Override
	default boolean intersects(Parallelogram2afp<?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "Parallelogram must be not null"; //$NON-NLS-1$
		return Parallelogram2afp.intersectsParallelogramRoundRectangle(
				s.getCenterX(), s.getCenterY(), 
				s.getFirstAxisX(), s.getFirstAxisY(), s.getFirstAxisExtent(),
				s.getSecondAxisX(), s.getSecondAxisY(), s.getSecondAxisExtent(),
				getMinX(), getMinY(), getWidth(), getHeight(), getArcWidth(), getArcHeight());
	}

	@Override
	default boolean intersects(Rectangle2afp<?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "Rectangle must be not null"; //$NON-NLS-1$
		return intersectsRoundRectangleRectangle(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				getArcWidth(), getArcHeight(),
				s.getMinX(), s.getMinY(),
				s.getMaxX(), s.getMaxY());
	}

	@Override
	default boolean intersects(RoundRectangle2afp<?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "Round rectangle must be not null"; //$NON-NLS-1$
		return intersectsRoundRectangleRoundRectangle(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				getArcWidth(), getArcHeight(),
				s.getMinX(), s.getMinY(),
				s.getMaxX(), s.getMaxY(),
				s.getArcWidth(), s.getArcHeight());
	}

	@Override
	default boolean intersects(Segment2afp<?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "Segment must be not null"; //$NON-NLS-1$
		return RoundRectangle2afp.intersectsRoundRectangleSegment(
				getMinX(), getMinY(),
				getMaxX(), getMaxY(),
				getArcWidth(), getArcHeight(),
				s.getX1(), s.getY1(),
				s.getX2(), s.getY2());
	}

	@Override
	default boolean intersects(Triangle2afp<?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "Triangle must be not null"; //$NON-NLS-1$
		return intersects(s.getPathIterator());
	}

	@Override
	default boolean intersects(PathIterator2afp<?> iterator) {
		assert (iterator != null) : "Iterator must be not null"; //$NON-NLS-1$
		int mask = (iterator.getWindingRule() == PathWindingRule.NON_ZERO ? -1 : 2);
		int crossings = Path2afp.computeCrossingsFromRoundRect(
				0,
				iterator,
				getMinX(), getMinY(), getMaxX(), getMaxY(), getArcWidth(), getArcHeight(),
				CrossingComputationType.SIMPLE_INTERSECTION_WHEN_NOT_POLYGON);
		return (crossings == MathConstants.SHAPE_INTERSECTS ||
				(crossings & mask) != 0);

	}

	@Pure
	@Override
	default boolean intersects(MultiShape2afp<?, ?, ?, ?, ?, ?, ?> s) {
		assert (s != null) : "MultiShape must be not null"; //$NON-NLS-1$
		return s.intersects(this);
	}

	@Pure
	@Override
	default PathIterator2afp<IE> getPathIterator(Transform2D transform) {
		if (transform == null || transform.isIdentity()) {
			return new RoundRectanglePathIterator<>(this);
		}
		return new TransformedRoundRectanglePathIterator<>(this, transform);
	}

	@Pure
	@Override
	default P getClosestPointTo(Point2D<?, ?> p) {
		assert (p != null) : "Point must be not null"; //$NON-NLS-1$
		double px = p.getX();
		double py = p.getY();
		double rx1 = getMinX();
		double ry1 = getMinY();
		double rx2 = getMaxX();
		double ry2 = getMaxY();

		double aw = getArcWidth();
		double ah = getArcHeight();

		GeomFactory2afp<?, P, V, ?> factory = getGeomFactory();

		if (px < rx1 + aw) {
			if (py < ry1 + ah) {
				P point = factory.newPoint();
				Ellipse2afp.computeClosestPointToSolidEllipse(
						px, py,
						rx1, ry1,
						aw * 2, ah * 2,
						point);
				return point;
			}
			if (py > ry2 - ah) {
				double eh = ah * 2;
				P point = factory.newPoint();
				Ellipse2afp.computeClosestPointToSolidEllipse(
						px, py,
						rx1, ry2 - eh,
						aw * 2, eh,
						point);
				return point;
			}
		}
		else if (px > rx2 - aw) {
			if (py < ry1 + ah) {
				double ew = aw * 2;
				P point = factory.newPoint();
				Ellipse2afp.computeClosestPointToSolidEllipse(
						px, py,
						rx2 - ew, ry1,
						ew, ah * 2,
						point);
				return point;
			}
			if (py > ry2 - ah) {
				double ew = aw * 2;
				double eh = ah * 2;
				P point = factory.newPoint();
				Ellipse2afp.computeClosestPointToSolidEllipse(
						px, py,
						rx2 - ew, ry2 - eh,
						ew, eh,
						point);
				return point;
			}
		}

		int same = 0;
		double x, y;

		if (px < rx1) {
			x = rx1;
		} else if (px > rx2) {
			x = rx2;
		} else {
			x = px;
			++same;
		}

		if (py < ry1) {
			y = ry1;
		}
		else if (py > ry2) {
			y = ry2;
		} else {
			y = py;
			++same;
		}

		if (same == 2) {
			return factory.convertToPoint(p);
		}
		return factory.newPoint(x, y);
	}

	@Pure
	@Override
	default P getFarthestPointTo(Point2D<?, ?> p) {
		assert (p != null) : "Point must be not null"; //$NON-NLS-1$
		double px = p.getX();
		double py = p.getY();
		double rx1 = getMinX();
		double ry1 = getMinY();
		double rx2 = getMaxX();
		double ry2 = getMaxY();
		double centerX = getCenterX();
		double centerY = getCenterY();

		double aw = getArcWidth();
		double ah = getArcHeight();

		P point = getGeomFactory().newPoint();

		if (px <= centerX) {
			if (py <= centerY) {
				double ew = aw * 2;
				double eh = ah * 2;
				Ellipse2afp.computeFarthestPointToShallowEllipse(
						px, py,
						rx2 - ew, ry2 - eh,
						ew, eh,
						point);
			} else {
				double ew = aw * 2;
				Ellipse2afp.computeFarthestPointToShallowEllipse(
						px, py,
						rx2 - ew, ry1,
						ew, ah * 2,
						point);
			}
		}
		else if (px <= centerY) {
			double eh = ah * 2;
			Ellipse2afp.computeFarthestPointToShallowEllipse(
					px, py,
					rx1, ry2 - eh,
					aw * 2, eh,
					point);
		} else {
			Ellipse2afp.computeFarthestPointToShallowEllipse(
					px, py,
					rx1, ry1,
					aw * 2, ah * 2,
					point);
		}

		return point;
	}


	/** Abstract iterator on the path elements of the round rectangle.
	 * 
	 * @param <T> the type of the path elements.
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	abstract class AbstractRoundRectanglePathIterator<T extends PathElement2afp> implements PathIterator2afp<T> {

		/** Number of elements in the path (except move).
		 */
		protected static final int ELEMENT_COUNT = 9;
		
		/** The iterator round rectangle.
		 */
		protected final RoundRectangle2afp<?, ?, T, ?, ?, ?> rectangle;

		/**
		 * @param rectangle the iterated rectangle.
		 */
		public AbstractRoundRectanglePathIterator(RoundRectangle2afp<?, ?, T, ?, ?, ?> rectangle) {
			assert (rectangle != null) : "Round rectangle must be not null"; //$NON-NLS-1$
			this.rectangle = rectangle;
		}

		@Override
		public GeomFactory2afp<T, ?, ?, ?> getGeomFactory() {
			return this.rectangle.getGeomFactory();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

		@Pure
		@Override
		public PathWindingRule getWindingRule() {
			return PathWindingRule.NON_ZERO;
		}

		@Pure
		@Override
		public boolean isPolyline() {
			return false;
		}

		@Pure
		@Override
		public boolean isCurved() {
			return true;
		}

		@Pure
		@Override
		public boolean isPolygon() {
			return true;
		}

		@Pure
		@Override
		public boolean isMultiParts() {
			return false;
		}

	}

	/** Iterator on the path elements of the rectangle.
	 * 
	 * @param <T> the type of the path elements.
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 * @since 13.0
	 */
	class RoundRectanglePathIterator<T extends PathElement2afp> extends AbstractRoundRectanglePathIterator<T> {

		private double x;

		private double y;

		private double w;

		private double h;

		private double aw;

		private double ah;

		private int index;
		
		private double lastX;
		
		private double lastY;

		private double moveX;
		
		private double moveY;

		/**
		 * @param rectangle the round rectangle to iterate on.
		 */
		public RoundRectanglePathIterator(RoundRectangle2afp<?, ?, T, ?, ?, ?> rectangle) {
			super(rectangle);
			if (rectangle.isEmpty()) {
				this.index = ELEMENT_COUNT;
			} else {
				this.x = rectangle.getMinX();
				this.y = rectangle.getMinY();
				this.w = rectangle.getWidth();
				this.h = rectangle.getHeight();
				this.aw = rectangle.getArcWidth();
				this.ah = rectangle.getArcHeight();
				this.index = -1;
			}
		}
		
		@Override
		public PathIterator2afp<T> restartIterations() {
			return new RoundRectanglePathIterator<>(this.rectangle);
		}

		@Pure
		@Override
		public boolean hasNext() {
			return this.index < ELEMENT_COUNT;
		}

		@Override
		public T next() {
			if (this.index >= ELEMENT_COUNT) {
				throw new NoSuchElementException();
			}
			int idx = this.index;
			++this.index;
			
			if (idx < 0) {
				this.moveX = this.x + this.aw;
				this.moveY = this.y;
				this.lastX = this.moveX;
				this.lastY = this.moveY;
				return getGeomFactory().newMovePathElement(
						this.lastX, this.lastY);
			}
			
			double x = this.lastX;
			double y = this.lastY;
			
			double curveX, curveY;
			
			switch(idx) {
			case 0:
				this.lastX = this.x + this.w - this.aw;
				return getGeomFactory().newLinePathElement(
						x, y,
						this.lastX, this.lastY);
			case 1:
				this.lastX += this.aw;
				this.lastY += this.ah;
				curveX = x + AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.aw;
				curveY = this.lastY - AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.ah;
				return getGeomFactory().newCurvePathElement(
						x, y,
						curveX, y,
						this.lastX, curveY,
						this.lastX, this.lastY);
			case 2:
				this.lastY = this.y + this.h - this.ah;
				return getGeomFactory().newLinePathElement(
						x, y,
						this.lastX, this.lastY);
			case 3:
				this.lastX -= this.aw;
				this.lastY += this.ah;
				curveX = this.lastX + AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.aw;
				curveY = y + AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.ah;
				return getGeomFactory().newCurvePathElement(
						x, y,
						x, curveY,
						curveX, this.lastY,
						this.lastX, this.lastY);
			case 4:
				this.lastX = this.x + this.aw;
				return getGeomFactory().newLinePathElement(
						x, y,
						this.lastX, this.lastY);
			case 5:
				this.lastX -= this.aw;
				this.lastY -= this.ah;
				curveX = x - AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.aw;
				curveY = this.lastY + AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.ah;
				return getGeomFactory().newCurvePathElement(
						x, y,
						curveX, y,
						this.lastX, curveY,
						this.lastX, this.lastY);
			case 6:
				this.lastY = this.y + this.ah;
				return getGeomFactory().newLinePathElement(
						x, y,
						this.lastX, this.lastY);
			case 7:
				this.lastX += this.aw;
				this.lastY -= this.ah;
				curveX = this.lastX - AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.aw;
				curveY = y - AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.ah;
				return getGeomFactory().newCurvePathElement(
						x, y,
						x, curveY,
						curveX, this.lastY,
						this.lastX, this.lastY);
			default:
				return getGeomFactory().newClosePathElement(
						x, y,
						this.moveX, this.moveY);
			}
		}

	}

	/** Iterator on the path elements of the rectangle.
	 * 
	 * @param <T> the type of the path elements.
	 * @author $Author: sgalland$
	 * @version $FullVersion$
	 * @mavengroupid $GroupId$
	 * @mavenartifactid $ArtifactId$
	 */
	class TransformedRoundRectanglePathIterator<T extends PathElement2afp> extends AbstractRoundRectanglePathIterator<T> {

		private final Transform2D transform;

		private double x;

		private double y;

		private double w;

		private double h;

		private double aw;

		private double ah;

		private int index;
		
		private Point2D<?, ?> last;
		
		private Point2D<?, ?> move;

		private Point2D<?, ?> controlPoint1;

		private Point2D<?, ?> controlPoint2;

		/**
		 * @param rectangle the round rectangle to iterate on.
		 * @param transform the transformation.
		 */
		public TransformedRoundRectanglePathIterator(RoundRectangle2afp<?, ?, T, ?, ?, ?> rectangle, Transform2D transform) {
			super(rectangle);
			assert (transform != null) : "Transformation must be not null"; //$NON-NLS-1$
			this.transform = transform;
			if (rectangle.isEmpty()) {
				this.index = ELEMENT_COUNT;
			} else {
				this.last = new InnerComputationPoint2afp();
				this.move = new InnerComputationPoint2afp();
				this.controlPoint1 = new InnerComputationPoint2afp();
				this.controlPoint2 = new InnerComputationPoint2afp();
				this.x = rectangle.getMinX();
				this.y = rectangle.getMinY();
				this.w = rectangle.getWidth();
				this.h = rectangle.getHeight();
				this.aw = rectangle.getArcWidth();
				this.ah = rectangle.getArcHeight();
				this.index = -1;
			}
		}
		
		@Override
		public PathIterator2afp<T> restartIterations() {
			return new TransformedRoundRectanglePathIterator<>(this.rectangle, this.transform);
		}

		@Pure
		@Override
		public boolean hasNext() {
			return this.index < ELEMENT_COUNT;
		}

		@Override
		public T next() {
			if (this.index >= ELEMENT_COUNT) {
				throw new NoSuchElementException();
			}
			int idx = this.index;
			++this.index;
			
			if (idx < 0) {
				this.move.set(this.x + this.aw, this.y);
				this.transform.transform(this.move);
				this.last.set(this.move);
				return getGeomFactory().newMovePathElement(
						this.last.getX(), this.last.getY());
			}
			
			double x = this.last.getX();
			double y = this.last.getY();
			
			switch(idx) {
			case 0:
				this.last.set(
						this.x + this.w - this.aw,
						this.y);
				this.transform.transform(this.last);
				return getGeomFactory().newLinePathElement(
						x, y,
						this.last.getX(), this.last.getY());
			case 1:
				this.last.set(
						this.x + this.w,
						this.y + this.ah);
				this.controlPoint1.set(
						this.x + this.w - this.aw + AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.aw,
						this.y);
				this.controlPoint2.set(
						this.x + this.w,
						this.y + this.ah - AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.ah);
				this.transform.transform(this.last);
				this.transform.transform(this.controlPoint1);
				this.transform.transform(this.controlPoint2);
				return getGeomFactory().newCurvePathElement(
						x, y,
						this.controlPoint1.getX(), this.controlPoint1.getY(),
						this.controlPoint2.getX(), this.controlPoint2.getY(),
						this.last.getX(), this.last.getY());
			case 2:
				this.last.set(
						this.x + this.w,
						this.y + this.h - this.ah);
				this.transform.transform(this.last);
				return getGeomFactory().newLinePathElement(
						x, y,
						this.last.getX(), this.last.getY());
			case 3:
				this.last.set(
						this.x + this.w - this.aw,
						this.y + this.h);
				this.controlPoint1.set(
						this.x + this.w,
						this.y + this.h - this.ah + AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.ah);
				this.controlPoint2.set(
						this.x + this.w - this.aw + AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.aw,
						this.y + this.h);
				this.transform.transform(this.last);
				this.transform.transform(this.controlPoint1);
				this.transform.transform(this.controlPoint2);
				return getGeomFactory().newCurvePathElement(
						x, y,
						this.controlPoint1.getX(), this.controlPoint1.getY(),
						this.controlPoint2.getX(), this.controlPoint2.getY(),
						this.last.getX(), this.last.getY());
			case 4:
				this.last.set(
						this.x + this.aw,
						this.y + this.h);
				this.transform.transform(this.last);
				return getGeomFactory().newLinePathElement(
						x, y,
						this.last.getX(), this.last.getY());
			case 5:
				this.last.set(
						this.x,
						this.y + this.h - this.ah);
				this.controlPoint1.set(
						this.x + this.aw - AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.aw,
						this.y + this.h);
				this.controlPoint2.set(
						this.x,
						this.y + this.h - this.ah + AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.ah);
				this.transform.transform(this.last);
				this.transform.transform(this.controlPoint1);
				this.transform.transform(this.controlPoint2);
				return getGeomFactory().newCurvePathElement(
						x, y,
						this.controlPoint1.getX(), this.controlPoint1.getY(),
						this.controlPoint2.getX(), this.controlPoint2.getY(),
						this.last.getX(), this.last.getY());
			case 6:
				this.last.set(
						this.x,
						this.y + this.ah);
				this.transform.transform(this.last);
				return getGeomFactory().newLinePathElement(
						x, y,
						this.last.getX(), this.last.getY());
			case 7:
				this.controlPoint1.set(
						this.x,
						this.y + this.ah - AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.ah);
				this.controlPoint2.set(
						this.x + this.aw - AbstractCirclePathIterator.CTRL_POINT_DISTANCE * this.aw,
						this.y);
				this.transform.transform(this.controlPoint1);
				this.transform.transform(this.controlPoint2);
				return getGeomFactory().newCurvePathElement(
						x, y,
						this.controlPoint1.getX(), this.controlPoint1.getY(),
						this.controlPoint2.getX(), this.controlPoint2.getY(),
						this.move.getX(), this.move.getY());
			default:
				return getGeomFactory().newClosePathElement(
						x, y,
						this.move.getX(), this.move.getY());
			}
		}

	}

}
