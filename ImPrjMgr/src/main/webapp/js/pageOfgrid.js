//预加载
Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.data.*' ]

);

Ext.onReady(function() {
	// 创建Model
	Ext.define('User', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'name',
			mapping : 'name'
		}, {
			name : 'sex',
			mapping : 'sex'
		}, {
			name : 'age',
			mapping : 'age'
		} ]
	})
	// 创建数据源
	var store = Ext.create('Ext.data.Store', {
		model : 'User',

		// 设置分页大小
		pageSize : 5,
		proxy : {
			type : 'ajax',
			url : 'getUsersServlet',
			reader : {
				// 数据格式为json
				type : 'json',
				root : 'bugs',
				// 获取数据总数
				totalProperty : 'totalCount'
			}
		},
		autoLoad : true
	});

	// 创建grid
	var grid = Ext.create('Ext.grid.Panel', {
		store : store,
		columns : [ {
			text : '姓名',
			width : 120,
			dataIndex : 'name',
			sortable : true
		}, {
			text : '性别',
			width : 120,
			dataIndex : 'sex',
			sortable : true
		}, {
			text : '年龄',
			width : 120,
			dataIndex : 'age',
			sortable : true
		} ],
		height : 200,
		width : 480,
		x : 20,
		y : 40,
		title : 'ExtJS4 Grid分页查询示例示例',
		renderTo : 'grid',

		// 分页功能
		bbar : Ext.create('Ext.PagingToolbar', {
			store : store,
			displayInfo : true,
			displayMsg : '显示{0}-{1}条，共计{2}条',
			emptyMsg : "没有数据"
		})
	})
	store.loadPage(1);
})
