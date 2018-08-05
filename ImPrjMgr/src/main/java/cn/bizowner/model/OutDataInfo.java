package cn.bizowner.model;

import java.util.Date;

public class OutDataInfo {

			private String id;
			
			private Object o;

			private Date finishTime;

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public Object getO() {
				return o;
			}

			public void setO(Object o) {
				this.o = o;
			}

			public Date getFinishTime() {
				return finishTime;
			}

			public void setFinishTime(Date finishTime) {
				this.finishTime = finishTime;
			} 
}
