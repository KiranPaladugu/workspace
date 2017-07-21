package com.tcs.tools.yang;

import java.nio.channels.NotYetBoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tcs.tools.api.YangCardinality;
import com.tcs.tools.api.YangMember;
import com.tcs.tools.api.YangTree;
import com.test.xpath.api.TreeObject;
import com.test.xpath.api.XPathContext;
import com.test.xpath.api.XPathObject;

public class Member implements YangMember {
	private String name;
	private String[] members = { "description" };
	private String[] requiredMembers;
	private Map<String, YangMember> membersMap = new HashMap<String, YangMember>();
	private Map<String, String> cardinality = new HashMap<String, String>();
	private Object value;
	private boolean valueInquotes;
	private String indent = "";

	public YangTree toTree() {
		throw new NotYetBoundException();
	}

	public boolean validate() {
		throw new NotYetBoundException();
	}

	public String getStatementName() {
		return name;
	}

	public String[] getMemeberNames() {
		return members;
	}

	public String[] getRequiredMemberNames() {
		return requiredMembers;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}

	public List<YangMember> getAllMembers() {
		return null;
	}

	public YangMember getMember(String name) {
		return null;
	}

	public YangCardinality getCardinality() {
		return null;
	}

	public boolean hasRequiredMembers() {
		return requiredMembers.length > 0;
	}

	public YangMember getParent() {
		return null;
	}

	public String[] getMembers() {
		return members;
	}

	public void setMembers(String[] members) {
		this.members = members;
	}

	public String[] getRequiredMembers() {
		return requiredMembers;
	}

	public void setRequiredMembers(String[] requiredMembers) {
		this.requiredMembers = requiredMembers;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCardinality(Map<String, String> cardinality) {
		this.cardinality = cardinality;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public void addCardinality(String member, String cardinality) {
		this.cardinality.put(member, cardinality);
	}

	public boolean addMember(YangMember member){
		boolean add = false;
		if (this.members == null) {
			add = true;
		} else {
			for (String mbr : this.members) {
				if (mbr.equals(member.getName())) {
					add = true;
					break;
				}
			}
		}
		if (add) {
			String cardinality = this.cardinality.get(member.getName());
			if (cardinality != null) {
				switch (cardinality) {
				case YangCardinality.ONE:
					break;

				default:
					break;
				}
			}
			membersMap.put(member.getName(), member);
			return true;
		}
		throw new NotAMemberException(String.format("The Element [%s] is not a member of [%s]", member.getName(),this.getName()));
	}

	public boolean removeMember(YangMember member) {
		if (membersMap.containsKey(member.getName())) {
			return membersMap.remove(member.getName(), member);
		}
		return false;
	}

	public boolean isValueInquotes() {
		return valueInquotes;
	}

	public void setValueInquotes(boolean valueInquotes) {
		this.valueInquotes = valueInquotes;
	}

	public String getYangFormat() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(this.indent + this.name + " ");
		if (valueInquotes) {
			String val = ((String) value).trim();
			String nLine = "";
			if(val.length()>150){
				nLine = "\n  "+this.indent;
			}
			if (val.startsWith("\"") && val.endsWith("\"")) {
				buffer.append(nLine+ this.name );
			} else {
				buffer.append(nLine+"\"" + val + "\"");
			}
		} else
			buffer.append(this.value);
		if (this.membersMap.isEmpty()) {
			buffer.append(";");
		} else {
			buffer.append(" { \n");
			Set<String> keySet = membersMap.keySet();
			for (String key : keySet) {
				YangMember member = membersMap.get(key);
				if (member instanceof Member) {
					((Member) member).setIndent(this.indent + "  ");
				}
				buffer.append(member.getYangFormat() + "\n");
			}
			buffer.append(this.indent + "}");
		}
		return buffer.toString();
	}

	@Override
	public String toString() {
		return "Member [name=" + name + ", members=" + Arrays.toString(members) + ", requiredMembers="
				+ Arrays.toString(requiredMembers) + ", \nmembersMap=" + membersMap + ",\n cardinality=" + cardinality
				+ ", value=" + value + "]";
	}

	public String getIndent() {
		return indent;
	}

	public void setIndent(String indent) {
		this.indent = indent;
	}

    @Override
    public YangMember getNext() {
        return null;
    }

    @Override
    public YangMember getPrevious() {
        return null;
    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public String evaluate(String var) {
        return null;
    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    @Override
    public boolean hasParent() {
        return false;
    }

    @Override
    public boolean hasChildren() {
        return !membersMap.isEmpty();
    }

    @Override
    public YangMember findPathObject(String xpath) {
        return null;
    }

    @Override
    public boolean validatePath(String xpath) {
        return false;
    }

    @Override
    public XPathContext getContext() {
        return null;
    }

    @Override
    public boolean isParent() {
        return false;
    }

}
