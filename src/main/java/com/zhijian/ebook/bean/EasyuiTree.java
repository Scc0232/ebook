package com.zhijian.ebook.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

//import com.gener.common.util.ObjectUtil;

/**
 * easyui-tree bean
 * 
 * @author zhanghua on 15/11/05
 *
 */
public class EasyuiTree {

    public EasyuiTree() {
        super();
        this.children = new ArrayList<>();
    }

    /**
     * @param id 菜单ID
     * @param text
     *            文本
     * @param parentId
     *            父ID
     * @param iconCls
     *            图标
     * @param attribute
     *            属性
     * @param checked
     *            是否已选中
     * 
     */
    public EasyuiTree(String id, String text, String parentId, String iconCls, Object attribute, boolean checked) {
        super();
        this.id = id;
        this.text = text;
        this.parentId = parentId;
        this.iconCls = iconCls;
        this.attribute = attribute;
        this.checked = checked;
        this.children = new ArrayList<>();
    }

    /**
     * ID
     */
    private String id;

    /**
     * 文本
     */
    private String text;

    /**
     * 父ID
     */
    private String parentId;

    /**
     * 图标
     */
    private String iconCls;

    /**
     * 属性
     */
    private Object attribute;

    /**
     * 是否选中
     */
    private boolean checked;

    private List<EasyuiTree> children;

    /**
     * 排列，按照父子关系排列(目前只支持到二级树2015-11-05 18:50:39)
     * 
     * @param list 树列表
     */
    public static void arrange(List<EasyuiTree> list) {
        // 根节点
        EasyuiTree root = new EasyuiTree();

        // 先将所有的父节点增加到根节点
        for (EasyuiTree e : list) {
            String parentId = e.getParentId();

            if (StringUtils.isEmpty(parentId)) {
                root.addChildren(e);
            }
        }

        // 将所有的子节点增加到对应父节点下
        for (EasyuiTree e : list) {
            String parentId = e.getParentId();
            if (!StringUtils.isEmpty(parentId)) {
                EasyuiTree children = root.getChildren(parentId);

                if (children != null) {
                    children.addChildren(e);
                }
            }
        }
        
        list.clear();
        list.addAll(root.children);
    }

    private void addChildren(EasyuiTree e) {
        this.children.add(e);
    }

    private EasyuiTree getChildren(String id) {
        for (EasyuiTree e : children) {
            if (id.equals(e.getId())) {
                return e;
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Object getAttribute() {
        return attribute;
    }

    public void setAttribute(Object attribute) {
        this.attribute = attribute;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<EasyuiTree> getChildren() {
        return children;
    }

    public void setChildren(List<EasyuiTree> children) {
        this.children = children;
    }

}
