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

package org.arakhne.afc.math.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This interface representes a graph's point.
 * 
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 */
class ConnectionStub implements GraphPoint<ConnectionStub,SegmentStub> {

	/**
	 */
	final List<SegmentStub> segments = new ArrayList<>();
	
	private final String id;
	
	/**
	 * @param id1
	 */
	public ConnectionStub(String id1) {
		this.id = id1;
	}
	
	@Override
	public String toString() {
		return this.id;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof ConnectionStub)
			return this.id.equals(((ConnectionStub)o).id);
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode() * 7;
	}
	
	@Override
	public int compareTo(GraphPoint<ConnectionStub, SegmentStub> o) {
		return o.hashCode() - hashCode();
	}

	/** {@inheritDoc}
	 */
	@Override
	public int getConnectedSegmentCount() {
		return this.segments.size();
	}

	/** {@inheritDoc}
	 */
	@Override
	public Iterable<SegmentStub> getConnectedSegments() {
		return this.segments;
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public Iterable<SegmentStub> getConnectedSegmentsStartingFrom(SegmentStub startingPoint) {
		return this.segments;
	}

	/** {@inheritDoc}
	 */
	@Override
	public Iterable<? extends GraphPointConnection<ConnectionStub,SegmentStub>> getConnections() {
		return new GPCIterable();
	}

	/** {@inheritDoc}
	 */
	@Override
	public Iterable<? extends GraphPointConnection<ConnectionStub,SegmentStub>> getConnectionsStartingFrom(SegmentStub startingPoint) {
		return new GPCIterable();
	}

	/** {@inheritDoc}
	 */
	@Override
	public boolean isConnectedSegment(SegmentStub segment) {
		return this.segments.contains(segment);
	}

	/** {@inheritDoc}
	 */
	@Override
	public boolean isFinalConnectionPoint() {
		return this.segments.size()<=1;
	}

    /**
     * @author $Author: sgalland$
     * @version $FullVersion$
     * @mavengroupid $GroupId$
     * @mavenartifactid $ArtifactId$
     */
	private class GPCIterable implements Iterable<GraphPointConnection<ConnectionStub,SegmentStub>> {

		/**
		 */
		public GPCIterable() {
			//
		}

		@Override
		public Iterator<GraphPoint.GraphPointConnection<ConnectionStub, SegmentStub>> iterator() {
			return new GPCIterator(ConnectionStub.this.segments.iterator());
		}

	}

    /** 
     * @author $Author: sgalland$
     * @version $FullVersion$
     * @mavengroupid $GroupId$
     * @mavenartifactid $ArtifactId$
     */
	private class GPCIterator implements Iterator<GraphPointConnection<ConnectionStub,SegmentStub>> {

		private final Iterator<SegmentStub> iter;
		
		/**
		 * @param iterator
		 */
		public GPCIterator(Iterator<SegmentStub> iterator) {
			this.iter = iterator;
		}

		@Override
		public boolean hasNext() {
			return this.iter.hasNext();
		}

		@Override
		public GraphPointConnection<ConnectionStub, SegmentStub> next() {
			SegmentStub s = this.iter.next();
			return new GPC(s);
		}

		@Override
		public void remove() {
			this.iter.remove();
		}

	}

    /** 
     * @author $Author: sgalland$
     * @version $FullVersion$
     * @mavengroupid $GroupId$
     * @mavenartifactid $ArtifactId$
     */
	private class GPC implements GraphPointConnection<ConnectionStub,SegmentStub> {

		private final SegmentStub segment;
		
		/**
		 * @param segment1
		 */
		public GPC(SegmentStub segment1) {
			this.segment = segment1;
		}

		@Override
		public ConnectionStub getGraphPoint() {
			return ConnectionStub.this;
		}

		@Override
		public SegmentStub getGraphSegment() {
			return this.segment;
		}

		@Override
		public boolean isSegmentStartConnected() {
			return this.segment.c1==ConnectionStub.this;
		}
		
	}
	
}
