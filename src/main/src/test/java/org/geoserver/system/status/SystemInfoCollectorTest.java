/* (c) 2017 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */

package org.geoserver.system.status;

import static org.hamcrest.CoreMatchers.equalTo;

import java.util.List;
import org.geoserver.platform.GeoServerExtensions;
import org.geoserver.test.GeoServerSystemTestSupport;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class SystemInfoCollectorTest extends GeoServerSystemTestSupport {

    @Rule public ErrorCollector collector = new ErrorCollector();

    @Test
    public void testMetricCollector() {
        final OSHISystemInfoCollector systemInfoCollector =
                GeoServerExtensions.bean(OSHISystemInfoCollector.class);
        final Metrics collected = systemInfoCollector.retrieveAllSystemInfo();
        final List<MetricValue> metrics = collected.getMetrics();
        for (MetricValue m : metrics) {
            collector.checkThat(
                    "Metric for " + m.getName() + " available but value is not retrieved",
                    (m.getAvailable() && !m.getValue().equals(BaseSystemInfoCollector.DEFAULT_VALUE)
                            || (!m.getAvailable()
                                    && m.getValue().equals(BaseSystemInfoCollector.DEFAULT_VALUE))),
                    equalTo(true));
        }
    }
}
