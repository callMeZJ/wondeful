package com.wonderful.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonderful.bean.dto.MasterAttacherCardRelationshipDTO;
import com.wonderful.bean.dto.MasterCardOpenBillDetailsDTO;
import com.wonderful.bean.entity.MasterAttacherCardRelationship;
import com.wonderful.bean.entity.MasterCardOpenBillDetails;


public interface MasterAttacherCardRelationshipService extends IService<MasterAttacherCardRelationship> {

    IPage<MasterAttacherCardRelationship> page(MasterAttacherCardRelationshipDTO masterAttacherCardRelationshipDTO);
}
