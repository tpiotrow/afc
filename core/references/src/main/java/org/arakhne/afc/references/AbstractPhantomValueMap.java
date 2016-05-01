/* 
 * $Id$
 * 
 * Copyright (C) 2005-2009 Stephane GALLAND.
 * Copyright (C) 2011-12 Stephane GALLAND.
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

package org.arakhne.afc.references;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.Map;

/**
 * A <tt>Map</tt> implementation with {@link PhantomReference phantom values}. An entry in a
 * <tt>AbstractPhantomValueMap</tt> will automatically be removed when its value is no
 * longer in ordinary use or <code>null</code>.
 * <p>
 * This abstract implementation does not decide if the map is based on a tree or on a hashtable.
 *
 * @param <K> is the type of the keys.
 * @param <V> is the type of the values.
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 5.8
 */
public abstract class AbstractPhantomValueMap<K,V> extends AbstractReferencedValueMap<K,V> {
	
	/**
     * Constructs an empty <tt>Map</tt>.
     *
     * @param  map is the map instance to use to store the entries.
     * @throws IllegalArgumentException if the initial capacity is negative
     *         or the load factor is nonpositive
     */
    public AbstractPhantomValueMap(Map<K,ReferencableValue<K,V>> map) {
        super(map);
    }

    /** {@inheritDoc}
	 */
	@Override
	protected final ReferencableValue<K,V> makeValue(K k, V v, ReferenceQueue<V> queue) {
		return new PhantomReferencedValue<>(k, v, queue);
	}

}
