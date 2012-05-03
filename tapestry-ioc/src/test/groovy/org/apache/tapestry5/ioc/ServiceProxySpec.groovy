package org.apache.tapestry5.ioc

class ServiceProxySpec extends AbstractRegistrySpecification {

  def "shutdown deactivaties proxies"() {
    buildRegistry FredModule, BarneyModule

    def fred = getService "Fred", Runnable

    fred.run()

    shutdown()

    when:

    fred.run()

    then:

    RuntimeException ex = thrown()

    ex.message.contains "Proxy for service Fred is no longer active because the IOC Registry has been shut down."

    fred.toString() == "<Proxy for Fred(java.lang.Runnable)>"

    cleanup:

    registry = null
  }

  def "show that services defined without a implementation are instantiated immediately"() {
    buildRegistry NonProxiedServiceModule

    when:

    def holder = getService StringHolder

    then:

    holder instanceof StringHolderImpl // and not some proxy
  }

  def "service builder methods with a class (not interface) return type are not proxied, but are cached"() {

    buildRegistry ConcreteServiceBuilderModule

    when:

    def h1 = getService StringHolder

    then: "not proxied"

    h1 instanceof StringHolderImpl

    when:

    def h2 = getService StringHolder

    then: "cached"

    h2.is h1
  }

  def "verify that a proxy for an autobuilt object lazily instantiates the implementation"() {

    buildRegistry()

    expect:
    IntegrationTestFixture.countingGreeterInstantiationCount == 0

    when: "obtaining the proxy"

    def g = proxy Greeter, CountingGreeterImpl

    then: "the implementation is not yet instantiated"

    IntegrationTestFixture.countingGreeterInstantiationCount == 0

    when: "invoking toString() on the proxy"

    assert g.toString() == "<Autoreload proxy org.apache.tapestry5.ioc.CountingGreeterImpl(org.apache.tapestry5.ioc.Greeter)>"

    then: "the implementation is not yet instantiated"

    IntegrationTestFixture.countingGreeterInstantiationCount == 0

    when: "invoking other methods on the proxy"

    assert g.greeting == "Hello"

    then: "the implementation is now instantiated"

    IntegrationTestFixture.countingGreeterInstantiationCount == 1
  }

}
