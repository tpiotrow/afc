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
package org.arakhne.afc.math.geometry.d2.d;

import org.arakhne.afc.math.geometry.PathElementType;
import org.arakhne.afc.math.geometry.d2.afp.AbstractMultiShape2afpTest;
import org.arakhne.afc.math.geometry.d2.afp.MultiShape2afp;
import org.arakhne.afc.math.geometry.d2.afp.PathIterator2afp;
import org.arakhne.afc.math.geometry.d2.afp.TestShapeFactory;
import org.arakhne.afc.math.geometry.d2.d.MultiShape2d;
import org.arakhne.afc.math.geometry.d2.d.Rectangle2d;
import org.arakhne.afc.math.geometry.d2.d.Shape2d;

@SuppressWarnings("all")
public class MultiShape2dTest extends AbstractMultiShape2afpTest<MultiShape2d<Shape2d<?>>, Shape2d<?>, Rectangle2d> {

	@Override
	protected TestShapeFactory2d createFactory() {
		return TestShapeFactory2d.SINGLETON;
	}

}