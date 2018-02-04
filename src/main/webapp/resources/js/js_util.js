
/**
 * 包含easyui的扩展和常用的方法
 */

var sy = $.extend({}, sy);/* 全局对象 */
/**
 * 获得项目根路径
 * 
 * 使用方法：sy.bp();
 */
sy.bp = function() {
	var curWwwPath = window.document.location.href;
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	var localhostPaht = curWwwPath.substring(0, pos);
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPaht + projectName);
};

/**
 * 增加formatString功能
 * 
 * 使用方法：sy.fs('字符串{0}字符串{1}字符串','第一个变量','第二个变量');
 */
sy.fs = function(str) {
	for ( var i = 0; i < arguments.length - 1; i++) {
		str = str.replace("{" + i + "}", arguments[i + 1]);
	}
	return str;
};

/**
 * 增加命名空间功能
 * 
 * 使用方法：sy.ns('jQuery.bbb.ccc','jQuery.eee.fff');
 */
sy.ns = function() {
	var o = {}, d;
	for ( var i = 0; i < arguments.length; i++) {
		d = arguments[i].split(".");
		o = window[d[0]] = window[d[0]] || {};
		for ( var k = 0; k < d.slice(1).length; k++) {
			o = o[d[k + 1]] = o[d[k + 1]] || {};
		}
	}
	return o;
};

/**
 * 生成UUID
 */
sy.random4 = function() {
	return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
};
sy.UUID = function() {
	return (sy.random4() + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + "-" + sy.random4() + sy.random4() + sy.random4());
};

/**
 * 获得URL参数
 */
sy.getUrlParam = function(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
};

sy.getList = function(value) {
	if (value) {
		var values = [];
		var t = value.split(',');
		for ( var i = 0; i < t.length; i++) {
			values.push('' + t[i]);/* 避免他将ID当成数字 */
		}
		return values;
	} else {
		return [];
	}
};

sy.listToString = function(value) {
	var str='';
	if(value.length>0){
		for(var i=0; i<value.length; i++){
			str+=value[i]+",";
		}
		str=str.substring(0, str.lastIndexOf(","));
	}
	return str;
};

sy.png = function() {
	var imgArr = document.getElementsByTagName("IMG");
	for ( var i = 0; i < imgArr.length; i++) {
		if (imgArr[i].src.toLowerCase().lastIndexOf(".png") != -1) {
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + imgArr[i].src + "', sizingMethod='auto')";
			imgArr[i].src = "images/blank.gif";
		}
		if (imgArr[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = imgArr[i].currentStyle.backgroundImage.substring(5, imgArr[i].currentStyle.backgroundImage.length - 2);
			imgArr[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			imgArr[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};
sy.bgPng = function(bgElements) {
	for ( var i = 0; i < bgElements.length; i++) {
		if (bgElements[i].currentStyle.backgroundImage.lastIndexOf(".png") != -1) {
			var img = bgElements[i].currentStyle.backgroundImage.substring(5, bgElements[i].currentStyle.backgroundImage.length - 2);
			bgElements[i].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + img + "', sizingMethod='crop')";
			bgElements[i].style.backgroundImage = "url('images/blank.gif')";
		}
	}
};

sy.isLessThanIe8 = function() {/* 判断浏览器是否是IE并且版本小于8 */
	return ($.browser.msie && $.browser.version < 8);
};




var _change = {
    ary0:["零", "一", "二", "三", "四", "五", "六", "七", "八", "九"],
    ary1:["", "十", "百", "千"],
    ary2:["", "万", "亿", "兆"],
    init:function (name) {
        this.name = name;
    },
    strrev:function () {
        var ary = []
        for (var i = this.name.length; i >= 0; i--) {
            ary.push(this.name[i])
        }
        return ary.join("");
    }, //倒转字符串。
    pri_ary:function () {
        var $this = this
        var ary = this.strrev();
        var zero = ""
        var newary = ""
        var i4 = -1
        for (var i = 0; i < ary.length; i++) {
            if (i % 4 == 0) { //首先判断万级单位，每隔四个字符就让万级单位数组索引号递增
                i4++;
                newary = this.ary2[i4] + newary; //将万级单位存入该字符的读法中去，它肯定是放在当前字符读法的末尾，所以首先将它叠加入$r中，
                zero = ""; //在万级单位位置的“0”肯定是不用的读的，所以设置零的读法为空

            }
            //关于0的处理与判断。
            if (ary[i] == '0') { //如果读出的字符是“0”，执行如下判断这个“0”是否读作“零”
                switch (i % 4) {
                    case 0:
                        break;
                    //如果位置索引能被4整除，表示它所处位置是万级单位位置，这个位置的0的读法在前面就已经设置好了，所以这里直接跳过
                    case 1:
                    case 2:
                    case 3:
                        if (ary[i - 1] != '0') {
                            zero = "零"
                        }
                        ; //如果不被4整除，那么都执行这段判断代码：如果它的下一位数字（针对当前字符串来说是上一个字符，因为之前执行了反转）也是0，那么跳过，否则读作“零”
                        break;

                }

                newary = zero + newary;
                zero = '';
            }
            else { //如果不是“0”
                newary = this.ary0[parseInt(ary[i])] + this.ary1[i % 4] + newary; //就将该当字符转换成数值型,并作为数组ary0的索引号,以得到与之对应的中文读法，其后再跟上它的的一级单位（空、十、百还是千）最后再加上前面已存入的读法内容。
            }

        }
        if (newary.indexOf("零") == 0) {
            newary = newary.substr(1)
        }//处理前面的0
        return newary;
    }
}
//创建class类
function change() {
    this.init.apply(this, arguments);
}
change.prototype = _change



//格式化日期
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"h+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
	// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
					: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

//字符串转成日期对象,固定格式为yyyy-MM-dd
function parseToDate(DateStr)  
{   
	var converted = Date.parse(DateStr);  
  var myDate = new Date(converted);  
  if (isNaN(myDate))  
  {   
      //var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';  
      var arys= DateStr.split('-');  
      myDate = new Date(arys[0],--arys[1],arys[2]);  
  }  
  return myDate;  
}  

//日期比较,不包含时间
Date.prototype.compare = function(date) {
	var result=0;
	if(this.getTime()>date.getTime()){
		result=1;
	}else if(this.getTime()==date.getTime()){
		result=0;
	}else{
		result=-1;
	}
	if(this.format("yyyy-MM-dd")==date.format("yyyy-MM-dd")){
		result=0;
	}
	return result;
}

//获取数组对象索引，没有返回-1
Array.prototype.indexOf = function(e){
	for(var i=0; i<this.length;i++){
		if(this[i]==e){
			return i;
		}
	}
	return -1;
}

//两个数组的元素是否相同
Array.prototype.equals = function(values){
	return this.sort().toString()== values.sort().toString()
}


