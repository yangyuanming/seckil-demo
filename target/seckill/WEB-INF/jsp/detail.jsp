<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀商品详情</title>
    <%@ include file="common/head.jsp"%>
    <!-- jQuery cookie操作插件 -->
    <script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
    <!-- jQery countDonw倒计时插件  -->
    <script src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
    <!-- 开始编写交互逻辑 -->
    <script src="/resources/js/seckill.js"  type="text/javascript"></script>
    <script type="text/javascript">
        $(function(){
            //使用EL表达式传入参数
            seckill.detail.init({
                seckillId : ${seckill.seckillId},
                startTime : ${seckill.startTime.time},//毫秒
                endTime : ${seckill.endTime.time}
            });
        });
    </script>
</head>
<body>
<!--页面显示部分-->
<div class="container">
    <div class="panel panel-default text-center">
        <div class="pannel-heading"><h2>${seckill.name}</h2></div>
    </div>
    <div class="panel-body text-center">
        <h2 class="text-danger">
            <!-- 显示time图标 -->
            <span class="glyphicon glyphicon-time"></span>
            <!-- 展示倒计时 -->
            <span class="glyphicon" id="seckillBox"></span>
        </h2>
    </div>
</div>
</div>

<!-- 登录弹出层，输入电话 -->
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killphone" id="killphoneKey"
                               placeholder="填手机号^O^" class="form-control" />
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <span id="killphoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span> Submit
                </button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
