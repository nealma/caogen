/**
 * Created by neal on 9/1/16.
 */
/**
 * 获取host:port,eg:domain:port<br>
 * @returns {string}
 */
function getHost(){
    return window.location.host;
}
/**
 * 公用函数类<br>
 * 新版bindForm、formToObjParms、formToJsonParms、formToUrlParams等支持radio、select、checkbox等全部内容<br>
 * text\hidden\password\textarea\radio\checkbox\select\file,file不涉及上传功能实现，只是其值（路径）获取和回填。
 * 主要函数：
 * bindForm			将JSON数据填充到FORM中
 * formToObjParms	form元素转为对象数组
 * formToJsonParms	form元素转为JSON对象
 * formToUrlParams	form元素转为URL参数
 * formClean		重置表单
 */

/**
 * 绑定Form,将JSON数据填充到FORM中
 * @param formId: Form的Id
 * @param data: Json数据
 * 		data数据格式示例如下：
 * 		{	xx_id : value,
 *  		radio_name : val,
 * 			checkbox_name : "val1,val2,val3" or [val1,val2,val3](字符串或数字)
 * 		}
 * 		说明：radio、checkbox 以name绑定；其它数据，以id绑定。
 * @param suffix:属性的后缀名 如domain对象为user.name form表单中为nameUpdate,进行绑定时数据需要增加后缀字符串
 * @example bindForm("orgForm", jsonObject,"suffix")
 */
function bindForm(formId, data,suffix) {
    if (!suffix) suffix="";
    var $form = $("#"+formId);
    var $txt = $form.find("input:text,input[type='hidden'],input:password,textarea,input:file");
    var $radio = $form.find("input:radio");
    var $checkbox = $form.find("input:checkbox");
    var $select = $form.find("select");
    /*普通键值对处理*/
    $.each($txt,function(i,n){
        var elementId=$(this).attr("id");
        if(elementId && elementId.length>0){
            elementId=elementId.substring(0,elementId.length-suffix.length);
            if (data[elementId]){
                $(this).val(data[elementId]);
            };
        }
    });
    /*$radio处理*/
    var radioArray = new Array();
    $.each($radio,function(){
        var radioName = $(this).attr("name");
        if($.inArray(radioName, radioArray)<0){
            radioArray.push(radioName);
        }
    });
    $.each(radioArray,function(ri,rn){
        var $radioName = rn;
        var $cradio = $form.find("input:radio[name='"+$radioName+"']");
        var elementName=$radioName.substring(0,$radioName.length-suffix.length);
        $.each($cradio, function () {
            if($(this).val()== data[elementName]){
                $(this).attr("checked", true);
            } else {
                $(this).attr("checked", false);
            }
        });
        setRadioValue($radioName)
    });
    radioArray = null;
    /*$checkbox处理*/
    var checkboxArray = new Array();
    $.each($checkbox,function(){
        var checkboxName = $(this).attr("name");
        if($.inArray(checkboxName, checkboxArray)<0){
            checkboxArray.push(checkboxName);
        }
    });
    $.each(checkboxArray,function(ci,cn){
        var $checkboxName = cn;
        var elementName=$checkboxName.substring(0,$checkboxName.length-suffix.length);
        var ckdata = data[elementName];
        if(ckdata){
            /*不为数组时，将其以","分割为数组*/
            if (!(ckdata instanceof Array)) {
                ckdata = ckdata.split(",");
            }
            var $ccheckbox = $form.find("input:checkbox[name='"+$checkboxName+"']");
            $.each($ccheckbox, function () {
                if($.inArray($(this).val(), ckdata)>=0){
                    $(this).attr("checked", true);
                }else{
                    $(this).attr("checked", false);
                }
            });
            /*补充回填功能*/
            setCheckBoxValue($checkboxName)
        }
    });
    checkboxArray = null;
    /*$select处理*/
    $.each($select,function(){
        var $selectid = $(this).attr("id")
        var elementId=$selectid.substring(0,$selectid.length-suffix.length);
        if (data[elementId]){
            $.each($(this).find("option"),function(){
                if($(this).val()==data[elementId]){
                    $(this).attr("selected", true);
                } else {
                    $(this).attr("selected", false);
                }
            });
        };
        /*补充回填功能*/
        setSelectValue($selectid);
    });
}

/**
 * 通过Form绑定Json数据(JSON名与表单中的字段名请保持一致)
 * @param formId: Form的Id
 * @param data: Json数据
 * 		data数据格式示例如下：
 * 		{	xx_id : value,
 *  		radio_name : val,
 * 			checkbox_name : [val1,val2,val3]
 * 		}
 * 		说明：radio、checkbox 以name绑定；其它数据，以id绑定。
 * @example bindJson("orgForm", jsonObject)
 */
function bindJson(formId, data) {
    bindForm(formId, data,"");
}

function formToUrl(formId,urlParams,suffix) {
    return formToUrlParams(formId,urlParams,suffix);
}
/**
 * 把Form的所有项生成name1=value1&name2=value2&...的URL参数字符串
 * 说明：radio、checkbox 以name绑定；其它数据，以id绑定。
 *
 * @param formId: Form的Id
 * @param urlParams: 需要添加至的数组对象，可为空。
 * @param suffix: form元素后缀名(对象.name = 表单名-后缀名)
 *
 * @return objParms:返回URL参数字符串
 * 	EXP:
 * 	name1=value1&name2=value2&...
 */
function formToUrlParams(formId,urlParams,suffix) {
    if (!suffix) suffix="";
    var $form = $("#"+formId);
    var $txt = $form.find("input:text,input[type='hidden'],input:password,textarea,input:file");
    var $radio = $form.find("input:radio:checked");
    var $checkbox = $form.find("input:checkbox");
    var $select = $form.find("select");
    /*普通键值对处理*/
    $.each($txt,function(i,n){
        var elementId=$(this).attr("id");
        if(elementId && elementId.length>0){
            var elementVal=$(this).val();
            urlParams = pushObjForFormToUrlParams(elementId,elementVal,urlParams,suffix);
        }
    });
    /*$radio处理*/
    $.each($radio,function(){
        var elementName = $(this).attr("name");
        if(elementName && elementName.length>0){
            var elementVal = $(this).val();
            urlParams = pushObjForFormToUrlParams(elementName,elementVal,urlParams,suffix);
        }
    });
    /*$checkbox处理*/
    var checkboxArray = new Array();
    $.each($checkbox,function(){
        var checkboxName = $(this).attr("name");
        if($.inArray(checkboxName, checkboxArray)<0){
            checkboxArray.push(checkboxName);
        }
    });
    $.each(checkboxArray,function(ci,cn){
        var $checkboxName = cn;
        var $ccheckbox = $form.find("input:checkbox[name='"+$checkboxName+"']:checked");
        var rval = "";
        $.each($ccheckbox,function(){
            rval += $(this).val() + ",";
        });
        if(rval && rval.length > 0){
            rval = rval.substring(0,rval.length-1);
        }
        urlParams = pushObjForFormToUrlParams($checkboxName,rval,urlParams,suffix);
    });
    checkboxArray = null;
    /*$select处理*/
    $.each($select,function(){
        var elementId = $(this).attr("id");
        if(elementId && elementId.length>0){
            var elementVal = $(this).find("option:selected").val();
            urlParams = pushObjForFormToUrlParams(elementId,elementVal,urlParams,suffix);
        }
    });
    return urlParams;
}

function pushObjForFormToUrlParams(name,value,urlParams,suffix){
    if(name && name.length >0){
        name=name.substring(0,name.length-suffix.length);
        if(urlParams && urlParams.length>0){
            urlParams += "&";
        }else{
            urlParams = "";
        }
        urlParams += name + "=" + encodeURI(value);
    }
    return urlParams;
}

/**
 * 把Form的所有项生成 {name1:value1,name2:value2,...}的JSON对象
 * 说明：radio、checkbox 以name绑定；其它数据，以id绑定。
 *
 * @param formId: Form的Id
 * @param jsonParms: 需要添加至的数组对象，可为空。
 * @param suffix: form元素后缀名(对象.name = 表单名-后缀名)
 *
 * @return jsonParms:返回JSON对象
 * 	EXP: {
 * 		user_name:"Jone",
 * 		user_pwd:123456,
 * 		checkbox:"val1,val2,val3...",
 * 		......
 * 	}
 */
function formToJsonParms(formId,jsonParms,suffix) {
    if (!suffix) suffix="";
    var $form = $("#"+formId);
    var $txt = $form.find("input:text,input[type='hidden'],input:password,textarea,input:file");
    var $radio = $form.find("input:radio:checked");
    var $checkbox = $form.find("input:checkbox");
    var $select = $form.find("select");
    if(!jsonParms){
        jsonParms = {};
    }
    /*普通键值对处理*/
    $.each($txt,function(i,n){
        var elementId=$(this).attr("id");
        if(elementId && elementId.length>0){
            var elementVal=$(this).val();
            jsonParms = pushObjForFormToJsonParms(elementId,elementVal,jsonParms,suffix);
        }
    });
    /*$radio处理*/
    $.each($radio,function(){
        var elementName = $(this).attr("name");
        if(elementName && elementName.length>0){
            var elementVal = $(this).val();
            jsonParms = pushObjForFormToJsonParms(elementName,elementVal,jsonParms,suffix);
        }
    });
    /*$checkbox处理*/
    var checkboxArray = new Array();
    $.each($checkbox,function(){
        var checkboxName = $(this).attr("name");
        if($.inArray(checkboxName, checkboxArray)<0){
            checkboxArray.push(checkboxName);
        }
    });
    $.each(checkboxArray,function(ci,cn){
        var $checkboxName = cn;
        var $ccheckbox = $form.find("input:checkbox[name='"+$checkboxName+"']:checked");
        var rval = "";
        $.each($ccheckbox,function(){
            rval += $(this).val() + ",";
        });
        if(rval && rval.length > 0){
            rval = rval.substring(0,rval.length-1);
        }
        jsonParms = pushObjForFormToJsonParms($checkboxName,rval,jsonParms,suffix);
    });
    checkboxArray = null;
    /*$select处理*/
    $.each($select,function(){
        var elementId = $(this).attr("id");
        if(elementId && elementId.length>0){
            var elementVal = $(this).find("option:selected").val();
            jsonParms = pushObjForFormToJsonParms(elementId,elementVal,jsonParms,suffix);
        }
    });
    return jsonParms;
}

function pushObjForFormToJsonParms(name,value,jsonParms,suffix){
    if(name && name.length >0){
        name=name.substring(0,name.length-suffix.length);
        jsonParms[name] = value;
    }
    return jsonParms;
}
/**
 * 把Form的所有项生成 {对象.name1=value1,对象.name2=value2,...}的对象数组
 * 说明：radio、checkbox 以name绑定；其它数据，以id绑定。
 *
 * @param formId: Form的Id
 * @param objParms: 需要添加至的数组对象，可为空。
 * @param suffix: form元素后缀名(对象.name = 表单名-后缀名)
 *
 * @return objParms:返回对象数组
 * 	EXP:
 * 	[{"name":"user_name","value":"Jone"},{"name":"user_pwd","value":"123456"},...]
 *  	checkbox的"value":"val1,val2,val3..."
 */
function formToObjParms(formId,objParms,suffix) {
    if (!suffix) suffix="";
    var $form = $("#"+formId);
    var $txt = $form.find("input:text,input[type='hidden'],input:password,textarea,input:file");
    var $radio = $form.find("input:radio:checked");
    var $checkbox = $form.find("input:checkbox");
    var $select = $form.find("select");
    if(!objParms || !(objParms instanceof Array)){
        objParms = new Array();
    }
    /*普通键值对处理*/
    $.each($txt,function(i,n){
        var elementId=$(this).attr("id");
        if(elementId && elementId.length>0){
            var elementVal=$(this).val();
            objParms = pushObjForFormToObjParms(elementId,elementVal,objParms,suffix);
        }
    });
    /*$radio处理*/
    $.each($radio,function(){
        var elementName = $(this).attr("name");
        if(elementName && elementName.length>0){
            var elementVal = $(this).val();
            objParms = pushObjForFormToObjParms(elementName,elementVal,objParms,suffix);
        }
    });
    /*$checkbox处理*/
    var checkboxArray = new Array();
    $.each($checkbox,function(){
        var checkboxName = $(this).attr("name");
        if($.inArray(checkboxName, checkboxArray)<0){
            checkboxArray.push(checkboxName);
        }
    });
    $.each(checkboxArray,function(ci,cn){
        var $checkboxName = cn;
        var $ccheckbox = $form.find("input:checkbox[name='"+$checkboxName+"']:checked");
        var rval = "";
        $.each($ccheckbox,function(){
            rval += $(this).val() + ",";
        });
        if(rval && rval.length > 0){
            rval = rval.substring(0,rval.length-1);
        }
        objParms = pushObjForFormToObjParms($checkboxName,rval,objParms,suffix);
    });
    checkboxArray = null;
    /*$select处理*/
    $.each($select,function(){
        var elementId = $(this).attr("id");
        if(elementId && elementId.length>0){
            var elementVal = $(this).find("option:selected").val();
            objParms = pushObjForFormToObjParms(elementId,elementVal,objParms,suffix);
        }
    });
    return objParms;
}

function pushObjForFormToObjParms(name,value,objParms,suffix){
    if(name && name.length >0){
        name=name.substring(0,name.length-suffix.length);
        objParms.push({"name":name,"value":value});
    }
    return objParms;
}


/**
 * 把数组的所有项生成name1=value1&name2=value2&...的URL参数字符串
 * @private
 * @param urlParams: 传入字符串
 * @param params: 函数所有参数的数组
 * @param start: 从第几个参数开始截取
 */
function arrayToUrl(urlParams, params, start) {
    for (var i = start; i < params.length - 1; i = i + 2) {
        if (urlParams != '')
            urlParams += "&";
        urlParams += params[i] + "=" + params[i+1];
    }
    return urlParams;
}

/**
 * 清空Form所有项
 * 说明：支持现有的radio\select\checkbox
 *
 */
function formClean(formId){
    var $form = $("#"+formId);
    try{
        $form[0].reset();
    }catch(e){}
    var $txt = $form.find("input:text,input[type='hidden'],input:password,textarea,input:file");
    var $radio = $form.find("input:radio");
    var $checkbox = $form.find("input:checkbox");
    var $select = $form.find("select");
    // update 2016-7-6 23:26:16  $form[0].reset();不起作用，需要单独处理
    /*普通键值对处理*/
    $.each($txt,function(i,n){
        var elementId=$(this).attr("id");
        if(elementId && elementId.length>0){
            $(this).val("");
        }
    });
    /*$radio处理*/
    var radioArray = new Array();
    $.each($radio,function(){
        var radioName = $(this).attr("name");
        if($.inArray(radioName, radioArray)<0){
            radioArray.push(radioName);
        }
    });
    $.each(radioArray,function(ri,rn){
        setRadioValue(rn)
    });
    radioArray = null;
    /*$checkbox处理*/
    var checkboxArray = new Array();
    $.each($checkbox,function(){
        var checkboxName = $(this).attr("name");
        if($.inArray(checkboxName, checkboxArray)<0){
            checkboxArray.push(checkboxName);
        }
    });
    $.each(checkboxArray,function(ci,cn){
        setCheckBoxValue(cn)
    });
    checkboxArray = null;
    /*$select处理*/
    $.each($select,function(){
        setSelectValue($(this).attr("id"));
    });
}


/**
 * 判断项是否为空，如果为空则提示错误
 * @param itemId: 项的ID
 * @param itemPrompt: 项的中文说明
 */
function checkItemNull(itemId, itemPrompt) {
    var item = document.getElementById(itemId);
    if (item == undefined)
        return;
    if (item.value == "") {
        //alert(itemPrompt + "不能为空!");
        item.focus();
        throw "NULL";
    }
}

/**
 * 判断是否符合条件，如果符合则提示错误
 * @param condition: 条件
 * @param message: 错误信息
 */
function checkCondition(condition, message) {
    checkCondition(condition, message, null);
}

function checkCondition(condition, message, itemFocusing) {
    if (condition) {
        //alert(message);
        if (itemFocusing != null) {
            var item = document.getElementById(itemFocusing);
            if (item != undefined)
                item.focus();
        }
        throw "CONDITION";
    }
}

//格式化日期为yyyy.mm
function todate(s) {
    var str = "";
    if (s != "" && s.length >= 7) {
        str = s.substr(0, 4) + "." + s.substr(5, 2);
    }
    return str;
}
// 格式化日期为yyyy.mm.dd
function toYMDdate(s) {
    var str = "";
    if (s) {
        if (s != "" && s.length >= 10) {
            str = s.substr(0, 4) + "." + s.substr(5, 2) + "." + s.substr(8, 10);
        } else if (s != "" && s.length >= 5) {
            str = s + '.01';
        }
    }
    return str;
}
function WinOpen(url, height, width) {
    window.open(url, "", "height=" + height + ",width=" + width + ",top=" + getTop(heigth) + ",left=" + getLeft(width)
        + ",toolbar=no,menubar=no,scrollbars=no,resizable=yes,location=no,status=no");
}



function commonSeccessPrompt(json){
    if (json.type == 'SUCCESS') {
        $.messager.alert(json.title, json.text,'info');
    }else{
        $.messager.alert(json.title, json.text,'error');
    }
}
function errorTimeoutPrompt(){
    $.messager.alert('错误', '操作请求超时!','error');
}
