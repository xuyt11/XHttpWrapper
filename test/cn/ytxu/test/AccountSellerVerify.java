package cn.ytxu.test;

import java.util.List;

/**
 * @version 1.4.0
	 * @requestDesc Account - 提交验证信息（交易顾问)
	 */
	public class AccountSellerVerify extends ResponseEntity {

		private Data data;

		public Data getData() {return data;}
		public void setData(Data data) {this.data = data;}

		public static class Data {
			private String weibo_url;
			private String summary;
			private List<MemberTradehistory> member_tradehistory;
			private String weixin_number;
			private String first_name;

			public String getWeiboUrl() {return weibo_url;}
			public String getSummary() {return summary;}
			public List<MemberTradehistory> getMemberTradehistory() {return member_tradehistory;}
			public String getWeixinNumber() {return weixin_number;}
			public String getFirstName() {return first_name;}
			public void setWeiboUrl(String weibo_url) {this.weibo_url = weibo_url;}
			public void setSummary(String summary) {this.summary = summary;}
			public void setMemberTradehistory(List<MemberTradehistory> member_tradehistory) {this.member_tradehistory = member_tradehistory;}
			public void setWeixinNumber(String weixin_number) {this.weixin_number = weixin_number;}
			public void setFirstName(String first_name) {this.first_name = first_name;}

			public static class MemberTradehistory {
				private long project_type;
				private long project_stage;
				private String project_name;
				private String trade_date;

				public long getProjectType() {return project_type;}
				public long getProjectStage() {return project_stage;}
				public String getProjectName() {return project_name;}
				public String getTradeDate() {return trade_date;}
				public void setProjectType(long project_type) {this.project_type = project_type;}
				public void setProjectStage(long project_stage) {this.project_stage = project_stage;}
				public void setProjectName(String project_name) {this.project_name = project_name;}
				public void setTradeDate(String trade_date) {this.trade_date = trade_date;}

				@Override
				public String toString() {
					return "MemberTradehistory{" +
							"project_type=" + project_type +
							", project_stage=" + project_stage +
							", project_name='" + project_name + '\'' +
							", trade_date='" + trade_date + '\'' +
							'}';
				}
			}

			@Override
			public String toString() {
				return "Data{" +
						"weibo_url='" + weibo_url + '\'' +
						", summary='" + summary + '\'' +
						", member_tradehistory=" + member_tradehistory +
						", weixin_number='" + weixin_number + '\'' +
						", first_name='" + first_name + '\'' +
						'}';
			}
		}

	@Override
	public String toString() {
		return "AccountSellerVerify{" +
				"data=" + data + super.toString() +
				'}';
	}
}
