package com.yiyue.user.module.mine.settings.security;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivitySecurityBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.local.SharePreferencesUtils;
import com.yiyue.user.model.vo.bean.GetApplyStatusBean;
import com.yiyue.user.model.vo.bean.SecurityInfoBean;
import com.yiyue.user.model.vo.bean.WeiXin;
import com.yiyue.user.module.mine.settings.security.applystatus.ApplyStatusActivity;
import com.yiyue.user.module.mine.settings.security.cashaccount.CashAccountManageActivity;
import com.yiyue.user.module.mine.settings.security.certification.CertificationActivity;
import com.yiyue.user.module.mine.settings.security.password.UpdatePasswordActivity;
import com.yiyue.user.module.mine.settings.security.paypassword.PayPasswordActivity;
import com.yiyue.user.module.mine.settings.security.phone.PhoneVerifyActivity;
import com.yiyue.user.util.StringUtil;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lvlong on 2018/10/8.
 * <p>
 * 账号安全管理
 */
@CreatePresenter(SecurityPresenter.class)
public class SecurityActivity extends BaseMvpAppCompatActivity<ISecurityView, SecurityPresenter> implements ClickHandler, ISecurityView {

    ActivitySecurityBinding mBinding;
    private int bindWX = 0;//绑定微信，1绑定，0未绑定 ,
    private String mMobile;//当前手机号
    private int userAuthType = 0;//提现账户管理，1支付密码
    private int isAuth = 0;//1已实名，0未实名
    private String realName;//真实姓名
    //拉起微信登录界面
    /**
     * 微信登录方法
     */
    private IWXAPI wxapi; // IWXAPI 是第三方app和微信通信的openapi接口
    private String wechatUrl;//微信返回拼接地址
    private String wechatOpenid;//微信的OPENID
    private boolean isBindWx = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_security;
    }

    @Override
    protected void init() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        wxapi = WXAPIFactory.createWXAPI(this, Constants.WEIXIN_APPID, true);
        wxapi.registerApp(Constants.WEIXIN_APPID);

        mBinding = (ActivitySecurityBinding) viewDataBinding;
        mBinding.setClick(this);

        setTitleView();

        mMobile = StringUtil.getPhoneNumber(AccountManager.getInstance().getMobile());
        mBinding.tvVersionCode.setText(mMobile);

        StatusBarUtil.setLightMode(this);

        getMvpPresenter().getAccountInfoAccount();//获取账户信息
    }

    private void setTitleView() {
        mBinding.titleView.setLeftClickListener(view -> finish());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.rl_modify_phone:         //修改手机号
                startActivity(this, PhoneVerifyActivity.class);
                break;

            case R.id.rl_modify_password:      //修改密码
                startActivity(this, UpdatePasswordActivity.class);
                break;

            case R.id.rl_binding_wx:           //绑定微信号
                if (bindWX == 0) {
                    wxlogin();
                } else {
                    ToastUtils.shortToast("已绑定微信");
                }
                break;

            case R.id.rl_binding_account:      //提现账户管理
                if (isAuth==1){
                    startActivity(this,CashAccountManageActivity.class);
                }else {
                    ToastUtils.shortToast("实名认证未通过");
                }
                break;

            case R.id.rl_pay_pwd:              //支付密码
                getMvpPresenter().initPayWord(this);
                break;

            case R.id.rl_name_auth:            //实名认证
                getMvpPresenter().getApplyStatus(this);
                break;
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    /**
     * EventBus接受指令类
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WeiXin weiXin) {
        DLog.e("收到eventbus请求 type:" + weiXin.getType());
        if (weiXin.getType() == 1&&isBindWx) {//微信登录
            if (!TextUtils.isEmpty(weiXin.getCode())&&weiXin.getCode()!=null){
                getMvpPresenter().bindWXAccount(weiXin.getCode(), this);
            }
        }
    }

    /**
     * 微信登陆(三个步骤)
     * 1.微信授权登陆
     * 2.根据授权登陆code 获取该用户token
     * 3.根据token获取用户资料
     */
    public void wxlogin() {
        if (wxapi != null && wxapi.isWXAppInstalled()) {
            isBindWx = true;
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = String.valueOf(System.currentTimeMillis());
            wxapi.sendReq(req);
        } else {
            ToastUtils.shortToast("您尚未安装微信");
        }
    }

    @Override
    public void onAccountInfoSuccess(SecurityInfoBean bean) {
        if (bean != null) {
            bindWX = bean.getBindWX();
            isAuth = bean.getIsAuth();
            if(isAuth==1){
                realName = bean.getRealName();
                SharePreferencesUtils.getSharePreferencesUtils().setRealName(realName);
            }
            if (bindWX == 0) {
                mBinding.tvBindingWx.setText("未绑定");
            } else {
                mBinding.tvBindingWx.setText("已绑定");
            }
        }
    }

    @Override
    public void onBindWxSuccess() {
        showToast("绑定微信号成功");
        getMvpPresenter().getAccountInfoAccount();//获取账户信息
    }

    @Override
    public void getApplyStatusSuc(GetApplyStatusBean status) {
        switch (status.getApplyStatus()) {
            case 1://通过
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("status",status);
                ApplyStatusActivity.startActivity(SecurityActivity.this,ApplyStatusActivity.class, bundle1);
                break;
            case 0://待审核
            case -1://驳回
                Bundle bundle = new Bundle();
                bundle.putSerializable("status",status);
                ApplyStatusActivity.startActivity(SecurityActivity.this,ApplyStatusActivity.class, bundle);
                break;
            case 3://未申请
                CertificationActivity.startActivity(SecurityActivity.this, CertificationActivity.class);
                break;
        }
    }

    @Override
    public void getApplyStatusFail() {
        showToast("获取实名认证信息失败!");
    }

    @Override//支付密码状态获取成功
    public void oninitPayWordInfoSuccess(String json) {
        //data=0(未设置)，data=1(已设置)。若未设置支付密码，弹窗跳转到设置支付密码页面
        Bundle bundle = new Bundle();
        Log.e("---------","-----"+json);
        if (json.equals("0")||json.equals("0.0")){
            bundle.putBoolean("isrepin", false);
        }else {
            bundle.putBoolean("isrepin", true);
        }
        startActivity(this, PayPasswordActivity.class, bundle);
    }

    @Override
    public void oninitPayWordInfoFail() {

    }

}
