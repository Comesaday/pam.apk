package cn.comesaday.avt.example.designmode.celue.bean;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;

/**
 * <描述> FactoryListEditor
 * <详细背景>
 * @author: ChenWei
 * @CreateAt: 2021-04-14 14:03
 */
public class FactoryListEditor extends CustomCollectionEditor {

    public FactoryListEditor() {
        super(FactoryArrayList.class);
    }
}
