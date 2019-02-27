package com.java.hadoop.filemanager;

import com.java.hadoop.linux.filecontroller.download.LinuxFileDomain;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class FileData {
    String userName;
    List<LinuxFileDomain> linuxFileDomains = new ArrayList<LinuxFileDomain>() ;

}
