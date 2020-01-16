layui.extend({
  notice: '/notice'
}).define(['layer','util','element','laytpl', 'form','upload','notice'], function(exports){
  
  var $ = layui.jquery
  ,layer = layui.layer
  ,util = layui.util
  ,element = layui.element
  ,laytpl = layui.laytpl
  ,form = layui.form
  ,upload = layui.upload
  ,notice = layui.notice
  ,device = layui.device()

  ,DISABLED = 'layui-btn-disabled';

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

  //阻止IE7以下访问
  if(device.ie && device.ie < 8){
    layer.alert('如果您非得使用 IE 浏览器访问，那么请使用 IE8+');
  }
  
  layui.focusInsert = function(obj, str){
    var result, val = obj.value;
    obj.focus();
    if(document.selection){ //ie
      result = document.selection.createRange(); 
      document.selection.empty(); 
      result.text = str; 
    } else {
      result = [val.substring(0, obj.selectionStart), str, val.substr(obj.selectionEnd)];
      obj.focus();
      obj.value = result.join('');
    }
  };

  //数字前置补零
  layui.laytpl.digit = function(num, length, end){
    var str = '';
    num = String(num);
    length = length || 2;
    for(var i = num.length; i < length; i++){
      str += '0';
    }
    return num < Math.pow(10, length) ? str + (num|0) : num;
  };

  var index = {
    //Ajax
    req: function(options){
      var that = this
          ,success = options.success
          ,error = options.error
          ,debug = function(){
        return '<br><cite>URL：</cite>' + options.url;
      };

      options.data = options.data || {};
      options.headers = options.headers || {};

      delete options.success;
      delete options.error;

      return $.ajax($.extend({
        type: 'post'
        ,dataType: 'json'
        ,success: function(res){
          //只要 http 状态码正常，无论 response 的 code 是否正常都执行 success
          typeof success === 'function' && success(res);
        }
        ,error: function(e, code){
          var error = [
            '请求异常，请重试<br><cite>错误信息：</cite>'+ code
            ,debug()
          ].join('');
          view.error(error);

          typeof error === 'function' && error(res);
        }
      }, options));
    }

    ,form: {}

  };

  //手机设备的简单适配
  var treeMobile = $('.site-tree-mobile')
      ,shadeMobile = $('.site-mobile-shade')

  treeMobile.on('click', function(){
    $('body').addClass('site-mobile');
  });

  shadeMobile.on('click', function(){
    $('body').removeClass('site-mobile');
  });

  //固定Bar
/*  util.fixbar({
    bar1: '&#xe6af;'
    ,bgcolor: '#009688'
	,css: {bottom: 140,right:40}
    ,click: function(type){
      if(type === 'bar1'){
        layer.msg('诺诺热线 。。');
      }
    }
  });*/
  
  exports('index', index);
});

