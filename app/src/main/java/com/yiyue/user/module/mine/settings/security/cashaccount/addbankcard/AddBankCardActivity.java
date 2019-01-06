package com.yiyue.user.module.mine.settings.security.cashaccount.addbankcard;

import android.text.TextUtils;
import android.view.View;

import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.yiyue.user.model.local.SharePreferencesUtils;
import com.yiyue.user.module.mine.wallet.withdraw.account.AccountWithdrawActivity;
import com.yiyue.user.widget.dialog.YLDialog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityAddbankcardBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.vo.bean.BankCardBean;
import com.yiyue.user.module.mine.settings.ISettingsView;
import com.yiyue.user.widget.picker.OptionPickerView;
import com.yl.core.util.KeyboardUtil;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * 绑定银行卡
 * <p>
 * Create by lyj on 2018/11/8
 */
@CreatePresenter(AddBankCardPresenter.class)
public class AddBankCardActivity extends BaseMvpAppCompatActivity<AddBankCardView, AddBankCardPresenter>
        implements ClickHandler,AddBankCardView{

    ActivityAddbankcardBinding mBinding;
    private String bankType = "";
    private ArrayList<BankCardBean> bankCardList = new ArrayList<>();

    private ArrayList<String> bankCardList1 = new ArrayList<>();

    private OptionPickerView opView; // 条件选择器

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_addbankcard;
    }

    @Override
    protected void init() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setLightMode(AddBankCardActivity.this);
        initView();
    }

    private void initView() {
        mBinding = (ActivityAddbankcardBinding) viewDataBinding;
        mBinding.titleView.setLeftClickListener(v -> finish());
        mBinding.setClick(this);
        String realName = SharePreferencesUtils.getSharePreferencesUtils().getUserRealName();
        if (realName!=null&&!TextUtils.isEmpty(realName)){
            mBinding.etBankcardName.setText(realName);
            mBinding.etBankcardName.setEnabled(false);
        }
    }

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_bankcard ://选择银行
                KeyboardUtil.closeSoftKeyboard(this);
                getMvpPresenter().getAllBank(this);
                break;
            case R.id.btn_addbankcard_push ://提交
                getMvpPresenter().bindAccount(mBinding.etBankcardId.getText().toString().trim(),
                        "BANK",
                        mBinding.etBankcardBrach.getText().toString().trim(),
                        mBinding.etBankcardName.getText().toString().trim(),
                        bankType,//ICBC ..简称
                        AccountManager.getInstance().getUserId(),this);
                break;
            case R.id.iv_doubt_nor: // 疑问
                showDLDialog();
                break;
        }
    }

    /**
     * 疑问弹窗
     */
    private void showDLDialog() {
        new YLDialog.Builder(this)
                .setTitle("提示")
                .setType(YLDialog.DIALOG_TYPE_NORMAL)
                .setMessage(getResources().getString(R.string.bind_bankcard_tip))
                .setPositiveButton("我知道了", (dialog, which) -> {
                    dialog.dismiss();
                })
                .create()
                .show();
    }

    @Override//银行列表
    public void onBankSuccess(ArrayList<BankCardBean> bankCardBeans) {
        if (bankCardBeans!=null){
            bankCardList1.clear();
            bankCardList = bankCardBeans;
            for (int i = 0;i<bankCardList.size();i++){
                bankCardList1.add(bankCardList.get(i).getBankname());
            }
            // 初始化条件选择器
            initOptionPicker();
            if (opView != null) {
                opView.show();
            }
        }
    }

    @Override//绑定成功
    public void onBindSuccess() {
        AddBankCardSucceedActivity.startActivity(this , AddBankCardSucceedActivity.class);
        finish();
    }

    @Override//失败
    public void onFail(int type) {

    }

    private void initOptionPicker() {
        opView = new OptionPickerView(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                if (options1>=bankCardList.size()){
                    return;
                }
                mBinding.tvBankcardBankname.setText(bankCardList1.get(options1));
                bankType = bankCardList.get(options1).getBankshort();
            }
        });
        opView.set(bankCardList1);
    }


}
