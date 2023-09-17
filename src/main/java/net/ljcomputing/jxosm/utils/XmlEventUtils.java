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

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public enum XmlEventUtils {
    INSTANCE;

    public static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

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

    public static final void populateProperties(
            final Object bean, final Map<String, String> properties) {
        final List<PropertyDescriptor> descriptors =
                Arrays.asList(BeanUtils.getPropertyDescriptors(bean.getClass())).stream()
                        .filter(descriptor -> descriptor.getWriteMethod() != null)
                        .collect(Collectors.toList());

        descriptors.forEach(
                descriptor -> {
                    final String sourceValue = (String) properties.get(descriptor.getName());

                    if (sourceValue != null) {
                        final Object value =
                                convertValue(
                                        sourceValue, descriptor.getPropertyType().getSimpleName());
                        setProperty(bean, descriptor.getWriteMethod(), value);
                    }
                });
    }

    private static Object convertValue(final String value, final String klass) {
        switch (klass) {
            case "Byte":
                return Byte.parseByte(value);
            case "Long":
                return Long.valueOf(value);
            case "Float":
                return Float.valueOf(value);
            case "Short":
                return Short.valueOf(value);
            case "Integer":
                return Integer.valueOf(value);
            case "LocalDateTime":
                return LocalDateTime.parse(value, DATE_TIME_FORMATTER);
            default:
                return value;
        }
    }

    private static void setProperty(final Object bean, final Method setMethod, final Object value) {
        try {
            setMethod.invoke(bean, value);
        } catch (Exception e) {
            log.error("ERROR: ", e);
        }
    }
}
