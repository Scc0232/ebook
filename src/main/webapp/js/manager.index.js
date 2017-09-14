/**
 * 后台管理-首页 js
 * 
 * @author zhanghua on 15/09/29
 */
$(document).ready(function() {
    $('#menu').tree({ url : 'manager/menu/getMenu.do', onLoadSuccess : function(node, data) {
        // console.log(data);
    }, onClick : onClickMenu, loadFilter : function(data, parent) {
        // 过滤器， 过滤空的父元素
        for(i in data) {
            var item = data[i];
            if (0 === item.children.length && item.attribute == null){
                data.splice(i, 1);
            }
        }
        return data;
    } });
    $('#workspace').tabs({ onContextMenu : function(e, title, index) {
        e.preventDefault();
        $('#mm').menu('show', { left : e.pageX, top : e.pageY }).data("tabTitle", title);
    } });
    $('#mm').menu({ onClick : function(item) {
        closeTab(item.name);
    } });
    function closeTab(type) {
        $('#workspace').tabs(type);
    }
});
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

function onClickMenu(node) {
    if (node.attribute == null) {
        console.log(node.state);
        if ('open' === node.state) {
            $('#menu').tree('collapse', node.target);
        } else {
            $('#menu').tree('expand', node.target);
        }
        return;
    }
    addEasyuiTab({ title : node.text, href : node.attribute, closable : true, iconCls : node.iconCls });
}

/**
 * 格式化日期， 默认为yyyy/MM/dd hh:mm:ss
 * 
 * @param date
 *            待格式化的日期
 * @param format
 *            日期格式
 * @return 格式化不成功返回undefined
 */
function formatDate(date, format) {
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

// 增加Easyui标签页
function addEasyuiTab(opts, refreshIfExists) {
    var workspace = $('#workspace');
    if ($(workspace).tabs('exists', opts.title)) {
        if (refreshIfExists) {
            if (opts.href && opts.href.indexOf('/') == 0) {
                opts.href = opts.href.substr(1, opts.href.length);
            }
            var tab = $(workspace).tabs('getTab', opts.title);
            $(workspace).tabs('update', { tab : tab, options : { href : opts.href } });
        }
        $(workspace).tabs('select', opts.title);
    } else {
        if (opts.href && opts.href.indexOf('/') == 0) {
            opts.href = opts.href.substr(1, opts.href.length);
        }
        $(workspace).tabs('add', opts);
    }
}

/**
 * 关闭Easyui标签页，如果title参数为undefined，关闭当前页
 * 
 * @param title
 *            要关闭标签的标题
 */
function closeEasyuiTab(title) {
    var workspace = $('#workspace');
    if (title) {
        $(workspace).tabs('close', title);
    } else {
        var tab = $(workspace).tabs('getSelected');
        var index = $(workspace).tabs('getTabIndex', tab);
        $(workspace).tabs('close', index);
    }
}

/**
 * 将表单中的数据转换成Object
 * 
 * @param form
 *            jquery对象
 * @returns {___anonymous1247_1248}
 */
function toObject(form) {
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
        
        if ($(input).prop('type') == 'radio') {
            var name = $(input).attr('name');
            if (name) {
                obj[name] = $("input[name='" + name +"']:checked").val();
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
                if ($(input).textbox) {
                    obj[name] = $(input).textbox('getValue');
                } else {
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
}

/**
 * 加载中，在耗时任务时务必调用这个方法
 */
function loading() {
    layer.load(2);
}

/**
 * 关闭加载中
 */
function closeLoading() {
    layer.closeAll('loading');
}

/**
 * 系统异常消息
 * 
 * @param msg
 *            如果为undefined，显示"系统异常，请稍后重试！"，否则显示msg
 */
function systemErrorMsg(msg) {
    if (msg) {
        layer.msg(msg);
    } else {
        layer.msg('系统异常，请稍后重试！');
    }
}

/**
 * 请求成功消息
 * 
 * @param isCloseCurrTab
 *            是否关闭当前页
 */
function okMsg(isCloseCurrTab) {
    layer.msg('完成！');
    if (isCloseCurrTab) {
        closeEasyuiTab();
    }
}