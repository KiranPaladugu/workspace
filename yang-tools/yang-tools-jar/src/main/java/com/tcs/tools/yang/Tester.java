package com.tcs.tools.yang;

import com.tcs.tools.api.YangMember;

public class Tester {
	public static void main(String args[]) throws Exception{
		YangMember module = YangMemberFactory.createNewYangMember("module" ,"NameOfTheModule" );
		YangMember import1 = YangMemberFactory.createNewYangMember("include" ,"includable");
		YangMember import2 = YangMemberFactory.createNewYangMember("import", "importable");
		YangMember import3 = YangMemberFactory.createNewYangMember("revision" ,"SomeDummyrevision");
		YangMember desc = YangMemberFactory.createNewYangMember("description", "This is Description of the member description");
		YangMember xyz = YangMemberFactory.createNewYangMember("module", "SOMEVALUEXYY");
		import3.addMember(desc);
		module.addMember(import1);
//		import2.addMember(xyz);
		module.addMember(import2);
		module.addMember(import3);
		
		System.out.println(module.getYangFormat());
	}
}
