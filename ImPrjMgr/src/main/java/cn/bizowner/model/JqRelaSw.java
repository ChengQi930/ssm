package cn.bizowner.model;

public class JqRelaSw {

			private String id;
			
			private String jqId;
			
			private String swId;
			
			private String relaTime;
			
			private String userId;
			
			private Integer type;		//1表示一对一,2表示多对多

			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}

			public String getJqId() {
				return jqId;
			}

			public void setJqId(String jqId) {
				this.jqId = jqId;
			}

			public String getSwId() {
				return swId;
			}

			public void setSwId(String swId) {
				this.swId = swId;
			}

			public String getRelaTime() {
				return relaTime;
			}

			public void setRelaTime(String relaTime) {
				this.relaTime = relaTime;
			}

			public String getUserId() {
				return userId;
			}

			public void setUserId(String userId) {
				this.userId = userId;
			}

			public Integer getType() {
				return type;
			}

			public void setType(Integer type) {
				this.type = type;
			}

			
}
