package game;

import java.io.Serializable;
import java.util.LinkedList;

public class MessageLinkedList implements Serializable{
	
	private LinkedList<PlayerData> dataList;
	
	public MessageLinkedList(LinkedList<PlayerData> dataList) {
		// TODO Auto-generated constructor stub
		this.dataList=dataList;
	}

	public LinkedList<PlayerData> getDataList() {
		return dataList;
	}
	
	
}
