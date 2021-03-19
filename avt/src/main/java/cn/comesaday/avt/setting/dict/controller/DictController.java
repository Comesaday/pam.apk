package cn.comesaday.avt.setting.dict.controller;

import cn.comesaday.avt.setting.dict.model.Dict;
import cn.comesaday.avt.setting.dict.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <描述> DictController
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-03-17 16:22
 */
@Controller
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * <说明> 数据字典主页面
     * @param model Model
     * @author ChenWei
     * @date 2021/3/18 13:55
     * @return java.lang.String
     */
    @RequestMapping("/index")
    public String list(Model model) {
        Dict dict = new Dict();
        List<Dict> dicts = dictService.findAll(dict);
        model.addAttribute("dicts", dicts);
        return "dict/index";
    }

    /**
     * <说明> 编辑数据字典
     * @param dictId Long
     * @author ChenWei
     * @date 2021/3/18 14:34
     * @return java.lang.String
     */
    @RequestMapping("/edit/{dictId}")
    public String edit(Model model, @PathVariable(name = "dictId") Long dictId) {
        Dict dict = dictService.findOne(dictId);
        model.addAttribute("dict", dict);
        return "dict/edit";
    }

    /**
     * <说明> 保存数据字典
     * @param dict Dict
     * @author ChenWei
     * @date 2021/3/18 14:34
     * @return java.lang.String
     */
    @RequestMapping("/save")
    public String save(Dict dict) {
        dictService.saveAndUpdate(dict);
        return "formard:/dict/index";
    }

    /**
     * <说明> 删除数据字典
     * @param dictId Long
     * @author ChenWei
     * @date 2021/3/18 14:55
     * @return java.lang.String
     */
    @RequestMapping("/delete/{dictId}")
    public String delete(@PathVariable(name = "dictId") Long dictId) {
        dictService.delete(dictId);
        return "formard:/dict/index";
    }

    /**
     * <说明> 查询事项
     * @param model Model
     * @author ChenWei
     * @date 2021/3/18 16:06
     * @return java.lang.String
     */
    @RequestMapping("/list/process")
    public String listProcess(Model model) {
//        List<Dict> dicts = dictService.listProcess(FormEnum.PROCESS.getCode());
//        model.addAttribute("dicts", dicts);
        return "dict/index";
    }
}
