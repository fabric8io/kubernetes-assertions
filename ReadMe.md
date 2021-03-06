## Kubernetes Assertions

[![CircleCI](https://circleci.com/gh/fabric8io/kubernetes-assertions.svg?style=svg)](https://circleci.com/gh/fabric8io/kubernetes-assertions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.fabric8/kubernetes-assertions/badge.svg?style=flat-square)](https://maven-badges.herokuapp.com/maven-central/io.fabric8/kubernetes-assertions/)
[![Javadocs](http://www.javadoc.io/badge/io.fabric8/kubernetes-assertions.svg?color=blue)](http://www.javadoc.io/doc/io.fabric8/kubernetes-assertions)

This library provides a bunch of helpful [assertj](http://joel-costigliola.github.io/assertj/) assertions for working with the [kubernetes-api](https://github.com/fabric8io/fabric8/tree/master/components/kubernetes-api).

### Default system test

The following code provides a default system test:

```
             assertThat(client).deployments().pods().isPodReadyForPeriod();
```

This will assert that the current project's `Deployment` creates at least one pod; that it becomes `Ready` within a time period (30 seconds by default), then that the pod keeps being `Ready` for a period (defaults to 10 seconds).

This may seem a fairly simple test case; but it catches most errors with the `Deployment` being invalid or failing to start; the pod starting then failing due to some configuration issue etc.

If your application uses [liveness checks](http://kubernetes.io/docs/user-guide/liveness/) (which are used by default with Spring Boot apps) then this test also asserts that the liveness checks keep valid for the period too. So if your application fails to connect to a database or your Camel route fails to start a route or whatever; then the test fails!

This means to improve your system tests you can just improve your liveness checks; which also helps Kubernetes manage your production environment too!

### Other examples

Some quick examples:

* [assertThat(KubernetesClient)](https://github.com/fabric8io/fabric8/blob/master/components/kubernetes-assertions/src/test/java/io/fabric8/kubernetes/assertions/Example.java#L38) helper code that is available if you add the **kubernetes-assertions** dependency.
* [assertThat(Pod)](https://github.com/fabric8io/fabric8/blob/master/components/kubernetes-assertions/src/test/java/io/fabric8/kubernetes/assertions/ExampleTest.java#L49-L50) and navigating the model 
* [assertThat(PodList)](https://github.com/fabric8io/fabric8/blob/master/components/kubernetes-assertions/src/test/java/io/fabric8/kubernetes/assertions/ExampleTest.java#L96-L102) using list navigations

### Navigating and asserting around resources

When working with Kubernetes and OpenShift resources in test cases you'll find that objects can be massively nested. 

For example even using something as simple as a Pod you need to navigate to compare its name:

```java
Pod pod = kubernetesClient.pods().inNamespace(ns).withName("foo").get();
assertThat(pod).metadata().name().isEqualTo("foo");
```

Things get even more complex when asserting a ReplicationController

```java
ReplicationController rc = kubernetesClient.replicationControllers().inNamespace(ns).withName("foo").get();
assertThat(rc).spec().template().spec().containers().first().image().isEqualTo("someDockerImageName");
```

Whats great about Kubernetes Assertions is that you can chain methods together to navigate the model; if any navigation fails you get meaninful errors in the test failure telling you exactly which object was null or list was empty or other assertion failed (e.g. a list index was out of range) etc.

For example here's some example error messages from assertj when navigation or assertions fail in [these tests](https://github.com/fabric8io/fabric8/blob/master/components/kubernetes-assertions/src/test/java/io/fabric8/kubernetes/assertions/ExampleTest.java#L111-L123):

```
    org.junit.ComparisonFailure: [podListWith2Items.items.first().metadata.name] expected:<"[shouldNotMatch]"> but was:<"[abc]">
    
    java.lang.AssertionError: [podListWith2Items.items.index]
    Expecting:
     <-1>
    to be greater than or equal to:
     <0>
```
                                
### Add it to your Maven pom.xml

To be able to use the Java code in your [Apache Maven](http://maven.apache.org/) based project add this into your pom.xml

            <dependency>
                <groupId>io.fabric8</groupId>
                <artifactId>kubernetes-assertions</artifactId>
                <version>4.0.0</version>
                <scope>test</scope>
            </dependency>
