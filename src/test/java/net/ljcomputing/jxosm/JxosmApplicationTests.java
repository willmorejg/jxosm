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
package net.ljcomputing.jxosm;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlType;
import net.ljcomputing.jxosm.jaxb.Osm;
import net.ljcomputing.jxosm.jaxb.Way;
import net.ljcomputing.jxosm.serialization.OsmSerializer;
import net.ljcomputing.jxosm.serialization.impl.OsmDeserializeImpl;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JxosmApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(JxosmApplicationTests.class);
    @Autowired private Unmarshaller osmUnmarshaller;
    @Autowired private Marshaller osmMarshaller;
    @Autowired private OsmSerializer osmSerializerJaxb;
    @Autowired private OsmDeserializeImpl osmDeserializeImpl;

    @Test
    @Disabled
    void contextLoads() {
        assertNotNull(osmUnmarshaller);
        assertNotNull(osmMarshaller);
        assertNotNull(osmDeserializeImpl);
    }

    @Test
    @Disabled
    void getJaxbXmlElements() {
        final Way way = new Way();
        XmlType annotation = way.getClass().getAnnotation(XmlType.class);
        log.debug("{}", Arrays.asList(annotation.propOrder()));
    }

    @Test
    @Disabled
    void deserializeSerializeOsm() {
        final Path osmPath =
                Path.of(
                        System.getProperty("user.dir"),
                        "src",
                        "test",
                        "resources",
                        "data",
                        "osm",
                        "test.osm");
        final Osm osm = osmSerializerJaxb.deserializeToOsm(osmPath.toFile());
        assertNotNull(osm);
        log.debug("osm: {}", osm);

        final Path osmToPath =
                Path.of(
                        System.getProperty("user.dir"),
                        "src",
                        "test",
                        "resources",
                        "data",
                        "osm",
                        "out.osm");
        osmSerializerJaxb.serializeFromOsm(osm, osmToPath.toFile());
    }

    @Test
    void streamDeserialization() {
        final Path osmPath =
                Path.of(
                        System.getProperty("user.dir"),
                        "src",
                        "test",
                        "resources",
                        "data",
                        "osm",
                        "test.osm");
        osmDeserializeImpl.parse(osmPath.toFile());
    }
}
