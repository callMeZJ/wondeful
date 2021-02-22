package com.wonderful.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wonderful.bean.entity.MasterAttacherCardRelationship;
import com.wonderful.bean.entity.MasterCardPurchaseDetails;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterAttacherCardRelationshipMapper extends BaseMapper<MasterAttacherCardRelationship> {

    @Update("UPDATE `master_attacher_card_relationship` a SET a.is_use='yes' WHERE EXISTS (SELECT b.card_num FROM attacher_card_sale_details b WHERE a.attacher_card_num = b.card_num )")
    void updateIsUse();
}
