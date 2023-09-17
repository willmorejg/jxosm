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
package net.ljcomputing.jxosm.serialization.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import lombok.extern.slf4j.Slf4j;
import net.ljcomputing.jxosm.jaxb.Osm;
import net.ljcomputing.jxosm.serialization.OsmSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Serialization into / from an OSM File from / into a JAXB Osm Object. */
@Component
@Slf4j
public class OsmSerializerJaxbImpl implements OsmSerializer {
    @Autowired private Unmarshaller osmUnmarshaller;
    @Autowired private Marshaller osmMarshaller;

    @Override
    public Osm deserializeToOsm(final File file) {
        try (FileInputStream is = new FileInputStream(file)) {
            return (Osm) osmUnmarshaller.unmarshal(new StreamSource(is));
        } catch (final Exception e) {
            log.error("Error encountered while deserializing file {}: ", file, e);
        }

        return null;
    }

    @Override
    public void serializeFromOsm(Osm osm, File file) {
        try (FileOutputStream os = new FileOutputStream(file)) {
            osmMarshaller.marshal(osm, new StreamResult(os));
        } catch (final Exception e) {
            log.error("Error encountered while serializing file {}: ", file, e);
        }
    }
}
