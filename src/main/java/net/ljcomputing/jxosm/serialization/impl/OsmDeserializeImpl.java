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
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import lombok.extern.slf4j.Slf4j;
import net.ljcomputing.jxosm.processor.XmlEventProcessor;
import net.ljcomputing.jxosm.processor.impl.StaxProcessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OsmDeserializeImpl {
    private final XMLInputFactory factory = XMLInputFactory.newInstance();

    @Autowired private StaxProcessorFactory staxProcessorFactory;

    public void parse(final File file) {
        try (FileInputStream is = new FileInputStream(file)) {
            String previousElementName = "osm";
            final XMLEventReader reader = factory.createXMLEventReader(is);
            while (reader.hasNext()) {
                final XMLEvent event = reader.nextEvent();

                if (event.isStartElement()) {
                    final StartElement startElement = event.asStartElement();
                    final String elementName = startElement.getName().getLocalPart();
                    final XmlEventProcessor processor = staxProcessorFactory.locate(elementName);

                    if (processor != null) {
                        processor.process(event);
                    }
                }

                // if (event.isEndElement()) {
                //     log.debug("previous element: {}", previousElementName);
                //     previousElementName = event.asEndElement().getName().getLocalPart();
                // }
            }
        } catch (final Exception e) {
            log.error("Error encountered while deserializing file {}: ", file, e);
        }
    }
}
