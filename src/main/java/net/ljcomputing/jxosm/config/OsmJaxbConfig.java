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
package net.ljcomputing.jxosm.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import lombok.extern.slf4j.Slf4j;
import net.ljcomputing.jxosm.jaxb.Osm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** OSM JAXB configuration. */
@Configuration
@Slf4j
public class OsmJaxbConfig {
    /**
     * OSM JAXB unmarshaller.
     *
     * @return
     */
    @Bean
    public Unmarshaller osmUnmarshaller() {
        try {
            JAXBContext jc = JAXBContext.newInstance(Osm.class);
            return jc.createUnmarshaller();
        } catch (final Exception e) {
            log.error("Failed to create osmUnmarshaller bean: ", e);
        }

        return null;
    }

    /**
     * OSM JAXB marshaller.
     *
     * @return
     */
    @Bean
    public Marshaller osmMarshaller() {
        try {
            JAXBContext jc = JAXBContext.newInstance(Osm.class);
            return jc.createMarshaller();
        } catch (final Exception e) {
            log.error("Failed to create osmMarshaller bean: ", e);
        }

        return null;
    }
}
