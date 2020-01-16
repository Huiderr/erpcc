
layui.extend({
  setter: 'config' //配置模块
  ,admin: 'lib/admin' //核心模块
  ,view: 'lib/view' //视图渲染模块
  ,notice: 'lib/extend/notice'
  ,xmSelect: 'lib/extend/xm-select'
}).define(['setter', 'admin','notice','xmSelect'], function(exports){
  var setter = layui.setter
  ,element = layui.element
  ,admin = layui.admin
  ,tabsPage = admin.tabsPage
  ,view = layui.view
  
  //打开标签页
  ,openTabsPage = function(url, text){
    //遍历页签选项卡
    var matchTo
    ,tabs = $('#LAY_app_tabsheader>li')
    ,path = url.replace(/(^http(s*):)|(\?[\s\S]*$)/g, '');
    
    tabs.each(function(index){
      var li = $(this)
      ,layid = li.attr('lay-id');
      
      if(layid === url){
        matchTo = true;
        tabsPage.index = index;
      }
    });
    
    text = text || '新标签页';

    if(setter.pageTabs){
      //如果未在选项卡中匹配到，则追加选项卡
      if(!matchTo){
        $(APP_BODY).append([
          '<div class="layadmin-tabsbody-item layui-show">'
            ,'<iframe src="'+ url +'" frameborder="0" class="layadmin-iframe"></iframe>'
          ,'</div>'
        ].join(''));
        tabsPage.index = tabs.length;
        element.tabAdd(FILTER_TAB_TBAS, {
          title: '<span>'+ text +'</span>'
          ,id: url
          ,attr: path
        });
      }
    } else {
      var iframe = admin.tabsBody(admin.tabsPage.index).find('.layadmin-iframe');
      iframe[0].contentWindow.location.href = url;
    }

    //定位当前tabs
    element.tabChange(FILTER_TAB_TBAS, url);
    admin.tabsBodyChange(tabsPage.index, {
      url: url
      ,text: text
    });
  }
  
  ,APP_BODY = '#LAY_app_body', FILTER_TAB_TBAS = 'layadmin-layout-tabs'
  ,$ = layui.$, $win = $(window);
  
  //初始
  if(admin.screen() < 2) admin.sideFlexible();
  
  view().autoRender();

  var notice = layui.notice; // 允许别名 toastr

  // 初始化配置，同一样式只需要配置一次，非必须初始化，有默认配置
  notice.options = {
    closeButton:true,//显示关闭按钮
    debug:false,//启用debug
    positionClass:"toast-bottom-right",//弹出的位置,
    showDuration:"1000",//显示的时间
    hideDuration:"1000",//消失的时间
    timeOut:"2000",//停留的时间
    extendedTimeOut:"1000",//控制时间
    showEasing:"swing",//显示时的动画缓冲方式
    hideEasing:"linear",//消失时的动画缓冲方式
    iconClass: 'toast-info', // 自定义图标，有内置，如不需要则传空 支持layui内置图标/自定义iconfont类名
    onclick: null // 点击关闭回调
  };

  //对外输出
  exports('index', {
    openTabsPage: openTabsPage
  });
});
