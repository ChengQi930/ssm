Ext
		.onReady(function() {
			Ext.BLANK_IMAGE_URL = "../Ext/resources/images/default/s.gif";
			Ext.QuickTips.init();
			Ext.form.Field.prototype.msgTarget = "qtip";

			var tabPanel = new Ext.TabPanel(
					{
						id : 'mainTab',
						activeTab : 0,// 默认激活第一个tab页
						animScroll : true,// 使用动画滚动效果
						enableTabScroll : true,// tab标签超宽时自动出现滚动按钮
						items : [ {
							title : '欢迎页面',
							height : 600,
							closable : false,// 允许关闭
							html : '<div style="height:600px;padding-top:200px;text-align: center;"><font size = 6>Come On Baby</font></div>'
						} ]
					});

			/*
			 * var MyButton = new Ext.Button({ text : "点击更换主题", handler :
			 * function() { MyWindow.show(); } })
			 */
			var viewPort = new Ext.Viewport({
				title : "后台管理系统",
				layout : "border",
				items : [/*
							 * { title : "标题栏", region : "north", height : 120,
							 * collapsible : true, html : "<br><center><font
							 * size = 6></font></center>", items : [ MyButton ] },
							 */{
					title : "导航栏",
					region : "west",
					width : 200,
					items : menu,
					collapsible : true,
					split : true,
					autoScroll : true
				}, {
					title : "操作区",
					region : "center",
					items : tabPanel,
					autoScroll : true
				} ]
			});
		});