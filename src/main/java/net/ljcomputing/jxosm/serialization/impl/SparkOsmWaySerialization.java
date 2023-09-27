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

import static org.apache.spark.sql.functions.*;

import java.io.File;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import net.ljcomputing.jxosm.jaxb.Osm;

@Component
@Slf4j
public class SparkOsmWaySerialization {
    @Autowired SparkSession sparkSession;

    public Osm deserializeToOsm(File file) {
        log.debug("file: {}", file.getAbsolutePath());

        sparkSession
            .read()
            .format("com.databricks.spark.xml")
            .option("rootTag", "osm")
            .option("rowTag", "way")
            .load(file.getAbsolutePath())
            .repartition(25)
            // .select(col("*"),explode(col("tag._k"))).withColumnRenamed("col", "tag_key")
            // .select(col("*"),explode(col("tag._v"))).withColumnRenamed("col", "tag_value")
            .withColumnRenamed("_id", "osm_id")
            .drop("_changeset", "_timestamp", "_user", "_version", "_uid")
            .write()
            .mode(SaveMode.Overwrite)
            .format("avro")
            .save("/home/jim/eclipse-workspace/net.ljcomputing/jxosm/out/ways.avro")
            ;
        
        return null;

        // StructType schema = com.databricks.spark.xml.util.XSDToSchema.read(new File("/home/jim/eclipse-workspace/net.ljcomputing/jxosm/src/main/resources/schema/osm-jxosm.xsd"));
        // // schema.printTreeString();
        // // Dataset<Row> df = 
        // sparkSession
        //     .read()
        //     .format("com.databricks.spark.xml")
        //     .option("rootTag", "osm")
        //     .option("rowTag", "way")
        //     // .option("rowTag", "way")
        //     // .option("rowTag", "relation")
        //     // .option("rowTag", "nd")
        //     // .schema(schema)
        //     .load(file.getAbsolutePath())
        //     .repartition(25)//;
        // // df = df.withColumn("pt", concat(lit("POINT("), col("_lon"), lit(" "), col("_lat"), lit(")")));
        // // df = df.withColumn("node", posexplode(col("nd._ref")));
        // // df.printSchema();
        // // df = df
        //     .select(col("*"),posexplode(col("nd._ref")))
        //     .withColumnRenamed("col", "node")
        //     .withColumnRenamed("pos", "node_idx")
        //     .withColumnRenamed("_id", "osm_id")
        //     .drop("_changeset", "_timestamp", "_user", "_version", "_uid", "nd")
        //     .select(col("*"),explode(col("tag._k"))).withColumnRenamed("col", "tag_key")
        //     .select(col("*"),explode(col("tag._v"))).withColumnRenamed("col", "tag_value")
        //     .filter("tag_key == 'highway' and tag_value in ('living_street','motorway','motorway_link','primary','primary_link','residential','secondary','secondary_link','tertiary','tertiary_link','trunk','trunk_link','yes')")
        //     .drop("tag_key", "tag_value")
        //     // .dropDuplicates("osm_id", "tag")

        // //     ;
        // // df.show(false);
        // // df
        //     .write()
        //     .mode(SaveMode.Overwrite)
        //     .format("avro")
        //     .save("/home/jim/eclipse-workspace/net.ljcomputing/jxosm/out/ways.avro")
        //     ;
    }


    public void printAvro() {
        Dataset<Row> inDf = sparkSession.read().format("avro")
                .load("/home/jim/eclipse-workspace/net.ljcomputing/jxosm/out/ways.avro")
                // .filter("osm_id == 11777445")
                // .select(col("osm_id"), col("node_idx"), col("node"))// , explode(col("tag")))
                // .groupBy(col("osm_id"))
                // .agg(array_join(collect_list(col("node")), ",").alias("nodes"))
                ;

        inDf.printSchema();
        inDf.show(50, false);
    }
}
