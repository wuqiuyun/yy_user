package com.yiyue.user.module.mine.info.jobchoose;

import android.os.Bundle;
import android.view.View;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityJobChooseBinding;
import com.yiyue.user.model.vo.bean.EventBean;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 选择职业/脸型
 */
@CreatePresenter(JobChoosePresenter.class)
public class JobChooseActivity extends BaseMvpAppCompatActivity<IJobChooseView,JobChoosePresenter> implements IJobChooseView{

    private int baseType;//0 职业 1脸型

    private String jobName="学生";//选择的职业
    private int faceType = 2;//选择的脸型 默认为圆脸
    
    ActivityJobChooseBinding mBinding;
    
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_job_choose;
    }

    @Override
    protected void init() {
        Bundle bundle = getIntent().getExtras();
        if (null != bundle){
            baseType = bundle.getInt("type");
        }
        mBinding = (ActivityJobChooseBinding) viewDataBinding;
        initView();
        StatusBarUtil.setLightMode(this);
    }
    
    private void initView(){
        String title;
        if (baseType == 0){
            title = getString(R.string.update_job_title);
            mBinding.rgJob.setVisibility(View.VISIBLE);
            mBinding.rgFace.setVisibility(View.GONE);
        }else {
            title = getString(R.string.update_face_title);
            mBinding.rgJob.setVisibility(View.GONE);
            mBinding.rgFace.setVisibility(View.VISIBLE);
        }
        mBinding.titleView.setTitleText(title);
        mBinding.titleView.setLeftClickListener(view -> finish());

        mBinding.rgJob.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_job_1:
                    jobName = "学生";
                    break;

                case R.id.rb_job_2:
                    jobName = "商务休闲";
                    break;

                case R.id.rb_job_3:
                    jobName = "商务精英";
                    break;

                case R.id.rb_job_4:
//                case R.id.rb_job_5:
                    jobName = "企业管理者";
                    break;
            }
        });
        
        mBinding.rgFace.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_face_1: //圆脸
                    faceType = 2;
                    break;

                case R.id.rb_face_2: //方脸
                    faceType = 1;
                    break;

                case R.id.rb_face_3: //瓜子脸
                    faceType = 4;
                    break;

                case R.id.rb_face_4: //鹅蛋脸
                    faceType = 5;
                    break;

                case R.id.rb_face_5: //尖脸
                    faceType = 3;
                    break;
            }
        });
        
        mBinding.btnInviteCommit.setOnClickListener(view -> {
            if (baseType == 0){
                getMvpPresenter().updateJob(jobName);
            }else {
                getMvpPresenter().updateFace(faceType);
            }
        });
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void updateJobSuc() {
        showToast("修改成功！");
        EventBean.UpdateUserSuc update = new EventBean.UpdateUserSuc();
        update.setTag(2);
        update.setJob(jobName);
        EventBus.getDefault().post(update);
        finish();
    }

    @Override
    public void updateFaceSuc() {
        showToast("修改成功！");
        EventBean.UpdateUserSuc update = new EventBean.UpdateUserSuc();
        update.setTag(4);
        update.setFace(faceType);
        EventBus.getDefault().post(update);
        finish();
    }
}
