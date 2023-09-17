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
package net.ljcomputing.jxosm.processor.impl;

import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import lombok.extern.slf4j.Slf4j;
import net.ljcomputing.jxosm.processor.XmlEventProcessor;
import net.ljcomputing.jxosm.utils.XmlAttriubtesUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RelationProcessor implements XmlEventProcessor {
    public static final String ELEMENT_TO_PROCESS = "relation";

    @Override
    public String getElementToProcess() {
        return ELEMENT_TO_PROCESS;
    }

    @Override
    public void process(final XMLEvent event) throws XMLStreamException {
        final Map<String, String> map = XmlAttriubtesUtils.xmlAttributesToMap(event);
        log.debug("  {}", map);
    }
}
