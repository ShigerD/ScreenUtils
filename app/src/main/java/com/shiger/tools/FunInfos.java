package com.shiger.tools;



public class FunInfos {
    public int mFunIndex;
    public String mFunName;
    public String mFunTargrtActiv;

    public FunInfos(int funIndex,String funName, String funTargrtActiv){
        mFunIndex = funIndex;
        mFunName = funName;
        mFunTargrtActiv = funTargrtActiv;
    }

    public FunInfos(int funIndex,String funName){
        mFunIndex = funIndex;
        mFunName = funName;
    }
}
