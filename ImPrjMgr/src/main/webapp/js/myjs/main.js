VirfirMenu = function() {
 VirfirMenu.superclass.constructor.call(this, {
  autoScroll : true,
  animate : true,
  border : false,
  rootVisible : false,
  root : new Ext.tree.TreeNode( {
   text : 'Vifir.com',
   draggable : false,
   expanded : true
  })
 });
 this.root.appendChild(new Ext.tree.TreeNode( {
  id:"articlePanel",
  text : "发表文章",
  icon:"images/im2.gif",
  url:"article/add.htm",
  listeners:{'click':function(){
      var panel=Ext.getCmp("writeTopicPanel");
      if(!panel){
       panel=new WriteTopicPanel();
      }
      main.openTab(panel);
      }} 
 }));
 this.articleNode=new Ext.tree.AsyncTreeNode( {
  text : "文章管理",
  id:"root_article",
  loader:new Ext.tree.TreeLoader({url:"student.do?cmd=getSubCate"})  
 }); 
 this.photoNode=new Ext.tree.AsyncTreeNode( {
  text : "照片管理",
  id:"root_photo",
  loader:new Ext.tree.TreeLoader({url:"student.do?cmd=getSubCate"})
 });
 this.root.appendChild(this.articleNode);
 this.root.appendChild(this.photoNode);
 this.on("click",function(node,eventObject)
    {
     if(node==this.articleNode||this.articleNode.contains(node)){
      var panel=Ext.getCmp("topicListPanel");
      if(!panel){
        panel=new TopicListManage();
        removeTopic=function(id){
         panel.grid.getSelectionModel().selectRecords([panel.store.getById(id)]);
         panel.removeData();};
        editTopic=function(id){
         panel.grid.getSelectionModel().selectRecords([panel.store.getById(id)]);
         panel.edit();
         };
       }
      main.openTab(panel);
      if(this.articleNode!==node){
        panel.store.baseParams.myCategoryId=node.id;
        panel.currentMyCategory={id:node.id,name:node.text};
   }else panel.currentMyCategory=null;
      panel.store.baseParams.mine=true;
      panel.store.removeAll();
      panel.store.reload();
     } else if(this.photoNode==node||this.photoNode.contains(node)){
      var panel=Ext.getCmp("albumListPanel");
        if(!panel){
          panel=new AlbumListManage();
          removeAlbum=function(id){
           panel.grid.getSelectionModel().selectRecords([panel.store.getById(id)]);
           panel.removeData();};
          editAlbum=function(id){
           panel.grid.getSelectionModel().selectRecords([panel.store.getById(id)]);
           panel.edit();
           };
         }
        main.openTab(panel);        
        if(this.photoNode!==node){
        panel.store.baseParams.myCategoryId=node.id;
        panel.currentMyCategory={id:node.id,name:node.text};
        }else panel.currentMyCategory=null;
        panel.store.baseParams.mine=true;
        panel.store.removeAll();
        panel.store.reload();
     }
    },this); 
 this.root.appendChild(new Ext.tree.TreeNode( {
  text : "分类管理",
  listeners:{'click':function(){
      var panel=Ext.getCmp("userCategoryPanel");
      if(!panel){
       panel=new UserCategoryManage();
      }
      main.openTab(panel);
      }
     }
 }));
  this.root.appendChild(new Ext.tree.TreeNode( {
  text : "管理评论",
  icon:"images/im2.gif",
  url:"http://www.baidu.com",
  listeners:{ 'click':function(node) {
   var id=node.id;
    p=main.getComponent(id);
    if(!p){
     p=createTab(node);
     main.add(p);
    }
    main.setActiveTab(p);  
   main.openTab(p);
   }
  }
 }));
  this.root.appendChild(new Ext.tree.TreeNode( {
  id:"msgPanel",
  text : "管理留言",
  icon:"images/im2.gif",
  url:"http://www.baidu.com",
  listeners:{ 'click':function(node) {
   var id=node.id;
    p=main.getComponent(id);
    if(!p){
     p=createTab(node);
     main.add(p);
    }
    main.setActiveTab(p);  
   main.openTab(p);
   }
  }
 }));

};
Ext.extend(VirfirMenu, Ext.tree.TreePanel);