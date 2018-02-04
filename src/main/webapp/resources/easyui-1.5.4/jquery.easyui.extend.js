/**
 * easyui 插件扩展
 * @param {Object} jq
 * @param {Object} region
 */
$.extend($.fn.layout.methods, {  
	/**
	 * 移除header
	 * @param {Object} jq
	 * @param {Object} region
	 */
    removeHeader: function(jq, region){  
        return jq.each(function(){  
            var panel = $(this).layout("panel",region);
            if(panel){
                panel.panel('removeHeader');
				panel.panel('resize');
            }
        });  
    },
	/**
	 * 增加header
	 * @param {Object} jq
	 * @param {Object} params
	 */
    addHeader:function(jq, params){
        return jq.each(function(){  
            var panel = $(this).layout("panel",params.region);
			var opts = panel.panel("options");
            if(panel){
				var title = params.title?params.title:opts.title;
                panel.panel('addHeader',{title:title});
				panel.panel('resize');
            }
        });
    },
    /**  
     * 面板是否存在和可见  
     * @param {Object} jq  
     * @param {Object} params  
     */  
    isVisible: function(jq, params) {   
        var panels = $.data(jq[0], 'layout').panels;   
        var pp = panels[params];   
        if(!pp) {   
            return false;   
        }   
        if(pp.length) {   
            return pp.panel('panel').is(':visible');   
        } else {   
            return false;   
        }   
    },   
    /**  
     * 隐藏除某个region，center除外。  
     * @param {Object} jq  
     * @param {Object} params  
     */  
    hidden: function(jq, params) {   
        return jq.each(function() {   
            var opts = $.data(this, 'layout').options;   
            var panels = $.data(this, 'layout').panels;   
            if(!opts.regionState){   
                opts.regionState = {};   
            }   
            var region = params;   
            function hide(dom,region,doResize){   
                var first = region.substring(0,1);   
                var others = region.substring(1);   
                var expand = 'expand' + first.toUpperCase() + others;   
                if(panels[expand]) {   
                    if($(dom).layout('isVisible', expand)) {   
                        opts.regionState[region] = 1;   
                        panels[expand].panel('close');   
                    } else if($(dom).layout('isVisible', region)) {   
                        opts.regionState[region] = 0;   
                        panels[region].panel('close');   
                    }   
                } else {   
                    panels[region].panel('close');   
                }   
                if(doResize){   
                    $(dom).layout('resize');   
                }   
            };   
            if(region.toLowerCase() == 'all'){   
                hide(this,'east',false);   
                hide(this,'north',false);   
                hide(this,'west',false);   
                hide(this,'north',true);   
            }else{   
                hide(this,region,true);   
            }   
        });   
    },   
    /**  
     * 显示某个region，center除外。  
     * @param {Object} jq  
     * @param {Object} params  
     */  
    show: function(jq, params) {   
        return jq.each(function() {   
            var opts = $.data(this, 'layout').options;   
            var panels = $.data(this, 'layout').panels;   
            var region = params;   
  
            function show(dom,region,doResize){   
                var first = region.substring(0,1);   
                var others = region.substring(1);   
                var expand = 'expand' + first.toUpperCase() + others;   
                if(panels[expand]) {   
                    if(!$(dom).layout('isVisible', expand)) {   
                        if(!$(dom).layout('isVisible', region)) {   
                            if(opts.regionState[region] == 1) {   
                                panels[expand].panel('open');   
                            } else {   
                                panels[region].panel('open');   
                            }   
                        }   
                    }   
                } else {   
                    panels[region].panel('open');   
                }   
                if(doResize){   
                    $(dom).layout('resize');   
                }   
            };   
            if(region.toLowerCase() == 'all'){   
                show(this,'east',false);   
                show(this,'north',false);   
                show(this,'west',false);   
                show(this,'north',true);   
            }else{   
                show(this,region,true);   
            }   
        });   
    },
    
    /**
     * 设置north north east west的split是否可以拖动
     * @param {[type]} jq     [description]
     * @param {[type]} params [description]
     */
    setSplitActivateState:function(jq,params){
        return jq.each(function(){
            if(params.region=="center")
                return;
            $(this).layout('panel',params.region).panel('panel').resizable(params.disabled?'disable':'enable');
        });
    },
    /**
     * 设置north north east west的split是否显示
     * @param {[type]} jq     [description]
     * @param {[type]} params [description]
     */
    setSplitVisiableState:function(jq,params){
        return jq.each(function(){
            if(params.region=="center")
                return;
            var panel = $(this).layout('panel',params.region);
            panel.panel('options').split = params.visible;
            if(params.visible){
                panel.panel('panel').addClass('layout-split-west');
            }else{
                panel.panel('panel').removeClass('layout-split-west');
            }
            //panel.panel('resize');
            $(this).layout('resize');
        });
    },
    /**
     * 设置region的收缩按钮是否可见
     * @param {[type]} jq     [description]
     * @param {[type]} params [description]
     */
    setRegionToolVisableState:function(jq,params){
        return jq.each(function(){
            if(params.region=="center")
                return;
            var panels = $.data(this, 'layout').panels;
            var panel = panels[params.region];
            var tool = panel.panel('header').find('>div.panel-tool');
            tool.css({display:params.visible?'block':'none'});
            var first = params.region.substring(0,1);   
            var others = params.region.substring(1);   
            var expand = 'expand' + first.toUpperCase() + others;  
            if(panels[expand]){
                panels[expand].panel('header').find('>div.panel-tool').css({display:params.visible?'block':'none'});
            }
        });
    }
});


$.extend($.fn.datagrid.defaults.editors, {
	combocheckboxtree : {
		init : function(container, options) {
			var editor = $('<input/>').appendTo(container);
			options.multiple = true;
			editor.combotree(options);
			return editor;
		},
		destroy : function(target) {
			$(target).combotree('destroy');
		},
		getValue : function(target) {
			return $(target).combotree('getValues').join(',');
		},
		setValue : function(target, value) {
			$(target).combotree('setValues', sy.getList(value));
		},
		resize : function(target, width) {
			$(target).combotree('resize', width);
		}
	}
});


/**
 * 扩展treegrid，使其支持平滑数据格式
 * 
 * @author 孙宇
 * 
 * @requires jQuery,EasyUI
 * 
 */
$.extend($.fn.treegrid.defaults, {
	loadFilter : function(data, parentId) {
		var opt = $(this).data().treegrid.options;
		var idField, treeField, parentField,iconCls;
		if (opt.parentField) {
			idField = opt.idField || 'id';
			treeField = opt.textField || 'text';
			parentField = opt.parentField || 'pid';
			iconCls=opt.iconCls||'iconCls';
			var i, l, treeData = [], tmpMap = [];
			for (i = 0, l = data.length; i < l; i++) {
				tmpMap[data[i][idField]] = data[i];
			}
			for (i = 0, l = data.length; i < l; i++) {
				if (tmpMap[data[i][parentField]] && data[i][idField] != data[i][parentField]) {
					if (!tmpMap[data[i][parentField]]['children'])
						tmpMap[data[i][parentField]]['children'] = [];
					data[i]['text'] = data[i][treeField];
					data[i]['iconCls'] = data[i][iconCls];
					tmpMap[data[i][parentField]]['children'].push(data[i]);
				} else {
					data[i]['text'] = data[i][treeField];
					data[i]['iconCls'] = data[i][iconCls];
					treeData.push(data[i]);
				}
			}
			return treeData;
		}
		return data;
	}
});


$.fn.panel.defaults.onBeforeDestroy = function() {/* tab关闭时回收内存 */
	var frame = $('iframe', this);
	try {
		if (frame.length > 0) {
			frame[0].contentWindow.document.write('');
			frame[0].contentWindow.close();
			frame.remove();
			if ($.browser.msie) {
				CollectGarbage();
			}
		} else {
			$(this).find('.combo-f').each(function() {
				var panel = $(this).data().combo.panel;
				panel.panel('destroy');
			});
		}
	} catch (e) {
	}
};







/*
$.parser.auto = false;
$(function() {
	$.messager.progress({
		text : '页面加载中....',
		interval : 100
	});
	$.parser.parse(window.document);
	window.setTimeout(function() {
		$.messager.progress('close');
		if (self != parent) {
			window.setTimeout(function() {
				parent.$.messager.progress('close');
			}, 500);
		}
	}, 1);
	$.parser.auto = true;
});
*/

$.fn.panel.defaults.loadingMessage = '数据加载中，请稍候....';
$.fn.datagrid.defaults.loadMsg = '数据加载中，请稍候....';

var easyuiErrorFunction = function(XMLHttpRequest) {
	/* $.messager.progress('close'); */
	/* alert(XMLHttpRequest.responseText.split('<script')[0]); */
	//$.messager.alert('错误', XMLHttpRequest.responseText.split('<script')[0]);
};
$.fn.datagrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.treegrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combogrid.defaults.onLoadError = easyuiErrorFunction;
$.fn.combobox.defaults.onLoadError = easyuiErrorFunction;
$.fn.form.defaults.onLoadError = easyuiErrorFunction;

var easyuiPanelOnMove = function(left, top) {/* 防止超出浏览器边界 */
	var l = left;  
    var t = top;  
    if (l < 1) {  
        l = 1;  
    }  
    if (t < 1) {  
        t = 1;  
    }  
    var width = parseInt($(this).parent().css('width')) + 14;  
    var height = parseInt($(this).parent().css('height')) + 14;  
    var right = l + width;  
    var buttom = t + height;  
    var browserWidth = $(window).width();  
    var browserHeight = $(window).height();  
    if (right > browserWidth) {  
        l = browserWidth - width;  
    }  
    if (buttom > browserHeight) {  
        t = browserHeight - height;  
    }  
    $(this).parent().css({/* 修正面板位置 */  
        left : l,  
        top : t  
    });  
};
$.fn.panel.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;

	
$.extend($.fn.tabs.methods, {
	
	    setTabTitle:function(jq,opts){
	
	        return jq.each(function(){
	
	            var tab = opts.tab;
	
	            var options = tab.panel("options");
	
	            var tab = options.tab;
	
	            options.title = opts.title;
	
	            var title = tab.find("span.tabs-title");
	
	            title.html(opts.title);
	
	        });
	
	    }
	
	});

$.extend($.fn.panel.defaults, {
	onBeforeDestroy : function() {
		var frame = $('iframe', this);
		try {
			if (frame.length > 0) {
				for (var i = 0; i < frame.length; i++) {
					frame[i].src = '';
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
				}
				frame.remove();
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			}
		} catch (e) {
		}
	}
});

/**
 * tree控件,中获取tree中所有节点的id数组
 */
$.extend($.fn.tree.methods, {
    getAllNodeIds: function(jq){
    	var $nodeDivs=$(jq).find("div.tree-node");
    	var allNodeIds=[];
    	if($nodeDivs.length>0){
    		for(var i=0; i<$nodeDivs.length; i++){
    			var nodeId=jq.tree("getNode", $nodeDivs[i]).id;
    			allNodeIds.push(nodeId);
    		}
    	}
    	return allNodeIds;
    }
});


/**
 * tree控件,中获取tree中所有节点
 */
$.extend($.fn.tree.methods, {
    getAllNodes: function(jq){
    	var $nodeDivs=$("div.tree-node", $(jq));
    	var allNodes=[];
    	if($nodeDivs.length>0){
    		for(var i=0; i<$nodeDivs.length; i++){
    			allNodes.push(jq.tree("getNode", $nodeDivs[i]));
    		}
    	}
    	return allNodes;
    }
});

/**
 * tree控件中disable复选框
 */
$.extend($.fn.tree.methods, {
    disableCheck: function(jq){
    	$("span.tree-checkbox", $(jq)).each(function(){
    		if($(this).is(".tree-checkbox1")){
    			$(this).addClass('tree-checkbox-disabled1');
    		}else{
    			$(this).addClass('tree-checkbox-disabled0');
    		}
    	});
    }
});


/**
 * 扩展datagrid通过索引获取行
 */
$.extend($.fn.datagrid.methods, {
    getRow: function(jq, rowIndex){
    	var rows=jq.eq(0).datagrid("getRows");
    	return rows[rowIndex];
    },
    findRow: function(jq, id){//datagrid通过idField的值获取某一行
    	var grid=jq.eq(0);
    	var idField=grid.datagrid("options").idField;
    	var rows=grid.datagrid("getRows");
    	for(var i=0; i<rows.length; i++){
    		if(rows[i][idField]==id){
    			return rows[i];
    		}
    	}
    	return null;
	}
});


$.extend($.fn.form.methods, {
	"disable":function(jq) {
		return jq.each(function() {
			var _4c0=this;
			$("input,select,textarea", _4c0).each(
					function() {
						var t = this.type, tag = this.tagName.toLowerCase();
						if (t == "text" || t == "hidden" || t == "password"
								|| tag == "textarea" || t == "file" || t == "checkbox" || t == "radio" || tag == "select") {
							$(this).attr("disabled", "disabled");
						}
			});
			var t = $(_4c0);
			var _4c2 = [ "textbox", "combo", "combobox", "combotree", "combogrid",
					"slider" ];
			for (var i = 0; i < _4c2.length; i++) {
				var _4c3 = _4c2[i];
				var r = t.find("." + _4c3 + "-f");
				if (r.length && r[_4c3]) {
					r[_4c3]("disable");
				}
			}
		});
	},
	"enable":function(jq) {
		return jq.each(function() {
			var _4c0=this;
			$("input,select,textarea", _4c0).each(
					function() {
						var t = this.type, tag = this.tagName.toLowerCase();
						if (t == "text" || t == "hidden" || t == "password"
								|| tag == "textarea" || t == "file" || t == "checkbox" || t == "radio" || tag == "select") {
							$(this).removeAttr("disabled");
						}
			});
			var t = $(_4c0);
			var _4c2 = [ "textbox", "combo", "combobox", "combotree", "combogrid",
					"slider" ];
			for (var i = 0; i < _4c2.length; i++) {
				var _4c3 = _4c2[i];
				var r = t.find("." + _4c3 + "-f");
				if (r.length && r[_4c3]) {
					r[_4c3]("enable");
				}
			}
		});
	},
});

//扩展jQuery easyui form插件的三个方法
//serialize用法: $("form").form("serialize");        返回结果，是一个json对象
//getValue 用法：$("form").form("getValue","email"); 返回单个form元素值
//setValue 用法: $("form").form("setValue",{"name":"vv","email":"vv@163.com"); //可用easyui本身的load方法
$.extend($.fn.form.methods, {
    serialize: function(jq){
        var arrayValue = $(jq[0]).serializeArray();
		var json = {};
		$.each(arrayValue, function() {
			var item = this;
			if (json[item["name"]]) {
				json[item["name"]] = json[item["name"]] + "," + item["value"];
			} else {
				json[item["name"]] = item["value"];
			}
		});
		return json; 
    },
    getValue:function(jq,name){  
        var jsonValue = $(jq[0]).form("serialize");
		return jsonValue[name]; 
    },
    setValue:function(jq,name,value){
		return jq.each(function () {
				_b(this, _29);
				var data = {};
				data[name] = value;
				$(this).form("load",data);
		});
	}
});

$.self={
	inputDefaults:{
		panelHeight : 'auto',//下拉框高度自动适应
		panelMaxHeight : '250'//下拉框的最大高度250
	}
};
$.extend($.fn.textbox.defaults,$.self.inputDefaults);
$.extend($.fn.combobox.defaults,$.self.inputDefaults);
$.extend($.fn.combotree.defaults,$.self.inputDefaults,{
	animate:true,editable:false,
	qsearch:false,//是否能进行模糊搜索
	textbox:"",//另外显示选中项的文本框
	keyHandler: {
		query: function(q,e){
			var tree = $(this).combotree('tree');
	    	var $nodeDivs=tree.find("div.tree-node");
	    	for(var i=0; i<$nodeDivs.length; i++){
	    		var node=tree.tree("getNode",$nodeDivs[i]);
	    		if(q!=null && q.length>0){
    				if(node.text.indexOf(q)>-1){
    					$(node.target).show();
	    			}else{
	    				$(node.target).hide();
	    			}
	    		}else{
	    			$(node.target).show();
	    		}
			}
    	}
	}
});
$.extend($.fn.numberbox.defaults,$.self.inputDefaults);
$.extend($.fn.numberspinner.defaults,$.self.inputDefaults);
$.extend($.fn.datebox.defaults,$.self.inputDefaults,{editable:false});
$.extend($.fn.datetimebox.defaults,$.self.inputDefaults,{editable:false});
$.extend($.fn.filebox.defaults,$.self.inputDefaults,{buttonText: '选择文件'});
$.extend($.fn.dialog.defaults,{
	closed: true, cache: false, modal: true,
	onLoad:function(){
//		$(this).dialog("resize");
//		$(this).dialog("center");
	}
});

