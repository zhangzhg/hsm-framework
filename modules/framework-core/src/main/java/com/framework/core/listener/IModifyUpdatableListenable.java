package com.framework.core.listener;

/**
 * 描述:手动设置不更新修改人信息
 */
public interface IModifyUpdatableListenable {

    public boolean getModifyUpdatable();

    public void setModifyUpdatable(boolean modifyUpdatable);
}
