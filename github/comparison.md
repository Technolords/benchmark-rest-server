# Comparison

## Performance run 1
Test is with validation (of response), and has a run time of 60 secs,
with 30 secs of ramp up.

Module | Tps | Avg | Remarks
-------|-----|-----|--------
grizzly1-servlet-raw-1.0.0-SNAPSHOT.jar | 1973.0 | 133
grizzly2-resource-annotated-1.0.0-SNAPSHOT.jar | 1612.7 | 158
jetty9-camel-cxf-1.0.0-SNAPSHOT.jar | 937.9 | 137
jetty9-camel-raw-1.0.0-SNAPSHOT.jar | 1322.3 | 193
jetty9-cxf-1.0.0-SNAPSHOT.jar | 2086.3 | 126
jetty9-dropwizard-1.0.0-SNAPSHOT.jar | 812.0 | 148 | spam on console
jetty9-servlet-annotated-1.0.0-SNAPSHOT.jar | 1334.4 | 189
jetty9-servlet-raw-1.0.0-SNAPSHOT.jar | 2146.2 | 118
netty4-http-1.0.0-SNAPSHOT.jar | 2042.7 | 124 | spammy
undertow-raw-1.0.0-SNAPSHOT.jar | 2016.4 | 128
vertx3-core-1.0.0-SNAPSHOT.jar | 1911.9 | 69
vertx3-reactive-1.0.0-SNAPSHOT.jar | 857.8 | 222
vertx3-web-1.0.0-SNAPSHOT.jar | 843.0 | 234

## Performance run 2
Selecting four cases from above, the three fastest and most stable one (avg wise).<br/>
Test is without validation (of response), longer run time and using 300 threads.

Module | Reference| Tps | Avg | Remarks
-------|----------|-----|-----|--------
jetty9-cxf-1.0.0-SNAPSHOT.jar | 2086.3,  Avg: 126 | 3488.4/s | 47
vertx3-core-1.0.0-SNAPSHOT.jar | 1911.9,  Avg: 69 | 2300.2/s | 31
jetty9-servlet-raw-1.0.0-SNAPSHOT.jar | 2146.2, Avg: 118 | 2705.3/s | 62
netty4-http-1.0.0-SNAPSHOT.jar | 2042.7,  Avg: 124 | 4228.2/s | 56 | Very variable, load gen on 100% CPU

## Performance run 3
Select two cases from above, the fastest and re-introduced the dropwizard
after changing configuration (more threads, and tone down of logging).<br/>
Test is without validation (of response), and using 40 threads.


Module | Reference| Tps | Avg | Remarks
-------|----------|-----|-----|--------
netty4-http-1.0.0-SNAPSHOT.jar | 4228.2/s, Avg: 56 | 4616.0/s | 8
jetty9-cxf-1.0.0-SNAPSHOT.jar | 3488.4/s, Avg: 47 | 3821.8/s | 9
jetty9-dropwizard-1.0.0-SNAPSHOT.jar | | 3224.4/s | 9

