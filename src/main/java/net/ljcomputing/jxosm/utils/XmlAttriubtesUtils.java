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
package net.ljcomputing.jxosm.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public enum XmlAttriubtesUtils {
    INSTANCE;

    public static final Map<String, String> xmlAttributesToMap(final XMLEvent event) {
        final Map<String, String> map = new HashMap<>();

        if (event.isStartElement()) {
            final StartElement element = event.asStartElement();
            final Iterator<Attribute> attributeIterator = element.getAttributes();

            while (attributeIterator.hasNext()) {
                final Attribute attribute = attributeIterator.next();
                final String name = attribute.getName().getLocalPart();
                final String value = attribute.getValue();
                map.put(name, value);
            }
        }

        return map;
    }
}
