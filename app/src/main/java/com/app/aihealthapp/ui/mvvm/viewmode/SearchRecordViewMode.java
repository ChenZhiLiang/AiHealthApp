package com.app.aihealthapp.ui.mvvm.viewmode;

import com.app.aihealthapp.core.base.BaseMode;
import com.app.aihealthapp.ui.bean.SearchRecordBean;
import com.app.aihealthapp.ui.mvvm.view.SearchRecordView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name：AiHealth
 * @Description：描述信息
 * @Author：Chen
 * @Date：2019/8/19 22:07
 * 修改人：Chen
 * 修改时间：2019/8/19 22:07
 */
public class SearchRecordViewMode {

    private SearchRecordView mSearchRecordView;
    private BaseMode mBaseMode;

    public SearchRecordViewMode(SearchRecordView mSearchRecordView) {
        this.mSearchRecordView = mSearchRecordView;
        mBaseMode = new BaseMode();
    }


    public List<SearchRecordBean> getDatas(){

        List<SearchRecordBean> datas = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            SearchRecordBean mSearchRecordBean = new SearchRecordBean();
            datas.add(mSearchRecordBean);
        }
        return datas;
    }
}
