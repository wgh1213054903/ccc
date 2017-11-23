package com.jk.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jk.model.Json;
import com.jk.model.Power;
import com.jk.service.PowerService;
import com.jk.util.ExportExcel;



@Controller
@RequestMapping("/powerController")
//实现接口ServletContextAware
public class PowerController extends BaseController implements ServletContextAware{
	//日志记录
	private static final Logger logger = Logger.getLogger(PowerController.class);
    @Autowired
	private PowerService powerService;
    private ServletContext servletContext;
    //查询权限列表
    @RequestMapping("/powerList")
    public void powerList(Power power,HttpServletResponse response){
    	Json j = new Json();    	
    	try {
    		List<Power> powerList = powerService.powerList(power);
    		j.setSuccess(true);
    		j.setObject(powerList);
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("查询权限列表异常！");
		}
    	super.writeJson(j, response);
    }
    //获取子节点列表信息
    @RequestMapping("/getPowerRootNode")
	public void getPowerRootNode(Power power, HttpServletResponse response) throws Exception {
		List<Power> powerRootNode = powerService.getPowerRootNode(power);
		super.writeJson(powerRootNode, response);
	}
    //修改节点信息
    @RequestMapping("/updatePower")
    public void updatePower(Power power,HttpServletResponse response){
    	Json j = new Json();
    	try {
			powerService.updatePower(power);
			j.setSuccess(true);
			j.setObject(power);
			j.setMsg("修改成功！！！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("修改失败！！！");
		}
    	super.writeJson(j, response);
    
    }
    //修改保存节点信息
    @RequestMapping("/deletePower")
    public void deletePower(Power power,HttpServletResponse response){
    	Json j = new Json();
    	try {
			powerService.deletePower(power);
			j.setSuccess(true);
			j.setMsg("删除成功！！！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("删除失败！！！");
		}
    	super.writeJson(j, response);
    }
    //新增保存节点信息
    @RequestMapping("/addPowerNode")
    public void addPowerNode(Power power,HttpServletResponse response){
    	Json j = new Json();
    	try {
			powerService.addPowerNode(power);
			j.setSuccess(true);
			j.setObject(power);
			j.setMsg("新增节点成功！！！");
		} catch (Exception e) {
			e.printStackTrace();
			j.setSuccess(false);
			j.setMsg("新增节点失败！！！");
		}
    	super.writeJson(j, response);
    } 
   /**
    *  <pre>exportExcels(导出文件方法)   
    * 创建人：王广贺 wgh_java@126.com     
    * 创建时间：2017年9月25日 下午9:42:33    
    * 修改人：王广贺 wgh_java@126.com      
    * 修改时间：2017年9月25日 下午9:42:33    
    * 修改备注： 
    * @param power
    * @param checks
    * @param name
    * @param response</pre>
    */
    @RequestMapping("exportExcels")
	public void exportExcels(Power power ,String checks,String name,HttpServletResponse response){
    	try {
    		//编码格式
    		String name1 = new String(name.getBytes("ISO-8859-1"),"utf-8");
    		//名称
    		power.setName(name1);
    		//标题
    		String title = "菜单管理";
    		//复选框
    		if(checks==null || checks.equals("")){
				checks="序号,名称,图标,路径,跳转方式,类型,父节点id";
			}
    		//编码格式
    		String rowNames = new String(checks.getBytes("ISO-8859-1"),"utf-8");
    		String[] rowName =rowNames.split(",");
    		List<Object[]> list1 =new ArrayList<Object[]>();
    		List<Power>list = new ArrayList<Power>();
    		list=	powerService.powerList(power);
    		for (Power power2 : list) {
    			Object[] object =new Object[rowName.length];
				for (int i = 0; i < rowName.length; i++) {
					if(rowName[i].trim().equals("序号")){
						object[i] = power2.getId();
					}					
					if(rowName[i].trim().equals("名称")){
						object[i] =power2.getName();
					}
					if(rowName[i].trim().equals("图标")){
						object[i] =power2.getIcon();
					}
					if(rowName[i].trim().equals("路径")){
						object[i] =power2.getUrl();
					}
					if(rowName[i].trim().equals("跳转方式")){
						object[i] =power2.getTarget();
					}
					if(rowName[i].trim().equals("类型")){
						object[i] =power2.getType();
					}					
					if(rowName[i].trim().equals("父节点id")){
						if(power2.getPid()!=null){
							object[i] = power2.getPid();
						}
					}
					
				}
				list1.add(object);
			}
			ExportExcel excel= new ExportExcel(title, rowName, list1, response);
			excel.export();
    	} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    /**
     * <pre>importExcel(导入文件方法)   
     * 创建人：王广贺 wgh_java@126.com     
     * 创建时间：2017年9月26日 下午3:45:03    
     * 修改人：王广贺 wgh_java@126.com      
     * 修改时间：2017年9月26日 下午3:45:03    
     * 修改备注： 
     * @param power
     * @param file
     * @param response</pre>
     */
    @RequestMapping("importExcel")
	public void importExcel(Power power,@RequestParam("file") CommonsMultipartFile file, HttpServletResponse response){
    	//判断文件是否为空
    	if(!file.isEmpty()){
    		// 获取本地存储路径
    		String path = this.servletContext.getRealPath("/upload/");
    		logger.info(path);
    		String fileName = file.getOriginalFilename();
    		String fileType = fileName.substring(fileName.lastIndexOf("."));
    		logger.info(fileType);
    		String imgUrl = new Date().getTime() + fileType;
			File file2 = new File(path, imgUrl);
			// 将上传的文件写入到新建的文件中
			try {
				file.getFileItem().write(file2);
				//获得上传之后的文件具体路径
				String filePath = path + "\\" + imgUrl;
				List<Power> list = new ArrayList<Power>();
				//poi解析excel文件
				if(fileType.equals(".xls")){
					//创建Excel工作薄
					HSSFWorkbook hw = new HSSFWorkbook(new FileInputStream(new File(filePath)));
					//得到第一个工作表  
					HSSFSheet sheet1 = hw.getSheetAt(0);
					//遍历该表格中所有的工作表，i表示工作表的数量 getNumberOfSheets表示工作表的总数 
                         for (int i = 0; i < hw.getNumberOfSheets(); i++) {					
						        HSSFSheet sheetAt = hw.getSheetAt(i);
						      //遍历该行所有的行,j表示行数 getPhysicalNumberOfRows行的总数 
								for (int j = 3; j < sheetAt.getPhysicalNumberOfRows(); j++) {
									//获得每一个单元格
									HSSFRow row = sheetAt.getRow(j);
									Power p = new Power();
									p.setName(PowerController.getCellValue(row.getCell(1)));
									p.setIcon(PowerController.getCellValue(row.getCell(2)));
									p.setUrl(PowerController.getCellValue(row.getCell(3)));
									p.setTarget(PowerController.getCellValue(row.getCell(4)));
									p.setType(PowerController.getCellValue(row.getCell(5)));									
									list.add(p);
								}
							}
							//将菜单列表信息存入数据库
							powerService.insertPower(list);						
				} else if (fileType.equals(".xlsx")) {
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			}else {
				
    	}
    }
    

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
  //判断从Excel文件中解析出来数据的格式 
	private static String getCellValue(HSSFCell cell) {
		 String value = null;   
	        //简单的查检列类型   
	        switch(cell.getCellType())   
	        {   
	            case HSSFCell.CELL_TYPE_STRING://字符串   
	                value = cell.getRichStringCellValue().getString();   
	                break;   
	            case HSSFCell.CELL_TYPE_NUMERIC://数字   
	                long dd = (long)cell.getNumericCellValue();   
	                value = dd+"";   
	                break;   
	            case HSSFCell.CELL_TYPE_BLANK:   
	                value = "";   
	                break;      
	            case HSSFCell.CELL_TYPE_FORMULA:   
	                value = String.valueOf(cell.getCellFormula());   
	                break;   
	            case HSSFCell.CELL_TYPE_BOOLEAN://boolean型值   
	                value = String.valueOf(cell.getBooleanCellValue());   
	                break;   
	            case HSSFCell.CELL_TYPE_ERROR:   
	                value = String.valueOf(cell.getErrorCellValue());   
	                break;   
	            default:   
	                break;   
	        }   
	         return value;   	     
	}
  
}
