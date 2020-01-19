/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.devicestate.kafka;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;

import com.sitewhere.grpc.kafka.serdes.SiteWhereSerdes;
import com.sitewhere.microservice.kafka.KafkaStreamPipeline;

/**
 * Kafka pipeline for handling device state interactions
 */
public class DeviceStatePipeline extends KafkaStreamPipeline {

    /*
     * @see
     * com.sitewhere.spi.microservice.kafka.IKafkaStreamPipeline#buildStreams(org.
     * apache.kafka.streams.StreamsBuilder)
     */
    @Override
    public void buildStreams(StreamsBuilder builder) {
	builder.stream(
		getMicroservice().getKafkaTopicNaming().getOutboundEventsTopic(getTenantEngine().getTenantResource()),
		Consumed.with(Serdes.String(), SiteWhereSerdes.forEnrichedEventPayload()))
		.process(new DeviceStatePersistenceProcessorSupplier(), new String[0]);
    }
}
