/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.

James G Willmore - LJ Computing - (C) 2023
*/
package net.ljcomputing.jxosm.populator;

import java.util.List;
import java.util.Optional;
import net.ljcomputing.jxosm.jaxb.Member;
import net.ljcomputing.jxosm.jaxb.Nd;
import net.ljcomputing.jxosm.jaxb.Node;
import net.ljcomputing.jxosm.jaxb.Osm;
import net.ljcomputing.jxosm.jaxb.Relation;
import net.ljcomputing.jxosm.jaxb.Tag;
import net.ljcomputing.jxosm.jaxb.Way;

public enum OsmPopulator {
    INSTANCE;

    public static void populate(final Osm osm, Object child, final Object parent) {
        if (child instanceof Way) {
            osm.getWays().add((Way) child);
        }

        if (child instanceof Node) {
            osm.getNodes().add((Node) child);
        }

        if (child instanceof Relation) {
            osm.getRelations().add((Relation) child);
        }

        if (parent != null) {
            if (child instanceof Nd && parent instanceof Way) {
                final List<Way> list = osm.getWays();
                final Optional<Way> way =
                        list.stream()
                                .filter(w -> ((Way) parent).getId().equals(w.getId()))
                                .findFirst();

                if (way.isPresent()) {
                    way.get().getNds().add((Nd) child);
                }
            }

            if (child instanceof Tag && parent instanceof Way) {
                final List<Way> list = osm.getWays();
                final Optional<Way> way =
                        list.stream()
                                .filter(w -> ((Way) parent).getId().equals(w.getId()))
                                .findFirst();

                if (way.isPresent()) {
                    way.get().getTags().add((Tag) child);
                }
            }

            if (child instanceof Member && parent instanceof Relation) {
                final List<Relation> list = osm.getRelations();
                final Optional<Relation> relation =
                        list.stream()
                                .filter(w -> ((Relation) parent).getId().equals(w.getId()))
                                .findFirst();

                if (relation.isPresent()) {
                    relation.get().getMembers().add((Member) child);
                }
            }

            if (child instanceof Tag && parent instanceof Relation) {
                final List<Relation> list = osm.getRelations();
                final Optional<Relation> relation =
                        list.stream()
                                .filter(w -> ((Relation) parent).getId().equals(w.getId()))
                                .findFirst();

                if (relation.isPresent()) {
                    relation.get().getTags().add((Tag) child);
                }
            }
        }
    }
}
