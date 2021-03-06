package com.topie.campus.core.api.front;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.topie.campus.common.SimplePageInfo;
import com.topie.campus.common.utils.PageConvertUtil;
import com.topie.campus.common.utils.ResponseUtil;
import com.topie.campus.common.utils.Result;
import com.topie.campus.core.dto.StaticScoreDto;
import com.topie.campus.core.model.StuCet;
import com.topie.campus.core.model.StuScore;
import com.topie.campus.core.model.Student;
import com.topie.campus.core.service.IStuCetService;
import com.topie.campus.core.service.IStuSeleCourseService;
import com.topie.campus.core.service.IStudentScoreService;
import com.topie.campus.core.service.IStudentService;
import com.topie.campus.security.utils.SecurityUtil;
import com.topie.campus.tools.excel.ExcelUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/front/student")
public class FrontStuScoreController {

    @Autowired
    IStudentScoreService studentScoreService;

    @Autowired
    IStuCetService stuCetService;

    @Autowired
    IStuSeleCourseService stuSeleCourseService;

    @Autowired
    IStudentService iStudentService;

    @RequestMapping("/score")
    @ResponseBody
    public Result findByPage(StuScore stuScore, int pageSize, int pageNum) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseUtil.error(401, "未登录");
        }
        String studentNo = iStudentService.findStudentNoByUserId(userId);
        if (StringUtils.isEmpty(studentNo)) {
            return ResponseUtil.error(401, "当前用户非学生角色");
        }
        stuScore.setStuId(studentNo);
        SimplePageInfo<StuScore> pageInfo = studentScoreService.findByPage(pageNum, pageSize, stuScore);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping("/staticScore")
    @ResponseBody
    public Result findStaticScoreByStudentId() {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseUtil.error(401, "未登录");
        }
        String studentNo = iStudentService.findStudentNoByUserId(userId);
        if (StringUtils.isEmpty(studentNo)) {
            return ResponseUtil.error(401, "当前用户非学生角色");
        }
        StaticScoreDto dto = studentScoreService.findByStuScoreStatic(studentNo);
        return ResponseUtil.success(dto);
    }

    @RequestMapping("/cetscore")
    @ResponseBody
    public Result findByPage(StuCet stuCet, int pageSize, int pageNum) {
        Integer userId = SecurityUtil.getCurrentUserId();
        if (userId == null) {
            return ResponseUtil.error(401, "未登录");
        }
        String studentNo = iStudentService.findStudentNoByUserId(userId);
        if (StringUtils.isEmpty(studentNo)) {
            return ResponseUtil.error(401, "当前用户非学生角色");
        }
        stuCet.setStuId(studentNo);
        SimplePageInfo<StuCet> pageInfo = stuCetService.findByPage(pageNum, pageSize, stuCet);
        return ResponseUtil.success(PageConvertUtil.grid(pageInfo));
    }

    @RequestMapping("/staticScoreForTeacher")
    @ResponseBody
    public Result staticScoreForTeacher(@RequestParam("studentNo") String studentNo) {
        StaticScoreDto dto = studentScoreService.findByStuScoreStatic(studentNo);
        return ResponseUtil.success(dto);
    }
    
    @RequestMapping(value = "/viewScore", method = {RequestMethod.GET, RequestMethod.POST })
    public String viewScore(Integer studentId,Model model)
    {
    	Student student = iStudentService.selectByKey(studentId);
    	List<StuScore> stuScores = studentScoreService.findAllByStudentId(student.getStudentNo());
    	StaticScoreDto dto = studentScoreService.findByStuScoreStatic(student.getStudentNo());
    	model.addAttribute("stuScores", stuScores);
    	model.addAttribute("staticScoreDto", dto);
    	model.addAttribute("stuName", student.getName());
    	model.addAttribute("stuId", student.getId());
    	return "view-score";
    }
    
    @RequestMapping(value = "/exportExcel", method = {RequestMethod.GET, RequestMethod.POST })
    public void exportExcel(Integer studentId,Model model,HttpServletResponse response) throws IOException
    {
    	String[] headers = {"学年","学期","学生名称","课程名称","课程性质","课程归属","学分","绩点","成绩","辅修标记","补考成绩","重修成绩","重修标记"};
    	Student student = iStudentService.selectByKey(studentId);
    	response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((student.getName()+ "成绩表.xlsx").getBytes(), "iso-8859-1"));
    	List<StuScore> stuScores = studentScoreService.findAllByStudentId(student.getStudentNo());
    	StaticScoreDto dto = studentScoreService.findByStuScoreStatic(student.getStudentNo());
    	OutputStream outPutStream = response.getOutputStream();
    	ExcelUtil.exportExcelXScore(headers, stuScores, dto,outPutStream, "yyyy-MM-dd");
    	outPutStream.close();
    }
}
