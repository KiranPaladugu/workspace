/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import com.tcs.tools.ConnectionData.ConnectionType;

public class ConnectionTester {

	public static void main(final String[] args) throws Exception {
		final SSHConnection provider = new SSHConnection();
		provider.setRetryCount(3);
		provider.setEndOfSatement("]]>]]>");
		// provider.setSubsystemName("netconf-ecim");
		final ConnectionData connData = new ConnectionData("10.170.115.66", 830, ConnectionType.subsystem, "netconf-ecim", "test", "test");
		provider.initialize(connData);
		// provider.initialize("10.170.115.66", 830, "test", "test");
		provider.connect();
		System.out.println("Below is the response from Server::");
		Thread.sleep(1000);
		System.out.println(provider.read());
		final String hello = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><hello xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n"
				+ "<capabilities>\n" + "<capability>urn:ietf:params:netconf:base:1.0</capability>\n"
				+ "<capability>urn:ietf:params:xml:ns:netconf:base:1.0</capability>\n"
				+ "<capability>urn:ietf:params:xml:ns:netconf:capability:rollback-on-error:1.0</capability>\n"
				+ "<capability>urn:ietf:params:netconf:capability:writable-running:1.0</capability>\n"
				+ "<capability>urn:ietf:params:xml:ns:netconf:capability:candidate:1.0</capability>\n"
				+ "<capability>urn:ietf:params:xml:ns:netconf:capability:validate:1.0</capability>\n"
				+ "<capability>urn:ietf:params:netconf:capability:candidate:1.0</capability>\n"
				+ "<capability>urn:ietf:params:netconf:capability:validate:1.0</capability>\n"
				+ "<capability>urn:ericsson:com:netconf:action:1.0</capability>\n"
				+ "<capability>urn:ietf:params:netconf:capability:notification:1.0</capability>\n"
				+ "<capability>urn:ericsson:com:netconf:notification:1.1</capability>\n" + "<capability>urn:com:ericsson:ebase:0.1.0</capability>\n"
				+ "<capability>urn:com:ericsson:ebase:1.1.0</capability>\n" + "<capability>urn:com:ericsson:ebase:1.2.0</capability>\n"
				+ "<capability>urn:ericsson:com:netconf:heartbeat:1.0</capability>\n"
				+ "<capability>urn:com:ericsson:ipos:exec-cli:1.0</capability>\n" + "<capability>urn:com:ericsson:ipos:invoke-cli:1.0</capability>\n"
				+ "</capabilities>\n" + "</hello>]]>]]>\n";
		provider.write(hello);

		@SuppressWarnings("unused")
		final String getSchema = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<rpc message-id=\"4\"     xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n" + "<get>" + "<filter type=\"subtree\">"
				+ "<ManagedElement xmlns=\"urn:com:ericsson:ecim:ComTop\">" + "        <managedElementId></managedElementId>"
				+ "        <SystemFunctions xmlns=\"urn:com:ericsson:ecim:ComTop\">" + "                <systemFunctionsId>1</systemFunctionsId>"
				+ "                <SysM xmlns=\"urn:com:ericsson:ecim:ComSysM\"><sysMId>1</sysMId><Schema></Schema></SysM>"
				+ "        </SystemFunctions>" + "</ManagedElement>" + "</filter>" + "</get>\n" + "</rpc>\n" + "]]>]]>\n";

		final String get = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<rpc message-id=\"4\"     xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n" + "<get></get>\n" + "</rpc>\n" + "]]>]]>\n";
		// System.out.println(provider.read());
		provider.write(get);
		final String response = provider.read();
		System.out.println("Below is  Get response from Server::");
		System.out.println(response);

		final String close = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<rpc message-id=\"266\"    xmlns=\"urn:ietf:params:xml:ns:netconf:base:1.0\">\n" + "<close-session/>\n" + "</rpc>\n" + "]]>]]>\n";
		provider.write(close);
		// System.out.println(provider.read());
		provider.disConnect();
	}
}
