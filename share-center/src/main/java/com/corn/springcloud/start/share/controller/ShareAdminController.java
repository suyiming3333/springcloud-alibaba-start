package com.corn.springcloud.start.share.controller;


import com.corn.springcloud.start.dto.ShareAuditDto;
import com.corn.springcloud.start.dto.ShareDto;
import com.corn.springcloud.start.share.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shares")
public class ShareAdminController {

    @Autowired
    private ShareService shareService;

    @PutMapping("/audit/{id}")
    public ShareDto auditById(@PathVariable(required = true,value = "id")Integer id, @RequestBody ShareAuditDto shareAuditDto){
        //todo 认证 授权
        return shareService.auditById(id,shareAuditDto);

    }

}
