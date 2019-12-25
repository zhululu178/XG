package com.letsup.habit.app.backend.util;

import com.alibaba.fastjson.JSONArray;
import com.letsup.habit.app.backend.HabApplicationTest;
import com.letsup.habit.app.backend.constants.HabitFrequencyEnum;
import com.letsup.habit.app.backend.constants.HabitTypeEnum;
import com.letsup.habit.app.backend.dao.entity.*;
import com.letsup.habit.app.backend.dao.mapper.*;
import com.letsup.habit.app.backend.vo.HabitRemindVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ImportHabitTemplateData extends HabApplicationTest {
    @Autowired
    private HabAppHabitTemplateMapperExt habAppHabitTemplateMapper;
    @Autowired
    private HabAppHabitClassMapperExt habAppHabitClassMapper;
    @Autowired
    private HabAppHabitTemplateTagMapperExt habAppHabitTemplateTagMapperExt;
    @Autowired
    private HabAppTemplateTagReMapperExt habAppTemplateTagReMapperExt;


    public static final String OFFICE_EXCEL_XLS = "xls";
    public static final String OFFICE_EXCEL_XLSX = "xlsx";

    private Map<String, FieldType> habitTemplateFields = new HashMap<>();
    private Map<String, Long> classMap = new HashMap<>();
    private Map<String, Long> tagMap = new HashMap<>();
    @Before
    public void init() {
        habitTemplateFields.put("标题", new FieldType("title", "java.lang.String"));
        habitTemplateFields.put("描述", new FieldType("description", "java.lang.String"));
        habitTemplateFields.put("习惯类型（计时，计数）", new FieldType("type", "java.lang.String"));
        habitTemplateFields.put("坚持天数", new FieldType("stickDays", "java.lang.Integer"));
        habitTemplateFields.put("每日计数量", new FieldType("dailyCount", "java.lang.Integer"));
        habitTemplateFields.put("每计数次时间", new FieldType("eachTime", "java.lang.Integer"));
        habitTemplateFields.put("一级分类", new FieldType("class1", "java.lang.String"));
        habitTemplateFields.put("二级分类", new FieldType("class2", "java.lang.String"));
        habitTemplateFields.put("提醒", new FieldType("reminds", "java.lang.String"));
        habitTemplateFields.put("频率类型", new FieldType("freqType", "java.lang.String"));
        habitTemplateFields.put("频率日期", new FieldType("freqDays", "java.lang.String"));
        habitTemplateFields.put("计数器", new FieldType("counter", "java.lang.String"));
        habitTemplateFields.put("计时器", new FieldType("timer", "java.lang.String"));
        habitTemplateFields.put("序号（分类中的排序）", new FieldType("tempOrder", "java.lang.Integer"));
        habitTemplateFields.put("图标链接", new FieldType("iconUrl", "java.lang.String"));
        habitTemplateFields.put("图标颜色", new FieldType("iconColor", "java.lang.String"));
        habitTemplateFields.put("tag", new FieldType("tag", "java.lang.String"));

        //2. 初始化习惯分类
        List<HabAppHabitClass> habitClassList = habAppHabitClassMapper.getAll();
        if(habitClassList.size() > 0) {
            for(HabAppHabitClass habAppHabitClass : habitClassList) {
                classMap.put(habAppHabitClass.getName(), habAppHabitClass.getId());
            }
        }

        //3. 初始化tag
        HabAppHabitTemplateTagExample example = new HabAppHabitTemplateTagExample();
        example.createCriteria().andIsDeletedEqualTo("n");
        List<HabAppHabitTemplateTag> habitTagList = this.habAppHabitTemplateTagMapperExt.selectByExample(example);
        if(habitTagList.size() > 0) {
            for(HabAppHabitTemplateTag habAppHabitTag : habitTagList) {
                tagMap.put(habAppHabitTag.getName(), habAppHabitTag.getId());
            }
        }
    }

//    @Test
    public void testImportHabit() throws Exception {
//        HabAppHabitTemplate record = new HabAppHabitTemplate();
//        this.habAppHabitTemplateMapper.insertSelective(record);
        readExcel("D:\\tmp\\成长记忆基础数据模板(1).xls", 0);
//        HabAppHabitTemplate record = new HabAppHabitTemplate();
//        habAppHabitTemplateMapper.insertSelective(record);
    }

    /**
     * 读取指定Sheet也的内容
     * @param filepath filepath 文件全路径
     * @param sheetNo sheet序号,从0开始,如果读取全文sheetNo设置null
     */
    public String readExcel(String filepath, Integer sheetNo)
            throws EncryptedDocumentException, InvalidFormatException, IOException {
        StringBuilder sb = new StringBuilder();
        Workbook workbook = getWorkbook(filepath);
        if (workbook != null) {
            if (sheetNo == null) {
                int numberOfSheets = workbook.getNumberOfSheets();
                for (int i = 0; i < numberOfSheets; i++) {
                    Sheet sheet = workbook.getSheetAt(i);
                    if (sheet == null) {
                        continue;
                    }
                }
            } else {
                Sheet sheet = workbook.getSheetAt(sheetNo);
                if (sheet != null) {
                    readExcelSheet(sheet);
                }
            }
        }
        return sb.toString();
    }

    private String readExcelSheet(Sheet sheet) {
        StringBuilder sb = new StringBuilder();
        if(sheet != null){
            int rowNos = sheet.getLastRowNum();// 得到excel的总记录条数
//            rowNos = 2;
            Map<Integer, FieldType> properties = new HashMap<>();
            String cellValue = null;
            HabAppHabitTemplate record = null;
            for (int i = 1; i <= rowNos; i++) {// 遍历行
                Row row = sheet.getRow(i);
                if(row != null){
                    int columNos = row.getLastCellNum();// 表头总共的列数
                    record = new HabAppHabitTemplate();
                    for (int j = 0; j < columNos; j++) {
                        Cell cell = row.getCell(j);
                        if(cell != null){
                            cell.setCellType(CellType.STRING);
                            cellValue = cell.getStringCellValue();
                            if(cellValue != null) {
                                if(i == 1) {
                                    if(habitTemplateFields.containsKey(cellValue)) {
                                        properties.put(j, habitTemplateFields.get(cellValue));
                                    } else {
                                        Objects.nonNull(null);
                                    }
                                } else {
                                    if(StringUtils.isNotEmpty(cellValue)) {//非空
                                        cellValue = cellValue.trim();
                                        cellValue = cellValue.replaceAll("，", ",");
                                        cellValue = cellValue.replaceAll("、", ",");
                                        cellValue = cellValue.replaceAll("：", ":");

                                        FieldType fieldType = properties.get(j);
                                        if("class1".equals(fieldType.getFieldName())) {
                                            record.setClass1(classMap.get(cellValue));
                                        } else if("class2".equals(fieldType.getFieldName())) {
                                            record.setClass2(classMap.get(cellValue));
                                        } else if("eachTime".equals(fieldType.getFieldName())) {
                                            Integer time = Integer.parseInt(cellValue);
                                            record.setEachTime(time);
                                            record.setTimer(secondToTime(time));
                                        } else if("timer".equals(fieldType.getFieldName())) {
                                            //不处理
                                        } else if("reminds".equals(fieldType.getFieldName())) {
                                            List<HabitRemindVo> remindVoList = new ArrayList<>();
                                            if("上午".equals(cellValue)) {
                                                cellValue = "7:30";
                                            } else if("下午".equals(cellValue)) {
                                                cellValue = "16:00";
                                            } else if("晚上".equals(cellValue)) {
                                                cellValue = "20:00";
                                            }
                                            String[] remindStrArr = cellValue.split(",");
                                            for(String remindStr : remindStrArr) {
                                                HabitRemindVo vo = new HabitRemindVo();
                                                String[] timeArr = remindStr.split(":");
                                                vo.setHour(Integer.parseInt(timeArr[0]));
                                                if(timeArr.length == 2) {
                                                    vo.setMinute(Integer.parseInt(timeArr[1]));
                                                } else if(timeArr.length == 3) {
                                                    vo.setMinute(Integer.parseInt(timeArr[1]));
                                                    vo.setSecond(Integer.parseInt(timeArr[2]));
                                                }
                                                vo.setId(0l);
                                                vo.setTitle("");
                                                vo.setBody("");
                                                vo.setStatus(true);
                                                vo.setLabel(vo.getHour() + "时" + (vo.getMinute() == 0?"":vo.getMinute() + "分") + (vo.getSecond() == 0?"":vo.getSecond() + "秒"));
                                                remindVoList.add(vo);
                                            }
                                            record.setRemind(JSONArray.toJSONString(remindVoList));
                                        } else if("type".equals(fieldType.getFieldName())) {
                                            if("计数".equals(cellValue)) {
                                                record.setType(HabitTypeEnum.COUNT.name());
                                            } else if("计时".equals(cellValue)) {
                                                record.setType(HabitTypeEnum.TIME.name());
                                            }
                                        } else {
                                            if(Integer.class.getName().equals(fieldType.getFieldType())) {//
                                                ReflectionUtil.setFieldValueByFieldName(fieldType.getFieldName(), record, Integer.parseInt(cellValue));
                                            } else if(Long.class.getName().equals(fieldType.getFieldType())) {
                                                ReflectionUtil.setFieldValueByFieldName(fieldType.getFieldName(), record, Long.parseLong(cellValue));
                                            } else {
                                                if(!"tag".equals(fieldType.getFieldName())) {
                                                    ReflectionUtil.setFieldValueByFieldName(fieldType.getFieldName(), record, cellValue);
                                                }
                                            }
                                        }
                                        if(record != null) {
                                            if(j == (columNos - 1)) {//如果最后一个字段设置完成后，进行保存
                                                this.habAppHabitTemplateMapper.insertSelective(record);
                                            }
                                            if("tag".equals(fieldType.getFieldName())) {
                                                HabAppTemplateTagRe habAppTemplateTagRe = null;
                                                String[] tagArr = cellValue.split(",");
                                                for(String tag : tagArr) {
                                                    habAppTemplateTagRe = new HabAppTemplateTagRe();
                                                    habAppTemplateTagRe.setTagId(tagMap.get(tag));
                                                    habAppTemplateTagRe.setTemplateId(record.getId());
                                                    habAppTemplateTagReMapperExt.insertSelective(habAppTemplateTagRe);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * 根据文件路径获取Workbook对象
     * @param filepath 文件全路径
     */
    public Workbook getWorkbook(String filepath)
            throws EncryptedDocumentException, InvalidFormatException, IOException {
        InputStream is = null;
        Workbook wb = null;
        if (StringUtils.isBlank(filepath)) {
            throw new IllegalArgumentException("文件路径不能为空");
        } else {
            String suffiex = getSuffiex(filepath);
            if (StringUtils.isBlank(suffiex)) {
                throw new IllegalArgumentException("文件后缀不能为空");
            }
            if (OFFICE_EXCEL_XLS.equals(suffiex) || OFFICE_EXCEL_XLSX.equals(suffiex)) {
                try {
                    is = new FileInputStream(filepath);
                    wb = WorkbookFactory.create(is);
                } finally {
                    if (is != null) {
                        is.close();
                    }
                    if (wb != null) {
                        wb.close();
                    }
                }
            } else {
                throw new IllegalArgumentException("该文件非Excel文件");
            }
        }
        return wb;
    }

    /**
     * 获取后缀
     * @param filepath filepath 文件全路径
     */
    private String getSuffiex(String filepath) {
        if (StringUtils.isBlank(filepath)) {
            return "";
        }
        int index = filepath.lastIndexOf(".");
        if (index == -1) {
            return "";
        }
        return filepath.substring(index + 1, filepath.length());
    }


    /**
     * 返回日时分秒
     * @param second
     * @return
     */
    private String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
        if (0 < days){
            return (days==0?"":days + "天") + (hours==0?"":hours + "小时") + (minutes==0?"":minutes + "分钟") + (second==0?"":second + "秒");
        }else {
            return (hours==0?"":hours + "小时") + (minutes==0?"":minutes + "分钟") + (second==0?"":second + "秒");
        }
    }

    protected class FieldType{
        private String fieldName;
        private String fieldType;
        public FieldType(String fieldName, String fieldType) {
            this.fieldName = fieldName;
            this.fieldType = fieldType;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getFieldType() {
            return fieldType;
        }

        public void setFieldType(String fieldType) {
            this.fieldType = fieldType;
        }
    }
}
