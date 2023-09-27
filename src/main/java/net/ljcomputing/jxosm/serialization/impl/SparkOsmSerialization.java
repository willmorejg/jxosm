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
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import net.ljcomputing.jxosm.jaxb.Osm;

@Component
@Slf4j
public class SparkOsmSerialization {
    @Autowired private SparkOsmNodeSerialization sparkOsmNodeSerialization;
    @Autowired private SparkOsmWaySerialization sparkOsmWaySerialization;
    @Autowired private SparkOsmRelationSerialization sparkOsmRelationSerialization;

    public Osm deserializeToOsm(File file) {
        log.debug("file: {}", file.getAbsolutePath());
        sparkOsmNodeSerialization.deserializeToOsm(file);
        sparkOsmNodeSerialization.printAvro();
        sparkOsmWaySerialization.deserializeToOsm(file);
        sparkOsmWaySerialization.printAvro();
        sparkOsmRelationSerialization.deserializeToOsm(file);
        sparkOsmRelationSerialization.printAvro();
        return null;
    }
}
