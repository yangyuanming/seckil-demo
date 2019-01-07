package com.yangyuanming.seckill.web;

import com.yangyuanming.seckill.dto.Exposer;
import com.yangyuanming.seckill.dto.RespData;
import com.yangyuanming.seckill.entity.Seckill;
import com.yangyuanming.seckill.entity.SuccessKilled;
import com.yangyuanming.seckill.enums.CodeEnum;
import com.yangyuanming.seckill.enums.SeckillResultEnum;
import com.yangyuanming.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Yang Yuanming on 2018/8/19 with IntelliJ IDEA.
 * Description:
 */

@Controller
@RequestMapping("/seckill")//模块  restful url：模块/资源/{id}/细分
public class SeckillController {

    private final Logger logger=LoggerFactory.getLogger(this.getClass());
    @Autowired
    @Qualifier("seckillServiceImpl")
    private SeckillService seckillService;


   @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(Model model,int offset,int limit){
        List<Seckill> list=seckillService.getSeckillList(offset,limit);
        model.addAttribute("list",list);
        //model + list.jsp  ModelAndView
        return "list"; //WEB-INF/jsp/list.jsp
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId,Model model){
       if(seckillId==null){
           return "redirect:/seckill/list";
       }
       Seckill seckill=seckillService.getById(seckillId);
       if(seckill==null){
           return "forward:/seckill/list";
       }

       model.addAttribute("seckill",seckill);
       return "detail";
    }

    @RequestMapping(value="/{seckillId}/exposer",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
    @ResponseBody
    public RespData getExposer(@PathVariable("seckillId")Long seckillId) {
        RespData respData = null;
        Exposer exposer=null;
        exposer = seckillService.exportSeckillUrl(seckillId);
        respData =RespData.ok();
        respData.setMsg(CodeEnum.OK.getMsg());
        respData.putDataValue("exposer",exposer);
        return respData;
    }

    @RequestMapping(value="/{seckillId}/execution",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
    @ResponseBody
    //produces:指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；必须要和@ResponseBody注解一起使用才可以
    public RespData execute(@PathVariable("seckillId")Long seckillId,String md5, @CookieValue(value="userPhone",required = false) Long userPhone ) {

        RespData respData = null;
        SuccessKilled successKilled = null;
        if (userPhone == null) {
            respData = RespData.unauthorized();
            respData.setMsg(CodeEnum.UNAUTHORIZED.getMsg());
            return respData;
        }

        try {
            int result=seckillService.executeSeckillWithProcedure(seckillId,userPhone,md5);
            if(result==1){
                successKilled = seckillService.getSuccessKilledBySeckillIdAndPhone(seckillId,userPhone);
                respData = RespData.ok();
                respData.setMsg(SeckillResultEnum.getSeckillStateEnum(result).getResult());
                return respData.putDataValue("successKilled",successKilled);
            }else{
                respData=RespData.forbidden();
                respData.setMsg(SeckillResultEnum.getSeckillStateEnum(result).getResult());

            }
        } catch (Exception e) {
            respData=RespData.forbidden();
            respData.setMsg(SeckillResultEnum.INNER_ERROR.getResult());
            logger.error(e.getMessage(),e);
        }
          return respData;
   }

//    @RequestMapping(value="/{seckillId}/execution",method=RequestMethod.POST,produces={"application/json;charset=utf-8"})
//    @ResponseBody
//    public RespData execute(@PathVariable("seckillId")Long seckillId,String md5, @CookieValue(value="userPhone",required = false) Long userPhone ) {
//       logger.info("seckill的id："+seckillId);
//        logger.info("md5:"+md5);
//       RespData respData = null;
//        SuccessKilled successKilled = null;
//        if (userPhone == null) {
//            respData = RespData.unauthorized();
//            respData.setMsg(CodeEnum.UNAUTHORIZED.getMsg());
//            return respData;
//        }
//        try {
//            successKilled = seckillService.executeSeckill(seckillId, userPhone, md5);
//            respData = RespData.ok();
//            respData.setMsg(SeckillResultEnum.SUCCESS.getResult());
//            return respData.putDataValue("successKilled",successKilled);
//        } catch (RepeatKillException rke) {
//               respData=RespData.forbidden();
//               respData.setMsg(SeckillResultEnum.REPEAT_KILL.getResult());
//               return respData;
//        } catch (SeckillCloseException sce) {
//                respData=RespData.forbidden();
//                respData.setMsg(SeckillResultEnum.END.getResult());
//                return  respData;
//        } catch (SeckillException e) {
//            logger.error(e.getMessage(), e);
//            respData=RespData.forbidden();
//            respData.setMsg(SeckillResultEnum.INNER_ERROR.getResult());
//            return respData;
//        }
//    }

    @RequestMapping(value="/currentTime",method = RequestMethod.GET,produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public RespData getTime(){
       Date d=new Date();
       RespData respData=RespData.ok();
       respData.setMsg(CodeEnum.OK.getMsg());
       respData.putDataValue("time",d.getTime());
       return  respData;

    }

}