package com.test.models;


public class TranferableObject implements TransferableDataObject{

	/**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String name;
	private long length;
	private byte data[];
	private int type;
	private int count;
	
	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#getLength()
	 */
	@Override
	public long getLength() {
		return length;
	}
	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#setLength(long)
	 */
	@Override
	public void setLength(long length) {
		this.length = length;
	}
	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#getSize()
	 */
	@Override
	public long getSize() {
		return data.length;
	}

	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#getData()
	 */
	@Override
	public byte[] getData() {
		return data;
	}
	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#setData(byte[])
	 */
	@Override
	public void setData(byte[] data) {
		this.data = data;
	}
	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#getType()
	 */
	@Override
	public int getType() {
		return type;
	}
	/* (non-Javadoc)
	 * @see com.test.models.TransferableDataObject#setType(int)
	 */
	@Override
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int getCount() {
		return count;
	}
	
	
}
