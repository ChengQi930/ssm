Ext.onReady(function() {

	// 初始化标签中的Ext:Qtip属性。
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';

	var formlayout = new Ext.Panel({
		title : 'FormLayout',
		layout : 'form',
		items : [ new Ext.form.TextField({
			fieldLabel : '新闻标题'
		}), new Ext.form.TextField({
			fieldLabel : '新闻内容'
		}), new Ext.form.TextField({
			fieldLabel : '添加时间'
		}), new Ext.Button({
			text : '添加'
		}) ],
		renderTo : 'FormLayout'
	});

});