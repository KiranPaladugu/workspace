package com.tcs.tools.yang;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.tcs.tools.api.YangMember;

public class YangMemberFactory {
	private String propFileName = "yangMemConf.properties";
	private String statementsKey = "yang.statements";
	private String stmtMembersKey = "yang.%s.members";
	private String stmtMemberReqiredKey = "yang.%s.members.required";
	private String stmtMemberCardinalityKey = "yang.%s.member.%s.cardinality";
	private String mbrValFormatQuotes = "yang.%s.format.value.quotes";
	private boolean load = false;
	private Properties yangProperties;
	private List<String> statments = new ArrayList<String>();
	private static YangMemberFactory factory = new YangMemberFactory();

	public boolean canCreateMember(String name) {
		load();
		if (statments.contains(name)) {
			return true;
		}
		return false;
	}

	private void load() {
		if (load)
			return;
		InputStream fileStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propFileName);
		if (fileStream != null) {
			yangProperties = new Properties();
			try {
				yangProperties.load(fileStream);
				load = true;
				updateStatements();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public List<String> getAvailableStatements() {
		load();
		return statments;
	}

	private void updateStatements() {
		String stmts = yangProperties.getProperty(statementsKey);
		if (stmts != null && stmts.trim().length() > 0) {
			String[] stmtsList = stmts.trim().split(",");
			if (stmtsList.length > 0)
				statments = new ArrayList<String>();
			for (String stmt : stmtsList) {
				statments.add(stmt.trim());
			}
		}
	}


	public String[] getMemeberNames(String statement) {
		load();
		if (statments.contains(statement)) {
			String mems = this.yangProperties.getProperty(String.format(stmtMembersKey, statement));
			if (mems != null && mems.trim().length() > 0) {
				return mems.trim().split(",");
			}

		}
		return null;
	}

	public List<String> getMemeberNamesList(String statement) {
		String[] memsList = getMemeberNames(statement);
		if (memsList != null && memsList.length > 0) {
			List<String> tmpList = new ArrayList<String>();
			for (String member : memsList) {
				tmpList.add(member.trim());
			}
			return tmpList;
		}
		return null;
	}

	public String[] getMemeberRequiredNames(String statement) {
		load();
		if (statments.contains(statement)) {
			String mems = this.yangProperties.getProperty(String.format(stmtMemberReqiredKey, statement));
			if (mems != null && mems.trim().length() > 0) {
				return mems.trim().split(",");
			}
		}
		return null;
	}

	public List<String> getMemeberRequiredNamesList(String statement) {
		String[] memsList = getMemeberRequiredNames(statement);
		if (memsList != null && memsList.length > 0) {
			List<String> tmpList = new ArrayList<String>();
			for (String member : memsList) {
				tmpList.add(member.trim());
			}
			return tmpList;
		}
		return null;
	}

	public String getMemeberCardinality(String statement, String member) {
		load();
		if (statments.contains(statement)) {
			return this.yangProperties.getProperty(String.format(stmtMemberCardinalityKey, statement, member));

		}
		return null;
	}
	
	public boolean isVauleInQuotes(String memberName){
		load();
		String boolVal =this.yangProperties.getProperty(String.format(mbrValFormatQuotes, memberName));
		if(boolVal!=null){
			return Boolean.parseBoolean(boolVal);
		}
		return false;
	}

	public static void main(String args[]) {
		YangMemberFactory factory = new YangMemberFactory();
		List<String> statements = factory.getAvailableStatements();
		for (String statement : statements) {
			List<String> members = factory.getMemeberNamesList(statement);
			if (members == null) {
				System.out.println(String.format("Members are not defined for the statement [%s]", statement));
				continue;
			}
			for (String member : members) {
				String cardinality = factory.getMemeberCardinality(statement, member);
				System.out.println(String.format("Cardinality of the [%s]of statement [%s] is [%s]", member, statement,
						cardinality));
			}
		}
	}
	
	public static YangMember createNewYangMember(String memberName){
		return createNewYangMember(memberName, null );
	}

	public static YangMember createNewYangMember(String memberName , Object value){
		if(factory.canCreateMember(memberName)){
			Member member = new Member();
			member.setValue(value);
			member.setValueInquotes(factory.isVauleInQuotes(memberName));
			member.setName(memberName);
			String[] members = factory.getMemeberNames(memberName);
			member.setMembers(members);
			member.setRequiredMembers(factory.getMemeberRequiredNames(memberName));
			Map<String, String> cardinals= new HashMap<String, String>();
			if(members!=null && members.length>0){
				for(String mbr:members){
					cardinals.put(mbr, factory.getMemeberCardinality(memberName, mbr));
				}
			}
			if(cardinals.size()>0){
				member.setCardinality(cardinals);
			}
			if(member.getRequiredMembers()!=null && member.getRequiredMembers().length>0){
				for(String str:member.getRequiredMemberNames()){
					member.addMember(createNewYangMember(str,"memberValue" ));
				}
			}
			return member;
		}
		return null;
	}
}
