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

package org.arakhne.afc.math.tree;

import java.io.Serializable;
import java.util.Iterator;

import org.eclipse.xtext.xbase.lib.Pure;

import org.arakhne.afc.math.tree.iterator.BroadFirstTreeIterator;
import org.arakhne.afc.math.tree.iterator.DataBroadFirstTreeIterator;
import org.arakhne.afc.math.tree.iterator.DepthFirstNodeOrder;
import org.arakhne.afc.math.tree.iterator.InfixDataDepthFirstTreeIterator;
import org.arakhne.afc.math.tree.iterator.InfixDepthFirstTreeIterator;
import org.arakhne.afc.math.tree.iterator.PostfixDataDepthFirstTreeIterator;
import org.arakhne.afc.math.tree.iterator.PostfixDepthFirstTreeIterator;
import org.arakhne.afc.math.tree.iterator.PrefixDataDepthFirstTreeIterator;
import org.arakhne.afc.math.tree.iterator.PrefixDepthFirstTreeIterator;


/**
 * This is the generic implementation of a
 * tree based on linked lists.
 *
 * <p>This tree assumes that the nodes are linked with there
 * references.
 *
 * @param <D> is the type of the data inside the tree
 * @param <N> is the type of the tree nodes.
 * @author $Author: sgalland$
 * @version $FullVersion$
 * @mavengroupid $GroupId$
 * @mavenartifactid $ArtifactId$
 * @since 13.0
 */
public abstract class AbstractTree<D, N extends TreeNode<D, N>>
		implements DepthFirstIterableTree<D, N>, BroadFirstIterableTree<D, N>, Serializable {

	private static final long serialVersionUID = 1192947956138993568L;

	@Override
	@Pure
	public final boolean isEmpty() {
		return getUserDataCount() == 0;
	}

	/** {@inheritDoc}
	 * The default iterator is a depth first iterator.
	 */
	@Override
	@Pure
	public final Iterator<N> iterator() {
		return depthFirstIterator();
	}

	@Override
	@Pure
	public final Iterator<N> depthFirstIterator(DepthFirstNodeOrder nodeOrder) {
		switch (nodeOrder) {
		case POSTFIX:
			return new PostfixDepthFirstTreeIterator<>(this);
		case INFIX:
			return new InfixDepthFirstTreeIterator<>(this);
		default:
			return new PrefixDepthFirstTreeIterator<>(this);
		}
	}

	@Override
	@Pure
	public final Iterator<N> depthFirstIterator(int infixPosition) {
		if (infixPosition <= 0) {
			return new PrefixDepthFirstTreeIterator<>(this);
		}
		return new InfixDepthFirstTreeIterator<>(this, infixPosition);
	}

	@Override
	@Pure
	public final Iterator<N> depthFirstIterator() {
		return new PrefixDepthFirstTreeIterator<>(this);
	}

	@Override
	@Pure
	public final Iterator<N> broadFirstIterator() {
		return new BroadFirstTreeIterator<>(this);
	}

	@Override
	@Pure
	public final Iterator<D> dataDepthFirstIterator() {
		return new PrefixDataDepthFirstTreeIterator<>(this);
	}

	@Override
	@Pure
	public final Iterator<D> dataDepthFirstIterator(DepthFirstNodeOrder nodeOrder) {
		switch (nodeOrder) {
		case POSTFIX:
			return new PostfixDataDepthFirstTreeIterator<>(this);
		case INFIX:
			return new InfixDataDepthFirstTreeIterator<>(this);
		default:
			return new PrefixDataDepthFirstTreeIterator<>(this);
		}
	}

	@Pure
	@Override
	public final Iterator<D> dataDepthFirstIterator(int infixPosition) {
		if (infixPosition <= 0) {
			return new PrefixDataDepthFirstTreeIterator<>(this);
		}
		return new InfixDataDepthFirstTreeIterator<>(this, infixPosition);
	}

	@Pure
	@Override
	public final Iterator<D> dataBroadFirstIterator() {
		return new DataBroadFirstTreeIterator<>(this);
	}

	@Pure
	@Override
	public final Iterable<N> toDepthFirstIterable() {
		return new Iterable<N>() {
			@Override
			public Iterator<N> iterator() {
				return AbstractTree.this.depthFirstIterator();
			}
		};
	}

	@Pure
	@Override
	public final Iterable<N> toDepthFirstIterable(final DepthFirstNodeOrder nodeOrder) {
		return new Iterable<N>() {
			@Override
			public Iterator<N> iterator() {
				return AbstractTree.this.depthFirstIterator(nodeOrder);
			}
		};
	}

	@Override
	@Pure
	public final Iterable<N> toDepthFirstIterable(final int infixPosition) {
		return new Iterable<N>() {
			@Override
			public Iterator<N> iterator() {
				return AbstractTree.this.depthFirstIterator(infixPosition);
			}
		};
	}

	@Pure
	@Override
	public final Iterable<N> toBroadFirstIterable() {
		return new Iterable<N>() {
			@Override
			public Iterator<N> iterator() {
				return AbstractTree.this.broadFirstIterator();
			}
		};
	}

	@Override
	@Pure
	public final Iterable<D> toDataDepthFirstIterable() {
		return new Iterable<D>() {
			@Override
			public Iterator<D> iterator() {
				return AbstractTree.this.dataDepthFirstIterator();
			}
		};
	}

	@Override
	@Pure
	public final Iterable<D> toDataDepthFirstIterable(final DepthFirstNodeOrder nodeOrder) {
		return new Iterable<D>() {
			@Override
			public Iterator<D> iterator() {
				return AbstractTree.this.dataDepthFirstIterator(nodeOrder);
			}
		};
	}

	@Override
	@Pure
	public final Iterable<D> toDataDepthFirstIterable(final int infixPosition) {
		return new Iterable<D>() {
			@Override
			public Iterator<D> iterator() {
				return AbstractTree.this.dataDepthFirstIterator(infixPosition);
			}
		};
	}

	@Pure
	@Override
	public final Iterable<D> toDataBroadFirstIterable() {
		return new Iterable<D>() {
			@Override
			public Iterator<D> iterator() {
				return AbstractTree.this.dataBroadFirstIterator();
			}
		};
	}

}
