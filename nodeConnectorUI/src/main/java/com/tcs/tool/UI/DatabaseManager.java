/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.io.*;
import java.util.*;

import com.tcs.application.*;
import com.tcs.tools.UI.utils.UIConstants;

public class DatabaseManager implements Subscriber {

    /**
     * 
     */
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
        Application.getSubscriptionManager().subscribe(this, UIConstants.DO_SAVE_MESSAGE, UIConstants.LOAD_DATA_BASE,
                UIConstants.GET_SAVED_MESSAGE, UIConstants.GET_SAVED_MESSAGES_AS_LIST,UIConstants.DELETE_MESSAGE_REQUEST);

    }

    @SuppressWarnings("unchecked")
    public void loadDb() {
        if (dbPath != null) {
            dbFilePath = dbPath + File.separator + fileName;
            File dbFile = new File(dbFilePath);
            if (dbFile.exists() && dbFile.canRead()) {
                try {
                    ObjectInputStream ooStream = new ObjectInputStream(new FileInputStream(dbFile));
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
                    } else
                        Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_LOAD_FAIL, this);
                } catch (Exception e) {
                    Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_LOAD_FAIL, this);
                    e.printStackTrace();
                }
            } else {
                //Create DB;
                if (!dbFile.getParentFile().exists()) {
                    dbFile.getParentFile().mkdirs();
                }
                try {
                    dbFile.createNewFile();
                    saveMap = new HashMap<>();
                    saveMap(saveMap, dbFile.getAbsolutePath());
                    Application.getSubscriptionManager().notifySubscriber(UIConstants.DB_CREATE_SUCCESS, this);
                } catch (Exception e) {
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
    
    private void remove(MessageMetaData metaData){
        if(saveMap.containsKey(metaData)){
            Object obj = saveMap.remove(metaData);
            if(obj!=null){
                try {
                    this.saveMap(saveMap, dbFilePath);
                    Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVED_MESSAGE_DELETE_SUCCESS, this, metaData);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        }
        Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVED_MESSAGE_DELETE_FAIL, this, metaData);
    }

    private void saveMap(Map<MessageMetaData, String> map, String dbFilePath) throws Exception {
        File file = new File(dbFilePath);
        if (file.exists() && file.canWrite() && map != null) {
            try {
                ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(file));
                outStream.writeObject(map);
                outStream.flush();
                outStream.close();
            } catch (Exception e) {
                throw e;
            }
        }
    }

    private void getMessage(String messageId) {
        MessageMetaData data = new MessageMetaData();
        data.setMessageId(messageId);
        getMessage(data);
    }

    /**
     * @param data
     */
    @SuppressWarnings("unchecked")
    private void getMessage(MessageMetaData data) {
        String fileName = saveMap.get(data);
        File file = new File(dbPath + File.separator + fileName);
        if (file.exists() && file.canRead()) {
            try {
                ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
                Object obj = inputStream.readObject();
                if (obj != null) {
                    UISavableMessage<String> message = (UISavableMessage<String>) obj;
                    Application.getSubscriptionManager().notifySubscriber(UIConstants.GET_SAVEDMESSAGE_SUCCSS, this, message);
                    inputStream.close();
                    return;
                }
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Application.getSubscriptionManager().notifySubscriber(UIConstants.GET_SAVEDMESSAGE_FAIL, this, data);
    }

    public boolean saveMessage(UISavableMessage<String> message, MessageMetaData metaData) {
        boolean operation = true;
        if (metaData == null) {
            metaData = new MessageMetaData();
            metaData.setMessageId(message.getId());
            metaData.setMessageName(message.getName());
        }
        if (saveMap.containsKey(metaData)) {
            operation = updateMessage(metaData, message);
        } else {
            String uid = UUID.randomUUID().toString();
            try {
                String path = dbPath + File.separator + uid;
                writeMessage(message, path);
                saveMap.put(metaData, uid);
                saveMap(saveMap, dbFilePath);
            } catch (Exception e) {
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

    /**
     * @param metaData
     * @param message
     */
    private boolean updateMessage(MessageMetaData metaData, UISavableMessage<String> message) {
        boolean flag = true;
        String fileName = saveMap.get(metaData);
        String filePath = dbPath + File.separator + fileName;
        try {
            writeMessage(message, filePath);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * @param message
     * @param filePath
     * @throws Exception
     */
    private boolean writeMessage(UISavableMessage<String> message, String filePath) throws Exception {
        boolean flag = false;
        File saveFile = new File(filePath);
        if (!saveFile.exists()) {
            saveFile.createNewFile();
        }
        if (saveFile.canWrite()) {
            try {
                ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(saveFile));
                outStream.writeObject(message);
                outStream.flush();
                outStream.close();
                flag = true;
            } catch (Exception e) {
                flag = false;
                throw e;
            }
        }
        return flag;
    }

    @SuppressWarnings("unchecked")
    @Override
    public synchronized void onSubscriptionEvent(SubscriptionEvent event) {
        Object obj = event.getData();
        switch (event.getEvent()) {
        case UIConstants.DO_SAVE_MESSAGE:
            if (obj != null) {
                if (obj instanceof UISavableMessage<?>) {
                    MessageMetaData meta = null;
                    UISavableMessage<String> newMessage = (UISavableMessage<String>) obj;
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
                    MessageMetaData messageId = (MessageMetaData) event.getData();
                    if (messageId != null) {
                        getMessage(messageId);
                    }
                } else if (event.getData() instanceof String) {
                    String messageId = (String) event.getData();
                    getMessage(messageId);
                }
            }
            break;
        case UIConstants.DELETE_MESSAGE_REQUEST:
            if(obj!=null && obj instanceof MessageMetaData){
                remove((MessageMetaData) obj);
            }
            break;
        case UIConstants.GET_SAVED_MESSAGES_AS_LIST:
            sendMessageList();
        default:
            break;
        }
    }

    /**
     * 
     */
    private void sendMessageList() {
        List<MessageMetaData> data = new Vector<>();
        if (saveMap != null) {
            data = new Vector<MessageMetaData>((Collection<? extends MessageMetaData>) saveMap.keySet());
        }
        if (!data.isEmpty()) {
            Application.getSubscriptionManager().notifySubscriber(UIConstants.SAVED_MESSAGES_AS_LIST, this, data);
        }
    }

}
