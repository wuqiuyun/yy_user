package com.yiyue.user.module.mine.stylist.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yiyue.user.R;
import com.yiyue.user.base.BaseMvpAppCompatActivity;
import com.yiyue.user.base.adapter.BaseRecycleViewAdapter;
import com.yiyue.user.component.databind.ClickHandler;
import com.yiyue.user.component.recycleview.GridSpacingItemDecoration;
import com.yiyue.user.component.recycleview.SpaceItemHorizontalDecoration;
import com.yiyue.user.component.toast.ToastUtils;
import com.yiyue.user.databinding.ActivityProjectListBinding;
import com.yiyue.user.helper.AccountManager;
import com.yiyue.user.model.constant.Constants;
import com.yiyue.user.model.vo.bean.ProjectBean;
import com.yiyue.user.model.vo.bean.ServiceDetailesBean;
import com.yiyue.user.model.vo.bean.StylistServerBean;
import com.yiyue.user.model.vo.bean.UserBean;
import com.yiyue.user.model.vo.requestbody.CreateOrderBody;
import com.yiyue.user.module.login.LoginActivity;
import com.yiyue.user.module.mine.stylist.order.OrderConfirmActivity;
import com.yiyue.user.module.mine.stylist.selectstore.SelectStoreActivity;
import com.yiyue.user.util.CacheActivity;
import com.yiyue.user.util.FormatUtil;
import com.yiyue.user.widget.popwindow.PopupUtil;
import com.yl.core.component.image.ImageLoader;
import com.yl.core.component.log.DLog;
import com.yl.core.component.mvp.factory.CreatePresenter;
import com.yl.core.util.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目列表
 * Created by wqy on 2018/11/5.
 */
@CreatePresenter(ProjectListPresenter.class)
public class ProjectListActivity extends BaseMvpAppCompatActivity<IProjectListView, ProjectListPresenter>
        implements IProjectListView, ClickHandler {

    private ProjectListAdapter projectListAdapter;
    private ArrayList<StylistServerBean.ServerItemsBean> projectBeans = new ArrayList<>();

    private SelectorListAdapter selectorListAdapter;
    private ArrayList<StylistServerBean.ServerItemsBean> selectorBeans = new ArrayList<>();
    private ActivityProjectListBinding mProjectListBinding;
    private PopupUtil mPopupUtil;
    private ServiceContenAdapter mServiceContentAdapter;
    private ServiceContenAdapter mServiceContentAdapter2;
    private TextView mTv_service_name;
    private ImageView mIv_service;
    private TextView mTv_description;
    private TextView mTv_price;
    private ArrayList<ProjectBean> tempList = new ArrayList<>();
    private ArrayList<ProjectBean> tempList2 = new ArrayList<>();
    private ServiceDetailesBean serviceDetailesBean;
    private int tempPosition=-1;
    private float price;
    private float projectPrice;//项目基本价格
    private float potionPrice;//药水价格
    private ProjectBean mServiceSubContentBean;
    private String mStylistId;
    private CreateOrderBody mCreateOrderBody;
    private StylistServerBean.ServerItemsBean mTempServerItemsBean;
    private Button mBtn_ok;
    private ProjectBean mServiceContentBean;
    private Bundle mBundle;
    private UserBean userBean = new UserBean();

    @Override
    public void showToast(String message) {
        ToastUtils.shortToast(message);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_project_list;
    }

    @Override
    protected void init() {
        initView();
        loadData();
    }
    private void loadData() {
        initExtra();
    }

    private void initExtra() {
        String userId = AccountManager.getInstance().getUserId();
         mBundle = getIntent().getExtras();
        if (mBundle!=null){
            mCreateOrderBody = mBundle.getParcelable(Constants.STYLIST_ORDER_BODY);
            mStylistId = mBundle.getString(Constants.STYLISTId);
        }else {
            mBundle=new Bundle();
        }
        if (mCreateOrderBody==null){
            mCreateOrderBody = new CreateOrderBody();
        }else {
            mProjectListBinding.tvPriceSum.setText(FormatUtil.Formatstring("￥"+mCreateOrderBody.getOrderamount()));
        }
        mCreateOrderBody.setStylistId(mStylistId);
        mCreateOrderBody.setUserId(userId);
        getMvpPresenter().getStylistServer(mCreateOrderBody.getStylistId(),mCreateOrderBody.getUserId());

    }

    private void initView() {
        StatusBarUtil.setLightMode(this);
        if (!CacheActivity.activityList.contains(ProjectListActivity.this)) {
            CacheActivity.addActivity(ProjectListActivity.this);
        }
        mProjectListBinding = (ActivityProjectListBinding) viewDataBinding;
        mProjectListBinding.titleView.setLeftClickListener(view -> finish());
        mProjectListBinding.setClick(this);

        // 单选项目列表(上面那个列表)
        mProjectListBinding.projectList.setLayoutManager(new LinearLayoutManager(this));
        mProjectListBinding.projectList.addItemDecoration(new SpaceItemHorizontalDecoration(1));
        projectListAdapter = new ProjectListAdapter(this);
        mProjectListBinding.projectList.setAdapter(projectListAdapter);
        projectListAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {


            @Override
            public void onItemClick(View view, int position) {
                mServiceContentBean=null;
                mServiceSubContentBean=null;
                mTempServerItemsBean = projectListAdapter.getDatas().get(position);
                price = Float.valueOf(mTempServerItemsBean.getPrice());
                mProjectListBinding.tvPriceSum.setText("￥"+price);
                //服务选择需求是单选
                for (StylistServerBean.ServerItemsBean serverItemsBean:projectListAdapter.getDatas()) {
                    serverItemsBean.setChecked(false);
                }
                projectListAdapter.getDatas().get(position).setChecked(true);
                projectListAdapter.notifyDataSetChanged();
                initAdapterData(1);
            }
        });
        // （下面那个列表）
        mProjectListBinding.selectorList.setLayoutManager(new LinearLayoutManager(this));
        mProjectListBinding.selectorList.addItemDecoration(new SpaceItemHorizontalDecoration(1));
        selectorListAdapter = new SelectorListAdapter(this);
        mProjectListBinding.selectorList.setAdapter(selectorListAdapter);
        selectorListAdapter.setItemListener(new BaseRecycleViewAdapter.SimpleRecycleViewItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                mBtn_ok.setText(getString(R.string.cancel));
                tempPosition=position;
                    tempList.clear();
                    tempList2.clear();
                mTv_price.setText("");
                getMvpPresenter().getStylistServerDetail(selectorListAdapter.getDatas().get(position).getServiceId(),ProjectListActivity.this);
            }
        });
        initPop();
    }

    private void initPop() {
        mPopupUtil = PopupUtil.create()
                .setContext(this)
                .setContentView(R.layout.popwin_server_content, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
                .setAnimationStyle(R.style.TimePopupWindowAnimation)
                .setFocusAndOutsideEnable(true)
                .setBackgroundDimEnable(true)
                .setDimValue(0.4f)
                .setOnViewListener(new PopupUtil.OnViewListener() {
            @Override
            public void initViews(View view, PopupUtil basePopup) {
                mTv_service_name = view.findViewById(R.id.tv_service_name);
                mIv_service = view.findViewById(R.id.iv_service);
                mTv_description = view.findViewById(R.id.tv_description);
                mTv_price = view.findViewById(R.id.tv_price);
                mBtn_ok = view.findViewById(R.id.btn_ok);
                mBtn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mBtn_ok.getText().toString().equals(getString(R.string.ok))){
                            price=potionPrice+projectPrice;
                            mProjectListBinding.tvPriceSum.setText("￥"+price);

                            for (StylistServerBean.ServerItemsBean serverItemsBean:selectorListAdapter.getDatas() ){
                                serverItemsBean.setChecked(false);
                            }

                            selectorListAdapter.getDatas().get(tempPosition).setChecked(true);
                            if (mServiceContentBean!=null&&mServiceSubContentBean!=null){
                                selectorListAdapter.getDatas().get(tempPosition).setSelectedTitle(mServiceContentBean.getLabel()+"-"+mServiceSubContentBean.getLabel());
                            }
                            selectorListAdapter.getDatas().get(tempPosition).setSelectedPrice(String.valueOf(price));
                            mTempServerItemsBean = selectorListAdapter.getDatas().get(tempPosition);
                            selectorListAdapter.notifyDataSetChanged();
                            initAdapterData(0);
                        }else {
                            mServiceSubContentBean=null;
                        }
                        mPopupUtil.dismiss();
                    }
                });

                //服务类型列表
                RecyclerView rv_service_options = view.findViewById(R.id.rv_service_options);
                rv_service_options.setLayoutManager(new GridLayoutManager(ProjectListActivity.this, 3));
                mServiceContentAdapter = new ServiceContenAdapter(ProjectListActivity.this,1);
                rv_service_options.setAdapter(mServiceContentAdapter);

                mServiceContentAdapter.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        for (ProjectBean serviceContentBean : mServiceContentAdapter.getDatas()) {
                            serviceContentBean.setChecked(false);
                        }
                        mServiceContentAdapter.getDatas().get(position).setChecked(true);
                        mServiceContentBean = mServiceContentAdapter.getDatas().get(position);

                        List<ServiceDetailesBean.ServiceOptionsBean.ServiceOptionDetailsBean> serviceOptionDetails = serviceDetailesBean.getServiceOptions().get(position).getServiceOptionDetails();
                        tempList2.clear();
                        for (ServiceDetailesBean.ServiceOptionsBean.ServiceOptionDetailsBean serviceOptionDetailsBean:
                                serviceOptionDetails) {
                                if (mServiceSubContentBean!=null&&mServiceSubContentBean.getId()== serviceOptionDetailsBean.getServiceOptionId()){
                                    tempList2.add(mServiceSubContentBean);
                                }else {
                                    tempList2.add(new ProjectBean(serviceOptionDetailsBean.getOptionvalue(),false,serviceOptionDetailsBean.getServiceOptionId(),serviceOptionDetailsBean.getPrice()));
                                }
                        }

                        mServiceContentAdapter2.setDatas(tempList2,true);
                        mServiceContentAdapter.notifyDataSetChanged();
                        mServiceContentAdapter2.notifyDataSetChanged();
                    }
                    @Override
                    public void OnItemLongClickListener(View view, int position) {
                    }
                });

                //药水列表
                RecyclerView rv_service_option_details = view.findViewById(R.id.rv_service_option_details);
                rv_service_option_details.setLayoutManager(new GridLayoutManager(ProjectListActivity.this, 3));
                rv_service_option_details.addItemDecoration(new GridSpacingItemDecoration(3, 50, false));
                mServiceContentAdapter2 = new ServiceContenAdapter(ProjectListActivity.this,0);
                rv_service_option_details.setAdapter(mServiceContentAdapter2);
                mServiceContentAdapter2.setItemListener(new BaseRecycleViewAdapter.RecycleViewItemListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        for (ProjectBean serviceContentBean : mServiceContentAdapter2.getDatas()) {
                            serviceContentBean.setChecked(false);
                        }
                        mBtn_ok.setText(getString(R.string.ok));
                        mServiceSubContentBean = mServiceContentAdapter2.getDatas().get(position);
                        potionPrice=Float.valueOf(mServiceSubContentBean.getPrice());
                        mTv_price.setText(FormatUtil.Formatstring("￥"+(projectPrice+potionPrice)));
                        mServiceContentAdapter2.getDatas().get(position).setChecked(true);
                        mServiceContentAdapter2.notifyDataSetChanged();

                    }

                    @Override
                    public void OnItemLongClickListener(View view, int position) {

                    }
                });

            }
        })
        .apply();

    }

    private void initAdapterData(int type) {
        if (type==0){
            for (StylistServerBean.ServerItemsBean serverItemsBean:projectBeans ){
                serverItemsBean.setChecked(false);
            }
            projectListAdapter.setDatas(projectBeans,true);
        }else {
            for (StylistServerBean.ServerItemsBean serverItemsBean:selectorBeans ){
                serverItemsBean.setChecked(false);
            }
            selectorListAdapter.setDatas(selectorBeans,true);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_reservation: // 立即预约
                if (!AccountManager.getInstance().isLogin()){
                    LoginActivity.startActivity(ProjectListActivity.this,LoginActivity.class);
                return;
                }
                if (mTempServerItemsBean!=null){
                    //单项服务将此项去掉
                    mCreateOrderBody.setOptionId(null);
                    //此页面需提交数据
                    mCreateOrderBody.setServiceId(mTempServerItemsBean.getServiceId());
                    mCreateOrderBody.setOrderamount(String.valueOf(price));
                    if (mServiceContentBean!=null&&mServiceSubContentBean!=null){
                        mCreateOrderBody.setServiceName(mTempServerItemsBean.getServicename());
                        mCreateOrderBody.setOptionId(String.valueOf(mServiceSubContentBean.getId()));
                    }else {
                        mCreateOrderBody.setServiceName(mTempServerItemsBean.getServicename());
                    }
                        mCreateOrderBody.setServicemodel("1");
                }
                if (mCreateOrderBody.getIsResult()!=null){
                    //提交订单页返回
                    Intent intent = new Intent();
                    intent.putExtra(Constants.STYLIST_ORDER_BODY,mCreateOrderBody);
                    setResult(OrderConfirmActivity.FROMACTIVITY,intent);
                    finish();

                }else {
                    if (mTempServerItemsBean==null){
                        showToast("请选择服务内容");
                        return;
                    }
                    mBundle.putParcelable(Constants.STYLIST_ORDER_BODY,mCreateOrderBody);
                    mBundle.putSerializable(Constants.USER_INFO_BODY,userBean);
                    SelectStoreActivity.startActivity(ProjectListActivity.this, SelectStoreActivity.class,mBundle);
                }


                break;
        }
    }

    @Override
    public void onSuccess(StylistServerBean stylistServerBean) {
        mProjectListBinding.tvName.setText(FormatUtil.Formatstring(stylistServerBean.getNickname()));
        mProjectListBinding.tvRank.setText(FormatUtil.Formatstring(stylistServerBean.getPosition()));
        ImageLoader.loadImage(mProjectListBinding.ivAvatar,stylistServerBean.getHeadportrait());
        userBean.setNickname(stylistServerBean.getNickname());
        userBean.setPosition(stylistServerBean.getPosition());
        userBean.setHeadImg(stylistServerBean.getHeadportrait());
        userBean.setGender(stylistServerBean.getGender());
        for (StylistServerBean.ServerItemsBean serverItemsBean:stylistServerBean.getServerItems()) {
            if (serverItemsBean.getIsoption().equals("0")){//是否有选项0没有1有
                if (mCreateOrderBody.getServiceId()!=null&&mCreateOrderBody.getServiceId().equals(serverItemsBean.getServiceId())){
                    serverItemsBean.setChecked(true);
                    projectBeans.add(serverItemsBean);
                }else {
                    projectBeans.add(serverItemsBean);
                }
            }else {

                if (mCreateOrderBody.getServiceId()!=null&&mCreateOrderBody.getServiceId().equals(serverItemsBean.getServiceId())){
                    serverItemsBean.setChecked(true);
                    selectorBeans.add(serverItemsBean);
                }else {
                    selectorBeans.add(serverItemsBean);
                }
            }
        }
        projectListAdapter.setDatas(projectBeans,true);
        selectorListAdapter.setDatas(selectorBeans,true);
    }

    @Override
    public void onGetStylistServerDetailSuccess(ServiceDetailesBean serviceDetailesBean) {
        this.serviceDetailesBean=serviceDetailesBean;
        setPopAdapterData(serviceDetailesBean);
    }

    /**
     * 更改PopupWindow数据
     * @param serviceDetailesBean
     */
    public void setPopAdapterData(ServiceDetailesBean serviceDetailesBean) {
        this.projectPrice =Float.valueOf(serviceDetailesBean.getPrice());
        mTv_service_name.setText(FormatUtil.Formatstring(serviceDetailesBean.getServicename()));
        ImageLoader.loadImage(mIv_service,serviceDetailesBean.getPicture());
        mTv_description.setText(FormatUtil.Formatstring(serviceDetailesBean.getDescription()));
        if(serviceDetailesBean.getServiceOptions()==null||serviceDetailesBean.getServiceOptions().size()==0)return;

        for (int i = 0; i < serviceDetailesBean.getServiceOptions().size(); i++) {
            ServiceDetailesBean.ServiceOptionsBean serviceOptionsBean = serviceDetailesBean.getServiceOptions().get(i);

            if (mServiceContentBean!=null&&mServiceContentBean.getId()==serviceOptionsBean.getOptionId()){
                for (ServiceDetailesBean.ServiceOptionsBean.ServiceOptionDetailsBean bean:serviceOptionsBean.getServiceOptionDetails()) {
                    if (mServiceSubContentBean != null && mServiceSubContentBean.getId() == bean.getServiceOptionId()) {
                        tempList2.add(mServiceSubContentBean);
                    } else {
                        tempList2.add(new ProjectBean(bean.getOptionvalue(),false,bean.getServiceOptionId(),bean.getPrice()));
                    }
                }
                tempList.add(mServiceContentBean);
            }else {
                tempList.add(new ProjectBean(serviceOptionsBean.getOptiontitle(),false,serviceOptionsBean.getOptionId()));
            }
        }
        mServiceContentAdapter.setDatas(tempList,true);
        mServiceContentAdapter2.setDatas(tempList2,true);
        mPopupUtil.showAtLocation(mProjectListBinding.llReservation, Gravity.BOTTOM,0,0);
    }
}
