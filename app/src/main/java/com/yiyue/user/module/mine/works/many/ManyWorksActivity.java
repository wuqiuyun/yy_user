package com.yiyue.user.module.mine.works.many;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityManyWorksBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.bean.OpusBean;
import com.yiyue.user.model.vo.bean.WorksLabelBean;
import com.yiyue.user.module.mine.works.details.WorksDetailsActivity;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.ArrayList;

import static com.yiyue.user.model.constant.Constants.HEAD_PORTRAIT;
import static com.yiyue.user.model.constant.Constants.NICK_NAME;
import static com.yiyue.user.model.constant.Constants.OPUSID;
import static com.yiyue.user.model.constant.Constants.STYLISTId;
import static com.yiyue.user.model.constant.Constants.STYLIST_POSITION;

/**
 * 作品集
 * <p>
 * Create by wqy on 2018/11/10
 */
@CreatePresenter(ManyWorksPrenster.class)
public class ManyWorksActivity extends BaseMvpAppCompatActivity<IManyWorksView, ManyWorksPrenster>
        implements IManyWorksView {

    private ActivityManyWorksBinding mBinding;
    private ArrayList<OpusBean.OpusListBean> opusList = new ArrayList<>();
    private ArrayList<WorksLabelBean> mLabelBeans = new ArrayList<>();

    private ManyWorksAdapter adapter;
    private String stylistId = "";
    private String userId = "";
    private String headPortrait = "";//头像
    private String nickName = "";//昵称
    private String stylistPosition = ""; // 职级

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_many_works;
    }

    @Override
    protected void init() {
        hasExtras();
        initView();
        loadData();
    }

    private void hasExtras() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.stylistId = bundle.getString(STYLISTId);
            this.headPortrait = bundle.getString(HEAD_PORTRAIT);
            this.nickName = bundle.getString(NICK_NAME);
            this.stylistPosition = bundle.getString(STYLIST_POSITION);
        }
    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        mBinding = (ActivityManyWorksBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.flowLayout.setOnTagClickListener((view, position, parent) -> {
            // TODO 处理选中事件
            return false;
        });

        // 作品列表
        mBinding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));
        mBinding.recycleView.addItemDecoration(new GridSpacingItemDecoration(2, 30, false));
        adapter = new ManyWorksAdapter(this);
        adapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                String opusId = String.valueOf(adapter.getDatas().get(position).getStylistOpusId());
                Bundle bundle = new Bundle();
                bundle.putString(OPUSID, opusId);
                bundle.putString(HEAD_PORTRAIT, headPortrait);
                bundle.putString(NICK_NAME, nickName);
                bundle.putString(STYLIST_POSITION, stylistPosition);
                bundle.putString(STYLISTId, stylistId);
                WorksDetailsActivity.startActivity(getBaseContext(), WorksDetailsActivity.class, bundle);
            }
        });
        mBinding.recycleView.setAdapter(adapter);
    }

    private void loadData() {
        userId = AccountManager.getInstance().getUserId();
        getMvpPresenter().getOpusList(stylistId, userId);
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    // 获取作品集成功
    @Override
    public void getOpusListSuccess(OpusBean opusBean) {
        int total;
        //标题数目 + 作品列表展示
        if (null != opusBean.getOpusList() && opusBean.getOpusList().size() > 0) {
            opusList.addAll(opusBean.getOpusList());
            adapter.setDatas(opusList, true);
            total = opusBean.getOpusList().size();
            if (total > 99) {
                mBinding.titleView.setSubTitleText("(99+)");
            } else {
                mBinding.titleView.setSubTitleText("(" + total + ")");
            }
        }

        //label展示
        if (null != opusBean.getOpusHairstyleList() && opusBean.getOpusHairstyleList().size() > 0) {
            for (OpusBean.OpusHairstyleListBean hair : opusBean.getOpusHairstyleList()) {
                mLabelBeans.add(new WorksLabelBean(hair.getHairstyleName(), hair.getCount(), hair.getHairstyleId(), 1));
            }
        }

        if (null != opusBean.getOpusFeaTureList() && opusBean.getOpusFeaTureList().size() > 0) {
            for (OpusBean.OpusFeaTureListBean feature : opusBean.getOpusFeaTureList()) {
                mLabelBeans.add(new WorksLabelBean(feature.getFeaTureName(), feature.getCount(), feature.getFeaTureId(), 2));
            }
        }

        mBinding.flowLayout.setAdapter(new TagAdapter<WorksLabelBean>(mLabelBeans) {
            @Override
            public View getView(FlowLayout parent, int position, WorksLabelBean label) {
                View view = getLayoutInflater().inflate(R.layout.item_works_label, null, false);
                TextView tvName = view.findViewById(R.id.tv_name);
                tvName.setText(label.getLabel());
                TextView tvNum = view.findViewById(R.id.tv_number);
                tvNum.setText(label.getNumber() + "");
                return view;
            }
        });

        mBinding.flowLayout.setOnTagClickListener((view, position, parent) -> {
            WorksLabelBean label = mLabelBeans.get(position);
            getMvpPresenter().getOpusListScreen(stylistId, label.getId(), label.getType());
            return true;
        });
    }

    @Override
    public void getOpusListFail() {
        showToast("获取作品集失败");
        finish();
    }

    @Override
    public void getOpusListScreenSuc(OpusBean opusBean) {
        if (null != opusBean.getOpusList() && opusBean.getOpusList().size() > 0) {
            opusList.clear();
            opusList.addAll(opusBean.getOpusList());
            adapter.setDatas(opusList, true);
        }
    }
}
