package cn.bizowner.model;



public class Node {
		
		private String uuid;
		
		private ProgressInfo progressInfo;
		
		private Node next;
		
		public Node(String uuid,String progress)
		{
				this.uuid = uuid;
				progressInfo = new ProgressInfo();
				progressInfo.setProgress(progress);
				next = null;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		

		public ProgressInfo getProgressInfo() {
			return progressInfo;
		}

		public void setProgressInfo(ProgressInfo progressInfo) {
			this.progressInfo = progressInfo;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}
		
		
		
}
