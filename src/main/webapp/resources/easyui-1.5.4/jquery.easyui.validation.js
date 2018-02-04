/**
 * easy ui validate extend
 * @param {Object} jq
 * @param {Object} region
 */

$.extend($.fn.validatebox.defaults.rules, {
    number: {
        validator: function(value){
            return /^\d+$/.test(value);
        },
        message: '只能输入正整数!'
    },
	imagePath: {
	    validator: function(value){
	    	if(value!=null && value.length>0){
	    		var point = value.lastIndexOf(".");
	    		var type = value.substr(point);
	    		type = type.toLowerCase();
	    		if(type==".jpg"||type==".gif"||type==".png"||type==".bmp"||type==".jpeg"){
	    			return true;
	    		}
	    		return false;
	    	}else{
	    		return true;
	    	}
	    },
	    message: '请上传jpg、gif、png、bmp、jpeg格式的图片!'
	},
	excelFile: {
	    validator: function(value){
	    	if(value!=null && value.length>0){
	    		var point = value.lastIndexOf(".");
	    		var type = value.substr(point);
	    		type = type.toLowerCase();
	    		if(type==".xls" ||type==".xlsx" ||type==".xlsm"){
	    			return true;
	    		}
	    		return false;
	    	}else{
	    		return true;
	    	}
	    },
	    message: '上传文件格式不正确!'
	},
	equals: {
        validator: function(value,param){
            return value == $(param[0]).val();
        },
        message: '两次输入的值不一致!.'
    },
    CHS: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: '请输入汉字!'
    },

    ENS: {
        validator: function (value) {
            return /^[a-zA-Z1-9]+$/.test(value);
        },
        message: '请输入英文和数字!'
    },
    mobile: {//value值为文本框中的值
        validator: function (value) {
            var reg = /^1[3|4|5|8|9]\d{9}$/;
            return reg.test(value);
        },
        message: '输入手机号码格式不准确!'
    },
    eqPassword : {/* 扩展验证两次密码 */
		validator : function(value, param) {
			return value == $(param[0]).val();
		},
		message : '密码不一致！'
	}
});	