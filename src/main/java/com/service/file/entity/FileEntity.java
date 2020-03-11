package com.service.file.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity {
    private long size;
    private String type;
    private String name;
    private long createTime;
    private String dir;
    //数字加密信封
    private String envelope;
    private String formatTime;


}
