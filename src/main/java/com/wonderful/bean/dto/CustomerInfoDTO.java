package com.wonderful.bean.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoDTO {

    @TableId(type = IdType.AUTO)
    private BigInteger id;

    private String customerAlias;

    private String customerNum;

    private String customerName;

    private String telNum;

    private String sex;

    private String idCard;

    private String area;

    private Timestamp registerTime;

    private String carSize;

    private String carLoadSize;

    private String carNum;

    private String carUse;

    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    private Integer page;

    private Integer rows;
}
