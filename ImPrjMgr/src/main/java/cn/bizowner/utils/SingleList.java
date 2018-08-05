package cn.bizowner.utils;

import java.util.ArrayList;
import java.util.List;

import cn.bizowner.model.Node;
import cn.bizowner.model.ProgressInfo;


public class SingleList {

		Node head = null;
		
		
		private static SingleList singleList = null;
		
		
		public static SingleList getInstance()
		{
				if(singleList == null)
					singleList = new SingleList();
				return singleList;
		}
		
	
		public void addTail(String uuid,String process){ 
	        Node node = new Node(uuid,process);
	        if(head==null)
	            head=node;
	        else
	        	getTailNode().setNext(node);
	    }
		
		
		public void addHead(String uuid,String process)
		{
				Node node = new Node(uuid,process);
				node.setNext(head);
				head = node;
		}
		
		public Node getTailNode(){       
	        Node nodelast = head;
	        while(nodelast.getNext()!=null){
	            nodelast = nodelast.getNext();
	        }
	        return nodelast;
	    }
		
		
		
	    public  Node search(String uuid){     
	    	Node node = head;
	        while(node!=null)
	        {
	        		if(node.getUuid().equals(uuid))
	        				return node;
	        		node = node.getNext();
	        }
	        return null;
	    }
		
		
		public  Node searchprevious(String uuid){ 
	        Node nodelast = head;
	        while(nodelast.getNext()!=null){
	            if(nodelast.getNext().getUuid().equals(uuid))
	                return nodelast;
	            nodelast = nodelast.getNext();
	        }
	        return null;
	    }
		
		
		
	    public void change(String uuid,ProgressInfo progressInfo){
	        if(search(uuid)!=null)
	            search(uuid).setProgressInfo(progressInfo);
	    }

	    
	    public void delete(String uuid){
	        if(search(uuid)!=null)
	        {
	        	Node previous = searchprevious(uuid);
	        	if(previous != null)
	        	{
	        		searchprevious(uuid).setNext(search(uuid).getNext());
	        	}
	        	else
	        	{
	        		head = search(uuid).getNext();
	        	}
	        }
	        	
	    }
	    
	    
	    
	    public List<Node> getNodeList()
	    {
	    		List<Node> listNode = new ArrayList<Node>();
	    		Node node = head;
		        while(node!=null)
		        {
		        		listNode.add(node);
		        		node = node.getNext();
		        }
		        return listNode;
	    }
	    
	    
	    public void printList(){
	        Node cur = head;
	        while(cur!=null){
	        	System.out.print(cur.getUuid()+"\t");
	        	ProgressInfo progressInfo = cur.getProgressInfo();
	            System.out.print(progressInfo.getProgress()+"\t");
	            System.out.print(progressInfo.isFinishFlag()+"\t");
	            System.out.print(progressInfo.isUpdateFlag()+"\t");
	            System.out.print(progressInfo.getError()+"\t");
	            System.out.print(progressInfo.getFinishTime()+"\t");
	            System.out.println();
	            cur=cur.getNext();
	        }
	    }
		
}
