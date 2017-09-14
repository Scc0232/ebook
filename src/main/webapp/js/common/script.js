/**
 * 公共JS工具
 * 
 * @author zhanghua on 2015-07-30 14:08:16
 */
var mLayer = {};
mLayer.load = function() {
    layer.load();
};
mLayer.closeLoad = function() {
    layer.closeAll("loading");
}
/**
 * 关闭当前弹出窗口，并显示消息。
 */
mLayer.closeWindow = function(msg) {
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.msg(msg);
    parent.layer.close(index);
};
mLayer.msg = function(msg) {
    layer.msg(msg);
};

var mForm = {};
/**
 * 清空表单中的数据。支持标签input, easyui-textbox, easyui-combo
 */
mForm.clear = function(form) {
    var inputs = $(form).find('input');
    for (var i = 0; i < inputs.length; i++) {
        if ($(inputs[i]).hasClass('textbox-value')) {
            continue;
        }
        if ($(inputs[i]).hasClass('textbox-text')) {
            continue;
        }
        if ($(inputs[i]).hasClass('textbox-f')) {
            $(inputs[i]).textbox('clear');
        }
    }

    var selects = $(form).find('select');
    for (var i = 0; i < selects.length; i++) {
        if ($(selects[i]).hasClass('combo-f')) {
            $(selects[i]).combo('clear');
        }
    }
};

/**
 * 读取表单中的数据,返回Object。标签的name属性的Object的属性名。 支持标签input, easyui-textbox,
 * easyui-combo, textarea
 */
mForm.toObject = function(form) {
    if (!form) {
        return;
    }
    var obj = $(form).data('Object');
    if (!obj) {
        obj = {};
    }
    var inputs = $(form).find('input');
    for (var i = 0, input; input = inputs[i]; i++) {
        if ($(input).prop('type') == 'checkbox') {
            var name = $(input).attr('name');
            if (name) {
                obj[name] = $(input).is(':checked');
            }
            continue;
        }
        if ($(input).hasClass('textbox-value')) {
            continue;
        }
        if ($(input).hasClass('textbox-text')) {
            continue;
        }
        if ($(input).hasClass('textbox-f')) {
            var name = $(input).attr('textboxname');
            if (name) {
                if ($(input).textbox){
                    obj[name] = $(input).textbox('getValue');
                }else{
                    obj[name] = $(input).val();
                }
            }
        } else {
            var name = $(input).attr('name');
            if (name) {
                obj[name] = $(input).val();
            }
        }
    }
    var selects = $(form).find('select');
    for (var i = 0, select; select = selects[i]; i++) {
        if ($(select).hasClass('combo-f')) {
            var name = $(select).attr('comboname');
            if (name) {
                var usetext = $(select).attr('usetext');
                if (usetext) {
                    obj[name] = $(select).combo('getText');
                } else {
                    obj[name] = $(select).combo('getValue');
                }
            }
        } else {
            var name = $(select).attr('name');
            if (name) {
                obj[name] = $(select).val();
            }
        }
    }
    var textareas = $(form).find('textarea');
    for (var i = 0, textarea; textarea = textareas[i]; i++) {
        var name = $(textarea).attr('name');
        if (name) {
            obj[name] = $(textarea).val();
        }
    }
    return obj;
};

var Formatter = {};

/**
 * 扩展Date，增加格式化日期方法
 * 
 * @param format
 * @returns
 */
Date.prototype.formatDate = function(format) {
    var o = { "M+" : this.getMonth() + 1, // month
    "d+" : this.getDate(), // day
    "h+" : this.getHours(), // hour
    "m+" : this.getMinutes(), // minute
    "s+" : this.getSeconds(), // second
    "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
    "S" : this.getMilliseconds()
    // millisecond
    };
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};
/**
 * 格式化日期， 默认为yyyy/MM/dd hh:mm:ss
 * 
 * @param date
 *            待格式化的日期
 * @param format
 *            日期格式
 * @return 格式化不成功返回undefined
 */
Formatter.formatDate = function(date, format) {
    if (typeof date == "string" && date.trim() == "") {
        return undefined;
    }
    var d = new Date(date);
    // console.log(d);
    if (format) {
        return d.formatDate(format);
    } else {
        return d.formatDate("yyyy/MM/dd hh:mm:ss");
    }
};