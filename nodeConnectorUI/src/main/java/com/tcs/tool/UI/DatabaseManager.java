/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import com.tcs.application.Application;
import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;
import com.tcs.tools.UI.utils.UIConstants;

public class DatabaseManager implements Subscriber {

	private String home = null;
	private final String dirName = ".tcs";
	private final String dataDir = "data";
	private String dbPath = null;
	private final String fileName = "nodeConnector.db";
	private Map<MessageMetaData, String> saveMap;
	private String dbFilePath = null;

	public DatabaseManager() {
		home = System.getProperty("user.home");
		if (home != null) {
			dbPath = home + File.separator + dirName + File.separator + dataDir;
		}
		Application.getSubscriptionManager().subscribe(this, UIConstants.DO_SAVE_MESSAGE, UIConstants.LOAD_DATA_BASE, UIConstants.GET_SAVED_MESSAGE,
				UIConstants.GET_SAVED_MESSAGES_AS_LIST, UIConstants.DELETE_MESSAGE_REQUEST);

	}

	@SuppressWarnings("unchecked")
	public void loadDb() {
		if (dbPath != null) {
			dbFilePath = dbPath + File.separator + fileName;
			final File dbFile = new File(dbFilePath);
			if (dbFile.exists() && dbFile.canRead()) {
				try {
					final ObjectInputStream ooStream = new ObjectInputStream(new FileInputStream(dbFile));
					Object obj = null;
					while ((obj = ooStream.readObject()) != null) {
						if (obj instanceof Map<?, ?>) {
							saveMap = (Map<MessageMetaData, String>) obj;
							break;
						}
					}
					ooStream.close();
					if (saveMap != null) {
						Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_LOAD_SUCCESS, this);
					} else Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_LOAD_FAIL, this);
				} catch (final Exception e) {
					Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_LOAD_FAIL, this);
					e.printStackTrace();
				}
			} else {
				// Create DB;
				if (!dbFile.getParentFile().exists()) {
					dbFile.getParentFile().mkdirs();
				}
				try {
					dbFile.createNewFile();
					saveMap = new HashMap<>();
					saveMap(saveMap, dbFile.getAbsolutePath());
					Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_CREATE_SUCCESS, this);
				} catch (final Exception e) {
					e.printStackTrace();
					Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_LOAD_FAIL, this);
					return;
				}
				Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_LOAD_SUCCESS, this);

			}
		} else {
			Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_LOAD_FAIL, this);
		}
	}

	private void remove(final MessageMetaData metaData) {
		if (saveMap.containsKey(metaData)) {
			final Object obj = saveMap.remove(metaData);
			if (obj != null) {
				try {
					this.saveMap(saveMap, dbFilePath);
					Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVED_MESSAGE_DELETE_SUCCESS, this, metaData);
					return;
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

		}
		Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVED_MESSAGE_DELETE_FAIL, this, metaData);
	}

	private void saveMap(final Map<MessageMetaData, String> map, final String dbFilePath) throws Exception {
		final File file = new File(dbFilePath);
		if (file.exists() && file.canWrite() && map != null) {
			try {
				final ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
				outStream.writeObject(map);
				outStream.flush();
				outStream.close();
			} catch (final Exception e) {
				throw e;
			}
		}
	}

	private void getMessage(final String messageId) {
		final MessageMetaData data = new MessageMetaData();
		data.setMessageId(messageId);
		getMessage(data);
	}

	@SuppressWarnings("unchecked")
	private void getMessage(final MessageMetaData data) {
		final String fileName = saveMap.get(data);
		final File file = new File(dbPath + File.separator + fileName);
		if (file.exists() && file.canRead()) {
			try {
				final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
				final Object obj = inputStream.readObject();
				if (obj != null) {
					final UISavableMessage<String> message = (UISavableMessage<String>) obj;
					Application.getSubscriptionManager().notifySubscriber(UIConstants.GET_SAVEDMESSAGE_SUCCSS, this, message);
					inputStream.close();
					return;
				}
				inputStream.close();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		Application.getSubscriptionManager().notifySubscriber(UIConstants.GET_SAVEDMESSAGE_FAIL, this, data);
	}

	public boolean saveMessage(final UISavableMessage<String> message, MessageMetaData metaData) {
		boolean operation = true;
		if (metaData == null) {
			metaData = new MessageMetaData();
			metaData.setMessageId(message.getId());
			metaData.setMessageName(message.getName());
		}
		if (saveMap.containsKey(metaData)) {
			operation = updateMessage(metaData, message);
		} else {
			final String uid = UUID.randomUUID().toString();
			try {
				final String path = dbPath + File.separator + uid;
				writeMessage(message, path);
				saveMap.put(metaData, uid);
				saveMap(saveMap, dbFilePath);
			} catch (final Exception e) {
				e.printStackTrace();
				operation = false;
			}
		}
		if (operation) {
			Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVE_MESSAGE_SUCCESS, message, metaData);
		} else {
			Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVE_MESSAGE_FAIL, message, metaData);
		}
		return operation;
	}

	private boolean updateMessage(final MessageMetaData metaData, final UISavableMessage<String> message) {
		boolean flag = true;
		final String fileName = saveMap.get(metaData);
		final String filePath = dbPath + File.separator + fileName;
		try {
			writeMessage(message, filePath);
		} catch (final Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	private boolean writeMessage(final UISavableMessage<String> message, final String filePath) throws Exception {
		boolean flag = false;
		final File saveFile = new File(filePath);
		if (!saveFile.exists()) {
			saveFile.createNewFile();
		}
		if (saveFile.canWrite()) {
			try {
				final ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(saveFile));
				outStream.writeObject(message);
				outStream.flush();
				outStream.close();
				flag = true;
			} catch (final Exception e) {
				flag = false;
				throw e;
			}
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized void onSubscriptionEvent(final SubscriptionEvent event) {
		final Object obj = event.getData();
		switch (event.getEvent()) {
		case UIConstants.DO_SAVE_MESSAGE:
			if (obj != null) {
				if (obj instanceof UISavableMessage<?>) {
					MessageMetaData meta = null;
					final UISavableMessage<String> newMessage = (UISavableMessage<String>) obj;
					if (newMessage.getId() == null || newMessage.getId().length() == 0) {
						newMessage.setId(UUID.randomUUID().toString());
					}
					if (event.getSource() != null && event.getSource() instanceof MessageMetaData) {
						meta = (MessageMetaData) event.getSource();
					}
					saveMessage(newMessage, meta);
				}
			}
			break;
		case UIConstants.LOAD_DATA_BASE:
			loadDb();
			break;
		case UIConstants.GET_SAVED_MESSAGE:
			if (event.getData() != null) {
				if (event.getData() instanceof MessageMetaData) {
					final MessageMetaData messageId = (MessageMetaData) event.getData();
					if (messageId != null) {
						getMessage(messageId);
					}
				} else if (event.getData() instanceof String) {
					final String messageId = (String) event.getData();
					getMessage(messageId);
				}
			}
			break;
		case UIConstants.DELETE_MESSAGE_REQUEST:
			if (obj != null && obj instanceof MessageMetaData) {
				remove((MessageMetaData) obj);
			}
			break;
		case UIConstants.GET_SAVED_MESSAGES_AS_LIST:
			sendMessageList();
		default:
			break;
		}
	}

	private void sendMessageList() {
		List<MessageMetaData> data = new Vector<>();
		if (saveMap != null) {
			data = new Vector<MessageMetaData>(saveMap.keySet());
		}
		if (!data.isEmpty()) {
			Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVED_MESSAGES_AS_LIST, this, data);
		}
	}

}
