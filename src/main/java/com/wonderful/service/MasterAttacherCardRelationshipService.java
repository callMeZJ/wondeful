package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.MasterAttacherCardRelationshipDTO;
import com.wonderful.bean.dto.MasterCardOpenBillDetailsDTO;
import com.wonderful.bean.entity.AttacherCardSaleDetails;
import com.wonderful.bean.entity.MasterAttacherCardRelationship;
import com.wonderful.bean.entity.MasterCardOpenBillDetails;

import java.util.List;


public interface MasterAttacherCardRelationshipService extends IService<MasterAttacherCardRelationship> {

    IPage<MasterAttacherCardRelationship> page(MasterAttacherCardRelationshipDTO masterAttacherCardRelationshipDTO);

    List<MasterAttacherCardRelationship> getByMasterCardNumList(List<String> masterCardNumList);
}
